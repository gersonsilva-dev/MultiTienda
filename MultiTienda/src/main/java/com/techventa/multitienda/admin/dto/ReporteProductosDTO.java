package com.techventa.multitienda.admin.dto;

import java.math.BigDecimal;

public class ReporteProductosDTO {
    private Integer idProducto;
    private String nombreProducto;
    private String categoria;
    private Integer cantidadVendida;
    private BigDecimal totalVendido;

    public ReporteProductosDTO(Integer idProducto, String nombreProducto, String categoria, 
                               Integer cantidadVendida, BigDecimal totalVendido) {
        this.idProducto = idProducto;
        this.nombreProducto = nombreProducto;
        this.categoria = categoria;
        this.cantidadVendida = cantidadVendida;
        this.totalVendido = totalVendido;
    }

    // Getters y Setters
    public Integer getIdProducto() { return idProducto; }
    public void setIdProducto(Integer idProducto) { this.idProducto = idProducto; }
    public String getNombreProducto() { return nombreProducto; }
    public void setNombreProducto(String nombreProducto) { this.nombreProducto = nombreProducto; }
    public String getCategoria() { return categoria; }
    public void setCategoria(String categoria) { this.categoria = categoria; }
    public Integer getCantidadVendida() { return cantidadVendida; }
    public void setCantidadVendida(Integer cantidadVendida) { this.cantidadVendida = cantidadVendida; }
    public BigDecimal getTotalVendido() { return totalVendido; }
    public void setTotalVendido(BigDecimal totalVendido) { this.totalVendido = totalVendido; }
}