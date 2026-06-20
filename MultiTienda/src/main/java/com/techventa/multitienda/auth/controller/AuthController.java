package com.techventa.multitienda.auth.controller;

import com.techventa.multitienda.admin.model.Usuario;
import com.techventa.multitienda.auth.service.AuthService;

import jakarta.servlet.http.HttpSession;

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
            @RequestParam String email,
            @RequestParam String password,
            HttpSession session, // Inyectamos la sesión aquí
            Model model) {

        Usuario usuario = authService.login(email, password);

        if (usuario == null) {
            model.addAttribute("error", "Correo o contraseña incorrectos");
            return "login";
        }

        session.setAttribute("usuarioLogueado", usuario);

        return "redirect:/dashboard"; // Usamos REDIRECT para limpiar la URL
    }
    
    @GetMapping("/dashboard")
    public String dashboard(HttpSession session, Model model) {
        Usuario usuario = (Usuario) session.getAttribute("usuarioLogueado");
        
        // Si no hay usuario en sesión, obligar a ir al login
        if (usuario == null) {
            return "redirect:/login";
        }
        
        // Ahora sí podemos acceder al rol sin miedo a que sea null
        model.addAttribute("userRole", usuario.getRol().getNombreRol().toLowerCase());
        return "dashboard"; 
    }
}