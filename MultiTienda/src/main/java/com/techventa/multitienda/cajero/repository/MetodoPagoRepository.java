package com.techventa.multitienda.cajero.repository;

import com.techventa.multitienda.cajero.model.MetodoPago;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface MetodoPagoRepository extends JpaRepository<MetodoPago, Integer> {
    Optional<MetodoPago> findByNombreMetodoIgnoreCase(String nombreMetodo);
}