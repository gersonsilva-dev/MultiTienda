package com.techventa.multitienda.auth.controller;

import com.techventa.multitienda.admin.model.Usuario;
import com.techventa.multitienda.auth.service.AuthService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class AuthController {

    private final AuthService authService;

    // Inyección de dependencias manual (sin Lombok)
    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @GetMapping({"/", "/login"})
    public String index() {
        return "login";
    }

    @PostMapping("/login")
    public String login(
            @RequestParam String email, // Asegúrate de que el name en tu HTML sea "email"
            @RequestParam String password,
            Model model) {

        Usuario usuario = authService.login(email, password);

        if (usuario == null) {
            model.addAttribute("error", "Correo o contraseña incorrectos");
            return "login";
        }

        return "home"; // Vista a la que rediriges tras éxito
    }
}