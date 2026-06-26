package com.techventa.multitienda.almacenero.repository;

import com.techventa.multitienda.almacenero.model.InventarioTienda;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
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

    // ========== MÉTODOS PARA REPORTES ==========

    /**
     * Productos con stock bajo (menor o igual al mínimo)
     */
    @Query("SELECT p.idProducto, p.nombreProducto, t.nombreTienda, i.stockActual, i.stockMinimo " +
           "FROM InventarioTienda i JOIN i.producto p JOIN i.tienda t " +
           "WHERE i.stockActual <= i.stockMinimo")
    List<Object[]> findBajoStock();

    /**
     * Productos con stock bajo por tienda
     */
    @Query("SELECT p.idProducto, p.nombreProducto, t.nombreTienda, i.stockActual, i.stockMinimo " +
           "FROM InventarioTienda i JOIN i.producto p JOIN i.tienda t " +
           "WHERE i.stockActual <= i.stockMinimo AND t.idTienda = :idTienda")
    List<Object[]> findBajoStockByTienda(Integer idTienda);
    
    
    
    
    
    

    /**
     * Resumen de inventario por tienda
     */
    @Query("SELECT t.idTienda, t.nombreTienda, COUNT(i), SUM(i.stockActual), AVG(i.stockActual) " +
           "FROM InventarioTienda i JOIN i.tienda t " +
           "GROUP BY t.idTienda, t.nombreTienda")
    List<Object[]> findResumenInventarioPorTienda();

    /**
     * Productos con stock agotado (stock = 0)
     */
    @Query("SELECT p.idProducto, p.nombreProducto, t.nombreTienda, i.stockActual " +
           "FROM InventarioTienda i JOIN i.producto p JOIN i.tienda t " +
           "WHERE i.stockActual = 0")
    List<Object[]> findProductosAgotados();
    
    
}