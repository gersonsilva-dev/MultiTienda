package com.techventa.multitienda.admin.dto;

import com.techventa.multitienda.admin.model.Caja;
import com.techventa.multitienda.admin.model.Rol;
import com.techventa.multitienda.admin.model.Tienda;

public class UsuarioRequest {

    private String nombres;
    private String apellidos;
    private String numeroDocumento;
    private String correoElectronico;
    private String telefono;
    private String direccion;
    private String contrasena;
    private Rol rol;
    private Tienda tienda;
    private Caja caja;
    private Boolean activo;

    // Getters y Setters
    public String getNombres() { return nombres; }
    public void setNombres(String nombres) { this.nombres = nombres; }

    public String getApellidos() { return apellidos; }
    public void setApellidos(String apellidos) { this.apellidos = apellidos; }

    public String getNumeroDocumento() { return numeroDocumento; }
    public void setNumeroDocumento(String numeroDocumento) { this.numeroDocumento = numeroDocumento; }

    public String getCorreoElectronico() { return correoElectronico; }
    public void setCorreoElectronico(String correoElectronico) { this.correoElectronico = correoElectronico; }

    public String getTelefono() { return telefono; }
    public void setTelefono(String telefono) { this.telefono = telefono; }

    public String getDireccion() { return direccion; }
    public void setDireccion(String direccion) { this.direccion = direccion; }

    public String getContrasena() { return contrasena; }
    public void setContrasena(String contrasena) { this.contrasena = contrasena; }

    public Rol getRol() { return rol; }
    public void setRol(Rol rol) { this.rol = rol; }

    public Tienda getTienda() { return tienda; }
    public void setTienda(Tienda tienda) { this.tienda = tienda; }

    public Caja getCaja() { return caja; }
    public void setCaja(Caja caja) { this.caja = caja; }

    public Boolean getActivo() { return activo; }
    public void setActivo(Boolean activo) { this.activo = activo; }
}