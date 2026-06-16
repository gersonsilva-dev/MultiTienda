package com.techventa.multitienda.admin.controller;

import com.techventa.multitienda.admin.model.DetalleTransferencia;
import com.techventa.multitienda.admin.model.Transferencia;
import com.techventa.multitienda.admin.service.TransferenciaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/transferencias")
public class TransferenciaController {

    @Autowired
    private TransferenciaService transferenciaService;

    @GetMapping public ResponseEntity<List<Transferencia>> listarTodas() { return ResponseEntity.ok(transferenciaService.listarTodas()); }
    @GetMapping("/activas") public ResponseEntity<List<Transferencia>> listarActivas() { return ResponseEntity.ok(transferenciaService.listarActivas()); }
    @GetMapping("/origen/{idTienda}") public ResponseEntity<List<Transferencia>> listarPorTiendaOrigen(@PathVariable Integer idTienda) { return ResponseEntity.ok(transferenciaService.listarPorTiendaOrigen(idTienda)); }
    @GetMapping("/destino/{idTienda}") public ResponseEntity<List<Transferencia>> listarPorTiendaDestino(@PathVariable Integer idTienda) { return ResponseEntity.ok(transferenciaService.listarPorTiendaDestino(idTienda)); }
    @GetMapping("/estado/{idEstado}") public ResponseEntity<List<Transferencia>> listarPorEstado(@PathVariable Integer idEstado) { return ResponseEntity.ok(transferenciaService.listarPorEstado(idEstado)); }
    @GetMapping("/{id}") public ResponseEntity<Transferencia> buscarPorId(@PathVariable Integer id) { return transferenciaService.buscarPorId(id).map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build()); }

    @PostMapping
    public ResponseEntity<Object> crear(@RequestBody TransferenciaRequest request) {
        try {
            Transferencia creada = transferenciaService.crear(request.getTransferencia(), request.getDetalles());
            return ResponseEntity.status(HttpStatus.CREATED).body(creada);
        } catch (Exception e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", "Error: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
        }
    }

    @PutMapping("/{id}/estado")
    public ResponseEntity<Object> actualizarEstado(@PathVariable Integer id, @RequestParam String estado) {
        try {
            Transferencia actualizada = transferenciaService.actualizarEstado(id, estado);
            if (actualizada == null) return ResponseEntity.notFound().build();
            return ResponseEntity.ok(actualizada);
        } catch (Exception e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", "Error: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> eliminar(@PathVariable Integer id) {
        transferenciaService.eliminarLogico(id);
        Map<String, String> response = new HashMap<>();
        response.put("mensaje", "Transferencia eliminada");
        return ResponseEntity.ok(response);
    }

    // Clase auxiliar para recibir transferencia + detalles
    public static class TransferenciaRequest {
        private Transferencia transferencia;
        private List<DetalleTransferencia> detalles;
        public Transferencia getTransferencia() { return transferencia; }
        public void setTransferencia(Transferencia transferencia) { this.transferencia = transferencia; }
        public List<DetalleTransferencia> getDetalles() { return detalles; }
        public void setDetalles(List<DetalleTransferencia> detalles) { this.detalles = detalles; }
    }
}