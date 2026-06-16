package com.techventa.multitienda.admin.repository;

import com.techventa.multitienda.admin.model.Oferta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface OfertaRepository extends JpaRepository<Oferta, Integer> {

    // Buscar ofertas activas
    List<Oferta> findByActivoTrue();

    // Buscar ofertas por estado
    List<Oferta> findByEstadoOferta_IdEstadoOferta(Integer idEstadoOferta);

    // Buscar ofertas por nombre (contiene)
    List<Oferta> findByNombreOfertaContainingIgnoreCase(String nombre);

    // Buscar ofertas vigentes (fecha actual entre fecha_inicio y fecha_fin)
    List<Oferta> findByFechaInicioBeforeAndFechaFinAfter(LocalDateTime ahora, LocalDateTime ahora2);

    // Buscar ofertas que empiezan en un rango de fechas
    List<Oferta> findByFechaInicioBetween(LocalDateTime inicio, LocalDateTime fin);

    // Buscar ofertas que terminan en un rango de fechas
    List<Oferta> findByFechaFinBetween(LocalDateTime inicio, LocalDateTime fin);

    // Buscar ofertas por tipo de descuento
    List<Oferta> findByTipoDescuento_IdTipoDescuento(Integer idTipoDescuento);

    // Buscar ofertas por prioridad
    List<Oferta> findByPrioridad(Integer prioridad);

    // Buscar ofertas que aplican a todas las tiendas
    List<Oferta> findByAplicaTodasTiendasTrue();

    // Buscar ofertas creadas por un usuario
    List<Oferta> findByUsuarioCrea(Integer usuarioCrea);

    // Buscar ofertas aprobadas por un usuario
    List<Oferta> findByUsuarioAprueba(Integer usuarioAprueba);

    // Verificar si existe una oferta con el mismo nombre
    boolean existsByNombreOferta(String nombreOferta);
}