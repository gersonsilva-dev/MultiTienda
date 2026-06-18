package com.techventa.multitienda.cajero.controller;

import com.techventa.multitienda.cajero.model.TurnoCaja;
import com.techventa.multitienda.cajero.service.TurnoCajaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/turnos-caja")
public class TurnoCajaController {

    @Autowired
    private TurnoCajaService turnoCajaService;

    @GetMapping
    public ResponseEntity<List<TurnoCaja>> listarTodos() {
        return ResponseEntity.ok(turnoCajaService.listarTodos());
    }

    @GetMapping("/cajero/{idCajero}")
    public ResponseEntity<List<TurnoCaja>> listarPorCajero(@PathVariable Integer idCajero) {
        return ResponseEntity.ok(turnoCajaService.listarPorCajero(idCajero));
    }

    @GetMapping("/tienda/{idTienda}")
    public ResponseEntity<List<TurnoCaja>> listarPorTienda(@PathVariable Integer idTienda) {
        return ResponseEntity.ok(turnoCajaService.listarPorTienda(idTienda));
    }

    @GetMapping("/caja/{idCaja}")
    public ResponseEntity<List<TurnoCaja>> listarPorCaja(@PathVariable Integer idCaja) {
        return ResponseEntity.ok(turnoCajaService.listarPorCaja(idCaja));
    }

    @GetMapping("/activos")
    public ResponseEntity<List<TurnoCaja>> listarActivos() {
        return ResponseEntity.ok(turnoCajaService.listarActivos());
    }

    @GetMapping("/caja/{idCaja}/activo")
    public ResponseEntity<TurnoCaja> buscarActivoPorCaja(@PathVariable Integer idCaja) {
        return turnoCajaService.buscarActivoPorCaja(idCaja)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/{id}")
    public ResponseEntity<TurnoCaja> buscarPorId(@PathVariable Integer id) {
        return turnoCajaService.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping("/abrir")
    public ResponseEntity<Object> abrirTurno(@RequestBody TurnoCaja turnoCaja) {
        try {
            TurnoCaja nuevo = turnoCajaService.abrirTurno(turnoCaja);
            return ResponseEntity.status(HttpStatus.CREATED).body(nuevo);
        } catch (Exception e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", "Error al abrir turno: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
        }
    }

    @PutMapping("/{id}/cerrar")
    public ResponseEntity<Object> cerrarTurno(
            @PathVariable Integer id,
            @RequestParam BigDecimal fondoFinal,
            @RequestParam(required = false) String observaciones) {
        try {
            TurnoCaja cerrado = turnoCajaService.cerrarTurno(id, fondoFinal, observaciones);
            if (cerrado == null) {
                return ResponseEntity.notFound().build();
            }
            Map<String, String> response = new HashMap<>();
            response.put("mensaje", "Turno cerrado correctamente");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", "Error al cerrar turno: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
        }
    }
}