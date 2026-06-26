package com.techventa.multitienda.admin.model;

import com.techventa.multitienda.admin.model.TipoDescuento;
import com.techventa.multitienda.admin.model.EstadoOferta;
import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "ofertas")
public class Oferta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_oferta")
    private Integer idOferta;

    @Column(name = "nombre_oferta", nullable = false, length = 150)
    private String nombreOferta;

    @ManyToOne
    @JoinColumn(name = "id_tipo_descuento", nullable = false)
    private TipoDescuento tipoDescuento;

    @Column(name = "valor_descuento", nullable = false, precision = 12, scale = 2)
    private BigDecimal valorDescuento;

    @Column(name = "cantidad_compra")
    private Integer cantidadCompra = 1;

    @Column(name = "cantidad_beneficio")
    private Integer cantidadBeneficio = 0;

    @Column(name = "monto_minimo", precision = 12, scale = 2)
    private BigDecimal montoMinimo = BigDecimal.ZERO;

    @Column(name = "prioridad")
    private Integer prioridad = 1;

    @Column(name = "fecha_inicio", nullable = false)
    private LocalDateTime fechaInicio;

    @Column(name = "fecha_fin", nullable = false)
    private LocalDateTime fechaFin;

    @Column(name = "aplica_todas_tiendas")
    private Boolean aplicaTodasTiendas = true;

    @ManyToOne
    @JoinColumn(name = "id_estado_oferta")
    private EstadoOferta estadoOferta;

    @Column(name = "usuario_crea", nullable = false)
    private Integer usuarioCrea;

    @Column(name = "usuario_aprueba")
    private Integer usuarioAprueba;

    @Column(name = "fecha_aprobacion")
    private LocalDateTime fechaAprobacion;

    @Column(name = "activo")
    private Boolean activo = true;

    @Column(name = "fecha_creacion", updatable = false)
    private LocalDateTime fechaCreacion;

    @Column(name = "fecha_actualizacion")
    private LocalDateTime fechaActualizacion;

    // Constructor vacío
    public Oferta() {
    }

    // Constructor básico
    public Oferta(String nombreOferta, TipoDescuento tipoDescuento, BigDecimal valorDescuento,
                  LocalDateTime fechaInicio, LocalDateTime fechaFin) {
        this.nombreOferta = nombreOferta;
        this.tipoDescuento = tipoDescuento;
        this.valorDescuento = valorDescuento;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
        this.activo = true;
    }

    // Getters y Setters
    public Integer getIdOferta() {
        return idOferta;
    }

    public void setIdOferta(Integer idOferta) {
        this.idOferta = idOferta;
    }

    public String getNombreOferta() {
        return nombreOferta;
    }

    public void setNombreOferta(String nombreOferta) {
        this.nombreOferta = nombreOferta;
    }

    public TipoDescuento getTipoDescuento() {
        return tipoDescuento;
    }

    public void setTipoDescuento(TipoDescuento tipoDescuento) {
        this.tipoDescuento = tipoDescuento;
    }

    public BigDecimal getValorDescuento() {
        return valorDescuento;
    }

    public void setValorDescuento(BigDecimal valorDescuento) {
        this.valorDescuento = valorDescuento;
    }

    public Integer getCantidadCompra() {
        return cantidadCompra;
    }

    public void setCantidadCompra(Integer cantidadCompra) {
        this.cantidadCompra = cantidadCompra;
    }

    public Integer getCantidadBeneficio() {
        return cantidadBeneficio;
    }

    public void setCantidadBeneficio(Integer cantidadBeneficio) {
        this.cantidadBeneficio = cantidadBeneficio;
    }

    public BigDecimal getMontoMinimo() {
        return montoMinimo;
    }

    public void setMontoMinimo(BigDecimal montoMinimo) {
        this.montoMinimo = montoMinimo;
    }

    public Integer getPrioridad() {
        return prioridad;
    }

    public void setPrioridad(Integer prioridad) {
        this.prioridad = prioridad;
    }

    public LocalDateTime getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(LocalDateTime fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public LocalDateTime getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(LocalDateTime fechaFin) {
        this.fechaFin = fechaFin;
    }

    public Boolean getAplicaTodasTiendas() {
        return aplicaTodasTiendas;
    }

    public void setAplicaTodasTiendas(Boolean aplicaTodasTiendas) {
        this.aplicaTodasTiendas = aplicaTodasTiendas;
    }

    public EstadoOferta getEstadoOferta() {
        return estadoOferta;
    }

    public void setEstadoOferta(EstadoOferta estadoOferta) {
        this.estadoOferta = estadoOferta;
    }

    public Integer getUsuarioCrea() {
        return usuarioCrea;
    }

    public void setUsuarioCrea(Integer usuarioCrea) {
        this.usuarioCrea = usuarioCrea;
    }

    public Integer getUsuarioAprueba() {
        return usuarioAprueba;
    }

    public void setUsuarioAprueba(Integer usuarioAprueba) {
        this.usuarioAprueba = usuarioAprueba;
    }

    public LocalDateTime getFechaAprobacion() {
        return fechaAprobacion;
    }

    public void setFechaAprobacion(LocalDateTime fechaAprobacion) {
        this.fechaAprobacion = fechaAprobacion;
    }

    public Boolean getActivo() {
        return activo;
    }

    public void setActivo(Boolean activo) {
        this.activo = activo;
    }

    public LocalDateTime getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(LocalDateTime fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public LocalDateTime getFechaActualizacion() {
        return fechaActualizacion;
    }

    public void setFechaActualizacion(LocalDateTime fechaActualizacion) {
        this.fechaActualizacion = fechaActualizacion;
    }

    @Override
    public String toString() {
        return "Oferta{" +
                "idOferta=" + idOferta +
                ", nombreOferta='" + nombreOferta + '\'' +
                ", tipoDescuento=" + (tipoDescuento != null ? tipoDescuento.getNombreTipo() : "null") +
                ", valorDescuento=" + valorDescuento +
                ", fechaInicio=" + fechaInicio +
                ", fechaFin=" + fechaFin +
                '}';
    }
}