package com.techventa.multitienda.admin.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "cat_tipos_movimiento_inventario")
public class TipoMovimientoInventario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_tipo_movimiento")
    private Integer idTipoMovimiento;

    @Column(name = "nombre_movimiento", nullable = false, length = 50)
    private String nombreMovimiento;

    @Column(name = "afecta_stock", length = 10)
    private String afectaStock;

    @Column(name = "signo", nullable = false)
    private Integer signo;

    @Column(name = "activo")
    private Boolean activo = true;

    @Column(name = "fecha_creacion", updatable = false)
    private LocalDateTime fechaCreacion;

    @Column(name = "fecha_actualizacion")
    private LocalDateTime fechaActualizacion;

    public TipoMovimientoInventario() {}

    public TipoMovimientoInventario(String nombreMovimiento, Integer signo) {
        this.nombreMovimiento = nombreMovimiento;
        this.signo = signo;
        this.activo = true;
    }

    // Getters y Setters
    public Integer getIdTipoMovimiento() { return idTipoMovimiento; }
    public void setIdTipoMovimiento(Integer idTipoMovimiento) { this.idTipoMovimiento = idTipoMovimiento; }
    public String getNombreMovimiento() { return nombreMovimiento; }
    public void setNombreMovimiento(String nombreMovimiento) { this.nombreMovimiento = nombreMovimiento; }
    public String getAfectaStock() { return afectaStock; }
    public void setAfectaStock(String afectaStock) { this.afectaStock = afectaStock; }
    public Integer getSigno() { return signo; }
    public void setSigno(Integer signo) { this.signo = signo; }
    public Boolean getActivo() { return activo; }
    public void setActivo(Boolean activo) { this.activo = activo; }
    public LocalDateTime getFechaCreacion() { return fechaCreacion; }
    public void setFechaCreacion(LocalDateTime fechaCreacion) { this.fechaCreacion = fechaCreacion; }
    public LocalDateTime getFechaActualizacion() { return fechaActualizacion; }
    public void setFechaActualizacion(LocalDateTime fechaActualizacion) { this.fechaActualizacion = fechaActualizacion; }

    @Override
    public String toString() {
        return "TipoMovimientoInventario{" + "idTipoMovimiento=" + idTipoMovimiento + ", nombreMovimiento='" + nombreMovimiento + '\'' + '}';
    }
}