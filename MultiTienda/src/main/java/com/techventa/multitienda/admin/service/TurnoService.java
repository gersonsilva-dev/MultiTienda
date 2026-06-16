package com.techventa.multitienda.admin.service;

import com.techventa.multitienda.admin.model.Turno;
import com.techventa.multitienda.admin.repository.TurnoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

@Service
public class TurnoService {

    @Autowired
    private TurnoRepository turnoRepository;

    // Listar todos los turnos
    public List<Turno> listarTodos() {
        return turnoRepository.findAll();
    }

    // Listar solo turnos activos
    public List<Turno> listarActivos() {
        return turnoRepository.findByActivoTrue();
    }

    // Buscar turno por ID
    public Optional<Turno> buscarPorId(Integer id) {
        return turnoRepository.findById(id);
    }

    // Buscar turno por nombre
    public Optional<Turno> buscarPorNombre(String nombre) {
        return turnoRepository.findByNombreTurno(nombre);
    }

    // Buscar turnos que empiezan después de cierta hora
    public List<Turno> buscarPorHoraInicio(LocalTime hora) {
        return turnoRepository.findByHoraInicioAfter(hora);
    }

    // Crear nuevo turno
    public Turno crear(Turno turno) {
        turno.setActivo(true);
        return turnoRepository.save(turno);
    }

    // Actualizar turno
    public Turno actualizar(Turno turno) {
        return turnoRepository.save(turno);
    }

    // Eliminar turno (borrado lógico)
    public void eliminarLogico(Integer id) {
        Optional<Turno> turnoOpt = turnoRepository.findById(id);
        if (turnoOpt.isPresent()) {
            Turno turno = turnoOpt.get();
            turno.setActivo(false);
            turnoRepository.save(turno);
        }
    }

    // Eliminar turno (borrado físico)
    public void eliminarFisico(Integer id) {
        turnoRepository.deleteById(id);
    }

    // Verificar si existe nombre de turno
    public boolean existeNombre(String nombre) {
        return turnoRepository.existsByNombreTurno(nombre);
    }
}