package com.techventa.multitienda.cajero.repository;

import com.techventa.multitienda.cajero.model.EstadoVenta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface EstadoVentaRepository extends JpaRepository<EstadoVenta, Integer> {
    Optional<EstadoVenta> findByNombreEstado(String nombreEstado);
}