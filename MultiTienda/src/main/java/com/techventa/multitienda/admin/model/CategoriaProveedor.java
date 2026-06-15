package com.techventa.multitienda.admin.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "cat_categorias_proveedor")
public class CategoriaProveedor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_categoria_proveedor")
    private Integer idCategoriaProveedor;

    @Column(name = "nombre_categoria", nullable = false, length = 100)
    private String nombreCategoria;

    @Column(name = "activo")
    private Boolean activo = true;

    @Column(name = "fecha_creacion", updatable = false)
    private LocalDateTime fechaCreacion;

    @Column(name = "fecha_actualizacion")
    private LocalDateTime fechaActualizacion;

    // Constructor vacío
    public CategoriaProveedor() {
    }

    // Constructor con nombre
    public CategoriaProveedor(String nombreCategoria) {
        this.nombreCategoria = nombreCategoria;
        this.activo = true;
    }

    // Getters y Setters
    public Integer getIdCategoriaProveedor() {
        return idCategoriaProveedor;
    }

    public void setIdCategoriaProveedor(Integer idCategoriaProveedor) {
        this.idCategoriaProveedor = idCategoriaProveedor;
    }

    public String getNombreCategoria() {
        return nombreCategoria;
    }

    public void setNombreCategoria(String nombreCategoria) {
        this.nombreCategoria = nombreCategoria;
    }

    public Boolean getActivo() {
        return activo;
    }

    public void setActivo(Boolean activo) {
        this.activo = activo;
    }

    public LocalDateTime getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(LocalDateTime fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public LocalDateTime getFechaActualizacion() {
        return fechaActualizacion;
    }

    public void setFechaActualizacion(LocalDateTime fechaActualizacion) {
        this.fechaActualizacion = fechaActualizacion;
    }

    @Override
    public String toString() {
        return "CategoriaProveedor{" +
                "idCategoriaProveedor=" + idCategoriaProveedor +
                ", nombreCategoria='" + nombreCategoria + '\'' +
                ", activo=" + activo +
                '}';
    }
}