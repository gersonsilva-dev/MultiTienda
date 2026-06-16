package com.techventa.multitienda.admin.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "transferencias_stock")
public class Transferencia {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_transferencia")
    private Integer idTransferencia;

    @Column(name = "codigo_transferencia", unique = true, length = 50)
    private String codigoTransferencia;

    @ManyToOne
    @JoinColumn(name = "id_tienda_origen", nullable = false)
    private Tienda tiendaOrigen;

    @ManyToOne
    @JoinColumn(name = "id_tienda_destino", nullable = false)
    private Tienda tiendaDestino;

    @ManyToOne
    @JoinColumn(name = "id_almacenero_solicita", nullable = false)
    private Usuario almaceneroSolicita;

    @ManyToOne
    @JoinColumn(name = "id_almacenero_aprueba")
    private Usuario almaceneroAprueba;

    @Column(name = "fecha_solicitud", nullable = false)
    private LocalDateTime fechaSolicitud;

    @Column(name = "fecha_envio")
    private LocalDateTime fechaEnvio;

    @Column(name = "fecha_recepcion")
    private LocalDateTime fechaRecepcion;

    @ManyToOne
    @JoinColumn(name = "id_estado_transferencia")
    private EstadoTransferencia estadoTransferencia;

    @Column(name = "observaciones")
    private String observaciones;

    @Column(name = "activo")
    private Boolean activo = true;

    @Column(name = "fecha_creacion", updatable = false)
    private LocalDateTime fechaCreacion;

    @Column(name = "fecha_actualizacion")
    private LocalDateTime fechaActualizacion;

    public Transferencia() {}

    // Getters y Setters
    public Integer getIdTransferencia() { return idTransferencia; }
    public void setIdTransferencia(Integer idTransferencia) { this.idTransferencia = idTransferencia; }
    public String getCodigoTransferencia() { return codigoTransferencia; }
    public void setCodigoTransferencia(String codigoTransferencia) { this.codigoTransferencia = codigoTransferencia; }
    public Tienda getTiendaOrigen() { return tiendaOrigen; }
    public void setTiendaOrigen(Tienda tiendaOrigen) { this.tiendaOrigen = tiendaOrigen; }
    public Tienda getTiendaDestino() { return tiendaDestino; }
    public void setTiendaDestino(Tienda tiendaDestino) { this.tiendaDestino = tiendaDestino; }
    public Usuario getAlmaceneroSolicita() { return almaceneroSolicita; }
    public void setAlmaceneroSolicita(Usuario almaceneroSolicita) { this.almaceneroSolicita = almaceneroSolicita; }
    public Usuario getAlmaceneroAprueba() { return almaceneroAprueba; }
    public void setAlmaceneroAprueba(Usuario almaceneroAprueba) { this.almaceneroAprueba = almaceneroAprueba; }
    public LocalDateTime getFechaSolicitud() { return fechaSolicitud; }
    public void setFechaSolicitud(LocalDateTime fechaSolicitud) { this.fechaSolicitud = fechaSolicitud; }
    public LocalDateTime getFechaEnvio() { return fechaEnvio; }
    public void setFechaEnvio(LocalDateTime fechaEnvio) { this.fechaEnvio = fechaEnvio; }
    public LocalDateTime getFechaRecepcion() { return fechaRecepcion; }
    public void setFechaRecepcion(LocalDateTime fechaRecepcion) { this.fechaRecepcion = fechaRecepcion; }
    public EstadoTransferencia getEstadoTransferencia() { return estadoTransferencia; }
    public void setEstadoTransferencia(EstadoTransferencia estadoTransferencia) { this.estadoTransferencia = estadoTransferencia; }
    public String getObservaciones() { return observaciones; }
    public void setObservaciones(String observaciones) { this.observaciones = observaciones; }
    public Boolean getActivo() { return activo; }
    public void setActivo(Boolean activo) { this.activo = activo; }
    public LocalDateTime getFechaCreacion() { return fechaCreacion; }
    public void setFechaCreacion(LocalDateTime fechaCreacion) { this.fechaCreacion = fechaCreacion; }
    public LocalDateTime getFechaActualizacion() { return fechaActualizacion; }
    public void setFechaActualizacion(LocalDateTime fechaActualizacion) { this.fechaActualizacion = fechaActualizacion; }

    @Override
    public String toString() {
        return "Transferencia{" + "idTransferencia=" + idTransferencia + ", codigoTransferencia='" + codigoTransferencia + '\'' + '}';
    }
}