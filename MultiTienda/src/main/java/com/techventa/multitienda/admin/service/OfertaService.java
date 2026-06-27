package com.techventa.multitienda.admin.service;

import com.techventa.multitienda.admin.dto.OfertaDTO;
import com.techventa.multitienda.admin.model.Oferta;
import com.techventa.multitienda.admin.model.EstadoOferta; 
import com.techventa.multitienda.admin.repository.OfertaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class OfertaService {

    @Autowired
    private OfertaRepository ofertaRepository;

    public List<Oferta> listarTodas() {
        return ofertaRepository.findAll();
    }

    public List<Oferta> listarActivas() {
        return ofertaRepository.findByEstadoOferta_IdEstadoOfertaAndActivoTrue(2);
    }

    // 🔥 NUEVO: Método que devuelve DTO para el cajero
    public List<OfertaDTO> listarActivasDTO() {
        List<Oferta> ofertas = ofertaRepository.findByEstadoOferta_IdEstadoOfertaAndActivoTrue(2);
        return ofertas.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    // Método auxiliar para convertir Oferta a OfertaDTO
    private OfertaDTO convertToDTO(Oferta oferta) {
        return new OfertaDTO(
                oferta.getIdOferta(),
                oferta.getNombreOferta(),
                oferta.getValorDescuento(),
                oferta.getCantidadCompra(),
                oferta.getCantidadBeneficio(),
                oferta.getMontoMinimo(),
                oferta.getPrioridad(),
                oferta.getEstadoOferta() != null ? oferta.getEstadoOferta().getNombreEstado() : null,
                oferta.getTipoDescuento() != null ? oferta.getTipoDescuento().getIdTipoDescuento() : null,
                oferta.getTipoDescuento() != null ? oferta.getTipoDescuento().getNombreTipo() : null
        );
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

    // ========== MÉTODOS DE APROBACIÓN / SUSPENSIÓN ==========

    public Oferta aprobar(Integer id, Integer idUsuarioAprobador) {
        Optional<Oferta> ofertaOpt = ofertaRepository.findById(id);
        if (ofertaOpt.isPresent()) {
            Oferta oferta = ofertaOpt.get();
            EstadoOferta estadoActiva = new EstadoOferta();
            estadoActiva.setIdEstadoOferta(2);
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
        estadoSuspender.setIdEstadoOferta(4);
        oferta.setEstadoOferta(estadoSuspender);
        return ofertaRepository.save(oferta);
    }

    public Oferta reactivar(Integer id, Integer idUsuario) {
        Optional<Oferta> opt = ofertaRepository.findById(id);
        if (opt.isEmpty()) return null;
        Oferta oferta = opt.get();
        EstadoOferta estadoActiva = new EstadoOferta();
        estadoActiva.setIdEstadoOferta(2);
        oferta.setEstadoOferta(estadoActiva);
        return ofertaRepository.save(oferta);
    }

    public Oferta finalizar(Integer id) {
        Optional<Oferta> opt = ofertaRepository.findById(id);
        if (opt.isEmpty()) return null;
        Oferta oferta = opt.get();
        EstadoOferta estadoFinalizada = new EstadoOferta();
        estadoFinalizada.setIdEstadoOferta(3);
        oferta.setEstadoOferta(estadoFinalizada);
        oferta.setFechaFin(LocalDateTime.now());
        return ofertaRepository.save(oferta);
    }

    public void eliminarFisico(Integer id) {
        ofertaRepository.deleteById(id);
    }

    public boolean existeNombre(String nombre) {
        return ofertaRepository.existsByNombreOferta(nombre);
    }
}