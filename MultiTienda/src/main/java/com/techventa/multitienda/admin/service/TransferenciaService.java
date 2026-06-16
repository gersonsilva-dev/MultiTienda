package com.techventa.multitienda.admin.service;

import com.techventa.multitienda.admin.model.*;
import com.techventa.multitienda.admin.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class TransferenciaService {

    @Autowired
    private TransferenciaRepository transferenciaRepository;
    @Autowired
    private DetalleTransferenciaRepository detalleRepository;
    @Autowired
    private EstadoTransferenciaRepository estadoRepository;

    private String generarCodigo() {
        return "TRF-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
    }

    public List<Transferencia> listarTodas() { return transferenciaRepository.findAll(); }
    public List<Transferencia> listarActivas() { return transferenciaRepository.findByActivoTrue(); }
    public List<Transferencia> listarPorTiendaOrigen(Integer idTienda) { return transferenciaRepository.findByTiendaOrigen_IdTienda(idTienda); }
    public List<Transferencia> listarPorTiendaDestino(Integer idTienda) { return transferenciaRepository.findByTiendaDestino_IdTienda(idTienda); }
    public List<Transferencia> listarPorEstado(Integer idEstado) { return transferenciaRepository.findByEstadoTransferencia_IdEstadoTransferencia(idEstado); }
    public Optional<Transferencia> buscarPorId(Integer id) { return transferenciaRepository.findById(id); }
    public Optional<Transferencia> buscarPorCodigo(String codigo) { return transferenciaRepository.findByCodigoTransferencia(codigo); }

    @Transactional
    public Transferencia crear(Transferencia transferencia, List<DetalleTransferencia> detalles) {
        transferencia.setCodigoTransferencia(generarCodigo());
        transferencia.setFechaSolicitud(LocalDateTime.now());
        Optional<EstadoTransferencia> estadoPendiente = estadoRepository.findByNombreEstado("PENDIENTE");
        estadoPendiente.ifPresent(transferencia::setEstadoTransferencia);
        transferencia.setActivo(true);
        Transferencia saved = transferenciaRepository.save(transferencia);
        for (DetalleTransferencia detalle : detalles) {
            detalle.setTransferencia(saved);
            detalleRepository.save(detalle);
        }
        return saved;
    }

    @Transactional
    public Transferencia actualizarEstado(Integer id, String nombreEstado) {
        Optional<Transferencia> transferenciaOpt = transferenciaRepository.findById(id);
        if (transferenciaOpt.isPresent()) {
            Transferencia t = transferenciaOpt.get();
            Optional<EstadoTransferencia> estadoOpt = estadoRepository.findByNombreEstado(nombreEstado);
            if (estadoOpt.isPresent()) {
                t.setEstadoTransferencia(estadoOpt.get());
                if ("EN_TRANSITO".equals(nombreEstado)) t.setFechaEnvio(LocalDateTime.now());
                if ("RECIBIDO".equals(nombreEstado)) t.setFechaRecepcion(LocalDateTime.now());
                return transferenciaRepository.save(t);
            }
        }
        return null;
    }

    public void eliminarLogico(Integer id) {
        transferenciaRepository.findById(id).ifPresent(t -> {
            t.setActivo(false);
            transferenciaRepository.save(t);
        });
    }
}