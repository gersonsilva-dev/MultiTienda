package com.techventa.multitienda.admin.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.techventa.multitienda.admin.model.Usuario;

import java.util.List;
import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {

    Optional<Usuario> findByCorreoElectronico(String correoElectronico);

    Optional<Usuario> findByNumeroDocumento(String numeroDocumento);

    List<Usuario> findByRol_IdRol(Integer idRol);

    List<Usuario> findByTienda_IdTienda(Integer idTienda);

    List<Usuario> findByActivoTrue();

    boolean existsByCorreoElectronico(String correoElectronico);

    boolean existsByNumeroDocumento(String numeroDocumento);
}