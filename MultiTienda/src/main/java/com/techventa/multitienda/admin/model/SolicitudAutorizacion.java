package com.techventa.multitienda.admin.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

import com.techventa.multitienda.cajero.model.TurnoCaja;

@Entity
@Table(name = "solicitudes_autorizacion")
public class SolicitudAutorizacion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_solicitud")
    private Integer idSolicitud;

    @ManyToOne
    @JoinColumn(name = "id_cajero")
    private Usuario cajero;

    @ManyToOne
    @JoinColumn(name = "id_tienda")
    private Tienda tienda;

    @ManyToOne
    @JoinColumn(name = "id_turno_caja")
    private TurnoCaja turnoCaja;

    @Column(name = "tipo_solicitud")
    private String tipoSolicitud;

    @Column(name = "detalle_solicitud", columnDefinition = "json")
    private String detalleSolicitud;

    @Column(name = "monto_afectado")
    private Double montoAfectado;

    // 🔥 CORRECCIÓN: Relación con Usuario (supervisor)
    @ManyToOne
    @JoinColumn(name = "id_supervisor")
    private Usuario supervisor;

    @Column(name = "estado_solicitud")
    private String estadoSolicitud = "PENDIENTE";

    @Column(name = "fecha_atencion")
    private LocalDateTime fechaAtencion;

    @Column(name = "respuesta_supervisor")
    private String respuestaSupervisor;

    @Column(name = "activo")
    private Boolean activo = true;

    @Column(name = "fecha_creacion", updatable = false)
    private LocalDateTime fechaCreacion;

    // Getters y Setters
    public Integer getIdSolicitud() { return idSolicitud; }
    public void setIdSolicitud(Integer idSolicitud) { this.idSolicitud = idSolicitud; }

    public Usuario getCajero() { return cajero; }
    public void setCajero(Usuario cajero) { this.cajero = cajero; }

    public Tienda getTienda() { return tienda; }
    public void setTienda(Tienda tienda) { this.tienda = tienda; }

    public TurnoCaja getTurnoCaja() { return turnoCaja; }
    public void setTurnoCaja(TurnoCaja turnoCaja) { this.turnoCaja = turnoCaja; }

    public String getTipoSolicitud() { return tipoSolicitud; }
    public void setTipoSolicitud(String tipoSolicitud) { this.tipoSolicitud = tipoSolicitud; }

    public String getDetalleSolicitud() { return detalleSolicitud; }
    public void setDetalleSolicitud(String detalleSolicitud) { this.detalleSolicitud = detalleSolicitud; }

    public Double getMontoAfectado() { return montoAfectado; }
    public void setMontoAfectado(Double montoAfectado) { this.montoAfectado = montoAfectado; }

    public Usuario getSupervisor() { return supervisor; }
    public void setSupervisor(Usuario supervisor) { this.supervisor = supervisor; }

    public String getEstadoSolicitud() { return estadoSolicitud; }
    public void setEstadoSolicitud(String estadoSolicitud) { this.estadoSolicitud = estadoSolicitud; }

    public LocalDateTime getFechaAtencion() { return fechaAtencion; }
    public void setFechaAtencion(LocalDateTime fechaAtencion) { this.fechaAtencion = fechaAtencion; }

    public String getRespuestaSupervisor() { return respuestaSupervisor; }
    public void setRespuestaSupervisor(String respuestaSupervisor) { this.respuestaSupervisor = respuestaSupervisor; }

    public Boolean getActivo() { return activo; }
    public void setActivo(Boolean activo) { this.activo = activo; }

    public LocalDateTime getFechaCreacion() { return fechaCreacion; }
    public void setFechaCreacion(LocalDateTime fechaCreacion) { this.fechaCreacion = fechaCreacion; }
}