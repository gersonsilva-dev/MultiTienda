package com.techventa.multitienda.cajero.service;

import com.techventa.multitienda.cajero.model.*;
import com.techventa.multitienda.cajero.repository.*;
import com.techventa.multitienda.admin.model.Usuario;
import com.techventa.multitienda.admin.repository.InventarioTiendaRepository;
import com.techventa.multitienda.admin.repository.LoteRepository;
import com.techventa.multitienda.almacenero.model.InventarioTienda;
import com.techventa.multitienda.admin.model.Lote;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class DevolucionService {

    @Autowired
    private DevolucionRepository devolucionRepository;

    @Autowired
    private DetalleDevolucionRepository detalleDevolucionRepository;

    @Autowired
    private DetalleVentaRepository detalleVentaRepository;

    @Autowired
    private InventarioTiendaRepository inventarioRepository;

    @Autowired
    private LoteRepository loteRepository;

    @Autowired
    private EstadoDevolucionRepository estadoDevolucionRepository;

    private String generarCodigoDevolucion() {
        return "DEV-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
    }

    public List<Devolucion> listarTodas() {
        return devolucionRepository.findAll();
    }

    public List<Devolucion> listarPorVenta(Integer idVenta) {
        return devolucionRepository.findByVenta_IdVenta(idVenta);
    }

    public List<Devolucion> listarPorCajero(Integer idCajero) {
        return devolucionRepository.findByCajero_IdUsuario(idCajero);
    }

    public List<Devolucion> listarPorEstado(Integer idEstado) {
        return devolucionRepository.findByEstadoDevolucion_IdEstadoDevolucion(idEstado);
    }

    public Optional<Devolucion> buscarPorId(Integer id) {
        return devolucionRepository.findById(id);
    }

    public Optional<Devolucion> buscarPorCodigo(String codigo) {
        return devolucionRepository.findByCodigoDevolucion(codigo);
    }

    @Transactional
    public Devolucion registrarDevolucion(Devolucion devolucion, List<DetalleDevolucion> detalles) {
        devolucion.setCodigoDevolucion(generarCodigoDevolucion());
        devolucion.setFechaSolicitud(LocalDateTime.now());

        Optional<EstadoDevolucion> estadoOpt = estadoDevolucionRepository.findByNombreEstado("SOLICITADA");
        estadoOpt.ifPresent(devolucion::setEstadoDevolucion);

        devolucion.setActivo(true);
        Devolucion saved = devolucionRepository.save(devolucion);

        for (DetalleDevolucion detalle : detalles) {
            detalle.setDevolucion(saved);
            Optional<DetalleVenta> dvOpt = detalleVentaRepository.findById(detalle.getDetalleVenta().getIdDetalleVenta());
            if (dvOpt.isPresent()) {
                DetalleVenta dv = dvOpt.get();
                detalle.setMontoDevuelto(dv.getPrecioUnitario().multiply(BigDecimal.valueOf(detalle.getCantidad())));
            }
            detalleDevolucionRepository.save(detalle);
        }
        return saved;
    }

    @Transactional
    public Devolucion aprobarDevolucion(Integer id, Integer idSupervisor) {
        Optional<Devolucion> devolucionOpt = devolucionRepository.findById(id);
        if (devolucionOpt.isPresent()) {
            Devolucion devolucion = devolucionOpt.get();
            Optional<EstadoDevolucion> estadoOpt = estadoDevolucionRepository.findByNombreEstado("APROBADA");
            estadoOpt.ifPresent(devolucion::setEstadoDevolucion);
            devolucion.setFechaAutorizacion(LocalDateTime.now());
            Usuario supervisor = new Usuario();
            supervisor.setIdUsuario(idSupervisor);
            devolucion.setSupervisorAutoriza(supervisor);
            return devolucionRepository.save(devolucion);
        }
        return null;
    }

    @Transactional
    public Devolucion rechazarDevolucion(Integer id, String motivo) {
        Optional<Devolucion> devolucionOpt = devolucionRepository.findById(id);
        if (devolucionOpt.isPresent()) {
            Devolucion devolucion = devolucionOpt.get();
            Optional<EstadoDevolucion> estadoOpt = estadoDevolucionRepository.findByNombreEstado("RECHAZADA");
            estadoOpt.ifPresent(devolucion::setEstadoDevolucion);
            devolucion.setObservaciones(devolucion.getObservaciones() + " | Rechazo: " + motivo);
            return devolucionRepository.save(devolucion);
        }
        return null;
    }
}