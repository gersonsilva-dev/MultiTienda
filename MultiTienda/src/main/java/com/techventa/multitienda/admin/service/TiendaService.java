package com.techventa.multitienda.admin.service;

import com.techventa.multitienda.admin.model.Tienda;
import com.techventa.multitienda.admin.repository.TiendaRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class TiendaService {

    @Autowired
    private TiendaRepository tiendaRepository;

    // Listar todas las tiendas
    public List<Tienda> listarTodas() {
        return tiendaRepository.findAll();
    }

    // Listar solo tiendas activas
    public List<Tienda> listarActivas() {
        return tiendaRepository.findByActivoTrue();
    }

    // Buscar tienda por ID
    public Optional<Tienda> buscarPorId(Integer id) {
        return tiendaRepository.findById(id);
    }

    // Buscar tienda por código
    public Optional<Tienda> buscarPorCodigo(String codigoTienda) {
        return tiendaRepository.findByCodigoTienda(codigoTienda);
    }

    // Buscar por nombre (contiene)
    public List<Tienda> buscarPorNombre(String nombre) {
        return tiendaRepository.findByNombreTiendaContainingIgnoreCase(nombre);
    }

    // Buscar por estado
    public List<Tienda> buscarPorEstado(Integer idEstadoTienda) {
        return tiendaRepository.findByIdEstadoTienda(idEstadoTienda);
    }

    // Crear nueva tienda
    public Tienda crear(Tienda tienda) {
        tienda.setActivo(true);
        return tiendaRepository.save(tienda);
    }

    // Actualizar tienda
    public Tienda actualizar(Tienda tienda) {
        return tiendaRepository.save(tienda);
    }

    // Eliminar tienda (borrado lógico)
    public void eliminarLogico(Integer id) {
        Optional<Tienda> tiendaOpt = tiendaRepository.findById(id);
        if (tiendaOpt.isPresent()) {
            Tienda tienda = tiendaOpt.get();
            tienda.setActivo(false);
            tiendaRepository.save(tienda);
        }
    }

    // Eliminar tienda (borrado físico)
    public void eliminarFisico(Integer id) {
        tiendaRepository.deleteById(id);
    }

    // Verificar si existe código de tienda
    public boolean existeCodigo(String codigoTienda) {
        return tiendaRepository.existsByCodigoTienda(codigoTienda);
    }
}