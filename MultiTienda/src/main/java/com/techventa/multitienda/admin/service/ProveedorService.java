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

    // Listar todos los proveedores
    public List<Proveedor> listarTodos() {
        return proveedorRepository.findAll();
    }

    // Listar solo proveedores activos
    public List<Proveedor> listarActivos() {
        return proveedorRepository.findByActivoTrue();
    }

    // Buscar proveedor por ID
    public Optional<Proveedor> buscarPorId(Integer id) {
        return proveedorRepository.findById(id);
    }

    // Buscar proveedor por RUC
    public Optional<Proveedor> buscarPorRuc(String ruc) {
        return proveedorRepository.findByRuc(ruc);
    }

    // Buscar por razón social (contiene)
    public List<Proveedor> buscarPorRazonSocial(String razonSocial) {
        return proveedorRepository.findByRazonSocialContainingIgnoreCase(razonSocial);
    }

    // Buscar proveedores por categoría
    public List<Proveedor> buscarPorCategoria(Integer idCategoria) {
        return proveedorRepository.findByCategoriaProveedor_IdCategoriaProveedor(idCategoria);
    }

    // Buscar proveedores por estado
    public List<Proveedor> buscarPorEstado(Integer idEstado) {
        return proveedorRepository.findByEstadoProveedor_IdEstadoProveedor(idEstado);
    }

    // Crear nuevo proveedor
    public Proveedor crear(Proveedor proveedor) {
        proveedor.setActivo(true);
        return proveedorRepository.save(proveedor);
    }

    // Actualizar proveedor existente
    public Proveedor actualizar(Proveedor proveedor) {
        return proveedorRepository.save(proveedor);
    }

    // Eliminar proveedor (borrado lógico)
    public void eliminarLogico(Integer id) {
        Optional<Proveedor> proveedorOpt = proveedorRepository.findById(id);
        if (proveedorOpt.isPresent()) {
            Proveedor proveedor = proveedorOpt.get();
            proveedor.setActivo(false);
            proveedorRepository.save(proveedor);
        }
    }

    // Eliminar proveedor (borrado físico)
    public void eliminarFisico(Integer id) {
        proveedorRepository.deleteById(id);
    }

    // Verificar si existe RUC
    public boolean existeRuc(String ruc) {
        return proveedorRepository.existsByRuc(ruc);
    }
}