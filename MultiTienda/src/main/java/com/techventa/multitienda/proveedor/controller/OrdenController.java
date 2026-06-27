package com.techventa.multitienda.proveedor.controller;

import com.techventa.multitienda.proveedor.model.OrdenCompra;
import com.techventa.multitienda.proveedor.service.OrdenCompraService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/ordenes")
public class OrdenController {

    @Autowired
    private OrdenCompraService ordenCompraService;

    @GetMapping
    public ResponseEntity<List<OrdenCompra>> listarTodas() {
        return ResponseEntity.ok(ordenCompraService.listarTodas());
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrdenCompra> buscarPorId(@PathVariable Integer id) {
        return ordenCompraService.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/proveedor/{idProveedor}")
    public ResponseEntity<List<OrdenCompra>> listarPorProveedor(@PathVariable Integer idProveedor) {
        return ResponseEntity.ok(ordenCompraService.listarPorProveedor(idProveedor));
    }
    
    
    
}