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
public class pieza {
    String tipo;
    String costo;
    String cantidad;
    
    /////////////////////
    
    pieza() {
        tipo = "";
        costo = "";
        cantidad = "";
    }
    
    ///////////////////// 

    public String getTipo() {
        return tipo;
    }

    public String getCosto() {
        return costo;
    }

    public String getCantidad() {
        return cantidad;
    }
    
    /////////////////////

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public void setCosto(String costo) {
        this.costo = costo;
    }

    public void setCantidad(String cantidad) {
        this.cantidad = cantidad;
    }
    
    /////////////////////
    
}
