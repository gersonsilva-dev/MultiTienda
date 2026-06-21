package com.techventa.multitienda.cajero.service;

import com.techventa.multitienda.cajero.model.VentaSuspendida;
import com.techventa.multitienda.cajero.repository.VentaSuspendidaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class VentaSuspendidaService {

    @Autowired
    private VentaSuspendidaRepository ventaSuspendidaRepository;

    private String generarCodigoSuspension() {
        return "SUS-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
    }

    public List<VentaSuspendida> listarPorCajero(Integer idCajero) {
        return ventaSuspendidaRepository.findByCajero_IdUsuarioAndEstado(idCajero, "SUSPENDIDA");
    }

    public List<VentaSuspendida> listarPorTienda(Integer idTienda) {
        return ventaSuspendidaRepository.findByTienda_IdTienda(idTienda);
    }

    public Optional<VentaSuspendida> buscarPorId(Integer id) {
        return ventaSuspendidaRepository.findById(id);
    }

    public Optional<VentaSuspendida> buscarPorCodigo(String codigo) {
        return ventaSuspendidaRepository.findByCodigoSuspension(codigo);
    }

    @Transactional
    public VentaSuspendida suspenderVenta(VentaSuspendida suspension) {
        suspension.setCodigoSuspension(generarCodigoSuspension());
        suspension.setFechaSuspension(LocalDateTime.now());
        suspension.setEstado("SUSPENDIDA");
        return ventaSuspendidaRepository.save(suspension);
    }

    @Transactional
    public VentaSuspendida recuperarVenta(Integer id) {
        Optional<VentaSuspendida> opt = ventaSuspendidaRepository.findById(id);
        if (opt.isPresent()) {
            VentaSuspendida suspension = opt.get();
            suspension.setEstado("RECUPERADA");
            suspension.setFechaRecuperacion(LocalDateTime.now());
            return ventaSuspendidaRepository.save(suspension);
        }
        return null;
    }

    @Transactional
    public void cancelarSuspension(Integer id) {
        ventaSuspendidaRepository.findById(id).ifPresent(suspension -> {
            suspension.setEstado("CANCELADA");
            ventaSuspendidaRepository.save(suspension);
        });
    }
}