package com.techventa.multitienda.admin.controller;

import com.techventa.multitienda.admin.model.Producto;
import com.techventa.multitienda.admin.model.Usuario;
import com.techventa.multitienda.admin.model.Merma;
import com.techventa.multitienda.admin.service.MermaService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/mermas")
public class MermaController {

    @Autowired
    private MermaService mermaService;

    @GetMapping("/buscar-producto")
    public ResponseEntity<List<Map<String, Object>>> buscarProducto(@RequestParam String search) {
        List<Producto> productos = mermaService.buscarProductosParaAutocompletado(search);
        
        List<Map<String, Object>> resultado = productos.stream()
                .map(p -> {
                    Map<String, Object> map = new HashMap<>();
                    map.put("id", p.getIdProducto());
                    map.put("nombre", p.getNombreProducto());
                    map.put("codigoBarras", p.getCodigoBarras());
                    return map;
                })
                .collect(Collectors.toList());

        return ResponseEntity.ok(resultado);
    }

    @PostMapping("/registrar")
    public ResponseEntity<?> registrarMerma(
            @RequestBody Map<String, Object> request,
            HttpSession session) {

        // ✅ CAMBIO: Devolver JSON en lugar de texto plano
        Usuario usuario = (Usuario) session.getAttribute("usuarioLogueado");
        if (usuario == null) {
            Map<String, Object> error = new HashMap<>();
            error.put("success", false);
            error.put("message", "No autenticado. Por favor inicia sesión nuevamente.");
            error.put("redirect", "/login");
            return ResponseEntity.status(401).body(error);
        }

        try {
            Integer idProducto = Integer.parseInt(request.get("idProducto").toString());
            Integer idTienda = usuario.getTienda() != null ? usuario.getTienda().getIdTienda() : null;
            
            if (idTienda == null) {
                Map<String, Object> error = new HashMap<>();
                error.put("success", false);
                error.put("message", "El usuario no tiene una tienda asignada.");
                return ResponseEntity.badRequest().body(error);
            }
            
            Integer cantidad = Integer.parseInt(request.get("cantidad").toString());
            Integer idMotivoMerma = Integer.parseInt(request.get("idMotivoMerma").toString());
            String observaciones = request.get("observaciones") != null ? request.get("observaciones").toString() : "";

            Merma merma = mermaService.registrarMermaDesdeFormulario(
                    idProducto,
                    idTienda,
                    cantidad,
                    idMotivoMerma,
                    observaciones,
                    usuario
            );

            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("message", "Merma registrada correctamente");
            response.put("idMerma", merma.getIdMerma());

            return ResponseEntity.ok(response);

        } catch (NumberFormatException e) {
            Map<String, Object> error = new HashMap<>();
            error.put("success", false);
            error.put("message", "Datos inválidos. Revisa los campos numéricos.");
            return ResponseEntity.badRequest().body(error);
            
        } catch (Exception e) {
            e.printStackTrace();
            Map<String, Object> error = new HashMap<>();
            error.put("success", false);
            error.put("message", "Error al registrar merma: " + e.getMessage());
            return ResponseEntity.badRequest().body(error);
        }
    }
}