package com.techventa.multitienda.admin.controller;

import com.techventa.multitienda.admin.model.Caja;
import com.techventa.multitienda.admin.service.CajaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/cajas")
public class CajaController {

    @Autowired
    private CajaService cajaService;

    // Listar todas las cajas
    @GetMapping
    public ResponseEntity<List<Caja>> listarTodas() {
        List<Caja> cajas = cajaService.listarTodas();
        return ResponseEntity.ok(cajas);
    }

    // Listar solo cajas activas
    @GetMapping("/activas")
    public ResponseEntity<List<Caja>> listarActivas() {
        List<Caja> cajas = cajaService.listarActivas();
        return ResponseEntity.ok(cajas);
    }

    // Buscar caja por ID
    @GetMapping("/{id}")
    public ResponseEntity<Caja> buscarPorId(@PathVariable Integer id) {
        Optional<Caja> caja = cajaService.buscarPorId(id);
        return caja.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Buscar cajas por tienda
    @GetMapping("/tienda/{idTienda}")
    public ResponseEntity<List<Caja>> buscarPorTienda(@PathVariable Integer idTienda) {
        List<Caja> cajas = cajaService.buscarPorTienda(idTienda);
        return ResponseEntity.ok(cajas);
    }

    // Buscar cajas por estado
    @GetMapping("/estado/{idEstado}")
    public ResponseEntity<List<Caja>> buscarPorEstado(@PathVariable Integer idEstado) {
        List<Caja> cajas = cajaService.buscarPorEstado(idEstado);
        return ResponseEntity.ok(cajas);
    }

    // Buscar por nombre
    @GetMapping("/buscar")
    public ResponseEntity<List<Caja>> buscarPorNombre(@RequestParam String nombre) {
        List<Caja> cajas = cajaService.buscarPorNombre(nombre);
        return ResponseEntity.ok(cajas);
    }

    // Buscar caja por número y tienda
    @GetMapping("/validar")
    public ResponseEntity<Caja> buscarPorNumeroYTienda(
            @RequestParam Integer numeroCaja,
            @RequestParam Integer idTienda) {
        Optional<Caja> caja = cajaService.buscarPorNumeroYTienda(numeroCaja, idTienda);
        return caja.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Crear nueva caja
    @PostMapping
    public ResponseEntity<Object> crear(@RequestBody Caja caja) {
        try {
            // Verificar si ya existe una caja con el mismo número en la tienda
            if (cajaService.existeNumeroEnTienda(caja.getNumeroCaja(), caja.getTienda().getIdTienda())) {
                Map<String, String> error = new HashMap<>();
                error.put("error", "Ya existe una caja con el número " + caja.getNumeroCaja() + 
                        " en la tienda seleccionada");
                return ResponseEntity.status(HttpStatus.CONFLICT).body(error);
            }
            Caja nuevaCaja = cajaService.crear(caja);
            return ResponseEntity.status(HttpStatus.CREATED).body(nuevaCaja);
        } catch (Exception e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", "Error al crear la caja: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
        }
    }

    // Actualizar caja
    @PutMapping("/{id}")
    public ResponseEntity<Object> actualizar(@PathVariable Integer id, @RequestBody Caja caja) {
        try {
            Optional<Caja> existente = cajaService.buscarPorId(id);
            if (existente.isEmpty()) {
                return ResponseEntity.notFound().build();
            }
            caja.setIdCaja(id);
            Caja actualizada = cajaService.actualizar(caja);
            return ResponseEntity.ok(actualizada);
        } catch (Exception e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", "Error al actualizar la caja: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
        }
    }

    // Eliminar caja (borrado lógico)
    @DeleteMapping("/{id}")
    public ResponseEntity<Object> eliminar(@PathVariable Integer id) {
        try {
            Optional<Caja> existente = cajaService.buscarPorId(id);
            if (existente.isEmpty()) {
                return ResponseEntity.notFound().build();
            }
            cajaService.eliminarLogico(id);
            Map<String, String> response = new HashMap<>();
            response.put("mensaje", "Caja eliminada correctamente");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", "Error al eliminar la caja: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
        }
    }
}