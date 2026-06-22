package com.techventa.multitienda;

import com.techventa.multitienda.admin.model.Usuario;
import com.techventa.multitienda.admin.service.CajaService;
import com.techventa.multitienda.admin.service.TiendaService;
import com.techventa.multitienda.admin.service.TurnoService;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestHeader;

@Controller
@RequestMapping("/views")
public class ViewFragmentController {

    @Autowired
    private TiendaService tiendaService;

    @Autowired
    private CajaService cajaService;

    @Autowired
    private TurnoService turnoService;

    @GetMapping("/{fragmento}")
    public String mostrarFragmento(@PathVariable String fragmento,
                                   HttpSession session,
                                   Model model,
                                   @RequestHeader(value = "X-Requested-With", required = false) String requestedWith) {
        Usuario usuario = (Usuario) session.getAttribute("usuarioLogueado");
        if (usuario == null) {
            return "redirect:/login";
        }

        // 🔥 SIEMPRE PASAR EL USUARIO Y VIEWNAME
        model.addAttribute("viewName", fragmento);
        model.addAttribute("usuario", usuario);

        if ("apertura".equals(fragmento)) {
            model.addAttribute("tiendas", tiendaService.listarTodas());
            model.addAttribute("cajas", cajaService.listarTodas());
            model.addAttribute("turnos", turnoService.listarTodos());
        }

        // 🔥 PARA AJAX, DEVOLVER EL FRAGMENTO CON LAS VARIABLES
        if ("XMLHttpRequest".equals(requestedWith)) {
            return "fragments/" + fragmento + " :: content";
        }

        // PARA PETICIÓN NORMAL, DEVOLVER EL LAYOUT COMPLETO
        return "layout-pos";
    }
}