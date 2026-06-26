package com.techventa.multitienda.cajero.dto;

import com.techventa.multitienda.cajero.model.Venta;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class VentaDTO {
    private Integer idVenta;
    private String codigoVenta;
    private String cajero;
    private String tienda;
    private String tipoComprobante;
    private BigDecimal total;
    private String metodoPago;
    private LocalDateTime fechaVenta;
    private String estado;
    private List<DetalleVentaDTO> detalles = new ArrayList<>();

    public VentaDTO() {}

    public VentaDTO(Venta venta) {
        this.idVenta = venta.getIdVenta();
        this.codigoVenta = venta.getCodigoVenta();
        
        // 🔥 OBTENER EL NOMBRE REAL DEL CAJERO
        if (venta.getCajero() != null) {
            String nombres = venta.getCajero().getNombres() != null ? venta.getCajero().getNombres() : "";
            String apellidos = venta.getCajero().getApellidos() != null ? venta.getCajero().getApellidos() : "";
            this.cajero = (nombres + " " + apellidos).trim();
        } else {
            this.cajero = "Cajero no asignado";
        }
        
        this.tienda = venta.getTienda() != null && venta.getTienda().getNombreTienda() != null 
            ? venta.getTienda().getNombreTienda() : "";
        this.tipoComprobante = venta.getTipoComprobante() != null && venta.getTipoComprobante().getNombreComprobante() != null
            ? venta.getTipoComprobante().getNombreComprobante() : "Boleta";
        this.total = venta.getTotal();
        this.metodoPago = venta.getMetodoPago() != null ? venta.getMetodoPago() : "EFECTIVO";
        this.fechaVenta = venta.getFechaVenta();
        this.estado = venta.getEstadoVenta() != null && venta.getEstadoVenta().getNombreEstado() != null
            ? venta.getEstadoVenta().getNombreEstado() : "COMPLETADO";
    }

    // Getters y Setters
    public Integer getIdVenta() { return idVenta; }
    public void setIdVenta(Integer idVenta) { this.idVenta = idVenta; }
    public String getCodigoVenta() { return codigoVenta; }
    public void setCodigoVenta(String codigoVenta) { this.codigoVenta = codigoVenta; }
    public String getCajero() { return cajero; }
    public void setCajero(String cajero) { this.cajero = cajero; }
    public String getTienda() { return tienda; }
    public void setTienda(String tienda) { this.tienda = tienda; }
    public String getTipoComprobante() { return tipoComprobante; }
    public void setTipoComprobante(String tipoComprobante) { this.tipoComprobante = tipoComprobante; }
    public BigDecimal getTotal() { return total; }
    public void setTotal(BigDecimal total) { this.total = total; }
    public String getMetodoPago() { return metodoPago; }
    public void setMetodoPago(String metodoPago) { this.metodoPago = metodoPago; }
    public LocalDateTime getFechaVenta() { return fechaVenta; }
    public void setFechaVenta(LocalDateTime fechaVenta) { this.fechaVenta = fechaVenta; }
    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }
    public List<DetalleVentaDTO> getDetalles() { return detalles; }
    public void setDetalles(List<DetalleVentaDTO> detalles) { this.detalles = detalles; }
}