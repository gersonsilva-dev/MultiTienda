package com.techventa.multitienda.cajero.model;

import com.techventa.multitienda.admin.model.Cliente;
import com.techventa.multitienda.admin.model.Usuario;
import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "devoluciones")
public class Devolucion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_devolucion")
    private Integer idDevolucion;

    @Column(name = "codigo_devolucion", unique = true, length = 50)
    private String codigoDevolucion;

    @ManyToOne
    @JoinColumn(name = "id_venta", nullable = false)
    private Venta venta;

    @ManyToOne
    @JoinColumn(name = "id_cliente", nullable = false)
    private Cliente cliente;

    @ManyToOne
    @JoinColumn(name = "id_cajero", nullable = false)
    private Usuario cajero;

    @ManyToOne
    @JoinColumn(name = "id_supervisor_autoriza")
    private Usuario supervisorAutoriza;

    @ManyToOne
    @JoinColumn(name = "id_motivo_devolucion", nullable = false)
    private MotivoDevolucion motivoDevolucion;

    @Column(name = "monto_devolucion", nullable = false, precision = 12, scale = 2)
    private BigDecimal montoDevolucion;

    @Column(name = "observaciones")
    private String observaciones;

    @ManyToOne
    @JoinColumn(name = "id_estado_devolucion")
    private EstadoDevolucion estadoDevolucion;

    @Column(name = "fecha_solicitud", nullable = false)
    private LocalDateTime fechaSolicitud;

    @Column(name = "fecha_autorizacion")
    private LocalDateTime fechaAutorizacion;

    @Column(name = "fecha_proceso")
    private LocalDateTime fechaProceso;

    @Column(name = "activo")
    private Boolean activo = true;

    @Column(name = "fecha_creacion", updatable = false)
    private LocalDateTime fechaCreacion;

    public Devolucion() {}

    // Getters y Setters
    public Integer getIdDevolucion() { return idDevolucion; }
    public void setIdDevolucion(Integer idDevolucion) { this.idDevolucion = idDevolucion; }
    public String getCodigoDevolucion() { return codigoDevolucion; }
    public void setCodigoDevolucion(String codigoDevolucion) { this.codigoDevolucion = codigoDevolucion; }
    public Venta getVenta() { return venta; }
    public void setVenta(Venta venta) { this.venta = venta; }
    public Cliente getCliente() { return cliente; }
    public void setCliente(Cliente cliente) { this.cliente = cliente; }
    public Usuario getCajero() { return cajero; }
    public void setCajero(Usuario cajero) { this.cajero = cajero; }
    public Usuario getSupervisorAutoriza() { return supervisorAutoriza; }
    public void setSupervisorAutoriza(Usuario supervisorAutoriza) { this.supervisorAutoriza = supervisorAutoriza; }
    public MotivoDevolucion getMotivoDevolucion() { return motivoDevolucion; }
    public void setMotivoDevolucion(MotivoDevolucion motivoDevolucion) { this.motivoDevolucion = motivoDevolucion; }
    public BigDecimal getMontoDevolucion() { return montoDevolucion; }
    public void setMontoDevolucion(BigDecimal montoDevolucion) { this.montoDevolucion = montoDevolucion; }
    public String getObservaciones() { return observaciones; }
    public void setObservaciones(String observaciones) { this.observaciones = observaciones; }
    public EstadoDevolucion getEstadoDevolucion() { return estadoDevolucion; }
    public void setEstadoDevolucion(EstadoDevolucion estadoDevolucion) { this.estadoDevolucion = estadoDevolucion; }
    public LocalDateTime getFechaSolicitud() { return fechaSolicitud; }
    public void setFechaSolicitud(LocalDateTime fechaSolicitud) { this.fechaSolicitud = fechaSolicitud; }
    public LocalDateTime getFechaAutorizacion() { return fechaAutorizacion; }
    public void setFechaAutorizacion(LocalDateTime fechaAutorizacion) { this.fechaAutorizacion = fechaAutorizacion; }
    public LocalDateTime getFechaProceso() { return fechaProceso; }
    public void setFechaProceso(LocalDateTime fechaProceso) { this.fechaProceso = fechaProceso; }
    public Boolean getActivo() { return activo; }
    public void setActivo(Boolean activo) { this.activo = activo; }
    public LocalDateTime getFechaCreacion() { return fechaCreacion; }
    public void setFechaCreacion(LocalDateTime fechaCreacion) { this.fechaCreacion = fechaCreacion; }
}