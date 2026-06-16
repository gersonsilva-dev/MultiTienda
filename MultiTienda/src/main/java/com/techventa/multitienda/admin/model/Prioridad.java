package com.techventa.multitienda.admin.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "cat_prioridades")
public class Prioridad {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_prioridad")
    private Integer idPrioridad;

    @Column(name = "nombre_prioridad", nullable = false, length = 50)
    private String nombrePrioridad;

    @Column(name = "nivel", nullable = false)
    private Integer nivel;

    @Column(name = "color_hex", length = 7)
    private String colorHex;

    @Column(name = "activo")
    private Boolean activo = true;

    @Column(name = "fecha_creacion", updatable = false)
    private LocalDateTime fechaCreacion;

    @Column(name = "fecha_actualizacion")
    private LocalDateTime fechaActualizacion;

    public Prioridad() {}

    public Prioridad(String nombrePrioridad, Integer nivel) {
        this.nombrePrioridad = nombrePrioridad;
        this.nivel = nivel;
        this.activo = true;
    }

    // Getters y Setters
    public Integer getIdPrioridad() { return idPrioridad; }
    public void setIdPrioridad(Integer idPrioridad) { this.idPrioridad = idPrioridad; }
    public String getNombrePrioridad() { return nombrePrioridad; }
    public void setNombrePrioridad(String nombrePrioridad) { this.nombrePrioridad = nombrePrioridad; }
    public Integer getNivel() { return nivel; }
    public void setNivel(Integer nivel) { this.nivel = nivel; }
    public String getColorHex() { return colorHex; }
    public void setColorHex(String colorHex) { this.colorHex = colorHex; }
    public Boolean getActivo() { return activo; }
    public void setActivo(Boolean activo) { this.activo = activo; }
    public LocalDateTime getFechaCreacion() { return fechaCreacion; }
    public void setFechaCreacion(LocalDateTime fechaCreacion) { this.fechaCreacion = fechaCreacion; }
    public LocalDateTime getFechaActualizacion() { return fechaActualizacion; }
    public void setFechaActualizacion(LocalDateTime fechaActualizacion) { this.fechaActualizacion = fechaActualizacion; }

    @Override
    public String toString() {
        return "Prioridad{" + "idPrioridad=" + idPrioridad + ", nombrePrioridad='" + nombrePrioridad + '\'' + ", nivel=" + nivel + '}';
    }
}