package myPackage;

/*
Ronald Danilo Chávez Calderón
200130586
*/

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.PrintWriter;
import java.util.Scanner;
import java.util.*;
import java.sql.*;
import java.time.temporal.*;
import java.time.*;

import myPackage.cliente;
import myPackage.ensamblarMuebles;
import myPackage.ensamblePiezas;
import myPackage.mueblesDevueltos;
import myPackage.mueblesVendidos;
import myPackage.pieza;
import myPackage.usuario;

public class MainClass {
    
    //Global connection
    Connection connection;
    
    ArrayList<cliente> arrayCliente = new ArrayList<>();
    ArrayList<ensamblarMuebles> arrayEnsamblarMuebles = new ArrayList<>();
    ArrayList<ensamblePiezas> arrayEnsamblePiezas = new ArrayList<>();
    ArrayList<mueblesDevueltos> arrayMueblesDevueltos = new ArrayList<>();
    ArrayList<mueblesVendidos> arrayMueblesVendidos = new ArrayList<>();
    ArrayList<pieza> arrayPieza = new ArrayList<>();
    ArrayList<usuario> arrayUsuario = new ArrayList<>();
    
    public void main (String[] args) {
        System.out.println("Hola mundo");
    }
    
    // Conexion de mySQL
    public void mySQLConnect () {
        try {
            // Creando una conexión a la base de datos de mySQL
            String myDriver = "com.mysql.cj.jdbc.Driver";
            // parece que no es necesario esto "jdbc:mysql://localhost/mysql"
            // es recomendado "jdbc:mysql://localhost:3306"
            Class.forName(myDriver);
            //Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306", "root", "123456789");
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306", "root", "123456");
            System.out.println("CONEXION ESTABLECIDA!");

            Statement st = connection.createStatement();
            
            System.out.println("\nCONEXIÓN ESTABLECIDA CON LA BASE DE DATOS");
            
            st.close();
        }
        catch (Exception e){
            System.err.println("Excepción!");
            System.err.println(e.getMessage());
            System.out.println("\nPor favor ingrese correctamente la información del servidor");
        }
    }
    
    // Para buscar la lista de usuarios disponibles
    public boolean IsUsuario(String nombre, String password, String tipo) {
        boolean isTrue = false;
        
        arrayUsuario.clear();

        try {      
            // Nos conectamos a mySQL
            mySQLConnect();
            
            // crea un java statement
            Statement st = connection.createStatement();

            // To sort by UpDown or Category                      
            PreparedStatement ps = connection.prepareStatement("USE mimuebleria;");

            ps = connection.prepareStatement("SELECT * FROM mimuebleria.usuario ORDER BY mimuebleria.usuario.Nombre ASC");
            // en la sentencia se us ? para cada string que se utiliza despues del WHERE nombre = ?
            //ps.setString(1, "string1");
            //ps.setString(2, "string2");

            ResultSet rs = ps.executeQuery();

            // iteración a traves de java resultset
            while (rs.next())
            {   
                usuario myUsuario = new usuario();

                myUsuario.nombre = rs.getString("nombre"); ;
                myUsuario.password = rs.getString("password"); ;
                myUsuario.tipo = rs.getString("tipo"); ;
                myUsuario.bloqueado = rs.getString("bloqueado"); ;

                arrayUsuario.add(myUsuario);

                myUsuario = null;
            }

            ps.close();
            
            // Busca un usuario y password
            for (int i = 0; i < arrayUsuario.size(); i++) {
                if (arrayUsuario.get(i).nombre.equals(nombre) && arrayUsuario.get(i).password.equals(password) && arrayUsuario.get(i).tipo.equals(tipo) && arrayUsuario.get(i).bloqueado.equals("falso")){
                    isTrue = true;
                    break;
                }
            }
            
            arrayUsuario = null;
        }
        catch (Exception e)
        {
            System.err.println("Hay un error! ");
            System.err.println(e.getMessage());
        }
        
        return isTrue;
    }
    
    // Para buscar las piezas disponibles
    public String piezasDisponiblesSelect() {
        String piezasSelect = "<option value=\"pieza0\">------</option>";
        
        arrayPieza.clear();

        try {      
            // Nos conectamos a mySQL
            mySQLConnect();
            
            // crea un java statement
            Statement st = connection.createStatement();

            // To sort by UpDown or Category                      
            PreparedStatement ps = connection.prepareStatement("USE mimuebleria;");

            ps = connection.prepareStatement("SELECT * FROM mimuebleria.pieza ORDER BY mimuebleria.pieza.Tipo ASC");
            // en la sentencia se us ? para cada string que se utiliza despues del WHERE nombre = ?
            //ps.setString(1, "string1");
            //ps.setString(2, "string2");

            ResultSet rs = ps.executeQuery();

            // iteración a traves de java resultset
            while (rs.next())
            {   
                pieza myPieza = new pieza();

                myPieza.tipo = rs.getString("Tipo");
                myPieza.costo = rs.getString("Costo");
                myPieza.cantidad = rs.getString("Cantidad");

                arrayPieza.add(myPieza);

                myPieza = null;
            }

            ps.close();
            
            // Escribe el código html del select de piezas
            if (arrayPieza.size() > 0){
                String temp = arrayPieza.get(0).tipo;
                piezasSelect = "<option value=\"pieza"+ Integer.toString(0) + "\">" + arrayPieza.get(0).tipo + "</option>";
                
                for (int i = 1; i < arrayPieza.size(); i++) {
                    if (!arrayPieza.get(i).tipo.equals(temp)) {
                        piezasSelect += "<option value=\"pieza"+ Integer.toString(i) + "\">" + arrayPieza.get(i).tipo + "</option>";
                        temp = arrayPieza.get(i).tipo;
                    }
                }
            }
            
            arrayPieza.clear();
        }
        catch (Exception e)
        {
            System.err.println("Hay un error! ");
            System.err.println(e.getMessage());
        }
        
        return piezasSelect;
    }
    
    // Para buscar los muebles disponibles
    public String mueblesDisponiblesSelect() {
        String mueblesSelect = "<option value=\"mueble0\">------</option>";
        
        arrayEnsamblarMuebles.clear();

        try {      
            // Nos conectamos a mySQL
            mySQLConnect();
            
            // crea un java statement
            Statement st = connection.createStatement();

            // To sort by UpDown or Category                      
            PreparedStatement ps = connection.prepareStatement("USE mimuebleria;");

            ps = connection.prepareStatement("SELECT * FROM mimuebleria.ensamblarmuebles ORDER BY mimuebleria.ensamblarmuebles.IdentificadorMueble ASC");
            // en la sentencia se us ? para cada string que se utiliza despues del WHERE nombre = ?
            //ps.setString(1, "string1");
            //ps.setString(2, "string2");

            ResultSet rs = ps.executeQuery();

            // iteración a traves de java resultset
            while (rs.next())
            {   
                ensamblarMuebles myMueble = new ensamblarMuebles();

                myMueble.identificadorMueble = rs.getString("IdentificadorMueble");
                myMueble.nombreMueble = rs.getString("NombreMueble");
                myMueble.usuario = rs.getString("Usuario");
                myMueble.fecha = rs.getString("Fecha");
                myMueble.costo = rs.getString("Costo");
                myMueble.precio = rs.getString("Precio");

                arrayEnsamblarMuebles.add(myMueble);

                myMueble = null;
            }

            ps.close();
            
            // Escribe el código html del select de muebles ensamblados
            if (arrayEnsamblarMuebles.size() > 0){
                mueblesSelect = "<option value=\"mueble"+ Integer.toString(0) + "\">" + arrayEnsamblarMuebles.get(0).identificadorMueble + " " + arrayEnsamblarMuebles.get(0).nombreMueble +  "</option>";
                
                if (arrayEnsamblarMuebles.size() > 1) {
                    for (int i = 1; i < arrayEnsamblarMuebles.size(); i++) {
                        mueblesSelect += "<option value=\"mueble"+ Integer.toString(i) + "\">" + arrayEnsamblarMuebles.get(i).identificadorMueble + " " + arrayEnsamblarMuebles.get(i).nombreMueble +  "</option>";
                    }
                }
            }
            
            arrayEnsamblarMuebles.clear();
        }
        catch (Exception e)
        {
            System.err.println("Hay un error! ");
            System.err.println(e.getMessage());
        }
        
        return mueblesSelect;
    }
    
    // Para buscar los muebles definidos
    public String mueblesDefinidosSelect() {
        String mueblesSelect = "<option value=\"mueble0\">------</option>";
        
        arrayEnsamblePiezas.clear();

        try {      
            // Nos conectamos a mySQL
            mySQLConnect();
            
            // crea un java statement
            Statement st = connection.createStatement();

            // To sort by UpDown or Category                      
            PreparedStatement ps = connection.prepareStatement("USE mimuebleria;");

            ps = connection.prepareStatement("SELECT * FROM mimuebleria.ensamblepiezas ORDER BY mimuebleria.ensamblepiezas.NombreMueble ASC");
            // en la sentencia se us ? para cada string que se utiliza despues del WHERE nombre = ?
            //ps.setString(1, "string1");
            //ps.setString(2, "string2");

            ResultSet rs = ps.executeQuery();

            // iteración a traves de java resultset
            while (rs.next())
            {   
                ensamblePiezas myMueble = new ensamblePiezas();

                myMueble.nombreMueble = rs.getString("NombreMueble");
                myMueble.pieza = rs.getString("Pieza");
                myMueble.cantidad = rs.getString("Cantidad");

                arrayEnsamblePiezas.add(myMueble);

                myMueble = null;
            }

            ps.close();
            
            // Escribe el código html del select de los muebles
            if (arrayEnsamblePiezas.size() > 0){     
                mueblesSelect = "";
                
                String nombreMueble = arrayEnsamblePiezas.get(0).nombreMueble;
                mueblesSelect += "<option value=\"mueble0\">" + nombreMueble + "</option>";
                
                // Para agrupar las piezas en una lista con valores únicos
                for (int i = 0; i < arrayEnsamblePiezas.size(); i++) {
                    if (i == arrayEnsamblePiezas.size() - 1) {
                        if (!arrayEnsamblePiezas.get(i).nombreMueble.equals(nombreMueble)) {
                            nombreMueble = arrayEnsamblePiezas.get(i).nombreMueble;
                            mueblesSelect += "<option value=\"mueble0\">" + nombreMueble + "</option>";
                        }
                    }
                    else {
                        if (!arrayEnsamblePiezas.get(i).nombreMueble.equals(nombreMueble)) {
                            nombreMueble = arrayEnsamblePiezas.get(i).nombreMueble;
                            mueblesSelect += "<option value=\"mueble0\">" + nombreMueble + "</option>";
                        }
                    }
                }
            }
            
            arrayEnsamblePiezas.clear();
        }
        catch (Exception e)
        {
            System.err.println("Hay un error! ");
            System.err.println(e.getMessage());
        }
        
        return mueblesSelect;
    }
    
    // Para buscar los muebles vendidos disponibles
    public String usuariosFabricaDisponiblesSelect() {
        String usuariosFabricaDisponiblesSelect = "<option value=\"usuario0\">------</option>";
        
        arrayUsuario.clear();

        try {      
            // Nos conectamos a mySQL
            mySQLConnect();
            
            // crea un java statement
            Statement st = connection.createStatement();

            // To sort by UpDown or Category                      
            PreparedStatement ps = connection.prepareStatement("USE mimuebleria;");

            ps = connection.prepareStatement("SELECT * FROM mimuebleria.usuario WHERE Tipo = ? ORDER BY mimuebleria.usuario.Nombre ASC");
            // en la sentencia se us ? para cada string que se utiliza despues del WHERE nombre = ?
            //ps.setString(1, "string1");
            //ps.setString(2, "string2");
            ps.setString(1, "1");

            ResultSet rs = ps.executeQuery();

            // iteración a traves de java resultset
            while (rs.next())
            {   
                usuario myUsuario = new usuario();

                myUsuario.nombre = rs.getString("Nombre");
                myUsuario.password = rs.getString("Password");
                myUsuario.tipo = rs.getString("Tipo");
                myUsuario.bloqueado = rs.getString("Bloqueado");

                arrayUsuario.add(myUsuario);

                myUsuario = null;
            }

            ps.close();
            
            // Escribe el código html del select de muebles vendidos
            if (arrayUsuario.size() > 0){
                usuariosFabricaDisponiblesSelect = "";
                
                for (int i = 0; i < arrayUsuario.size(); i++) {
                    usuariosFabricaDisponiblesSelect += "<option value=\"usuario0\">" + arrayUsuario.get(i).nombre + "</option>";
                }
            }
            
            arrayUsuario.clear();
        }
        catch (Exception e)
        {
            System.err.println("Hay un error! ");
            System.err.println(e.getMessage());
        }
        
        return usuariosFabricaDisponiblesSelect;
    }
    
    // Para buscar los clientes disponibles
    public String clientesDisponiblesSelect() {
        String clientesSelect = "<option value=\"cliente0\">------</option>";
        
        arrayCliente.clear();

        try {      
            // Nos conectamos a mySQL
            mySQLConnect();
            
            // crea un java statement
            Statement st = connection.createStatement();

            // To sort by UpDown or Category                      
            PreparedStatement ps = connection.prepareStatement("USE mimuebleria;");

            ps = connection.prepareStatement("SELECT * FROM mimuebleria.cliente ORDER BY mimuebleria.cliente.NITCliente ASC");
            // en la sentencia se us ? para cada string que se utiliza despues del WHERE nombre = ?
            //ps.setString(1, "string1");
            //ps.setString(2, "string2");

            ResultSet rs = ps.executeQuery();

            // iteración a traves de java resultset
            while (rs.next())
            {   
                cliente myCliente = new cliente();

                myCliente.NITCliente = rs.getString("NITCliente");
                myCliente.nombreCliente = rs.getString("NombreCliente");
                myCliente.direccionCliente = rs.getString("DireccionCliente");
                myCliente.municipio = rs.getString("Municipio");
                myCliente.departamento = rs.getString("Departamento");

                arrayCliente.add(myCliente);

                myCliente = null;
            }

            ps.close();
            
            // Escribe el código html del select de muebles ensamblados
            if (arrayCliente.size() > 0){
                clientesSelect = "<option value=\"cliente"+ Integer.toString(0) + "\">" + arrayCliente.get(0).NITCliente + " " + arrayCliente.get(0).getNombreCliente() +  "</option>";
                
                if (arrayCliente.size() > 1) {
                    for (int i = 1; i < arrayCliente.size(); i++) {
                        clientesSelect += "<option value=\"cliente"+ Integer.toString(i) + "\">" + arrayCliente.get(i).NITCliente + " " + arrayCliente.get(i).nombreCliente +  "</option>";
                    }
                }
            }
            
            arrayCliente.clear();
        }
        catch (Exception e)
        {
            System.err.println("Hay un error! ");
            System.err.println(e.getMessage());
        }
        
        return clientesSelect;
    }
    
    // Para buscar los usuarios disponibles
    public String usuariosDisponiblesSelect() {
        String usuariosSelect = "<option value=\"cliente0\">------</option>";
        
        arrayUsuario.clear();

        try {      
            // Nos conectamos a mySQL
            mySQLConnect();
            
            // crea un java statement
            Statement st = connection.createStatement();

            // To sort by UpDown or Category                      
            PreparedStatement ps = connection.prepareStatement("USE mimuebleria;");

            ps = connection.prepareStatement("SELECT * FROM mimuebleria.usuario ORDER BY mimuebleria.usuario.Nombre ASC");
            // en la sentencia se us ? para cada string que se utiliza despues del WHERE nombre = ?
            //ps.setString(1, "string1");
            //ps.setString(2, "string2");

            ResultSet rs = ps.executeQuery();

            // iteración a traves de java resultset
            while (rs.next())
            {   
                usuario myUsuario = new usuario();

                myUsuario.nombre = rs.getString("Nombre");
                myUsuario.password = rs.getString("Password");
                myUsuario.tipo = rs.getString("Tipo");
                myUsuario.bloqueado = rs.getString("Bloqueado");

                arrayUsuario.add(myUsuario);

                myUsuario = null;
            }

            ps.close();
            
            // Escribe el código html del select de muebles ensamblados
            if (arrayUsuario.size() > 0){
                usuariosSelect = "<option value=\"cliente"+ Integer.toString(0) + "\">" + arrayUsuario.get(0).nombre +  "</option>";
                
                if (arrayUsuario.size() > 1) {
                    for (int i = 1; i < arrayUsuario.size(); i++) {
                        usuariosSelect += "<option value=\"cliente"+ Integer.toString(i) + "\">" + arrayUsuario.get(i).nombre +  "</option>";
                    }
                }
            }
            
            arrayUsuario.clear();
        }
        catch (Exception e)
        {
            System.err.println("Hay un error! ");
            System.err.println(e.getMessage());
        }
        
        return usuariosSelect;
    }
    
    // Para buscar las piezas que estan por agotarse
    public String piezasPorAgotarse() {
        String piezasPorAgotarse = "";
        
        arrayPieza.clear();

        try {      
            // Nos conectamos a mySQL
            mySQLConnect();
            
            // crea un java statement
            Statement st = connection.createStatement();

            // To sort by UpDown or Category                      
            PreparedStatement ps = connection.prepareStatement("USE mimuebleria;");

            ps = connection.prepareStatement("SELECT * FROM mimuebleria.pieza ORDER BY mimuebleria.pieza.Tipo ASC");
            // en la sentencia se us ? para cada string que se utiliza despues del WHERE nombre = ?
            //ps.setString(1, "string1");
            //ps.setString(2, "string2");

            ResultSet rs = ps.executeQuery();

            // iteración a traves de java resultset
            while (rs.next())
            {   
                pieza myPieza = new pieza();

                myPieza.tipo = rs.getString("Tipo");
                myPieza.costo = rs.getString("Costo");
                myPieza.cantidad = rs.getString("Cantidad");

                arrayPieza.add(myPieza);

                myPieza = null;
            }

            ps.close();
            
            // Escribe el código html del select de piezas
            if (arrayPieza.size() > 0){  
                String temp = arrayPieza.get(0).tipo;
                int tempInt = Integer.valueOf(arrayPieza.get(0).cantidad);
                
                if (arrayPieza.size() == 1) {
                        if ((Integer.parseInt(arrayPieza.get(0).cantidad) <= 4)) {
                            piezasPorAgotarse += "<tr><td>" + arrayPieza.get(0).tipo + "</td><td>" + arrayPieza.get(0).costo + "</td><td>" + arrayPieza.get(0).cantidad + "</td></tr>";
                        }
                    }
                else {
                    for (int i = 1; i < arrayPieza.size(); i++) {
                        if (i == arrayPieza.size() - 1)
                        {
                            if (arrayPieza.get(i).tipo.equals(temp)) {
                                tempInt += Integer.valueOf(arrayPieza.get(i).cantidad);
                                if (tempInt <= 4) {
                                    piezasPorAgotarse += "<tr><td>" + arrayPieza.get(i).tipo + "</td><td>" + arrayPieza.get(i).costo + "</td><td>" + String.valueOf(tempInt) + "</td></tr>";
                                }
                            }
                            else if (!arrayPieza.get(i).tipo.equals(temp)) {
                                if (Integer.parseInt(arrayPieza.get(i).cantidad) <= 4) {
                                    piezasPorAgotarse += "<tr><td>" + arrayPieza.get(i).tipo + "</td><td>" + arrayPieza.get(i).costo + "</td><td>" + arrayPieza.get(i).cantidad + "</td></tr>";
                                }
                            }                           
                            break;
                        }
                        
                        if (arrayPieza.get(i).tipo.equals(temp)) {
                            tempInt += Integer.valueOf(arrayPieza.get(i).cantidad);
                        }
                        else if (!arrayPieza.get(i).tipo.equals(temp)) {
                            if (tempInt <= 4) {
                                piezasPorAgotarse += "<tr><td>" + arrayPieza.get(i - 1).tipo + "</td><td>" + arrayPieza.get(i - 1).costo + "</td><td>" + String.valueOf(tempInt) + "</td></tr>";
                            }
                            temp = arrayPieza.get(i).tipo;
                            tempInt = Integer.valueOf(arrayPieza.get(i).cantidad);
                        }
                    }
                }
            }
            
            arrayPieza.clear();
        }
        catch (Exception e)
        {
            System.err.println("Hay un error! ");
            System.err.println(e.getMessage());
        }
        
        return piezasPorAgotarse;
    }
    
    // Para crear una pieza nueva
    public String fabricaCrearPieza(String piezaTipo, String piezaCosto, String piezaCantidad) {
        String crearPieza = "";
        
        arrayPieza.clear();

        try {      
            // Nos conectamos a mySQL
            mySQLConnect();
            
            // crea un java statement
            Statement st = connection.createStatement();
            
            // To sort by UpDown or Category                      
            PreparedStatement ps = connection.prepareStatement("USE mimuebleria;");
            
            ps = connection.prepareStatement("SELECT * FROM mimuebleria.pieza WHERE Tipo = ?;");
            // en la sentencia se us ? para cada string que se utiliza despues del WHERE nombre = ?
            //ps.setString(1, "string1");
            //ps.setString(2, "string2");
            ps.setString(1, piezaTipo);
            
            ResultSet rs = ps.executeQuery();

            // iteración a traves de java resultset
            while (rs.next())
            {   
                pieza myPieza = new pieza();

                myPieza.tipo = rs.getString("Tipo");
                myPieza.costo = rs.getString("Costo");
                myPieza.cantidad = rs.getString("Cantidad");

                arrayPieza.add(myPieza);

                myPieza = null;
            }
            
            // Escribe el código html del select de piezas
            if (arrayPieza.size() > 0){ 
                
                int intUpdate = 0;
                int cantidadPiezas = arrayPieza.size();
                    
                // Primero se borran las piezas
                ps = connection.prepareStatement("DELETE FROM mimuebleria.pieza WHERE Tipo = ?;");
                // en la sentencia se us ? para cada string que se utiliza despues del WHERE nombre = ?
                //ps.setString(1, "string1");
                //ps.setString(2, "string2");
                ps.setString(1, piezaTipo);

                intUpdate = ps.executeUpdate();
                
                ps.close();

                for (int i = 0; i < Integer.valueOf(piezaCantidad) + cantidadPiezas; i++) {

                    // Aqui el REPLACE añade piezas porque no hay PRIMARY KEY
                    ps = connection.prepareStatement("REPLACE INTO mimuebleria.pieza VALUES (?,?,?);");
                    // en la sentencia se us ? para cada string que se utiliza despues del WHERE nombre = ?
                    //ps.setString(1, "string1");
                    //ps.setString(2, "string2");
                    ps.setString(1, piezaTipo);
                    ps.setString(2, piezaCosto);
                    ps.setString(3, "1");

                    intUpdate = ps.executeUpdate();
                    
                    ps.close();
                }     
                    
                crearPieza = "<h2>Pieza creada</h2><h2>Tipo: " + piezaTipo + "</h2><h2>Cantidad: " + piezaCantidad + "</h2><h2>Costo: " + piezaCosto + "</h2><br><br>";
            }
            else {
                for (int i = 0; i < Integer.valueOf(piezaCantidad); i++) {
                    // El REPLACE funciona añadiendo porque no hay PRIMARY KEY
                    ps = connection.prepareStatement("REPLACE INTO mimuebleria.pieza VALUES (?,?,?);");
                    // en la sentencia se us ? para cada string que se utiliza despues del WHERE nombre = ?
                    //ps.setString(1, "string1");
                    //ps.setString(2, "string2");
                    ps.setString(1, piezaTipo);
                    ps.setString(2, piezaCosto);
                    ps.setString(3, "1");

                    int intUpdate = ps.executeUpdate();
                    
                    ps.close();
                }
                
                crearPieza = "<h2>Pieza creada</h2><h2>Tipo: " + piezaTipo + "</h2><h2>Cantidad: " + piezaCantidad + "</h2><h2>Costo: " + piezaCosto + "</h2><br><br>";
            }
            
        }
        catch (Exception e)
        {
            System.err.println("Hay un error! ");
            System.err.println(e.getMessage());
        }
        
        return crearPieza;
    }
    
    // Para modificar una pieza nueva
    public String fabricaModificarPieza(String piezaTipo, String piezaCosto, String piezaCantidad) {
        String modificarPieza = "";
        
        arrayPieza.clear();

        try {      
            // Nos conectamos a mySQL
            mySQLConnect();
            
            // crea un java statement
            Statement st = connection.createStatement();

            // To sort by UpDown or Category                      
            PreparedStatement ps = connection.prepareStatement("USE mimuebleria;");
            
            ps = connection.prepareStatement("SELECT * FROM mimuebleria.pieza WHERE Tipo = ?;");
            // en la sentencia se us ? para cada string que se utiliza despues del WHERE nombre = ?
            //ps.setString(1, "string1");
            //ps.setString(2, "string2");
            ps.setString(1, piezaTipo);
            
            ResultSet rs = ps.executeQuery();

            // iteración a traves de java resultset
            while (rs.next())
            {   
                pieza myPieza = new pieza();

                myPieza.tipo = rs.getString("Tipo");
                myPieza.costo = rs.getString("Costo");
                myPieza.cantidad = rs.getString("Cantidad");

                arrayPieza.add(myPieza);

                myPieza = null;
            }
            
            // Escribe el código html del select de piezas
            if (arrayPieza.size() > 0){ 
                
                int intUpdate = 0;
                    
                // Primero se borran las piezas
                ps = connection.prepareStatement("DELETE FROM mimuebleria.pieza WHERE Tipo = ?;");
                // en la sentencia se us ? para cada string que se utiliza despues del WHERE nombre = ?
                //ps.setString(1, "string1");
                //ps.setString(2, "string2");
                ps.setString(1, piezaTipo);

                intUpdate = ps.executeUpdate();

                for (int i = 0; i < Integer.valueOf(piezaCantidad); i++) {

                    // Aqui el REPLACE añade piezas porque no hay PRIMARY KEY
                    ps = connection.prepareStatement("REPLACE INTO mimuebleria.pieza VALUES (?,?,?);");
                    // en la sentencia se us ? para cada string que se utiliza despues del WHERE nombre = ?
                    //ps.setString(1, "string1");
                    //ps.setString(2, "string2");
                    ps.setString(1, piezaTipo);
                    ps.setString(2, piezaCosto);
                    ps.setString(3, "1");

                    intUpdate = ps.executeUpdate();
                }     
                    
                modificarPieza = "<h2>Pieza modificada</h2><h2>Tipo: " + piezaTipo + "</h2><h2>Cantidad: " + piezaCantidad + "</h2><h2>Costo: " + piezaCosto + "</h2><br><br>";
            }
            else {
                modificarPieza = "<h2>¡La pieza NO EXISTE!</h2><br><br>";
            }
            
            ps.close();
            
            arrayPieza.clear();
            
        }
        catch (Exception e)
        {
            System.err.println("Hay un error! ");
            System.err.println(e.getMessage());
        }
        
        return modificarPieza;
    }
    
    // Para borrar una pieza nueva
    public String fabricaBorrarPieza(String piezaTipo) {
        String borrarPieza = "";
        
        arrayPieza.clear();

        try {      
            // Nos conectamos a mySQL
            mySQLConnect();
            
            // crea un java statement
            Statement st = connection.createStatement();

            // To sort by UpDown or Category                      
            PreparedStatement ps = connection.prepareStatement("USE mimuebleria;");
            
            ps = connection.prepareStatement("DELETE FROM mimuebleria.pieza WHERE Tipo = ?;");
            // en la sentencia se us ? para cada string que se utiliza despues del WHERE nombre = ?
            //ps.setString(1, "string1");
            //ps.setString(2, "string2");
            ps.setString(1, piezaTipo);
            
            int intUpdate = ps.executeUpdate();
            
            ps.close();
            
        }
        catch (Exception e)
        {
            System.err.println("Hay un error! ");
            System.err.println(e.getMessage());
        }
        
        return borrarPieza = "<h2>Pieza borrada</h2><h2>Tipo: " + piezaTipo + "</h2><br><br>";
    }
    
    // Para definir ensamble de mueble a partir de piezas
    public String definirEnsamblePiezas(String muebleNombre, String mueblePieza, String muebleCantidad) {
        String definirEnsamblePiezas = "";
        
        arrayEnsamblePiezas.clear();

        try {      
            // Nos conectamos a mySQL
            mySQLConnect();
            
            // crea un java statement
            Statement st = connection.createStatement();
            
            if (Integer.valueOf(muebleCantidad) >= 0) {                
                PreparedStatement ps = connection.prepareStatement("USE mimuebleria;");
                // El REPLACE funciona añadiendo porque no hay PRIMARY KEY
                ps = connection.prepareStatement("REPLACE INTO mimuebleria.ensamblepiezas VALUES (?,?,?);");
                // en la sentencia se us ? para cada string que se utiliza despues del WHERE nombre = ?
                //ps.setString(1, "string1");
                //ps.setString(2, "string2");
                ps.setString(1, muebleNombre);
                ps.setString(2, mueblePieza);
                ps.setString(3, muebleCantidad);

                int intUpdate = ps.executeUpdate();

                ps.close();

                ps = connection.prepareStatement("SELECT * FROM mimuebleria.ensamblepiezas WHERE NombreMueble = ?;");
                // en la sentencia se us ? para cada string que se utiliza despues del WHERE nombre = ?
                //ps.setString(1, "string1");
                //ps.setString(2, "string2");
                ps.setString(1, muebleNombre);

                ResultSet rs = ps.executeQuery();

                // iteración a traves de java resultset
                while (rs.next())
                {   
                    ensamblePiezas myEnsamblePiezas = new ensamblePiezas();

                    myEnsamblePiezas.nombreMueble = rs.getString("NombreMueble");
                    myEnsamblePiezas.pieza = rs.getString("Pieza");
                    myEnsamblePiezas.cantidad = rs.getString("Cantidad");

                    arrayEnsamblePiezas.add(myEnsamblePiezas);

                    myEnsamblePiezas = null;
                }
                
                ps.close();
                
                if (arrayEnsamblePiezas.size() > 0) {
                    for (int i = 0; i < arrayEnsamblePiezas.size(); i++) {
                        definirEnsamblePiezas += "<h2>" + arrayEnsamblePiezas.get(i).nombreMueble + ", " + arrayEnsamblePiezas.get(i).pieza + ", "  + arrayEnsamblePiezas.get(i).cantidad + "</h2>";
                    }
                }

            }
            
            arrayEnsamblePiezas.clear();
            
        }
        catch (Exception e)
        {
            System.err.println("Hay un error! ");
            System.err.println(e.getMessage());
        }
        
        return definirEnsamblePiezas;
    }
    
    // Para ensamblar muebles a partir de piezas
    public String fabricaCrearMueble(String piezaTipo, String piezaCosto, String piezaCantidad) {
        String ensamblarMueble = "";
        
        arrayPieza.clear();

        try {      
            // Nos conectamos a mySQL
            mySQLConnect();
            
            // crea un java statement
            Statement st = connection.createStatement();
            
            if (Integer.valueOf(piezaCantidad) >= 0) {                
                for (int i = 0; i < Integer.valueOf(piezaCantidad); i++) {
                    PreparedStatement ps = connection.prepareStatement("USE mimuebleria;");
                    // El REPLACE funciona añadiendo porque no hay PRIMARY KEY
                    ps = connection.prepareStatement("REPLACE INTO mimuebleria.pieza VALUES (?,?,?);");
                    // en la sentencia se us ? para cada string que se utiliza despues del WHERE nombre = ?
                    //ps.setString(1, "string1");
                    //ps.setString(2, "string2");
                    ps.setString(1, piezaTipo);
                    ps.setString(2, piezaCosto);
                    ps.setString(3, "1");

                    ResultSet rs = ps.executeQuery();
                    
                    ps.close();
                }
            }
            /*

            // iteración a traves de java resultset
            while (rs.next())
            {   
                pieza myPieza = new pieza();

                myPieza.tipo = rs.getString("Tipo");
                myPieza.costo = rs.getString("Costo");
                myPieza.cantidad = rs.getString("Cantidad");

                arrayPieza.add(myPieza);

                myPieza = null;
            }

            ps.close();
            
            // Escribe el código html del select de piezas
            if (arrayPieza.size() > 0){  
                
            }
            
            arrayPieza.clear();

            */
            
            ensamblarMueble = "<h2>Pieza creada</h2><h2>Tipo: " + piezaTipo + "</h2><h2>Cantidad: " + piezaCantidad + "</h2><h2>Costo: " + piezaCosto + "</h2><br><br>";
            
        }
        catch (Exception e)
        {
            System.err.println("Hay un error! ");
            System.err.println(e.getMessage());
        }
        
        return ensamblarMueble;
    }
    
    // Para consultar las piezas de manera ascendente
    public String fabricaConsultarPiezasAscendente() {
        String piezasAscendente = "";
        
        arrayPieza.clear();

        try {      
            // Nos conectamos a mySQL
            mySQLConnect();
            
            // crea un java statement
            Statement st = connection.createStatement();

            // To sort by UpDown or Category                      
            PreparedStatement ps = connection.prepareStatement("USE mimuebleria;");

            ps = connection.prepareStatement("SELECT * FROM mimuebleria.pieza ORDER BY mimuebleria.pieza.Tipo ASC");
            // en la sentencia se us ? para cada string que se utiliza despues del WHERE nombre = ?
            //ps.setString(1, "string1");
            //ps.setString(2, "string2");

            ResultSet rs = ps.executeQuery();

            // iteración a traves de java resultset
            while (rs.next())
            {   
                pieza myPieza = new pieza();

                myPieza.tipo = rs.getString("Tipo");
                myPieza.costo = rs.getString("Costo");
                myPieza.cantidad = rs.getString("Cantidad");

                arrayPieza.add(myPieza);

                myPieza = null;
            }

            ps.close();
            
            // Escribe el código html del select de piezas
            if (arrayPieza.size() > 0){               
                ArrayList<pieza> piezaLista = new ArrayList<>();
                String tipo = arrayPieza.get(0).tipo;
                String costo = arrayPieza.get(0).costo;
                int cantidad = 0;
                
                // Para agrupar las piezas en una lista con valores únicos
                for (int i = 0; i < arrayPieza.size(); i++) {
                    if (i == arrayPieza.size() - 1) {
                        if (arrayPieza.get(i).tipo.equals(tipo)) {
                            cantidad++;
                            pieza myPieza = new pieza();
                            myPieza.tipo = tipo;
                            myPieza.costo = costo;
                            myPieza.cantidad = String.valueOf(cantidad);
                            piezaLista.add(myPieza);
                            myPieza = null;
                        }
                        else {
                            pieza myPieza = new pieza();
                            myPieza.tipo = arrayPieza.get(i).tipo;
                            myPieza.costo = arrayPieza.get(i).costo;
                            myPieza.cantidad = String.valueOf(1);
                            piezaLista.add(myPieza);
                            myPieza = null;
                        }
                    }
                    else {
                        if (arrayPieza.get(i).tipo.equals(tipo)) {
                            cantidad++;
                        }
                        else {
                            pieza myPieza = new pieza();
                            myPieza.tipo = tipo;
                            myPieza.costo = costo;
                            myPieza.cantidad = String.valueOf(cantidad);
                            piezaLista.add(myPieza);
                            myPieza = null;
                            tipo = arrayPieza.get(i).tipo;
                            costo = arrayPieza.get(i).costo;
                            cantidad = 1;
                        }
                    }
                }
                
                ArrayList<Integer> listaNumeros = new ArrayList<>();
                
                for (int i = 0; i < piezaLista.size(); i++) {
                    listaNumeros.add(Integer.valueOf(piezaLista.get(i).cantidad));
                }
                
                Collections.sort(listaNumeros);
                
                ArrayList<pieza> piezaOrdenado = new ArrayList<>();
                
                // En modo ascendente
                for (int i = 0; i < listaNumeros.size(); i++) {
                    for (int j = 0; j < piezaLista.size(); j++) {
                        if (Integer.valueOf(piezaLista.get(j).cantidad).equals(listaNumeros.get(i))) {
                            piezaOrdenado.add(piezaLista.get(j));
                        }
                    }
                }
                
                for (int i = 0; i < piezaOrdenado.size(); i++) {
                    piezasAscendente += "<tr><td>" + piezaOrdenado.get(i).tipo + "</td><td>" + piezaOrdenado.get(i).costo + "</td><td>" + piezaOrdenado.get(i).cantidad + "</td></tr>";
                }
            }
            
            arrayPieza.clear();
        }
        catch (Exception e)
        {
            System.err.println("Hay un error! ");
            System.err.println(e.getMessage());
        }
        
        return piezasAscendente;
    }
    
    // Para consultar las piezas de manera ascendente
    public String fabricaConsultarPiezasDescendente() {
        String piezasDescendente = "";
        
        arrayPieza.clear();

        try {      
            // Nos conectamos a mySQL
            mySQLConnect();
            
            // crea un java statement
            Statement st = connection.createStatement();

            // To sort by UpDown or Category                      
            PreparedStatement ps = connection.prepareStatement("USE mimuebleria;");

            ps = connection.prepareStatement("SELECT * FROM mimuebleria.pieza ORDER BY mimuebleria.pieza.Tipo ASC");
            // en la sentencia se us ? para cada string que se utiliza despues del WHERE nombre = ?
            //ps.setString(1, "string1");
            //ps.setString(2, "string2");

            ResultSet rs = ps.executeQuery();

            // iteración a traves de java resultset
            while (rs.next())
            {   
                pieza myPieza = new pieza();

                myPieza.tipo = rs.getString("Tipo");
                myPieza.costo = rs.getString("Costo");
                myPieza.cantidad = rs.getString("Cantidad");

                arrayPieza.add(myPieza);

                myPieza = null;
            }

            ps.close();
            
            // Escribe el código html del select de piezas
            if (arrayPieza.size() > 0){               
                ArrayList<pieza> piezaLista = new ArrayList<>();
                String tipo = arrayPieza.get(0).tipo;
                String costo = arrayPieza.get(0).costo;
                int cantidad = 0;
                
                // Para agrupar las piezas en una lista con valores únicos
                for (int i = 0; i < arrayPieza.size(); i++) {
                    if (i == arrayPieza.size() - 1) {
                        if (arrayPieza.get(i).tipo.equals(tipo)) {
                            cantidad++;
                            pieza myPieza = new pieza();
                            myPieza.tipo = tipo;
                            myPieza.costo = costo;
                            myPieza.cantidad = String.valueOf(cantidad);
                            piezaLista.add(myPieza);
                            myPieza = null;
                        }
                        else {
                            pieza myPieza = new pieza();
                            myPieza.tipo = arrayPieza.get(i).tipo;
                            myPieza.costo = arrayPieza.get(i).costo;
                            myPieza.cantidad = String.valueOf(1);
                            piezaLista.add(myPieza);
                            myPieza = null;
                        }
                    }
                    else {
                        if (arrayPieza.get(i).tipo.equals(tipo)) {
                            cantidad++;
                        }
                        else {
                            pieza myPieza = new pieza();
                            myPieza.tipo = tipo;
                            myPieza.costo = costo;
                            myPieza.cantidad = String.valueOf(cantidad);
                            piezaLista.add(myPieza);
                            myPieza = null;
                            tipo = arrayPieza.get(i).tipo;
                            costo = arrayPieza.get(i).costo;
                            cantidad = 1;
                        }
                    }
                }
                
                ArrayList<Integer> listaNumeros = new ArrayList<>();
                
                for (int i = 0; i < piezaLista.size(); i++) {
                    listaNumeros.add(Integer.valueOf(piezaLista.get(i).cantidad));
                }
                
                Collections.sort(listaNumeros);
                
                ArrayList<pieza> piezaOrdenado = new ArrayList<>();
                
                // En modo descendente
                for (int i = listaNumeros.size() - 1; i >= 0; i--) {
                    for (int j = 0; j < piezaLista.size(); j++) {
                        if (Integer.valueOf(piezaLista.get(j).cantidad).equals(listaNumeros.get(i))) {
                            piezaOrdenado.add(piezaLista.get(j));
                        }
                    }
                }
                
                for (int i = 0; i < piezaOrdenado.size(); i++) {
                    piezasDescendente += "<tr><td>" + piezaOrdenado.get(i).tipo + "</td><td>" + piezaOrdenado.get(i).costo + "</td><td>" + piezaOrdenado.get(i).cantidad + "</td></tr>";
                }
            }
            
            arrayPieza.clear();
        }
        catch (Exception e)
        {
            System.err.println("Hay un error! ");
            System.err.println(e.getMessage());
        }
        
        return piezasDescendente;
    }
    
    // Para buscar los muebles de manera ascendente
    public String fabricaConsultarMueblesAscendente() {
        String mueblesAscendente = "";
        
        arrayEnsamblarMuebles.clear();

        try {      
            // Nos conectamos a mySQL
            mySQLConnect();
            
            // crea un java statement
            Statement st = connection.createStatement();

            // To sort by UpDown or Category                      
            PreparedStatement ps = connection.prepareStatement("USE mimuebleria;");

            ps = connection.prepareStatement("SELECT * FROM mimuebleria.ensamblarmuebles ORDER BY mimuebleria.ensamblarmuebles.Fecha ASC");
            // en la sentencia se us ? para cada string que se utiliza despues del WHERE nombre = ?
            //ps.setString(1, "string1");
            //ps.setString(2, "string2");

            ResultSet rs = ps.executeQuery();

            // iteración a traves de java resultset
            while (rs.next())
            {   
                ensamblarMuebles myMueble = new ensamblarMuebles();

                myMueble.identificadorMueble = rs.getString("IdentificadorMueble");
                myMueble.nombreMueble = rs.getString("NombreMueble");
                myMueble.usuario = rs.getString("Usuario");
                myMueble.fecha = rs.getString("Fecha");
                myMueble.costo = rs.getString("Costo");
                myMueble.precio = rs.getString("Precio");

                arrayEnsamblarMuebles.add(myMueble);

                myMueble = null;
            }

            ps.close();
            
            // Escribe el código html del select de muebles ensamblados
            if (arrayEnsamblarMuebles.size() > 0){
                if (arrayEnsamblarMuebles.size() > 1) {
                    for (int i = 0; i < arrayEnsamblarMuebles.size(); i++) {
                        mueblesAscendente += "<tr><td>" + arrayEnsamblarMuebles.get(i).identificadorMueble + "</td><td>" + arrayEnsamblarMuebles.get(i).nombreMueble + "</td><td>" + arrayEnsamblarMuebles.get(i).usuario + "</td><td>" + arrayEnsamblarMuebles.get(i).fecha + "</td><td>" + arrayEnsamblarMuebles.get(i).costo + "</td><td>" + arrayEnsamblarMuebles.get(i).precio + "</td></tr>";
                    }
                }
            }
            
            arrayEnsamblarMuebles.clear();
        }
        catch (Exception e)
        {
            System.err.println("Hay un error! ");
            System.err.println(e.getMessage());
        }
        
        return mueblesAscendente;
    }
    
    // Para buscar los muebles de manera descendente
    public String fabricaConsultarMueblesDescendente() {
        String mueblesDescendente = "";
        
        arrayEnsamblarMuebles.clear();

        try {      
            // Nos conectamos a mySQL
            mySQLConnect();
            
            // crea un java statement
            Statement st = connection.createStatement();

            // To sort by UpDown or Category                      
            PreparedStatement ps = connection.prepareStatement("USE mimuebleria;");

            ps = connection.prepareStatement("SELECT * FROM mimuebleria.ensamblarmuebles ORDER BY mimuebleria.ensamblarmuebles.Fecha DESC");
            // en la sentencia se us ? para cada string que se utiliza despues del WHERE nombre = ?
            //ps.setString(1, "string1");
            //ps.setString(2, "string2");

            ResultSet rs = ps.executeQuery();

            // iteración a traves de java resultset
            while (rs.next())
            {   
                ensamblarMuebles myMueble = new ensamblarMuebles();

                myMueble.identificadorMueble = rs.getString("IdentificadorMueble");
                myMueble.nombreMueble = rs.getString("NombreMueble");
                myMueble.usuario = rs.getString("Usuario");
                myMueble.fecha = rs.getString("Fecha");
                myMueble.costo = rs.getString("Costo");
                myMueble.precio = rs.getString("Precio");

                arrayEnsamblarMuebles.add(myMueble);

                myMueble = null;
            }

            ps.close();
            
            // Escribe el código html del select de muebles ensamblados
            if (arrayEnsamblarMuebles.size() > 0){
                if (arrayEnsamblarMuebles.size() > 1) {
                    for (int i = 0; i < arrayEnsamblarMuebles.size(); i++) {
                        mueblesDescendente += "<tr><td>" + arrayEnsamblarMuebles.get(i).identificadorMueble + "</td><td>" + arrayEnsamblarMuebles.get(i).nombreMueble + "</td><td>" + arrayEnsamblarMuebles.get(i).usuario + "</td><td>" + arrayEnsamblarMuebles.get(i).fecha + "</td><td>" + arrayEnsamblarMuebles.get(i).costo + "</td><td>" + arrayEnsamblarMuebles.get(i).precio + "</td></tr>";
                    }
                }
            }
            
            arrayEnsamblarMuebles.clear();
        }
        catch (Exception e)
        {
            System.err.println("Hay un error! ");
            System.err.println(e.getMessage());
        }
        
        return mueblesDescendente;
    }
    
}
