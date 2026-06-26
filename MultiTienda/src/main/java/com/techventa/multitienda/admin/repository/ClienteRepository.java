package com.techventa.multitienda.admin.repository;

import com.techventa.multitienda.admin.model.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Integer> {

    // Buscar por número de documento (DNI, etc.)
    Optional<Cliente> findByNumeroDocumento(String numeroDocumento);

    // Buscar por RUC (personas jurídicas)
    Optional<Cliente> findByRuc(String ruc);

    // Buscar por correo electrónico
    Optional<Cliente> findByCorreoElectronico(String correoElectronico);

    // Buscar clientes activos
    List<Cliente> findByActivoTrue();

    // Buscar por tipo de cliente (NATURAL o JURIDICO)
    List<Cliente> findByTipoCliente(String tipoCliente);

    // Buscar por estado del cliente
    List<Cliente> findByEstadoCliente_IdEstadoCliente(Integer idEstadoCliente);

    // Buscar por nombre o apellido (contiene)
    List<Cliente> findByNombresContainingIgnoreCaseOrApellidosContainingIgnoreCase(String nombres, String apellidos);

    // Buscar por razón social (contiene)
    List<Cliente> findByRazonSocialContainingIgnoreCase(String razonSocial);

    // Verificar si existe documento
    boolean existsByNumeroDocumento(String numeroDocumento);

    // Verificar si existe RUC
    boolean existsByRuc(String ruc);

    // Verificar si existe correo
    boolean existsByCorreoElectronico(String correoElectronico);
}