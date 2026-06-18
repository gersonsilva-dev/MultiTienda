package com.techventa.multitienda.almacenero.model;

import com.techventa.multitienda.admin.model.Producto;
import com.techventa.multitienda.admin.model.Lote;
import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "detalle_recepcion_mercaderia")
public class DetalleRecepcion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_detalle_recepcion")
    private Integer idDetalleRecepcion;

    @ManyToOne
    @JoinColumn(name = "id_recepcion", nullable = false)
    private RecepcionMercaderia recepcion;

    @ManyToOne
    @JoinColumn(name = "id_producto", nullable = false)
    private Producto producto;

    @ManyToOne
    @JoinColumn(name = "id_lote")
    private Lote lote;

    @Column(name = "cantidad_esperada", nullable = false)
    private Integer cantidadEsperada;

    @Column(name = "cantidad_recibida", nullable = false)
    private Integer cantidadRecibida;

    @Column(name = "precio_recibido", precision = 12, scale = 2)
    private BigDecimal precioRecibido;

    @Column(name = "fecha_vencimiento_recibida")
    private LocalDate fechaVencimientoRecibida;

    @Column(name = "observaciones")
    private String observaciones;

    public DetalleRecepcion() {}

    // Getters y Setters
    public Integer getIdDetalleRecepcion() { return idDetalleRecepcion; }
    public void setIdDetalleRecepcion(Integer idDetalleRecepcion) { this.idDetalleRecepcion = idDetalleRecepcion; }
    public RecepcionMercaderia getRecepcion() { return recepcion; }
    public void setRecepcion(RecepcionMercaderia recepcion) { this.recepcion = recepcion; }
    public Producto getProducto() { return producto; }
    public void setProducto(Producto producto) { this.producto = producto; }
    public Lote getLote() { return lote; }
    public void setLote(Lote lote) { this.lote = lote; }
    public Integer getCantidadEsperada() { return cantidadEsperada; }
    public void setCantidadEsperada(Integer cantidadEsperada) { this.cantidadEsperada = cantidadEsperada; }
    public Integer getCantidadRecibida() { return cantidadRecibida; }
    public void setCantidadRecibida(Integer cantidadRecibida) { this.cantidadRecibida = cantidadRecibida; }
    public BigDecimal getPrecioRecibido() { return precioRecibido; }
    public void setPrecioRecibido(BigDecimal precioRecibido) { this.precioRecibido = precioRecibido; }
    public LocalDate getFechaVencimientoRecibida() { return fechaVencimientoRecibida; }
    public void setFechaVencimientoRecibida(LocalDate fechaVencimientoRecibida) { this.fechaVencimientoRecibida = fechaVencimientoRecibida; }
    public String getObservaciones() { return observaciones; }
    public void setObservaciones(String observaciones) { this.observaciones = observaciones; }
}