package com.techventa.multitienda.cajero.repository;

import com.techventa.multitienda.cajero.model.RetiroCaja;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RetiroRepository extends JpaRepository<RetiroCaja, Integer> {

    @Query("SELECT SUM(r.monto) FROM RetiroCaja r WHERE r.turnoCaja.idTurnoCaja = :idTurnoCaja")
    Double sumMontoByTurnoCajaIdTurnoCaja(@Param("idTurnoCaja") Integer idTurnoCaja);

    List<RetiroCaja> findByTurnoCajaIdTurnoCajaOrderByFechaRetiroDesc(Integer idTurnoCaja);
}