package com.techventa.multitienda.cajero.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "est_estado_venta")
public class EstadoVenta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_estado_venta")
    private Integer idEstadoVenta;

    @Column(name = "nombre_estado", nullable = false, length = 50)
    private String nombreEstado;

    @Column(name = "permite_anular")
    private Boolean permiteAnular = false;

    @Column(name = "permite_modificar")
    private Boolean permiteModificar = false;

    @Column(name = "permite_reembolso")
    private Boolean permiteReembolso = false;

    @Column(name = "activo")
    private Boolean activo = true;

    @Column(name = "fecha_creacion", updatable = false)
    private LocalDateTime fechaCreacion;

    @Column(name = "fecha_actualizacion")
    private LocalDateTime fechaActualizacion;

    public EstadoVenta() {}

    public EstadoVenta(String nombreEstado) {
        this.nombreEstado = nombreEstado;
        this.activo = true;
    }

    // Getters y Setters
    public Integer getIdEstadoVenta() { return idEstadoVenta; }
    public void setIdEstadoVenta(Integer idEstadoVenta) { this.idEstadoVenta = idEstadoVenta; }
    public String getNombreEstado() { return nombreEstado; }
    public void setNombreEstado(String nombreEstado) { this.nombreEstado = nombreEstado; }
    public Boolean getPermiteAnular() { return permiteAnular; }
    public void setPermiteAnular(Boolean permiteAnular) { this.permiteAnular = permiteAnular; }
    public Boolean getPermiteModificar() { return permiteModificar; }
    public void setPermiteModificar(Boolean permiteModificar) { this.permiteModificar = permiteModificar; }
    public Boolean getPermiteReembolso() { return permiteReembolso; }
    public void setPermiteReembolso(Boolean permiteReembolso) { this.permiteReembolso = permiteReembolso; }
    public Boolean getActivo() { return activo; }
    public void setActivo(Boolean activo) { this.activo = activo; }
    public LocalDateTime getFechaCreacion() { return fechaCreacion; }
    public void setFechaCreacion(LocalDateTime fechaCreacion) { this.fechaCreacion = fechaCreacion; }
    public LocalDateTime getFechaActualizacion() { return fechaActualizacion; }
    public void setFechaActualizacion(LocalDateTime fechaActualizacion) { this.fechaActualizacion = fechaActualizacion; }
}