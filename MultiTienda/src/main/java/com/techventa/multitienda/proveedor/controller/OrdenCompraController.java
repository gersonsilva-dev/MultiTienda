package com.techventa.multitienda.proveedor.controller;

import com.techventa.multitienda.proveedor.model.DetalleOrdenCompra;
import com.techventa.multitienda.proveedor.model.OrdenCompra;
import com.techventa.multitienda.proveedor.service.OrdenCompraService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/ordenes")
public class OrdenCompraController {

    @Autowired
    private OrdenCompraService ordenCompraService;

    // ============================================================
    // LISTAR TODAS LAS ÓRDENES CON TOTAL CALCULADO
    // ============================================================
    @GetMapping
    public ResponseEntity<List<OrdenCompra>> listarTodas() {
        // 1. Obtener todas las órdenes
        List<OrdenCompra> ordenes = ordenCompraService.listarTodas();
        
        // 2. Calcular total para cada orden
        for (OrdenCompra orden : ordenes) {
            List<DetalleOrdenCompra> detalles = ordenCompraService.listarDetallesPorOrden(orden.getIdOrden());
            
            double total = 0.0;
            if (detalles != null && !detalles.isEmpty()) {
                for (DetalleOrdenCompra detalle : detalles) {
                    total += detalle.getCantidad() * detalle.getPrecioCompra().doubleValue();
                }
            }
            orden.setTotal(total);
        }
        
        return ResponseEntity.ok(ordenes);
    }

    @GetMapping("/activas")
    public ResponseEntity<List<OrdenCompra>> listarActivas() {
        return ResponseEntity.ok(ordenCompraService.listarActivas());
    }

    @GetMapping("/proveedor/{idProveedor}")
    public ResponseEntity<List<OrdenCompra>> listarPorProveedor(@PathVariable Integer idProveedor) {
        return ResponseEntity.ok(ordenCompraService.listarPorProveedor(idProveedor));
    }

    @GetMapping("/tienda/{idTienda}")
    public ResponseEntity<List<OrdenCompra>> listarPorTienda(@PathVariable Integer idTienda) {
        return ResponseEntity.ok(ordenCompraService.listarPorTienda(idTienda));
    }

    @GetMapping("/estado/{idEstado}")
    public ResponseEntity<List<OrdenCompra>> listarPorEstado(@PathVariable Integer idEstado) {
        return ResponseEntity.ok(ordenCompraService.listarPorEstado(idEstado));
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrdenCompra> buscarPorId(@PathVariable Integer id) {
        return ordenCompraService.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/codigo/{codigo}")
    public ResponseEntity<OrdenCompra> buscarPorCodigo(@PathVariable String codigo) {
        return ordenCompraService.buscarPorCodigo(codigo)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/{id}/detalles")
    public ResponseEntity<List<DetalleOrdenCompra>> listarDetalles(@PathVariable Integer id) {
        return ResponseEntity.ok(ordenCompraService.listarDetallesPorOrden(id));
    }

    @PostMapping
    public ResponseEntity<Object> crear(@RequestBody OrdenCompraRequest request) {
        try {
            OrdenCompra nueva = ordenCompraService.crear(request.getOrdenCompra(), request.getDetalles());
            return ResponseEntity.status(HttpStatus.CREATED).body(nueva);
        } catch (Exception e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", "Error al crear la orden: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> eliminar(@PathVariable Integer id) {
        try {
            if (ordenCompraService.buscarPorId(id).isEmpty()) {
                return ResponseEntity.notFound().build();
            }
            ordenCompraService.eliminarLogico(id);
            Map<String, String> response = new HashMap<>();
            response.put("mensaje", "Orden eliminada correctamente");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", "Error al eliminar: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
        }
    }

    // ============================================================
    // INNER CLASS PARA REQUEST
    // ============================================================
    public static class OrdenCompraRequest {
        private OrdenCompra ordenCompra;
        private List<DetalleOrdenCompra> detalles;

        public OrdenCompra getOrdenCompra() { return ordenCompra; }
        public void setOrdenCompra(OrdenCompra ordenCompra) { this.ordenCompra = ordenCompra; }
        public List<DetalleOrdenCompra> getDetalles() { return detalles; }
        public void setDetalles(List<DetalleOrdenCompra> detalles) { this.detalles = detalles; }
    }
}