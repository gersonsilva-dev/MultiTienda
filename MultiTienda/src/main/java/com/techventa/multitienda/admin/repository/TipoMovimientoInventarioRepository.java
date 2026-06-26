package com.techventa.multitienda.admin.repository;

import com.techventa.multitienda.admin.model.TipoMovimientoInventario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface TipoMovimientoInventarioRepository extends JpaRepository<TipoMovimientoInventario, Integer> {
    Optional<TipoMovimientoInventario> findByNombreMovimiento(String nombreMovimiento);
}