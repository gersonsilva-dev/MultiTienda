package com.techventa.multitienda.cajero.controller;

import com.techventa.multitienda.cajero.model.DetalleDevolucion;
import com.techventa.multitienda.cajero.model.Devolucion;
import com.techventa.multitienda.cajero.service.DevolucionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/devoluciones")
public class DevolucionController {

    @Autowired
    private DevolucionService devolucionService;

    @GetMapping
    public ResponseEntity<List<Devolucion>> listarTodas() {
        return ResponseEntity.ok(devolucionService.listarTodas());
    }

    @GetMapping("/venta/{idVenta}")
    public ResponseEntity<List<Devolucion>> listarPorVenta(@PathVariable Integer idVenta) {
        return ResponseEntity.ok(devolucionService.listarPorVenta(idVenta));
    }

    @GetMapping("/cajero/{idCajero}")
    public ResponseEntity<List<Devolucion>> listarPorCajero(@PathVariable Integer idCajero) {
        return ResponseEntity.ok(devolucionService.listarPorCajero(idCajero));
    }

    @GetMapping("/estado/{idEstado}")
    public ResponseEntity<List<Devolucion>> listarPorEstado(@PathVariable Integer idEstado) {
        return ResponseEntity.ok(devolucionService.listarPorEstado(idEstado));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Devolucion> buscarPorId(@PathVariable Integer id) {
        return devolucionService.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/codigo/{codigo}")
    public ResponseEntity<Devolucion> buscarPorCodigo(@PathVariable String codigo) {
        return devolucionService.buscarPorCodigo(codigo)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Object> registrarDevolucion(@RequestBody DevolucionRequest request) {
        try {
            Devolucion nueva = devolucionService.registrarDevolucion(request.getDevolucion(), request.getDetalles());
            return ResponseEntity.status(HttpStatus.CREATED).body(nueva);
        } catch (Exception e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", "Error al registrar la devolución: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
        }
    }

    @PutMapping("/{id}/aprobar")
    public ResponseEntity<Object> aprobarDevolucion(@PathVariable Integer id, @RequestParam Integer idSupervisor) {
        try {
            Devolucion aprobada = devolucionService.aprobarDevolucion(id, idSupervisor);
            if (aprobada == null) return ResponseEntity.notFound().build();
            Map<String, String> response = new HashMap<>();
            response.put("mensaje", "Devolución aprobada correctamente");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", "Error al aprobar: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
        }
    }

    @PutMapping("/{id}/rechazar")
    public ResponseEntity<Object> rechazarDevolucion(@PathVariable Integer id, @RequestParam String motivo) {
        try {
            Devolucion rechazada = devolucionService.rechazarDevolucion(id, motivo);
            if (rechazada == null) return ResponseEntity.notFound().build();
            Map<String, String> response = new HashMap<>();
            response.put("mensaje", "Devolución rechazada");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", "Error al rechazar: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
        }
    }

    public static class DevolucionRequest {
        private Devolucion devolucion;
        private List<DetalleDevolucion> detalles;

        public Devolucion getDevolucion() { return devolucion; }
        public void setDevolucion(Devolucion devolucion) { this.devolucion = devolucion; }
        public List<DetalleDevolucion> getDetalles() { return detalles; }
        public void setDetalles(List<DetalleDevolucion> detalles) { this.detalles = detalles; }
    }
}