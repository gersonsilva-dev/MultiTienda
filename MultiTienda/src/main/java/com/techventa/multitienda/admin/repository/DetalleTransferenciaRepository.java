package com.techventa.multitienda.admin.repository;

import com.techventa.multitienda.admin.model.DetalleTransferencia;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface DetalleTransferenciaRepository extends JpaRepository<DetalleTransferencia, Integer> {
    List<DetalleTransferencia> findByTransferencia_IdTransferencia(Integer idTransferencia);
    List<DetalleTransferencia> findByProducto_IdProducto(Integer idProducto);
}