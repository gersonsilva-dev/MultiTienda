package com.techventa.multitienda.admin.service;

import com.techventa.multitienda.admin.dto.*;
import com.techventa.multitienda.admin.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class ReporteService {

    @Autowired
    private VentaRepository ventaRepository;
    @Autowired
    private DetalleVentaRepository detalleVentaRepository;
    @Autowired
    private InventarioTiendaRepository inventarioRepository;
    @Autowired
    private MermaRepository mermaRepository;
    @Autowired
    private IncidenciaRepository incidenciaRepository;

    // Reporte de ventas por período
    public List<ReporteVentasDTO> reporteVentas(LocalDateTime inicio, LocalDateTime fin, Integer idTienda) {
        List<Object[]> resultados;
        if (idTienda != null) {
            resultados = ventaRepository.findVentasByPeriodoAndTienda(inicio, fin, idTienda);
        } else {
            resultados = ventaRepository.findVentasByPeriodo(inicio, fin);
        }
        
        List<ReporteVentasDTO> reporte = new ArrayList<>();
        for (Object[] row : resultados) {
            reporte.add(new ReporteVentasDTO(
                (LocalDateTime) row[0],
                (String) row[1],
                (String) row[2],
                ((Number) row[3]).intValue(),
                (BigDecimal) row[4],
                (BigDecimal) row[5]
            ));
        }
        return reporte;
    }

    // Top productos más vendidos
    public List<ReporteProductosDTO> topProductos(LocalDateTime inicio, LocalDateTime fin, Integer limit) {
        List<Object[]> resultados = detalleVentaRepository.findTopProductos(inicio, fin, limit != null ? limit : 10);
        
        List<ReporteProductosDTO> reporte = new ArrayList<>();
        for (Object[] row : resultados) {
            reporte.add(new ReporteProductosDTO(
                ((Number) row[0]).intValue(),
                (String) row[1],
                (String) row[2],
                ((Number) row[3]).intValue(),
                (BigDecimal) row[4]
            ));
        }
        return reporte;
    }

    // Inventario bajo stock
    public List<ReporteInventarioDTO> inventarioBajoStock() {
        List<Object[]> resultados = inventarioRepository.findBajoStock();
        
        List<ReporteInventarioDTO> reporte = new ArrayList<>();
        for (Object[] row : resultados) {
            Integer stockActual = ((Number) row[3]).intValue();
            Integer stockMinimo = ((Number) row[4]).intValue();
            String estado = stockActual <= stockMinimo ? "CRÍTICO" : "OK";
            
            reporte.add(new ReporteInventarioDTO(
                ((Number) row[0]).intValue(),
                (String) row[1],
                (String) row[2],
                stockActual,
                stockMinimo,
                estado
            ));
        }
        return reporte;
    }

    // Reporte de mermas
    public List<ReporteMermasDTO> reporteMermas(LocalDateTime inicio, LocalDateTime fin, Integer idTienda) {
        List<Object[]> resultados;
        if (idTienda != null) {
            resultados = mermaRepository.findMermasByPeriodoAndTienda(inicio, fin, idTienda);
        } else {
            resultados = mermaRepository.findMermasByPeriodo(inicio, fin);
        }
        
        List<ReporteMermasDTO> reporte = new ArrayList<>();
        for (Object[] row : resultados) {
            reporte.add(new ReporteMermasDTO(
                (LocalDateTime) row[0],
                (String) row[1],
                (String) row[2],
                (String) row[3],
                ((Number) row[4]).intValue(),
                (String) row[5]
            ));
        }
        return reporte;
    }

    // Reporte de incidencias por estado
    public List<ReporteIncidenciasDTO> reporteIncidencias(Integer idEstado, Integer idTienda) {
        List<Object[]> resultados;
        if (idEstado != null && idTienda != null) {
            resultados = incidenciaRepository.findByEstadoAndTienda(idEstado, idTienda);
        } else if (idEstado != null) {
            resultados = incidenciaRepository.findByEstado(idEstado);
        } else if (idTienda != null) {
            resultados = incidenciaRepository.findByTienda(idTienda);
        } else {
            resultados = incidenciaRepository.findAllIncidenciasReporte();
        }
        
        List<ReporteIncidenciasDTO> reporte = new ArrayList<>();
        for (Object[] row : resultados) {
            reporte.add(new ReporteIncidenciasDTO(
                (String) row[0],
                (String) row[1],
                (String) row[2],
                (String) row[3],
                (String) row[4],
                (String) row[5],
                (LocalDateTime) row[6]
            ));
        }
        return reporte;
    }
}