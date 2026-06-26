package com.techventa.multitienda.admin.service;

import com.techventa.multitienda.admin.model.Lote;
import com.techventa.multitienda.admin.repository.LoteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class LoteService {

    @Autowired
    private LoteRepository loteRepository;

    public List<Lote> listarTodos() { return loteRepository.findAll(); }
    
    public List<Lote> listarActivos() { return loteRepository.findByActivoTrue(); }
    
    public List<Lote> listarPorProducto(Integer idProducto) { return loteRepository.findByProducto_IdProducto(idProducto); }
    
    public List<Lote> listarPorTienda(Integer idTienda) { return loteRepository.findByTienda_IdTienda(idTienda); }
    
    public List<Lote> listarVencidos() { return loteRepository.findByFechaVencimientoBefore(LocalDate.now()); }
    
    public List<Lote> listarPorVencer() { return loteRepository.findByFechaVencimientoAfter(LocalDate.now()); }
    
    public List<Lote> listarStockBajo(Integer stock) { return loteRepository.findByStockLoteLessThanEqual(stock); }
    
    public Optional<Lote> buscarPorId(Integer id) { return loteRepository.findById(id); }
    
    public Optional<Lote> buscarPorNumeroLote(String numero) { return loteRepository.findByNumeroLote(numero); }
    
    public List<Lote> buscarPorProductoYTienda(Integer idProducto, Integer idTienda) { 
        return loteRepository.findByProducto_IdProductoAndTienda_IdTienda(idProducto, idTienda); 
    }

    public Lote crear(Lote lote) {
        lote.setActivo(true);
        return loteRepository.save(lote);
    }

    public Lote actualizar(Lote lote) { return loteRepository.save(lote); }

    public void eliminarLogico(Integer id) {
        loteRepository.findById(id).ifPresent(lote -> {
            lote.setActivo(false);
            loteRepository.save(lote);
        });
    }

    public void eliminarFisico(Integer id) { loteRepository.deleteById(id); }
    
    public boolean existeNumeroLote(String numero) { return loteRepository.existsByNumeroLote(numero); }
}