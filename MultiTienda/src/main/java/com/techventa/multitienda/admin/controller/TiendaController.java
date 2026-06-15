package com.techventa.multitienda.admin.controller;

import com.techventa.multitienda.admin.model.Tienda;
import com.techventa.multitienda.admin.service.TiendaService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/tiendas")
public class TiendaController {

    @Autowired
    private TiendaService tiendaService;

    // Listar todas las tiendas
    @GetMapping
    public ResponseEntity<List<Tienda>> listarTodas() {
        List<Tienda> tiendas = tiendaService.listarTodas();
        return ResponseEntity.ok(tiendas);
    }

    // Listar solo tiendas activas
    @GetMapping("/activas")
    public ResponseEntity<List<Tienda>> listarActivas() {
        List<Tienda> tiendas = tiendaService.listarActivas();
        return ResponseEntity.ok(tiendas);
    }

    // Buscar tienda por ID
    @GetMapping("/{id}")
    public ResponseEntity<Tienda> buscarPorId(@PathVariable Integer id) {
        Optional<Tienda> tienda = tiendaService.buscarPorId(id);
        return tienda.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Buscar tienda por código
    @GetMapping("/codigo/{codigo}")
    public ResponseEntity<Tienda> buscarPorCodigo(@PathVariable String codigo) {
        Optional<Tienda> tienda = tiendaService.buscarPorCodigo(codigo);
        return tienda.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Buscar por nombre
    @GetMapping("/buscar")
    public ResponseEntity<List<Tienda>> buscarPorNombre(@RequestParam String nombre) {
        List<Tienda> tiendas = tiendaService.buscarPorNombre(nombre);
        return ResponseEntity.ok(tiendas);
    }

    // Buscar por estado
    @GetMapping("/estado/{idEstado}")
    public ResponseEntity<List<Tienda>> buscarPorEstado(@PathVariable Integer idEstado) {
        List<Tienda> tiendas = tiendaService.buscarPorEstado(idEstado);
        return ResponseEntity.ok(tiendas);
    }

    // Crear nueva tienda
    @PostMapping
    public ResponseEntity<Object> crear(@RequestBody Tienda tienda) {
        try {
            // Verificar si el código ya existe
            if (tiendaService.existeCodigo(tienda.getCodigoTienda())) {
                Map<String, String> error = new HashMap<>();
                error.put("error", "Ya existe una tienda con el código: " + tienda.getCodigoTienda());
                return ResponseEntity.status(HttpStatus.CONFLICT).body(error);
            }
            Tienda nuevaTienda = tiendaService.crear(tienda);
            return ResponseEntity.status(HttpStatus.CREATED).body(nuevaTienda);
        } catch (Exception e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", "Error al crear la tienda: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
        }
    }

    // Actualizar tienda
    @PutMapping("/{id}")
    public ResponseEntity<Object> actualizar(@PathVariable Integer id, @RequestBody Tienda tienda) {
        try {
            Optional<Tienda> existente = tiendaService.buscarPorId(id);
            if (existente.isEmpty()) {
                return ResponseEntity.notFound().build();
            }
            tienda.setIdTienda(id);
            Tienda actualizada = tiendaService.actualizar(tienda);
            return ResponseEntity.ok(actualizada);
        } catch (Exception e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", "Error al actualizar la tienda: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
        }
    }

    // Eliminar tienda (borrado lógico)
    @DeleteMapping("/{id}")
    public ResponseEntity<Object> eliminar(@PathVariable Integer id) {
        try {
            Optional<Tienda> existente = tiendaService.buscarPorId(id);
            if (existente.isEmpty()) {
                return ResponseEntity.notFound().build();
            }
            tiendaService.eliminarLogico(id);
            Map<String, String> response = new HashMap<>();
            response.put("mensaje", "Tienda eliminada correctamente");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", "Error al eliminar la tienda: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
        }
    }
}