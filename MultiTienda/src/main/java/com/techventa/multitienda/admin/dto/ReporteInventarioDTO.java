package com.techventa.multitienda.admin.dto;

public class ReporteInventarioDTO {
    private Integer idProducto;
    private String nombreProducto;
    private String tienda;
    private Integer stockActual;
    private Integer stockMinimo;
    private String estado;

    public ReporteInventarioDTO(Integer idProducto, String nombreProducto, String tienda,
                                Integer stockActual, Integer stockMinimo, String estado) {
        this.idProducto = idProducto;
        this.nombreProducto = nombreProducto;
        this.tienda = tienda;
        this.stockActual = stockActual;
        this.stockMinimo = stockMinimo;
        this.estado = estado;
    }

    // Getters y Setters
    public Integer getIdProducto() { return idProducto; }
    public void setIdProducto(Integer idProducto) { this.idProducto = idProducto; }
    public String getNombreProducto() { return nombreProducto; }
    public void setNombreProducto(String nombreProducto) { this.nombreProducto = nombreProducto; }
    public String getTienda() { return tienda; }
    public void setTienda(String tienda) { this.tienda = tienda; }
    public Integer getStockActual() { return stockActual; }
    public void setStockActual(Integer stockActual) { this.stockActual = stockActual; }
    public Integer getStockMinimo() { return stockMinimo; }
    public void setStockMinimo(Integer stockMinimo) { this.stockMinimo = stockMinimo; }
    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }
}