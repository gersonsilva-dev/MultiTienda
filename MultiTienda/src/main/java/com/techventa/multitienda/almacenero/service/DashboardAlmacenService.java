package com.techventa.multitienda.almacenero.service;

import com.techventa.multitienda.almacenero.dto.DashboardAlmacenDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class DashboardAlmacenService {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public DashboardAlmacenDTO cargarMetricasDashboard(Integer idTienda) {
        DashboardAlmacenDTO dto = new DashboardAlmacenDTO();

        // =========================================================================
        // 1. OBTENCIÓN DE LOS COPIADOS / INDICADORES (KPIs)
        // =========================================================================

        // KPI 1: Stock Total Físico actual en la tienda
        String sqlStockTotal = "SELECT COALESCE(SUM(stock_actual), 0) FROM inventario_tienda WHERE id_tienda = ? AND activo = 1";
        int stockTotal = jdbcTemplate.queryForObject(sqlStockTotal, Integer.class, idTienda);
        dto.setStockTotal(stockTotal);

        // KPI 2: Cantidad de productos en Alerta de Stock Crítico
        String sqlStockCritico = "SELECT COUNT(*) FROM inventario_tienda WHERE id_tienda = ? AND stock_actual <= stock_minimo AND activo = 1";
        long stockCriticoCount = jdbcTemplate.queryForObject(sqlStockCritico, Long.class, idTienda);
        dto.setStockCriticoCount(stockCriticoCount);

        // KPI 3: Productos pendientes de validación técnica
        String sqlPendientesValidar = "SELECT COUNT(DISTINCT vp.id_validacion) " +
                "FROM validacion_productos vp " +
                "JOIN inventario_tienda it ON vp.id_producto = it.id_producto " +
                "JOIN est_estado_validacion_producto evp ON vp.id_estado_validacion = evp.id_estado_validacion " +
                "WHERE it.id_tienda = ? AND evp.nombre_estado = 'PENDIENTE' AND vp.activo = 1";
        long pendientesValidarCount = jdbcTemplate.queryForObject(sqlPendientesValidar, Long.class, idTienda);
        dto.setPendientesValidarCount(pendientesValidarCount);

        // KPI 4: Valor monetario total de las mermas aprobadas en la tienda (Cantidad * Precio Compra Activo)
        String sqlValorMermas = "SELECT COALESCE(SUM(m.cantidad * hp.precio_compra), 0.0) " +
                "FROM mermas m " +
                "JOIN historial_precios hp ON m.id_producto = hp.id_producto " +
                "WHERE m.id_tienda = ? AND m.estado_autorizacion = 'APROBADO' " +
                "AND m.activo = 1 AND hp.activo = 1";
        double valorMermas = jdbcTemplate.queryForObject(sqlValorMermas, Double.class, idTienda);
        dto.setValorMermas(valorMermas);


        // =========================================================================
        // 2. OBTENCIÓN DE LOS LISTADOS INFERIORES (Tablas Dinámicas)
        // =========================================================================

        // LISTA A: Top de Productos con Stock Crítico (Ordenados por el más desabastecido)
        String sqlListaCriticos = "SELECT p.id_producto AS id, p.nombre_producto AS nombre, " +
                "p.marca AS marca, it.stock_actual AS stock, it.stock_minimo AS minimo " +
                "FROM inventario_tienda it " +
                "JOIN productos p ON it.id_producto = p.id_producto " +
                "WHERE it.id_tienda = ? AND it.stock_actual <= it.stock_minimo AND it.activo = 1 " +
                "ORDER BY it.stock_actual ASC LIMIT 5";
        List<Map<String, Object>> productosCriticos = jdbcTemplate.queryForList(sqlListaCriticos, idTienda);
        dto.setProductosCriticos(productosCriticos);

        // LISTA B: Pedidos / Órdenes de Compra en camino (Pendientes, Aprobadas o Enviadas)
        String sqlPedidosCamino = "SELECT oc.codigo_orden AS codigo, prov.razon_social AS proveedor, " +
                "oc.fecha_entrega_estimada AS fechaEntrega, eoc.nombre_estado AS estado " +
                "FROM ordenes_compra oc " +
                "JOIN proveedores prov ON oc.id_proveedor = prov.id_proveedor " +
                "JOIN est_estado_orden_compra eoc ON oc.id_estado_orden_compra = eoc.id_estado_orden_compra " +
                "WHERE oc.id_tienda = ? AND eoc.nombre_estado IN ('PENDIENTE', 'APROBADA', 'ENVIADA') AND oc.activo = 1 " +
                "ORDER BY oc.fecha_entrega_estimada ASC";
        List<Map<String, Object>> pedidosCamino = jdbcTemplate.queryForList(sqlPedidosCamino, idTienda);
        dto.setPedidosCamino(pedidosCamino);

        // LISTA C: Actividad Reciente del Almacén (Últimos 10 movimientos registrados en Kardex)
        String sqlActividad = "SELECT mk.fecha_movimiento AS fecha, p.nombre_producto AS producto, " +
                "tm.nombre_movimiento AS tipoMovimiento, mk.cantidad AS cantidad, mk.observacion AS observacion " +
                "FROM movimientos_kardex mk " +
                "JOIN productos p ON mk.id_producto = p.id_producto " +
                "JOIN cat_tipos_movimiento_inventario tm ON mk.id_tipo_movimiento = tm.id_tipo_movimiento " +
                "WHERE mk.id_tienda = ? " +
                "ORDER BY mk.fecha_movimiento DESC LIMIT 10";
        List<Map<String, Object>> actividadReciente = jdbcTemplate.queryForList(sqlActividad, idTienda);
        dto.setActividadReciente(actividadReciente);

        return dto;
    }
}