package com.techventa.multitienda.admin.controller;

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
@RequestMapping("/ofertas")
public class OfertaController {

    @Autowired
    private OfertaService ofertaService;

    // Listar todas las ofertas
    @GetMapping
    public ResponseEntity<List<Oferta>> listarTodas() {
        List<Oferta> ofertas = ofertaService.listarTodas();
        return ResponseEntity.ok(ofertas);
    }

    // Listar solo ofertas activas
    @GetMapping("/activas")
    public ResponseEntity<List<Oferta>> listarActivas() {
        List<Oferta> ofertas = ofertaService.listarActivas();
        return ResponseEntity.ok(ofertas);
    }

    // Listar ofertas vigentes (ahora mismo)
    @GetMapping("/vigentes")
    public ResponseEntity<List<Oferta>> listarVigentes() {
        List<Oferta> ofertas = ofertaService.listarVigentes();
        return ResponseEntity.ok(ofertas);
    }

    // Listar ofertas por estado
    @GetMapping("/estado/{idEstado}")
    public ResponseEntity<List<Oferta>> listarPorEstado(@PathVariable Integer idEstado) {
        List<Oferta> ofertas = ofertaService.listarPorEstado(idEstado);
        return ResponseEntity.ok(ofertas);
    }

    // Buscar oferta por ID
    @GetMapping("/{id}")
    public ResponseEntity<Oferta> buscarPorId(@PathVariable Integer id) {
        Optional<Oferta> oferta = ofertaService.buscarPorId(id);
        return oferta.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Buscar ofertas por nombre
    @GetMapping("/buscar")
    public ResponseEntity<List<Oferta>> buscarPorNombre(@RequestParam String nombre) {
        List<Oferta> ofertas = ofertaService.buscarPorNombre(nombre);
        return ResponseEntity.ok(ofertas);
    }

    // Buscar ofertas por tipo de descuento
    @GetMapping("/tipo-descuento/{idTipo}")
    public ResponseEntity<List<Oferta>> buscarPorTipoDescuento(@PathVariable Integer idTipo) {
        List<Oferta> ofertas = ofertaService.buscarPorTipoDescuento(idTipo);
        return ResponseEntity.ok(ofertas);
    }

    // Crear nueva oferta
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

    // Aprobar oferta
    @PutMapping("/{id}/aprobar")
    public ResponseEntity<Object> aprobar(@PathVariable Integer id, @RequestParam Integer idUsuarioAprobador) {
        try {
            Optional<Oferta> existente = ofertaService.buscarPorId(id);
            if (existente.isEmpty()) {
                return ResponseEntity.notFound().build();
            }
            Oferta aprobada = ofertaService.aprobar(id, idUsuarioAprobador);
            Map<String, String> response = new HashMap<>();
            response.put("mensaje", "Oferta aprobada correctamente");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", "Error al aprobar la oferta: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
        }
    }

    // Actualizar oferta
    @PutMapping("/{id}")
    public ResponseEntity<Object> actualizar(@PathVariable Integer id, @RequestBody Oferta oferta) {
        try {
            Optional<Oferta> existente = ofertaService.buscarPorId(id);
            if (existente.isEmpty()) {
                return ResponseEntity.notFound().build();
            }
            oferta.setIdOferta(id);
            Oferta actualizada = ofertaService.actualizar(oferta);
            return ResponseEntity.ok(actualizada);
        } catch (Exception e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", "Error al actualizar la oferta: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
        }
    }

    // Eliminar oferta (borrado lógico)
    @DeleteMapping("/{id}")
    public ResponseEntity<Object> eliminar(@PathVariable Integer id) {
        try {
            Optional<Oferta> existente = ofertaService.buscarPorId(id);
            if (existente.isEmpty()) {
                return ResponseEntity.notFound().build();
            }
            ofertaService.eliminarLogico(id);
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