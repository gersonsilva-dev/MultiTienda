package com.techventa.multitienda.admin.controller;

import com.techventa.multitienda.admin.model.Lote;
import com.techventa.multitienda.admin.service.LoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/lotes")
public class LoteController {

    @Autowired
    private LoteService loteService;

    @GetMapping
    public ResponseEntity<List<Lote>> listarTodos() { return ResponseEntity.ok(loteService.listarTodos()); }
    
    @GetMapping("/activos") public ResponseEntity<List<Lote>> listarActivos() { return ResponseEntity.ok(loteService.listarActivos()); }
    
    @GetMapping("/vencidos") public ResponseEntity<List<Lote>> listarVencidos() { return ResponseEntity.ok(loteService.listarVencidos()); }
    
    @GetMapping("/producto/{idProducto}") public ResponseEntity<List<Lote>> listarPorProducto(@PathVariable Integer idProducto) { return ResponseEntity.ok(loteService.listarPorProducto(idProducto)); }
    
    @GetMapping("/tienda/{idTienda}") public ResponseEntity<List<Lote>> listarPorTienda(@PathVariable Integer idTienda) { return ResponseEntity.ok(loteService.listarPorTienda(idTienda)); }
    
    @GetMapping("/{id}") public ResponseEntity<Lote> buscarPorId(@PathVariable Integer id) { return loteService.buscarPorId(id).map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build()); }
    
    @GetMapping("/numero/{numero}") public ResponseEntity<Lote> buscarPorNumero(@PathVariable String numero) { return loteService.buscarPorNumeroLote(numero).map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build()); }

    @PostMapping
    public ResponseEntity<Object> crear(@RequestBody Lote lote) {
        try {
            if (loteService.existeNumeroLote(lote.getNumeroLote())) {
                Map<String, String> error = new HashMap<>();
                error.put("error", "Ya existe un lote con el número: " + lote.getNumeroLote());
                return ResponseEntity.status(HttpStatus.CONFLICT).body(error);
            }
            Lote nuevo = loteService.crear(lote);
            return ResponseEntity.status(HttpStatus.CREATED).body(nuevo);
        } catch (Exception e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", "Error al crear: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> actualizar(@PathVariable Integer id, @RequestBody Lote lote) {
        try {
            if (loteService.buscarPorId(id).isEmpty()) return ResponseEntity.notFound().build();
            lote.setIdLote(id);
            Lote actualizado = loteService.actualizar(lote);
            return ResponseEntity.ok(actualizado);
        } catch (Exception e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", "Error al actualizar: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> eliminar(@PathVariable Integer id) {
        if (loteService.buscarPorId(id).isEmpty()) return ResponseEntity.notFound().build();
        loteService.eliminarLogico(id);
        Map<String, String> response = new HashMap<>();
        response.put("mensaje", "Lote eliminado");
        return ResponseEntity.ok(response);
    }
}