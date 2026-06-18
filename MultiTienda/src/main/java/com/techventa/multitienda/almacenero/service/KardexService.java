package com.techventa.multitienda.almacenero.service;

import com.techventa.multitienda.admin.model.*;
import com.techventa.multitienda.admin.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class KardexService {

    @Autowired
    private MovimientoKardexRepository kardexRepository;
    
    @Autowired
    private TipoMovimientoInventarioRepository tipoMovimientoRepository;

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
        
        // Buscar tipo de movimiento
        tipoMovimientoRepository.findByNombreMovimiento(nombreMovimiento)
                .ifPresent(movimiento::setTipoMovimiento);
        
        kardexRepository.save(movimiento);
    }

    public List<MovimientoKardex> listarPorProducto(Integer idProducto) {
        return kardexRepository.findByProducto_IdProducto(idProducto);
    }

    public List<MovimientoKardex> listarPorTienda(Integer idTienda) {
        return kardexRepository.findByTienda_IdTienda(idTienda);
    }

    public List<MovimientoKardex> listarPorFechas(LocalDateTime inicio, LocalDateTime fin) {
        return kardexRepository.findByFechaMovimientoBetween(inicio, fin);
    }
}