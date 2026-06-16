package com.techventa.multitienda.admin.model;

import com.techventa.multitienda.admin.model.Oferta;
import com.techventa.multitienda.admin.model.Producto;
import jakarta.persistence.*;

@Entity
@Table(name = "detalle_oferta_producto")
public class DetalleOfertaProducto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_detalle_oferta")
    private Integer idDetalleOferta;

    @ManyToOne
    @JoinColumn(name = "id_oferta", nullable = false)
    private Oferta oferta;

    @ManyToOne
    @JoinColumn(name = "id_producto", nullable = false)
    private Producto producto;

    // Constructor vacío
    public DetalleOfertaProducto() {
    }

    // Constructor con parámetros
    public DetalleOfertaProducto(Oferta oferta, Producto producto) {
        this.oferta = oferta;
        this.producto = producto;
    }

    // Getters y Setters
    public Integer getIdDetalleOferta() {
        return idDetalleOferta;
    }

    public void setIdDetalleOferta(Integer idDetalleOferta) {
        this.idDetalleOferta = idDetalleOferta;
    }

    public Oferta getOferta() {
        return oferta;
    }

    public void setOferta(Oferta oferta) {
        this.oferta = oferta;
    }

    public Producto getProducto() {
        return producto;
    }

    public void setProducto(Producto producto) {
        this.producto = producto;
    }

    @Override
    public String toString() {
        return "DetalleOfertaProducto{" +
                "idDetalleOferta=" + idDetalleOferta +
                ", oferta=" + (oferta != null ? oferta.getNombreOferta() : "null") +
                ", producto=" + (producto != null ? producto.getNombreProducto() : "null") +
                '}';
    }
}