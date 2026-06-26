package com.techventa.multitienda.supervisor.controller;

import com.techventa.multitienda.admin.model.Caja;
import com.techventa.multitienda.admin.service.CajaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/supervisor/cajas")
public class SupervisorCajaController {

    @Autowired
    private CajaService cajaService;

    @GetMapping
    public ResponseEntity<List<Caja>> listarTodas() {
        return ResponseEntity.ok(cajaService.listarTodas());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Caja> buscarPorId(@PathVariable Integer id) {
        return cajaService.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/tienda/{idTienda}")
    public ResponseEntity<List<Caja>> buscarPorTienda(
            @PathVariable Integer idTienda) {

        return ResponseEntity.ok(
                cajaService.buscarPorTienda(idTienda)
        );
    }

    @PutMapping("/{id}")
    public ResponseEntity<Caja> actualizar(
            @PathVariable Integer id,
            @RequestBody Caja caja) {

        caja.setIdCaja(id);

        return ResponseEntity.ok(
                cajaService.actualizar(caja)
        );
    }
}