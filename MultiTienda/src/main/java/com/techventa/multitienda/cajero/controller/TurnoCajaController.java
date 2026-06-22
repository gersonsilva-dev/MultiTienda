package com.techventa.multitienda.cajero.controller;

import com.techventa.multitienda.admin.model.Usuario;
import com.techventa.multitienda.cajero.model.TurnoCaja;
import com.techventa.multitienda.cajero.service.TurnoCajaService;

import jakarta.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/turnos-caja")
public class TurnoCajaController {

    @Autowired
    private TurnoCajaService turnoCajaService;

    @GetMapping
    public ResponseEntity<List<TurnoCaja>> listarTodos() {
        return ResponseEntity.ok(turnoCajaService.listarTodos());
    }

    @GetMapping("/cajero/{idCajero}")
    public ResponseEntity<List<TurnoCaja>> listarPorCajero(@PathVariable Integer idCajero) {
        return ResponseEntity.ok(turnoCajaService.listarPorCajero(idCajero));
    }

    @GetMapping("/tienda/{idTienda}")
    public ResponseEntity<List<TurnoCaja>> listarPorTienda(@PathVariable Integer idTienda) {
        return ResponseEntity.ok(turnoCajaService.listarPorTienda(idTienda));
    }

    @GetMapping("/caja/{idCaja}")
    public ResponseEntity<List<TurnoCaja>> listarPorCaja(@PathVariable Integer idCaja) {
        return ResponseEntity.ok(turnoCajaService.listarPorCaja(idCaja));
    }

    @GetMapping("/activos")
    public ResponseEntity<List<TurnoCaja>> listarActivos() {
        return ResponseEntity.ok(turnoCajaService.listarActivos());
    }

    @GetMapping("/caja/{idCaja}/activo")
    public ResponseEntity<TurnoCaja> buscarActivoPorCaja(@PathVariable Integer idCaja) {
        return turnoCajaService.buscarActivoPorCaja(idCaja)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/{id}")
    public ResponseEntity<TurnoCaja> buscarPorId(@PathVariable Integer id) {
        return turnoCajaService.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping("/abrir")
    public ResponseEntity<Object> abrirTurno(@RequestBody Map<String, Object> requestData,
                                              HttpSession session) {
        try {
            // 1️⃣ Obtener usuario de la sesión
            Usuario usuario = (Usuario) session.getAttribute("usuarioLogueado");
            if (usuario == null) {
                Map<String, String> error = new HashMap<>();
                error.put("error", "Usuario no autenticado");
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(error);
            }

            // 2️⃣ Validar que el usuario tenga tienda
            if (usuario.getTienda() == null) {
                Map<String, String> error = new HashMap<>();
                error.put("error", "El usuario no tiene una tienda asignada");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
            }

            // 3️⃣ Validar que el usuario tenga caja
            if (usuario.getCaja() == null) {
                Map<String, String> error = new HashMap<>();
                error.put("error", "El usuario no tiene una caja asignada");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
            }

            // 4️⃣ Obtener el fondo inicial del request
            BigDecimal fondoInicial;
            if (requestData.get("fondoInicial") instanceof Integer) {
                fondoInicial = BigDecimal.valueOf((Integer) requestData.get("fondoInicial"));
            } else if (requestData.get("fondoInicial") instanceof Double) {
                fondoInicial = BigDecimal.valueOf((Double) requestData.get("fondoInicial"));
            } else {
                fondoInicial = new BigDecimal(requestData.get("fondoInicial").toString());
            }

            // 5️⃣ Crear el turno automáticamente con los datos del usuario
            TurnoCaja turnoCaja = new TurnoCaja();
            turnoCaja.setCajero(usuario);
            turnoCaja.setTienda(usuario.getTienda());
            turnoCaja.setCaja(usuario.getCaja());
            turnoCaja.setFondoInicial(fondoInicial);

            // 6️⃣ Abrir el turno
            TurnoCaja nuevo = turnoCajaService.abrirTurno(turnoCaja);

            // 7️⃣ Respuesta exitosa
            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("message", "Turno abierto correctamente");
            response.put("idTurnoCaja", nuevo.getIdTurnoCaja());
            return ResponseEntity.status(HttpStatus.CREATED).body(response);

        } catch (Exception e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", "Error al abrir turno: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
        }
    }

    @PutMapping("/{id}/cerrar")
    public ResponseEntity<Object> cerrarTurno(
            @PathVariable Integer id,
            @RequestParam BigDecimal fondoFinal,
            @RequestParam(required = false) String observaciones) {
        try {
            TurnoCaja cerrado = turnoCajaService.cerrarTurno(id, fondoFinal, observaciones);
            if (cerrado == null) {
                return ResponseEntity.notFound().build();
            }
            Map<String, String> response = new HashMap<>();
            response.put("mensaje", "Turno cerrado correctamente");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", "Error al cerrar turno: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
        }
    }
    // En TurnoCajaController.java
    @GetMapping("/usuario/{idUsuario}/activo")
    public ResponseEntity<Map<String, Object>> buscarActivoPorUsuario(@PathVariable Integer idUsuario) {
        Map<String, Object> response = new HashMap<>();
        Optional<TurnoCaja> turno = turnoCajaService.buscarActivoPorUsuario(idUsuario);
        
        if (turno.isPresent()) {
            response.put("activo", true);
            response.put("turno", turno.get());
            return ResponseEntity.ok(response);
        } else {
            response.put("activo", false);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }
}