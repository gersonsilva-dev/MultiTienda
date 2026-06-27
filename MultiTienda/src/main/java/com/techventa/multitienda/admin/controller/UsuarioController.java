package com.techventa.multitienda.admin.controller;

import com.techventa.multitienda.admin.dto.UsuarioRequest;
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
@RequestMapping("/api/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @GetMapping
    public ResponseEntity<List<Usuario>> listarTodos() {
        return ResponseEntity.ok(usuarioService.listarTodos());
    }

    @GetMapping("/activos")
    public ResponseEntity<List<Usuario>> listarActivos() {
        return ResponseEntity.ok(usuarioService.listarActivos());
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
        return ResponseEntity.ok(usuarioService.buscarPorRol(idRol));
    }

    @GetMapping("/tienda/{idTienda}")
    public ResponseEntity<List<Usuario>> buscarPorTienda(@PathVariable Integer idTienda) {
        return ResponseEntity.ok(usuarioService.buscarPorTienda(idTienda));
    }

    // ============================================================
    // CREAR USUARIO (CON CONTRASEÑA ENCRIPTADA Y CAJA)
    // ============================================================
    @PostMapping
    public ResponseEntity<Object> crear(@RequestBody UsuarioRequest request) {
        try {
            if (usuarioService.existeCorreo(request.getCorreoElectronico())) {
                Map<String, String> error = new HashMap<>();
                error.put("error", "Ya existe un usuario con el correo: " + request.getCorreoElectronico());
                return ResponseEntity.status(HttpStatus.CONFLICT).body(error);
            }
            if (request.getNumeroDocumento() != null && usuarioService.existeDocumento(request.getNumeroDocumento())) {
                Map<String, String> error = new HashMap<>();
                error.put("error", "Ya existe un usuario con el documento: " + request.getNumeroDocumento());
                return ResponseEntity.status(HttpStatus.CONFLICT).body(error);
            }

            Usuario usuario = new Usuario();
            usuario.setNombres(request.getNombres());
            usuario.setApellidos(request.getApellidos());
            usuario.setCorreoElectronico(request.getCorreoElectronico());
            usuario.setNumeroDocumento(request.getNumeroDocumento());
            usuario.setTelefono(request.getTelefono());
            usuario.setDireccion(request.getDireccion());
            usuario.setRol(request.getRol());
            usuario.setTienda(request.getTienda());
            
            // 🔥 ASIGNAR CAJA (si viene)
            if (request.getCaja() != null) {
                usuario.setCaja(request.getCaja());
            }

            Usuario nuevoUsuario = usuarioService.crear(usuario, request.getContrasena());
            return ResponseEntity.status(HttpStatus.CREATED).body(nuevoUsuario);

        } catch (Exception e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", "Error al crear el usuario: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
        }
    }

    // ============================================================
    // ACTUALIZAR USUARIO (CON CONTRASEÑA ENCRIPTADA Y CAJA)
    // ============================================================
    @PutMapping("/{id}")
    public ResponseEntity<Object> actualizar(@PathVariable Integer id, @RequestBody UsuarioRequest request) {
        try {
            Optional<Usuario> existente = usuarioService.buscarPorId(id);
            if (existente.isEmpty()) {
                return ResponseEntity.notFound().build();
            }

            Usuario usuario = existente.get();
            usuario.setNombres(request.getNombres());
            usuario.setApellidos(request.getApellidos());
            usuario.setCorreoElectronico(request.getCorreoElectronico());
            usuario.setNumeroDocumento(request.getNumeroDocumento());
            usuario.setTelefono(request.getTelefono());
            usuario.setDireccion(request.getDireccion());
            usuario.setRol(request.getRol());
            usuario.setTienda(request.getTienda());
            
            // 🔥 ACTUALIZAR CAJA
            if (request.getCaja() != null) {
                usuario.setCaja(request.getCaja());
            } else {
                usuario.setCaja(null);
            }

            Usuario actualizado = usuarioService.actualizar(usuario, request.getContrasena());
            return ResponseEntity.ok(actualizado);

        } catch (Exception e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", "Error al actualizar el usuario: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
        }
    }

    // ============================================================
    // CAMBIAR ESTADO DEL USUARIO
    // ============================================================
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

    // ============================================================
    // ELIMINAR USUARIO
    // ============================================================
    @DeleteMapping("/{id}")
    public ResponseEntity<Object> eliminar(@PathVariable Integer id) {
        try {
            Optional<Usuario> existente = usuarioService.buscarPorId(id);
            if (existente.isEmpty()) {
                return ResponseEntity.notFound().build();
            }
            usuarioService.eliminarFisico(id);
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