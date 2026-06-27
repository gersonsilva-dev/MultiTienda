package com.techventa.multitienda.admin.controller;

import com.techventa.multitienda.admin.dto.OfertaDTO;
import com.techventa.multitienda.admin.model.Oferta;
import com.techventa.multitienda.admin.service.OfertaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/ofertas")
public class OfertaController {

    @Autowired
    private OfertaService ofertaService;

    @GetMapping
    public ResponseEntity<List<Oferta>> listarTodas() {
        return ResponseEntity.ok(ofertaService.listarTodas());
    }

    // 🔥 Este es el que usa el cajero - ahora devuelve DTO
    @GetMapping("/activas")
    public ResponseEntity<List<OfertaDTO>> listarActivas() {
        return ResponseEntity.ok(ofertaService.listarActivasDTO());
    }

    @GetMapping("/vigentes")
    public ResponseEntity<List<Oferta>> listarVigentes() {
        return ResponseEntity.ok(ofertaService.listarVigentes());
    }

    @GetMapping("/estado/{idEstado}")
    public ResponseEntity<List<Oferta>> listarPorEstado(@PathVariable Integer idEstado) {
        return ResponseEntity.ok(ofertaService.listarPorEstado(idEstado));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Oferta> buscarPorId(@PathVariable Integer id) {
        Optional<Oferta> oferta = ofertaService.buscarPorId(id);
        return oferta.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/buscar")
    public ResponseEntity<List<Oferta>> buscarPorNombre(@RequestParam String nombre) {
        return ResponseEntity.ok(ofertaService.buscarPorNombre(nombre));
    }

    @GetMapping("/tipo-descuento/{idTipo}")
    public ResponseEntity<List<Oferta>> buscarPorTipoDescuento(@PathVariable Integer idTipo) {
        return ResponseEntity.ok(ofertaService.buscarPorTipoDescuento(idTipo));
    }

    @PostMapping
    public ResponseEntity<Object> crear(@RequestBody Oferta oferta) {
        try {
            if (ofertaService.existeNombre(oferta.getNombreOferta())) {
                Map<String, String> error = new HashMap<>();
                error.put("error", "Ya existe una oferta con el nombre: " + oferta.getNombreOferta());
                return ResponseEntity.status(HttpStatus.CONFLICT).body(error);
            }
            Oferta nuevaOferta = ofertaService.crear(oferta);
            return ResponseEntity.status(HttpStatus.CREATED).body(nuevaOferta);
        } catch (Exception e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", "Error al crear la oferta: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
        }
    }

    @PutMapping("/{id}/aprobar")
    public ResponseEntity<Object> aprobar(@PathVariable Integer id, @RequestParam Integer idUsuarioAprobador) {
        try {
            Optional<Oferta> existente = ofertaService.buscarPorId(id);
            if (existente.isEmpty()) {
                return ResponseEntity.notFound().build();
            }
            ofertaService.aprobar(id, idUsuarioAprobador);
            Map<String, String> response = new HashMap<>();
            response.put("mensaje", "Oferta aprobada correctamente");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", "Error al aprobar la oferta: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> actualizar(@PathVariable Integer id, @RequestBody Oferta oferta) {
        try {
            Optional<Oferta> existente = ofertaService.buscarPorId(id);
            if (existente.isEmpty()) {
                return ResponseEntity.notFound().build();
            }
            oferta.setIdOferta(id);
            ofertaService.actualizar(oferta);
            Map<String, String> response = new HashMap<>();
            response.put("mensaje", "Oferta actualizada correctamente");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", "Error al actualizar la oferta: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
        }
    }

    @PutMapping("/{id}/estado")
    public ResponseEntity<Object> cambiarEstado(@PathVariable Integer id, @RequestParam Boolean activo) {
        try {
            Optional<Oferta> existente = ofertaService.buscarPorId(id);
            if (existente.isEmpty()) {
                return ResponseEntity.notFound().build();
            }
            Oferta oferta = existente.get();
            oferta.setActivo(activo);
            ofertaService.actualizar(oferta);
            Map<String, String> response = new HashMap<>();
            response.put("mensaje", "Estado actualizado correctamente");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", "Error al actualizar estado: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> eliminar(@PathVariable Integer id) {
        try {
            Optional<Oferta> existente = ofertaService.buscarPorId(id);
            if (existente.isEmpty()) {
                return ResponseEntity.notFound().build();
            }
            ofertaService.eliminarFisico(id);
            Map<String, String> response = new HashMap<>();
            response.put("mensaje", "Oferta eliminada correctamente");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", "Error al eliminar la oferta: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
        }
    }
}