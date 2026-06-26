package com.techventa.multitienda.almacenero.repository;

import com.techventa.multitienda.almacenero.model.DetalleRecepcion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface DetalleRecepcionRepository extends JpaRepository<DetalleRecepcion, Integer> {

    List<DetalleRecepcion> findByRecepcion_IdRecepcion(Integer idRecepcion);

    List<DetalleRecepcion> findByProducto_IdProducto(Integer idProducto);

    List<DetalleRecepcion> findByLote_IdLote(Integer idLote);
}