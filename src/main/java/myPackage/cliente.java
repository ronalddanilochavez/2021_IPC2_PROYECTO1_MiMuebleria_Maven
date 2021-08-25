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
public class cliente {
    String NITCliente;
    String nombreCliente;
    String direccionCliente;
    String municipio;
    String departamento;
    
    /////////////////////
    
    cliente() {
        NITCliente = "";
        nombreCliente = "";
        direccionCliente = "";
        municipio = "";
        departamento = "";
    }
    
    /////////////////////    

    public String getNITCliente() {
        return NITCliente;
    }

    public String getNombreCliente() {
        return nombreCliente;
    }

    public String getDireccionCliente() {
        return direccionCliente;
    }

    public String getMunicipio() {
        return municipio;
    }

    public String getDepartamento() {
        return departamento;
    }

    /////////////////////  

    public void setNITCliente(String NITCliente) {
        this.NITCliente = NITCliente;
    }

    public void setNombreCliente(String nombreCliente) {
        this.nombreCliente = nombreCliente;
    }

    public void setDireccionCliente(String direccionCliente) {
        this.direccionCliente = direccionCliente;
    }

    public void setMunicipio(String municipio) {
        this.municipio = municipio;
    }

    public void setDepartamento(String departamento) {
        this.departamento = departamento;
    }
    
    
}
