package com.techventa.multitienda.admin.service;

import com.techventa.multitienda.admin.model.Caja;
import com.techventa.multitienda.admin.repository.CajaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class CajaService {

    @Autowired
    private CajaRepository cajaRepository;

    // Listar todas las cajas
    public List<Caja> listarTodas() {
        return cajaRepository.findAll();
    }

    // Listar solo cajas activas
    public List<Caja> listarActivas() {
        return cajaRepository.findByActivoTrue();
    }

    // Buscar caja por ID
    public Optional<Caja> buscarPorId(Integer id) {
        return cajaRepository.findById(id);
    }

    // Buscar cajas por tienda
    public List<Caja> buscarPorTienda(Integer idTienda) {
        return cajaRepository.findByTienda_IdTienda(idTienda);
    }

    // Buscar cajas por estado
    public List<Caja> buscarPorEstado(Integer idEstadoCaja) {
        return cajaRepository.findByEstadoCaja_IdEstadoCaja(idEstadoCaja);
    }

    // Buscar caja por número y tienda
    public Optional<Caja> buscarPorNumeroYTienda(Integer numeroCaja, Integer idTienda) {
        return cajaRepository.findByNumeroCajaAndTienda_IdTienda(numeroCaja, idTienda);
    }

    // Buscar por nombre
    public List<Caja> buscarPorNombre(String nombre) {
        return cajaRepository.findByNombreCajaContainingIgnoreCase(nombre);
    }

    // Crear nueva caja
    public Caja crear(Caja caja) {
        caja.setActivo(true);
        return cajaRepository.save(caja);
    }

    // Actualizar caja
    public Caja actualizar(Caja caja) {
        return cajaRepository.save(caja);
    }

    // Eliminar caja (borrado lógico)
    public void eliminarLogico(Integer id) {
        Optional<Caja> cajaOpt = cajaRepository.findById(id);
        if (cajaOpt.isPresent()) {
            Caja caja = cajaOpt.get();
            caja.setActivo(false);
            cajaRepository.save(caja);
        }
    }

    // Eliminar caja (borrado físico)
    public void eliminarFisico(Integer id) {
        cajaRepository.deleteById(id);
    }

    // Verificar si existe número de caja en una tienda
    public boolean existeNumeroEnTienda(Integer numeroCaja, Integer idTienda) {
        return cajaRepository.existsByNumeroCajaAndTienda_IdTienda(numeroCaja, idTienda);
    }
}