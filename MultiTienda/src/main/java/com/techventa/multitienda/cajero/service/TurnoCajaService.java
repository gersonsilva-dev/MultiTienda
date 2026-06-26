package com.techventa.multitienda.cajero.service;

import com.techventa.multitienda.admin.model.Usuario;
import com.techventa.multitienda.cajero.model.TurnoCaja;
import com.techventa.multitienda.cajero.repository.TurnoCajaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class TurnoCajaService {

    @Autowired
    private TurnoCajaRepository turnoCajaRepository;

    public List<TurnoCaja> listarTodos() { 
        return turnoCajaRepository.findAll(); 
    }
    
    public List<TurnoCaja> listarPorCajero(Integer idCajero) { 
        return turnoCajaRepository.findByCajero_IdUsuario(idCajero); 
    }
    
    public List<TurnoCaja> listarPorTienda(Integer idTienda) { 
        return turnoCajaRepository.findByTienda_IdTienda(idTienda); 
    }
    
    public List<TurnoCaja> listarPorCaja(Integer idCaja) { 
        return turnoCajaRepository.findByCaja_IdCaja(idCaja); 
    }
    
    public List<TurnoCaja> listarActivos() { 
        return turnoCajaRepository.findByEstadoTurno("ACTIVO"); 
    }
    
    public Optional<TurnoCaja> buscarPorId(Integer id) { 
        return turnoCajaRepository.findById(id); 
    }
    
    public Optional<TurnoCaja> buscarActivoPorCaja(Integer idCaja) { 
        return turnoCajaRepository.findByCaja_IdCajaAndEstadoTurno(idCaja, "ACTIVO"); 
    }

    public Optional<TurnoCaja> buscarActivoPorUsuario(Integer idUsuario) {
        return turnoCajaRepository.findByCajero_IdUsuarioAndEstadoTurno(idUsuario, "ACTIVO");
    }

    public TurnoCaja abrirTurno(TurnoCaja turnoCaja) {
        // 1️⃣ Validar que el cajero tenga una caja asignada
        Usuario cajero = turnoCaja.getCajero();
        if (cajero == null || cajero.getIdUsuario() == null) {
            throw new RuntimeException("Cajero no especificado");
        }

        if (cajero.getCaja() == null) {
            throw new RuntimeException("El cajero no tiene una caja asignada");
        }

        // 2️⃣ Verificar si el cajero ya tiene turno activo
        Optional<TurnoCaja> turnoActivo = turnoCajaRepository
                .findByCajero_IdUsuarioAndEstadoTurno(cajero.getIdUsuario(), "ACTIVO");
        if (turnoActivo.isPresent()) {
            throw new RuntimeException("El cajero ya tiene un turno activo");
        }

        // 3️⃣ Verificar si la caja ya tiene turno activo
        Optional<TurnoCaja> turnoCajaActivo = turnoCajaRepository
                .findByCaja_IdCajaAndEstadoTurno(cajero.getCaja().getIdCaja(), "ACTIVO");
        if (turnoCajaActivo.isPresent()) {
            throw new RuntimeException("La caja ya tiene un turno activo");
        }

        // 4️⃣ Asignar valores y guardar
        turnoCaja.setFechaApertura(LocalDate.now());
        turnoCaja.setHoraApertura(LocalTime.now());
        turnoCaja.setEstadoTurno("ACTIVO");
        turnoCaja.setActivo(true);
        turnoCaja.setTotalVentasEfectivo(BigDecimal.ZERO);
        turnoCaja.setTotalVentasTarjeta(BigDecimal.ZERO);
        turnoCaja.setTotalVentasYape(BigDecimal.ZERO);
        turnoCaja.setTotalVentasPlin(BigDecimal.ZERO);
        turnoCaja.setDiferencia(BigDecimal.ZERO);

        return turnoCajaRepository.save(turnoCaja);
    }

    public TurnoCaja cerrarTurno(Integer id, BigDecimal fondoFinal, String observaciones) {
        Optional<TurnoCaja> turnoOpt = turnoCajaRepository.findById(id);
        if (turnoOpt.isPresent()) {
            TurnoCaja turno = turnoOpt.get();
            turno.setFechaCierre(LocalDate.now());
            turno.setHoraCierre(LocalTime.now());
            turno.setFondoFinal(fondoFinal);
            turno.setObservacionesCierre(observaciones);
            turno.setEstadoTurno("CERRADO");
            return turnoCajaRepository.save(turno);
        }
        return null;
    }
}