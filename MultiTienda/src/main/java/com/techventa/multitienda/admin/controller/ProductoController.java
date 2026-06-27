package com.techventa.multitienda.admin.controller;

import com.techventa.multitienda.admin.dto.ProductoRequest;
import com.techventa.multitienda.admin.model.CategoriaProducto;
import com.techventa.multitienda.admin.model.Producto;
import com.techventa.multitienda.admin.model.UnidadMedida;
import com.techventa.multitienda.admin.model.Usuario;
import com.techventa.multitienda.admin.service.ProductoService;
import com.techventa.multitienda.admin.service.UsuarioService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/productos")
public class ProductoController {

    @Autowired
    private ProductoService productoService;

    @Autowired
    private UsuarioService usuarioService;

    @GetMapping
    public ResponseEntity<List<Producto>> listarTodos() {
        return ResponseEntity.ok(productoService.listarTodos());
    }

    @GetMapping("/activos")
    public ResponseEntity<List<Producto>> listarActivos() {
        return ResponseEntity.ok(productoService.listarActivos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Producto> buscarPorId(@PathVariable Integer id) {
        Optional<Producto> producto = productoService.buscarPorId(id);
        return producto.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/codigo/{codigo}")
    public ResponseEntity<Producto> buscarPorCodigoBarras(@PathVariable String codigo) {
        Optional<Producto> producto = productoService.buscarPorCodigoBarras(codigo);
        return producto.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/buscar")
    public ResponseEntity<List<Producto>> buscarPorNombre(@RequestParam String nombre) {
        return ResponseEntity.ok(productoService.buscarPorNombre(nombre));
    }

    @GetMapping("/categoria/{idCategoria}")
    public ResponseEntity<List<Producto>> buscarPorCategoria(@PathVariable Integer idCategoria) {
        return ResponseEntity.ok(productoService.buscarPorCategoria(idCategoria));
    }

    @GetMapping("/marca/{marca}")
    public ResponseEntity<List<Producto>> buscarPorMarca(@PathVariable String marca) {
        return ResponseEntity.ok(productoService.buscarPorMarca(marca));
    }

    @GetMapping("/estado/{idEstado}")
    public ResponseEntity<List<Producto>> buscarPorEstado(@PathVariable Integer idEstado) {
        return ResponseEntity.ok(productoService.buscarPorEstado(idEstado));
    }

    // ============================================================
    // CREAR PRODUCTO (CON REQUEST DTO)
    // ============================================================
    @PostMapping
    public ResponseEntity<Object> crear(@RequestBody ProductoRequest request) {
        try {
            System.out.println("📦 Creando producto: " + request.getNombreProducto());

            if (request.getCodigoBarras() != null && productoService.existeCodigoBarras(request.getCodigoBarras())) {
                Map<String, String> error = new HashMap<>();
                error.put("error", "Ya existe un producto con el código de barras: " + request.getCodigoBarras());
                return ResponseEntity.status(HttpStatus.CONFLICT).body(error);
            }

            // Crear el producto
            Producto producto = new Producto();
            producto.setNombreProducto(request.getNombreProducto());
            producto.setCodigoBarras(request.getCodigoBarras());
            producto.setDescripcionProducto(request.getDescripcionProducto());
            producto.setMarca(request.getMarca());
            producto.setModelo(request.getModelo());
            producto.setPrecioCompra(request.getPrecioCompra());
            producto.setPrecioVenta(request.getPrecioVenta());
            producto.setStockMinimo(request.getStockMinimo());
            producto.setStockMaximo(request.getStockMaximo());

            // Asignar categoría
            CategoriaProducto categoria = new CategoriaProducto();
            categoria.setIdCategoria(request.getIdCategoria());
            producto.setCategoria(categoria);

            // Asignar unidad de medida
            UnidadMedida unidadMedida = new UnidadMedida();
            unidadMedida.setIdUnidadMedida(request.getIdUnidadMedida());
            producto.setUnidadMedida(unidadMedida);

            producto.setIdEstadoProducto(1);
            producto.setActivo(true);

            Producto nuevo = productoService.crear(producto);
            return ResponseEntity.status(HttpStatus.CREATED).body(nuevo);

        } catch (Exception e) {
            e.printStackTrace();
            Map<String, String> error = new HashMap<>();
            error.put("error", "Error al crear el producto: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
        }
    }

    /// ============================================================
	// ACTUALIZAR PRODUCTO (CORREGIDO)
	// ============================================================
    @PutMapping("/{id}")
    public ResponseEntity<Object> actualizar(@PathVariable Integer id, @RequestBody ProductoRequest request) {
        try {
            Optional<Producto> existente = productoService.buscarPorId(id);
            if (existente.isEmpty()) {
                return ResponseEntity.notFound().build();
            }

            Producto producto = existente.get();
            
            // ✅ ACTUALIZAR SOLO LOS CAMPOS PERMITIDOS
            if (request.getDescripcionProducto() != null) {
                producto.setDescripcionProducto(request.getDescripcionProducto());
            }
            if (request.getPrecioVenta() != null) {
                producto.setPrecioVenta(request.getPrecioVenta());
            }
            if (request.getStockMinimo() != null) {
                producto.setStockMinimo(request.getStockMinimo());
            }
            if (request.getActivo() != null) {
                producto.setActivo(request.getActivo());
            }

            // 🔥 Si el request tiene ID de categoría, actualizar
            if (request.getIdCategoria() != null) {
                CategoriaProducto categoria = new CategoriaProducto();
                categoria.setIdCategoria(request.getIdCategoria());
                producto.setCategoria(categoria);
            }
            
            // 🔥 Si el request tiene ID de unidad de medida, actualizar
            if (request.getIdUnidadMedida() != null) {
                UnidadMedida unidadMedida = new UnidadMedida();
                unidadMedida.setIdUnidadMedida(request.getIdUnidadMedida());
                producto.setUnidadMedida(unidadMedida);
            }

            Producto actualizado = productoService.actualizar(producto);
            return ResponseEntity.ok(actualizado);

        } catch (Exception e) {
            e.printStackTrace();
            Map<String, String> error = new HashMap<>();
            error.put("error", "Error al actualizar el producto: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
        }
    }
    
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Object> eliminar(@PathVariable Integer id) {
        try {
            Optional<Producto> existente = productoService.buscarPorId(id);
            if (existente.isEmpty()) {
                return ResponseEntity.notFound().build();
            }
            productoService.eliminarFisico(id);
            Map<String, String> response = new HashMap<>();
            response.put("mensaje", "Producto eliminado correctamente");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", "Error al eliminar el producto: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
        }
    }
}