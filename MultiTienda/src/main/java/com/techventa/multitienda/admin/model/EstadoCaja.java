package com.techventa.multitienda.admin.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "est_estado_caja")
public class EstadoCaja {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_estado_caja")
    private Integer idEstadoCaja;

    @Column(name = "nombre_estado", nullable = false, length = 50)
    private String nombreEstado;

    @Column(name = "permite_ventas")
    private Boolean permiteVentas = false;

    @Column(name = "activo")
    private Boolean activo = true;

    @Column(name = "fecha_creacion", updatable = false)
    private LocalDateTime fechaCreacion;

    @Column(name = "fecha_actualizacion")
    private LocalDateTime fechaActualizacion;

    // Constructor vacío
    public EstadoCaja() {
    }

    // Constructor con nombre
    public EstadoCaja(String nombreEstado, Boolean permiteVentas) {
        this.nombreEstado = nombreEstado;
        this.permiteVentas = permiteVentas;
        this.activo = true;
    }

    // Getters y Setters
    public Integer getIdEstadoCaja() {
        return idEstadoCaja;
    }

    public void setIdEstadoCaja(Integer idEstadoCaja) {
        this.idEstadoCaja = idEstadoCaja;
    }

    public String getNombreEstado() {
        return nombreEstado;
    }

    public void setNombreEstado(String nombreEstado) {
        this.nombreEstado = nombreEstado;
    }

    public Boolean getPermiteVentas() {
        return permiteVentas;
    }

    public void setPermiteVentas(Boolean permiteVentas) {
        this.permiteVentas = permiteVentas;
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
        return "EstadoCaja{" +
                "idEstadoCaja=" + idEstadoCaja +
                ", nombreEstado='" + nombreEstado + '\'' +
                ", permiteVentas=" + permiteVentas +
                '}';
    }
}