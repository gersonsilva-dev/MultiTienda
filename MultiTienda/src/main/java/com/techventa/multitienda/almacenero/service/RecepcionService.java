package com.techventa.multitienda.almacenero.service;

import com.techventa.multitienda.almacenero.model.*;
import com.techventa.multitienda.almacenero.repository.*;
import com.techventa.multitienda.admin.model.*;
import com.techventa.multitienda.admin.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class RecepcionService {

    @Autowired
    private RecepcionRepository recepcionRepository;
    
    @Autowired
    private DetalleRecepcionRepository detalleRecepcionRepository;
    
    @Autowired
    private InventarioRepository inventarioRepository;
    
    @Autowired
    private LoteRepository loteRepository;
    
    @Autowired
    private KardexService kardexService;

    public List<RecepcionMercaderia> listarTodas() { return recepcionRepository.findAll(); }
    
    public List<RecepcionMercaderia> listarPorProveedor(Integer idProveedor) { 
        return recepcionRepository.findByProveedor_IdProveedor(idProveedor); 
    }
    
    public List<RecepcionMercaderia> listarPorTienda(Integer idTienda) { 
        return recepcionRepository.findByTienda_IdTienda(idTienda); 
    }
    
    public Optional<RecepcionMercaderia> buscarPorId(Integer id) { 
        return recepcionRepository.findById(id); 
    }

    @Transactional
    public RecepcionMercaderia registrarRecepcion(RecepcionMercaderia recepcion, List<DetalleRecepcion> detalles) {
        recepcion.setFechaRecepcion(LocalDateTime.now());
        recepcion.setActivo(true);
        RecepcionMercaderia saved = recepcionRepository.save(recepcion);
        
        for (DetalleRecepcion detalle : detalles) {
            detalle.setRecepcion(saved);
            detalleRecepcionRepository.save(detalle);
            
            // Actualizar stock
            Optional<InventarioTienda> invOpt = inventarioRepository
                    .findByProducto_IdProductoAndTienda_IdTienda(
                            detalle.getProducto().getIdProducto(),
                            recepcion.getTienda().getIdTienda());
            
            if (invOpt.isPresent()) {
                InventarioTienda inventario = invOpt.get();
                int stockAntes = inventario.getStockActual();
                inventario.setStockActual(stockAntes + detalle.getCantidadRecibida());
                inventarioRepository.save(inventario);
                
                // Registrar en Kardex
                kardexService.registrarMovimiento(
                    detalle.getProducto(),
                    recepcion.getTienda(),
                    detalle.getLote(),
                    detalle.getCantidadRecibida(),
                    stockAntes,
                    inventario.getStockActual(),
                    "ENTRADA POR COMPRA",
                    saved.getIdRecepcion(),
                    "recepcion_mercaderia",
                    "Recepción #" + saved.getIdRecepcion(),
                    recepcion.getAlmacenero()
                );
            }
        }
        return saved;
    }
}