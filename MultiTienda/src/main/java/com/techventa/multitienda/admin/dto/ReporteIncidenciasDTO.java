package com.techventa.multitienda.admin.dto;

import java.time.LocalDateTime;

public class ReporteIncidenciasDTO {
    private String codigo;
    private String titulo;
    private String tipo;
    private String prioridad;
    private String estado;
    private String tienda;
    private LocalDateTime fechaCreacion;

    public ReporteIncidenciasDTO(String codigo, String titulo, String tipo, String prioridad,
                                 String estado, String tienda, LocalDateTime fechaCreacion) {
        this.codigo = codigo;
        this.titulo = titulo;
        this.tipo = tipo;
        this.prioridad = prioridad;
        this.estado = estado;
        this.tienda = tienda;
        this.fechaCreacion = fechaCreacion;
    }

    // Getters y Setters
    public String getCodigo() { return codigo; }
    public void setCodigo(String codigo) { this.codigo = codigo; }
    public String getTitulo() { return titulo; }
    public void setTitulo(String titulo) { this.titulo = titulo; }
    public String getTipo() { return tipo; }
    public void setTipo(String tipo) { this.tipo = tipo; }
    public String getPrioridad() { return prioridad; }
    public void setPrioridad(String prioridad) { this.prioridad = prioridad; }
    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }
    public String getTienda() { return tienda; }
    public void setTienda(String tienda) { this.tienda = tienda; }
    public LocalDateTime getFechaCreacion() { return fechaCreacion; }
    public void setFechaCreacion(LocalDateTime fechaCreacion) { this.fechaCreacion = fechaCreacion; }
}