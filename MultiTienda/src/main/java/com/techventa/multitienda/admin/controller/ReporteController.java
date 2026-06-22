package com.techventa.multitienda.admin.controller;

import com.techventa.multitienda.admin.dto.*;
import com.techventa.multitienda.admin.service.ReporteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/reportes")
public class ReporteController {

    @Autowired
    private ReporteService reporteService;

    @GetMapping("/ventas")
    public ResponseEntity<List<ReporteVentasDTO>> reporteVentas(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime inicio,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime fin,
            @RequestParam(required = false) Integer idTienda) {
        return ResponseEntity.ok(reporteService.reporteVentas(inicio, fin, idTienda));
    }

    @GetMapping("/top-productos")
    public ResponseEntity<List<ReporteProductosDTO>> topProductos(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime inicio,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime fin,
            @RequestParam(required = false, defaultValue = "10") Integer limit) {
        return ResponseEntity.ok(reporteService.topProductos(inicio, fin, limit));
    }

    @GetMapping("/inventario-bajo-stock")
    public ResponseEntity<List<ReporteInventarioDTO>> inventarioBajoStock() {
        return ResponseEntity.ok(reporteService.inventarioBajoStock());
    }

    @GetMapping("/mermas")
    public ResponseEntity<List<ReporteMermasDTO>> reporteMermas(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime inicio,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime fin,
            @RequestParam(required = false) Integer idTienda) {
        return ResponseEntity.ok(reporteService.reporteMermas(inicio, fin, idTienda));
    }

    @GetMapping("/incidencias")
    public ResponseEntity<List<ReporteIncidenciasDTO>> reporteIncidencias(
            @RequestParam(required = false) Integer idEstado,
            @RequestParam(required = false) Integer idTienda) {
        return ResponseEntity.ok(reporteService.reporteIncidencias(idEstado, idTienda));
    }
}