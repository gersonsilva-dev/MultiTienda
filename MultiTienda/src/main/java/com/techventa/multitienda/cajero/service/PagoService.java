package com.techventa.multitienda.cajero.service;

import com.techventa.multitienda.cajero.model.*;
import com.techventa.multitienda.cajero.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class PagoService {

    @Autowired
    private PagoRepository pagoRepository;
    
    @Autowired
    private EstadoPagoRepository estadoPagoRepository;

    public List<Pago> listarPorVenta(Integer idVenta) { return pagoRepository.findByVenta_IdVenta(idVenta); }
    
    public List<Pago> listarPorMetodo(Integer idMetodo) { return pagoRepository.findByMetodoPago_IdMetodoPago(idMetodo); }
    
    public Optional<Pago> buscarPorId(Integer id) { return pagoRepository.findById(id); }

    public Pago registrarPago(Pago pago) {
        pago.setFechaPago(LocalDateTime.now());
        Optional<EstadoPago> estadoOpt = estadoPagoRepository.findByNombreEstado("PAGADO");
        estadoOpt.ifPresent(pago::setEstadoPago);
        return pagoRepository.save(pago);
    }
}