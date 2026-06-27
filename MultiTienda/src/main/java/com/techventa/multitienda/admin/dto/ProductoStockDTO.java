package com.techventa.multitienda.admin.dto;

public class ProductoStockDTO {
    private Integer idProducto;
    private String codigoBarras;
    private String nombreProducto;
    private String descripcionProducto;
    private String marca;
    private String modelo;
    private Integer stockActual;
    private Integer stockMinimo;

    // Constructor vacío
    public ProductoStockDTO() {}

    // Constructor con parámetros
    public ProductoStockDTO(Integer idProducto, String codigoBarras, String nombreProducto, 
                           String descripcionProducto, String marca, String modelo,
                           Integer stockActual, Integer stockMinimo) {
        this.idProducto = idProducto;
        this.codigoBarras = codigoBarras;
        this.nombreProducto = nombreProducto;
        this.descripcionProducto = descripcionProducto;
        this.marca = marca;
        this.modelo = modelo;
        this.stockActual = stockActual;
        this.stockMinimo = stockMinimo;
    }

    // Getters y Setters
    public Integer getIdProducto() { return idProducto; }
    public void setIdProducto(Integer idProducto) { this.idProducto = idProducto; }

    public String getCodigoBarras() { return codigoBarras; }
    public void setCodigoBarras(String codigoBarras) { this.codigoBarras = codigoBarras; }

    public String getNombreProducto() { return nombreProducto; }
    public void setNombreProducto(String nombreProducto) { this.nombreProducto = nombreProducto; }

    public String getDescripcionProducto() { return descripcionProducto; }
    public void setDescripcionProducto(String descripcionProducto) { this.descripcionProducto = descripcionProducto; }

    public String getMarca() { return marca; }
    public void setMarca(String marca) { this.marca = marca; }

    public String getModelo() { return modelo; }
    public void setModelo(String modelo) { this.modelo = modelo; }

    public Integer getStockActual() { return stockActual; }
    public void setStockActual(Integer stockActual) { this.stockActual = stockActual; }

    public Integer getStockMinimo() { return stockMinimo; }
    public void setStockMinimo(Integer stockMinimo) { this.stockMinimo = stockMinimo; }
}