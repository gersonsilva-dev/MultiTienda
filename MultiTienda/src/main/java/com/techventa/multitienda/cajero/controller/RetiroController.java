package com.techventa.multitienda.cajero.controller;

import com.techventa.multitienda.cajero.model.RetiroCaja;
import com.techventa.multitienda.cajero.model.TurnoCaja;
import com.techventa.multitienda.cajero.service.RetiroService;
import com.techventa.multitienda.cajero.service.TurnoCajaService;
import com.techventa.multitienda.admin.model.Usuario;
import com.techventa.multitienda.admin.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/retiros")
public class RetiroController {

    @Autowired
    private RetiroService retiroService;

    @Autowired
    private TurnoCajaService turnoCajaService;

    @Autowired
    private UsuarioService usuarioService;

    @PostMapping
    public ResponseEntity<Object> registrarRetiro(@RequestBody Map<String, Object> request) {
        try {
            BigDecimal monto = new BigDecimal(request.get("monto").toString());
            String motivo = request.get("motivo").toString();
            String observaciones = request.get("observaciones") != null ? request.get("observaciones").toString() : "";

            // Obtener usuario actual (ID 3 fijo)
            Usuario usuario = usuarioService.buscarPorId(3).orElse(null);
            if (usuario == null) {
                Map<String, String> error = new HashMap<>();
                error.put("error", "Usuario no encontrado");
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
            }

            // Obtener turno activo del cajero
            Optional<TurnoCaja> turnoOpt = turnoCajaService.buscarActivoPorUsuario(3);
            if (turnoOpt.isEmpty()) {
                Map<String, String> error = new HashMap<>();
                error.put("error", "No hay turno de caja activo para este usuario");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
            }
            
            TurnoCaja turno = turnoOpt.get();

            // Calcular efectivo actual: fondoInicial + totalVentasEfectivo
            BigDecimal efectivoActual = turno.getFondoInicial()
                    .add(turno.getTotalVentasEfectivo() != null ? turno.getTotalVentasEfectivo() : BigDecimal.ZERO);

            // Verificar que el monto no sea mayor al efectivo actual
            if (monto.compareTo(efectivoActual) > 0) {
                Map<String, String> error = new HashMap<>();
                error.put("error", "No hay suficiente efectivo en caja. Disponible: S/ " + efectivoActual);
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
            }

            // Crear retiro
            RetiroCaja retiro = new RetiroCaja();
            retiro.setTurnoCaja(turno);
            retiro.setMonto(monto);
            retiro.setMotivo(motivo);
            retiro.setObservaciones(observaciones);
            retiro.setFechaRetiro(LocalDateTime.now());
            retiro.setEstadoAutorizacion("APROBADO");
            retiro.setActivo(true);

            RetiroCaja guardado = retiroService.guardar(retiro);

            // 🔥 SOLO RESTAR EL MONTO DEL totalVentasEfectivo - SIN CERRAR EL TURNO
            BigDecimal nuevoTotalEfectivo = turno.getTotalVentasEfectivo()
                    .subtract(monto);
            turno.setTotalVentasEfectivo(nuevoTotalEfectivo);
            
            // 🔥 GUARDAR EL TURNO ACTUALIZADO (sin cerrarlo)
            turnoCajaService.actualizarTurno(turno);

            Map<String, Object> response = new HashMap<>();
            response.put("mensaje", "Retiro registrado correctamente");
            response.put("retiro", guardado);
            response.put("efectivoActual", efectivoActual.subtract(monto));

            return ResponseEntity.ok(response);

        } catch (Exception e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
        }
    }

    @GetMapping("/historial")
    public ResponseEntity<Object> obtenerHistorialRetiros() {
        try {
            Optional<TurnoCaja> turnoOpt = turnoCajaService.buscarActivoPorUsuario(3);
            if (turnoOpt.isEmpty()) {
                Map<String, String> error = new HashMap<>();
                error.put("error", "No hay turno de caja activo");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
            }

            return ResponseEntity.ok(retiroService.obtenerHistorialRetiros(turnoOpt.get().getIdTurnoCaja()));
        } catch (Exception e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
        }
    }
}