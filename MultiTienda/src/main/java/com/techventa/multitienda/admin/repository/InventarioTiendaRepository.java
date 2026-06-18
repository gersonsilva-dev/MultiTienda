package com.techventa.multitienda.admin.repository;

import com.techventa.multitienda.almacenero.model.InventarioTienda;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface InventarioTiendaRepository extends JpaRepository<InventarioTienda, Integer> {

    Optional<InventarioTienda> findByProducto_IdProductoAndTienda_IdTienda(Integer idProducto, Integer idTienda);

    List<InventarioTienda> findByTienda_IdTienda(Integer idTienda);

    List<InventarioTienda> findByProducto_IdProducto(Integer idProducto);

    List<InventarioTienda> findByStockActualLessThanEqual(Integer stock);

    List<InventarioTienda> findByActivoTrue();

    @Query("SELECT p.idProducto, p.nombreProducto, t.nombreTienda, i.stockActual, i.stockMinimo " +
           "FROM InventarioTienda i JOIN i.producto p JOIN i.tienda t " +
           "WHERE i.stockActual <= i.stockMinimo")
    List<Object[]> findBajoStock();
}