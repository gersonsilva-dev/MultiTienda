package com.techventa.multitienda.admin.service;

import com.techventa.multitienda.admin.model.*;
import com.techventa.multitienda.admin.repository.*;
import com.techventa.multitienda.almacenero.model.InventarioTienda;
import com.techventa.multitienda.almacenero.repository.MotivoMermaRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.HashMap;
import java.util.Map;

@Service
public class MermaService {

    @Autowired
    private MermaRepository mermaRepository;

    @Autowired
    private InventarioTiendaRepository inventarioTiendaRepository;

    @Autowired
    private LoteRepository loteRepository;

    @Autowired
    private MovimientoKardexRepository movimientoKardexRepository;

    @Autowired
    private TipoMovimientoInventarioRepository tipoMovimientoRepository;

    @Autowired
    private MotivoMermaRepository motivoMermaRepository;

    @Autowired
    private ProductoRepository productoRepository;

    // ============================================================
    // MÉTODOS EXISTENTES (NO MODIFICAR)
    // ============================================================

    public List<Merma> listarTodas() { return mermaRepository.findAll(); }
    public List<Merma> listarActivas() { return mermaRepository.findByActivoTrue(); }
    public List<Merma> listarPorProducto(Integer idProducto) { return mermaRepository.findByProducto_IdProducto(idProducto); }
    public List<Merma> listarPorTienda(Integer idTienda) { return mermaRepository.findByTienda_IdTienda(idTienda); }
    public List<Merma> listarPorMotivo(Integer idMotivo) { return mermaRepository.findByMotivoMerma_IdMotivoMerma(idMotivo); }
    public Optional<Merma> buscarPorId(Integer id) { return mermaRepository.findById(id); }

    @Transactional
    public Merma crear(Merma merma) {
        merma.setActivo(true);
        merma.setFechaMerma(LocalDateTime.now());

        // Actualizar stock
        if (merma.getLote() != null && merma.getLote().getIdLote() != null) {
            Optional<Lote> loteOpt = loteRepository.findById(merma.getLote().getIdLote());
            if (loteOpt.isPresent()) {
                Lote lote = loteOpt.get();
                lote.setStockLote(lote.getStockLote() - merma.getCantidad());
                loteRepository.save(lote);
            }
        }

        Optional<InventarioTienda> invOpt = inventarioTiendaRepository
                .findByProducto_IdProductoAndTienda_IdTienda(
                        merma.getProducto().getIdProducto(),
                        merma.getTienda().getIdTienda());

        if (invOpt.isPresent()) {
            InventarioTienda inventario = invOpt.get();
            int stockAntes = inventario.getStockActual();
            inventario.setStockActual(stockAntes - merma.getCantidad());
            inventarioTiendaRepository.save(inventario);

            // Registrar en Kardex
            registrarKardex(merma, stockAntes, inventario.getStockActual());
        }

        return mermaRepository.save(merma);
    }

    private void registrarKardex(Merma merma, int stockAntes, int stockDespues) {
        MovimientoKardex movimiento = new MovimientoKardex();
        movimiento.setProducto(merma.getProducto());
        movimiento.setTienda(merma.getTienda());
        movimiento.setLote(merma.getLote());
        movimiento.setCantidad(merma.getCantidad());
        movimiento.setStockAntes(stockAntes);
        movimiento.setStockDespues(stockDespues);
        movimiento.setIdReferencia(merma.getIdMerma());
        movimiento.setTablaReferencia("mermas");
        movimiento.setObservacion("Merma: " + merma.getMotivoMerma().getNombreMotivo());
        movimiento.setFechaMovimiento(LocalDateTime.now());
        movimiento.setUsuarioCreacion(merma.getAlmacenero());

        Optional<TipoMovimientoInventario> tipoMov = tipoMovimientoRepository
        	    .findFirstByNombreMovimientoIgnoreCase("MERMA");
        tipoMov.ifPresent(movimiento::setTipoMovimiento);

        movimientoKardexRepository.save(movimiento);
    }

    public Merma actualizar(Merma merma) { return mermaRepository.save(merma); }

    public void eliminarLogico(Integer id) {
        mermaRepository.findById(id).ifPresent(m -> {
            m.setActivo(false);
            mermaRepository.save(m);
        });
    }

    public void eliminarFisico(Integer id) {
        mermaRepository.deleteById(id);
    }

    // ============================================================
    // ✅ NUEVOS MÉTODOS PARA LA VISTA DE MERMAS (ALMACENERO)
    // ============================================================

    /**
     * Obtiene el resumen de mermas del mes actual para los cards del dashboard
     * @param idTienda ID de la tienda
     * @return Map con totalUnidades, valorPerdidas, motivoPrincipal
     */
    public Map<String, Object> obtenerResumenMermas(Integer idTienda) {
        Map<String, Object> resumen = new HashMap<>();

        Integer totalUnidades = mermaRepository.getTotalUnidadesMermaMes(idTienda);
        Double valorPerdidas = mermaRepository.getValorPerdidasMes(idTienda);
        List<String> motivoPrincipal = mermaRepository.getMotivoPrincipalMes(idTienda);

        resumen.put("totalUnidades", totalUnidades != null ? totalUnidades : 0);
        resumen.put("valorPerdidas", valorPerdidas != null ? valorPerdidas : 0.0);
        resumen.put("motivoPrincipal", motivoPrincipal.isEmpty() ? "Sin datos" : motivoPrincipal.get(0));

        return resumen;
    }

    /**
     * Obtiene las mermas agrupadas por motivo del mes actual
     * @param idTienda ID de la tienda
     * @return Lista de Object[] con [nombreMotivo, cantidadTotal]
     */
    public List<Object[]> obtenerMermasPorMotivoMes(Integer idTienda) {
        return mermaRepository.getMermasPorMotivoMes(idTienda);
    }

    /**
     * Obtiene las últimas 10 mermas registradas en la tienda
     * @param idTienda ID de la tienda
     * @return Lista de las últimas mermas
     */
    public List<Merma> obtenerUltimasMermasRegistradas(Integer idTienda) {
        List<Merma> mermas = mermaRepository.getUltimasMermas(idTienda);
        return mermas.size() > 10 ? mermas.subList(0, 10) : mermas;
    }

    /**
     * Lista todos los motivos de merma activos (para el select del formulario)
     * @return Lista de motivos ordenados alfabéticamente
     */
    public List<MotivoMerma> listarMotivosActivosParaMerma() {
        return motivoMermaRepository.findByActivoTrueOrderByNombreMotivoAsc();
    }

    /**
     * Busca productos por nombre o código de barras (para autocompletado)
     * @param search Texto de búsqueda
     * @return Lista de productos que coinciden
     */
    public List<Producto> buscarProductosParaAutocompletado(String search) {
        return mermaRepository.buscarProductos(search);
    }
    
    /**
     * Registra una merma desde el formulario del almacenero
     * Construye el objeto Merma y lo guarda con toda la lógica de negocio
     */
    @Transactional
    public Merma registrarMermaDesdeFormulario(
            Integer idProducto,
            Integer idTienda,
            Integer cantidad,
            Integer idMotivoMerma,
            String observaciones,
            Usuario almacenero) {

        // Buscar producto
        Producto producto = productoRepository.findById(idProducto)
                .orElseThrow(() -> new RuntimeException("Producto no encontrado"));

        // Buscar tienda
        Tienda tienda = new Tienda();
        tienda.setIdTienda(idTienda);

        // Buscar motivo
        MotivoMerma motivo = motivoMermaRepository.findById(idMotivoMerma)
                .orElseThrow(() -> new RuntimeException("Motivo de merma no encontrado"));

        // Construir objeto Merma
        Merma merma = new Merma();
        merma.setProducto(producto);
        merma.setTienda(tienda);
        merma.setCantidad(cantidad);
        merma.setMotivoMerma(motivo);
        merma.setObservaciones(observaciones);
        merma.setAlmacenero(almacenero);
        merma.setFechaMerma(LocalDateTime.now());
        
        // ✅ CORREGIDO: es String, no enum
        merma.setEstadoAutorizacion("APROBADO");

        // Usar el método crear() existente que ya maneja stock y kardex
        return crear(merma);
    }
}