package com.techventa.multitienda.admin.repository;

import com.techventa.multitienda.admin.model.EstadoOferta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface EstadoOfertaRepository extends JpaRepository<EstadoOferta, Integer> {
    List<EstadoOferta> findByActivoTrue();
}