package com.techventa.multitienda;

import com.techventa.multitienda.admin.model.Usuario;
import com.techventa.multitienda.admin.service.CajaService;
import com.techventa.multitienda.admin.service.TiendaService;
import com.techventa.multitienda.almacenero.service.KardexService;
import com.techventa.multitienda.cajero.service.TurnoCajaService;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/api/views")
public class ViewFragmentController {

	  @Autowired
	    private KardexService kardexService;

    @Autowired
    private TiendaService tiendaService;
    
    @Autowired
    private CajaService cajaService;
    
    @Autowired
    private TurnoCajaService turnoCajaService;

    @GetMapping("/{viewName}")
    public String getFragment(@PathVariable String viewName,
    		  @RequestParam(required = false) String search,
    		  @RequestParam(required = false) Integer tipoMovId,
    		  @RequestParam(required = false, defaultValue = "0") int page,
    		HttpSession session, 
    		Model model) {
        
        Usuario usuario = (Usuario) session.getAttribute("usuarioLogueado");
        
        if (usuario == null) {
            return "redirect:/login";
        }

   String rol = usuario.getRol().getNombreRol().toLowerCase().trim();
        
        System.out.println("🔍 PETICIÓN: " + viewName + " | ROL: " + rol + 
                          " | search=" + search + " | tipoMovId=" + tipoMovId + " | page=" + page);


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
        
        // ✅ CORREGIDO: Kardex almacenero - ahora usa los parámetros
        if (viewName.equals("kardex")) {
            try {
                model.addAttribute("resumen", kardexService.getResumenMensual());
                model.addAttribute("productos", kardexService.listarProductosParaFiltro());
                model.addAttribute("tiposMovimiento", kardexService.listarTiposMovimiento());
                
                // ✅ AHORA PASA LOS PARÁMETROS REALES
                model.addAttribute("movimientos", kardexService.buscarMovimientos(
                    search != null && !search.trim().isEmpty() ? search : null,
                    tipoMovId,
                    page,
                    50
                ));
                
                // ✅ AGREGAR PARA QUE EL HTML LOS USE
                model.addAttribute("search", search);
                model.addAttribute("tipoMovId", tipoMovId);
                model.addAttribute("currentPage", page);
                
                System.out.println("✅ Kardex cargado con filtros: search=" + search + 
                                 ", tipoMovId=" + tipoMovId + ", page=" + page);
            } catch (Exception e) {
                System.err.println("❌ Error al cargar kardex: " + e.getMessage());
                e.printStackTrace();
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