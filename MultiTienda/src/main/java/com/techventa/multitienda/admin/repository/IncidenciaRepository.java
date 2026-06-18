package com.techventa.multitienda.admin.repository;

import com.techventa.multitienda.admin.model.Incidencia;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface IncidenciaRepository extends JpaRepository<Incidencia, Integer> {

    // ========== MÉTODOS BÁSICOS ==========
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

    // ========== MÉTODOS PARA REPORTES ==========

    /**
     * Todas las incidencias para reporte
     */
    @Query("SELECT i.codigoIncidencia, i.titulo, ti.nombreTipo, p.nombrePrioridad, e.nombreEstado, t.nombreTienda, i.fechaCreacion " +
           "FROM Incidencia i JOIN i.tipoIncidencia ti JOIN i.prioridad p JOIN i.estadoIncidencia e JOIN i.tienda t")
    List<Object[]> findAllIncidenciasReporte();

    /**
     * Incidencias por estado
     */
    @Query("SELECT i.codigoIncidencia, i.titulo, ti.nombreTipo, p.nombrePrioridad, e.nombreEstado, t.nombreTienda, i.fechaCreacion " +
           "FROM Incidencia i JOIN i.tipoIncidencia ti JOIN i.prioridad p JOIN i.estadoIncidencia e JOIN i.tienda t " +
           "WHERE i.estadoIncidencia.idEstadoIncidencia = :idEstado")
    List<Object[]> findByEstado(@Param("idEstado") Integer idEstado);

    /**
     * Incidencias por tienda
     */
    @Query("SELECT i.codigoIncidencia, i.titulo, ti.nombreTipo, p.nombrePrioridad, e.nombreEstado, t.nombreTienda, i.fechaCreacion " +
           "FROM Incidencia i JOIN i.tipoIncidencia ti JOIN i.prioridad p JOIN i.estadoIncidencia e JOIN i.tienda t " +
           "WHERE i.tienda.idTienda = :idTienda")
    List<Object[]> findByTienda(@Param("idTienda") Integer idTienda);

    /**
     * Incidencias por estado y tienda
     */
    @Query("SELECT i.codigoIncidencia, i.titulo, ti.nombreTipo, p.nombrePrioridad, e.nombreEstado, t.nombreTienda, i.fechaCreacion " +
           "FROM Incidencia i JOIN i.tipoIncidencia ti JOIN i.prioridad p JOIN i.estadoIncidencia e JOIN i.tienda t " +
           "WHERE i.estadoIncidencia.idEstadoIncidencia = :idEstado AND i.tienda.idTienda = :idTienda")
    List<Object[]> findByEstadoAndTienda(@Param("idEstado") Integer idEstado,
                                          @Param("idTienda") Integer idTienda);

    /**
     * Resumen de incidencias por estado
     */
    @Query("SELECT e.idEstadoIncidencia, e.nombreEstado, COUNT(i) " +
           "FROM Incidencia i JOIN i.estadoIncidencia e " +
           "GROUP BY e.idEstadoIncidencia, e.nombreEstado")
    List<Object[]> findResumenIncidenciasPorEstado();

    /**
     * Resumen de incidencias por tipo
     */
    @Query("SELECT ti.idTipoIncidencia, ti.nombreTipo, COUNT(i) " +
           "FROM Incidencia i JOIN i.tipoIncidencia ti " +
           "GROUP BY ti.idTipoIncidencia, ti.nombreTipo")
    List<Object[]> findResumenIncidenciasPorTipo();
}