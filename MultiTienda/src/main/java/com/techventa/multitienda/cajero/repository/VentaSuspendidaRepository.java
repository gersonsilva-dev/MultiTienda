package com.techventa.multitienda.cajero.repository;

import com.techventa.multitienda.cajero.model.VentaSuspendida;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface VentaSuspendidaRepository extends JpaRepository<VentaSuspendida, Integer> {

    Optional<VentaSuspendida> findByCodigoSuspension(String codigoSuspension);

    List<VentaSuspendida> findByCajero_IdUsuarioAndEstado(Integer idCajero, String estado);

    List<VentaSuspendida> findByTienda_IdTienda(Integer idTienda);

    List<VentaSuspendida> findByEstado(String estado);
}