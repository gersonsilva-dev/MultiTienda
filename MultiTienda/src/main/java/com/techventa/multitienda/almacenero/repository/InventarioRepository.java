package com.techventa.multitienda.almacenero.repository;

import com.techventa.multitienda.almacenero.model.InventarioTienda;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface InventarioRepository extends JpaRepository<InventarioTienda, Integer> {

    // ========== MÉTODOS BÁSICOS ==========
    Optional<InventarioTienda> findByProducto_IdProductoAndTienda_IdTienda(Integer idProducto, Integer idTienda);
    List<InventarioTienda> findByTienda_IdTienda(Integer idTienda);
    List<InventarioTienda> findByProducto_IdProducto(Integer idProducto);
    List<InventarioTienda> findByStockActualLessThanEqual(Integer stock);
    List<InventarioTienda> findByActivoTrue();

    // ========== MÉTODOS PARA LA VISTA "STOCK" ==========

    @Query("SELECT COUNT(i) FROM InventarioTienda i WHERE i.activo = true AND i.tienda.idTienda = :idTienda")
    long countTotalProductosActivos(@Param("idTienda") Integer idTienda);

    @Query("SELECT COUNT(i) FROM InventarioTienda i WHERE i.stockActual <= i.stockMinimo " +
           "AND i.activo = true AND i.tienda.idTienda = :idTienda")
    long countProductosBajoMinimo(@Param("idTienda") Integer idTienda);

    // ✅ CORREGIDO: p.categoria.idCategoria en vez de p.idCategoria
    @Query("SELECT COUNT(DISTINCT p.categoria.idCategoria) FROM InventarioTienda i " +
           "JOIN i.producto p WHERE i.activo = true AND i.tienda.idTienda = :idTienda")
    long countCategoriasActivas(@Param("idTienda") Integer idTienda);

    @Query("SELECT i FROM InventarioTienda i " +
           "JOIN FETCH i.producto p " +
           "JOIN FETCH p.categoria c " +
           "WHERE i.tienda.idTienda = :idTienda AND i.activo = true " +
           "ORDER BY i.stockActual ASC")
    List<InventarioTienda> findInventarioCompletoByTienda(@Param("idTienda") Integer idTienda);

    @Query("SELECT i FROM InventarioTienda i " +
           "JOIN FETCH i.producto p " +
           "WHERE i.tienda.idTienda = :idTienda AND i.activo = true " +
           "AND (LOWER(p.nombreProducto) LIKE LOWER(CONCAT('%', :search, '%')) " +
           "OR LOWER(p.codigoBarras) LIKE LOWER(CONCAT('%', :search, '%')))")
    List<InventarioTienda> buscarPorNombreOCodigo(@Param("idTienda") Integer idTienda,
                                                  @Param("search") String search);

    @Query("SELECT COALESCE(SUM(i.stockActual * p.precioVenta), 0) FROM InventarioTienda i " +
           "JOIN i.producto p WHERE i.tienda.idTienda = :idTienda AND i.activo = true")
    Double calcularValorTotalStock(@Param("idTienda") Integer idTienda);

    // ✅ NUEVO: productos críticos directo en BD (más eficiente)
    @Query("SELECT i FROM InventarioTienda i " +
           "JOIN FETCH i.producto p " +
           "WHERE i.tienda.idTienda = :idTienda AND i.activo = true " +
           "AND i.stockActual <= i.stockMinimo AND i.stockActual > 0 " +
           "ORDER BY i.stockActual ASC")
    List<InventarioTienda> findProductosCriticos(@Param("idTienda") Integer idTienda);
}