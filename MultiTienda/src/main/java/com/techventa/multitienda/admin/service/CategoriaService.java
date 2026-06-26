package com.techventa.multitienda.admin.service;

import com.techventa.multitienda.admin.model.CategoriaProducto;
import com.techventa.multitienda.admin.repository.CategoriaRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class CategoriaService {

    @Autowired
    private CategoriaRepository categoriaRepository;

    // Listar todas las categorías
    public List<CategoriaProducto> listarTodas() {
        return categoriaRepository.findAll();
    }

    // Listar solo categorías activas
    public List<CategoriaProducto> listarActivas() {
        return categoriaRepository.findByActivoTrue();
    }

    // Listar categorías raíz (sin padre)
    public List<CategoriaProducto> listarRaices() {
        return categoriaRepository.findByCategoriaPadreIsNull();
    }

    // Buscar por ID
    public Optional<CategoriaProducto> buscarPorId(Integer id) {
        return categoriaRepository.findById(id);
    }

    // Buscar por nombre
    public Optional<CategoriaProducto> buscarPorNombre(String nombre) {
        return categoriaRepository.findByNombreCategoria(nombre);
    }

    // Buscar por nivel
    public List<CategoriaProducto> buscarPorNivel(Integer nivel) {
        return categoriaRepository.findByNivel(nivel);
    }

    // Buscar subcategorías de una categoría padre
    public List<CategoriaProducto> buscarSubcategorias(Integer idCategoriaPadre) {
        return categoriaRepository.findByCategoriaPadre_IdCategoria(idCategoriaPadre);
    }

    // Crear nueva categoría
    public CategoriaProducto crear(CategoriaProducto categoria) {
        // Si tiene padre, calcular el nivel
        if (categoria.getCategoriaPadre() != null && categoria.getCategoriaPadre().getIdCategoria() != null) {
            Optional<CategoriaProducto> padreOpt = categoriaRepository.findById(categoria.getCategoriaPadre().getIdCategoria());
            if (padreOpt.isPresent()) {
                categoria.setNivel(padreOpt.get().getNivel() + 1);
            }
        } else {
            categoria.setNivel(1);
            categoria.setCategoriaPadre(null);
        }
        categoria.setActivo(true);
        return categoriaRepository.save(categoria);
    }

    // Actualizar categoría
    public CategoriaProducto actualizar(CategoriaProducto categoria) {
        return categoriaRepository.save(categoria);
    }

    public void eliminarLogico(Integer id) {
        Optional<CategoriaProducto> categoriaOpt = categoriaRepository.findById(id);
        if (categoriaOpt.isPresent()) {
            CategoriaProducto categoria = categoriaOpt.get();
            categoria.setActivo(false);
            categoriaRepository.save(categoria);
        }
    }

    // AHORA (borrado físico)
    public void eliminarFisico(Integer id) {
        categoriaRepository.deleteById(id);
    }

    // Verificar si existe nombre de categoría
    public boolean existeNombre(String nombre) {
        return categoriaRepository.existsByNombreCategoria(nombre);
    }
}