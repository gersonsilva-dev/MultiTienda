package com.techventa.multitienda.admin.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "est_estado_orden_compra")
public class EstadoOrdenCompra {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_estado_orden_compra")
    private Integer idEstadoOrdenCompra;

    @Column(name = "nombre_estado", nullable = false, length = 50)
    private String nombreEstado;

    @Column(name = "activo")
    private Boolean activo = true;

    @Column(name = "fecha_creacion", updatable = false)
    private LocalDateTime fechaCreacion;

    @Column(name = "fecha_actualizacion")
    private LocalDateTime fechaActualizacion;

    public EstadoOrdenCompra() {}

    public EstadoOrdenCompra(String nombreEstado) {
        this.nombreEstado = nombreEstado;
        this.activo = true;
    }

    // Getters y Setters
    public Integer getIdEstadoOrdenCompra() { return idEstadoOrdenCompra; }
    public void setIdEstadoOrdenCompra(Integer idEstadoOrdenCompra) { this.idEstadoOrdenCompra = idEstadoOrdenCompra; }
    public String getNombreEstado() { return nombreEstado; }
    public void setNombreEstado(String nombreEstado) { this.nombreEstado = nombreEstado; }
    public Boolean getActivo() { return activo; }
    public void setActivo(Boolean activo) { this.activo = activo; }
    public LocalDateTime getFechaCreacion() { return fechaCreacion; }
    public void setFechaCreacion(LocalDateTime fechaCreacion) { this.fechaCreacion = fechaCreacion; }
    public LocalDateTime getFechaActualizacion() { return fechaActualizacion; }
    public void setFechaActualizacion(LocalDateTime fechaActualizacion) { this.fechaActualizacion = fechaActualizacion; }
}