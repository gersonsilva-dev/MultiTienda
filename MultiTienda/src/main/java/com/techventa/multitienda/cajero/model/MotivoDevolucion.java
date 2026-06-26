package com.techventa.multitienda.cajero.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "cat_motivos_devolucion")
public class MotivoDevolucion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_motivo_devolucion")
    private Integer idMotivoDevolucion;

    @Column(name = "nombre_motivo", nullable = false, length = 100)
    private String nombreMotivo;

    @Column(name = "activo")
    private Boolean activo = true;

    @Column(name = "fecha_creacion", updatable = false)
    private LocalDateTime fechaCreacion;

    public MotivoDevolucion() {}

    public MotivoDevolucion(String nombreMotivo) {
        this.nombreMotivo = nombreMotivo;
        this.activo = true;
    }

    public Integer getIdMotivoDevolucion() { return idMotivoDevolucion; }
    public void setIdMotivoDevolucion(Integer idMotivoDevolucion) { this.idMotivoDevolucion = idMotivoDevolucion; }
    public String getNombreMotivo() { return nombreMotivo; }
    public void setNombreMotivo(String nombreMotivo) { this.nombreMotivo = nombreMotivo; }
    public Boolean getActivo() { return activo; }
    public void setActivo(Boolean activo) { this.activo = activo; }
    public LocalDateTime getFechaCreacion() { return fechaCreacion; }
    public void setFechaCreacion(LocalDateTime fechaCreacion) { this.fechaCreacion = fechaCreacion; }
}