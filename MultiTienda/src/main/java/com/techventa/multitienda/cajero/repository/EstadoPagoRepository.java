package com.techventa.multitienda.cajero.repository;

import com.techventa.multitienda.cajero.model.EstadoPago;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface EstadoPagoRepository extends JpaRepository<EstadoPago, Integer> {
    Optional<EstadoPago> findByNombreEstado(String nombreEstado);
}