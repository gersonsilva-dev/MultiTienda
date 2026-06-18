package com.techventa.multitienda.cajero.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "cat_tipos_comprobante")
public class TipoComprobante {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_tipo_comprobante")
    private Integer idTipoComprobante;

    @Column(name = "nombre_comprobante", nullable = false, length = 50)
    private String nombreComprobante;

    @Column(name = "serie", length = 5)
    private String serie;

    @Column(name = "correlativo_actual")
    private Integer correlativoActual = 1;

    @Column(name = "activo")
    private Boolean activo = true;

    @Column(name = "fecha_creacion", updatable = false)
    private LocalDateTime fechaCreacion;

    @Column(name = "fecha_actualizacion")
    private LocalDateTime fechaActualizacion;

    public TipoComprobante() {}

    public TipoComprobante(String nombreComprobante, String serie) {
        this.nombreComprobante = nombreComprobante;
        this.serie = serie;
        this.activo = true;
    }

    // Getters y Setters
    public Integer getIdTipoComprobante() { return idTipoComprobante; }
    public void setIdTipoComprobante(Integer idTipoComprobante) { this.idTipoComprobante = idTipoComprobante; }
    public String getNombreComprobante() { return nombreComprobante; }
    public void setNombreComprobante(String nombreComprobante) { this.nombreComprobante = nombreComprobante; }
    public String getSerie() { return serie; }
    public void setSerie(String serie) { this.serie = serie; }
    public Integer getCorrelativoActual() { return correlativoActual; }
    public void setCorrelativoActual(Integer correlativoActual) { this.correlativoActual = correlativoActual; }
    public Boolean getActivo() { return activo; }
    public void setActivo(Boolean activo) { this.activo = activo; }
    public LocalDateTime getFechaCreacion() { return fechaCreacion; }
    public void setFechaCreacion(LocalDateTime fechaCreacion) { this.fechaCreacion = fechaCreacion; }
    public LocalDateTime getFechaActualizacion() { return fechaActualizacion; }
    public void setFechaActualizacion(LocalDateTime fechaActualizacion) { this.fechaActualizacion = fechaActualizacion; }
}