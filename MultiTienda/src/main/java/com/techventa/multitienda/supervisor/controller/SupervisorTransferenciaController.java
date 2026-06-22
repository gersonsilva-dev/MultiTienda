package com.techventa.multitienda.supervisor.controller;

import com.techventa.multitienda.admin.model.Transferencia;
import com.techventa.multitienda.admin.service.TransferenciaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/supervisor/transferencias")
public class SupervisorTransferenciaController {

    @Autowired
    private TransferenciaService transferenciaService;

    @GetMapping
    public ResponseEntity<List<Transferencia>> listarTodas() {
        return ResponseEntity.ok(transferenciaService.listarTodas());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Transferencia> buscarPorId(@PathVariable Integer id) {
        return transferenciaService.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}/en-transito")
    public ResponseEntity<?> enviar(@PathVariable Integer id) {

        Transferencia transferencia =
                transferenciaService.actualizarEstado(id, "EN_TRANSITO");

        if (transferencia == null) {
            return ResponseEntity.notFound().build();
        }

        Map<String, String> response = new HashMap<>();
        response.put("mensaje", "Transferencia enviada correctamente");

        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}/recibido")
    public ResponseEntity<?> recibir(@PathVariable Integer id) {

        Transferencia transferencia =
                transferenciaService.actualizarEstado(id, "RECIBIDO");

        if (transferencia == null) {
            return ResponseEntity.notFound().build();
        }

        Map<String, String> response = new HashMap<>();
        response.put("mensaje", "Transferencia recibida correctamente");

        return ResponseEntity.ok(response);
    }
}
