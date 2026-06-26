package com.techventa.multitienda.cajero.service;

import com.techventa.multitienda.cajero.model.RetiroCaja;
import com.techventa.multitienda.cajero.repository.RetiroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RetiroService {

    @Autowired
    private RetiroRepository retiroRepository;

    public RetiroCaja guardar(RetiroCaja retiro) {
        return retiroRepository.save(retiro);
    }

    public Double obtenerTotalRetiros(Integer idTurnoCaja) {
        Double total = retiroRepository.sumMontoByTurnoCajaIdTurnoCaja(idTurnoCaja);
        return total != null ? total : 0.0;
    }

    public List<RetiroCaja> obtenerHistorialRetiros(Integer idTurnoCaja) {
        return retiroRepository.findByTurnoCajaIdTurnoCajaOrderByFechaRetiroDesc(idTurnoCaja);
    }
}