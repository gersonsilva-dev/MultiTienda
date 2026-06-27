package com.techventa.multitienda.admin.dto;

import java.math.BigDecimal;

public class OfertaDTO {
    private Integer idOferta;
    private String nombreOferta;
    private BigDecimal valorDescuento;
    private Integer cantidadCompra;
    private Integer cantidadBeneficio;
    private BigDecimal montoMinimo;
    private Integer prioridad;
    private String estado;
    private Integer idTipoDescuento;
    private String nombreTipoDescuento;
    private Boolean aplicaTodasTiendas;

    // Constructor vacío
    public OfertaDTO() {}

    // Constructor con todos los campos
    public OfertaDTO(Integer idOferta, String nombreOferta, BigDecimal valorDescuento,
                     Integer cantidadCompra, Integer cantidadBeneficio, BigDecimal montoMinimo,
                     Integer prioridad, String estado, Integer idTipoDescuento, 
                     String nombreTipoDescuento) {
        this.idOferta = idOferta;
        this.nombreOferta = nombreOferta;
        this.valorDescuento = valorDescuento;
        this.cantidadCompra = cantidadCompra;
        this.cantidadBeneficio = cantidadBeneficio;
        this.montoMinimo = montoMinimo;
        this.prioridad = prioridad;
        this.estado = estado;
        this.idTipoDescuento = idTipoDescuento;
        this.nombreTipoDescuento = nombreTipoDescuento;
        this.aplicaTodasTiendas = true;
    }

    // Getters y Setters
    public Integer getIdOferta() { return idOferta; }
    public void setIdOferta(Integer idOferta) { this.idOferta = idOferta; }

    public String getNombreOferta() { return nombreOferta; }
    public void setNombreOferta(String nombreOferta) { this.nombreOferta = nombreOferta; }

    public BigDecimal getValorDescuento() { return valorDescuento; }
    public void setValorDescuento(BigDecimal valorDescuento) { this.valorDescuento = valorDescuento; }

    public Integer getCantidadCompra() { return cantidadCompra; }
    public void setCantidadCompra(Integer cantidadCompra) { this.cantidadCompra = cantidadCompra; }

    public Integer getCantidadBeneficio() { return cantidadBeneficio; }
    public void setCantidadBeneficio(Integer cantidadBeneficio) { this.cantidadBeneficio = cantidadBeneficio; }

    public BigDecimal getMontoMinimo() { return montoMinimo; }
    public void setMontoMinimo(BigDecimal montoMinimo) { this.montoMinimo = montoMinimo; }

    public Integer getPrioridad() { return prioridad; }
    public void setPrioridad(Integer prioridad) { this.prioridad = prioridad; }

    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }

    public Integer getIdTipoDescuento() { return idTipoDescuento; }
    public void setIdTipoDescuento(Integer idTipoDescuento) { this.idTipoDescuento = idTipoDescuento; }

    public String getNombreTipoDescuento() { return nombreTipoDescuento; }
    public void setNombreTipoDescuento(String nombreTipoDescuento) { this.nombreTipoDescuento = nombreTipoDescuento; }

    public Boolean getAplicaTodasTiendas() { return aplicaTodasTiendas; }
    public void setAplicaTodasTiendas(Boolean aplicaTodasTiendas) { this.aplicaTodasTiendas = aplicaTodasTiendas; }
}