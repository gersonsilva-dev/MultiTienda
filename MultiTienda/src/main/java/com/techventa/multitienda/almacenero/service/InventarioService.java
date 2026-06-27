package com.techventa.multitienda.almacenero.service;

import com.techventa.multitienda.almacenero.model.InventarioTienda;
import com.techventa.multitienda.almacenero.repository.InventarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class InventarioService {

    @Autowired
    private InventarioRepository inventarioRepository;

    public Map<String, Object> getResumenInventario(Integer idTienda) {
        Map<String, Object> resumen = new HashMap<>();
        resumen.put("totalProductos", inventarioRepository.countTotalProductosActivos(idTienda));
        resumen.put("bajoMinimo", inventarioRepository.countProductosBajoMinimo(idTienda));
        resumen.put("categoriasActivas", inventarioRepository.countCategoriasActivas(idTienda));

        Double valorStock = inventarioRepository.calcularValorTotalStock(idTienda);
        resumen.put("valorTotalStock", valorStock != null ? valorStock : 0.0);
        return resumen;
    }

    public List<InventarioTienda> listarInventarioPorTienda(Integer idTienda) {
        return inventarioRepository.findInventarioCompletoByTienda(idTienda);
    }

    public List<InventarioTienda> buscarProductos(Integer idTienda, String search) {
        if (search == null || search.trim().isEmpty()) {
            return listarInventarioPorTienda(idTienda);
        }
        return inventarioRepository.buscarPorNombreOCodigo(idTienda, search.trim());
    }

    // ✅ CORREGIDO: ahora usa query directa en BD
    public List<InventarioTienda> getProductosCriticos(Integer idTienda) {
        return inventarioRepository.findProductosCriticos(idTienda);
    }

    public BigDecimal getValorTotalStock(Integer idTienda) {
        Double valor = inventarioRepository.calcularValorTotalStock(idTienda);
        return valor != null ? BigDecimal.valueOf(valor) : BigDecimal.ZERO;
    }
}