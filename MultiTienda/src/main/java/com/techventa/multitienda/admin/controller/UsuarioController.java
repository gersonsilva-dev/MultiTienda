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
@RequestMapping("/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    // Listar todos los usuarios
    @GetMapping
    public ResponseEntity<List<Usuario>> listarTodos() {
        List<Usuario> usuarios = usuarioService.listarTodos();
        return ResponseEntity.ok(usuarios);
    }

    // Listar solo usuarios activos
    @GetMapping("/activos")
    public ResponseEntity<List<Usuario>> listarActivos() {
        List<Usuario> usuarios = usuarioService.listarActivos();
        return ResponseEntity.ok(usuarios);
    }

    // Buscar usuario por ID
    @GetMapping("/{id}")
    public ResponseEntity<Usuario> buscarPorId(@PathVariable Integer id) {
        Optional<Usuario> usuario = usuarioService.buscarPorId(id);
        return usuario.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Buscar usuario por correo
    @GetMapping("/correo/{correo}")
    public ResponseEntity<Usuario> buscarPorCorreo(@PathVariable String correo) {
        Optional<Usuario> usuario = usuarioService.buscarPorCorreo(correo);
        return usuario.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Buscar usuario por documento
    @GetMapping("/documento/{documento}")
    public ResponseEntity<Usuario> buscarPorDocumento(@PathVariable String documento) {
        Optional<Usuario> usuario = usuarioService.buscarPorDocumento(documento);
        return usuario.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Buscar usuarios por rol
    @GetMapping("/rol/{idRol}")
    public ResponseEntity<List<Usuario>> buscarPorRol(@PathVariable Integer idRol) {
        List<Usuario> usuarios = usuarioService.buscarPorRol(idRol);
        return ResponseEntity.ok(usuarios);
    }

    // Buscar usuarios por tienda
    @GetMapping("/tienda/{idTienda}")
    public ResponseEntity<List<Usuario>> buscarPorTienda(@PathVariable Integer idTienda) {
        List<Usuario> usuarios = usuarioService.buscarPorTienda(idTienda);
        return ResponseEntity.ok(usuarios);
    }

    // Crear nuevo usuario
    @PostMapping
    public ResponseEntity<Object> crear(@RequestBody Usuario usuario) {
        try {
            // Verificar si el correo ya existe
            if (usuarioService.existeCorreo(usuario.getCorreoElectronico())) {
                Map<String, String> error = new HashMap<>();
                error.put("error", "Ya existe un usuario con el correo: " + usuario.getCorreoElectronico());
                return ResponseEntity.status(HttpStatus.CONFLICT).body(error);
            }
            // Verificar si el documento ya existe
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

    // Actualizar usuario
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

    // Eliminar usuario (borrado lógico)
    @DeleteMapping("/{id}")
    public ResponseEntity<Object> eliminar(@PathVariable Integer id) {
        try {
            Optional<Usuario> existente = usuarioService.buscarPorId(id);
            if (existente.isEmpty()) {
                return ResponseEntity.notFound().build();
            }
            usuarioService.eliminarLogico(id);
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