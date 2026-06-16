package com.techventa.multitienda.admin.model;

import com.techventa.multitienda.admin.model.Oferta;
import com.techventa.multitienda.admin.model.Tienda;
import jakarta.persistence.*;

@Entity
@Table(name = "detalle_oferta_tienda")
public class DetalleOfertaTienda {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_detalle_oferta_tienda")
    private Integer idDetalleOfertaTienda;

    @ManyToOne
    @JoinColumn(name = "id_oferta", nullable = false)
    private Oferta oferta;

    @ManyToOne
    @JoinColumn(name = "id_tienda", nullable = false)
    private Tienda tienda;

    // Constructor vacío
    public DetalleOfertaTienda() {
    }

    // Constructor con parámetros
    public DetalleOfertaTienda(Oferta oferta, Tienda tienda) {
        this.oferta = oferta;
        this.tienda = tienda;
    }

    // Getters y Setters
    public Integer getIdDetalleOfertaTienda() {
        return idDetalleOfertaTienda;
    }

    public void setIdDetalleOfertaTienda(Integer idDetalleOfertaTienda) {
        this.idDetalleOfertaTienda = idDetalleOfertaTienda;
    }

    public Oferta getOferta() {
        return oferta;
    }

    public void setOferta(Oferta oferta) {
        this.oferta = oferta;
    }

    public Tienda getTienda() {
        return tienda;
    }

    public void setTienda(Tienda tienda) {
        this.tienda = tienda;
    }

    @Override
    public String toString() {
        return "DetalleOfertaTienda{" +
                "idDetalleOfertaTienda=" + idDetalleOfertaTienda +
                ", oferta=" + (oferta != null ? oferta.getNombreOferta() : "null") +
                ", tienda=" + (tienda != null ? tienda.getNombreTienda() : "null") +
                '}';
    }
}