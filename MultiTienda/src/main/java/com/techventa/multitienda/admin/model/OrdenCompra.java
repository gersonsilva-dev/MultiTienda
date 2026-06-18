package com.techventa.multitienda.admin.model;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "ordenes_compra")
public class OrdenCompra {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_orden")
    private Integer idOrden;

    @Column(name = "codigo_orden", unique = true, length = 50)
    private String codigoOrden;

    @ManyToOne
    @JoinColumn(name = "id_tienda", nullable = false)
    private Tienda tienda;

    @ManyToOne
    @JoinColumn(name = "id_proveedor", nullable = false)
    private Proveedor proveedor;

    @Column(name = "fecha_orden", nullable = false)
    private LocalDateTime fechaOrden;

    @Column(name = "fecha_entrega_estimada")
    private LocalDate fechaEntregaEstimada;

    @Column(name = "id_estado_orden_compra")
    private Integer idEstadoOrdenCompra;

    @Column(name = "observaciones")
    private String observaciones;

    @Column(name = "activo")
    private Boolean activo = true;

    @Column(name = "fecha_creacion", updatable = false)
    private LocalDateTime fechaCreacion;

    @Column(name = "fecha_actualizacion")
    private LocalDateTime fechaActualizacion;

    public OrdenCompra() {}

    // Constructor básico
    public OrdenCompra(Tienda tienda, Proveedor proveedor) {
        this.tienda = tienda;
        this.proveedor = proveedor;
        this.fechaOrden = LocalDateTime.now();
        this.activo = true;
    }

    // Getters y Setters
    public Integer getIdOrden() { return idOrden; }
    public void setIdOrden(Integer idOrden) { this.idOrden = idOrden; }
    public String getCodigoOrden() { return codigoOrden; }
    public void setCodigoOrden(String codigoOrden) { this.codigoOrden = codigoOrden; }
    public Tienda getTienda() { return tienda; }
    public void setTienda(Tienda tienda) { this.tienda = tienda; }
    public Proveedor getProveedor() { return proveedor; }
    public void setProveedor(Proveedor proveedor) { this.proveedor = proveedor; }
    public LocalDateTime getFechaOrden() { return fechaOrden; }
    public void setFechaOrden(LocalDateTime fechaOrden) { this.fechaOrden = fechaOrden; }
    public LocalDate getFechaEntregaEstimada() { return fechaEntregaEstimada; }
    public void setFechaEntregaEstimada(LocalDate fechaEntregaEstimada) { this.fechaEntregaEstimada = fechaEntregaEstimada; }
    public Integer getIdEstadoOrdenCompra() { return idEstadoOrdenCompra; }
    public void setIdEstadoOrdenCompra(Integer idEstadoOrdenCompra) { this.idEstadoOrdenCompra = idEstadoOrdenCompra; }
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
        return "OrdenCompra{" +
                "idOrden=" + idOrden +
                ", codigoOrden='" + codigoOrden + '\'' +
                ", fechaOrden=" + fechaOrden +
                '}';
    }
}