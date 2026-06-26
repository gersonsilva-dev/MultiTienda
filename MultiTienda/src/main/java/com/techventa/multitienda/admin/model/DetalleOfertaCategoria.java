package com.techventa.multitienda.admin.model;

import com.techventa.multitienda.admin.model.Oferta;
import com.techventa.multitienda.admin.model.CategoriaProducto;
import jakarta.persistence.*;

@Entity
@Table(name = "detalle_oferta_categoria")
public class DetalleOfertaCategoria {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_detalle_oferta_categoria")
    private Integer idDetalleOfertaCategoria;

    @ManyToOne
    @JoinColumn(name = "id_oferta", nullable = false)
    private Oferta oferta;

    @ManyToOne
    @JoinColumn(name = "id_categoria", nullable = false)
    private CategoriaProducto categoria;

    // Constructor vacío
    public DetalleOfertaCategoria() {
    }

    // Constructor con parámetros
    public DetalleOfertaCategoria(Oferta oferta, CategoriaProducto categoria) {
        this.oferta = oferta;
        this.categoria = categoria;
    }

    // Getters y Setters
    public Integer getIdDetalleOfertaCategoria() {
        return idDetalleOfertaCategoria;
    }

    public void setIdDetalleOfertaCategoria(Integer idDetalleOfertaCategoria) {
        this.idDetalleOfertaCategoria = idDetalleOfertaCategoria;
    }

    public Oferta getOferta() {
        return oferta;
    }

    public void setOferta(Oferta oferta) {
        this.oferta = oferta;
    }

    public CategoriaProducto getCategoria() {
        return categoria;
    }

    public void setCategoria(CategoriaProducto categoria) {
        this.categoria = categoria;
    }

    @Override
    public String toString() {
        return "DetalleOfertaCategoria{" +
                "idDetalleOfertaCategoria=" + idDetalleOfertaCategoria +
                ", oferta=" + (oferta != null ? oferta.getNombreOferta() : "null") +
                ", categoria=" + (categoria != null ? categoria.getNombreCategoria() : "null") +
                '}';
    }
}