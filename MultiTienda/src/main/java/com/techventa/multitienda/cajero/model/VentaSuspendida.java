package com.techventa.multitienda.cajero.model;

import com.techventa.multitienda.admin.model.Caja;
import com.techventa.multitienda.admin.model.Tienda;
import com.techventa.multitienda.admin.model.Usuario;
import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "ventas_suspendidas")
public class VentaSuspendida {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_suspension")
    private Integer idSuspension;

    @Column(name = "codigo_suspension", unique = true, length = 50)
    private String codigoSuspension;

    @ManyToOne
    @JoinColumn(name = "id_cajero", nullable = false)
    private Usuario cajero;

    @ManyToOne
    @JoinColumn(name = "id_tienda", nullable = false)
    private Tienda tienda;

    @ManyToOne
    @JoinColumn(name = "id_caja", nullable = false)
    private Caja caja;

    @Column(name = "carrito_json", nullable = false, columnDefinition = "JSON")
    private String carritoJson;

    @Column(name = "total_suspendido", nullable = false, precision = 12, scale = 2)
    private BigDecimal totalSuspendido;

    @Column(name = "fecha_suspension", nullable = false)
    private LocalDateTime fechaSuspension;

    @Column(name = "fecha_recuperacion")
    private LocalDateTime fechaRecuperacion;

    @Column(name = "estado", length = 20)
    private String estado = "SUSPENDIDA";

    @Column(name = "activo")
    private Boolean activo = true;

    @Column(name = "fecha_creacion", updatable = false)
    private LocalDateTime fechaCreacion;

    public VentaSuspendida() {}

    public Integer getIdSuspension() { return idSuspension; }
    public void setIdSuspension(Integer idSuspension) { this.idSuspension = idSuspension; }
    public String getCodigoSuspension() { return codigoSuspension; }
    public void setCodigoSuspension(String codigoSuspension) { this.codigoSuspension = codigoSuspension; }
    public Usuario getCajero() { return cajero; }
    public void setCajero(Usuario cajero) { this.cajero = cajero; }
    public Tienda getTienda() { return tienda; }
    public void setTienda(Tienda tienda) { this.tienda = tienda; }
    public Caja getCaja() { return caja; }
    public void setCaja(Caja caja) { this.caja = caja; }
    public String getCarritoJson() { return carritoJson; }
    public void setCarritoJson(String carritoJson) { this.carritoJson = carritoJson; }
    public BigDecimal getTotalSuspendido() { return totalSuspendido; }
    public void setTotalSuspendido(BigDecimal totalSuspendido) { this.totalSuspendido = totalSuspendido; }
    public LocalDateTime getFechaSuspension() { return fechaSuspension; }
    public void setFechaSuspension(LocalDateTime fechaSuspension) { this.fechaSuspension = fechaSuspension; }
    public LocalDateTime getFechaRecuperacion() { return fechaRecuperacion; }
    public void setFechaRecuperacion(LocalDateTime fechaRecuperacion) { this.fechaRecuperacion = fechaRecuperacion; }
    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }
    public Boolean getActivo() { return activo; }
    public void setActivo(Boolean activo) { this.activo = activo; }
    public LocalDateTime getFechaCreacion() { return fechaCreacion; }
    public void setFechaCreacion(LocalDateTime fechaCreacion) { this.fechaCreacion = fechaCreacion; }
}