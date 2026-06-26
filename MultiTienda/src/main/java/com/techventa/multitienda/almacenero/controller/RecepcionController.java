package com.techventa.multitienda.almacenero.controller;

import com.techventa.multitienda.almacenero.model.*;
import com.techventa.multitienda.almacenero.service.RecepcionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/recepciones")
public class RecepcionController {

    @Autowired
    private RecepcionService recepcionService;

    @GetMapping
    public ResponseEntity<List<RecepcionMercaderia>> listarTodas() {
        return ResponseEntity.ok(recepcionService.listarTodas());
    }

    @GetMapping("/proveedor/{idProveedor}")
    public ResponseEntity<List<RecepcionMercaderia>> listarPorProveedor(@PathVariable Integer idProveedor) {
        return ResponseEntity.ok(recepcionService.listarPorProveedor(idProveedor));
    }

    @GetMapping("/tienda/{idTienda}")
    public ResponseEntity<List<RecepcionMercaderia>> listarPorTienda(@PathVariable Integer idTienda) {
        return ResponseEntity.ok(recepcionService.listarPorTienda(idTienda));
    }

    @GetMapping("/{id}")
    public ResponseEntity<RecepcionMercaderia> buscarPorId(@PathVariable Integer id) {
        return recepcionService.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Object> registrarRecepcion(@RequestBody RecepcionRequest request) {
        try {
            RecepcionMercaderia nueva = recepcionService.registrarRecepcion(
                request.getRecepcion(), 
                request.getDetalles()
            );
            return ResponseEntity.status(HttpStatus.CREATED).body(nueva);
        } catch (Exception e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", "Error al registrar recepción: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
        }
    }

    public static class RecepcionRequest {
        private RecepcionMercaderia recepcion;
        private List<DetalleRecepcion> detalles;

        public RecepcionMercaderia getRecepcion() { return recepcion; }
        public void setRecepcion(RecepcionMercaderia recepcion) { this.recepcion = recepcion; }
        public List<DetalleRecepcion> getDetalles() { return detalles; }
        public void setDetalles(List<DetalleRecepcion> detalles) { this.detalles = detalles; }
    }
}