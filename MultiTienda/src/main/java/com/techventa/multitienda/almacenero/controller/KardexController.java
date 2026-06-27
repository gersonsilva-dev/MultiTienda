package com.techventa.multitienda.almacenero.controller;

import com.techventa.multitienda.almacenero.service.KardexService;
import com.techventa.multitienda.admin.model.Usuario;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/almacenero")
public class KardexController {

    @Autowired
    private KardexService kardexService;

    @GetMapping("/kardex")
    public String mostrarKardex(
            HttpSession session,
            Model model,
            @RequestParam(required = false) String search,           // ← Nuevo: texto de búsqueda
            @RequestParam(required = false) Integer tipoMovId,
            @RequestParam(defaultValue = "0") int page) {

        Usuario usuario = (Usuario) session.getAttribute("usuarioLogueado");
        if (usuario == null) return "redirect:/login";

        String rol = usuario.getRol().getNombreRol().toLowerCase().trim();
        if (!"almacenero".equals(rol) && !"supervisor".equals(rol) && !"administrador".equals(rol)) {
            return "redirect:/dashboard";
        }

        model.addAttribute("usuario", usuario);
        model.addAttribute("userRole", rol);
        model.addAttribute("resumen", kardexService.getResumenMensual());
        model.addAttribute("tiposMovimiento", kardexService.listarTiposMovimiento());
        model.addAttribute("search", search);        // ← Para mantener el valor en el input
        model.addAttribute("tipoMovId", tipoMovId);
        model.addAttribute("currentPage", page);

        // Llamada al nuevo método de búsqueda
        model.addAttribute("movimientos", kardexService.buscarMovimientos(search, tipoMovId, page, 50));

        return "fragments/almacenero/kardex";
    }
}
