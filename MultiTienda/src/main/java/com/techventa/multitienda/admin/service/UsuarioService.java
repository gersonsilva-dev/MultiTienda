package com.techventa.multitienda.admin.service;

import com.techventa.multitienda.admin.model.Usuario;
import com.techventa.multitienda.admin.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

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

    // ============================================================
    // CREAR USUARIO (CON CONTRASEÑA ENCRIPTADA)
    // ============================================================
    public Usuario crear(Usuario usuario, String contrasena) {
        String hash = passwordEncoder.encode(contrasena);
        usuario.setContrasenaHash(hash);
        usuario.setActivo(true);
        usuario.setIdEstadoUsuario(1);  // ACTIVO
        return usuarioRepository.save(usuario);
    }

    // ============================================================
    // ACTUALIZAR USUARIO (CON CONTRASEÑA ENCRIPTADA)
    // ============================================================
    public Usuario actualizar(Usuario usuario, String contrasena) {
        if (contrasena != null && !contrasena.isEmpty()) {
            String hash = passwordEncoder.encode(contrasena);
            usuario.setContrasenaHash(hash);
        }
        return usuarioRepository.save(usuario);
    }

    // ============================================================
    // ACTUALIZAR USUARIO (SIN CAMBIAR CONTRASEÑA)
    // ============================================================
    public Usuario actualizar(Usuario usuario) {
        return usuarioRepository.save(usuario);
    }

    public Usuario obtenerUsuarioActual() {
        return usuarioRepository.findById(3).orElse(null);
    }

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