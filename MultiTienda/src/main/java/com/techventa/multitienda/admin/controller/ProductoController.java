package com.techventa.multitienda.admin.controller;

import com.techventa.multitienda.admin.model.Producto;
import com.techventa.multitienda.admin.service.ProductoService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/productos")
public class ProductoController {

    @Autowired
    private ProductoService productoService;

    // Listar todos los productos
    @GetMapping
    public ResponseEntity<List<Producto>> listarTodos() {
        List<Producto> productos = productoService.listarTodos();
        return ResponseEntity.ok(productos);
    }

    // Listar solo productos activos
    @GetMapping("/activos")
    public ResponseEntity<List<Producto>> listarActivos() {
        List<Producto> productos = productoService.listarActivos();
        return ResponseEntity.ok(productos);
    }

    // Buscar producto por ID
    @GetMapping("/{id}")
    public ResponseEntity<Producto> buscarPorId(@PathVariable Integer id) {
        Optional<Producto> producto = productoService.buscarPorId(id);
        return producto.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Buscar producto por código de barras
    @GetMapping("/codigo/{codigo}")
    public ResponseEntity<Producto> buscarPorCodigoBarras(@PathVariable String codigo) {
        Optional<Producto> producto = productoService.buscarPorCodigoBarras(codigo);
        return producto.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Buscar por nombre
    @GetMapping("/buscar")
    public ResponseEntity<List<Producto>> buscarPorNombre(@RequestParam String nombre) {
        List<Producto> productos = productoService.buscarPorNombre(nombre);
        return ResponseEntity.ok(productos);
    }

    // Buscar por categoría
    @GetMapping("/categoria/{idCategoria}")
    public ResponseEntity<List<Producto>> buscarPorCategoria(@PathVariable Integer idCategoria) {
        List<Producto> productos = productoService.buscarPorCategoria(idCategoria);
        return ResponseEntity.ok(productos);
    }

    // Buscar por marca
    @GetMapping("/marca/{marca}")
    public ResponseEntity<List<Producto>> buscarPorMarca(@PathVariable String marca) {
        List<Producto> productos = productoService.buscarPorMarca(marca);
        return ResponseEntity.ok(productos);
    }

    // Buscar por estado
    @GetMapping("/estado/{idEstado}")
    public ResponseEntity<List<Producto>> buscarPorEstado(@PathVariable Integer idEstado) {
        List<Producto> productos = productoService.buscarPorEstado(idEstado);
        return ResponseEntity.ok(productos);
    }

    // Crear nuevo producto
    @PostMapping
    public ResponseEntity<Object> crear(@RequestBody Producto producto) {
        try {
            // Verificar si el código de barras ya existe
            if (producto.getCodigoBarras() != null && productoService.existeCodigoBarras(producto.getCodigoBarras())) {
                Map<String, String> error = new HashMap<>();
                error.put("error", "Ya existe un producto con el código de barras: " + producto.getCodigoBarras());
                return ResponseEntity.status(HttpStatus.CONFLICT).body(error);
            }
            Producto nuevoProducto = productoService.crear(producto);
            return ResponseEntity.status(HttpStatus.CREATED).body(nuevoProducto);
        } catch (Exception e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", "Error al crear el producto: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
        }
    }

    // Actualizar producto
    @PutMapping("/{id}")
    public ResponseEntity<Object> actualizar(@PathVariable Integer id, @RequestBody Producto producto) {
        try {
            Optional<Producto> existente = productoService.buscarPorId(id);
            if (existente.isEmpty()) {
                return ResponseEntity.notFound().build();
            }
            producto.setIdProducto(id);
            Producto actualizado = productoService.actualizar(producto);
            return ResponseEntity.ok(actualizado);
        } catch (Exception e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", "Error al actualizar el producto: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
        }
    }

    // Eliminar producto (borrado lógico)
    @DeleteMapping("/{id}")
    public ResponseEntity<Object> eliminar(@PathVariable Integer id) {
        try {
            Optional<Producto> existente = productoService.buscarPorId(id);
            if (existente.isEmpty()) {
                return ResponseEntity.notFound().build();
            }
            productoService.eliminarFisico(id);  // ← CAMBIADO
            Map<String, String> response = new HashMap<>();
            response.put("mensaje", "Producto eliminado correctamente");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", "Error al eliminar el producto: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
        }
    }
}