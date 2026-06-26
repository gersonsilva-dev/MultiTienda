package com.techventa.multitienda.proveedor.service;

import com.techventa.multitienda.proveedor.model.*;
import com.techventa.multitienda.proveedor.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class OrdenCompraService {

    @Autowired
    private OrdenCompraRepository ordenCompraRepository;

    @Autowired
    private DetalleOrdenCompraRepository detalleOrdenCompraRepository;

    private String generarCodigoOrden() {
        return "OC-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
    }

    public List<OrdenCompra> listarTodas() {
        return ordenCompraRepository.findAll();
    }

    public List<OrdenCompra> listarActivas() {
        return ordenCompraRepository.findByActivoTrue();
    }

    public List<OrdenCompra> listarPorProveedor(Integer idProveedor) {
        return ordenCompraRepository.findByProveedor_IdProveedor(idProveedor);
    }

    public List<OrdenCompra> listarPorTienda(Integer idTienda) {
        return ordenCompraRepository.findByTienda_IdTienda(idTienda);
    }

    public List<OrdenCompra> listarPorEstado(Integer idEstado) {
        return ordenCompraRepository.findByIdEstadoOrdenCompra(idEstado);
    }

    public Optional<OrdenCompra> buscarPorId(Integer id) {
        return ordenCompraRepository.findById(id);
    }

    public Optional<OrdenCompra> buscarPorCodigo(String codigo) {
        return ordenCompraRepository.findByCodigoOrden(codigo);
    }

    public List<DetalleOrdenCompra> listarDetallesPorOrden(Integer idOrden) {
        return detalleOrdenCompraRepository.findByOrdenCompra_IdOrden(idOrden);
    }

    @Transactional
    public OrdenCompra crear(OrdenCompra ordenCompra, List<DetalleOrdenCompra> detalles) {
        ordenCompra.setCodigoOrden(generarCodigoOrden());
        ordenCompra.setFechaOrden(LocalDateTime.now());
        ordenCompra.setActivo(true);
        // Estado por defecto: PENDIENTE (1)
        ordenCompra.setIdEstadoOrdenCompra(1);

        OrdenCompra saved = ordenCompraRepository.save(ordenCompra);

        for (DetalleOrdenCompra detalle : detalles) {
            detalle.setOrdenCompra(saved);
            detalleOrdenCompraRepository.save(detalle);
        }

        return saved;
    }

    public OrdenCompra actualizarEstado(Integer id, Integer idEstado) {
        Optional<OrdenCompra> ordenOpt = ordenCompraRepository.findById(id);
        if (ordenOpt.isPresent()) {
            OrdenCompra orden = ordenOpt.get();
            orden.setIdEstadoOrdenCompra(idEstado);
            return ordenCompraRepository.save(orden);
        }
        return null;
    }

    public void eliminarLogico(Integer id) {
        ordenCompraRepository.findById(id).ifPresent(orden -> {
            orden.setActivo(false);
            ordenCompraRepository.save(orden);
        });
    }
}