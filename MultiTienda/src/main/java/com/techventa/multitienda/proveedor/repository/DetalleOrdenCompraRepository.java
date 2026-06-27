package com.techventa.multitienda.proveedor.repository;

import com.techventa.multitienda.proveedor.model.DetalleOrdenCompra;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DetalleOrdenCompraRepository extends JpaRepository<DetalleOrdenCompra, Integer> {

    // 🔥 BUSCAR DETALLES POR ID DE ORDEN
    List<DetalleOrdenCompra> findByOrdenCompra_IdOrden(Integer idOrden);
}