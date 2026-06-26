package com.techventa.multitienda.admin.repository;

import com.techventa.multitienda.admin.model.Lote;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface LoteRepository extends JpaRepository<Lote, Integer> {

    Optional<Lote> findByNumeroLote(String numeroLote);

    List<Lote> findByProducto_IdProducto(Integer idProducto);

    List<Lote> findByTienda_IdTienda(Integer idTienda);

    List<Lote> findByActivoTrue();

    List<Lote> findByFechaVencimientoBefore(LocalDate fecha);

    List<Lote> findByFechaVencimientoAfter(LocalDate fecha);

    List<Lote> findByStockLoteLessThanEqual(Integer stock);

    List<Lote> findByProducto_IdProductoAndTienda_IdTienda(Integer idProducto, Integer idTienda);

    boolean existsByNumeroLote(String numeroLote);
}