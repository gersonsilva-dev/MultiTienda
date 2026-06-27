package com.techventa.multitienda.supervisor.controller;

import com.techventa.multitienda.admin.model.SolicitudAutorizacion;
import com.techventa.multitienda.admin.model.Usuario;
import com.techventa.multitienda.admin.repository.SolicitudAutorizacionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/supervisor/autorizaciones")
public class SupervisorAutorizacionController {

    @Autowired
    private SolicitudAutorizacionRepository solicitudRepository;

    // ============================================================
    // LISTAR SOLICITUDES PENDIENTES
    // ============================================================
    @GetMapping("/pendientes")
    public ResponseEntity<List<SolicitudAutorizacion>> listarPendientes() {
        List<SolicitudAutorizacion> solicitudes = solicitudRepository
            .findByEstadoSolicitudAndActivoTrue("PENDIENTE");
        return ResponseEntity.ok(solicitudes);
    }

    // ============================================================
    // LISTAR TODAS LAS SOLICITUDES (CON FILTRO)
    // ============================================================
    @GetMapping
    public ResponseEntity<List<SolicitudAutorizacion>> listarTodas(
            @RequestParam(required = false) String estado) {
        
        List<SolicitudAutorizacion> solicitudes;
        if (estado != null && !estado.isEmpty()) {
            solicitudes = solicitudRepository
                .findByEstadoSolicitudAndActivoTrue(estado);
        } else {
            solicitudes = solicitudRepository.findByActivoTrueOrderByFechaCreacionDesc();
        }
        return ResponseEntity.ok(solicitudes);
    }

    // ============================================================
    // APROBAR SOLICITUD
    // ============================================================
    @PutMapping("/{id}/aprobar")
    public ResponseEntity<?> aprobar(
            @PathVariable Integer id,
            @RequestParam Integer idSupervisor) {
        
        SolicitudAutorizacion solicitud = solicitudRepository.findById(id).orElse(null);
        if (solicitud == null) {
            return ResponseEntity.notFound().build();
        }
        
        solicitud.setEstadoSolicitud("APROBADO");
        
        // 🔥 CORRECCIÓN: Crear objeto Usuario con el ID
        Usuario supervisor = new Usuario();
        supervisor.setIdUsuario(idSupervisor);
        solicitud.setSupervisor(supervisor);
        
        solicitud.setFechaAtencion(LocalDateTime.now());
        solicitud.setRespuestaSupervisor("Aprobado por supervisor ID: " + idSupervisor);
        
        solicitudRepository.save(solicitud);
        
        Map<String, String> response = new HashMap<>();
        response.put("mensaje", "Solicitud aprobada correctamente");
        response.put("estado", "APROBADO");
        
        return ResponseEntity.ok(response);
    }

    // ============================================================
    // RECHAZAR SOLICITUD
    // ============================================================
    @PutMapping("/{id}/rechazar")
    public ResponseEntity<?> rechazar(
            @PathVariable Integer id,
            @RequestParam String motivo) {
        
        SolicitudAutorizacion solicitud = solicitudRepository.findById(id).orElse(null);
        if (solicitud == null) {
            return ResponseEntity.notFound().build();
        }
        
        solicitud.setEstadoSolicitud("RECHAZADO");
        solicitud.setFechaAtencion(LocalDateTime.now());
        solicitud.setRespuestaSupervisor(motivo);
        
        solicitudRepository.save(solicitud);
        
        Map<String, String> response = new HashMap<>();
        response.put("mensaje", "Solicitud rechazada correctamente");
        response.put("estado", "RECHAZADO");
        
        return ResponseEntity.ok(response);
    }
}