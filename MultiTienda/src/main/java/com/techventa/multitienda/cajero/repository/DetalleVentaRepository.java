package com.techventa.multitienda.cajero.repository;

import com.techventa.multitienda.cajero.model.DetalleVenta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface DetalleVentaRepository extends JpaRepository<DetalleVenta, Integer> {

    // ========== MÉTODOS BÁSICOS ==========
    List<DetalleVenta> findByVenta_IdVenta(Integer idVenta);
    List<DetalleVenta> findByProducto_IdProducto(Integer idProducto);
    List<DetalleVenta> findByVenta_Cajero_IdUsuario(Integer idCajero);

    // ========== MÉTODOS PARA REPORTES ==========

    /**
     * Top productos más vendidos por cantidad
     */
    @Query("SELECT p.idProducto, p.nombreProducto, c.nombreCategoria, SUM(dv.cantidad), SUM(dv.subtotalLinea) " +
           "FROM DetalleVenta dv JOIN dv.producto p JOIN p.categoria c " +
           "WHERE dv.venta.fechaVenta BETWEEN :inicio AND :fin " +
           "GROUP BY p.idProducto, p.nombreProducto, c.nombreCategoria " +
           "ORDER BY SUM(dv.cantidad) DESC")
    List<Object[]> findTopProductos(@Param("inicio") LocalDateTime inicio,
                                     @Param("fin") LocalDateTime fin);

    /**
     * Top productos más vendidos por monto
     */
    @Query("SELECT p.idProducto, p.nombreProducto, c.nombreCategoria, SUM(dv.cantidad), SUM(dv.subtotalLinea) " +
           "FROM DetalleVenta dv JOIN dv.producto p JOIN p.categoria c " +
           "WHERE dv.venta.fechaVenta BETWEEN :inicio AND :fin " +
           "GROUP BY p.idProducto, p.nombreProducto, c.nombreCategoria " +
           "ORDER BY SUM(dv.subtotalLinea) DESC")
    List<Object[]> findTopProductosByMonto(@Param("inicio") LocalDateTime inicio,
                                            @Param("fin") LocalDateTime fin);

    /**
     * Productos más vendidos por tienda
     */
    @Query("SELECT p.idProducto, p.nombreProducto, t.nombreTienda, SUM(dv.cantidad), SUM(dv.subtotalLinea) " +
           "FROM DetalleVenta dv JOIN dv.producto p JOIN dv.venta v JOIN v.tienda t " +
           "WHERE dv.venta.fechaVenta BETWEEN :inicio AND :fin AND t.idTienda = :idTienda " +
           "GROUP BY p.idProducto, p.nombreProducto, t.nombreTienda " +
           "ORDER BY SUM(dv.cantidad) DESC")
    List<Object[]> findTopProductosByTienda(@Param("inicio") LocalDateTime inicio,
                                             @Param("fin") LocalDateTime fin,
                                             @Param("idTienda") Integer idTienda);
}