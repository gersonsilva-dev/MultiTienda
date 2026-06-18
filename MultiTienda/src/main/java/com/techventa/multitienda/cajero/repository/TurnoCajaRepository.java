package com.techventa.multitienda.cajero.repository;

import com.techventa.multitienda.cajero.model.TurnoCaja;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface TurnoCajaRepository extends JpaRepository<TurnoCaja, Integer> {

    List<TurnoCaja> findByCajero_IdUsuario(Integer idCajero);

    List<TurnoCaja> findByTienda_IdTienda(Integer idTienda);

    List<TurnoCaja> findByCaja_IdCaja(Integer idCaja);

    List<TurnoCaja> findByEstadoTurno(String estado);

    Optional<TurnoCaja> findByCaja_IdCajaAndEstadoTurno(Integer idCaja, String estado);

    List<TurnoCaja> findByFechaAperturaBetween(LocalDate inicio, LocalDate fin);
}