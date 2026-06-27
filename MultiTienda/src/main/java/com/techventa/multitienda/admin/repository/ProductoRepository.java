package com.techventa.multitienda.admin.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.techventa.multitienda.admin.model.Producto;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductoRepository extends JpaRepository<Producto, Integer> {

    // Buscar por código de barras
    Optional<Producto> findByCodigoBarras(String codigoBarras);

    // Buscar por nombre (contiene, ignorando mayúsculas)
    List<Producto> findByNombreProductoContainingIgnoreCase(String nombre);

    // Buscar por categoría
    List<Producto> findByCategoria_IdCategoria(Integer idCategoria);

    // Buscar productos activos
    List<Producto> findByActivoTrue();

    // Buscar por marca
    List<Producto> findByMarcaContainingIgnoreCase(String marca);

    // Buscar por estado del producto
    List<Producto> findByIdEstadoProducto(Integer idEstadoProducto);

    // Verificar si existe código de barras
    boolean existsByCodigoBarras(String codigoBarras);
 // Dentro de ProductoRepository.java
    @Query("SELECT p FROM Producto p WHERE p.activo = true")
    List<Producto> findAllByActivoTrue();
}