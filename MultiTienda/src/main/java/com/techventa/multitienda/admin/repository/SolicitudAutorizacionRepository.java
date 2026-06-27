package com.techventa.multitienda.admin.repository;

import com.techventa.multitienda.admin.model.SolicitudAutorizacion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SolicitudAutorizacionRepository extends JpaRepository<SolicitudAutorizacion, Integer> {
    
    List<SolicitudAutorizacion> findByEstadoSolicitudAndActivoTrue(String estado);
    
    List<SolicitudAutorizacion> findByActivoTrueOrderByFechaCreacionDesc();
}