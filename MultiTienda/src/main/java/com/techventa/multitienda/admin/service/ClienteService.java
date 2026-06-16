package com.techventa.multitienda.admin.service;

import com.techventa.multitienda.admin.model.Cliente;
import com.techventa.multitienda.admin.repository.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class ClienteService {

    @Autowired
    private ClienteRepository clienteRepository;

    // Listar todos los clientes
    public List<Cliente> listarTodos() {
        return clienteRepository.findAll();
    }

    // Listar solo clientes activos
    public List<Cliente> listarActivos() {
        return clienteRepository.findByActivoTrue();
    }

    // Buscar cliente por ID
    public Optional<Cliente> buscarPorId(Integer id) {
        return clienteRepository.findById(id);
    }

    // Buscar cliente por número de documento
    public Optional<Cliente> buscarPorDocumento(String documento) {
        return clienteRepository.findByNumeroDocumento(documento);
    }

    // Buscar cliente por RUC
    public Optional<Cliente> buscarPorRuc(String ruc) {
        return clienteRepository.findByRuc(ruc);
    }

    // Buscar cliente por correo
    public Optional<Cliente> buscarPorCorreo(String correo) {
        return clienteRepository.findByCorreoElectronico(correo);
    }

    // Buscar por tipo de cliente
    public List<Cliente> buscarPorTipo(String tipoCliente) {
        return clienteRepository.findByTipoCliente(tipoCliente);
    }

    // Buscar por estado
    public List<Cliente> buscarPorEstado(Integer idEstadoCliente) {
        return clienteRepository.findByEstadoCliente_IdEstadoCliente(idEstadoCliente);
    }

    // Buscar por nombre o apellido
    public List<Cliente> buscarPorNombre(String nombres, String apellidos) {
        return clienteRepository.findByNombresContainingIgnoreCaseOrApellidosContainingIgnoreCase(nombres, apellidos);
    }

    // Buscar por razón social
    public List<Cliente> buscarPorRazonSocial(String razonSocial) {
        return clienteRepository.findByRazonSocialContainingIgnoreCase(razonSocial);
    }

    // Crear nuevo cliente
    public Cliente crear(Cliente cliente) {
        cliente.setActivo(true);
        return clienteRepository.save(cliente);
    }

    // Actualizar cliente
    public Cliente actualizar(Cliente cliente) {
        return clienteRepository.save(cliente);
    }

    // Eliminar cliente (borrado lógico)
    public void eliminarLogico(Integer id) {
        Optional<Cliente> clienteOpt = clienteRepository.findById(id);
        if (clienteOpt.isPresent()) {
            Cliente cliente = clienteOpt.get();
            cliente.setActivo(false);
            clienteRepository.save(cliente);
        }
    }

    // Eliminar cliente (borrado físico)
    public void eliminarFisico(Integer id) {
        clienteRepository.deleteById(id);
    }

    // Verificar si existe documento
    public boolean existeDocumento(String documento) {
        return clienteRepository.existsByNumeroDocumento(documento);
    }

    // Verificar si existe RUC
    public boolean existeRuc(String ruc) {
        return clienteRepository.existsByRuc(ruc);
    }

    // Verificar si existe correo
    public boolean existeCorreo(String correo) {
        return clienteRepository.existsByCorreoElectronico(correo);
    }
}