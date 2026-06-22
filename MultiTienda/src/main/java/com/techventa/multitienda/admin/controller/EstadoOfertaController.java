package com.techventa.multitienda.admin.controller;

import com.techventa.multitienda.admin.model.EstadoOferta;
import com.techventa.multitienda.admin.repository.EstadoOfertaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController
@RequestMapping("/api/estados-oferta")
public class EstadoOfertaController {

    @Autowired
    private EstadoOfertaRepository estadoOfertaRepository;

    @GetMapping
    public List<EstadoOferta> listar() {
        return estadoOfertaRepository.findByActivoTrue();
    }
}