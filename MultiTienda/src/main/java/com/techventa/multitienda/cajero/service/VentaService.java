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
        // Generar código
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
        
        venta.setActivo(true);
        Venta saved = ventaRepository.save(venta);
        
        // Guardar detalles y actualizar stock
        for (DetalleVenta detalle : detalles) {
            detalle.setVenta(saved);
            detalleVentaRepository.save(detalle);
            
            // Actualizar stock
            Optional<InventarioTienda> invOpt = inventarioRepository
                    .findByProducto_IdProductoAndTienda_IdTienda(
                            detalle.getProducto().getIdProducto(),
                            venta.getTienda().getIdTienda());
            
            if (invOpt.isPresent()) {
                InventarioTienda inventario = invOpt.get();
                inventario.setStockActual(inventario.getStockActual() - detalle.getCantidad());
                inventarioRepository.save(inventario);
            }
            
            // Actualizar stock de lote
            if (detalle.getLote() != null) {
                Optional<Lote> loteOpt = loteRepository.findById(detalle.getLote().getIdLote());
                if (loteOpt.isPresent()) {
                    Lote lote = loteOpt.get();
                    lote.setStockLote(lote.getStockLote() - detalle.getCantidad());
                    loteRepository.save(lote);
                }
            }
        }
        
        return saved;
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