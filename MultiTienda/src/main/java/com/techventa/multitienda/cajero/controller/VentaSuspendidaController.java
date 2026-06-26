package com.techventa.multitienda.cajero.controller;

import com.techventa.multitienda.cajero.model.VentaSuspendida;
import com.techventa.multitienda.cajero.service.VentaSuspendidaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/ventas-suspendidas")
public class VentaSuspendidaController {

    @Autowired
    private VentaSuspendidaService ventaSuspendidaService;

    @GetMapping("/cajero/{idCajero}")
    public ResponseEntity<List<VentaSuspendida>> listarPorCajero(@PathVariable Integer idCajero) {
        return ResponseEntity.ok(ventaSuspendidaService.listarPorCajero(idCajero));
    }

    @GetMapping("/tienda/{idTienda}")
    public ResponseEntity<List<VentaSuspendida>> listarPorTienda(@PathVariable Integer idTienda) {
        return ResponseEntity.ok(ventaSuspendidaService.listarPorTienda(idTienda));
    }

    @GetMapping("/{id}")
    public ResponseEntity<VentaSuspendida> buscarPorId(@PathVariable Integer id) {
        return ventaSuspendidaService.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/codigo/{codigo}")
    public ResponseEntity<VentaSuspendida> buscarPorCodigo(@PathVariable String codigo) {
        return ventaSuspendidaService.buscarPorCodigo(codigo)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping("/suspender")
    public ResponseEntity<Object> suspenderVenta(@RequestBody VentaSuspendida suspension) {
        try {
            VentaSuspendida nueva = ventaSuspendidaService.suspenderVenta(suspension);
            return ResponseEntity.status(HttpStatus.CREATED).body(nueva);
        } catch (Exception e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", "Error al suspender la venta: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
        }
    }

    @PutMapping("/{id}/recuperar")
    public ResponseEntity<Object> recuperarVenta(@PathVariable Integer id) {
        try {
            VentaSuspendida recuperada = ventaSuspendidaService.recuperarVenta(id);
            if (recuperada == null) {
                return ResponseEntity.notFound().build();
            }
            Map<String, String> response = new HashMap<>();
            response.put("mensaje", "Venta recuperada exitosamente");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", "Error al recuperar la venta: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> cancelarSuspension(@PathVariable Integer id) {
        try {
            ventaSuspendidaService.cancelarSuspension(id);
            Map<String, String> response = new HashMap<>();
            response.put("mensaje", "Venta suspendida cancelada");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", "Error al cancelar: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
        }
    }
}