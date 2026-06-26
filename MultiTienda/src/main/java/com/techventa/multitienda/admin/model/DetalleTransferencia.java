package com.techventa.multitienda.admin.model;

import jakarta.persistence.*;

@Entity
@Table(name = "detalle_transferencia")
public class DetalleTransferencia {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_detalle_transferencia")
    private Integer idDetalleTransferencia;

    @ManyToOne
    @JoinColumn(name = "id_transferencia", nullable = false)
    private Transferencia transferencia;

    @ManyToOne
    @JoinColumn(name = "id_producto", nullable = false)
    private Producto producto;

    @Column(name = "cantidad", nullable = false)
    private Integer cantidad;

    public DetalleTransferencia() {}

    public DetalleTransferencia(Transferencia transferencia, Producto producto, Integer cantidad) {
        this.transferencia = transferencia;
        this.producto = producto;
        this.cantidad = cantidad;
    }

    // Getters y Setters
    public Integer getIdDetalleTransferencia() { return idDetalleTransferencia; }
    public void setIdDetalleTransferencia(Integer idDetalleTransferencia) { this.idDetalleTransferencia = idDetalleTransferencia; }
    public Transferencia getTransferencia() { return transferencia; }
    public void setTransferencia(Transferencia transferencia) { this.transferencia = transferencia; }
    public Producto getProducto() { return producto; }
    public void setProducto(Producto producto) { this.producto = producto; }
    public Integer getCantidad() { return cantidad; }
    public void setCantidad(Integer cantidad) { this.cantidad = cantidad; }

    @Override
    public String toString() {
        return "DetalleTransferencia{" + "idDetalleTransferencia=" + idDetalleTransferencia + ", cantidad=" + cantidad + '}';
    }
}