package com.techventa.multitienda.admin.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "cajas")
public class Caja {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_caja")
    private Integer idCaja;

    @ManyToOne
    @JoinColumn(name = "id_tienda", nullable = false)
    private Tienda tienda;

    @Column(name = "nombre_caja", nullable = false, length = 50)
    private String nombreCaja;

    @Column(name = "numero_caja", nullable = false)
    private Integer numeroCaja;

    @ManyToOne
    @JoinColumn(name = "id_estado_caja")
    private EstadoCaja estadoCaja;

    @Column(name = "activo")
    private Boolean activo = true;

    @Column(name = "fecha_creacion", updatable = false)
    private LocalDateTime fechaCreacion;

    @Column(name = "fecha_actualizacion")
    private LocalDateTime fechaActualizacion;

    // Constructor vacío
    public Caja() {
    }

    // Constructor básico
    public Caja(Tienda tienda, String nombreCaja, Integer numeroCaja) {
        this.tienda = tienda;
        this.nombreCaja = nombreCaja;
        this.numeroCaja = numeroCaja;
        this.activo = true;
    }

    // Getters y Setters
    public Integer getIdCaja() {
        return idCaja;
    }

    public void setIdCaja(Integer idCaja) {
        this.idCaja = idCaja;
    }

    public Tienda getTienda() {
        return tienda;
    }

    public void setTienda(Tienda tienda) {
        this.tienda = tienda;
    }

    public String getNombreCaja() {
        return nombreCaja;
    }

    public void setNombreCaja(String nombreCaja) {
        this.nombreCaja = nombreCaja;
    }

    public Integer getNumeroCaja() {
        return numeroCaja;
    }

    public void setNumeroCaja(Integer numeroCaja) {
        this.numeroCaja = numeroCaja;
    }

    public EstadoCaja getEstadoCaja() {
        return estadoCaja;
    }

    public void setEstadoCaja(EstadoCaja estadoCaja) {
        this.estadoCaja = estadoCaja;
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
        return "Caja{" +
                "idCaja=" + idCaja +
                ", nombreCaja='" + nombreCaja + '\'' +
                ", numeroCaja=" + numeroCaja +
                ", tienda=" + (tienda != null ? tienda.getNombreTienda() : "null") +
                '}';
    }
}