package com.techventa.multitienda.admin.repository;

import com.techventa.multitienda.admin.model.Merma;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface MermaRepository extends JpaRepository<Merma, Integer> {

    // ========== MÉTODOS BÁSICOS ==========
    List<Merma> findByActivoTrue();
    List<Merma> findByProducto_IdProducto(Integer idProducto);
    List<Merma> findByTienda_IdTienda(Integer idTienda);
    List<Merma> findByMotivoMerma_IdMotivoMerma(Integer idMotivo);
    List<Merma> findByAlmacenero_IdUsuario(Integer idAlmacenero);
    List<Merma> findByEstadoAutorizacion(String estadoAutorizacion);
    List<Merma> findByFechaMermaBetween(LocalDateTime inicio, LocalDateTime fin);
    List<Merma> findByCantidadGreaterThanEqual(Integer cantidad);

    // ========== MÉTODOS PARA REPORTES ==========

    /**
     * Reporte de mermas por período (todas las tiendas)
     */
    @Query("SELECT m.fechaMerma, p.nombreProducto, t.nombreTienda, mo.nombreMotivo, m.cantidad, u.nombres " +
           "FROM Merma m JOIN m.producto p JOIN m.tienda t JOIN m.motivoMerma mo JOIN m.almacenero u " +
           "WHERE m.fechaMerma BETWEEN :inicio AND :fin")
    List<Object[]> findMermasByPeriodo(@Param("inicio") LocalDateTime inicio,
                                        @Param("fin") LocalDateTime fin);

    /**
     * Reporte de mermas por período (tienda específica)
     */
    @Query("SELECT m.fechaMerma, p.nombreProducto, t.nombreTienda, mo.nombreMotivo, m.cantidad, u.nombres " +
           "FROM Merma m JOIN m.producto p JOIN m.tienda t JOIN m.motivoMerma mo JOIN m.almacenero u " +
           "WHERE m.fechaMerma BETWEEN :inicio AND :fin AND m.tienda.idTienda = :idTienda")
    List<Object[]> findMermasByPeriodoAndTienda(@Param("inicio") LocalDateTime inicio,
                                                 @Param("fin") LocalDateTime fin,
                                                 @Param("idTienda") Integer idTienda);

    /**
     * Resumen de mermas por motivo
     */
    @Query("SELECT mo.idMotivoMerma, mo.nombreMotivo, COUNT(m), SUM(m.cantidad) " +
           "FROM Merma m JOIN m.motivoMerma mo " +
           "WHERE m.fechaMerma BETWEEN :inicio AND :fin " +
           "GROUP BY mo.idMotivoMerma, mo.nombreMotivo")
    List<Object[]> findResumenMermasPorMotivo(@Param("inicio") LocalDateTime inicio,
                                               @Param("fin") LocalDateTime fin);

    /**
     * Resumen de mermas por tienda
     */
    @Query("SELECT t.idTienda, t.nombreTienda, COUNT(m), SUM(m.cantidad) " +
           "FROM Merma m JOIN m.tienda t " +
           "WHERE m.fechaMerma BETWEEN :inicio AND :fin " +
           "GROUP BY t.idTienda, t.nombreTienda")
    List<Object[]> findResumenMermasPorTienda(@Param("inicio") LocalDateTime inicio,
                                               @Param("fin") LocalDateTime fin);
}