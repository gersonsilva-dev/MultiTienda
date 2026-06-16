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

    // Listar todas las ofertas
    public List<Oferta> listarTodas() {
        return ofertaRepository.findAll();
    }

    // Listar solo ofertas activas
    public List<Oferta> listarActivas() {
        return ofertaRepository.findByActivoTrue();
    }

    // Listar ofertas por estado
    public List<Oferta> listarPorEstado(Integer idEstadoOferta) {
        return ofertaRepository.findByEstadoOferta_IdEstadoOferta(idEstadoOferta);
    }

    // Listar ofertas vigentes (activas en este momento)
    public List<Oferta> listarVigentes() {
        LocalDateTime ahora = LocalDateTime.now();
        return ofertaRepository.findByFechaInicioBeforeAndFechaFinAfter(ahora, ahora);
    }

    // Buscar oferta por ID
    public Optional<Oferta> buscarPorId(Integer id) {
        return ofertaRepository.findById(id);
    }

    // Buscar ofertas por nombre
    public List<Oferta> buscarPorNombre(String nombre) {
        return ofertaRepository.findByNombreOfertaContainingIgnoreCase(nombre);
    }

    // Buscar ofertas por tipo de descuento
    public List<Oferta> buscarPorTipoDescuento(Integer idTipoDescuento) {
        return ofertaRepository.findByTipoDescuento_IdTipoDescuento(idTipoDescuento);
    }

    // Buscar ofertas por prioridad
    public List<Oferta> buscarPorPrioridad(Integer prioridad) {
        return ofertaRepository.findByPrioridad(prioridad);
    }

    // Crear nueva oferta (en estado BORRADOR)
    public Oferta crear(Oferta oferta) {
        oferta.setActivo(true);
        return ofertaRepository.save(oferta);
    }

    // Actualizar oferta
    public Oferta actualizar(Oferta oferta) {
        return ofertaRepository.save(oferta);
    }

    // Aprobar oferta (cambiar estado y guardar fecha de aprobación)
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

    // Eliminar oferta (borrado lógico)
    public void eliminarLogico(Integer id) {
        Optional<Oferta> ofertaOpt = ofertaRepository.findById(id);
        if (ofertaOpt.isPresent()) {
            Oferta oferta = ofertaOpt.get();
            oferta.setActivo(false);
            ofertaRepository.save(oferta);
        }
    }

    // Eliminar oferta (borrado físico)
    public void eliminarFisico(Integer id) {
        ofertaRepository.deleteById(id);
    }

    // Verificar si existe nombre de oferta
    public boolean existeNombre(String nombre) {
        return ofertaRepository.existsByNombreOferta(nombre);
    }
}