package myPackage;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Admin
 */
public class mueblesVendidos {
    String identificadorMueble;
    String nombreMueble;
    String usuario;
    String costo;
    String precio;
    String cantidad;
    String ganancia;
    String nombreCliente;
    String NITCliente;
    String fechaVenta;
    
    /////////////////////
    
    mueblesVendidos() {
        identificadorMueble = "";
        nombreMueble = "";
        usuario = "";
        costo = "";
        precio = "";
        cantidad = "";
        ganancia = "";
        nombreCliente = "";
        NITCliente = "";
        fechaVenta = "";
    }
    
    /////////////////////  

    public String getIdentificadorMueble() {
        return identificadorMueble;
    }

    public String getNombreMueble() {
        return nombreMueble;
    }

    public String getUsuario() {
        return usuario;
    }

    public String getCosto() {
        return costo;
    }

    public String getPrecio() {
        return precio;
    }

    public String getCantidad() {
        return cantidad;
    }

    public String getGanancia() {
        return ganancia;
    }

    public String getNombreCliente() {
        return nombreCliente;
    }

    public String getNITCliente() {
        return NITCliente;
    }

    public String getFechaVenta() {
        return fechaVenta;
    }
    
    /////////////////////

    public void setIdentificadorMueble(String identificadorMueble) {
        this.identificadorMueble = identificadorMueble;
    }

    public void setNombreMueble(String nombreMueble) {
        this.nombreMueble = nombreMueble;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public void setCosto(String costo) {
        this.costo = costo;
    }

    public void setPrecio(String precio) {
        this.precio = precio;
    }

    public void setCantidad(String cantidad) {
        this.cantidad = cantidad;
    }

    public void setGanancia(String ganancia) {
        this.ganancia = ganancia;
    }

    public void setNombreCliente(String nombreCliente) {
        this.nombreCliente = nombreCliente;
    }

    public void setNITCliente(String NITCliente) {
        this.NITCliente = NITCliente;
    }

    public void setFechaVenta(String fechaVenta) {
        this.fechaVenta = fechaVenta;
    }
    
    /////////////////////
}
