package com.techventa.multitienda.admin.repository;

import com.techventa.multitienda.admin.model.Caja;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface CajaRepository extends JpaRepository<Caja, Integer> {

    // Buscar cajas por tienda
    List<Caja> findByTienda_IdTienda(Integer idTienda);

    // Buscar cajas activas
    List<Caja> findByActivoTrue();

    // Buscar cajas por estado
    List<Caja> findByEstadoCaja_IdEstadoCaja(Integer idEstadoCaja);

    // Buscar caja por número y tienda (único)
    Optional<Caja> findByNumeroCajaAndTienda_IdTienda(Integer numeroCaja, Integer idTienda);

    // Buscar cajas por nombre (contiene)
    List<Caja> findByNombreCajaContainingIgnoreCase(String nombre);

    // Verificar si existe número de caja en una tienda
    boolean existsByNumeroCajaAndTienda_IdTienda(Integer numeroCaja, Integer idTienda);
}