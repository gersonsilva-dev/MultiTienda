package com.techventa.multitienda.admin.repository;

import com.techventa.multitienda.admin.model.Turno;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface TurnoRepository extends JpaRepository<Turno, Integer> {

    // Buscar turno por nombre
    Optional<Turno> findByNombreTurno(String nombreTurno);

    // Buscar turnos activos
    List<Turno> findByActivoTrue();

    // Buscar turnos por rango de hora (ejemplo: turnos que empiezan después de una hora)
    List<Turno> findByHoraInicioAfter(LocalTime hora);

    // Verificar si existe nombre de turno
    boolean existsByNombreTurno(String nombreTurno);
}