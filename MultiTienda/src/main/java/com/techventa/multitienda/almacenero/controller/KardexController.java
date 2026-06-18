package com.techventa.multitienda.almacenero.controller;

import com.techventa.multitienda.admin.model.MovimientoKardex;
import com.techventa.multitienda.almacenero.service.KardexService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/kardex")
public class KardexController {

    @Autowired
    private KardexService kardexService;

    @GetMapping("/producto/{idProducto}")
    public ResponseEntity<List<MovimientoKardex>> listarPorProducto(@PathVariable Integer idProducto) {
        return ResponseEntity.ok(kardexService.listarPorProducto(idProducto));
    }

    @GetMapping("/tienda/{idTienda}")
    public ResponseEntity<List<MovimientoKardex>> listarPorTienda(@PathVariable Integer idTienda) {
        return ResponseEntity.ok(kardexService.listarPorTienda(idTienda));
    }

    @GetMapping("/fechas")
    public ResponseEntity<List<MovimientoKardex>> listarPorFechas(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime inicio,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime fin) {
        return ResponseEntity.ok(kardexService.listarPorFechas(inicio, fin));
    }
}