package com.techventa.multitienda.admin.repository;

import com.techventa.multitienda.admin.model.Prioridad;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface PrioridadRepository extends JpaRepository<Prioridad, Integer> {
    List<Prioridad> findByActivoTrue();
}