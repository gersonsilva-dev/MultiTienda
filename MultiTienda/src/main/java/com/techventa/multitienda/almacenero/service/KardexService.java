package com.techventa.multitienda.almacenero.service;

import com.techventa.multitienda.admin.model.*;
import com.techventa.multitienda.admin.repository.ProductoRepository;
import com.techventa.multitienda.admin.repository.TipoMovimientoInventarioRepository;
import com.techventa.multitienda.almacenero.repository.KardexRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class KardexService {

    @Autowired
    private KardexRepository kardexRepository;

    @Autowired
    private ProductoRepository productoRepo;

    @Autowired
    private TipoMovimientoInventarioRepository tipoMovimientoRepository;

    // ==================== RESUMEN MENSUAL ====================
    public Map<String, Integer> getResumenMensual() {
        Map<String, Integer> resumen = new HashMap<>();
        
        resumen.put("ingresos", kardexRepository.getTotalUnidadesPorTipoMes(1)); // Ajusta IDs si es necesario
        resumen.put("salidas",  kardexRepository.getTotalUnidadesPorTipoMes(2));
        resumen.put("mermas",   kardexRepository.getTotalUnidadesPorTipoMes(3));
        resumen.put("ajustes",  kardexRepository.getTotalUnidadesPorTipoMes(4));

        return resumen;
    }

    // ==================== BÚSQUEDA CON FILTROS ====================
 // Reemplaza o agrega este método
    public Page<MovimientoKardex> buscarMovimientos(String search, Integer tipoMovId, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        
        if (search == null || search.trim().isEmpty()) {
            return kardexRepository.buscarMovimientos(null, tipoMovId, pageable);
        }
        
        // Búsqueda por nombre de producto o código de barras
        return kardexRepository.buscarPorTexto(search.trim(), tipoMovId, pageable);
    }

    // ==================== MÉTODOS AUXILIARES ====================
    public List<Producto> listarProductosParaFiltro() {
        return productoRepo.findAllByActivoTrue();
    }

    

    // ==================== MÉTODOS EXISTENTES (mantenidos) ====================
    public void registrarMovimiento(Producto producto, Tienda tienda, Lote lote,
                                    Integer cantidad, Integer stockAntes, Integer stockDespues,
                                    String nombreMovimiento, Integer idReferencia,
                                    String tablaReferencia, String observacion, Usuario usuario) {

        MovimientoKardex movimiento = new MovimientoKardex();
        movimiento.setProducto(producto);
        movimiento.setTienda(tienda);
        movimiento.setLote(lote);
        movimiento.setCantidad(cantidad);
        movimiento.setStockAntes(stockAntes);
        movimiento.setStockDespues(stockDespues);
        movimiento.setIdReferencia(idReferencia);
        movimiento.setTablaReferencia(tablaReferencia);
        movimiento.setObservacion(observacion);
        movimiento.setFechaMovimiento(LocalDateTime.now());
        movimiento.setUsuarioCreacion(usuario);

        tipoMovimientoRepository.findByNombreMovimiento(nombreMovimiento)
                .ifPresent(movimiento::setTipoMovimiento);

        kardexRepository.save(movimiento);
    }

    public List<TipoMovimientoInventario> listarTiposMovimiento() {
        return tipoMovimientoRepository.findAllByActivoTrueOrderByNombreMovimientoAsc();
    }
    public List<MovimientoKardex> listarPorProducto(Integer idProducto) {
        return kardexRepository.findByProducto_IdProducto(idProducto);
    }

    public List<MovimientoKardex> listarPorTienda(Integer idTienda) {
        return kardexRepository.findByTienda_IdTienda(idTienda);
    }
}