package com.techventa.multitienda.cajero.model;

import com.techventa.multitienda.admin.model.Cliente;
import com.techventa.multitienda.admin.model.Tienda;
import com.techventa.multitienda.admin.model.Usuario;
import com.techventa.multitienda.admin.model.Caja;
import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "ventas")
public class Venta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_venta")
    private Integer idVenta;

    @Column(name = "codigo_venta", unique = true, length = 50)
    private String codigoVenta;

    @ManyToOne
    @JoinColumn(name = "id_cajero", nullable = false)
    private Usuario cajero;

    @ManyToOne
    @JoinColumn(name = "id_cliente")
    private Cliente cliente;

    @ManyToOne
    @JoinColumn(name = "id_tienda", nullable = false)
    private Tienda tienda;

    @ManyToOne
    @JoinColumn(name = "id_caja", nullable = false)
    private Caja caja;

    @ManyToOne
    @JoinColumn(name = "id_turno_caja", nullable = false)
    private TurnoCaja turnoCaja;

    @ManyToOne
    @JoinColumn(name = "id_tipo_comprobante", nullable = false)
    private TipoComprobante tipoComprobante;

    @Column(name = "numero_comprobante", length = 50)
    private String numeroComprobante;

    @Column(name = "ruc_cliente", length = 11)
    private String rucCliente;

    @Column(name = "razon_social", length = 200)
    private String razonSocial;

    @Column(name = "subtotal", nullable = false, precision = 12, scale = 2)
    private BigDecimal subtotal;

    @Column(name = "descuento_aplicado", precision = 12, scale = 2)
    private BigDecimal descuentoAplicado = BigDecimal.ZERO;

    @Column(name = "igv", nullable = false, precision = 12, scale = 2)
    private BigDecimal igv;

    @Column(name = "total", nullable = false, precision = 12, scale = 2)
    private BigDecimal total;

    @ManyToOne
    @JoinColumn(name = "id_estado_venta")
    private EstadoVenta estadoVenta;

    @Column(name = "fecha_venta", nullable = false)
    private LocalDateTime fechaVenta;

    @Column(name = "activo")
    private Boolean activo = true;

    @Column(name = "fecha_creacion", updatable = false)
    private LocalDateTime fechaCreacion;

    public Venta() {}

    // Getters y Setters
    public Integer getIdVenta() { return idVenta; }
    public void setIdVenta(Integer idVenta) { this.idVenta = idVenta; }
    public String getCodigoVenta() { return codigoVenta; }
    public void setCodigoVenta(String codigoVenta) { this.codigoVenta = codigoVenta; }
    public Usuario getCajero() { return cajero; }
    public void setCajero(Usuario cajero) { this.cajero = cajero; }
    public Cliente getCliente() { return cliente; }
    public void setCliente(Cliente cliente) { this.cliente = cliente; }
    public Tienda getTienda() { return tienda; }
    public void setTienda(Tienda tienda) { this.tienda = tienda; }
    public Caja getCaja() { return caja; }
    public void setCaja(Caja caja) { this.caja = caja; }
    public TurnoCaja getTurnoCaja() { return turnoCaja; }
    public void setTurnoCaja(TurnoCaja turnoCaja) { this.turnoCaja = turnoCaja; }
    public TipoComprobante getTipoComprobante() { return tipoComprobante; }
    public void setTipoComprobante(TipoComprobante tipoComprobante) { this.tipoComprobante = tipoComprobante; }
    public String getNumeroComprobante() { return numeroComprobante; }
    public void setNumeroComprobante(String numeroComprobante) { this.numeroComprobante = numeroComprobante; }
    public String getRucCliente() { return rucCliente; }
    public void setRucCliente(String rucCliente) { this.rucCliente = rucCliente; }
    public String getRazonSocial() { return razonSocial; }
    public void setRazonSocial(String razonSocial) { this.razonSocial = razonSocial; }
    public BigDecimal getSubtotal() { return subtotal; }
    public void setSubtotal(BigDecimal subtotal) { this.subtotal = subtotal; }
    public BigDecimal getDescuentoAplicado() { return descuentoAplicado; }
    public void setDescuentoAplicado(BigDecimal descuentoAplicado) { this.descuentoAplicado = descuentoAplicado; }
    public BigDecimal getIgv() { return igv; }
    public void setIgv(BigDecimal igv) { this.igv = igv; }
    public BigDecimal getTotal() { return total; }
    public void setTotal(BigDecimal total) { this.total = total; }
    public EstadoVenta getEstadoVenta() { return estadoVenta; }
    public void setEstadoVenta(EstadoVenta estadoVenta) { this.estadoVenta = estadoVenta; }
    public LocalDateTime getFechaVenta() { return fechaVenta; }
    public void setFechaVenta(LocalDateTime fechaVenta) { this.fechaVenta = fechaVenta; }
    public Boolean getActivo() { return activo; }
    public void setActivo(Boolean activo) { this.activo = activo; }
    public LocalDateTime getFechaCreacion() { return fechaCreacion; }
    public void setFechaCreacion(LocalDateTime fechaCreacion) { this.fechaCreacion = fechaCreacion; }
}