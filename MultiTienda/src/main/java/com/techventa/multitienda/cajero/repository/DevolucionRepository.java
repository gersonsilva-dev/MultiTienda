package com.techventa.multitienda.cajero.repository;

import com.techventa.multitienda.cajero.model.Devolucion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface DevolucionRepository extends JpaRepository<Devolucion, Integer> {

    Optional<Devolucion> findByCodigoDevolucion(String codigoDevolucion);

    List<Devolucion> findByVenta_IdVenta(Integer idVenta);

    List<Devolucion> findByCliente_IdCliente(Integer idCliente);

    List<Devolucion> findByCajero_IdUsuario(Integer idCajero);

    List<Devolucion> findByEstadoDevolucion_IdEstadoDevolucion(Integer idEstado);

    List<Devolucion> findByActivoTrue();

    boolean existsByCodigoDevolucion(String codigoDevolucion);
}