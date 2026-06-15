package com.techventa.multitienda.admin.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.techventa.multitienda.admin.model.UnidadMedida;

import java.util.List;
import java.util.Optional;

@Repository
public interface UnidadMedidaRepository extends JpaRepository<UnidadMedida, Integer> {

    // Buscar por nombre
    Optional<UnidadMedida> findByNombreUnidad(String nombreUnidad);

    // Buscar por abreviatura
    Optional<UnidadMedida> findByAbreviatura(String abreviatura);

    // Listar unidades activas
    List<UnidadMedida> findByActivoTrue();

    // Verificar si existe por nombre
    boolean existsByNombreUnidad(String nombreUnidad);
}