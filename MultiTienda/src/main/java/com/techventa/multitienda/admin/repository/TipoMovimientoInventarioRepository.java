package com.techventa.multitienda.admin.repository;

import com.techventa.multitienda.admin.model.TipoMovimientoInventario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TipoMovimientoInventarioRepository extends JpaRepository<TipoMovimientoInventario, Integer> {
    Optional<TipoMovimientoInventario> findByNombreMovimiento(String nombreMovimiento);
    List<TipoMovimientoInventario> findAllByActivoTrueOrderByNombreMovimientoAsc();
    
 // ✅ NUEVO: Más robusto, siempre devuelve el primero que encuentre
    Optional<TipoMovimientoInventario> findFirstByNombreMovimientoIgnoreCase(String nombreMovimiento);

    // ✅ NUEVO: Para búsquedas más flexibles
    Optional<TipoMovimientoInventario> findFirstByNombreMovimientoContainingIgnoreCase(String nombre);
}