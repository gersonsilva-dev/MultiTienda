package com.techventa.multitienda.admin.service;

import com.techventa.multitienda.admin.model.Rol;
import com.techventa.multitienda.admin.repository.RolRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class RolService {

    @Autowired
    private RolRepository rolRepository;

    // Listar todos los roles
    public List<Rol> listarTodos() {
        return rolRepository.findAll();
    }

    // Listar solo roles activos
    public List<Rol> listarActivos() {
        return rolRepository.findByActivoTrue();
    }

    // Buscar rol por ID
    public Optional<Rol> buscarPorId(Integer id) {
        return rolRepository.findById(id);
    }

    // Buscar rol por nombre
    public Optional<Rol> buscarPorNombre(String nombreRol) {
        return rolRepository.findByNombreRol(nombreRol);
    }

    // Buscar por nivel de acceso
    public List<Rol> buscarPorNivelAcceso(Integer nivelAcceso) {
        return rolRepository.findByNivelAcceso(nivelAcceso);
    }

    // Crear nuevo rol
    public Rol crear(Rol rol) {
        rol.setActivo(true);
        return rolRepository.save(rol);
    }

    // Actualizar rol
    public Rol actualizar(Rol rol) {
        return rolRepository.save(rol);
    }

    // Eliminar rol (borrado lógico)
    public void eliminarLogico(Integer id) {
        Optional<Rol> rolOpt = rolRepository.findById(id);
        if (rolOpt.isPresent()) {
            Rol rol = rolOpt.get();
            rol.setActivo(false);
            rolRepository.save(rol);
        }
    }

    // Eliminar rol (borrado físico)
    public void eliminarFisico(Integer id) {
        rolRepository.deleteById(id);
    }

    // Verificar si existe nombre de rol
    public boolean existeNombreRol(String nombreRol) {
        return rolRepository.existsByNombreRol(nombreRol);
    }
}