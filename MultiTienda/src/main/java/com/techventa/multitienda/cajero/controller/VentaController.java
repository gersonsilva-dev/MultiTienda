package com.techventa.multitienda.cajero.controller;

import com.techventa.multitienda.cajero.model.DetalleVenta;
import com.techventa.multitienda.cajero.model.Venta;
import com.techventa.multitienda.cajero.service.VentaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/ventas")  // 🔥 AGREGAR /api AQUÍ
public class VentaController {

    @Autowired
    private VentaService ventaService;

    @GetMapping
    public ResponseEntity<List<Venta>> listarTodas() {
        return ResponseEntity.ok(ventaService.listarTodas());
    }

    @GetMapping("/activas")
    public ResponseEntity<List<Venta>> listarActivas() {
        return ResponseEntity.ok(ventaService.listarActivas());
    }

    @GetMapping("/cajero/{idCajero}")
    public ResponseEntity<List<Venta>> listarPorCajero(@PathVariable Integer idCajero) {
        return ResponseEntity.ok(ventaService.listarPorCajero(idCajero));
    }

    @GetMapping("/tienda/{idTienda}")
    public ResponseEntity<List<Venta>> listarPorTienda(@PathVariable Integer idTienda) {
        return ResponseEntity.ok(ventaService.listarPorTienda(idTienda));
    }

    @GetMapping("/estado/{idEstado}")
    public ResponseEntity<List<Venta>> listarPorEstado(@PathVariable Integer idEstado) {
        return ResponseEntity.ok(ventaService.listarPorEstado(idEstado));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Venta> buscarPorId(@PathVariable Integer id) {
        return ventaService.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/codigo/{codigo}")
    public ResponseEntity<Venta> buscarPorCodigo(@PathVariable String codigo) {
        return ventaService.buscarPorCodigo(codigo)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Object> registrarVenta(@RequestBody VentaRequest request) {
        try {
            Venta nueva = ventaService.registrarVenta(request.getVenta(), request.getDetalles());
            return ResponseEntity.status(HttpStatus.CREATED).body(nueva);
        } catch (Exception e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", "Error al registrar la venta: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
        }
    }

    @PutMapping("/{id}/anular")
    public ResponseEntity<Object> anularVenta(@PathVariable Integer id) {
        try {
            Venta anulada = ventaService.anularVenta(id);
            if (anulada == null) {
                return ResponseEntity.notFound().build();
            }
            Map<String, String> response = new HashMap<>();
            response.put("mensaje", "Venta anulada correctamente");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", "Error al anular la venta: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
        }
    }

    public static class VentaRequest {
        private Venta venta;
        private List<DetalleVenta> detalles;

        public Venta getVenta() { return venta; }
        public void setVenta(Venta venta) { this.venta = venta; }
        public List<DetalleVenta> getDetalles() { return detalles; }
        public void setDetalles(List<DetalleVenta> detalles) { this.detalles = detalles; }
    }
}