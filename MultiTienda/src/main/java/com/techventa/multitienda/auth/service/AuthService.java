package com.techventa.multitienda.auth.service;

import com.techventa.multitienda.admin.model.Usuario;
import com.techventa.multitienda.admin.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    public Usuario login(String correo, String password) {
        System.out.println("🔐 Intento de login: " + correo);
        
        Usuario usuario = usuarioRepository.findByCorreoElectronico(correo).orElse(null);
        
        if (usuario == null) {
            System.out.println("❌ Usuario no encontrado: " + correo);
            return null;
        }
        
        System.out.println("🔐 Hash guardado: " + usuario.getContrasenaHash());
        System.out.println("🔐 Contraseña ingresada: " + password);
        
        boolean coincide = passwordEncoder.matches(password, usuario.getContrasenaHash());
        System.out.println("🔐 ¿Coinciden? " + coincide);
        
        if (!coincide) {
            System.out.println("❌ Contraseña incorrecta para: " + correo);
            return null;
        }

        // 🔥 Validar estado del usuario (manejando null)
        Integer idEstado = usuario.getIdEstadoUsuario();
        if (idEstado == null || idEstado != 1 || !Boolean.TRUE.equals(usuario.getActivo())) {
            System.out.println("❌ Usuario inactivo o bloqueado: " + correo);
            return null;
        }

        System.out.println("✅ Login exitoso: " + correo);
        return usuario;
    }
}