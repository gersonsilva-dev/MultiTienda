package com.techventa.multitienda.cajero.repository;

import com.techventa.multitienda.cajero.model.DetalleDevolucion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface DetalleDevolucionRepository extends JpaRepository<DetalleDevolucion, Integer> {

    List<DetalleDevolucion> findByDevolucion_IdDevolucion(Integer idDevolucion);

    List<DetalleDevolucion> findByDetalleVenta_IdDetalleVenta(Integer idDetalleVenta);
}