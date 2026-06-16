package com.techventa.multitienda.admin.dto;

import java.time.LocalDateTime;

public class ReporteMermasDTO {
    private LocalDateTime fecha;
    private String producto;
    private String tienda;
    private String motivo;
    private Integer cantidad;
    private String almacenero;

    public ReporteMermasDTO(LocalDateTime fecha, String producto, String tienda,
                            String motivo, Integer cantidad, String almacenero) {
        this.fecha = fecha;
        this.producto = producto;
        this.tienda = tienda;
        this.motivo = motivo;
        this.cantidad = cantidad;
        this.almacenero = almacenero;
    }

    // Getters y Setters
    public LocalDateTime getFecha() { return fecha; }
    public void setFecha(LocalDateTime fecha) { this.fecha = fecha; }
    public String getProducto() { return producto; }
    public void setProducto(String producto) { this.producto = producto; }
    public String getTienda() { return tienda; }
    public void setTienda(String tienda) { this.tienda = tienda; }
    public String getMotivo() { return motivo; }
    public void setMotivo(String motivo) { this.motivo = motivo; }
    public Integer getCantidad() { return cantidad; }
    public void setCantidad(Integer cantidad) { this.cantidad = cantidad; }
    public String getAlmacenero() { return almacenero; }
    public void setAlmacenero(String almacenero) { this.almacenero = almacenero; }
}