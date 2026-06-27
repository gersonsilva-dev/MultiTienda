package com.techventa.multitienda.admin.repository;

import com.techventa.multitienda.admin.model.Oferta;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface OfertaRepository extends JpaRepository<Oferta, Integer> {

    List<Oferta> findByActivoTrue();

    List<Oferta> findByEstadoOferta_IdEstadoOferta(Integer idEstadoOferta);

    List<Oferta> findByNombreOfertaContainingIgnoreCase(String nombre);

    List<Oferta> findByFechaInicioBeforeAndFechaFinAfter(LocalDateTime ahora, LocalDateTime ahora2);

    List<Oferta> findByFechaInicioBetween(LocalDateTime inicio, LocalDateTime fin);

    List<Oferta> findByFechaFinBetween(LocalDateTime inicio, LocalDateTime fin);

    List<Oferta> findByTipoDescuento_IdTipoDescuento(Integer idTipoDescuento);

    List<Oferta> findByPrioridad(Integer prioridad);

    List<Oferta> findByAplicaTodasTiendasTrue();

    List<Oferta> findByUsuarioCrea(Integer usuarioCrea);

    List<Oferta> findByUsuarioAprueba(Integer usuarioAprueba);

    boolean existsByNombreOferta(String nombreOferta);

    // 🔥 Este es el que usa el cajero
    List<Oferta> findByEstadoOferta_IdEstadoOfertaAndActivoTrue(Integer idEstadoOferta);
}