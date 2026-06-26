package com.techventa.multitienda.admin.service;

import com.techventa.multitienda.admin.model.Oferta;
import com.techventa.multitienda.admin.model.EstadoOferta; 
import com.techventa.multitienda.admin.repository.OfertaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class OfertaService {

    @Autowired
    private OfertaRepository ofertaRepository;

    public List<Oferta> listarTodas() {
        return ofertaRepository.findAll();
    }

    public List<Oferta> listarActivas() {
        return ofertaRepository.findByActivoTrue();
    }

    public List<Oferta> listarPorEstado(Integer idEstadoOferta) {
        return ofertaRepository.findByEstadoOferta_IdEstadoOferta(idEstadoOferta);
    }

    public List<Oferta> listarVigentes() {
        LocalDateTime ahora = LocalDateTime.now();
        return ofertaRepository.findByFechaInicioBeforeAndFechaFinAfter(ahora, ahora);
    }

    public Optional<Oferta> buscarPorId(Integer id) {
        return ofertaRepository.findById(id);
    }

    public List<Oferta> buscarPorNombre(String nombre) {
        return ofertaRepository.findByNombreOfertaContainingIgnoreCase(nombre);
    }

    public List<Oferta> buscarPorTipoDescuento(Integer idTipoDescuento) {
        return ofertaRepository.findByTipoDescuento_IdTipoDescuento(idTipoDescuento);
    }

    public List<Oferta> buscarPorPrioridad(Integer prioridad) {
        return ofertaRepository.findByPrioridad(prioridad);
    }

    public Oferta crear(Oferta oferta) {
        oferta.setActivo(true);
        return ofertaRepository.save(oferta);
    }

    public Oferta actualizar(Oferta oferta) {
        return ofertaRepository.save(oferta);
    }

    // ============================================================
    // APROBAR OFERTA - CORREGIDO ✅
    // ============================================================
    public Oferta aprobar(Integer id, Integer idUsuarioAprobador) {
        Optional<Oferta> ofertaOpt = ofertaRepository.findById(id);
        if (ofertaOpt.isPresent()) {
            Oferta oferta = ofertaOpt.get();
            
            // 🔥 CORRECCIÓN: Asignar el objeto EstadoOferta con ID 2 (ACTIVA)
            EstadoOferta estadoActiva = new EstadoOferta();
            estadoActiva.setIdEstadoOferta(2); // ACTIVA
            oferta.setEstadoOferta(estadoActiva);
            
            oferta.setUsuarioAprueba(idUsuarioAprobador);
            oferta.setFechaAprobacion(LocalDateTime.now());
            
            return ofertaRepository.save(oferta);
        }
        return null;
    }

    public Oferta suspender(Integer id, Integer idUsuario) {
        Optional<Oferta> opt = ofertaRepository.findById(id);
        if (opt.isEmpty()) return null;
        
        Oferta oferta = opt.get();
        EstadoOferta estadoSuspender = new EstadoOferta();
        estadoSuspender.setIdEstadoOferta(4); // SUSPENDIDA
        oferta.setEstadoOferta(estadoSuspender);
        
        return ofertaRepository.save(oferta);
    }

    public Oferta reactivar(Integer id, Integer idUsuario) {
        Optional<Oferta> opt = ofertaRepository.findById(id);
        if (opt.isEmpty()) return null;
        
        Oferta oferta = opt.get();
        EstadoOferta estadoActiva = new EstadoOferta();
        estadoActiva.setIdEstadoOferta(2); // ACTIVA
        oferta.setEstadoOferta(estadoActiva);
        
        return ofertaRepository.save(oferta);
    }

    public Oferta finalizar(Integer id) {
        Optional<Oferta> opt = ofertaRepository.findById(id);
        if (opt.isEmpty()) return null;
        
        Oferta oferta = opt.get();
        EstadoOferta estadoFinalizada = new EstadoOferta();
        estadoFinalizada.setIdEstadoOferta(3); // FINALIZADA
        oferta.setEstadoOferta(estadoFinalizada);
        oferta.setFechaFin(LocalDateTime.now()); // Opcional: marcar fecha de fin
        
        return ofertaRepository.save(oferta);
    }
    
    
    // ============================================================
    // ELIMINAR FÍSICAMENTE
    // ============================================================
    public void eliminarFisico(Integer id) {
        ofertaRepository.deleteById(id);
    }

    public boolean existeNombre(String nombre) {
        return ofertaRepository.existsByNombreOferta(nombre);
    }
}