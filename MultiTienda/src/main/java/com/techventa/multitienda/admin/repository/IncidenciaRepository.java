package com.techventa.multitienda.admin.repository;

import com.techventa.multitienda.admin.model.Incidencia;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface IncidenciaRepository extends JpaRepository<Incidencia, Integer> {

    Optional<Incidencia> findByCodigoIncidencia(String codigoIncidencia);
    List<Incidencia> findByActivoTrue();
    List<Incidencia> findBySupervisor_IdUsuario(Integer idSupervisor);
    List<Incidencia> findByTienda_IdTienda(Integer idTienda);
    List<Incidencia> findByTipoIncidencia_IdTipoIncidencia(Integer idTipo);
    List<Incidencia> findByPrioridad_Nivel(Integer nivelPrioridad);
    List<Incidencia> findByEstadoIncidencia_IdEstadoIncidencia(Integer idEstado);
    List<Incidencia> findByTituloContainingIgnoreCase(String titulo);
    List<Incidencia> findByAdminResuelve_IdUsuario(Integer idAdmin);
    boolean existsByCodigoIncidencia(String codigoIncidencia);
}