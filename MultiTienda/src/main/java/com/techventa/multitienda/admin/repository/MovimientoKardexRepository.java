package com.techventa.multitienda.admin.repository;

import com.techventa.multitienda.admin.model.MovimientoKardex;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface MovimientoKardexRepository extends JpaRepository<MovimientoKardex, Integer> {

    List<MovimientoKardex> findByProducto_IdProducto(Integer idProducto);

    List<MovimientoKardex> findByTienda_IdTienda(Integer idTienda);

    List<MovimientoKardex> findByTipoMovimiento_IdTipoMovimiento(Integer idTipoMovimiento);

    List<MovimientoKardex> findByFechaMovimientoBetween(LocalDateTime inicio, LocalDateTime fin);
}