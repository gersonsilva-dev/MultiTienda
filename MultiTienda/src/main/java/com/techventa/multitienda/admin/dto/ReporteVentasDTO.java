package com.techventa.multitienda.admin.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class ReporteVentasDTO {
    private LocalDateTime fecha;
    private String tienda;
    private String cajero;
    private Integer cantidadVentas;
    private BigDecimal totalVentas;
    private BigDecimal promedioVenta;

    public ReporteVentasDTO(LocalDateTime fecha, String tienda, String cajero, 
                            Integer cantidadVentas, BigDecimal totalVentas, BigDecimal promedioVenta) {
        this.fecha = fecha;
        this.tienda = tienda;
        this.cajero = cajero;
        this.cantidadVentas = cantidadVentas;
        this.totalVentas = totalVentas;
        this.promedioVenta = promedioVenta;
    }

    // Getters y Setters
    public LocalDateTime getFecha() { return fecha; }
    public void setFecha(LocalDateTime fecha) { this.fecha = fecha; }
    public String getTienda() { return tienda; }
    public void setTienda(String tienda) { this.tienda = tienda; }
    public String getCajero() { return cajero; }
    public void setCajero(String cajero) { this.cajero = cajero; }
    public Integer getCantidadVentas() { return cantidadVentas; }
    public void setCantidadVentas(Integer cantidadVentas) { this.cantidadVentas = cantidadVentas; }
    public BigDecimal getTotalVentas() { return totalVentas; }
    public void setTotalVentas(BigDecimal totalVentas) { this.totalVentas = totalVentas; }
    public BigDecimal getPromedioVenta() { return promedioVenta; }
    public void setPromedioVenta(BigDecimal promedioVenta) { this.promedioVenta = promedioVenta; }
}