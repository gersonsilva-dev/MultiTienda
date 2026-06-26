package com.techventa.multitienda.cajero.model;

import jakarta.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "detalle_devolucion")
public class DetalleDevolucion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_detalle_devolucion")
    private Integer idDetalleDevolucion;

    @ManyToOne
    @JoinColumn(name = "id_devolucion", nullable = false)
    private Devolucion devolucion;

    @ManyToOne
    @JoinColumn(name = "id_detalle_venta", nullable = false)
    private DetalleVenta detalleVenta;

    @Column(name = "cantidad", nullable = false)
    private Integer cantidad;

    @Column(name = "monto_devuelto", nullable = false, precision = 12, scale = 2)
    private BigDecimal montoDevuelto;

    public DetalleDevolucion() {}

    public Integer getIdDetalleDevolucion() { return idDetalleDevolucion; }
    public void setIdDetalleDevolucion(Integer idDetalleDevolucion) { this.idDetalleDevolucion = idDetalleDevolucion; }
    public Devolucion getDevolucion() { return devolucion; }
    public void setDevolucion(Devolucion devolucion) { this.devolucion = devolucion; }
    public DetalleVenta getDetalleVenta() { return detalleVenta; }
    public void setDetalleVenta(DetalleVenta detalleVenta) { this.detalleVenta = detalleVenta; }
    public Integer getCantidad() { return cantidad; }
    public void setCantidad(Integer cantidad) { this.cantidad = cantidad; }
    public BigDecimal getMontoDevuelto() { return montoDevuelto; }
    public void setMontoDevuelto(BigDecimal montoDevuelto) { this.montoDevuelto = montoDevuelto; }
}