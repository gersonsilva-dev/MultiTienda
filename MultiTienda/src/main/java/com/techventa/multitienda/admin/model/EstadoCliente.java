package com.techventa.multitienda.admin.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "est_estado_cliente")
public class EstadoCliente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_estado_cliente")
    private Integer idEstadoCliente;

    @Column(name = "nombre_estado", nullable = false, length = 50)
    private String nombreEstado;

    @Column(name = "permite_compras")
    private Boolean permiteCompras = true;

    @Column(name = "activo")
    private Boolean activo = true;

    @Column(name = "fecha_creacion", updatable = false)
    private LocalDateTime fechaCreacion;

    @Column(name = "fecha_actualizacion")
    private LocalDateTime fechaActualizacion;

    // Constructor vacío
    public EstadoCliente() {
    }

    // Constructor con nombre
    public EstadoCliente(String nombreEstado, Boolean permiteCompras) {
        this.nombreEstado = nombreEstado;
        this.permiteCompras = permiteCompras;
        this.activo = true;
    }

    // Getters y Setters
    public Integer getIdEstadoCliente() {
        return idEstadoCliente;
    }

    public void setIdEstadoCliente(Integer idEstadoCliente) {
        this.idEstadoCliente = idEstadoCliente;
    }

    public String getNombreEstado() {
        return nombreEstado;
    }

    public void setNombreEstado(String nombreEstado) {
        this.nombreEstado = nombreEstado;
    }

    public Boolean getPermiteCompras() {
        return permiteCompras;
    }

    public void setPermiteCompras(Boolean permiteCompras) {
        this.permiteCompras = permiteCompras;
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
        return "EstadoCliente{" +
                "idEstadoCliente=" + idEstadoCliente +
                ", nombreEstado='" + nombreEstado + '\'' +
                ", permiteCompras=" + permiteCompras +
                '}';
    }
}