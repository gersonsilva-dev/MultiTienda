package com.techventa.multitienda.admin.repository;

import com.techventa.multitienda.admin.model.EstadoTransferencia;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface EstadoTransferenciaRepository extends JpaRepository<EstadoTransferencia, Integer> {
    Optional<EstadoTransferencia> findByNombreEstado(String nombreEstado);
}