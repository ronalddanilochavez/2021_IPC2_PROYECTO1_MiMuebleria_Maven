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
public class ensamblarMuebles {
    String identificadorMueble;
    String nombreMueble;
    String usuario;
    String fecha;
    String costo;
    String precio;
    
    /////////////////////
    
    ensamblarMuebles() {
        identificadorMueble = "";
        nombreMueble = "";
        usuario = "";
        fecha = "";
        costo = "";
        precio = "";
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

    public String getFecha() {
        return fecha;
    }

    public String getCosto() {
        return costo;
    }

    public String getPrecio() {
        return precio;
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

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public void setCosto(String costo) {
        this.costo = costo;
    }

    public void setPrecio(String precio) {
        this.precio = precio;
    }
    
    /////////////////////
}
