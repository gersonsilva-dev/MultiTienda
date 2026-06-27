package com.techventa.multitienda.proveedor.model;

import com.techventa.multitienda.admin.model.Proveedor;
import com.techventa.multitienda.admin.model.Tienda;
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

    @Column(name = "codigo_orden", unique = true, nullable = false, length = 50)
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
    private Integer idEstadoOrdenCompra = 1;

    @Column(name = "observaciones")
    private String observaciones;

    @Column(name = "activo")
    private Boolean activo = true;

    @Column(name = "fecha_creacion", updatable = false)
    private LocalDateTime fechaCreacion;

    // 🔥 CAMPO TOTAL (NO SE GUARDA EN BD)
    @Transient
    private double total;

    // ============================================================
    // CONSTRUCTORES
    // ============================================================
    public OrdenCompra() {}

    public OrdenCompra(String codigoOrden, Tienda tienda, Proveedor proveedor, 
                       LocalDateTime fechaOrden, LocalDate fechaEntregaEstimada) {
        this.codigoOrden = codigoOrden;
        this.tienda = tienda;
        this.proveedor = proveedor;
        this.fechaOrden = fechaOrden;
        this.fechaEntregaEstimada = fechaEntregaEstimada;
        this.idEstadoOrdenCompra = 1;
        this.activo = true;
    }

    // ============================================================
    // GETTERS Y SETTERS
    // ============================================================
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

    // 🔥 GETTER Y SETTER PARA TOTAL
    public double getTotal() { return total; }
    public void setTotal(double total) { this.total = total; }

    // ============================================================
    // MÉTODO PARA OBTENER EL NOMBRE DEL ESTADO
    // ============================================================
    public String getEstadoNombre() {
        switch (idEstadoOrdenCompra != null ? idEstadoOrdenCompra : 0) {
            case 1: return "PENDIENTE";
            case 2: return "APROBADA";
            case 3: return "ENVIADA";
            case 4: return "RECIBIDA";
            case 5: return "CANCELADA";
            case 6: return "PARCIAL";
            default: return "DESCONOCIDO";
        }
    }

    // ============================================================
    // MÉTODO PARA OBTENER EL COLOR DEL ESTADO
    // ============================================================
    public String getEstadoColor() {
        switch (idEstadoOrdenCompra != null ? idEstadoOrdenCompra : 0) {
            case 1: return "badge-warning";
            case 2: return "badge-success";
            case 3: return "badge-info";
            case 4: return "badge-primary";
            case 5: return "badge-danger";
            case 6: return "badge-secondary";
            default: return "badge-secondary";
        }
    }

    @Override
    public String toString() {
        return "OrdenCompra{" +
                "idOrden=" + idOrden +
                ", codigoOrden='" + codigoOrden + '\'' +
                ", proveedor=" + (proveedor != null ? proveedor.getRazonSocial() : "null") +
                ", total=" + total +
                '}';
    }
}