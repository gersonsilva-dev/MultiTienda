package com.techventa.multitienda.admin.controller;

import com.techventa.multitienda.admin.model.UnidadMedida;
import com.techventa.multitienda.admin.repository.UnidadMedidaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController
@RequestMapping("/api/unidades-medida")
public class UnidadMedidaController {

    @Autowired
    private UnidadMedidaRepository unidadMedidaRepository;

    @GetMapping
    public List<UnidadMedida> listar() {
        return unidadMedidaRepository.findByActivoTrue();
    }
}