package com.techventa.multitienda.admin.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.techventa.multitienda.admin.model.CategoriaProducto;

import java.util.List;
import java.util.Optional;

@Repository
public interface CategoriaRepository extends JpaRepository<CategoriaProducto, Integer> {

    // Buscar por nombre
    Optional<CategoriaProducto> findByNombreCategoria(String nombreCategoria);

    // Buscar categorías activas
    List<CategoriaProducto> findByActivoTrue();

    // Buscar por nivel
    List<CategoriaProducto> findByNivel(Integer nivel);

    // Buscar categorías hijas de una categoría padre
    List<CategoriaProducto> findByCategoriaPadre_IdCategoria(Integer idCategoriaPadre);

    // Buscar categorías que NO tienen padre (nivel 1)
    List<CategoriaProducto> findByCategoriaPadreIsNull();

    // Verificar si existe nombre de categoría
    boolean existsByNombreCategoria(String nombreCategoria);
}