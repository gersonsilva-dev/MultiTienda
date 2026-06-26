package com.techventa.multitienda.almacenero.controller;

import com.techventa.multitienda.almacenero.dto.DashboardAlmacenDTO;
import com.techventa.multitienda.almacenero.service.DashboardAlmacenService;
import com.techventa.multitienda.admin.model.Usuario; // Ajusta según tu paquete real

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/dashboard/almacen")
public class DashboardAlmacenController {

    @Autowired
    private DashboardAlmacenService dashboardAlmacenService;

    @GetMapping
    public ResponseEntity<Object> obtenerDatosDashboard(HttpSession session) {
        try {
            // 1. Recuperar el objeto usuario que guardaron en sesión al hacer Login
            Usuario usuarioLogueado = (Usuario) session.getAttribute("usuarioLogueado");

            // Validar si la sesión expiró o no ha iniciado sesión
            if (usuarioLogueado == null) {
                Map<String, String> error = new HashMap<>();
                error.put("error", "Sesión no válida o usuario no autenticado.");
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(error);
            }

            // Validar que el usuario tenga una tienda asociada
            if (usuarioLogueado.getTienda() == null) {
                Map<String, String> error = new HashMap<>();
                error.put("error", "El empleado no tiene una tienda física asignada.");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
            }

            // 2. Extraer el id de la tienda de forma segura desde el backend
            Integer idTienda = usuarioLogueado.getTienda().getIdTienda();

            // 3. Procesar y armar el DTO con los datos de esa tienda específica
            DashboardAlmacenDTO datosDashboard = dashboardAlmacenService.cargarMetricasDashboard(idTienda);

            return ResponseEntity.ok(datosDashboard);

        } catch (Exception e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", "Error interno al procesar los datos del almacén: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
        }
    }
}