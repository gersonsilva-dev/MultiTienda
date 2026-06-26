package com.techventa.multitienda.cajero.repository;

import com.techventa.multitienda.cajero.model.EstadoDevolucion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface EstadoDevolucionRepository extends JpaRepository<EstadoDevolucion, Integer> {
    Optional<EstadoDevolucion> findByNombreEstado(String nombreEstado);
}