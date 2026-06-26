package com.techventa.multitienda.cajero.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "est_estado_devolucion")
public class EstadoDevolucion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_estado_devolucion")
    private Integer idEstadoDevolucion;

    @Column(name = "nombre_estado", nullable = false, length = 50)
    private String nombreEstado;

    @Column(name = "activo")
    private Boolean activo = true;

    @Column(name = "fecha_creacion", updatable = false)
    private LocalDateTime fechaCreacion;

    public EstadoDevolucion() {}

    public EstadoDevolucion(String nombreEstado) {
        this.nombreEstado = nombreEstado;
        this.activo = true;
    }

    public Integer getIdEstadoDevolucion() { return idEstadoDevolucion; }
    public void setIdEstadoDevolucion(Integer idEstadoDevolucion) { this.idEstadoDevolucion = idEstadoDevolucion; }
    public String getNombreEstado() { return nombreEstado; }
    public void setNombreEstado(String nombreEstado) { this.nombreEstado = nombreEstado; }
    public Boolean getActivo() { return activo; }
    public void setActivo(Boolean activo) { this.activo = activo; }
    public LocalDateTime getFechaCreacion() { return fechaCreacion; }
    public void setFechaCreacion(LocalDateTime fechaCreacion) { this.fechaCreacion = fechaCreacion; }
}