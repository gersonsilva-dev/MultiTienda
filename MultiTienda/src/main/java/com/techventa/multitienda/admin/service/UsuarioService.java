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

    public List<Usuario> listarTodos() {
        return usuarioRepository.findAll();
    }

    public List<Usuario> listarActivos() {
        return usuarioRepository.findByActivoTrue();
    }

    public Optional<Usuario> buscarPorId(Integer id) {
        return usuarioRepository.findById(id);
    }

    public Optional<Usuario> buscarPorCorreo(String correo) {
        return usuarioRepository.findByCorreoElectronico(correo);
    }

    public Optional<Usuario> buscarPorDocumento(String documento) {
        return usuarioRepository.findByNumeroDocumento(documento);
    }

    public List<Usuario> buscarPorRol(Integer idRol) {
        return usuarioRepository.findByRol_IdRol(idRol);
    }

    public List<Usuario> buscarPorTienda(Integer idTienda) {
        return usuarioRepository.findByTienda_IdTienda(idTienda);
    }

    public Usuario crear(Usuario usuario) {
        usuario.setActivo(true);
        return usuarioRepository.save(usuario);
    }

    public Usuario actualizar(Usuario usuario) {
        return usuarioRepository.save(usuario);
    }

    // ============================================================
    // ELIMINAR FÍSICAMENTE
    // ============================================================
    public void eliminarFisico(Integer id) {
        usuarioRepository.deleteById(id);
    }

    public boolean existeCorreo(String correo) {
        return usuarioRepository.existsByCorreoElectronico(correo);
    }

    public boolean existeDocumento(String documento) {
        return usuarioRepository.existsByNumeroDocumento(documento);
    }
}