package com.techventa.multitienda.cajero.model;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.techventa.multitienda.admin.model.Usuario;

@Entity
@Table(name = "retiros_caja")
public class RetiroCaja {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_retiro")
    private Integer idRetiro;
    
    @ManyToOne
    @JoinColumn(name = "id_turno_caja")
    private TurnoCaja turnoCaja;
    
    @Column(name = "monto_retiro", precision = 12, scale = 2)
    private BigDecimal monto;
    
    @Column(name = "motivo_retiro", length = 255)
    private String motivo;
    
    @Column(name = "observaciones")
    private String observaciones;
    
    @Column(name = "fecha_autorizacion")
    private LocalDateTime fechaRetiro;
    
    // ❌ ELIMINAR id_usuario
    // @ManyToOne
    // @JoinColumn(name = "id_usuario")
    // private Usuario usuario;
    
    @ManyToOne
    @JoinColumn(name = "id_supervisor_autoriza")
    private Usuario supervisorAutoriza;
    
    @Column(name = "estado_autorizacion")
    private String estadoAutorizacion = "PENDIENTE";
    
    @Column(name = "activo")
    private Boolean activo = true;
    
    @Column(name = "fecha_creacion", updatable = false)
    private LocalDateTime fechaCreacion;

    // Getters y Setters
    public Integer getIdRetiro() { return idRetiro; }
    public void setIdRetiro(Integer idRetiro) { this.idRetiro = idRetiro; }
    
    public TurnoCaja getTurnoCaja() { return turnoCaja; }
    public void setTurnoCaja(TurnoCaja turnoCaja) { this.turnoCaja = turnoCaja; }
    
    public BigDecimal getMonto() { return monto; }
    public void setMonto(BigDecimal monto) { this.monto = monto; }
    
    public String getMotivo() { return motivo; }
    public void setMotivo(String motivo) { this.motivo = motivo; }
    
    public String getObservaciones() { return observaciones; }
    public void setObservaciones(String observaciones) { this.observaciones = observaciones; }
    
    public LocalDateTime getFechaRetiro() { return fechaRetiro; }
    public void setFechaRetiro(LocalDateTime fechaRetiro) { this.fechaRetiro = fechaRetiro; }
    
    // ❌ ELIMINAR getUsuario() y setUsuario()
    // public Usuario getUsuario() { return usuario; }
    // public void setUsuario(Usuario usuario) { this.usuario = usuario; }
    
    public Usuario getSupervisorAutoriza() { return supervisorAutoriza; }
    public void setSupervisorAutoriza(Usuario supervisorAutoriza) { this.supervisorAutoriza = supervisorAutoriza; }
    
    public String getEstadoAutorizacion() { return estadoAutorizacion; }
    public void setEstadoAutorizacion(String estadoAutorizacion) { this.estadoAutorizacion = estadoAutorizacion; }
    
    public Boolean getActivo() { return activo; }
    public void setActivo(Boolean activo) { this.activo = activo; }
    
    public LocalDateTime getFechaCreacion() { return fechaCreacion; }
    public void setFechaCreacion(LocalDateTime fechaCreacion) { this.fechaCreacion = fechaCreacion; }
}