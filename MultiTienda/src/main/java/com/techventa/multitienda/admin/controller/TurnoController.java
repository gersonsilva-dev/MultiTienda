package com.techventa.multitienda.admin.controller;

import com.techventa.multitienda.admin.model.Turno;
import com.techventa.multitienda.admin.service.TurnoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/turnos")
public class TurnoController {

    @Autowired
    private TurnoService turnoService;

    // Listar todos los turnos
    @GetMapping
    public ResponseEntity<List<Turno>> listarTodos() {
        List<Turno> turnos = turnoService.listarTodos();
        return ResponseEntity.ok(turnos);
    }

    // Listar solo turnos activos
    @GetMapping("/activos")
    public ResponseEntity<List<Turno>> listarActivos() {
        List<Turno> turnos = turnoService.listarActivos();
        return ResponseEntity.ok(turnos);
    }

    // Buscar turno por ID
    @GetMapping("/{id}")
    public ResponseEntity<Turno> buscarPorId(@PathVariable Integer id) {
        Optional<Turno> turno = turnoService.buscarPorId(id);
        return turno.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Buscar turno por nombre
    @GetMapping("/nombre/{nombre}")
    public ResponseEntity<Turno> buscarPorNombre(@PathVariable String nombre) {
        Optional<Turno> turno = turnoService.buscarPorNombre(nombre);
        return turno.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Buscar turnos por hora de inicio
    @GetMapping("/hora-inicio/{hora}")
    public ResponseEntity<List<Turno>> buscarPorHoraInicio(@PathVariable String hora) {
        LocalTime horaTime = LocalTime.parse(hora);
        List<Turno> turnos = turnoService.buscarPorHoraInicio(horaTime);
        return ResponseEntity.ok(turnos);
    }

    // Crear nuevo turno
    @PostMapping
    public ResponseEntity<Object> crear(@RequestBody Turno turno) {
        try {
            // Verificar si el nombre del turno ya existe
            if (turnoService.existeNombre(turno.getNombreTurno())) {
                Map<String, String> error = new HashMap<>();
                error.put("error", "Ya existe un turno con el nombre: " + turno.getNombreTurno());
                return ResponseEntity.status(HttpStatus.CONFLICT).body(error);
            }
            Turno nuevoTurno = turnoService.crear(turno);
            return ResponseEntity.status(HttpStatus.CREATED).body(nuevoTurno);
        } catch (Exception e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", "Error al crear el turno: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
        }
    }

    // Actualizar turno
    @PutMapping("/{id}")
    public ResponseEntity<Object> actualizar(@PathVariable Integer id, @RequestBody Turno turno) {
        try {
            Optional<Turno> existente = turnoService.buscarPorId(id);
            if (existente.isEmpty()) {
                return ResponseEntity.notFound().build();
            }
            turno.setIdTurno(id);
            Turno actualizado = turnoService.actualizar(turno);
            return ResponseEntity.ok(actualizado);
        } catch (Exception e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", "Error al actualizar el turno: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
        }
    }

    // Eliminar turno (borrado lógico)
    @DeleteMapping("/{id}")
    public ResponseEntity<Object> eliminar(@PathVariable Integer id) {
        try {
            Optional<Turno> existente = turnoService.buscarPorId(id);
            if (existente.isEmpty()) {
                return ResponseEntity.notFound().build();
            }
            turnoService.eliminarLogico(id);
            Map<String, String> response = new HashMap<>();
            response.put("mensaje", "Turno eliminado correctamente");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", "Error al eliminar el turno: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
        }
    }
}