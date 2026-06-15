package com.techventa.multitienda.admin.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.techventa.multitienda.admin.model.Tienda;

import java.util.List;
import java.util.Optional;

@Repository
public interface TiendaRepository extends JpaRepository<Tienda, Integer> {

    // Buscar por código de tienda (único)
    Optional<Tienda> findByCodigoTienda(String codigoTienda);

    // Buscar tiendas activas
    List<Tienda> findByActivoTrue();

    // Buscar por nombre (contiene)
    List<Tienda> findByNombreTiendaContainingIgnoreCase(String nombre);

    // Buscar por estado
    List<Tienda> findByIdEstadoTienda(Integer idEstadoTienda);

    // Verificar si existe código de tienda
    boolean existsByCodigoTienda(String codigoTienda);
}