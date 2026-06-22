package com.techventa.multitienda.admin.controller;

import com.techventa.multitienda.admin.model.Rol;
import com.techventa.multitienda.admin.service.RolService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/roles")
public class RolController {

    @Autowired
    private RolService rolService;

    // Listar todos los roles
    @GetMapping
    public ResponseEntity<List<Rol>> listarTodos() {
        List<Rol> roles = rolService.listarTodos();
        return ResponseEntity.ok(roles);
    }

    // Listar solo roles activos
    @GetMapping("/activos")
    public ResponseEntity<List<Rol>> listarActivos() {
        List<Rol> roles = rolService.listarActivos();
        return ResponseEntity.ok(roles);
    }

    // Buscar rol por ID
    @GetMapping("/{id}")
    public ResponseEntity<Rol> buscarPorId(@PathVariable Integer id) {
        Optional<Rol> rol = rolService.buscarPorId(id);
        return rol.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Buscar rol por nombre
    @GetMapping("/nombre/{nombre}")
    public ResponseEntity<Rol> buscarPorNombre(@PathVariable String nombre) {
        Optional<Rol> rol = rolService.buscarPorNombre(nombre);
        return rol.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Buscar por nivel de acceso
    @GetMapping("/nivel/{nivel}")
    public ResponseEntity<List<Rol>> buscarPorNivel(@PathVariable Integer nivel) {
        List<Rol> roles = rolService.buscarPorNivelAcceso(nivel);
        return ResponseEntity.ok(roles);
    }

    // Crear nuevo rol
    @PostMapping
    public ResponseEntity<Object> crear(@RequestBody Rol rol) {
        try {
            // Verificar si el nombre del rol ya existe
            if (rolService.existeNombreRol(rol.getNombreRol())) {
                Map<String, String> error = new HashMap<>();
                error.put("error", "Ya existe un rol con el nombre: " + rol.getNombreRol());
                return ResponseEntity.status(HttpStatus.CONFLICT).body(error);
            }
            Rol nuevoRol = rolService.crear(rol);
            return ResponseEntity.status(HttpStatus.CREATED).body(nuevoRol);
        } catch (Exception e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", "Error al crear el rol: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
        }
    }

    // Actualizar rol
    @PutMapping("/{id}")
    public ResponseEntity<Object> actualizar(@PathVariable Integer id, @RequestBody Rol rol) {
        try {
            Optional<Rol> existente = rolService.buscarPorId(id);
            if (existente.isEmpty()) {
                return ResponseEntity.notFound().build();
            }
            rol.setIdRol(id);
            Rol actualizado = rolService.actualizar(rol);
            return ResponseEntity.ok(actualizado);
        } catch (Exception e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", "Error al actualizar el rol: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
        }
    }

    // Eliminar rol (borrado lógico)
    @DeleteMapping("/{id}")
    public ResponseEntity<Object> eliminar(@PathVariable Integer id) {
        try {
            Optional<Rol> existente = rolService.buscarPorId(id);
            if (existente.isEmpty()) {
                return ResponseEntity.notFound().build();
            }
            rolService.eliminarLogico(id);
            Map<String, String> response = new HashMap<>();
            response.put("mensaje", "Rol eliminado correctamente");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", "Error al eliminar el rol: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
        }
    }
}