package com.techventa.multitienda.proveedor.repository;

import com.techventa.multitienda.proveedor.model.OrdenCompra;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface OrdenCompraRepository extends JpaRepository<OrdenCompra, Integer> {

    Optional<OrdenCompra> findByCodigoOrden(String codigoOrden);

    List<OrdenCompra> findByProveedor_IdProveedor(Integer idProveedor);

    List<OrdenCompra> findByTienda_IdTienda(Integer idTienda);

    List<OrdenCompra> findByIdEstadoOrdenCompra(Integer idEstado);

    List<OrdenCompra> findByActivoTrue();

    boolean existsByCodigoOrden(String codigoOrden);
}