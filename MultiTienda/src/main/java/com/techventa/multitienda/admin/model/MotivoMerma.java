package com.techventa.multitienda.admin.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "cat_motivos_merma")
public class MotivoMerma {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_motivo_merma")
    private Integer idMotivoMerma;

    @Column(name = "nombre_motivo", nullable = false, length = 100)
    private String nombreMotivo;

    @Column(name = "activo")
    private Boolean activo = true;

    @Column(name = "fecha_creacion", updatable = false)
    private LocalDateTime fechaCreacion;

    @Column(name = "fecha_actualizacion")
    private LocalDateTime fechaActualizacion;

    public MotivoMerma() {}

    public MotivoMerma(String nombreMotivo) {
        this.nombreMotivo = nombreMotivo;
        this.activo = true;
    }

    // Getters y Setters
    public Integer getIdMotivoMerma() { return idMotivoMerma; }
    public void setIdMotivoMerma(Integer idMotivoMerma) { this.idMotivoMerma = idMotivoMerma; }
    public String getNombreMotivo() { return nombreMotivo; }
    public void setNombreMotivo(String nombreMotivo) { this.nombreMotivo = nombreMotivo; }
    public Boolean getActivo() { return activo; }
    public void setActivo(Boolean activo) { this.activo = activo; }
    public LocalDateTime getFechaCreacion() { return fechaCreacion; }
    public void setFechaCreacion(LocalDateTime fechaCreacion) { this.fechaCreacion = fechaCreacion; }
    public LocalDateTime getFechaActualizacion() { return fechaActualizacion; }
    public void setFechaActualizacion(LocalDateTime fechaActualizacion) { this.fechaActualizacion = fechaActualizacion; }

    @Override
    public String toString() {
        return "MotivoMerma{" + "idMotivoMerma=" + idMotivoMerma + ", nombreMotivo='" + nombreMotivo + '\'' + '}';
    }
}