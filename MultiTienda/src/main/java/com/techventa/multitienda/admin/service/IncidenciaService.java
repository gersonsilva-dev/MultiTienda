package com.techventa.multitienda.admin.service;

import com.techventa.multitienda.admin.model.Incidencia;
import com.techventa.multitienda.admin.model.Usuario;
import com.techventa.multitienda.admin.repository.IncidenciaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class IncidenciaService {

    @Autowired
    private IncidenciaRepository incidenciaRepository;

    private String generarCodigo() {
        return "INC-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
    }

    public List<Incidencia> listarTodas() { return incidenciaRepository.findAll(); }
    public List<Incidencia> listarActivas() { return incidenciaRepository.findByActivoTrue(); }
    public List<Incidencia> listarPorSupervisor(Integer idSupervisor) { return incidenciaRepository.findBySupervisor_IdUsuario(idSupervisor); }
    public List<Incidencia> listarPorTienda(Integer idTienda) { return incidenciaRepository.findByTienda_IdTienda(idTienda); }
    public List<Incidencia> listarPorEstado(Integer idEstado) { return incidenciaRepository.findByEstadoIncidencia_IdEstadoIncidencia(idEstado); }
    public List<Incidencia> listarPorPrioridad(Integer nivel) { return incidenciaRepository.findByPrioridad_Nivel(nivel); }
    public Optional<Incidencia> buscarPorId(Integer id) { return incidenciaRepository.findById(id); }
    public Optional<Incidencia> buscarPorCodigo(String codigo) { return incidenciaRepository.findByCodigoIncidencia(codigo); }
    public List<Incidencia> buscarPorTitulo(String titulo) { return incidenciaRepository.findByTituloContainingIgnoreCase(titulo); }

    public Incidencia crear(Incidencia incidencia) {
        incidencia.setCodigoIncidencia(generarCodigo());
        incidencia.setActivo(true);
        return incidenciaRepository.save(incidencia);
    }

    public Incidencia actualizar(Incidencia incidencia) { return incidenciaRepository.save(incidencia); }

    public Incidencia resolver(Integer id, Integer idAdmin, String respuesta) {
        Optional<Incidencia> incidenciaOpt = incidenciaRepository.findById(id);
        if (incidenciaOpt.isPresent()) {
            Incidencia incidencia = incidenciaOpt.get();
            incidencia.setAdminResuelve(new Usuario());
            incidencia.getAdminResuelve().setIdUsuario(idAdmin);
            incidencia.setRespuestaAdmin(respuesta);
            incidencia.setFechaResolucion(LocalDateTime.now());
            return incidenciaRepository.save(incidencia);
        }
        return null;
    }

    public Incidencia cerrar(Integer id) {
        Optional<Incidencia> incidenciaOpt = incidenciaRepository.findById(id);
        if (incidenciaOpt.isPresent()) {
            Incidencia incidencia = incidenciaOpt.get();
            incidencia.setFechaCierre(LocalDateTime.now());
            return incidenciaRepository.save(incidencia);
        }
        return null;
    }

    public void eliminarLogico(Integer id) {
        incidenciaRepository.findById(id).ifPresent(incidencia -> {
            incidencia.setActivo(false);
            incidenciaRepository.save(incidencia);
        });
    }

    public void eliminarFisico(Integer id) { incidenciaRepository.deleteById(id); }
    public boolean existeCodigo(String codigo) { return incidenciaRepository.existsByCodigoIncidencia(codigo); }
}