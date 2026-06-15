package com.techventa.multitienda.admin.controller;

import com.techventa.multitienda.admin.model.Proveedor;
import com.techventa.multitienda.admin.service.ProveedorService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/proveedores")
public class ProveedorController {

    @Autowired
    private ProveedorService proveedorService;

    // Listar todos los proveedores
    @GetMapping
    public ResponseEntity<List<Proveedor>> listarTodos() {
        List<Proveedor> proveedores = proveedorService.listarTodos();
        return ResponseEntity.ok(proveedores);
    }

    // Listar solo proveedores activos
    @GetMapping("/activos")
    public ResponseEntity<List<Proveedor>> listarActivos() {
        List<Proveedor> proveedores = proveedorService.listarActivos();
        return ResponseEntity.ok(proveedores);
    }

    // Buscar proveedor por ID
    @GetMapping("/{id}")
    public ResponseEntity<Proveedor> buscarPorId(@PathVariable Integer id) {
        Optional<Proveedor> proveedor = proveedorService.buscarPorId(id);
        return proveedor.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Buscar proveedor por RUC
    @GetMapping("/ruc/{ruc}")
    public ResponseEntity<Proveedor> buscarPorRuc(@PathVariable String ruc) {
        Optional<Proveedor> proveedor = proveedorService.buscarPorRuc(ruc);
        return proveedor.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Buscar por razón social
    @GetMapping("/buscar")
    public ResponseEntity<List<Proveedor>> buscarPorRazonSocial(@RequestParam String razonSocial) {
        List<Proveedor> proveedores = proveedorService.buscarPorRazonSocial(razonSocial);
        return ResponseEntity.ok(proveedores);
    }

    // Buscar por categoría
    @GetMapping("/categoria/{idCategoria}")
    public ResponseEntity<List<Proveedor>> buscarPorCategoria(@PathVariable Integer idCategoria) {
        List<Proveedor> proveedores = proveedorService.buscarPorCategoria(idCategoria);
        return ResponseEntity.ok(proveedores);
    }

    // Buscar por estado
    @GetMapping("/estado/{idEstado}")
    public ResponseEntity<List<Proveedor>> buscarPorEstado(@PathVariable Integer idEstado) {
        List<Proveedor> proveedores = proveedorService.buscarPorEstado(idEstado);
        return ResponseEntity.ok(proveedores);
    }

    // Crear nuevo proveedor
    @PostMapping
    public ResponseEntity<Object> crear(@RequestBody Proveedor proveedor) {
        try {
            // Verificar si el RUC ya existe
            if (proveedorService.existeRuc(proveedor.getRuc())) {
                Map<String, String> error = new HashMap<>();
                error.put("error", "Ya existe un proveedor con el RUC: " + proveedor.getRuc());
                return ResponseEntity.status(HttpStatus.CONFLICT).body(error);
            }
            Proveedor nuevoProveedor = proveedorService.crear(proveedor);
            return ResponseEntity.status(HttpStatus.CREATED).body(nuevoProveedor);
        } catch (Exception e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", "Error al crear el proveedor: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
        }
    }

    // Actualizar proveedor
    @PutMapping("/{id}")
    public ResponseEntity<Object> actualizar(@PathVariable Integer id, @RequestBody Proveedor proveedor) {
        try {
            Optional<Proveedor> existente = proveedorService.buscarPorId(id);
            if (existente.isEmpty()) {
                return ResponseEntity.notFound().build();
            }
            proveedor.setIdProveedor(id);
            Proveedor actualizado = proveedorService.actualizar(proveedor);
            return ResponseEntity.ok(actualizado);
        } catch (Exception e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", "Error al actualizar el proveedor: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
        }
    }

    // Eliminar proveedor (borrado lógico)
    @DeleteMapping("/{id}")
    public ResponseEntity<Object> eliminar(@PathVariable Integer id) {
        try {
            Optional<Proveedor> existente = proveedorService.buscarPorId(id);
            if (existente.isEmpty()) {
                return ResponseEntity.notFound().build();
            }
            proveedorService.eliminarLogico(id);
            Map<String, String> response = new HashMap<>();
            response.put("mensaje", "Proveedor eliminado correctamente");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", "Error al eliminar el proveedor: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
        }
    }
}