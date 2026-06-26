package com.techventa.multitienda.admin.model;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "mermas")
public class Merma {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_merma")
    private Integer idMerma;

    @ManyToOne
    @JoinColumn(name = "id_producto", nullable = false)
    private Producto producto;

    @ManyToOne
    @JoinColumn(name = "id_tienda", nullable = false)
    private Tienda tienda;

    @ManyToOne
    @JoinColumn(name = "id_lote")
    private Lote lote;

    @Column(name = "cantidad", nullable = false)
    private Integer cantidad;

    @ManyToOne
    @JoinColumn(name = "id_motivo_merma", nullable = false)
    private MotivoMerma motivoMerma;

    @Column(name = "observaciones")
    private String observaciones;

    @Column(name = "evidencia_url", length = 500)
    private String evidenciaUrl;

    @ManyToOne
    @JoinColumn(name = "id_almacenero", nullable = false)
    private Usuario almacenero;

    @ManyToOne
    @JoinColumn(name = "id_supervisor_autoriza")
    private Usuario supervisorAutoriza;

    @Column(name = "estado_autorizacion")
    private String estadoAutorizacion = "APROBADO";

    @Column(name = "fecha_merma", nullable = false)
    private LocalDateTime fechaMerma;

    @Column(name = "activo")
    private Boolean activo = true;

    @Column(name = "fecha_creacion", updatable = false)
    private LocalDateTime fechaCreacion;

    // @Column(name = "fecha_actualizacion")
    // private LocalDateTime fechaActualizacion;

    public Merma() {}

    // Getters y Setters
    public Integer getIdMerma() { return idMerma; }
    public void setIdMerma(Integer idMerma) { this.idMerma = idMerma; }
    public Producto getProducto() { return producto; }
    public void setProducto(Producto producto) { this.producto = producto; }
    public Tienda getTienda() { return tienda; }
    public void setTienda(Tienda tienda) { this.tienda = tienda; }
    public Lote getLote() { return lote; }
    public void setLote(Lote lote) { this.lote = lote; }
    public Integer getCantidad() { return cantidad; }
    public void setCantidad(Integer cantidad) { this.cantidad = cantidad; }
    public MotivoMerma getMotivoMerma() { return motivoMerma; }
    public void setMotivoMerma(MotivoMerma motivoMerma) { this.motivoMerma = motivoMerma; }
    public String getObservaciones() { return observaciones; }
    public void setObservaciones(String observaciones) { this.observaciones = observaciones; }
    public String getEvidenciaUrl() { return evidenciaUrl; }
    public void setEvidenciaUrl(String evidenciaUrl) { this.evidenciaUrl = evidenciaUrl; }
    public Usuario getAlmacenero() { return almacenero; }
    public void setAlmacenero(Usuario almacenero) { this.almacenero = almacenero; }
    public Usuario getSupervisorAutoriza() { return supervisorAutoriza; }
    public void setSupervisorAutoriza(Usuario supervisorAutoriza) { this.supervisorAutoriza = supervisorAutoriza; }
    public String getEstadoAutorizacion() { return estadoAutorizacion; }
    public void setEstadoAutorizacion(String estadoAutorizacion) { this.estadoAutorizacion = estadoAutorizacion; }
    public LocalDateTime getFechaMerma() { return fechaMerma; }
    public void setFechaMerma(LocalDateTime fechaMerma) { this.fechaMerma = fechaMerma; }
    public Boolean getActivo() { return activo; }
    public void setActivo(Boolean activo) { this.activo = activo; }
    public LocalDateTime getFechaCreacion() { return fechaCreacion; }
    public void setFechaCreacion(LocalDateTime fechaCreacion) { this.fechaCreacion = fechaCreacion; }
    // public LocalDateTime getFechaActualizacion() { return fechaActualizacion; }
    // public void setFechaActualizacion(LocalDateTime fechaActualizacion) { this.fechaActualizacion = fechaActualizacion; }

    @Override
    public String toString() {
        return "Merma{" + "idMerma=" + idMerma + ", cantidad=" + cantidad + ", motivoMerma=" + (motivoMerma != null ? motivoMerma.getNombreMotivo() : "null") + '}';
    }
}