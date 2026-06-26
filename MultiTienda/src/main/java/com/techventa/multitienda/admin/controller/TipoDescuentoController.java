package com.techventa.multitienda.admin.controller;

import com.techventa.multitienda.admin.model.TipoDescuento;
import com.techventa.multitienda.admin.repository.TipoDescuentoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController
@RequestMapping("/api/tipos-descuento")
public class TipoDescuentoController {

    @Autowired
    private TipoDescuentoRepository tipoDescuentoRepository;

    @GetMapping
    public List<TipoDescuento> listar() {
        return tipoDescuentoRepository.findByActivoTrue();
    }
}