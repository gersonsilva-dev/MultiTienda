package com.techventa.multitienda.admin.service;

import com.techventa.multitienda.admin.model.Proveedor;
import com.techventa.multitienda.admin.repository.ProveedorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class ProveedorService {

    @Autowired
    private ProveedorRepository proveedorRepository;

    public List<Proveedor> listarTodos() {
        return proveedorRepository.findAll();
    }

    public List<Proveedor> listarActivos() {
        return proveedorRepository.findByActivoTrue();
    }

    public Optional<Proveedor> buscarPorId(Integer id) {
        return proveedorRepository.findById(id);
    }

    public Optional<Proveedor> buscarPorRuc(String ruc) {
        return proveedorRepository.findByRuc(ruc);
    }

    public List<Proveedor> buscarPorRazonSocial(String razonSocial) {
        return proveedorRepository.findByRazonSocialContainingIgnoreCase(razonSocial);
    }

    public List<Proveedor> buscarPorCategoria(Integer idCategoria) {
        return proveedorRepository.findByCategoriaProveedor_IdCategoriaProveedor(idCategoria);
    }

    public List<Proveedor> buscarPorEstado(Integer idEstado) {
        return proveedorRepository.findByEstadoProveedor_IdEstadoProveedor(idEstado);
    }

    public Proveedor crear(Proveedor proveedor) {
        proveedor.setActivo(true);
        return proveedorRepository.save(proveedor);
    }

    public Proveedor actualizar(Proveedor proveedor) {
        return proveedorRepository.save(proveedor);
    }

    // ============================================================
    // ELIMINAR FÍSICAMENTE (NO lógico)
    // ============================================================
    public void eliminarFisico(Integer id) {
        proveedorRepository.deleteById(id);
    }

    public boolean existeRuc(String ruc) {
        return proveedorRepository.existsByRuc(ruc);
    }
}