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
public class usuario {
    String nombre;
    String password;
    String tipo;
    String bloqueado;
    
    /////////////////////
    
    usuario() {
        nombre = "";
        password = "";
        tipo = "";
        bloqueado = "";
    }
    
    /////////////////////

    public String getNombre() {
        return nombre;
    }

    public String getPassword() {
        return password;
    }

    public String getTipo() {
        return tipo;
    }

    public String getBloqueado() {
        return bloqueado;
    }
    
    /////////////////////

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public void setBloqueado(String bloqueado) {
        this.bloqueado = bloqueado;
    }
    
    /////////////////////
    
}
