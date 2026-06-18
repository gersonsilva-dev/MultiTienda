package com.techventa.multitienda.cajero.service;

import com.techventa.multitienda.cajero.model.TurnoCaja;
import com.techventa.multitienda.cajero.repository.TurnoCajaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

@Service
public class TurnoCajaService {

    @Autowired
    private TurnoCajaRepository turnoCajaRepository;

    public List<TurnoCaja> listarTodos() { return turnoCajaRepository.findAll(); }
    
    public List<TurnoCaja> listarPorCajero(Integer idCajero) { return turnoCajaRepository.findByCajero_IdUsuario(idCajero); }
    
    public List<TurnoCaja> listarPorTienda(Integer idTienda) { return turnoCajaRepository.findByTienda_IdTienda(idTienda); }
    
    public List<TurnoCaja> listarPorCaja(Integer idCaja) { return turnoCajaRepository.findByCaja_IdCaja(idCaja); }
    
    public List<TurnoCaja> listarActivos() { return turnoCajaRepository.findByEstadoTurno("ACTIVO"); }
    
    public Optional<TurnoCaja> buscarPorId(Integer id) { return turnoCajaRepository.findById(id); }
    
    public Optional<TurnoCaja> buscarActivoPorCaja(Integer idCaja) { 
        return turnoCajaRepository.findByCaja_IdCajaAndEstadoTurno(idCaja, "ACTIVO"); 
    }

    public TurnoCaja abrirTurno(TurnoCaja turnoCaja) {
        turnoCaja.setFechaApertura(LocalDate.now());
        turnoCaja.setHoraApertura(LocalTime.now());
        turnoCaja.setEstadoTurno("ACTIVO");
        turnoCaja.setActivo(true);
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