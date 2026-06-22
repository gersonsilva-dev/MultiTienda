package com.techventa.multitienda.admin.controller;

import com.techventa.multitienda.admin.model.Usuario;
import com.techventa.multitienda.admin.service.UsuarioService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/usuarios")  // ← CAMBIADO
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @GetMapping
    public ResponseEntity<List<Usuario>> listarTodos() {
        List<Usuario> usuarios = usuarioService.listarTodos();
        return ResponseEntity.ok(usuarios);
    }

    @GetMapping("/activos")
    public ResponseEntity<List<Usuario>> listarActivos() {
        List<Usuario> usuarios = usuarioService.listarActivos();
        return ResponseEntity.ok(usuarios);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Usuario> buscarPorId(@PathVariable Integer id) {
        Optional<Usuario> usuario = usuarioService.buscarPorId(id);
        return usuario.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/correo/{correo}")
    public ResponseEntity<Usuario> buscarPorCorreo(@PathVariable String correo) {
        Optional<Usuario> usuario = usuarioService.buscarPorCorreo(correo);
        return usuario.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/documento/{documento}")
    public ResponseEntity<Usuario> buscarPorDocumento(@PathVariable String documento) {
        Optional<Usuario> usuario = usuarioService.buscarPorDocumento(documento);
        return usuario.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/rol/{idRol}")
    public ResponseEntity<List<Usuario>> buscarPorRol(@PathVariable Integer idRol) {
        List<Usuario> usuarios = usuarioService.buscarPorRol(idRol);
        return ResponseEntity.ok(usuarios);
    }

    @GetMapping("/tienda/{idTienda}")
    public ResponseEntity<List<Usuario>> buscarPorTienda(@PathVariable Integer idTienda) {
        List<Usuario> usuarios = usuarioService.buscarPorTienda(idTienda);
        return ResponseEntity.ok(usuarios);
    }

    @PostMapping
    public ResponseEntity<Object> crear(@RequestBody Usuario usuario) {
        try {
            if (usuarioService.existeCorreo(usuario.getCorreoElectronico())) {
                Map<String, String> error = new HashMap<>();
                error.put("error", "Ya existe un usuario con el correo: " + usuario.getCorreoElectronico());
                return ResponseEntity.status(HttpStatus.CONFLICT).body(error);
            }
            if (usuario.getNumeroDocumento() != null && usuarioService.existeDocumento(usuario.getNumeroDocumento())) {
                Map<String, String> error = new HashMap<>();
                error.put("error", "Ya existe un usuario con el documento: " + usuario.getNumeroDocumento());
                return ResponseEntity.status(HttpStatus.CONFLICT).body(error);
            }
            Usuario nuevoUsuario = usuarioService.crear(usuario);
            return ResponseEntity.status(HttpStatus.CREATED).body(nuevoUsuario);
        } catch (Exception e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", "Error al crear el usuario: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> actualizar(@PathVariable Integer id, @RequestBody Usuario usuario) {
        try {
            Optional<Usuario> existente = usuarioService.buscarPorId(id);
            if (existente.isEmpty()) {
                return ResponseEntity.notFound().build();
            }
            usuario.setIdUsuario(id);
            Usuario actualizado = usuarioService.actualizar(usuario);
            return ResponseEntity.ok(actualizado);
        } catch (Exception e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", "Error al actualizar el usuario: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
        }
    }

    @PutMapping("/{id}/estado")
    public ResponseEntity<Object> cambiarEstado(@PathVariable Integer id, @RequestParam Boolean activo) {
        try {
            Optional<Usuario> existente = usuarioService.buscarPorId(id);
            if (existente.isEmpty()) {
                return ResponseEntity.notFound().build();
            }
            Usuario usuario = existente.get();
            usuario.setActivo(activo);
            usuarioService.actualizar(usuario);
            Map<String, String> response = new HashMap<>();
            response.put("mensaje", "Estado actualizado correctamente");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", "Error al actualizar estado: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
        }
    }
    
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Object> eliminar(@PathVariable Integer id) {
        try {
            Optional<Usuario> existente = usuarioService.buscarPorId(id);
            if (existente.isEmpty()) {
                return ResponseEntity.notFound().build();
            }
            usuarioService.eliminarFisico(id);  // ← BORRADO FÍSICO
            Map<String, String> response = new HashMap<>();
            response.put("mensaje", "Usuario eliminado correctamente");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", "Error al eliminar el usuario: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
        }
    }
}