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

    public List<Caja> listarTodas() {
        return cajaRepository.findAll();
    }

    public List<Caja> listarActivas() {
        return cajaRepository.findByActivoTrue();
    }

    public Optional<Caja> buscarPorId(Integer id) {
        return cajaRepository.findById(id);
    }

    public List<Caja> buscarPorTienda(Integer idTienda) {
        return cajaRepository.findByTienda_IdTienda(idTienda);
    }

    public List<Caja> buscarPorEstado(Integer idEstadoCaja) {
        return cajaRepository.findByEstadoCaja_IdEstadoCaja(idEstadoCaja);
    }

    public Optional<Caja> buscarPorNumeroYTienda(Integer numeroCaja, Integer idTienda) {
        return cajaRepository.findByNumeroCajaAndTienda_IdTienda(numeroCaja, idTienda);
    }

    public List<Caja> buscarPorNombre(String nombre) {
        return cajaRepository.findByNombreCajaContainingIgnoreCase(nombre);
    }

    public Caja crear(Caja caja) {
        caja.setActivo(true);
        return cajaRepository.save(caja);
    }

    public Caja actualizar(Caja caja) {
        return cajaRepository.save(caja);
    }

    // ============================================================
    // ELIMINAR FÍSICAMENTE
    // ============================================================
    public void eliminarFisico(Integer id) {
        cajaRepository.deleteById(id);
    }

    public boolean existeNumeroEnTienda(Integer numeroCaja, Integer idTienda) {
        return cajaRepository.existsByNumeroCajaAndTienda_IdTienda(numeroCaja, idTienda);
    }
}