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

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @GetMapping({"/", "/login"})
    public String index() {
        return "login";
    }

    @PostMapping("/login")
    public String login(@RequestParam String email,
                        @RequestParam String password,
                        HttpSession session,
                        Model model) {

        Usuario usuario = authService.login(email, password);
        if (usuario == null) {
            model.addAttribute("error", "Correo o contraseña incorrectos");
            return "login";
        }

        session.setAttribute("usuarioLogueado", usuario);

        String rol = usuario.getRol().getNombreRol().toLowerCase();
        
        if ("cajero".equals(rol)) {
            return "redirect:/views/apertura";   // ✅ SIN /api
        }
        
        return "redirect:/dashboard";
    }
    
    @GetMapping("/dashboard")
    public String dashboard(HttpSession session, Model model) {
        Usuario usuario = (Usuario) session.getAttribute("usuarioLogueado");
        if (usuario == null) {
            return "redirect:/login";
        }
        model.addAttribute("userRole", usuario.getRol().getNombreRol().toLowerCase());
        return "dashboard"; 
    }
}