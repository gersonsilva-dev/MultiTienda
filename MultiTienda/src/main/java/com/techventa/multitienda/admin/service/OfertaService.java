package com.techventa.multitienda.admin.service;

import com.techventa.multitienda.admin.model.Oferta;
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

    public Oferta aprobar(Integer id, Integer idUsuarioAprobador) {
        Optional<Oferta> ofertaOpt = ofertaRepository.findById(id);
        if (ofertaOpt.isPresent()) {
            Oferta oferta = ofertaOpt.get();
            oferta.setUsuarioAprueba(idUsuarioAprobador);
            oferta.setFechaAprobacion(LocalDateTime.now());
            return ofertaRepository.save(oferta);
        }
        return null;
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