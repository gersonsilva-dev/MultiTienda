package com.techventa.multitienda.cajero.controller;

import com.techventa.multitienda.cajero.model.Pago;
import com.techventa.multitienda.cajero.service.PagoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/pagos")
public class PagoController {

    @Autowired
    private PagoService pagoService;

    @GetMapping("/venta/{idVenta}")
    public ResponseEntity<List<Pago>> listarPorVenta(@PathVariable Integer idVenta) {
        return ResponseEntity.ok(pagoService.listarPorVenta(idVenta));
    }

    @GetMapping("/metodo/{idMetodo}")
    public ResponseEntity<List<Pago>> listarPorMetodo(@PathVariable Integer idMetodo) {
        return ResponseEntity.ok(pagoService.listarPorMetodo(idMetodo));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Pago> buscarPorId(@PathVariable Integer id) {
        return pagoService.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Object> registrarPago(@RequestBody Pago pago) {
        try {
            Pago nuevo = pagoService.registrarPago(pago);
            return ResponseEntity.status(HttpStatus.CREATED).body(nuevo);
        } catch (Exception e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", "Error al registrar el pago: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
        }
    }
}