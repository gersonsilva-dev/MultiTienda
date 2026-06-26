package com.techventa.multitienda.supervisor.controller;

import com.techventa.multitienda.cajero.model.Devolucion;
import com.techventa.multitienda.cajero.service.DevolucionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/supervisor/devoluciones")
public class SupervisorDevolucionController {

    @Autowired
    private DevolucionService devolucionService;

    @GetMapping
    public ResponseEntity<List<Devolucion>> listarTodas() {
        return ResponseEntity.ok(devolucionService.listarTodas());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Devolucion> buscarPorId(@PathVariable Integer id) {
        return devolucionService.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}/aprobar")
    public ResponseEntity<?> aprobar(
            @PathVariable Integer id,
            @RequestParam Integer idSupervisor) {

        Devolucion devolucion =
                devolucionService.aprobarDevolucion(id, idSupervisor);

        if (devolucion == null) {
            return ResponseEntity.notFound().build();
        }

        Map<String, String> response = new HashMap<>();
        response.put("mensaje", "Devolución aprobada correctamente");

        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}/rechazar")
    public ResponseEntity<?> rechazar(
            @PathVariable Integer id,
            @RequestParam String motivo) {

        Devolucion devolucion =
                devolucionService.rechazarDevolucion(id, motivo);

        if (devolucion == null) {
            return ResponseEntity.notFound().build();
        }

        Map<String, String> response = new HashMap<>();
        response.put("mensaje", "Devolución rechazada correctamente");

        return ResponseEntity.ok(response);
    }
}
