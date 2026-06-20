package com.techventa.multitienda;

import com.techventa.multitienda.admin.model.Usuario;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/api/views")
public class ViewFragmentController {

    @GetMapping("/{viewName}")
    public String getFragment(@PathVariable String viewName, HttpSession session, Model model) {
        
        Usuario usuario = (Usuario) session.getAttribute("usuarioLogueado");
        
        if (usuario == null) {
            return "redirect:/login";
        }

        String rol = usuario.getRol().getNombreRol().toLowerCase().trim();
        
        System.out.println("🔍 PETICIÓN: " + viewName + " | ROL: " + rol);

        if (isRestricted(viewName, rol)) {
            return "fragments/error_acceso";
        }

        // === DASHBOARD POR ROL ===
        if (viewName.equals("dashboard")) {
            String dashboardView = "dashboard-" + rol;   // dashboard-administrador
            
            System.out.println("✅ Cargando dashboard específico: fragments/" + dashboardView);
            
            model.addAttribute("usuario", usuario);
            model.addAttribute("userRole", rol);
            
            return "fragments/" + dashboardView;
        }

        // Otras vistas
        model.addAttribute("usuario", usuario);
        model.addAttribute("userRole", rol);
        return "fragments/" + viewName;
    }

    private boolean isRestricted(String view, String rol) {
        if (view.equals("auditoria") && !rol.equals("admin")) return true;
        if (view.equals("configuracion") && !rol.equals("admin")) return true;
        return false;
    }
}