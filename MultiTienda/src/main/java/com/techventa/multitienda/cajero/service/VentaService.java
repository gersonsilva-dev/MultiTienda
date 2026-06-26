package com.techventa.multitienda.cajero.service;

import com.techventa.multitienda.cajero.model.*;
import com.techventa.multitienda.cajero.repository.*;
import com.techventa.multitienda.admin.model.*;
import com.techventa.multitienda.admin.repository.*;
import com.techventa.multitienda.almacenero.model.InventarioTienda;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class VentaService {

    @Autowired
    private VentaRepository ventaRepository;
    
    @Autowired
    private DetalleVentaRepository detalleVentaRepository;
    
    @Autowired
    private InventarioTiendaRepository inventarioRepository;
    
    @Autowired
    private LoteRepository loteRepository;
    
    @Autowired
    private EstadoVentaRepository estadoVentaRepository;
    
    @Autowired
    private PagoRepository pagoRepository;
    
    @Autowired
    private MetodoPagoRepository metodoPagoRepository;
    
    @Autowired
    private EstadoPagoRepository estadoPagoRepository;
    
    @Autowired
    private TurnoCajaRepository turnoCajaRepository;

    private String generarCodigoVenta() {
        return "VEN-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
    }

    public List<Venta> listarTodas() { return ventaRepository.findAll(); }
    
    public List<Venta> listarActivas() { return ventaRepository.findByActivoTrue(); }
    
    public List<Venta> listarPorCajero(Integer idCajero) { return ventaRepository.findByCajero_IdUsuario(idCajero); }
    
    public List<Venta> listarPorTienda(Integer idTienda) { return ventaRepository.findByTienda_IdTienda(idTienda); }
    
    public List<Venta> listarPorEstado(Integer idEstado) { return ventaRepository.findByEstadoVenta_IdEstadoVenta(idEstado); }
    
    public Optional<Venta> buscarPorId(Integer id) { return ventaRepository.findById(id); }
    
    public Optional<Venta> buscarPorCodigo(String codigo) { return ventaRepository.findByCodigoVenta(codigo); }

    @Transactional
    public Venta registrarVenta(Venta venta, List<DetalleVenta> detalles) {
        // ================================================================
        // 1. GUARDAR LA VENTA
        // ================================================================
        venta.setCodigoVenta(generarCodigoVenta());
        venta.setFechaVenta(LocalDateTime.now());
        
        // Calcular subtotal, IGV y total
        BigDecimal subtotal = BigDecimal.ZERO;
        for (DetalleVenta detalle : detalles) {
            BigDecimal subtotalLinea = detalle.getPrecioUnitario()
                    .multiply(BigDecimal.valueOf(detalle.getCantidad()))
                    .subtract(detalle.getDescuentoLinea());
            detalle.setSubtotalLinea(subtotalLinea);
            subtotal = subtotal.add(subtotalLinea);
        }
        
        venta.setSubtotal(subtotal);
        BigDecimal igv = subtotal.multiply(new BigDecimal("0.18")).setScale(2, RoundingMode.HALF_UP);
        venta.setIgv(igv);
        venta.setTotal(subtotal.add(igv));
        
        // Estado por defecto: COMPLETADO
        Optional<EstadoVenta> estadoOpt = estadoVentaRepository.findByNombreEstado("COMPLETADO");
        estadoOpt.ifPresent(venta::setEstadoVenta);
        
        // 🔥 Guardar el método de pago (viene del frontend)
        if (venta.getMetodoPago() == null || venta.getMetodoPago().isEmpty()) {
            venta.setMetodoPago("EFECTIVO");
        }
        venta.setMetodoPago(venta.getMetodoPago().toUpperCase());
        
        venta.setActivo(true);
        Venta saved = ventaRepository.save(venta);
        
        // ================================================================
        // 2. GUARDAR DETALLES Y ACTUALIZAR STOCK
        // ================================================================
        for (DetalleVenta detalle : detalles) {
            detalle.setVenta(saved);
            detalleVentaRepository.save(detalle);
            
            // Actualizar stock en inventario
            Optional<InventarioTienda> invOpt = inventarioRepository
                    .findByProducto_IdProductoAndTienda_IdTienda(
                            detalle.getProducto().getIdProducto(),
                            venta.getTienda().getIdTienda());
            
            if (invOpt.isPresent()) {
                InventarioTienda inventario = invOpt.get();
                inventario.setStockActual(inventario.getStockActual() - detalle.getCantidad());
                inventarioRepository.save(inventario);
            }
            
            // Actualizar stock de lote (si existe)
            if (detalle.getLote() != null) {
                Optional<Lote> loteOpt = loteRepository.findById(detalle.getLote().getIdLote());
                if (loteOpt.isPresent()) {
                    Lote lote = loteOpt.get();
                    lote.setStockLote(lote.getStockLote() - detalle.getCantidad());
                    loteRepository.save(lote);
                }
            }
        }
        
        // ================================================================
        // 3. CREAR EL REGISTRO DE PAGO
        // ================================================================
        String metodoNombre = venta.getMetodoPago();
        
        // Buscar o crear el método de pago
        MetodoPago metodoPago = metodoPagoRepository
                .findByNombreMetodoIgnoreCase(metodoNombre)
                .orElseGet(() -> {
                    MetodoPago nuevo = new MetodoPago(metodoNombre);
                    return metodoPagoRepository.save(nuevo);
                });
        
        // Buscar estado de pago "COMPLETADO" (suponiendo que existe)
        EstadoPago estadoPago = estadoPagoRepository
                .findByNombreEstado("COMPLETADO")
                .orElseThrow(() -> new RuntimeException("Estado de pago 'COMPLETADO' no encontrado"));
        
        Pago pago = new Pago();
        pago.setVenta(saved);
        pago.setMetodoPago(metodoPago);
        pago.setMonto(saved.getTotal());
        pago.setEstadoPago(estadoPago);
        pago.setFechaPago(LocalDateTime.now());
        pagoRepository.save(pago);
        
        // ================================================================
        // 4. ACTUALIZAR TOTALES DEL TURNO
        // ================================================================
        actualizarTotalesTurno(saved, metodoNombre);
        
        return saved;
    }

    private void actualizarTotalesTurno(Venta venta, String metodo) {
        // Obtener el turno completo desde la base de datos
        TurnoCaja turno = venta.getTurnoCaja();
        if (turno == null || turno.getIdTurnoCaja() == null) {
            throw new RuntimeException("La venta no tiene turno de caja asociado o ID nulo");
        }
        
        TurnoCaja turnoPersistido = turnoCajaRepository.findById(turno.getIdTurnoCaja())
                .orElseThrow(() -> new RuntimeException("Turno no encontrado con ID: " + turno.getIdTurnoCaja()));

        BigDecimal monto = venta.getTotal();
        String metodoUpper = metodo.toUpperCase();

        // Inicializar si es null
        if (turnoPersistido.getTotalVentasEfectivo() == null) turnoPersistido.setTotalVentasEfectivo(BigDecimal.ZERO);
        if (turnoPersistido.getTotalVentasTarjeta() == null) turnoPersistido.setTotalVentasTarjeta(BigDecimal.ZERO);
        if (turnoPersistido.getTotalVentasYape() == null) turnoPersistido.setTotalVentasYape(BigDecimal.ZERO);
        if (turnoPersistido.getTotalVentasPlin() == null) turnoPersistido.setTotalVentasPlin(BigDecimal.ZERO);

        switch (metodoUpper) {
            case "EFECTIVO":
                turnoPersistido.setTotalVentasEfectivo(turnoPersistido.getTotalVentasEfectivo().add(monto));
                break;
            case "TARJETA":
                turnoPersistido.setTotalVentasTarjeta(turnoPersistido.getTotalVentasTarjeta().add(monto));
                break;
            case "YAPE":
            case "YAPEPLIN":
                turnoPersistido.setTotalVentasYape(turnoPersistido.getTotalVentasYape().add(monto));
                break;
            case "PLIN":
                turnoPersistido.setTotalVentasPlin(turnoPersistido.getTotalVentasPlin().add(monto));
                break;
            default:
                // Si no se reconoce, sumar a efectivo por defecto
                turnoPersistido.setTotalVentasEfectivo(turnoPersistido.getTotalVentasEfectivo().add(monto));
        }
        
        turnoCajaRepository.save(turnoPersistido);
    }

    @Transactional
    public Venta anularVenta(Integer id) {
        Optional<Venta> ventaOpt = ventaRepository.findById(id);
        if (ventaOpt.isPresent()) {
            Venta venta = ventaOpt.get();
            Optional<EstadoVenta> estadoOpt = estadoVentaRepository.findByNombreEstado("CANCELADO");
            estadoOpt.ifPresent(venta::setEstadoVenta);
            
            // Devolver stock
            List<DetalleVenta> detalles = detalleVentaRepository.findByVenta_IdVenta(id);
            for (DetalleVenta detalle : detalles) {
                Optional<InventarioTienda> invOpt = inventarioRepository
                        .findByProducto_IdProductoAndTienda_IdTienda(
                                detalle.getProducto().getIdProducto(),
                                venta.getTienda().getIdTienda());
                if (invOpt.isPresent()) {
                    InventarioTienda inventario = invOpt.get();
                    inventario.setStockActual(inventario.getStockActual() + detalle.getCantidad());
                    inventarioRepository.save(inventario);
                }
            }
            
            return ventaRepository.save(venta);
        }
        return null;
    }
}