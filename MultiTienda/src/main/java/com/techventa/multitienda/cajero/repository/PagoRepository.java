package com.techventa.multitienda.cajero.repository;

import com.techventa.multitienda.cajero.model.Pago;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface PagoRepository extends JpaRepository<Pago, Integer> {

    List<Pago> findByVenta_IdVenta(Integer idVenta);

    List<Pago> findByMetodoPago_IdMetodoPago(Integer idMetodoPago);

    List<Pago> findByEstadoPago_IdEstadoPago(Integer idEstado);
}