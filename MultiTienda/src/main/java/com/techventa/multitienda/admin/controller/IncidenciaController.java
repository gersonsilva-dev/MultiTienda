package com.techventa.multitienda.admin.controller;

import com.techventa.multitienda.admin.model.Incidencia;
import com.techventa.multitienda.admin.service.IncidenciaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/incidencias")
public class IncidenciaController {

    @Autowired
    private IncidenciaService incidenciaService;

    @GetMapping
    public ResponseEntity<List<Incidencia>> listarTodas() { return ResponseEntity.ok(incidenciaService.listarTodas()); }
    @GetMapping("/activas") public ResponseEntity<List<Incidencia>> listarActivas() { return ResponseEntity.ok(incidenciaService.listarActivas()); }
    @GetMapping("/supervisor/{idSupervisor}") public ResponseEntity<List<Incidencia>> listarPorSupervisor(@PathVariable Integer idSupervisor) { return ResponseEntity.ok(incidenciaService.listarPorSupervisor(idSupervisor)); }
    @GetMapping("/tienda/{idTienda}") public ResponseEntity<List<Incidencia>> listarPorTienda(@PathVariable Integer idTienda) { return ResponseEntity.ok(incidenciaService.listarPorTienda(idTienda)); }
    @GetMapping("/estado/{idEstado}") public ResponseEntity<List<Incidencia>> listarPorEstado(@PathVariable Integer idEstado) { return ResponseEntity.ok(incidenciaService.listarPorEstado(idEstado)); }
    @GetMapping("/{id}") public ResponseEntity<Incidencia> buscarPorId(@PathVariable Integer id) { return incidenciaService.buscarPorId(id).map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build()); }
    @GetMapping("/codigo/{codigo}") public ResponseEntity<Incidencia> buscarPorCodigo(@PathVariable String codigo) { return incidenciaService.buscarPorCodigo(codigo).map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build()); }

    @PostMapping
    public ResponseEntity<Object> crear(@RequestBody Incidencia incidencia) {
        try {
            Incidencia nueva = incidenciaService.crear(incidencia);
            return ResponseEntity.status(HttpStatus.CREATED).body(nueva);
        } catch (Exception e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", "Error al crear: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
        }
    }

    @PutMapping("/{id}/resolver")
    public ResponseEntity<Object> resolver(@PathVariable Integer id, @RequestParam Integer idAdmin, @RequestParam String respuesta) {
        try {
            Incidencia resuelta = incidenciaService.resolver(id, idAdmin, respuesta);
            if (resuelta == null) return ResponseEntity.notFound().build();
            Map<String, String> response = new HashMap<>();
            response.put("mensaje", "Incidencia resuelta");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", "Error: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
        }
    }

    @PutMapping("/{id}/cerrar")
    public ResponseEntity<Object> cerrar(@PathVariable Integer id) {
        try {
            Incidencia cerrada = incidenciaService.cerrar(id);
            if (cerrada == null) return ResponseEntity.notFound().build();
            Map<String, String> response = new HashMap<>();
            response.put("mensaje", "Incidencia cerrada");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", "Error: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> eliminar(@PathVariable Integer id) {
        try {
            if (incidenciaService.buscarPorId(id).isEmpty()) return ResponseEntity.notFound().build();
            incidenciaService.eliminarLogico(id);
            Map<String, String> response = new HashMap<>();
            response.put("mensaje", "Incidencia eliminada");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", "Error: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
        }
    }
}