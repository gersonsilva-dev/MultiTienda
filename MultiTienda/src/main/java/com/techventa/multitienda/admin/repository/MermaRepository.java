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
    @Query("SELECT m.fechaMerma, p.nombreProducto, t.nombreTienda, mo.nombreMotivo, m.cantidad, u.nombres " +
           "FROM Merma m JOIN m.producto p JOIN m.tienda t JOIN m.motivoMerma mo JOIN m.almacenero u " +
           "WHERE m.fechaMerma BETWEEN :inicio AND :fin")
    List<Object[]> findMermasByPeriodo(@Param("inicio") LocalDateTime inicio,
                                        @Param("fin") LocalDateTime fin);

    
    
    @Query("SELECT m.fechaMerma, p.nombreProducto, t.nombreTienda, mo.nombreMotivo, m.cantidad, u.nombres " +
           "FROM Merma m JOIN m.producto p JOIN m.tienda t JOIN m.motivoMerma mo JOIN m.almacenero u " +
           "WHERE m.fechaMerma BETWEEN :inicio AND :fin AND m.tienda.idTienda = :idTienda")
    List<Object[]> findMermasByPeriodoAndTienda(@Param("inicio") LocalDateTime inicio,
                                                 @Param("fin") LocalDateTime fin,
                                                 @Param("idTienda") Integer idTienda);

    
    
    @Query("SELECT mo.idMotivoMerma, mo.nombreMotivo, COUNT(m), SUM(m.cantidad) " +
           "FROM Merma m JOIN m.motivoMerma mo " +
           "WHERE m.fechaMerma BETWEEN :inicio AND :fin " +
           "GROUP BY mo.idMotivoMerma, mo.nombreMotivo")
    List<Object[]> findResumenMermasPorMotivo(@Param("inicio") LocalDateTime inicio,
                                               @Param("fin") LocalDateTime fin);

    
    
    @Query("SELECT t.idTienda, t.nombreTienda, COUNT(m), SUM(m.cantidad) " +
           "FROM Merma m JOIN m.tienda t " +
           "WHERE m.fechaMerma BETWEEN :inicio AND :fin " +
           "GROUP BY t.idTienda, t.nombreTienda")
    List<Object[]> findResumenMermasPorTienda(@Param("inicio") LocalDateTime inicio,
                                               @Param("fin") LocalDateTime fin);

    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    // ============================================================
    // ✅ NUEVOS MÉTODOS - USANDO QUERIES NATIVAS (MONTH/YEAR)
    // ============================================================

    /**
     * Total de unidades en mermas del mes actual
     */
    @Query(value = "SELECT COALESCE(SUM(cantidad), 0) FROM mermas " +
                   "WHERE id_tienda = :idTienda " +
                   "AND activo = 1 " +
                   "AND MONTH(fecha_merma) = MONTH(CURRENT_DATE) " +
                   "AND YEAR(fecha_merma) = YEAR(CURRENT_DATE)", 
           nativeQuery = true)
    Integer getTotalUnidadesMermaMes(@Param("idTienda") Integer idTienda);

    /**
     * Valor total de pérdidas del mes (cantidad × precio_venta)
     */
    @Query(value = "SELECT COALESCE(SUM(m.cantidad * p.precio_venta), 0.0) " +
                   "FROM mermas m " +
                   "JOIN productos p ON m.id_producto = p.id_producto " +
                   "WHERE m.id_tienda = :idTienda " +
                   "AND m.activo = 1 " +
                   "AND MONTH(m.fecha_merma) = MONTH(CURRENT_DATE) " +
                   "AND YEAR(m.fecha_merma) = YEAR(CURRENT_DATE)", 
           nativeQuery = true)
    Double getValorPerdidasMes(@Param("idTienda") Integer idTienda);

    /**
     * Motivo principal del mes (el que tiene más unidades)
     */
    @Query(value = "SELECT mm.nombre_motivo FROM mermas m " +
                   "JOIN cat_motivos_merma mm ON m.id_motivo_merma = mm.id_motivo_merma " +
                   "WHERE m.id_tienda = :idTienda " +
                   "AND m.activo = 1 " +
                   "AND MONTH(m.fecha_merma) = MONTH(CURRENT_DATE) " +
                   "AND YEAR(m.fecha_merma) = YEAR(CURRENT_DATE) " +
                   "GROUP BY mm.id_motivo_merma, mm.nombre_motivo " +
                   "ORDER BY SUM(m.cantidad) DESC " +
                   "LIMIT 1", 
           nativeQuery = true)
    List<String> getMotivoPrincipalMes(@Param("idTienda") Integer idTienda);

    /**
     * Mermas agrupadas por motivo del mes actual
     */
    @Query(value = "SELECT mm.nombre_motivo, SUM(m.cantidad) as total " +
                   "FROM mermas m " +
                   "JOIN cat_motivos_merma mm ON m.id_motivo_merma = mm.id_motivo_merma " +
                   "WHERE m.id_tienda = :idTienda " +
                   "AND m.activo = 1 " +
                   "AND MONTH(m.fecha_merma) = MONTH(CURRENT_DATE) " +
                   "AND YEAR(m.fecha_merma) = YEAR(CURRENT_DATE) " +
                   "GROUP BY mm.id_motivo_merma, mm.nombre_motivo " +
                   "ORDER BY total DESC", 
           nativeQuery = true)
    List<Object[]> getMermasPorMotivoMes(@Param("idTienda") Integer idTienda);

    /**
     * Últimas mermas registradas (últimas 10)
     */
    @Query("SELECT m FROM Merma m " +
           "JOIN FETCH m.producto p " +
           "JOIN FETCH m.motivoMerma " +
           "WHERE m.tienda.idTienda = :idTienda " +
           "AND m.activo = true " +
           "ORDER BY m.fechaMerma DESC")
    List<Merma> getUltimasMermas(@Param("idTienda") Integer idTienda);

    /**
     * Buscar producto por nombre o código de barras (para autocompletado)
     */
    @Query("SELECT p FROM Producto p " +
           "WHERE p.activo = true " +
           "AND (LOWER(p.nombreProducto) LIKE LOWER(CONCAT('%', :search, '%')) " +
           "OR LOWER(p.codigoBarras) LIKE LOWER(CONCAT('%', :search, '%')))")
    List<com.techventa.multitienda.admin.model.Producto> buscarProductos(@Param("search") String search);
}