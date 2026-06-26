package com.techventa.multitienda.admin.service;

import com.techventa.multitienda.admin.model.Producto;
import com.techventa.multitienda.admin.repository.ProductoRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class ProductoService {

    @Autowired
    private ProductoRepository productoRepository;

    // Listar todos los productos
    public List<Producto> listarTodos() {
        return productoRepository.findAll();
    }

    // Listar solo productos activos
    public List<Producto> listarActivos() {
        return productoRepository.findByActivoTrue();
    }

    // Buscar producto por ID
    public Optional<Producto> buscarPorId(Integer id) {
        return productoRepository.findById(id);
    }

    // Buscar producto por código de barras
    public Optional<Producto> buscarPorCodigoBarras(String codigoBarras) {
        return productoRepository.findByCodigoBarras(codigoBarras);
    }

    // Buscar por nombre (contiene)
    public List<Producto> buscarPorNombre(String nombre) {
        return productoRepository.findByNombreProductoContainingIgnoreCase(nombre);
    }

    // Buscar por categoría
    public List<Producto> buscarPorCategoria(Integer idCategoria) {
        return productoRepository.findByCategoria_IdCategoria(idCategoria);
    }

    // Buscar por marca
    public List<Producto> buscarPorMarca(String marca) {
        return productoRepository.findByMarcaContainingIgnoreCase(marca);
    }

    // Buscar por estado
    public List<Producto> buscarPorEstado(Integer idEstadoProducto) {
        return productoRepository.findByIdEstadoProducto(idEstadoProducto);
    }

    // Crear nuevo producto
    public Producto crear(Producto producto) {
        producto.setActivo(true);
        return productoRepository.save(producto);
    }

    // Actualizar producto
    public Producto actualizar(Producto producto) {
        return productoRepository.save(producto);
    }

 // ANTES (borrado lógico)
    public void eliminarLogico(Integer id) {
        Optional<Producto> productoOpt = productoRepository.findById(id);
        if (productoOpt.isPresent()) {
            Producto producto = productoOpt.get();
            producto.setActivo(false);
            productoRepository.save(producto);
        }
    }

    // AHORA (borrado físico)
    public void eliminarFisico(Integer id) {
        productoRepository.deleteById(id);
    }

    // Verificar si existe código de barras
    public boolean existeCodigoBarras(String codigoBarras) {
        return productoRepository.existsByCodigoBarras(codigoBarras);
    }
}