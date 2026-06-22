package com.techventa.multitienda.admin.repository;

import com.techventa.multitienda.admin.model.TipoDescuento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface TipoDescuentoRepository extends JpaRepository<TipoDescuento, Integer> {
    List<TipoDescuento> findByActivoTrue();
}