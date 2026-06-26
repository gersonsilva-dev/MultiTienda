package com.techventa.multitienda.admin.controller;

import com.techventa.multitienda.admin.model.Cliente;
import com.techventa.multitienda.admin.service.ClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/clientes")
public class ClienteController {

    @Autowired
    private ClienteService clienteService;

    // Listar todos los clientes
    @GetMapping
    public ResponseEntity<List<Cliente>> listarTodos() {
        List<Cliente> clientes = clienteService.listarTodos();
        return ResponseEntity.ok(clientes);
    }

    // Listar solo clientes activos
    @GetMapping("/activos")
    public ResponseEntity<List<Cliente>> listarActivos() {
        List<Cliente> clientes = clienteService.listarActivos();
        return ResponseEntity.ok(clientes);
    }

    // Buscar cliente por ID
    @GetMapping("/{id}")
    public ResponseEntity<Cliente> buscarPorId(@PathVariable Integer id) {
        Optional<Cliente> cliente = clienteService.buscarPorId(id);
        return cliente.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Buscar cliente por número de documento
    @GetMapping("/documento/{documento}")
    public ResponseEntity<Cliente> buscarPorDocumento(@PathVariable String documento) {
        Optional<Cliente> cliente = clienteService.buscarPorDocumento(documento);
        return cliente.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Buscar cliente por RUC
    @GetMapping("/ruc/{ruc}")
    public ResponseEntity<Cliente> buscarPorRuc(@PathVariable String ruc) {
        Optional<Cliente> cliente = clienteService.buscarPorRuc(ruc);
        return cliente.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Buscar cliente por correo
    @GetMapping("/correo/{correo}")
    public ResponseEntity<Cliente> buscarPorCorreo(@PathVariable String correo) {
        Optional<Cliente> cliente = clienteService.buscarPorCorreo(correo);
        return cliente.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Buscar por tipo de cliente
    @GetMapping("/tipo/{tipo}")
    public ResponseEntity<List<Cliente>> buscarPorTipo(@PathVariable String tipo) {
        List<Cliente> clientes = clienteService.buscarPorTipo(tipo);
        return ResponseEntity.ok(clientes);
    }

    // Buscar por estado
    @GetMapping("/estado/{idEstado}")
    public ResponseEntity<List<Cliente>> buscarPorEstado(@PathVariable Integer idEstado) {
        List<Cliente> clientes = clienteService.buscarPorEstado(idEstado);
        return ResponseEntity.ok(clientes);
    }

    // Buscar por nombre o apellido
    @GetMapping("/buscar")
    public ResponseEntity<List<Cliente>> buscarPorNombre(
            @RequestParam String nombres,
            @RequestParam String apellidos) {
        List<Cliente> clientes = clienteService.buscarPorNombre(nombres, apellidos);
        return ResponseEntity.ok(clientes);
    }

    // Buscar por razón social
    @GetMapping("/razon-social")
    public ResponseEntity<List<Cliente>> buscarPorRazonSocial(@RequestParam String razonSocial) {
        List<Cliente> clientes = clienteService.buscarPorRazonSocial(razonSocial);
        return ResponseEntity.ok(clientes);
    }

    // Crear nuevo cliente
    @PostMapping
    public ResponseEntity<Object> crear(@RequestBody Cliente cliente) {
        try {
            // Validar según tipo de cliente
            if ("NATURAL".equals(cliente.getTipoCliente())) {
                if (cliente.getNumeroDocumento() != null && clienteService.existeDocumento(cliente.getNumeroDocumento())) {
                    Map<String, String> error = new HashMap<>();
                    error.put("error", "Ya existe un cliente con el documento: " + cliente.getNumeroDocumento());
                    return ResponseEntity.status(HttpStatus.CONFLICT).body(error);
                }
            } else if ("JURIDICO".equals(cliente.getTipoCliente())) {
                if (cliente.getRuc() != null && clienteService.existeRuc(cliente.getRuc())) {
                    Map<String, String> error = new HashMap<>();
                    error.put("error", "Ya existe un cliente con el RUC: " + cliente.getRuc());
                    return ResponseEntity.status(HttpStatus.CONFLICT).body(error);
                }
            }

            if (cliente.getCorreoElectronico() != null && clienteService.existeCorreo(cliente.getCorreoElectronico())) {
                Map<String, String> error = new HashMap<>();
                error.put("error", "Ya existe un cliente con el correo: " + cliente.getCorreoElectronico());
                return ResponseEntity.status(HttpStatus.CONFLICT).body(error);
            }

            Cliente nuevoCliente = clienteService.crear(cliente);
            return ResponseEntity.status(HttpStatus.CREATED).body(nuevoCliente);
        } catch (Exception e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", "Error al crear el cliente: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
        }
    }

    // Actualizar cliente
    @PutMapping("/{id}")
    public ResponseEntity<Object> actualizar(@PathVariable Integer id, @RequestBody Cliente cliente) {
        try {
            Optional<Cliente> existente = clienteService.buscarPorId(id);
            if (existente.isEmpty()) {
                return ResponseEntity.notFound().build();
            }
            cliente.setIdCliente(id);
            Cliente actualizado = clienteService.actualizar(cliente);
            return ResponseEntity.ok(actualizado);
        } catch (Exception e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", "Error al actualizar el cliente: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
        }
    }

    // Eliminar cliente (borrado lógico)
    @DeleteMapping("/{id}")
    public ResponseEntity<Object> eliminar(@PathVariable Integer id) {
        try {
            Optional<Cliente> existente = clienteService.buscarPorId(id);
            if (existente.isEmpty()) {
                return ResponseEntity.notFound().build();
            }
            clienteService.eliminarLogico(id);
            Map<String, String> response = new HashMap<>();
            response.put("mensaje", "Cliente eliminado correctamente");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", "Error al eliminar el cliente: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
        }
    }
    
 // Guardar o actualizar cliente (crear si no existe)
    @PostMapping("/guardar")
    public ResponseEntity<Object> guardarCliente(@RequestBody Cliente cliente) {
        try {
            // Buscar por documento o RUC
            Optional<Cliente> existente = Optional.empty();
            
            if (cliente.getNumeroDocumento() != null && !cliente.getNumeroDocumento().isEmpty()) {
                existente = clienteService.buscarPorDocumento(cliente.getNumeroDocumento());
            } else if (cliente.getRuc() != null && !cliente.getRuc().isEmpty()) {
                existente = clienteService.buscarPorRuc(cliente.getRuc());
            }
            
            if (existente.isPresent()) {
                // Actualizar datos del cliente existente
                Cliente clienteExistente = existente.get();
                if (cliente.getNombres() != null) clienteExistente.setNombres(cliente.getNombres());
                if (cliente.getApellidos() != null) clienteExistente.setApellidos(cliente.getApellidos());
                if (cliente.getRazonSocial() != null) clienteExistente.setRazonSocial(cliente.getRazonSocial());
                if (cliente.getCorreoElectronico() != null) clienteExistente.setCorreoElectronico(cliente.getCorreoElectronico());
                if (cliente.getTelefono() != null) clienteExistente.setTelefono(cliente.getTelefono());
                if (cliente.getDireccion() != null) clienteExistente.setDireccion(cliente.getDireccion());
                
                Cliente actualizado = clienteService.actualizar(clienteExistente);
                return ResponseEntity.ok(actualizado);
            } else {
                // Crear nuevo cliente
                cliente.setActivo(true);
                Cliente nuevoCliente = clienteService.crear(cliente);
                return ResponseEntity.status(HttpStatus.CREATED).body(nuevoCliente);
            }
        } catch (Exception e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", "Error al guardar el cliente: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
        }
    }
}