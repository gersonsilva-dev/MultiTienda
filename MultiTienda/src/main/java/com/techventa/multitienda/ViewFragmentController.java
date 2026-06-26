package com.techventa.multitienda;

import com.techventa.multitienda.admin.model.Usuario;
import com.techventa.multitienda.admin.service.CajaService;
import com.techventa.multitienda.admin.service.TiendaService;
import com.techventa.multitienda.cajero.service.TurnoCajaService;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/api/views")
public class ViewFragmentController {

    @Autowired
    private TiendaService tiendaService;
    
    @Autowired
    private CajaService cajaService;
    
    @Autowired
    private TurnoCajaService turnoCajaService;

    @GetMapping("/{viewName}")
    public String getFragment(@PathVariable String viewName, HttpSession session, Model model) {
        
        Usuario usuario = (Usuario) session.getAttribute("usuarioLogueado");
        
        if (usuario == null) {
            return "redirect:/login";
        }

        String rol = usuario.getRol().getNombreRol().toLowerCase().trim();
        
        System.out.println("🔍 PETICIÓN: " + viewName + " | ROL: " + rol);

        // ============================================================
        // AGREGAR DATOS AL MODELO
        // ============================================================
        model.addAttribute("usuario", usuario);
        model.addAttribute("userRole", rol);
        model.addAttribute("userRol", usuario.getRol().getNombreRol());

        // ============================================================
        // DATOS ESPECÍFICOS PARA CADA VISTA
        // ============================================================
        
        if (viewName.equals("apertura")) {
            model.addAttribute("tiendas", tiendaService.listarActivas());
            model.addAttribute("cajas", cajaService.listarActivas());
        }

        if (viewName.equals("cierre") || viewName.equals("misventas")) {
            try {
                var cajas = cajaService.buscarPorTienda(usuario.getTienda().getIdTienda());
                if (!cajas.isEmpty()) {
                    var turnoOpt = turnoCajaService.buscarActivoPorCaja(cajas.get(0).getIdCaja());
                    model.addAttribute("turno", turnoOpt.orElse(null));
                }
            } catch (Exception e) {
                model.addAttribute("turno", null);
            }
        }

        // ============================================================
        // CONSTRUIR RUTA SEGÚN ROL (NUEVA ESTRUCTURA)
        // ============================================================
        String path = "fragments/" + rol + "/" + viewName;
        
        System.out.println("📂 RUTA: " + path);

        // ============================================================
        // RETORNAR EL LAYOUT SEGÚN EL ROL
        // ============================================================
        // Si es cajero → usa layout-pos.html (sin sidebar)
        if ("cajero".equals(rol)) {
            model.addAttribute("content", path);
            return "layout-pos";
        }

        // Para otros roles → usa el fragmento solo
        return path;
    }
}