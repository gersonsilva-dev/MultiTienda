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
        // 1. Buscamos el usuario por correo
        Usuario usuario = usuarioRepository
                .findByCorreoElectronico(correo)
                .orElse(null);

        // 2. Validación de existencia y contraseña
        // Es fundamental verificar la contraseña ANTES de cualquier otra cosa
        // para prevenir ataques de enumeración de usuarios.
        if (usuario == null || !passwordEncoder.matches(password, usuario.getContrasenaHash())) {
            return null;
        }

        // 3. Validación de restricciones de negocio (las que estaban en tu SP)
        // id_estado_usuario = 1 (ACTIVO) y activo = 1 (Verdadero)
        boolean esEstadoValido = (usuario.getIdEstadoUsuario() != null && usuario.getIdEstadoUsuario() == 1);
        boolean esActivo = Boolean.TRUE.equals(usuario.getActivo());

        if (!esEstadoValido || !esActivo) {
            // Aquí podrías incluso lanzar una excepción personalizada si quisieras 
            // distinguir entre "Credenciales incorrectas" y "Cuenta suspendida"
            return null;
        }

        return usuario;
    }
}