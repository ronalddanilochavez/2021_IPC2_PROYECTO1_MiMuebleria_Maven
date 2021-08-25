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
public class ensamblePiezas {
    String nombreMueble;
    String pieza;
    String cantidad;
    String costo;
    
    /////////////////////
    
    ensamblePiezas() {
        nombreMueble = "";
        pieza = "";
        cantidad = "";
        costo = "";
    }
    
    /////////////////////   

    public String getNombreMueble() {
        return nombreMueble;
    }

    public String getPieza() {
        return pieza;
    }

    public String getCantidad() {
        return cantidad;
    }

    public String getCosto() {
        return costo;
    }
    
    /////////////////////

    public void setNombreMueble(String nombreMueble) {
        this.nombreMueble = nombreMueble;
    }

    public void setPieza(String pieza) {
        this.pieza = pieza;
    }

    public void setCantidad(String cantidad) {
        this.cantidad = cantidad;
    }

    public void setCosto(String costo) {
        this.costo = costo;
    }
    
    /////////////////////
}
