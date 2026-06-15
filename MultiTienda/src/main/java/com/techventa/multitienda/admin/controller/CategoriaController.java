package com.techventa.multitienda.admin.controller;

import com.techventa.multitienda.admin.model.CategoriaProducto;
import com.techventa.multitienda.admin.service.CategoriaService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/categorias")
public class CategoriaController {

    @Autowired
    private CategoriaService categoriaService;

    // Listar todas las categorías
    @GetMapping
    public ResponseEntity<List<CategoriaProducto>> listarTodas() {
        List<CategoriaProducto> categorias = categoriaService.listarTodas();
        return ResponseEntity.ok(categorias);
    }

    // Listar solo categorías activas
    @GetMapping("/activas")
    public ResponseEntity<List<CategoriaProducto>> listarActivas() {
        List<CategoriaProducto> categorias = categoriaService.listarActivas();
        return ResponseEntity.ok(categorias);
    }

    // Listar categorías raíz (sin padre)
    @GetMapping("/raices")
    public ResponseEntity<List<CategoriaProducto>> listarRaices() {
        List<CategoriaProducto> categorias = categoriaService.listarRaices();
        return ResponseEntity.ok(categorias);
    }

    // Buscar por ID
    @GetMapping("/{id}")
    public ResponseEntity<CategoriaProducto> buscarPorId(@PathVariable Integer id) {
        Optional<CategoriaProducto> categoria = categoriaService.buscarPorId(id);
        return categoria.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Buscar por nombre
    @GetMapping("/nombre/{nombre}")
    public ResponseEntity<CategoriaProducto> buscarPorNombre(@PathVariable String nombre) {
        Optional<CategoriaProducto> categoria = categoriaService.buscarPorNombre(nombre);
        return categoria.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Buscar por nivel
    @GetMapping("/nivel/{nivel}")
    public ResponseEntity<List<CategoriaProducto>> buscarPorNivel(@PathVariable Integer nivel) {
        List<CategoriaProducto> categorias = categoriaService.buscarPorNivel(nivel);
        return ResponseEntity.ok(categorias);
    }

    // Buscar subcategorías
    @GetMapping("/subcategorias/{idPadre}")
    public ResponseEntity<List<CategoriaProducto>> buscarSubcategorias(@PathVariable Integer idPadre) {
        List<CategoriaProducto> subcategorias = categoriaService.buscarSubcategorias(idPadre);
        return ResponseEntity.ok(subcategorias);
    }

    // Crear nueva categoría
    @PostMapping
    public ResponseEntity<Object> crear(@RequestBody CategoriaProducto categoria) {
        try {
            if (categoriaService.existeNombre(categoria.getNombreCategoria())) {
                Map<String, String> error = new HashMap<>();
                error.put("error", "Ya existe una categoría con el nombre: " + categoria.getNombreCategoria());
                return ResponseEntity.status(HttpStatus.CONFLICT).body(error);
            }
            CategoriaProducto nuevaCategoria = categoriaService.crear(categoria);
            return ResponseEntity.status(HttpStatus.CREATED).body(nuevaCategoria);
        } catch (Exception e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", "Error al crear la categoría: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
        }
    }

    // Actualizar categoría
    @PutMapping("/{id}")
    public ResponseEntity<Object> actualizar(@PathVariable Integer id, @RequestBody CategoriaProducto categoria) {
        try {
            Optional<CategoriaProducto> existente = categoriaService.buscarPorId(id);
            if (existente.isEmpty()) {
                return ResponseEntity.notFound().build();
            }
            categoria.setIdCategoria(id);
            CategoriaProducto actualizada = categoriaService.actualizar(categoria);
            return ResponseEntity.ok(actualizada);
        } catch (Exception e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", "Error al actualizar la categoría: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
        }
    }

    // Eliminar categoría (borrado lógico)
    @DeleteMapping("/{id}")
    public ResponseEntity<Object> eliminar(@PathVariable Integer id) {
        try {
            Optional<CategoriaProducto> existente = categoriaService.buscarPorId(id);
            if (existente.isEmpty()) {
                return ResponseEntity.notFound().build();
            }
            categoriaService.eliminarLogico(id);
            Map<String, String> response = new HashMap<>();
            response.put("mensaje", "Categoría eliminada correctamente");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", "Error al eliminar la categoría: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
        }
    }
}