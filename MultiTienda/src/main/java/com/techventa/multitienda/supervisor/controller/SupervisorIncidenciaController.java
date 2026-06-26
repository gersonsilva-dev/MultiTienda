package com.techventa.multitienda.supervisor.controller;

import com.techventa.multitienda.admin.model.Incidencia;
import com.techventa.multitienda.admin.service.IncidenciaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/supervisor/incidencias")
public class SupervisorIncidenciaController {

    @Autowired
    private IncidenciaService incidenciaService;

    @GetMapping
    public ResponseEntity<List<Incidencia>> listarTodas() {
        return ResponseEntity.ok(incidenciaService.listarTodas());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Incidencia> buscarPorId(@PathVariable Integer id) {
        return incidenciaService.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/estado/{idEstado}")
    public ResponseEntity<List<Incidencia>> listarPorEstado(
            @PathVariable Integer idEstado) {

        return ResponseEntity.ok(
                incidenciaService.listarPorEstado(idEstado)
        );
    }

    @PutMapping("/{id}/resolver")
    public ResponseEntity<?> resolver(
            @PathVariable Integer id,
            @RequestParam Integer idAdmin,
            @RequestParam String respuesta) {

        Incidencia incidencia =
                incidenciaService.resolver(id, idAdmin, respuesta);

        if (incidencia == null) {
            return ResponseEntity.notFound().build();
        }

        Map<String, String> response = new HashMap<>();
        response.put("mensaje", "Incidencia resuelta correctamente");

        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}/cerrar")
    public ResponseEntity<?> cerrar(@PathVariable Integer id) {

        Incidencia incidencia = incidenciaService.cerrar(id);

        if (incidencia == null) {
            return ResponseEntity.notFound().build();
        }

        Map<String, String> response = new HashMap<>();
        response.put("mensaje", "Incidencia cerrada correctamente");

        return ResponseEntity.ok(response);
    }
}