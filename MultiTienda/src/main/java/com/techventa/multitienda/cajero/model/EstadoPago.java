package com.techventa.multitienda.cajero.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "est_estado_pago")
public class EstadoPago {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_estado_pago")
    private Integer idEstadoPago;

    @Column(name = "nombre_estado", nullable = false, length = 50)
    private String nombreEstado;

    @Column(name = "activo")
    private Boolean activo = true;

    @Column(name = "fecha_creacion", updatable = false)
    private LocalDateTime fechaCreacion;

    @Column(name = "fecha_actualizacion")
    private LocalDateTime fechaActualizacion;

    public EstadoPago() {}

    public EstadoPago(String nombreEstado) {
        this.nombreEstado = nombreEstado;
        this.activo = true;
    }

    // Getters y Setters
    public Integer getIdEstadoPago() { return idEstadoPago; }
    public void setIdEstadoPago(Integer idEstadoPago) { this.idEstadoPago = idEstadoPago; }
    public String getNombreEstado() { return nombreEstado; }
    public void setNombreEstado(String nombreEstado) { this.nombreEstado = nombreEstado; }
    public Boolean getActivo() { return activo; }
    public void setActivo(Boolean activo) { this.activo = activo; }
    public LocalDateTime getFechaCreacion() { return fechaCreacion; }
    public void setFechaCreacion(LocalDateTime fechaCreacion) { this.fechaCreacion = fechaCreacion; }
    public LocalDateTime getFechaActualizacion() { return fechaActualizacion; }
    public void setFechaActualizacion(LocalDateTime fechaActualizacion) { this.fechaActualizacion = fechaActualizacion; }
}