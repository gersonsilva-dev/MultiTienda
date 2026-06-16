package com.techventa.multitienda.admin.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "est_estado_transferencia")
public class EstadoTransferencia {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_estado_transferencia")
    private Integer idEstadoTransferencia;

    @Column(name = "nombre_estado", nullable = false, length = 50)
    private String nombreEstado;

    @Column(name = "es_estado_final")
    private Boolean esEstadoFinal = false;

    @Column(name = "activo")
    private Boolean activo = true;

    @Column(name = "fecha_creacion", updatable = false)
    private LocalDateTime fechaCreacion;

    @Column(name = "fecha_actualizacion")
    private LocalDateTime fechaActualizacion;

    public EstadoTransferencia() {}

    public EstadoTransferencia(String nombreEstado) {
        this.nombreEstado = nombreEstado;
        this.activo = true;
    }

    // Getters y Setters
    public Integer getIdEstadoTransferencia() { return idEstadoTransferencia; }
    public void setIdEstadoTransferencia(Integer idEstadoTransferencia) { this.idEstadoTransferencia = idEstadoTransferencia; }
    public String getNombreEstado() { return nombreEstado; }
    public void setNombreEstado(String nombreEstado) { this.nombreEstado = nombreEstado; }
    public Boolean getEsEstadoFinal() { return esEstadoFinal; }
    public void setEsEstadoFinal(Boolean esEstadoFinal) { this.esEstadoFinal = esEstadoFinal; }
    public Boolean getActivo() { return activo; }
    public void setActivo(Boolean activo) { this.activo = activo; }
    public LocalDateTime getFechaCreacion() { return fechaCreacion; }
    public void setFechaCreacion(LocalDateTime fechaCreacion) { this.fechaCreacion = fechaCreacion; }
    public LocalDateTime getFechaActualizacion() { return fechaActualizacion; }
    public void setFechaActualizacion(LocalDateTime fechaActualizacion) { this.fechaActualizacion = fechaActualizacion; }

    @Override
    public String toString() {
        return "EstadoTransferencia{" + "idEstadoTransferencia=" + idEstadoTransferencia + ", nombreEstado='" + nombreEstado + '\'' + '}';
    }
}