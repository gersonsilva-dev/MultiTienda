package com.techventa.multitienda.admin.service;

import com.techventa.multitienda.admin.model.Usuario;
import com.techventa.multitienda.admin.repository.UsuarioRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    // Listar todos los usuarios
    public List<Usuario> listarTodos() {
        return usuarioRepository.findAll();
    }

    // Listar solo usuarios activos
    public List<Usuario> listarActivos() {
        return usuarioRepository.findByActivoTrue();
    }

    // Buscar usuario por ID
    public Optional<Usuario> buscarPorId(Integer id) {
        return usuarioRepository.findById(id);
    }

    // Buscar usuario por correo
    public Optional<Usuario> buscarPorCorreo(String correo) {
        return usuarioRepository.findByCorreoElectronico(correo);
    }

    // Buscar usuario por documento
    public Optional<Usuario> buscarPorDocumento(String documento) {
        return usuarioRepository.findByNumeroDocumento(documento);
    }

    // Buscar usuarios por rol
    public List<Usuario> buscarPorRol(Integer idRol) {
        return usuarioRepository.findByRol_IdRol(idRol);
    }

    // Buscar usuarios por tienda
    public List<Usuario> buscarPorTienda(Integer idTienda) {
        return usuarioRepository.findByTienda_IdTienda(idTienda);
    }

    // Crear nuevo usuario
    public Usuario crear(Usuario usuario) {
        usuario.setActivo(true);
        return usuarioRepository.save(usuario);
    }

    // Actualizar usuario
    public Usuario actualizar(Usuario usuario) {
        return usuarioRepository.save(usuario);
    }

    // Eliminar usuario (borrado lógico)
    public void eliminarLogico(Integer id) {
        Optional<Usuario> usuarioOpt = usuarioRepository.findById(id);
        if (usuarioOpt.isPresent()) {
            Usuario usuario = usuarioOpt.get();
            usuario.setActivo(false);
            usuarioRepository.save(usuario);
        }
    }

    // Eliminar usuario (borrado físico)
    public void eliminarFisico(Integer id) {
        usuarioRepository.deleteById(id);
    }

    // Verificar si existe correo
    public boolean existeCorreo(String correo) {
        return usuarioRepository.existsByCorreoElectronico(correo);
    }

    // Verificar si existe documento
    public boolean existeDocumento(String documento) {
        return usuarioRepository.existsByNumeroDocumento(documento);
    }
}