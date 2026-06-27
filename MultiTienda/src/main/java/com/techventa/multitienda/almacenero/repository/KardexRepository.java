package com.techventa.multitienda.almacenero.repository;

import com.techventa.multitienda.admin.model.MovimientoKardex;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface KardexRepository extends JpaRepository<MovimientoKardex, Integer> {

    // ✅ CORREGIDO - Usando la relación correcta
    @Query("SELECT COALESCE(SUM(m.cantidad), 0) FROM MovimientoKardex m " +
           "WHERE m.tipoMovimiento.idTipoMovimiento = :idTipo " +
           "AND MONTH(m.fechaMovimiento) = MONTH(CURRENT_DATE) " +
           "AND YEAR(m.fechaMovimiento) = YEAR(CURRENT_DATE)")
    int getTotalUnidadesPorTipoMes(@Param("idTipo") Integer idTipo);

    // ✅ Query principal también corregido
    @Query("SELECT m FROM MovimientoKardex m " +
           "LEFT JOIN FETCH m.producto " +
           "LEFT JOIN FETCH m.tipoMovimiento " +
           "LEFT JOIN FETCH m.usuarioCreacion " +
           "LEFT JOIN FETCH m.tienda " +
           "WHERE (:productoId IS NULL OR m.producto.idProducto = :productoId) " +
           "AND (:tipoMovId IS NULL OR m.tipoMovimiento.idTipoMovimiento = :tipoMovId) " +
           "ORDER BY m.fechaMovimiento DESC")
    Page<MovimientoKardex> buscarMovimientos(
            @Param("productoId") Integer productoId,
            @Param("tipoMovId") Integer tipoMovId,
            Pageable pageable);

    // Métodos derivados
    List<MovimientoKardex> findByProducto_IdProducto(Integer idProducto);
    List<MovimientoKardex> findByTienda_IdTienda(Integer idTienda);
    List<MovimientoKardex> findByProducto_IdProductoOrderByFechaMovimientoDesc(Integer idProducto);
    List<MovimientoKardex> findByTienda_IdTiendaOrderByFechaMovimientoDesc(Integer idTienda);
    
    @Query("SELECT m FROM MovimientoKardex m " +
    	       "LEFT JOIN FETCH m.producto " +
    	       "LEFT JOIN FETCH m.tipoMovimiento " +
    	       "LEFT JOIN FETCH m.usuarioCreacion " +
    	       "LEFT JOIN FETCH m.tienda " +
    	       "WHERE (:search IS NULL OR LOWER(m.producto.nombreProducto) LIKE LOWER(CONCAT('%', :search, '%')) " +
    	       "   OR LOWER(m.producto.codigoBarras) LIKE LOWER(CONCAT('%', :search, '%'))) " +
    	       "AND (:tipoMovId IS NULL OR m.tipoMovimiento.idTipoMovimiento = :tipoMovId) " +
    	       "ORDER BY m.fechaMovimiento DESC")
    	Page<MovimientoKardex> buscarPorTexto(
    	        @Param("search") String search,
    	        @Param("tipoMovId") Integer tipoMovId,
    	        Pageable pageable);
}