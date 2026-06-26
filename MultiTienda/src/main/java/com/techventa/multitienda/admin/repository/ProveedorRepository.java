package com.techventa.multitienda.admin.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.techventa.multitienda.admin.model.Proveedor;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProveedorRepository extends JpaRepository<Proveedor, Integer> {

    // Buscar por RUC (único)
    Optional<Proveedor> findByRuc(String ruc);

    // Buscar por razón social (like para búsquedas)
    List<Proveedor> findByRazonSocialContainingIgnoreCase(String razonSocial);

    // Buscar proveedores activos
    List<Proveedor> findByActivoTrue();

    // Buscar por categoría
    List<Proveedor> findByCategoriaProveedor_IdCategoriaProveedor(Integer idCategoria);

    // Buscar por estado
    List<Proveedor> findByEstadoProveedor_IdEstadoProveedor(Integer idEstado);

    // Verificar si existe un RUC
    boolean existsByRuc(String ruc);

    // Buscar por usuario asociado
    List<Proveedor> findByUsuarioAsociado_IdUsuario(Integer idUsuario);
}