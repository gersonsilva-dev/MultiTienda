package com.techventa.multitienda.admin.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "incidencias")
public class Incidencia {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_incidencia")
    private Integer idIncidencia;

    @Column(name = "codigo_incidencia", unique = true, length = 50)
    private String codigoIncidencia;

    @ManyToOne
    @JoinColumn(name = "id_supervisor", nullable = false)
    private Usuario supervisor;

    @ManyToOne
    @JoinColumn(name = "id_tienda", nullable = false)
    private Tienda tienda;

    @ManyToOne
    @JoinColumn(name = "id_tipo_incidencia", nullable = false)
    private TipoIncidencia tipoIncidencia;

    @ManyToOne
    @JoinColumn(name = "id_prioridad", nullable = false)
    private Prioridad prioridad;

    @Column(name = "titulo", nullable = false, length = 200)
    private String titulo;

    @Column(name = "descripcion_problema", nullable = false)
    private String descripcionProblema;

    @Column(name = "recomendacion_admin")
    private String recomendacionAdmin;

    @ManyToOne
    @JoinColumn(name = "id_estado_incidencia")
    private EstadoIncidencia estadoIncidencia;

    @ManyToOne
    @JoinColumn(name = "id_admin_resuelve")
    private Usuario adminResuelve;

    @Column(name = "respuesta_admin")
    private String respuestaAdmin;

    @Column(name = "fecha_resolucion")
    private LocalDateTime fechaResolucion;

    @Column(name = "fecha_cierre")
    private LocalDateTime fechaCierre;

    @Column(name = "activo")
    private Boolean activo = true;

    @Column(name = "fecha_creacion", updatable = false)
    private LocalDateTime fechaCreacion;

    // @Column(name = "fecha_actualizacion")
    // private LocalDateTime fechaActualizacion;

    public Incidencia() {}

    // Getters y Setters
    public Integer getIdIncidencia() { return idIncidencia; }
    public void setIdIncidencia(Integer idIncidencia) { this.idIncidencia = idIncidencia; }
    public String getCodigoIncidencia() { return codigoIncidencia; }
    public void setCodigoIncidencia(String codigoIncidencia) { this.codigoIncidencia = codigoIncidencia; }
    public Usuario getSupervisor() { return supervisor; }
    public void setSupervisor(Usuario supervisor) { this.supervisor = supervisor; }
    public Tienda getTienda() { return tienda; }
    public void setTienda(Tienda tienda) { this.tienda = tienda; }
    public TipoIncidencia getTipoIncidencia() { return tipoIncidencia; }
    public void setTipoIncidencia(TipoIncidencia tipoIncidencia) { this.tipoIncidencia = tipoIncidencia; }
    public Prioridad getPrioridad() { return prioridad; }
    public void setPrioridad(Prioridad prioridad) { this.prioridad = prioridad; }
    public String getTitulo() { return titulo; }
    public void setTitulo(String titulo) { this.titulo = titulo; }
    public String getDescripcionProblema() { return descripcionProblema; }
    public void setDescripcionProblema(String descripcionProblema) { this.descripcionProblema = descripcionProblema; }
    public String getRecomendacionAdmin() { return recomendacionAdmin; }
    public void setRecomendacionAdmin(String recomendacionAdmin) { this.recomendacionAdmin = recomendacionAdmin; }
    public EstadoIncidencia getEstadoIncidencia() { return estadoIncidencia; }
    public void setEstadoIncidencia(EstadoIncidencia estadoIncidencia) { this.estadoIncidencia = estadoIncidencia; }
    public Usuario getAdminResuelve() { return adminResuelve; }
    public void setAdminResuelve(Usuario adminResuelve) { this.adminResuelve = adminResuelve; }
    public String getRespuestaAdmin() { return respuestaAdmin; }
    public void setRespuestaAdmin(String respuestaAdmin) { this.respuestaAdmin = respuestaAdmin; }
    public LocalDateTime getFechaResolucion() { return fechaResolucion; }
    public void setFechaResolucion(LocalDateTime fechaResolucion) { this.fechaResolucion = fechaResolucion; }
    public LocalDateTime getFechaCierre() { return fechaCierre; }
    public void setFechaCierre(LocalDateTime fechaCierre) { this.fechaCierre = fechaCierre; }
    public Boolean getActivo() { return activo; }
    public void setActivo(Boolean activo) { this.activo = activo; }
    public LocalDateTime getFechaCreacion() { return fechaCreacion; }
    public void setFechaCreacion(LocalDateTime fechaCreacion) { this.fechaCreacion = fechaCreacion; }
    // public LocalDateTime getFechaActualizacion() { return fechaActualizacion; }
    // public void setFechaActualizacion(LocalDateTime fechaActualizacion) { this.fechaActualizacion = fechaActualizacion; }

    @Override
    public String toString() {
        return "Incidencia{" + "idIncidencia=" + idIncidencia + ", codigoIncidencia='" + codigoIncidencia + '\'' + ", titulo='" + titulo + '\'' + '}';
    }
}