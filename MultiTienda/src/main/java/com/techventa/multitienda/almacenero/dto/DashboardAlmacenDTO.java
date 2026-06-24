package com.techventa.multitienda.almacenero.dto;

import java.util.List;
import java.util.Map;

public class DashboardAlmacenDTO {
    // 1. Indicadores Principales (KPIs)
    private int stockTotal;
    private long stockCriticoCount;
    private long pendientesValidarCount;
    private double valorMermas;

    // 2. Listados Inferiores (Estructuras personalizadas o Mapas)
    private List<Map<String, Object>> productosCriticos;
    private List<Map<String, Object>> pedidosCamino;
    private List<Map<String, Object>> actividadReciente;

    // Constructores, Getters y Setters
    public DashboardAlmacenDTO() {}

    public int getStockTotal() { return stockTotal; }
    public void setStockTotal(int stockTotal) { this.stockTotal = stockTotal; }

    public long getStockCriticoCount() { return stockCriticoCount; }
    public void setStockCriticoCount(long stockCriticoCount) { this.stockCriticoCount = stockCriticoCount; }

    public long getPendientesValidarCount() { return pendientesValidarCount; }
    public void setPendientesValidarCount(long pendientesValidarCount) { this.pendientesValidarCount = pendientesValidarCount; }

    public double getValorMermas() { return valorMermas; }
    public void setValorMermas(double valorMermas) { this.valorMermas = valorMermas; }

    public List<Map<String, Object>> getProductosCriticos() { return productosCriticos; }
    public void setProductosCriticos(List<Map<String, Object>> productosCriticos) { this.productosCriticos = productosCriticos; }

    public List<Map<String, Object>> getPedidosCamino() { return pedidosCamino; }
    public void setPedidosCamino(List<Map<String, Object>> pedidosCamino) { this.pedidosCamino = pedidosCamino; }

    public List<Map<String, Object>> getActividadReciente() { return actividadReciente; }
    public void setActividadReciente(List<Map<String, Object>> actividadReciente) { this.actividadReciente = actividadReciente; }
}