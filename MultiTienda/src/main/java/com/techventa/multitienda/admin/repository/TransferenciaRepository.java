package com.techventa.multitienda.admin.repository;

import com.techventa.multitienda.admin.model.Transferencia;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface TransferenciaRepository extends JpaRepository<Transferencia, Integer> {
    Optional<Transferencia> findByCodigoTransferencia(String codigoTransferencia);
    List<Transferencia> findByTiendaOrigen_IdTienda(Integer idTiendaOrigen);
    List<Transferencia> findByTiendaDestino_IdTienda(Integer idTiendaDestino);
    List<Transferencia> findByEstadoTransferencia_IdEstadoTransferencia(Integer idEstado);
    List<Transferencia> findByAlmaceneroSolicita_IdUsuario(Integer idAlmacenero);
    List<Transferencia> findByActivoTrue();
}