package com.techventa.multitienda.supervisor.controller;

import com.techventa.multitienda.admin.dto.ProductoStockDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/stock")
public class StockController {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @GetMapping
    public ResponseEntity<List<ProductoStockDTO>> listarStock() {
        // Consulta SQL que une productos con inventario_tienda
        String sql = """
            SELECT 
                p.id_producto,
                p.codigo_barras,
                p.nombre_producto,
                p.descripcion_producto,
                p.marca,
                p.modelo,
                COALESCE(it.stock_actual, 0) AS stock_actual,
                COALESCE(it.stock_minimo, 5) AS stock_minimo
            FROM productos p
            LEFT JOIN inventario_tienda it ON p.id_producto = it.id_producto
            WHERE p.activo = 1
            ORDER BY p.id_producto
        """;
        
        List<ProductoStockDTO> productos = jdbcTemplate.query(sql, (rs, rowNum) -> {
            ProductoStockDTO dto = new ProductoStockDTO();
            dto.setIdProducto(rs.getInt("id_producto"));
            dto.setCodigoBarras(rs.getString("codigo_barras"));
            dto.setNombreProducto(rs.getString("nombre_producto"));
            dto.setDescripcionProducto(rs.getString("descripcion_producto"));
            dto.setMarca(rs.getString("marca"));
            dto.setModelo(rs.getString("modelo"));
            dto.setStockActual(rs.getInt("stock_actual"));
            dto.setStockMinimo(rs.getInt("stock_minimo"));
            return dto;
        });
        
        return ResponseEntity.ok(productos);
    }
}