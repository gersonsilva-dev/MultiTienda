package com.techventa.multitienda.admin.controller;

import com.techventa.multitienda.admin.model.Merma;
import com.techventa.multitienda.admin.service.MermaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/mermas")
public class MermaController {

    @Autowired
    private MermaService mermaService;

    @GetMapping
    public ResponseEntity<List<Merma>> listarTodas() { return ResponseEntity.ok(mermaService.listarTodas()); }
    @GetMapping("/activas") public ResponseEntity<List<Merma>> listarActivas() { return ResponseEntity.ok(mermaService.listarActivas()); }
    @GetMapping("/producto/{idProducto}") public ResponseEntity<List<Merma>> listarPorProducto(@PathVariable Integer idProducto) { return ResponseEntity.ok(mermaService.listarPorProducto(idProducto)); }
    @GetMapping("/tienda/{idTienda}") public ResponseEntity<List<Merma>> listarPorTienda(@PathVariable Integer idTienda) { return ResponseEntity.ok(mermaService.listarPorTienda(idTienda)); }
    @GetMapping("/motivo/{idMotivo}") public ResponseEntity<List<Merma>> listarPorMotivo(@PathVariable Integer idMotivo) { return ResponseEntity.ok(mermaService.listarPorMotivo(idMotivo)); }
    @GetMapping("/{id}") public ResponseEntity<Merma> buscarPorId(@PathVariable Integer id) { return mermaService.buscarPorId(id).map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build()); }

    @PostMapping
    public ResponseEntity<Object> crear(@RequestBody Merma merma) {
        try {
            Merma nueva = mermaService.crear(merma);
            return ResponseEntity.status(HttpStatus.CREATED).body(nueva);
        } catch (Exception e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", "Error al crear: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> eliminar(@PathVariable Integer id) {
        try {
            if (mermaService.buscarPorId(id).isEmpty()) {
                return ResponseEntity.notFound().build();
            }
            mermaService.eliminarFisico(id);  // ← CAMBIADO
            Map<String, String> response = new HashMap<>();
            response.put("mensaje", "Merma eliminada correctamente");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", "Error al eliminar: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
        }
    }
}