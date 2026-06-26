package com.techventa.multitienda.cajero.repository;

import com.techventa.multitienda.cajero.model.Venta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface VentaRepository extends JpaRepository<Venta, Integer> {

    // ========== MÉTODOS BÁSICOS ==========
    Optional<Venta> findByCodigoVenta(String codigoVenta);

    List<Venta> findByCajero_IdUsuario(Integer idCajero);

    List<Venta> findByTienda_IdTienda(Integer idTienda);

    List<Venta> findByEstadoVenta_IdEstadoVenta(Integer idEstado);

    List<Venta> findByFechaVentaBetween(LocalDateTime inicio, LocalDateTime fin);

    List<Venta> findByCliente_IdCliente(Integer idCliente);

    List<Venta> findByActivoTrue();

    boolean existsByCodigoVenta(String codigoVenta);

    // ========== MÉTODOS PARA REPORTES ==========

    /**
     * Reporte de ventas por período (todas las tiendas)
     */
    @Query("SELECT v.fechaVenta, t.nombreTienda, u.nombres, COUNT(v), SUM(v.total), AVG(v.total) " +
           "FROM Venta v JOIN v.tienda t JOIN v.cajero u " +
           "WHERE v.fechaVenta BETWEEN :inicio AND :fin " +
           "GROUP BY v.fechaVenta, t.nombreTienda, u.nombres")
    List<Object[]> findVentasByPeriodo(@Param("inicio") LocalDateTime inicio,
                                        @Param("fin") LocalDateTime fin);

    /**
     * Reporte de ventas por período (tienda específica)
     */
    @Query("SELECT v.fechaVenta, t.nombreTienda, u.nombres, COUNT(v), SUM(v.total), AVG(v.total) " +
           "FROM Venta v JOIN v.tienda t JOIN v.cajero u " +
           "WHERE v.fechaVenta BETWEEN :inicio AND :fin AND v.tienda.idTienda = :idTienda " +
           "GROUP BY v.fechaVenta, t.nombreTienda, u.nombres")
    List<Object[]> findVentasByPeriodoAndTienda(@Param("inicio") LocalDateTime inicio,
                                                 @Param("fin") LocalDateTime fin,
                                                 @Param("idTienda") Integer idTienda);

    /**
     * Resumen de ventas por tienda
     */
    @Query("SELECT t.idTienda, t.nombreTienda, COUNT(v), SUM(v.total), AVG(v.total) " +
           "FROM Venta v JOIN v.tienda t " +
           "WHERE v.fechaVenta BETWEEN :inicio AND :fin " +
           "GROUP BY t.idTienda, t.nombreTienda")
    List<Object[]> findResumenVentasPorTienda(@Param("inicio") LocalDateTime inicio,
                                               @Param("fin") LocalDateTime fin);

    /**
     * Resumen de ventas por cajero
     */
    @Query("SELECT u.idUsuario, u.nombres, u.apellidos, COUNT(v), SUM(v.total), AVG(v.total) " +
           "FROM Venta v JOIN v.cajero u " +
           "WHERE v.fechaVenta BETWEEN :inicio AND :fin " +
           "GROUP BY u.idUsuario, u.nombres, u.apellidos")
    List<Object[]> findResumenVentasPorCajero(@Param("inicio") LocalDateTime inicio,
                                               @Param("fin") LocalDateTime fin);
    
 // En VentaRepository.java
    @Query("SELECT v FROM Venta v LEFT JOIN FETCH v.detalles d LEFT JOIN FETCH d.producto p WHERE v.cajero.idUsuario = :idCajero")
    List<Venta> findByCajero_IdUsuarioWithDetails(@Param("idCajero") Integer idCajero);
    
}