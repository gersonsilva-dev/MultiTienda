package com.techventa.multitienda.cajero.controller;

import com.techventa.multitienda.admin.model.Producto;
import com.techventa.multitienda.admin.service.ProductoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/scanner")
public class ScannerController {

    @Autowired
    private ProductoService productoService;

    @GetMapping("/producto")
    public ResponseEntity<Producto> escanearProducto(@RequestParam String codigoBarras) {
        Optional<Producto> producto = productoService.buscarPorCodigoBarras(codigoBarras);
        if (producto.isPresent() && producto.get().getActivo()) {
            return ResponseEntity.ok(producto.get());
        }
        return ResponseEntity.notFound().build();
    }
}