package com.techventa.multitienda.admin.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "incidencia_evidencias")
public class IncidenciaEvidencia {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_evidencia")
    private Integer idEvidencia;

    @ManyToOne
    @JoinColumn(name = "id_incidencia", nullable = false)
    private Incidencia incidencia;

    @Column(name = "evidencia_url", nullable = false, length = 500)
    private String evidenciaUrl;

    @Column(name = "descripcion", length = 255)
    private String descripcion;

    @Column(name = "fecha_subida", nullable = false)
    private LocalDateTime fechaSubida;

    public IncidenciaEvidencia() {}

    public IncidenciaEvidencia(Incidencia incidencia, String evidenciaUrl, LocalDateTime fechaSubida) {
        this.incidencia = incidencia;
        this.evidenciaUrl = evidenciaUrl;
        this.fechaSubida = fechaSubida;
    }

    // Getters y Setters
    public Integer getIdEvidencia() { return idEvidencia; }
    public void setIdEvidencia(Integer idEvidencia) { this.idEvidencia = idEvidencia; }
    public Incidencia getIncidencia() { return incidencia; }
    public void setIncidencia(Incidencia incidencia) { this.incidencia = incidencia; }
    public String getEvidenciaUrl() { return evidenciaUrl; }
    public void setEvidenciaUrl(String evidenciaUrl) { this.evidenciaUrl = evidenciaUrl; }
    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }
    public LocalDateTime getFechaSubida() { return fechaSubida; }
    public void setFechaSubida(LocalDateTime fechaSubida) { this.fechaSubida = fechaSubida; }

    @Override
    public String toString() {
        return "IncidenciaEvidencia{" + "idEvidencia=" + idEvidencia + ", evidenciaUrl='" + evidenciaUrl + '\'' + '}';
    }
}