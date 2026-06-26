package com.techventa.multitienda.cajero.dto;

import com.techventa.multitienda.cajero.model.DetalleVenta;
import java.math.BigDecimal;

public class DetalleVentaDTO {
    private Integer idDetalleVenta;
    private String producto;
    private Integer cantidad;
    private BigDecimal precioUnitario;
    private BigDecimal subtotalLinea;

    public DetalleVentaDTO() {}

    public DetalleVentaDTO(DetalleVenta detalle) {
        this.idDetalleVenta = detalle.getIdDetalleVenta();
        // 🔥 OBTENER EL NOMBRE REAL DEL PRODUCTO
        this.producto = detalle.getProducto() != null && detalle.getProducto().getNombreProducto() != null 
            ? detalle.getProducto().getNombreProducto() 
            : "Producto sin nombre";
        this.cantidad = detalle.getCantidad();
        this.precioUnitario = detalle.getPrecioUnitario();
        this.subtotalLinea = detalle.getSubtotalLinea();
    }

    // Getters y Setters
    public Integer getIdDetalleVenta() { return idDetalleVenta; }
    public void setIdDetalleVenta(Integer idDetalleVenta) { this.idDetalleVenta = idDetalleVenta; }
    public String getProducto() { return producto; }
    public void setProducto(String producto) { this.producto = producto; }
    public Integer getCantidad() { return cantidad; }
    public void setCantidad(Integer cantidad) { this.cantidad = cantidad; }
    public BigDecimal getPrecioUnitario() { return precioUnitario; }
    public void setPrecioUnitario(BigDecimal precioUnitario) { this.precioUnitario = precioUnitario; }
    public BigDecimal getSubtotalLinea() { return subtotalLinea; }
    public void setSubtotalLinea(BigDecimal subtotalLinea) { this.subtotalLinea = subtotalLinea; }
}