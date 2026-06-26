package com.techventa.multitienda.almacenero.repository;

import com.techventa.multitienda.almacenero.model.RecepcionMercaderia;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface RecepcionRepository extends JpaRepository<RecepcionMercaderia, Integer> {

    List<RecepcionMercaderia> findByProveedor_IdProveedor(Integer idProveedor);

    List<RecepcionMercaderia> findByTienda_IdTienda(Integer idTienda);

    List<RecepcionMercaderia> findByAlmacenero_IdUsuario(Integer idAlmacenero);

    List<RecepcionMercaderia> findByEstadoRecepcion(String estado);

    List<RecepcionMercaderia> findByFechaRecepcionBetween(LocalDateTime inicio, LocalDateTime fin);

    List<RecepcionMercaderia> findByActivoTrue();
}