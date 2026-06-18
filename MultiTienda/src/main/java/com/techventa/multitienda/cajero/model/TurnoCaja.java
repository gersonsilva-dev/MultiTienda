package com.techventa.multitienda.cajero.model;

import com.techventa.multitienda.admin.model.Usuario;
import com.techventa.multitienda.admin.model.Tienda;
import com.techventa.multitienda.admin.model.Caja;
import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Entity
@Table(name = "turno_caja")
public class TurnoCaja {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_turno_caja")
    private Integer idTurnoCaja;

    @ManyToOne
    @JoinColumn(name = "id_cajero", nullable = false)
    private Usuario cajero;

    @ManyToOne
    @JoinColumn(name = "id_tienda", nullable = false)
    private Tienda tienda;

    @ManyToOne
    @JoinColumn(name = "id_caja", nullable = false)
    private Caja caja;

    @Column(name = "fecha_apertura", nullable = false)
    private LocalDate fechaApertura;

    @Column(name = "hora_apertura", nullable = false)
    private LocalTime horaApertura;

    @Column(name = "fondo_inicial", nullable = false, precision = 12, scale = 2)
    private BigDecimal fondoInicial;

    @Column(name = "fecha_cierre")
    private LocalDate fechaCierre;

    @Column(name = "hora_cierre")
    private LocalTime horaCierre;

    @Column(name = "fondo_final", precision = 12, scale = 2)
    private BigDecimal fondoFinal;

    @Column(name = "total_ventas_efectivo", precision = 12, scale = 2)
    private BigDecimal totalVentasEfectivo = BigDecimal.ZERO;

    @Column(name = "total_ventas_tarjeta", precision = 12, scale = 2)
    private BigDecimal totalVentasTarjeta = BigDecimal.ZERO;

    @Column(name = "total_ventas_yape", precision = 12, scale = 2)
    private BigDecimal totalVentasYape = BigDecimal.ZERO;

    @Column(name = "total_ventas_plin", precision = 12, scale = 2)
    private BigDecimal totalVentasPlin = BigDecimal.ZERO;

    @Column(name = "diferencia", precision = 12, scale = 2)
    private BigDecimal diferencia;

    @Column(name = "estado_turno")
    private String estadoTurno = "ACTIVO";

    @Column(name = "observaciones_cierre")
    private String observacionesCierre;

    @Column(name = "activo")
    private Boolean activo = true;

    @Column(name = "fecha_creacion", updatable = false)
    private LocalDateTime fechaCreacion;

    public TurnoCaja() {}

    // Getters y Setters
    public Integer getIdTurnoCaja() { return idTurnoCaja; }
    public void setIdTurnoCaja(Integer idTurnoCaja) { this.idTurnoCaja = idTurnoCaja; }
    public Usuario getCajero() { return cajero; }
    public void setCajero(Usuario cajero) { this.cajero = cajero; }
    public Tienda getTienda() { return tienda; }
    public void setTienda(Tienda tienda) { this.tienda = tienda; }
    public Caja getCaja() { return caja; }
    public void setCaja(Caja caja) { this.caja = caja; }
    public LocalDate getFechaApertura() { return fechaApertura; }
    public void setFechaApertura(LocalDate fechaApertura) { this.fechaApertura = fechaApertura; }
    public LocalTime getHoraApertura() { return horaApertura; }
    public void setHoraApertura(LocalTime horaApertura) { this.horaApertura = horaApertura; }
    public BigDecimal getFondoInicial() { return fondoInicial; }
    public void setFondoInicial(BigDecimal fondoInicial) { this.fondoInicial = fondoInicial; }
    public LocalDate getFechaCierre() { return fechaCierre; }
    public void setFechaCierre(LocalDate fechaCierre) { this.fechaCierre = fechaCierre; }
    public LocalTime getHoraCierre() { return horaCierre; }
    public void setHoraCierre(LocalTime horaCierre) { this.horaCierre = horaCierre; }
    public BigDecimal getFondoFinal() { return fondoFinal; }
    public void setFondoFinal(BigDecimal fondoFinal) { this.fondoFinal = fondoFinal; }
    public BigDecimal getTotalVentasEfectivo() { return totalVentasEfectivo; }
    public void setTotalVentasEfectivo(BigDecimal totalVentasEfectivo) { this.totalVentasEfectivo = totalVentasEfectivo; }
    public BigDecimal getTotalVentasTarjeta() { return totalVentasTarjeta; }
    public void setTotalVentasTarjeta(BigDecimal totalVentasTarjeta) { this.totalVentasTarjeta = totalVentasTarjeta; }
    public BigDecimal getTotalVentasYape() { return totalVentasYape; }
    public void setTotalVentasYape(BigDecimal totalVentasYape) { this.totalVentasYape = totalVentasYape; }
    public BigDecimal getTotalVentasPlin() { return totalVentasPlin; }
    public void setTotalVentasPlin(BigDecimal totalVentasPlin) { this.totalVentasPlin = totalVentasPlin; }
    public BigDecimal getDiferencia() { return diferencia; }
    public void setDiferencia(BigDecimal diferencia) { this.diferencia = diferencia; }
    public String getEstadoTurno() { return estadoTurno; }
    public void setEstadoTurno(String estadoTurno) { this.estadoTurno = estadoTurno; }
    public String getObservacionesCierre() { return observacionesCierre; }
    public void setObservacionesCierre(String observacionesCierre) { this.observacionesCierre = observacionesCierre; }
    public Boolean getActivo() { return activo; }
    public void setActivo(Boolean activo) { this.activo = activo; }
    public LocalDateTime getFechaCreacion() { return fechaCreacion; }
    public void setFechaCreacion(LocalDateTime fechaCreacion) { this.fechaCreacion = fechaCreacion; }
}