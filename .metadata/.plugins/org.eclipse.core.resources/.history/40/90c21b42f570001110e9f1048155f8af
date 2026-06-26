package com.techventa.multitienda.supervisor.controller;

import com.techventa.multitienda.admin.model.Oferta;
import com.techventa.multitienda.admin.service.OfertaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/supervisor/ofertas")
public class SupervisorOfertaController {

    @Autowired
    private OfertaService ofertaService;

    @GetMapping
    public ResponseEntity<List<Oferta>> listarTodas() {
        return ResponseEntity.ok(ofertaService.listarTodas());
    }

    @GetMapping("/vigentes")
    public ResponseEntity<List<Oferta>> listarVigentes() {
        return ResponseEntity.ok(ofertaService.listarVigentes());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Oferta> buscarPorId(@PathVariable Integer id) {
        return ofertaService.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}/aprobar")
    public ResponseEntity<?> aprobar(
            @PathVariable Integer id,
            @RequestParam Integer idUsuario) {

        Oferta oferta = ofertaService.aprobar(id, idUsuario);

        if (oferta == null) {
            return ResponseEntity.notFound().build();
        }

        Map<String, String> response = new HashMap<>();
        response.put("mensaje", "Oferta aprobada correctamente");

        return ResponseEntity.ok(response);
    }
}