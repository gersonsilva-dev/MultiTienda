package com.techventa.multitienda.auth.service;

import com.techventa.multitienda.admin.model.Usuario;
import com.techventa.multitienda.admin.repository.UsuarioRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final UsuarioRepository usuarioRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    // Inyección de dependencias manual
    public AuthService(UsuarioRepository usuarioRepository, BCryptPasswordEncoder passwordEncoder) {
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public Usuario login(String correo, String password) {
        Usuario usuario = usuarioRepository
                .findByCorreoElectronico(correo)
                .orElse(null);

        if (usuario == null || !passwordEncoder.matches(password, usuario.getContrasenaHash())) {
            return null;
        }

        return usuario;
    }
}