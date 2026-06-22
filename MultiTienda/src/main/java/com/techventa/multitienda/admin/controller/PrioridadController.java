package com.techventa.multitienda.admin.controller;

import com.techventa.multitienda.admin.model.Prioridad;
import com.techventa.multitienda.admin.repository.PrioridadRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController
@RequestMapping("/api/prioridades")
public class PrioridadController {

    @Autowired
    private PrioridadRepository prioridadRepository;

    @GetMapping
    public List<Prioridad> listar() {
        return prioridadRepository.findByActivoTrue();
    }
}