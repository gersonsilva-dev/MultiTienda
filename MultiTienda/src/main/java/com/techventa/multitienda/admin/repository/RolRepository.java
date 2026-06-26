package com.techventa.multitienda.admin.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.techventa.multitienda.admin.model.Rol;

import java.util.List;
import java.util.Optional;

@Repository
public interface RolRepository extends JpaRepository<Rol, Integer> {

    // Buscar rol por nombre
    Optional<Rol> findByNombreRol(String nombreRol);

    // Buscar roles activos
    List<Rol> findByActivoTrue();

    // Buscar por nivel de acceso
    List<Rol> findByNivelAcceso(Integer nivelAcceso);

    // Verificar si existe un rol por nombre
    boolean existsByNombreRol(String nombreRol);
}