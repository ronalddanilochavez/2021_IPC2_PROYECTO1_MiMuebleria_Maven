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
public class mueblesDevueltos {
    String identificadorMueble;
    String nombreMueble;
    String cantidad;
    String nombreCliente;
    String NITCliente;
    String fechaDevolucion;
    String perdida;
    
    /////////////////////
    
    mueblesDevueltos() {
        identificadorMueble = "";
        nombreMueble = "";
        cantidad = "";
        nombreCliente = "";
        NITCliente = "";
        fechaDevolucion = "";
        perdida = "";
    }
    
    /////////////////////  

    public String getIdentificadorMueble() {
        return identificadorMueble;
    }

    public String getNombreMueble() {
        return nombreMueble;
    }

    public String getCantidad() {
        return cantidad;
    }

    public String getNombreCliente() {
        return nombreCliente;
    }

    public String getNITCliente() {
        return NITCliente;
    }

    public String getFechaDevolucion() {
        return fechaDevolucion;
    }

    public String getPerdida() {
        return perdida;
    }
    
    /////////////////////

    public void setIdentificadorMueble(String identificadorMueble) {
        this.identificadorMueble = identificadorMueble;
    }

    public void setNombreMueble(String nombreMueble) {
        this.nombreMueble = nombreMueble;
    }

    public void setCantidad(String cantidad) {
        this.cantidad = cantidad;
    }

    public void setNombreCliente(String nombreCliente) {
        this.nombreCliente = nombreCliente;
    }

    public void setNITCliente(String NITCliente) {
        this.NITCliente = NITCliente;
    }

    public void setFechaDevolucion(String fechaDevolucion) {
        this.fechaDevolucion = fechaDevolucion;
    }

    public void setPerdida(String perdida) {
        this.perdida = perdida;
    }
    
    /////////////////////
}
