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
import java.sql.*;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

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
    
    // Arreglos principales de la muebleria
    ArrayList<cliente> arrayCliente = new ArrayList<>();
    ArrayList<ensamblarMuebles> arrayEnsamblarMuebles = new ArrayList<>();
    ArrayList<ensamblePiezas> arrayEnsamblePiezas = new ArrayList<>();
    ArrayList<mueblesDevueltos> arrayMueblesDevueltos = new ArrayList<>();
    ArrayList<mueblesVendidos> arrayMueblesVendidos = new ArrayList<>();
    ArrayList<pieza> arrayPieza = new ArrayList<>();
    ArrayList<usuario> arrayUsuario = new ArrayList<>();
    
    
    // Para guardar la información de los archivos csv
    ArrayList<String> arrayAdministracion = new ArrayList<>();
    
    public void main (String[] args) {
        System.out.println("Hola mundo");
    }
    
    /**
     * Conexion de mySQL
     */
    
    // Conexion de mySQL
    public void mySQLConnect () {
        try {
            String usuario_url = "";
            String usuario_nombre = "";
            String usuario_password = "";
            
            // To load the mySQLLogin.login file
            Scanner stdIn = new Scanner(System.in);
            Scanner fileIn;
            String line;
            try
            {
                //System.out.print("Introduzca el nombre del archivo: ");
                fileIn = new Scanner(new FileReader("mySQLLogin.login"));
                //while (fileIn.hasNextLine())
                //{
                    //To process each line of file
                    line = fileIn.nextLine();
                    usuario_url = line;
                    line = fileIn.nextLine();
                    usuario_nombre = line;
                    line = fileIn.nextLine();
                    usuario_password = line;
                //}
                
                fileIn.close();
            }
            catch (FileNotFoundException e)
            {
                System.out.println("Error: " + e.getMessage());
            }    
            
            // Creando una conexión a la base de datos de mySQL
            String myDriver = "com.mysql.cj.jdbc.Driver";
            // parece que no es necesario esto "jdbc:mysql://localhost/mysql"
            // es recomendado "jdbc:mysql://localhost:3306"
            Class.forName(myDriver);
            //Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306", "root", "123456789");
            if (usuario_url.length() > 0 && usuario_nombre.length() > 0 && usuario_password.length() > 0) {
                connection = DriverManager.getConnection(usuario_url, usuario_nombre, usuario_password);
                System.out.println("CONEXION ESTABLECIDA!");
            }
            else {
                connection = DriverManager.getConnection("jdbc:mysql://localhost:3306", "root", "123456");
                System.out.println("CONEXION ESTABLECIDA!");
            }

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
    
    /**
     * Archivo de login de mySQL
     * @param usuario_url
     * @param usuario_nombre
     * @param usuario_password
     * @return boolean
     */
    
    public boolean mySQLLoginFile(String usuario_url, String usuario_nombre, String usuario_password) {
        
        PrintWriter fileOut; // conexión al archivo HTML

        try {
            //***********************
            //This is the FileChooser section
            /*
            final JFileChooser SaveAs = new JFileChooser();
            SaveAs.setApproveButtonText("Guardar archivo CSV");
            int actionDialog = SaveAs.showOpenDialog(null);
            if (actionDialog != JFileChooser.APPROVE_OPTION) {
               return;
            }

            File fileName = new File(SaveAs.getSelectedFile() + ".csv");*/
            
            File fileName = new File("mySQLLogin.login");
            
            //************************
            
            //fileOut = new PrintWriter("Reporte_MiMueblerias.csv");
            fileOut = new PrintWriter(fileName);
            
            if (usuario_url.length() > 0 && usuario_nombre.length() > 0 && usuario_password.length() > 0) {
                fileOut.println(usuario_url);
                fileOut.println(usuario_nombre);
                fileOut.println(usuario_password);
            }
            else {
                fileOut.println("jdbc:mysql://localhost:3306");
                fileOut.println("root");
                fileOut.println("123456");
            }

            fileOut.close();
        }
        catch (FileNotFoundException e)
        {
            System.out.println("Error: " + e.getMessage());
        }  
        
        return true;
    }
    
    /**
     * Para buscar la lista de usuarios disponibles
     * @param nombre
     * @param password
     * @param tipo
     * @return boolean
     */
    
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

                myUsuario.setNombre(rs.getString("Nombre"));
                myUsuario.setPassword(rs.getString("Password"));
                myUsuario.setTipo(rs.getString("Tipo"));
                myUsuario.setBloqueado(rs.getString("Bloqueado"));

                arrayUsuario.add(myUsuario);

                myUsuario = null;
            }

            ps.close();
            
            // Busca un usuario y password
            for (int i = 0; i < arrayUsuario.size(); i++) {
                if (arrayUsuario.get(i).getNombre().equals(nombre) && arrayUsuario.get(i).getPassword().equals(password) && arrayUsuario.get(i).getTipo().equals(tipo) && arrayUsuario.get(i).getBloqueado().equals("falso")){
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
    
    /**
     * Para buscar las piezas disponibles
     * @return String
     */
    
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

                myPieza.setTipo(rs.getString("Tipo"));
                myPieza.setCosto(rs.getString("Costo"));
                myPieza.setCantidad(rs.getString("Cantidad"));

                arrayPieza.add(myPieza);

                myPieza = null;
            }

            ps.close();
            
            // Escribe el código html del select de piezas
            if (arrayPieza.size() > 0){
                String temp = arrayPieza.get(0).getTipo();
                piezasSelect = "<option value=\""+ arrayPieza.get(0).getTipo() + "\">" + arrayPieza.get(0).getTipo() + "</option>";
                
                for (int i = 1; i < arrayPieza.size(); i++) {
                    if (!arrayPieza.get(i).getTipo().equals(temp)) {
                        piezasSelect += "<option value=\""+ arrayPieza.get(i).getTipo() + "\">" + arrayPieza.get(i).getTipo() + "</option>";
                        temp = arrayPieza.get(i).getTipo();
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
    
    /**
     * Para buscar los muebles disponibles
     * @return String
     */
    
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

                myMueble.setIdentificadorMueble(rs.getString("IdentificadorMueble"));
                myMueble.setNombreMueble(rs.getString("NombreMueble"));
                myMueble.setUsuario(rs.getString("Usuario"));
                myMueble.setFecha(rs.getString("Fecha"));
                myMueble.setCosto(rs.getString("Costo"));
                myMueble.setPrecio(rs.getString("Precio"));

                arrayEnsamblarMuebles.add(myMueble);

                myMueble = null;
            }

            ps.close();
            
            // Escribe el código html del select de muebles ensamblados
            if (arrayEnsamblarMuebles.size() > 0){
                mueblesSelect = "<option value=\"" + arrayEnsamblarMuebles.get(0).getIdentificadorMueble() + "\">" + arrayEnsamblarMuebles.get(0).getIdentificadorMueble() + " " + arrayEnsamblarMuebles.get(0).getNombreMueble() +  "</option>";
                
                if (arrayEnsamblarMuebles.size() > 1) {
                    for (int i = 1; i < arrayEnsamblarMuebles.size(); i++) {
                        mueblesSelect += "<option value=\"" + arrayEnsamblarMuebles.get(i).getIdentificadorMueble() + "\">" + arrayEnsamblarMuebles.get(i).getIdentificadorMueble() + " " + arrayEnsamblarMuebles.get(i).getNombreMueble() +  "</option>";
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
    
    /**
     * Para buscar los muebles disponibles
     * @return String
     */
    
    // Para buscar los muebles disponibles
    public String mueblesVendidosDisponiblesSelect() {
        String mueblesSelect = "<option value=\"mueble0\">------</option>";
        
        arrayMueblesVendidos.clear();

        try {      
            // Nos conectamos a mySQL
            mySQLConnect();
            
            // crea un java statement
            Statement st = connection.createStatement();

            // To sort by UpDown or Category                      
            PreparedStatement ps = connection.prepareStatement("USE mimuebleria;");

            ps = connection.prepareStatement("SELECT * FROM mimuebleria.mueblesvendidos ORDER BY mimuebleria.mueblesvendidos.NombreMueble ASC");
            // en la sentencia se us ? para cada string que se utiliza despues del WHERE nombre = ?
            //ps.setString(1, "string1");
            //ps.setString(2, "string2");

            ResultSet rs = ps.executeQuery();

            // iteración a traves de java resultset
            while (rs.next())
            {   
                mueblesVendidos myMueble = new mueblesVendidos();

                myMueble.setIdentificadorMueble(rs.getString("IdentificadorMueble"));
                myMueble.setNombreMueble(rs.getString("NombreMueble"));
                myMueble.setUsuario(rs.getString("Usuario"));
                myMueble.setCosto(rs.getString("Costo"));
                myMueble.setPrecio(rs.getString("Precio"));
                myMueble.setCantidad(rs.getString("Cantidad"));
                myMueble.setGanancia(rs.getString("Ganancia"));
                myMueble.setNombreCliente(rs.getString("NombreCliente"));
                myMueble.setNITCliente(rs.getString("NITCliente"));
                myMueble.setFechaVenta(rs.getString("FechaVenta"));

                arrayMueblesVendidos.add(myMueble);

                myMueble = null;
            }

            ps.close();
            
            // Escribe el código html del select de muebles ensamblados
            if (arrayMueblesVendidos.size() > 0){
                mueblesSelect = "<option value=\""+ arrayMueblesVendidos.get(0).getIdentificadorMueble() + "\">" + arrayMueblesVendidos.get(0).getIdentificadorMueble() + " " + arrayMueblesVendidos.get(0).getNombreMueble() +  "</option>";
                
                if (arrayMueblesVendidos.size() > 1) {
                    for (int i = 1; i < arrayMueblesVendidos.size(); i++) {
                        mueblesSelect += "<option value=\""+ arrayMueblesVendidos.get(i).getIdentificadorMueble() + "\">" + arrayMueblesVendidos.get(i).getIdentificadorMueble() + " " + arrayMueblesVendidos.get(i).getNombreMueble() +  "</option>";
                    }
                }
            }
            
            arrayMueblesVendidos.clear();
        }
        catch (Exception e)
        {
            System.err.println("Hay un error! ");
            System.err.println(e.getMessage());
        }
        
        return mueblesSelect;
    }
    
    /**
     * Para buscar los muebles definidos
     * @return String
     */
    
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

                myMueble.setNombreMueble(rs.getString("NombreMueble"));
                myMueble.setPieza(rs.getString("Pieza"));
                myMueble.setCantidad(rs.getString("Cantidad"));

                arrayEnsamblePiezas.add(myMueble);

                myMueble = null;
            }

            ps.close();
            
            // Escribe el código html del select de los muebles
            if (arrayEnsamblePiezas.size() > 0){     
                mueblesSelect = "";
                
                String nombreMueble = arrayEnsamblePiezas.get(0).getNombreMueble();
                mueblesSelect += "<option value=\"" + nombreMueble + "\">" + nombreMueble + "</option>";
                
                // Para agrupar las piezas en una lista con valores únicos
                for (int i = 0; i < arrayEnsamblePiezas.size(); i++) {
                    if (i == arrayEnsamblePiezas.size() - 1) {
                        if (!arrayEnsamblePiezas.get(i).getNombreMueble().equals(nombreMueble)) {
                            nombreMueble = arrayEnsamblePiezas.get(i).getNombreMueble();
                            mueblesSelect += "<option value=\"" + nombreMueble + "\">" + nombreMueble + "</option>";
                        }
                    }
                    else {
                        if (!arrayEnsamblePiezas.get(i).getNombreMueble().equals(nombreMueble)) {
                            nombreMueble = arrayEnsamblePiezas.get(i).getNombreMueble();
                            mueblesSelect += "<option value=\"" + nombreMueble + "\">" + nombreMueble + "</option>";
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
    
    /**
     * Para buscar los usuarios de fábrica disponibles
     * @return String
     */
    
    // Para buscar los usuarios de fábrica disponibles
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

                myUsuario.setNombre(rs.getString("Nombre"));
                myUsuario.setPassword(rs.getString("Password"));
                myUsuario.setTipo(rs.getString("Tipo"));
                myUsuario.setBloqueado(rs.getString("Bloqueado"));

                arrayUsuario.add(myUsuario);

                myUsuario = null;
            }

            ps.close();
            
            // Escribe el código html del select de muebles vendidos
            if (arrayUsuario.size() > 0){
                usuariosFabricaDisponiblesSelect = "";
                
                for (int i = 0; i < arrayUsuario.size(); i++) {
                    if (arrayUsuario.get(i).getBloqueado().equals("falso")) {
                        usuariosFabricaDisponiblesSelect += "<option value=\"" + arrayUsuario.get(i).getNombre() + "\">" + arrayUsuario.get(i).getNombre() + "</option>";
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
        
        return usuariosFabricaDisponiblesSelect;
    }
    
    /**
     * Para buscar los usuarios de ventas disponibles
     * @return String
     */
    
    // Para buscar los usuarios de ventas disponibles
    public String usuariosVentasDisponiblesSelect() {
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
            ps.setString(1, "2");

            ResultSet rs = ps.executeQuery();

            // iteración a traves de java resultset
            while (rs.next())
            {   
                usuario myUsuario = new usuario();

                myUsuario.setNombre(rs.getString("Nombre"));
                myUsuario.setPassword(rs.getString("Password"));
                myUsuario.setTipo(rs.getString("Tipo"));
                myUsuario.setBloqueado(rs.getString("Bloqueado"));

                arrayUsuario.add(myUsuario);

                myUsuario = null;
            }

            ps.close();
            
            // Escribe el código html del select de muebles vendidos
            if (arrayUsuario.size() > 0){
                usuariosFabricaDisponiblesSelect = "";
                
                for (int i = 0; i < arrayUsuario.size(); i++) {
                    if (arrayUsuario.get(i).getBloqueado().equals("falso")) {
                        usuariosFabricaDisponiblesSelect += "<option value=\"" + arrayUsuario.get(i).getNombre() + "\">" + arrayUsuario.get(i).getNombre() + "</option>";
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
        
        return usuariosFabricaDisponiblesSelect;
    }
    
    /**
     * Para buscar los usuarios de administración disponibles
     * @return String
     */
    
    // Para buscar los usuarios de administración disponibles
    public String usuariosAdministracionDisponiblesSelect() {
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
            ps.setString(1, "3");

            ResultSet rs = ps.executeQuery();

            // iteración a traves de java resultset
            while (rs.next())
            {   
                usuario myUsuario = new usuario();

                myUsuario.setNombre(rs.getString("Nombre"));
                myUsuario.setPassword(rs.getString("Password"));
                myUsuario.setTipo(rs.getString("Tipo"));
                myUsuario.setBloqueado(rs.getString("Bloqueado"));

                arrayUsuario.add(myUsuario);

                myUsuario = null;
            }

            ps.close();
            
            // Escribe el código html del select de muebles vendidos
            if (arrayUsuario.size() > 0){
                usuariosFabricaDisponiblesSelect = "";
                
                for (int i = 0; i < arrayUsuario.size(); i++) {
                    if (arrayUsuario.get(i).getBloqueado().equals("falso")) {
                        usuariosFabricaDisponiblesSelect += "<option value=\"" + arrayUsuario.get(i).getNombre() + "\">" + arrayUsuario.get(i).getNombre() + "</option>";
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
        
        return usuariosFabricaDisponiblesSelect;
    }
    
    /**
     * Para buscar los clientes disponibles
     * @return String
     */
    
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

                myCliente.setNITCliente(rs.getString("NITCliente"));
                myCliente.setNombreCliente(rs.getString("NombreCliente"));
                myCliente.setDireccionCliente(rs.getString("DireccionCliente"));
                myCliente.setMunicipio(rs.getString("Municipio"));
                myCliente.setDepartamento(rs.getString("Departamento"));

                arrayCliente.add(myCliente);

                myCliente = null;
            }

            ps.close();
            
            // Escribe el código html del select de muebles ensamblados
            if (arrayCliente.size() > 0){
                clientesSelect = "<option value=\""+ arrayCliente.get(0).getNITCliente() + "\">" + arrayCliente.get(0).getNITCliente() + " " + arrayCliente.get(0).getNombreCliente() +  "</option>";
                
                if (arrayCliente.size() > 1) {
                    for (int i = 1; i < arrayCliente.size(); i++) {
                        clientesSelect += "<option value=\"" + arrayCliente.get(i).getNITCliente() + "\">" + arrayCliente.get(i).getNITCliente() + " " + arrayCliente.get(i).getNombreCliente() +  "</option>";
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
    
    /**
     * Para buscar los clientes de muebles devueltos disponibles
     * @return String
     */
    
    // Para buscar los clientes disponibles
    public String clientesDevolucionDisponiblesSelect() {
        String clientesDevolucionSelect = "<option value=\"cliente0\">------</option>";
        
        arrayMueblesDevueltos.clear();

        try {      
            // Nos conectamos a mySQL
            mySQLConnect();
            
            // crea un java statement
            Statement st = connection.createStatement();

            // To sort by UpDown or Category                      
            PreparedStatement ps = connection.prepareStatement("USE mimuebleria;");

            ps = connection.prepareStatement("SELECT * FROM mimuebleria.mueblesdevueltos ORDER BY mimuebleria.mueblesdevueltos.NITCliente ASC");
            // en la sentencia se us ? para cada string que se utiliza despues del WHERE nombre = ?
            //ps.setString(1, "string1");
            //ps.setString(2, "string2");

            ResultSet rs = ps.executeQuery();

            // iteración a traves de java resultset
            while (rs.next())
            {   
                mueblesDevueltos myClienteDevolucion = new mueblesDevueltos();

                myClienteDevolucion.setIdentificadorMueble(rs.getString("IdentificadorMueble"));
                myClienteDevolucion.setNombreMueble(rs.getString("NombreMueble"));
                myClienteDevolucion.setCantidad(rs.getString("Cantidad"));
                myClienteDevolucion.setNombreCliente(rs.getString("NombreCliente"));
                myClienteDevolucion.setNITCliente(rs.getString("NITCliente"));
                myClienteDevolucion.setFechaDevolucion(rs.getString("FechaDevolucion"));
                myClienteDevolucion.setPerdida(rs.getString("Perdida"));

                arrayMueblesDevueltos.add(myClienteDevolucion);

                myClienteDevolucion = null;
            }

            ps.close();
            
            // Escribe el código html del select de muebles ensamblados
            if (arrayMueblesDevueltos.size() > 0){
                clientesDevolucionSelect = "<option value=\""+ arrayMueblesDevueltos.get(0).getNITCliente() + "\">" + arrayMueblesDevueltos.get(0).getNITCliente() + " " + arrayMueblesDevueltos.get(0).getNombreCliente() +  "</option>";
                
                if (arrayMueblesDevueltos.size() > 1) {
                    for (int i = 1; i < arrayMueblesDevueltos.size(); i++) {
                        clientesDevolucionSelect += "<option value=\"" + arrayMueblesDevueltos.get(i).getNITCliente() + "\">" + arrayMueblesDevueltos.get(i).getNITCliente() + " " + arrayMueblesDevueltos.get(i).getNombreCliente() +  "</option>";
                    }
                }
            }
            
            arrayMueblesDevueltos.clear();
        }
        catch (Exception e)
        {
            System.err.println("Hay un error! ");
            System.err.println(e.getMessage());
        }
        
        return clientesDevolucionSelect;
    }
    
    /**
     * Para buscar los usuarios disponibles
     * @return String
     */
    
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

                myUsuario.setNombre(rs.getString("Nombre"));
                myUsuario.setPassword(rs.getString("Password"));
                myUsuario.setTipo(rs.getString("Tipo"));
                myUsuario.setBloqueado(rs.getString("Bloqueado"));

                arrayUsuario.add(myUsuario);

                myUsuario = null;
            }

            ps.close();
            
            // Escribe el código html del select de muebles ensamblados
            if (arrayUsuario.size() > 0){
                if (arrayUsuario.get(0).getBloqueado().equals("falso")) {
                    usuariosSelect = "<option value=\"" + arrayUsuario.get(0).getNombre() + "\">" + arrayUsuario.get(0).getNombre() + "</option>";
                }
                else {
                    usuariosSelect = "<option value=\"" + arrayUsuario.get(0).getNombre() + "\">" + arrayUsuario.get(0).getNombre() + " bloqueado" + "</option>";
                }
                
                if (arrayUsuario.size() > 1) {
                    for (int i = 1; i < arrayUsuario.size(); i++) {
                        if (arrayUsuario.get(i).getBloqueado().equals("falso")) {
                            usuariosSelect += "<option value=\"" + arrayUsuario.get(i).getNombre() + "\">" + arrayUsuario.get(i).getNombre() +  "</option>";
                        }
                        else {
                            usuariosSelect += "<option value=\"" + arrayUsuario.get(i).getNombre() + "\">" + arrayUsuario.get(i).getNombre() + " bloqueado" + "</option>";
                        }
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
    
    /**
     * Para buscar las piezas que estan por agotarse
     * @return String
     */
    
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

                myPieza.setTipo(rs.getString("Tipo"));
                myPieza.setCosto(rs.getString("Costo"));
                myPieza.setCantidad(rs.getString("Cantidad"));

                arrayPieza.add(myPieza);

                myPieza = null;
            }

            ps.close();
            
            // Escribe el código html del select de piezas
            if (arrayPieza.size() > 0){  
                String temp = arrayPieza.get(0).getTipo();
                int tempInt = Integer.valueOf(arrayPieza.get(0).getCantidad());
                
                if (arrayPieza.size() == 1) {
                        if ((Integer.parseInt(arrayPieza.get(0).getCantidad()) <= 4)) {
                            piezasPorAgotarse += "<tr><td>" + arrayPieza.get(0).getTipo() + "</td><td>" + arrayPieza.get(0).getCosto() + "</td><td>" + arrayPieza.get(0).getCantidad() + "</td></tr>";
                        }
                    }
                else {
                    for (int i = 1; i < arrayPieza.size(); i++) {
                        if (i == arrayPieza.size() - 1)
                        {
                            if (arrayPieza.get(i).getTipo().equals(temp)) {
                                tempInt += Integer.valueOf(arrayPieza.get(i).getCantidad());
                                if (tempInt <= 4) {
                                    piezasPorAgotarse += "<tr><td>" + arrayPieza.get(i).getTipo() + "</td><td>" + arrayPieza.get(i).getCosto() + "</td><td>" + String.valueOf(tempInt) + "</td></tr>";
                                }
                            }
                            else if (!arrayPieza.get(i).getTipo().equals(temp)) {
                                if (Integer.parseInt(arrayPieza.get(i).getCantidad()) <= 4) {
                                    piezasPorAgotarse += "<tr><td>" + arrayPieza.get(i).getTipo() + "</td><td>" + arrayPieza.get(i).getCosto() + "</td><td>" + arrayPieza.get(i).getCantidad() + "</td></tr>";
                                }
                            }                           
                            break;
                        }
                        
                        if (arrayPieza.get(i).getTipo().equals(temp)) {
                            tempInt += Integer.valueOf(arrayPieza.get(i).getCantidad());
                        }
                        else if (!arrayPieza.get(i).getTipo().equals(temp)) {
                            if (tempInt <= 4) {
                                piezasPorAgotarse += "<tr><td>" + arrayPieza.get(i - 1).getTipo() + "</td><td>" + arrayPieza.get(i - 1).getCosto() + "</td><td>" + String.valueOf(tempInt) + "</td></tr>";
                            }
                            temp = arrayPieza.get(i).getTipo();
                            tempInt = Integer.valueOf(arrayPieza.get(i).getCantidad());
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
    
    /**
     * Para crear una pieza nueva
     * @param piezaTipo
     * @param piezaCosto
     * @param piezaCantidad
     * @return String
     */
    
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

                myPieza.setTipo(rs.getString("Tipo"));
                myPieza.setCosto(rs.getString("Costo"));
                myPieza.setCantidad(rs.getString("Cantidad"));

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
    
    /**
     * Para modificar una pieza nueva
     * @param piezaTipo
     * @param piezaCosto
     * @param piezaCantidad
     * @return String
     */
    
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

                myPieza.setTipo(rs.getString("Tipo"));
                myPieza.setCosto(rs.getString("Costo"));
                myPieza.setCantidad(rs.getString("Cantidad"));

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
    
    /**
     * Para borrar una pieza nueva
     * @param piezaTipo
     * @return String
     */
    
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
    
    /**
     * Para definir ensamble de mueble a partir de piezas
     * @param muebleNombre
     * @param mueblePieza
     * @param muebleCantidad
     * @return String
     */
    
    // Para definir ensamble de mueble a partir de piezas
    public String fabricaDefinirEnsamblePiezas(String muebleNombre, String mueblePieza, String muebleCantidad) {
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

                    myEnsamblePiezas.setNombreMueble(rs.getString("NombreMueble"));
                    myEnsamblePiezas.setPieza(rs.getString("Pieza"));
                    myEnsamblePiezas.setCantidad(rs.getString("Cantidad"));

                    arrayEnsamblePiezas.add(myEnsamblePiezas);

                    myEnsamblePiezas = null;
                }
                
                ps.close();
                
                if (arrayEnsamblePiezas.size() > 0) {
                    for (int i = 0; i < arrayEnsamblePiezas.size(); i++) {
                        definirEnsamblePiezas += "<h2>" + arrayEnsamblePiezas.get(i).getNombreMueble() + ", " + arrayEnsamblePiezas.get(i).getPieza() + ", "  + arrayEnsamblePiezas.get(i).getCantidad() + "</h2>";
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
    
    /**
     * Para ensamblar muebles a partir de piezas
     * @param muebleNombre
     * @param muebleUsuario
     * @param muebleFecha
     * @return String
     */
    
    // Para ensamblar muebles a partir de piezas
    public String fabricaEnsamblarMueble(String muebleNombre, String muebleUsuario, String muebleFecha) {
        String ensamblarMueble = "";
        
        arrayEnsamblePiezas.clear();
        arrayPieza.clear();
        arrayEnsamblarMuebles.clear();
        arrayMueblesVendidos.clear();
        arrayMueblesDevueltos.clear();

        try {      
            // Nos conectamos a mySQL
            mySQLConnect();
            
            // crea un java statement
            Statement st = connection.createStatement();
            
            PreparedStatement ps = connection.prepareStatement("USE mimuebleria;");
            
            // Para buscar la definición del mueble
            ps = connection.prepareStatement("SELECT * FROM mimuebleria.ensamblepiezas WHERE NombreMueble = ? ORDER BY mimuebleria.ensamblepiezas.NombreMueble ASC;");
            // en la sentencia se us ? para cada string que se utiliza despues del WHERE nombre = ?
            //ps.setString(1, "string1");
            //ps.setString(2, "string2");
            ps.setString(1, muebleNombre);

            ResultSet rs = ps.executeQuery();

            // iteración a traves de java resultset
            while (rs.next())
            {   
                ensamblePiezas myEnsamblePiezas = new ensamblePiezas();

                myEnsamblePiezas.setNombreMueble(rs.getString("NombreMueble"));
                myEnsamblePiezas.setPieza(rs.getString("Pieza"));
                myEnsamblePiezas.setCantidad(rs.getString("Cantidad"));

                arrayEnsamblePiezas.add(myEnsamblePiezas);

                myEnsamblePiezas = null;
            }

            ps.close();
            
            if (arrayEnsamblePiezas.size() > 0){
                ensamblarMueble += "<h2>Mueble ensamblado</h2>";
                
                double ensamblarMuebleCosto = 0.0;
                double ensamblarMueblePrecio = 0.0;
                int identificadorMueble = 0;
                
                // Para calcular el costo total de las piezas
                for (int i = 0; i < arrayEnsamblePiezas.size(); i++) {
                    
                    // El REPLACE funciona añadiendo porque no hay PRIMARY KEY
                    ps = connection.prepareStatement("SELECT * FROM mimuebleria.pieza WHERE Tipo = ?;");
                    // en la sentencia se us ? para cada string que se utiliza despues del WHERE nombre = ?
                    //ps.setString(1, "string1");
                    //ps.setString(2, "string2");
                    ps.setString(1, arrayEnsamblePiezas.get(i).getPieza());

                    rs = ps.executeQuery();

                    while (rs.next())
                    {   
                        pieza myPieza = new pieza();

                        myPieza.setTipo(rs.getString("Tipo"));
                        myPieza.setCosto(rs.getString("Costo"));
                        myPieza.setCantidad(rs.getString("Cantidad"));

                        arrayPieza.add(myPieza);

                        myPieza = null;
                    }

                    ps.close();
                    
                    // Para calcular el costo
                    if (arrayPieza.size() > 0) {
                        for (int j = 0; j < Integer.parseInt(arrayEnsamblePiezas.get(i).getCantidad()); j++) {
                            ensamblarMuebleCosto += Double.parseDouble(arrayPieza.get(0).getCosto());
                        }
                        
                        // Se borra la pieza del inventario
                        ps = connection.prepareStatement("DELETE FROM mimuebleria.pieza WHERE Tipo = ?");
                        // en la sentencia se us ? para cada string que se utiliza despues del WHERE nombre = ?
                        //ps.setString(1, "string1");
                        //ps.setString(2, "string2");
                        ps.setString(1, arrayEnsamblePiezas.get(i).getPieza());
                        
                        int intUpdate = ps.executeUpdate();
                        
                        ps.close();
                        
                        for (int j = 0; j < arrayPieza.size() - Integer.parseInt(arrayEnsamblePiezas.get(i).getCantidad()); j++) {
                            // Se añade el numero original de piezas menos las utilizadas
                            ps = connection.prepareStatement("REPLACE INTO mimuebleria.pieza VALUES (?,?,?);");
                            // en la sentencia se us ? para cada string que se utiliza despues del WHERE nombre = ?
                            //ps.setString(1, "string1");
                            //ps.setString(2, "string2");
                            ps.setString(1, arrayPieza.get(0).getTipo());
                            ps.setString(2, arrayPieza.get(0).getCosto());
                            ps.setString(3, "1");
                            
                            intUpdate = ps.executeUpdate();
                            
                            ps.close();
                        }
                    }
                }
                
                // Para averiguar el ID de muebles ensamblados
                ps = connection.prepareStatement("SELECT * FROM mimuebleria.ensamblarmuebles ORDER BY mimuebleria.ensamblarmuebles.IdentificadorMueble ASC");
                // en la sentencia se us ? para cada string que se utiliza despues del WHERE nombre = ?
                //ps.setString(1, "string1");
                //ps.setString(2, "string2");

                rs = ps.executeQuery();
                
                while (rs.next())
                {   
                    ensamblarMuebles myMuebleEnsamblado = new ensamblarMuebles();

                    myMuebleEnsamblado.setIdentificadorMueble(rs.getString("IdentificadorMueble"));

                    arrayEnsamblarMuebles.add(myMuebleEnsamblado);

                    myMuebleEnsamblado = null;
                }

                ps.close();
                
                // Para averiguar el ID de mueble vendidos
                ps = connection.prepareStatement("SELECT * FROM mimuebleria.mueblesvendidos ORDER BY mimuebleria.mueblesvendidos.IdentificadorMueble ASC");
                // en la sentencia se us ? para cada string que se utiliza despues del WHERE nombre = ?
                //ps.setString(1, "string1");
                //ps.setString(2, "string2");

                rs = ps.executeQuery();
                
                while (rs.next())
                {   
                    mueblesVendidos myMuebleVendido = new mueblesVendidos();

                    myMuebleVendido.setIdentificadorMueble(rs.getString("IdentificadorMueble"));

                    arrayMueblesVendidos.add(myMuebleVendido);

                    myMuebleVendido = null;
                }

                ps.close();
                
                // Para averiguar el ID de mueble devueltos
                ps = connection.prepareStatement("SELECT * FROM mimuebleria.mueblesdevueltos ORDER BY mimuebleria.mueblesdevueltos.IdentificadorMueble ASC");
                // en la sentencia se us ? para cada string que se utiliza despues del WHERE nombre = ?
                //ps.setString(1, "string1");
                //ps.setString(2, "string2");

                rs = ps.executeQuery();
                
                while (rs.next())
                {   
                    mueblesDevueltos myMuebleDevuelto = new mueblesDevueltos();

                    myMuebleDevuelto.setIdentificadorMueble(rs.getString("IdentificadorMueble"));

                    arrayMueblesDevueltos.add(myMuebleDevuelto);

                    myMuebleDevuelto = null;
                }

                ps.close();
                
                ArrayList<Integer> IDMuebles = new ArrayList<>();
                
                // Para agregar los ID de los muebles ensamblados
                for (int i = 0; i < arrayEnsamblarMuebles.size(); i++) {
                    IDMuebles.add(Integer.parseInt(arrayEnsamblarMuebles.get(i).getIdentificadorMueble()));
                }
                // Para agregar los ID de los muebles vendidos
                for (int i = 0; i < arrayMueblesVendidos.size(); i++) {
                    IDMuebles.add(Integer.parseInt(arrayMueblesVendidos.get(i).getIdentificadorMueble()));
                }
                // Para agregar los ID de los muebles devueltos
                for (int i = 0; i < arrayMueblesDevueltos.size(); i++) {
                    IDMuebles.add(Integer.parseInt(arrayMueblesDevueltos.get(i).getIdentificadorMueble()));
                }
                
                // Para ordenar los ID de los muebles
                Collections.sort(IDMuebles);
                
                int ID = 1;
                for (int i = 0; i < IDMuebles.size(); i++) {
                    if (IDMuebles.size() == 1) {
                        if ((i + 1) != IDMuebles.get(i)) {
                            ID = 1;
                            break;
                        }
                        else {
                            ID = 2;
                            break;
                        }
                    }
                    if (IDMuebles.size() > 1 && i != IDMuebles.size() - 1) {
                        if ((i + 1) != IDMuebles.get(i)) {
                            if ((i + 1) != IDMuebles.get(i - 1)) {
                                ID = IDMuebles.get(i - 1) + 1;
                                break;
                            }
                        }
                    }
                    if (IDMuebles.size() > 1 && i == IDMuebles.size() - 1) {
                        if ((i + 1) != IDMuebles.get(i)) {
                            if ((i + 1) != IDMuebles.get(i - 1)) {
                                ID = IDMuebles.get(i - 1) + 1;
                                break;
                            }
                        }
                        else {
                            ID = IDMuebles.get(i) + 1;
                            break;
                        }
                    }
                }
                
                if (ensamblarMuebleCosto > 0) {
                    ensamblarMueblePrecio = ensamblarMuebleCosto + 50.0;
                }
                else {
                    ensamblarMueblePrecio = 0;
                }
                
                // Para ensamblar el mueble completo
                ps = connection.prepareStatement("REPLACE INTO mimuebleria.ensamblarmuebles VALUES (?,?,?,?,?,?)");
                // en la sentencia se us ? para cada string que se utiliza despues del WHERE nombre = ?
                //ps.setString(1, "string1");
                //ps.setString(2, "string2");
                ps.setString(1, String.valueOf(ID));
                ps.setString(2, muebleNombre);
                ps.setString(3, muebleUsuario);
                ps.setString(4, muebleFecha);
                ps.setString(5, String.valueOf(ensamblarMuebleCosto));
                ps.setString(6, String.valueOf(ensamblarMueblePrecio));
                
                int intUpdate = ps.executeUpdate();
                
                ps.close();
                
                ensamblarMueble += "<h2>ID de mueble: " + String.valueOf(ID) + "</h2>" + 
                        "<h2>Nombre de mueble: " + muebleNombre + "</h2>" + 
                        "<h2>Usuario: " + muebleUsuario + "</h2>" + 
                        "<h2>Fecha de ensamble: " + muebleFecha + "</h2>" + 
                        "<h2>Costo: " + String.valueOf(ensamblarMuebleCosto) + "</h2>" + 
                        "<h2>Precio: " + String.valueOf(ensamblarMueblePrecio) + "</h2>";
            }
            else {
                ensamblarMueble += "<h2>No EXISTE la definición del mueble</h2>";
            }
            
            arrayEnsamblePiezas.clear();
            arrayPieza.clear();
            arrayEnsamblarMuebles.clear();
            arrayMueblesVendidos.clear();
            arrayMueblesDevueltos.clear();
            
        }
        catch (Exception e)
        {
            System.err.println("Hay un error! ");
            System.err.println(e.getMessage());
        }
        
        return ensamblarMueble;
    }
    
    /**
     * Para consultar las piezas de manera ascendente
     * @return String
     */
    
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

                myPieza.setTipo(rs.getString("Tipo"));
                myPieza.setCosto(rs.getString("Costo"));
                myPieza.setCantidad(rs.getString("Cantidad"));

                arrayPieza.add(myPieza);

                myPieza = null;
            }

            ps.close();
            
            // Escribe el código html del select de piezas
            if (arrayPieza.size() > 0){               
                ArrayList<pieza> piezaLista = new ArrayList<>();
                String tipo = arrayPieza.get(0).getTipo();
                String costo = arrayPieza.get(0).getCosto();
                int cantidad = 0;
                
                // Para agrupar las piezas en una lista con valores únicos
                for (int i = 0; i < arrayPieza.size(); i++) {
                    if (i == arrayPieza.size() - 1) {
                        if (arrayPieza.get(i).getTipo().equals(tipo)) {
                            cantidad++;
                            pieza myPieza = new pieza();
                            myPieza.setTipo(tipo);
                            myPieza.setCosto(costo);
                            myPieza.setCantidad(String.valueOf(cantidad));
                            piezaLista.add(myPieza);
                            myPieza = null;
                        }
                        else {
                            pieza myPieza = new pieza();
                            myPieza.setTipo(arrayPieza.get(i).getTipo());
                            myPieza.setCosto(arrayPieza.get(i).getCosto());
                            myPieza.setCantidad(String.valueOf(1));
                            piezaLista.add(myPieza);
                            myPieza = null;
                        }
                    }
                    else {
                        if (arrayPieza.get(i).getTipo().equals(tipo)) {
                            cantidad++;
                        }
                        else {
                            pieza myPieza = new pieza();
                            myPieza.setTipo(tipo);
                            myPieza.setCosto(costo);
                            myPieza.setCantidad(String.valueOf(cantidad));
                            piezaLista.add(myPieza);
                            myPieza = null;
                            tipo = arrayPieza.get(i).getTipo();
                            costo = arrayPieza.get(i).getCosto();
                            cantidad = 1;
                        }
                    }
                }
                
                ArrayList<Integer> listaNumeros = new ArrayList<>();
                
                for (int i = 0; i < piezaLista.size(); i++) {
                    listaNumeros.add(Integer.valueOf(piezaLista.get(i).getCantidad()));
                }
                
                Collections.sort(listaNumeros);
                
                ArrayList<pieza> piezaOrdenado = new ArrayList<>();
                
                // En modo ascendente
                for (int i = 0; i < listaNumeros.size(); i++) {
                    for (int j = 0; j < piezaLista.size(); j++) {
                        if (Integer.valueOf(piezaLista.get(j).getCantidad()).equals(listaNumeros.get(i))) {
                            piezaOrdenado.add(piezaLista.get(j));
                        }
                    }
                }
                
                for (int i = 0; i < piezaOrdenado.size(); i++) {
                    piezasAscendente += "<tr><td>" + piezaOrdenado.get(i).getTipo() + "</td><td>" + piezaOrdenado.get(i).getCosto() + "</td><td>" + piezaOrdenado.get(i).getCantidad() + "</td></tr>";
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
    
    /**
     * Para consultar las piezas de manera ascendente
     * @return String
     */
    
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

                myPieza.setTipo(rs.getString("Tipo"));
                myPieza.setCosto(rs.getString("Costo"));
                myPieza.setCantidad(rs.getString("Cantidad"));

                arrayPieza.add(myPieza);

                myPieza = null;
            }

            ps.close();
            
            // Escribe el código html del select de piezas
            if (arrayPieza.size() > 0){               
                ArrayList<pieza> piezaLista = new ArrayList<>();
                String tipo = arrayPieza.get(0).getTipo();
                String costo = arrayPieza.get(0).getCosto();
                int cantidad = 0;
                
                // Para agrupar las piezas en una lista con valores únicos
                for (int i = 0; i < arrayPieza.size(); i++) {
                    if (i == arrayPieza.size() - 1) {
                        if (arrayPieza.get(i).getTipo().equals(tipo)) {
                            cantidad++;
                            pieza myPieza = new pieza();
                            myPieza.setTipo(tipo);
                            myPieza.setCosto(costo);
                            myPieza.setCantidad(String.valueOf(cantidad));
                            piezaLista.add(myPieza);
                            myPieza = null;
                        }
                        else {
                            pieza myPieza = new pieza();
                            myPieza.setTipo(arrayPieza.get(i).getTipo());
                            myPieza.setCosto(arrayPieza.get(i).getCosto());
                            myPieza.setCantidad(String.valueOf(1));
                            piezaLista.add(myPieza);
                            myPieza = null;
                        }
                    }
                    else {
                        if (arrayPieza.get(i).getTipo().equals(tipo)) {
                            cantidad++;
                        }
                        else {
                            pieza myPieza = new pieza();
                            myPieza.setTipo(tipo);
                            myPieza.setCosto(costo);
                            myPieza.setCantidad(String.valueOf(cantidad));
                            piezaLista.add(myPieza);
                            myPieza = null;
                            tipo = arrayPieza.get(i).getTipo();
                            costo = arrayPieza.get(i).getCosto();
                            cantidad = 1;
                        }
                    }
                }
                
                ArrayList<Integer> listaNumeros = new ArrayList<>();
                
                for (int i = 0; i < piezaLista.size(); i++) {
                    listaNumeros.add(Integer.valueOf(piezaLista.get(i).getCantidad()));
                }
                
                Collections.sort(listaNumeros);
                
                ArrayList<pieza> piezaOrdenado = new ArrayList<>();
                
                // En modo descendente
                for (int i = listaNumeros.size() - 1; i >= 0; i--) {
                    for (int j = 0; j < piezaLista.size(); j++) {
                        if (Integer.valueOf(piezaLista.get(j).getCantidad()).equals(listaNumeros.get(i))) {
                            piezaOrdenado.add(piezaLista.get(j));
                        }
                    }
                }
                
                for (int i = 0; i < piezaOrdenado.size(); i++) {
                    piezasDescendente += "<tr><td>" + piezaOrdenado.get(i).getTipo() + "</td><td>" + piezaOrdenado.get(i).getCosto() + "</td><td>" + piezaOrdenado.get(i).getCantidad() + "</td></tr>";
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
    
    /**
     * Para buscar los muebles de manera ascendente
     * @return String
     */
    
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

                myMueble.setIdentificadorMueble(rs.getString("IdentificadorMueble"));
                myMueble.setNombreMueble(rs.getString("NombreMueble"));
                myMueble.setUsuario(rs.getString("Usuario"));
                myMueble.setFecha(rs.getString("Fecha"));
                myMueble.setCosto(rs.getString("Costo"));
                myMueble.setPrecio(rs.getString("Precio"));

                arrayEnsamblarMuebles.add(myMueble);

                myMueble = null;
            }

            ps.close();
            
            // Escribe el código html del select de muebles ensamblados
            if (arrayEnsamblarMuebles.size() > 0){
                if (arrayEnsamblarMuebles.size() > 1) {
                    for (int i = 0; i < arrayEnsamblarMuebles.size(); i++) {
                        mueblesAscendente += "<tr><td>" + arrayEnsamblarMuebles.get(i).getIdentificadorMueble() + "</td><td>" + arrayEnsamblarMuebles.get(i).getNombreMueble() + "</td><td>" + arrayEnsamblarMuebles.get(i).getUsuario() + "</td><td>" + arrayEnsamblarMuebles.get(i).getFecha() + "</td><td>" + arrayEnsamblarMuebles.get(i).getCosto() + "</td><td>" + arrayEnsamblarMuebles.get(i).getPrecio() + "</td></tr>";
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
    
    /**
     * Para buscar los muebles de manera descendente
     * @return String
     */
    
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

                myMueble.setIdentificadorMueble(rs.getString("IdentificadorMueble"));
                myMueble.setNombreMueble(rs.getString("NombreMueble"));
                myMueble.setUsuario(rs.getString("Usuario"));
                myMueble.setFecha(rs.getString("Fecha"));
                myMueble.setCosto(rs.getString("Costo"));
                myMueble.setPrecio(rs.getString("Precio"));

                arrayEnsamblarMuebles.add(myMueble);

                myMueble = null;
            }

            ps.close();
            
            // Escribe el código html del select de muebles ensamblados
            if (arrayEnsamblarMuebles.size() > 0){
                if (arrayEnsamblarMuebles.size() > 1) {
                    for (int i = 0; i < arrayEnsamblarMuebles.size(); i++) {
                        mueblesDescendente += "<tr><td>" + arrayEnsamblarMuebles.get(i).getIdentificadorMueble() + "</td><td>" + arrayEnsamblarMuebles.get(i).getNombreMueble() + "</td><td>" + arrayEnsamblarMuebles.get(i).getUsuario() + "</td><td>" + arrayEnsamblarMuebles.get(i).getFecha() + "</td><td>" + arrayEnsamblarMuebles.get(i).getCosto() + "</td><td>" + arrayEnsamblarMuebles.get(i).getPrecio() + "</td></tr>";
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
    
    /**
     * Para buscar el NIT de los clientes disponibles
     * @param NITCliente
     * @return String
     */
    
    // Para buscar el NIT de los clientes disponibles
    public String ventasConsultarNITClientes(String NITCliente) {
        String ventasConsultarNITClientes = "<form action=\"ventas_formulario_consultarNITCliente.jsp\" method=\"post\" autocomplete=\"on\">" +
                "<label for=\"fname\">NIT cliente </label>\n" +
                "<input type=\"text\" id=\"ventas_NITCliente\" name=\"ventas_NITCliente\" value=\"" + NITCliente + "\" required>\n" +
                "<br><br>\n" +
                "<input type=\"submit\" value=\"Consultar NIT\">\n" +
                "<br><br>\n" +
                "</form>" +
                "<form action=\"ventas_formulario_crearFactura.jsp\" method=\"post\" autocomplete=\"on\">" +
                "<label for=\"fname\">NIT cliente </label>\n" +
                "<input type=\"text\" id=\"ventas_NITCliente_factura\" name=\"ventas_NITCliente_factura\" value=\"" + NITCliente + "\" required>" +
                "<br><br>" +
                "<label for=\"fname\">Nombre cliente </label>\n" +
                "<input type=\"text\" id=\"ventas_nombrecliente\" name=\"ventas_nombrecliente\" value=\"\" required>\n" +
                "<br><br>\n" +
                "<label for=\"fname\">Dirección cliente </label>\n" +
                "<input type=\"text\" id=\"ventas_direccioncliente\" name=\"ventas_direccioncliente\" value=\"\" required>\n" +
                "<br><br>\n" +
                "<label for=\"fname\">Municipio </label>\n" +
                "<input type=\"text\" id=\"ventas_municipio\" name=\"ventas_municipio\" value=\"\" required>\n" +
                "<br><br>\n" +
                "<label for=\"fname\">Departamento </label>\n" +
                "<input type=\"text\" id=\"ventas_departamento\" name=\"ventas_departamento\" value=\"\" required>\n" +
                "<br><br>";
        
        arrayCliente.clear();

        try {      
            // Nos conectamos a mySQL
            mySQLConnect();
            
            // crea un java statement
            Statement st = connection.createStatement();

            // To sort by UpDown or Category                      
            PreparedStatement ps = connection.prepareStatement("USE mimuebleria;");

            ps = connection.prepareStatement("SELECT * FROM mimuebleria.cliente WHERE NITCliente = ? ORDER BY mimuebleria.cliente.NITCliente ASC");
            // en la sentencia se us ? para cada string que se utiliza despues del WHERE nombre = ?
            //ps.setString(1, "string1");
            //ps.setString(2, "string2");
            ps.setString(1, NITCliente);

            ResultSet rs = ps.executeQuery();

            // iteración a traves de java resultset
            while (rs.next())
            {   
                cliente myCliente = new cliente();

                myCliente.setNITCliente(rs.getString("NITCliente"));
                myCliente.setNombreCliente(rs.getString("NombreCliente"));
                myCliente.setDireccionCliente(rs.getString("DireccionCliente"));
                myCliente.setMunicipio(rs.getString("Municipio"));
                myCliente.setDepartamento(rs.getString("Departamento"));

                arrayCliente.add(myCliente);

                myCliente = null;
            }

            ps.close();
            
            // Escribe el código html del select de muebles ensamblados
            if (arrayCliente.size() > 0){
                ventasConsultarNITClientes = "<form action=\"ventas_formulario_consultarNITCliente.jsp\" method=\"post\" autocomplete=\"on\">" + 
                "<label for=\"fname\">NIT cliente </label>\n" +
                "<input type=\"text\" id=\"ventas_NITCliente\" name=\"ventas_NITCliente\" value=\"" + NITCliente + "\" required>\n" +
                "<br><br>\n" +
                "<input type=\"submit\" value=\"Consultar NIT\">\n" +
                "<br><br>\n" +
                "</form>" +
                "<form action=\"ventas_formulario_crearFactura.jsp\" method=\"post\" autocomplete=\"on\">" +
                "<label for=\"fname\">NIT cliente </label>\n" +
                "<input type=\"text\" id=\"ventas_NITCliente_factura\" name=\"ventas_NITCliente_factura\" value=\"" + arrayCliente.get(0).getNITCliente() + "\" required>" +
                "<br><br>" +
                "<label for=\"fname\">Nombre cliente </label>\n" +
                "<input type=\"text\" id=\"ventas_nombrecliente\" name=\"ventas_nombrecliente\" value=\"" + arrayCliente.get(0).getNombreCliente() + "\" required>\n" +
                "<br><br>\n" +
                "<label for=\"fname\">Dirección cliente </label>\n" +
                "<input type=\"text\" id=\"ventas_direccioncliente\" name=\"ventas_direccioncliente\" value=\"" + arrayCliente.get(0).getDireccionCliente() + "\" required>\n" +
                "<br><br>\n" +
                "<label for=\"fname\">Municipio </label>\n" +
                "<input type=\"text\" id=\"ventas_municipio\" name=\"ventas_municipio\" value=\"" + arrayCliente.get(0).getMunicipio() + "\" required>\n" +
                "<br><br>\n" +
                "<label for=\"fname\">Departamento </label>\n" +
                "<input type=\"text\" id=\"ventas_departamento\" name=\"ventas_departamento\" value=\"" + arrayCliente.get(0).getDepartamento() + "\" required>\n" +
                "<br><br>";
            }
            
            arrayCliente.clear();
        }
        catch (Exception e)
        {
            System.err.println("Hay un error! ");
            System.err.println(e.getMessage());
        }
        
        return ventasConsultarNITClientes;
    }
    
    /**
     * Para creear la factura de la venta
     * @param NITCliente
     * @param nombreCliente
     * @param direccionCliente
     * @param municipio
     * @param departamento
     * @param muebleID
     * @param fechaVenta
     * @return String
     */
    
    // Para creear la factura de la venta
    public String ventasCrearFactura(String NITCliente,String nombreCliente,String direccionCliente,String municipio,String departamento,String muebleID,String fechaVenta) {
        String ventasCrearFactura = "";
        
        arrayCliente.clear();
        arrayEnsamblarMuebles.clear();
        arrayMueblesVendidos.clear();

        try {      
            // Nos conectamos a mySQL
            mySQLConnect();
            
            // crea un java statement
            Statement st = connection.createStatement();

            // To sort by UpDown or Category                      
            PreparedStatement ps = connection.prepareStatement("USE mimuebleria;");
            
            // Primero se busca el mueble a vender
            ps = connection.prepareStatement("SELECT * FROM mimuebleria.ensamblarmuebles WHERE IdentificadorMueble = ? ORDER BY mimuebleria.ensamblarmuebles.IdentificadorMueble ASC");
            // en la sentencia se us ? para cada string que se utiliza despues del WHERE nombre = ?
            //ps.setString(1, "string1");
            //ps.setString(2, "string2");
            ps.setString(1, muebleID);

            ResultSet rs = ps.executeQuery();

            // iteración a traves de java resultset
            while (rs.next())
            {   
                ensamblarMuebles myMueble = new ensamblarMuebles();

                myMueble.setIdentificadorMueble(rs.getString("IdentificadorMueble"));
                myMueble.setNombreMueble(rs.getString("NombreMueble"));
                myMueble.setUsuario(rs.getString("Usuario"));
                myMueble.setFecha(rs.getString("Fecha"));
                myMueble.setCosto(rs.getString("Costo"));
                myMueble.setPrecio(rs.getString("Precio"));

                arrayEnsamblarMuebles.add(myMueble);

                myMueble = null;
            }

            ps.close();
            
            // Borra el mueble de esnamblarmuebles y lo pasa a mueblesVendidos
            ps = connection.prepareStatement("DELETE FROM mimuebleria.ensamblarmuebles WHERE IdentificadorMueble = ?");
            // en la sentencia se us ? para cada string que se utiliza despues del WHERE nombre = ?
            //ps.setString(1, "string1");
            //ps.setString(2, "string2");
            ps.setString(1, muebleID);
            
            int intUpdate = ps.executeUpdate();
            
            // Se lleva el mueble a mueblesvendidos
            double mueblePrecio = Double.parseDouble(arrayEnsamblarMuebles.get(0).getPrecio());
            double muebleCosto = Double.parseDouble(arrayEnsamblarMuebles.get(0).getCosto());
            double muebleGanancia = mueblePrecio - muebleCosto;
            ps = connection.prepareStatement("REPLACE INTO mimuebleria.mueblesvendidos VALUES (?,?,?,?,?,?,?,?,?,?,?)");
            // en la sentencia se us ? para cada string que se utiliza despues del WHERE nombre = ?
            //ps.setString(1, "string1");
            //ps.setString(2, "string2");
            ps.setString(1, muebleID);
            ps.setString(2, arrayEnsamblarMuebles.get(0).getNombreMueble());
            ps.setString(3, arrayEnsamblarMuebles.get(0).getUsuario());
            ps.setString(4, arrayEnsamblarMuebles.get(0).getCosto());
            ps.setString(5, arrayEnsamblarMuebles.get(0).getPrecio());
            ps.setString(6, "1");
            ps.setString(7, String.valueOf(muebleGanancia));
            ps.setString(8, nombreCliente);
            // No se utiliza el apelleido del cliente así que usaremos el mismo nombre
            ps.setString(9, "");
            ps.setString(10, NITCliente);
            ps.setString(11, fechaVenta);
            
            intUpdate = ps.executeUpdate();
            
            // Borra el cliente si este existiera, si el cliente no existe no pasa nada y seguimos con añadir el cliente en el siguiente comando
            ps = connection.prepareStatement("DELETE FROM mimuebleria.cliente WHERE NITCliente = ?");
            // en la sentencia se us ? para cada string que se utiliza despues del WHERE nombre = ?
            //ps.setString(1, "string1");
            //ps.setString(2, "string2");
            ps.setString(1, NITCliente);
            
            intUpdate = ps.executeUpdate();
            
            // Añadimos el cliente a cliente
            ps = connection.prepareStatement("REPLACE INTO mimuebleria.cliente VALUES (?,?,?,?,?)");
            // en la sentencia se us ? para cada string que se utiliza despues del WHERE nombre = ?
            //ps.setString(1, "string1");
            //ps.setString(2, "string2");
            ps.setString(1, NITCliente);
            ps.setString(2, nombreCliente);
            ps.setString(3, direccionCliente);
            ps.setString(4, municipio);
            ps.setString(5, departamento);
            
            intUpdate = ps.executeUpdate();
            
            ps.close();
            
            // Hacemos la facturacion escrita
            
            ventasCrearFactura += "<h2>Identificador mueble = " + muebleID + "</h2>\n" +
                "<h2>Nombre del mueble = " + arrayEnsamblarMuebles.get(0).getNombreMueble() + "</h2>\n" +
                "<h2>Usuario = " + arrayEnsamblarMuebles.get(0).getUsuario() + "</h2>\n" +
                "<h2>Costo = " + muebleCosto + "</h2>\n" +
                "<h2>Precio = " + mueblePrecio + "</h2>\n" +
                "<h2>Cantidad = 1</h2>\n" +
                "<h2>Nombre cliente = " + nombreCliente + "</h2>\n" +
                "<h2>NIT cliente = " + NITCliente + "</h2>\n" +
                "<h2>Fecha de venta = " + fechaVenta + "</h2>";
            
            arrayCliente.clear();
            arrayEnsamblarMuebles.clear();
            arrayMueblesVendidos.clear();
        }
        catch (Exception e)
        {
            System.err.println("Hay un error! ");
            System.err.println(e.getMessage());
        }
        
        return ventasCrearFactura;
    }
    
    /**
     * Para efectuar una devolución
     * @param muebleID
     * @return String
     */
    
    // Para efectuar una devolución
    public String ventasDevoluciones(String muebleID) {
        String ventasDevoluciones = "";
        
        arrayMueblesVendidos.clear();
        arrayMueblesDevueltos.clear();
        arrayEnsamblePiezas.clear();
        arrayPieza.clear();

        try {      
            // Nos conectamos a mySQL
            mySQLConnect();
            
            // crea un java statement
            Statement st = connection.createStatement();

            // To sort by UpDown or Category                      
            PreparedStatement ps = connection.prepareStatement("USE mimuebleria;");
            
            // Primero se busca el mueble que se devuelve
            // Hay que verificar que el mueble se haya vendido en la ultima semana (7 dias) para poder hacer la devolución
            ps = connection.prepareStatement("SELECT * FROM mimuebleria.mueblesvendidos WHERE IdentificadorMueble = ? AND FechaVenta >= DATE_ADD(NOW(),INTERVAL -7 DAY) ORDER BY mimuebleria.mueblesvendidos.IdentificadorMueble ASC");
            // en la sentencia se us ? para cada string que se utiliza despues del WHERE nombre = ?
            //ps.setString(1, "string1");
            //ps.setString(2, "string2");
            ps.setString(1, muebleID);

            ResultSet rs = ps.executeQuery();

            // iteración a traves de java resultset
            while (rs.next())
            {   
                mueblesVendidos myMueble = new mueblesVendidos();

                myMueble.setIdentificadorMueble(rs.getString("IdentificadorMueble"));
                myMueble.setNombreMueble(rs.getString("NombreMueble"));
                myMueble.setUsuario(rs.getString("Usuario"));
                myMueble.setCosto(rs.getString("Costo"));
                myMueble.setPrecio(rs.getString("Precio"));
                myMueble.setCantidad(rs.getString("Cantidad"));
                myMueble.setGanancia(rs.getString("Ganancia"));
                myMueble.setNombreCliente(rs.getString("NombreCliente"));
                myMueble.setNITCliente(rs.getString("NITCliente"));
                myMueble.setFechaVenta(rs.getString("FechaVenta"));

                arrayMueblesVendidos.add(myMueble);

                myMueble = null;
            }

            ps.close();
            
            // Hay que verificar que el mueble se haya vendido en la ultima semana (7 dias) para poder hacer la devolución
            if (arrayMueblesVendidos.size() > 0) {
                // La pérdida es el costo divido 3, un tercio del costo de fabricación
                String perdida = Double.toString(Double.parseDouble(arrayMueblesVendidos.get(0).getCosto()) / 3);
                
                // Para tomar la fecha de hoy
                ps = connection.prepareStatement("SELECT DATE(NOW());");
                // en la sentencia se us ? para cada string que se utiliza despues del WHERE nombre = ?
                //ps.setString(1, "string1");
                //ps.setString(2, "string2");
                
                rs = ps.executeQuery();
                
                String hoy = "";
                
                while (rs.next())
                {
                    hoy = rs.getString(1);
                } 
                
                // Borra el mueble de los mueblesvendidos
                ps = connection.prepareStatement("DELETE FROM mimuebleria.mueblesvendidos WHERE IdentificadorMueble = ?;");
                // en la sentencia se us ? para cada string que se utiliza despues del WHERE nombre = ?
                //ps.setString(1, "string1");
                //ps.setString(2, "string2");
                ps.setString(1, muebleID);

                int intUpdate = ps.executeUpdate();
                
                ps.close();
                
                // Se lleva el mueble a mueblesdevueltos
                ps = connection.prepareStatement("REPLACE INTO mimuebleria.mueblesdevueltos VALUES (?,?,?,?,?,?,?,?);");
                // en la sentencia se us ? para cada string que se utiliza despues del WHERE nombre = ?
                //ps.setString(1, "string1");
                //ps.setString(2, "string2");
                ps.setString(1, muebleID);
                ps.setString(2, arrayMueblesVendidos.get(0).getNombreMueble());
                ps.setString(3, "1");
                ps.setString(4, arrayMueblesVendidos.get(0).getNombreCliente());
                ps.setString(5, "");
                ps.setString(6, arrayMueblesVendidos.get(0).getNITCliente());
                ps.setString(7, hoy);
                ps.setString(8, perdida);
                
                intUpdate = ps.executeUpdate();
                
                ps.close();
                
                // Hay que buscar la definición de ensamble de piezas
                ps = connection.prepareStatement("SELECT * FROM mimuebleria.ensamblepiezas WHERE NombreMueble = ? ORDER BY mimuebleria.ensamblepiezas.Pieza ASC;");
                // en la sentencia se us ? para cada string que se utiliza despues del WHERE nombre = ?
                //ps.setString(1, "string1");
                //ps.setString(2, "string2");
                ps.setString(1, arrayMueblesVendidos.get(0).getNombreMueble());

                rs = ps.executeQuery();

                // iteración a traves de java resultset
                while (rs.next())
                {   
                    ensamblePiezas myEnsamblePiezas = new ensamblePiezas();

                    myEnsamblePiezas.setNombreMueble(rs.getString("NombreMueble"));
                    myEnsamblePiezas.setPieza(rs.getString("Piezas"));
                    myEnsamblePiezas.setCantidad(rs.getString("Cantidad"));

                    arrayEnsamblePiezas.add(myEnsamblePiezas);

                    myEnsamblePiezas = null;
                }
                
                ps.close();
                
                if (arrayEnsamblePiezas.size() > 0) {
                    for (int i = 0; i < arrayEnsamblePiezas.size(); i++) {
                        arrayPieza.clear();
                        
                        ps = connection.prepareStatement("SELECT * FROM mimuebleria.pieza WHERE Tipo = ? ORDER BY mimuebleria.pieza.Tipo ASC;");
                        // en la sentencia se us ? para cada string que se utiliza despues del WHERE nombre = ?
                        //ps.setString(1, "string1");
                        //ps.setString(2, "string2");
                        ps.setString(1, arrayEnsamblePiezas.get(i).getPieza());

                        rs = ps.executeQuery();

                        // iteración a traves de java resultset
                        while (rs.next())
                        {   
                            pieza myPieza = new pieza();

                            myPieza.setTipo(rs.getString("Tipo"));
                            myPieza.setCosto(rs.getString("Costo"));
                            myPieza.setCantidad(rs.getString("Cantidad"));

                            arrayPieza.add(myPieza);

                            myPieza = null;
                        }

                        ps.close();
                        
                        if (arrayPieza.size() > 0) {      
                            for (int j = 0; j < Integer.parseInt(arrayEnsamblePiezas.get(i).getCantidad()); j++) {                         
                                // Hay que insertar las piezas
                                ps = connection.prepareStatement("REPLACE INTO mimuebleria.pieza VALUES (?,?,?);");
                                // en la sentencia se us ? para cada string que se utiliza despues del WHERE nombre = ?
                                //ps.setString(1, "string1");
                                //ps.setString(2, "string2");
                                ps.setString(1, arrayPieza.get(i).getTipo());
                                ps.setString(2, arrayPieza.get(i).getCosto());
                                ps.setString(3, "1");

                                intUpdate = ps.executeUpdate();
                                
                                ps.close();
                                
                                ventasDevoluciones += "<h2>" + arrayPieza.get(i).getTipo() + ", " + arrayPieza.get(i).getCosto() + "</h2>";
                            }
                        }
                        else {
                            for (int j = 0; j < Integer.parseInt(arrayEnsamblePiezas.get(i).getCantidad()); j++) {                         
                                // Hay que insertar las piezas
                                ps = connection.prepareStatement("REPLACE INTO mimuebleria.pieza VALUES (?,?,?);");
                                // en la sentencia se us ? para cada string que se utiliza despues del WHERE nombre = ?
                                //ps.setString(1, "string1");
                                //ps.setString(2, "string2");
                                ps.setString(1, arrayPieza.get(i).getTipo());
                                ps.setString(2, "30.0");
                                ps.setString(3, "1");

                                intUpdate = ps.executeUpdate();
                                
                                ps.close();
                                
                                ventasDevoluciones += "<h2>" + arrayPieza.get(i).getTipo() + ", 30.0</h2>";
                            }
                        }
                    }
                }
                
                ventasDevoluciones += "<h2>El mueble ha sido DEVUELTO</h2>" + 
                        "<h2>Identificador del mueble: " + muebleID + "</h2>" + 
                        "<h2>Nombre del mueble: " + arrayMueblesVendidos.get(0).getNombreMueble() + "</h2>" +
                        "<h2>Nombre del cliente: " + arrayMueblesVendidos.get(0).getNombreCliente() + "</h2>" +
                        "<h2>NIT del cliente: " + arrayMueblesVendidos.get(0).getNITCliente() + "</h2>" + 
                        "<h2>Fecha: " + hoy + "</h2>" + 
                        "<h2>La pérdida es de Q. " + perdida + "</h2>";
            }
            else {
                ventasDevoluciones += "<h2>El mueble NO puede ser devuelto, ha pasado más de una semana desde la compra o no existe la compra</h2>";
            }
            
            arrayMueblesVendidos.clear();
            arrayMueblesDevueltos.clear();
            arrayEnsamblePiezas.clear();
            arrayPieza.clear();
        }
        catch (Exception e)
        {
            System.err.println("Hay un error! ");
            System.err.println(e.getMessage());
        }
        
        return ventasDevoluciones;
    }
    
    /**
     * Para consultar las compras de un cliente
     * @param NITCliente
     * @param fechaInicial
     * @param fechaFinal
     * @return String
     */
    
    // Para consultar las compras de un cliente
    public String ventasConsultaDeComprasCliente(String NITCliente,String fechaInicial, String fechaFinal) {
        String ventasConsultaDeComprasCliente = "";
        
        arrayMueblesVendidos.clear();

        try {      
            // Nos conectamos a mySQL
            mySQLConnect();
            
            // crea un java statement
            Statement st = connection.createStatement();

            // To sort by UpDown or Category                      
            PreparedStatement ps = connection.prepareStatement("USE mimuebleria;");
            
            if (fechaInicial.equals("") && fechaFinal.equals("")){
                // Se busca el intervalo requerido
                ps = connection.prepareStatement("SELECT * FROM mimuebleria.mueblesvendidos WHERE NITCliente = ? ORDER BY mimuebleria.mueblesvendidos.FechaVenta ASC");
                // en la sentencia se us ? para cada string que se utiliza despues del WHERE nombre = ?
                //ps.setString(1, "string1");
                //ps.setString(2, "string2");
                ps.setString(1, NITCliente);
                
                ResultSet rs = ps.executeQuery();
            }
            else if (fechaInicial.equals("") && !fechaFinal.equals("")){
                // Se busca el intervalo requerido
                ps = connection.prepareStatement("SELECT * FROM mimuebleria.mueblesvendidos WHERE NITCliente = ? AND FechaVenta <= ? ORDER BY mimuebleria.mueblesvendidos.FechaVenta ASC");
                // en la sentencia se us ? para cada string que se utiliza despues del WHERE nombre = ?
                //ps.setString(1, "string1");
                //ps.setString(2, "string2");
                ps.setString(1, NITCliente);
                ps.setDate(2, java.sql.Date.valueOf(fechaFinal));
                
                ResultSet rs = ps.executeQuery();
            }
            else if (!fechaInicial.equals("") && fechaFinal.equals("")){
                // Se busca el intervalo requerido
                ps = connection.prepareStatement("SELECT * FROM mimuebleria.mueblesvendidos WHERE NITCliente = ? AND FechaVenta >= ? ORDER BY mimuebleria.mueblesvendidos.FechaVenta ASC");
                // en la sentencia se us ? para cada string que se utiliza despues del WHERE nombre = ?
                //ps.setString(1, "string1");
                //ps.setString(2, "string2");
                ps.setString(1, NITCliente);
                ps.setDate(2, java.sql.Date.valueOf(fechaInicial));
                
                ResultSet rs = ps.executeQuery();
            }
            else if (!fechaInicial.equals("") && !fechaFinal.equals("")){
                // Se busca el intervalo requerido
                ps = connection.prepareStatement("SELECT * FROM mimuebleria.mueblesvendidos WHERE NITCliente = ? AND FechaVenta >= ? AND FechaVenta <= ? ORDER BY mimuebleria.mueblesvendidos.FechaVenta ASC");
                // en la sentencia se us ? para cada string que se utiliza despues del WHERE nombre = ?
                //ps.setString(1, "string1");
                //ps.setString(2, "string2");
                ps.setString(1, NITCliente);
                ps.setDate(2, java.sql.Date.valueOf(fechaInicial));
                ps.setDate(3, java.sql.Date.valueOf(fechaFinal));
                
                ResultSet rs = ps.executeQuery();
            }
            
            ResultSet rs = ps.executeQuery();
                
            // iteración a traves de java resultset
            while (rs.next())
            {   
                mueblesVendidos myMueble = new mueblesVendidos();

                myMueble.setIdentificadorMueble(rs.getString("IdentificadorMueble"));
                myMueble.setNombreMueble(rs.getString("NombreMueble"));
                myMueble.setUsuario(rs.getString("Usuario"));
                myMueble.setCosto(rs.getString("Costo"));
                myMueble.setPrecio(rs.getString("Precio"));
                myMueble.setCantidad(rs.getString("Cantidad"));
                myMueble.setGanancia(rs.getString("Ganancia"));
                myMueble.setNombreCliente(rs.getString("NombreCliente"));
                myMueble.setNITCliente(rs.getString("NITCliente"));
                myMueble.setFechaVenta(rs.getString("FechaVenta"));

                arrayMueblesVendidos.add(myMueble);

                myMueble = null;
            }
            
            ps.close();
            
            if (arrayMueblesVendidos.size() > 0) {
                for (int i = 0; i < arrayMueblesVendidos.size(); i++) {
                    ventasConsultaDeComprasCliente += "<tr>\n" +
                        "<td>" + arrayMueblesVendidos.get(i).getIdentificadorMueble() + "</td>\n" +
                        "<td>" + arrayMueblesVendidos.get(i).getNombreMueble() + "</td>\n" +
                        "<td>" + arrayMueblesVendidos.get(i).getUsuario() + "</td>\n" +
                        "<td>" + arrayMueblesVendidos.get(i).getCosto() + "</td>\n" +
                        "<td>" + arrayMueblesVendidos.get(i).getPrecio() + "</td>\n" +
                        "<td>" + arrayMueblesVendidos.get(i).getCantidad() + "</td>\n" +
                        "<td>" + arrayMueblesVendidos.get(i).getGanancia() + "</td>\n" +
                        "<td>" + arrayMueblesVendidos.get(i).getNombreCliente() + "</td>\n" +
                        "<td>" + arrayMueblesVendidos.get(i).getNITCliente() + "</td>\n" +
                        "<td>" + arrayMueblesVendidos.get(i).getFechaVenta() + "</td>\n" +
                        "</tr>";
                }
            }
            
            arrayMueblesVendidos.clear();
        }
        catch (Exception e)
        {
            System.err.println("Hay un error! ");
            System.err.println(e.getMessage());
        }
        
        return ventasConsultaDeComprasCliente;
    }
    
    /**
     * Para consultar las compras de un cliente
     * @param fechaDelDia
     * @return String
     */
    
    // Para consultar las compras de un cliente
    public String ventasConsultaVentasDelDia(String fechaDelDia) {
        String ventasConsultaVentasDelDia = "";
        
        arrayMueblesVendidos.clear();

        try {      
            // Nos conectamos a mySQL
            mySQLConnect();
            
            // crea un java statement
            Statement st = connection.createStatement();

            // To sort by UpDown or Category                      
            PreparedStatement ps = connection.prepareStatement("USE mimuebleria;");
            
            // Se busca el intervalo requerido
            ps = connection.prepareStatement("SELECT * FROM mimuebleria.mueblesvendidos WHERE FechaVenta = ? ORDER BY mimuebleria.mueblesvendidos.FechaVenta ASC");
            // en la sentencia se us ? para cada string que se utiliza despues del WHERE nombre = ?
            //ps.setString(1, "string1");
            //ps.setString(2, "string2");
            ps.setString(1, fechaDelDia);

            ResultSet rs = ps.executeQuery();
                
            // iteración a traves de java resultset
            while (rs.next())
            {   
                mueblesVendidos myMueble = new mueblesVendidos();

                myMueble.setIdentificadorMueble(rs.getString("IdentificadorMueble"));
                myMueble.setNombreMueble(rs.getString("NombreMueble"));
                myMueble.setUsuario(rs.getString("Usuario"));
                myMueble.setCosto(rs.getString("Costo"));
                myMueble.setPrecio(rs.getString("Precio"));
                myMueble.setCantidad(rs.getString("Cantidad"));
                myMueble.setGanancia(rs.getString("Ganancia"));
                myMueble.setNombreCliente(rs.getString("NombreCliente"));
                myMueble.setNITCliente(rs.getString("NITCliente"));
                myMueble.setFechaVenta(rs.getString("FechaVenta"));

                arrayMueblesVendidos.add(myMueble);

                myMueble = null;
            }
            
            ps.close();
            
            if (arrayMueblesVendidos.size() > 0) {
                for (int i = 0; i < arrayMueblesVendidos.size(); i++) {
                    ventasConsultaVentasDelDia += "<tr>\n" +
                        "<td>" + arrayMueblesVendidos.get(i).getIdentificadorMueble() + "</td>\n" +
                        "<td>" + arrayMueblesVendidos.get(i).getNombreMueble() + "</td>\n" +
                        "<td>" + arrayMueblesVendidos.get(i).getUsuario() + "</td>\n" +
                        "<td>" + arrayMueblesVendidos.get(i).getCosto() + "</td>\n" +
                        "<td>" + arrayMueblesVendidos.get(i).getPrecio() + "</td>\n" +
                        "<td>" + arrayMueblesVendidos.get(i).getCantidad() + "</td>\n" +
                        "<td>" + arrayMueblesVendidos.get(i).getGanancia() + "</td>\n" +
                        "<td>" + arrayMueblesVendidos.get(i).getNombreCliente() + "</td>\n" +
                        "<td>" + arrayMueblesVendidos.get(i).getNITCliente() + "</td>\n" +
                        "<td>" + arrayMueblesVendidos.get(i).getFechaVenta() + "</td>\n" +
                        "</tr>";
                }
            }
            
            arrayMueblesVendidos.clear();
        }
        catch (Exception e)
        {
            System.err.println("Hay un error! ");
            System.err.println(e.getMessage());
        }
        
        return ventasConsultaVentasDelDia;
    }
    
    /**
     * Para consultar las devoluciones de un cliente
     * @param NITCliente
     * @param fechaInicial
     * @param fechaFinal
     * @return String
     */
    
    // Para consultar las devoluciones de un cliente
    public String ventasConsultaDevolucionesCliente(String NITCliente,String fechaInicial, String fechaFinal) {
        String ventasConsultaDevolucionesCliente = "";
        
        arrayMueblesDevueltos.clear();

        try {      
            // Nos conectamos a mySQL
            mySQLConnect();
            
            // crea un java statement
            Statement st = connection.createStatement();

            // To sort by UpDown or Category                      
            PreparedStatement ps = connection.prepareStatement("USE mimuebleria;");
            
            if (fechaInicial.equals("") && fechaFinal.equals("")){
                // Se busca el intervalo requerido
                ps = connection.prepareStatement("SELECT * FROM mimuebleria.mueblesdevueltos WHERE NITCliente = ? ORDER BY mimuebleria.mueblesdevueltos.FechaDevolucion ASC");
                // en la sentencia se us ? para cada string que se utiliza despues del WHERE nombre = ?
                //ps.setString(1, "string1");
                //ps.setString(2, "string2");
                ps.setString(1, NITCliente);
                
                ResultSet rs = ps.executeQuery();
            }
            else if (fechaInicial.equals("") && !fechaFinal.equals("")){
                // Se busca el intervalo requerido
                ps = connection.prepareStatement("SELECT * FROM mimuebleria.mueblesdevueltos WHERE NITCliente = ? AND FechaDevolucion <= ? ORDER BY mimuebleria.mueblesdevueltos.FechaDevolucion ASC");
                // en la sentencia se us ? para cada string que se utiliza despues del WHERE nombre = ?
                //ps.setString(1, "string1");
                //ps.setString(2, "string2");
                ps.setString(1, NITCliente);
                ps.setDate(2, java.sql.Date.valueOf(fechaFinal));
                
                ResultSet rs = ps.executeQuery();
            }
            else if (!fechaInicial.equals("") && fechaFinal.equals("")){
                // Se busca el intervalo requerido
                ps = connection.prepareStatement("SELECT * FROM mimuebleria.mueblesdevueltos WHERE NITCliente = ? AND FechaDevolucion >= ? ORDER BY mimuebleria.mueblesdevueltos.FechaDevolucion ASC");
                // en la sentencia se us ? para cada string que se utiliza despues del WHERE nombre = ?
                //ps.setString(1, "string1");
                //ps.setString(2, "string2");
                ps.setString(1, NITCliente);
                ps.setDate(2, java.sql.Date.valueOf(fechaInicial));
                
                ResultSet rs = ps.executeQuery();
            }
            else if (!fechaInicial.equals("") && !fechaFinal.equals("")){
                // Se busca el intervalo requerido
                ps = connection.prepareStatement("SELECT * FROM mimuebleria.mueblesdevueltos WHERE NITCliente = ? AND FechaDevolucion >= ? AND FechaDevolucion <= ? ORDER BY mimuebleria.mueblesdevueltos.FechaDevolucion ASC");
                // en la sentencia se us ? para cada string que se utiliza despues del WHERE nombre = ?
                //ps.setString(1, "string1");
                //ps.setString(2, "string2");
                ps.setString(1, NITCliente);
                ps.setDate(2, java.sql.Date.valueOf(fechaInicial));
                ps.setDate(3, java.sql.Date.valueOf(fechaFinal));
                
                ResultSet rs = ps.executeQuery();
            }
            
            ResultSet rs = ps.executeQuery();
                
            // iteración a traves de java resultset
            while (rs.next())
            {   
                mueblesDevueltos myMueble = new mueblesDevueltos();

                myMueble.setIdentificadorMueble(rs.getString("IdentificadorMueble"));
                myMueble.setNombreMueble(rs.getString("NombreMueble"));
                myMueble.setCantidad(rs.getString("Cantidad"));
                myMueble.setNombreCliente(rs.getString("NombreCliente"));
                myMueble.setNITCliente(rs.getString("NITCliente"));
                myMueble.setFechaDevolucion(rs.getString("FechaDevolucion"));
                myMueble.setPerdida(rs.getString("Perdida"));

                arrayMueblesDevueltos.add(myMueble);

                myMueble = null;
            }
            
            ps.close();
            
            if (arrayMueblesDevueltos.size() > 0) {
                for (int i = 0; i < arrayMueblesDevueltos.size(); i++) {
                    ventasConsultaDevolucionesCliente += "<tr>\n" +
                        "<td>" + arrayMueblesDevueltos.get(i).getIdentificadorMueble() + "</td>\n" +
                        "<td>" + arrayMueblesDevueltos.get(i).getNombreMueble() + "</td>\n" +
                        "<td>" + arrayMueblesDevueltos.get(i).getCantidad() + "</td>\n" +
                        "<td>" + arrayMueblesDevueltos.get(i).getNombreCliente() + "</td>\n" +
                        "<td>" + arrayMueblesDevueltos.get(i).getNITCliente() + "</td>\n" +
                        "<td>" + arrayMueblesDevueltos.get(i).getFechaDevolucion() + "</td>\n" +
                        "<td>" + arrayMueblesDevueltos.get(i).getPerdida() + "</td>\n" +
                        "</tr>";
                }
            }
            
            arrayMueblesDevueltos.clear();
        }
        catch (Exception e)
        {
            System.err.println("Hay un error! ");
            System.err.println(e.getMessage());
        }
        
        return ventasConsultaDevolucionesCliente;
    }
    
    /**
     * Para consultar los muebles disponibles en la sala de ventas
     * @return String
     */
    
    // Para consultar los muebles disponibles en la sala de ventas
    public String ventasConsultaMueblesDisponibles() {
        String ventasConsultaMueblesDisponibles = "";
        
        arrayEnsamblarMuebles.clear();

        try {      
            // Nos conectamos a mySQL
            mySQLConnect();
            
            // crea un java statement
            Statement st = connection.createStatement();

            // To sort by UpDown or Category                      
            PreparedStatement ps = connection.prepareStatement("USE mimuebleria;");
            
            // Se busca el intervalo requerido
            ps = connection.prepareStatement("SELECT * FROM mimuebleria.ensamblarmuebles ORDER BY mimuebleria.ensamblarmuebles.NombreMueble ASC");
            // en la sentencia se us ? para cada string que se utiliza despues del WHERE nombre = ?
            //ps.setString(1, "string1");
            //ps.setString(2, "string2");

            ResultSet rs = ps.executeQuery();
                
            // iteración a traves de java resultset
            while (rs.next())
            {   
                ensamblarMuebles myMueble = new ensamblarMuebles();

                myMueble.setIdentificadorMueble(rs.getString("IdentificadorMueble"));
                myMueble.setNombreMueble(rs.getString("NombreMueble"));
                myMueble.setUsuario(rs.getString("Usuario"));
                myMueble.setFecha(rs.getString("Fecha"));
                myMueble.setCosto(rs.getString("Costo"));
                myMueble.setPrecio(rs.getString("Precio"));

                arrayEnsamblarMuebles.add(myMueble);

                myMueble = null;
            }
            
            ps.close();
            
            if (arrayEnsamblarMuebles.size() > 0) {
                for (int i = 0; i < arrayEnsamblarMuebles.size(); i++) {
                    ventasConsultaMueblesDisponibles += "<tr>\n" +
                        "<td>" + arrayEnsamblarMuebles.get(i).getIdentificadorMueble() + "</td>\n" +
                        "<td>" + arrayEnsamblarMuebles.get(i).getNombreMueble() + "</td>\n" +
                        "<td>" + arrayEnsamblarMuebles.get(i).getUsuario() + "</td>\n" +
                        "<td>" + arrayEnsamblarMuebles.get(i).getFecha() + "</td>\n" +
                        "<td>" + arrayEnsamblarMuebles.get(i).getCosto() + "</td>\n" +
                        "<td>" + arrayEnsamblarMuebles.get(i).getPrecio() + "</td>\n" +
                        "</tr>";
                }
            }
            
            arrayEnsamblarMuebles.clear();
        }
        catch (Exception e)
        {
            System.err.println("Hay un error! ");
            System.err.println(e.getMessage());
        }
        
        return ventasConsultaMueblesDisponibles;
    }
    
    /**
     * Para consultar las ventas en un intervalo de tiempo
     * @param fechaInicial
     * @param fechaFinal
     * @param exportarCSV
     * @return String
     */
    
    // Para consultar las ventas en un intervalo de tiempo
    public String administracionConsultaVentas(String fechaInicial, String fechaFinal, boolean exportarCSV) {
        String administracionConsultaVentas = "";
        
        arrayMueblesVendidos.clear();
        
        arrayAdministracion.clear();

        try {      
            // Nos conectamos a mySQL
            mySQLConnect();
            
            // crea un java statement
            Statement st = connection.createStatement();

            // To sort by UpDown or Category                      
            PreparedStatement ps = connection.prepareStatement("USE mimuebleria;");
            
            if (fechaInicial.equals("") && fechaFinal.equals("")){
                // Se busca el intervalo requerido
                ps = connection.prepareStatement("SELECT * FROM mimuebleria.mueblesvendidos ORDER BY mimuebleria.mueblesvendidos.FechaVenta ASC");
                // en la sentencia se us ? para cada string que se utiliza despues del WHERE nombre = ?
                //ps.setString(1, "string1");
                //ps.setString(2, "string2");
                
                ResultSet rs = ps.executeQuery();
            }
            else if (fechaInicial.equals("") && !fechaFinal.equals("")){
                // Se busca el intervalo requerido
                ps = connection.prepareStatement("SELECT * FROM mimuebleria.mueblesvendidos WHERE FechaVenta <= ? ORDER BY mimuebleria.mueblesvendidos.FechaVenta ASC");
                // en la sentencia se us ? para cada string que se utiliza despues del WHERE nombre = ?
                //ps.setString(1, "string1");
                //ps.setString(2, "string2");
                ps.setDate(1, java.sql.Date.valueOf(fechaFinal));
                
                ResultSet rs = ps.executeQuery();
            }
            else if (!fechaInicial.equals("") && fechaFinal.equals("")){
                // Se busca el intervalo requerido
                ps = connection.prepareStatement("SELECT * FROM mimuebleria.mueblesvendidos WHERE FechaVenta >= ? ORDER BY mimuebleria.mueblesvendidos.FechaVenta ASC");
                // en la sentencia se us ? para cada string que se utiliza despues del WHERE nombre = ?
                //ps.setString(1, "string1");
                //ps.setString(2, "string2");
                //ps.setString(1, fechaInicial);
                ps.setDate(1, java.sql.Date.valueOf(fechaInicial));
                
                ResultSet rs = ps.executeQuery();
            }
            else if (!fechaInicial.equals("") && !fechaFinal.equals("")){
                // Se busca el intervalo requerido
                ps = connection.prepareStatement("SELECT * FROM mimuebleria.mueblesvendidos WHERE FechaVenta >= ? AND FechaVenta <= ? ORDER BY mimuebleria.mueblesvendidos.FechaVenta ASC");
                // en la sentencia se us ? para cada string que se utiliza despues del WHERE nombre = ?
                //ps.setString(1, "string1");
                //ps.setString(2, "string2");
                ps.setDate(1, java.sql.Date.valueOf(fechaInicial));
                ps.setDate(2, java.sql.Date.valueOf(fechaFinal));
                
                ResultSet rs = ps.executeQuery();
            }
            
            ResultSet rs = ps.executeQuery();
                
            // iteración a traves de java resultset
            while (rs.next())
            {   
                mueblesVendidos myMueble = new mueblesVendidos();

                myMueble.setIdentificadorMueble(rs.getString("IdentificadorMueble"));
                myMueble.setNombreMueble(rs.getString("NombreMueble"));
                myMueble.setUsuario(rs.getString("Usuario"));
                myMueble.setCosto(rs.getString("Costo"));
                myMueble.setPrecio(rs.getString("Precio"));
                myMueble.setCantidad(rs.getString("Cantidad"));
                myMueble.setGanancia(rs.getString("Ganancia"));
                myMueble.setNombreCliente(rs.getString("NombreCliente"));
                myMueble.setNITCliente(rs.getString("NITCliente"));
                myMueble.setFechaVenta(rs.getString("FechaVenta"));

                arrayMueblesVendidos.add(myMueble);

                myMueble = null;
            }
            
            ps.close();
            
            if (arrayMueblesVendidos.size() > 0) {
                for (int i = 0; i < arrayMueblesVendidos.size(); i++) {
                    administracionConsultaVentas += "<tr>\n" +
                        "<td>" + arrayMueblesVendidos.get(i).getIdentificadorMueble() + "</td>\n" +
                        "<td>" + arrayMueblesVendidos.get(i).getNombreMueble() + "</td>\n" +
                        "<td>" + arrayMueblesVendidos.get(i).getUsuario() + "</td>\n" +
                        "<td>" + arrayMueblesVendidos.get(i).getCosto() + "</td>\n" +
                        "<td>" + arrayMueblesVendidos.get(i).getPrecio() + "</td>\n" +
                        "<td>" + arrayMueblesVendidos.get(i).getCantidad() + "</td>\n" +
                        "<td>" + arrayMueblesVendidos.get(i).getGanancia() + "</td>\n" +
                        "<td>" + arrayMueblesVendidos.get(i).getNombreCliente() + "</td>\n" +
                        "<td>" + arrayMueblesVendidos.get(i).getNITCliente() + "</td>\n" +
                        "<td>" + arrayMueblesVendidos.get(i).getFechaVenta() + "</td>\n" +
                        "</tr>";
                }
            }
            
            // Para exportar el archivo 
            if (exportarCSV) {
                String linea = "";

                linea += "ID de Mueble,Nombre de Mueble,Usuario,Costo,Precio,Cantidad,Ganancia,Nombre de Cliente,NIT de Cliente,Fecha de Venta";
                arrayAdministracion.add(linea);

                for (int i = 0; i < arrayMueblesVendidos.size(); i++) {
                    linea = "";
                    linea += arrayMueblesVendidos.get(i).getIdentificadorMueble() + ",";
                    linea += arrayMueblesVendidos.get(i).getNombreMueble() + ",";
                    linea += arrayMueblesVendidos.get(i).getUsuario() + ",";
                    linea += arrayMueblesVendidos.get(i).getCosto() + ",";
                    linea += arrayMueblesVendidos.get(i).getPrecio() + ",";
                    linea += arrayMueblesVendidos.get(i).getCantidad() + ",";
                    linea += arrayMueblesVendidos.get(i).getGanancia() + ",";
                    linea += arrayMueblesVendidos.get(i).getNombreCliente() + ",";
                    linea += arrayMueblesVendidos.get(i).getNITCliente() + ",";
                    linea += arrayMueblesVendidos.get(i).getFechaVenta() + ",";

                    arrayAdministracion.add(linea);
                }

                guardarArchivoCSV();
            }
            
            arrayMueblesVendidos.clear();
            
            arrayAdministracion.clear();
        }
        catch (Exception e)
        {
            System.err.println("Hay un error! ");
            System.err.println(e.getMessage());
        }
        
        return administracionConsultaVentas;
    }
    
    /**
     * Para consultar las devoluciones en un intervalo de tiempo
     * @param fechaInicial
     * @param fechaFinal
     * @param exportarCSV
     * @return String
     */
    
    // Para consultar las devoluciones en un intervalo de tiempo
    public String administracionConsultaDevoluciones(String fechaInicial, String fechaFinal, boolean exportarCSV) {
        String administracionConsultaDevoluciones = "";
        
        arrayMueblesDevueltos.clear();
        
        arrayAdministracion.clear();

        try {      
            // Nos conectamos a mySQL
            mySQLConnect();
            
            // crea un java statement
            Statement st = connection.createStatement();

            // To sort by UpDown or Category                      
            PreparedStatement ps = connection.prepareStatement("USE mimuebleria;");
            
            if (fechaInicial.equals("") && fechaFinal.equals("")){
                // Se busca el intervalo requerido
                ps = connection.prepareStatement("SELECT * FROM mimuebleria.mueblesdevueltos ORDER BY mimuebleria.mueblesdevueltos.FechaDevolucion ASC");
                // en la sentencia se us ? para cada string que se utiliza despues del WHERE nombre = ?
                //ps.setString(1, "string1");
                //ps.setString(2, "string2");
                
                ResultSet rs = ps.executeQuery();
            }
            else if (fechaInicial.equals("") && !fechaFinal.equals("")){
                // Se busca el intervalo requerido
                ps = connection.prepareStatement("SELECT * FROM mimuebleria.mueblesdevueltos WHERE FechaDevolucion <= ? ORDER BY mimuebleria.mueblesdevueltos.FechaDevolucion ASC");
                // en la sentencia se us ? para cada string que se utiliza despues del WHERE nombre = ?
                //ps.setString(1, "string1");
                //ps.setString(2, "string2");
                ps.setDate(1, java.sql.Date.valueOf(fechaFinal));
                
                ResultSet rs = ps.executeQuery();
            }
            else if (!fechaInicial.equals("") && fechaFinal.equals("")){
                // Se busca el intervalo requerido
                ps = connection.prepareStatement("SELECT * FROM mimuebleria.mueblesdevueltos WHERE FechaDevolucion >= ? ORDER BY mimuebleria.mueblesdevueltos.FechaDevolucion ASC");
                // en la sentencia se us ? para cada string que se utiliza despues del WHERE nombre = ?
                //ps.setString(1, "string1");
                //ps.setString(2, "string2");
                ps.setDate(1, java.sql.Date.valueOf(fechaInicial));
                
                ResultSet rs = ps.executeQuery();
            }
            else if (!fechaInicial.equals("") && !fechaFinal.equals("")){
                // Se busca el intervalo requerido
                ps = connection.prepareStatement("SELECT * FROM mimuebleria.mueblesdevueltos WHERE FechaDevolucion >= ? AND FechaDevolucion <= ? ORDER BY mimuebleria.mueblesdevueltos.FechaDevolucion ASC");
                // en la sentencia se us ? para cada string que se utiliza despues del WHERE nombre = ?
                //ps.setString(1, "string1");
                //ps.setString(2, "string2");
                ps.setDate(1, java.sql.Date.valueOf(fechaInicial));
                ps.setDate(2, java.sql.Date.valueOf(fechaFinal));
                
                ResultSet rs = ps.executeQuery();
            }
            
            ResultSet rs = ps.executeQuery();
                
            // iteración a traves de java resultset
            while (rs.next())
            {   
                mueblesDevueltos myMueble = new mueblesDevueltos();

                myMueble.setIdentificadorMueble(rs.getString("IdentificadorMueble"));
                myMueble.setNombreMueble(rs.getString("NombreMueble"));
                myMueble.setCantidad(rs.getString("Cantidad"));
                myMueble.setNombreCliente(rs.getString("NombreCliente"));
                myMueble.setNITCliente(rs.getString("NITCliente"));
                myMueble.setFechaDevolucion(rs.getString("FechaDevolucion"));
                myMueble.setPerdida(rs.getString("Perdida"));

                arrayMueblesDevueltos.add(myMueble);

                myMueble = null;
            }
            
            ps.close();
            
            if (arrayMueblesDevueltos.size() > 0) {
                for (int i = 0; i < arrayMueblesDevueltos.size(); i++) {
                    administracionConsultaDevoluciones += "<tr>\n" +
                        "<td>" + arrayMueblesDevueltos.get(i).getIdentificadorMueble() + "</td>\n" +
                        "<td>" + arrayMueblesDevueltos.get(i).getNombreMueble() + "</td>\n" +
                        "<td>" + arrayMueblesDevueltos.get(i).getCantidad() + "</td>\n" +
                        "<td>" + arrayMueblesDevueltos.get(i).getNombreCliente() + "</td>\n" +
                        "<td>" + arrayMueblesDevueltos.get(i).getNITCliente() + "</td>\n" +
                        "<td>" + arrayMueblesDevueltos.get(i).getFechaDevolucion() + "</td>\n" +
                        "<td>" + arrayMueblesDevueltos.get(i).getPerdida() + "</td>\n" +
                        "</tr>";
                }
            }
            
            // Para exportar el archivo CSV
            if (exportarCSV) {
                String linea = "";
                
                linea += "ID de Mueble,Nombre de Mueble,Cantidad,Nombre de Cliente,NIT de Cliente,Fecha de Devolucion,Perdida";
                arrayAdministracion.add(linea);
                
                for (int i = 0; i < arrayMueblesDevueltos.size(); i++) {
                    linea = "";
                    linea += arrayMueblesDevueltos.get(i).getIdentificadorMueble() + ",";
                    linea += arrayMueblesDevueltos.get(i).getNombreMueble() + ",";
                    linea += arrayMueblesDevueltos.get(i).getCantidad() + ",";
                    linea += arrayMueblesDevueltos.get(i).getNombreCliente() + ",";
                    linea += arrayMueblesDevueltos.get(i).getNITCliente() + ",";
                    linea += arrayMueblesDevueltos.get(i).getFechaDevolucion() + ",";
                    linea += arrayMueblesDevueltos.get(i).getPerdida() + ",";
                    
                    arrayAdministracion.add(linea);
                }
                
                guardarArchivoCSV();
            }
            
            arrayMueblesDevueltos.clear();
            
            arrayAdministracion.clear();
        }
        catch (Exception e)
        {
            System.err.println("Hay un error! ");
            System.err.println(e.getMessage());
        }
        
        return administracionConsultaDevoluciones;
    }
    
    /**
     * Para consultar las ganancias en un intervalo de tiempo
     * @param fechaInicial
     * @param fechaFinal
     * @param exportarCSV
     * @return String
     */
    
    // Para consultar las ganancias en un intervalo de tiempo
    public String administracionConsultaGanancias(String fechaInicial, String fechaFinal, boolean exportarCSV) {
        String administracionConsultaGanancias = "";
        
        arrayMueblesVendidos.clear();
        arrayMueblesDevueltos.clear();
        
        arrayAdministracion.clear();
        
        double ganancia = 0.0;
        double perdida = 0.0;

        try {      
            // Nos conectamos a mySQL
            mySQLConnect();
            
            // crea un java statement
            Statement st = connection.createStatement();

            // To sort by UpDown or Category                      
            PreparedStatement ps = connection.prepareStatement("USE mimuebleria;");
            
            ////////////////////////////////////
            // Para obtener los muebles vendidos
            if (fechaInicial.equals("") && fechaFinal.equals("")){
                // Se busca el intervalo requerido
                ps = connection.prepareStatement("SELECT * FROM mimuebleria.mueblesvendidos ORDER BY mimuebleria.mueblesvendidos.FechaVenta ASC");
                // en la sentencia se us ? para cada string que se utiliza despues del WHERE nombre = ?
                //ps.setString(1, "string1");
                //ps.setString(2, "string2");
                
                ResultSet rs = ps.executeQuery();
            }
            else if (fechaInicial.equals("") && !fechaFinal.equals("")){
                // Se busca el intervalo requerido
                ps = connection.prepareStatement("SELECT * FROM mimuebleria.mueblesvendidos WHERE FechaVenta <= ? ORDER BY mimuebleria.mueblesvendidos.FechaVenta ASC");
                // en la sentencia se us ? para cada string que se utiliza despues del WHERE nombre = ?
                //ps.setString(1, "string1");
                //ps.setString(2, "string2");
                ps.setDate(1, java.sql.Date.valueOf(fechaFinal));
                
                ResultSet rs = ps.executeQuery();
            }
            else if (!fechaInicial.equals("") && fechaFinal.equals("")){
                // Se busca el intervalo requerido
                ps = connection.prepareStatement("SELECT * FROM mimuebleria.mueblesvendidos WHERE FechaVenta >= ? ORDER BY mimuebleria.mueblesvendidos.FechaVenta ASC");
                // en la sentencia se us ? para cada string que se utiliza despues del WHERE nombre = ?
                //ps.setString(1, "string1");
                //ps.setString(2, "string2");
                //ps.setString(1, fechaInicial);
                ps.setDate(1, java.sql.Date.valueOf(fechaInicial));
                
                ResultSet rs = ps.executeQuery();
            }
            else if (!fechaInicial.equals("") && !fechaFinal.equals("")){
                // Se busca el intervalo requerido
                ps = connection.prepareStatement("SELECT * FROM mimuebleria.mueblesvendidos WHERE FechaVenta >= ? AND FechaVenta <= ? ORDER BY mimuebleria.mueblesvendidos.FechaVenta ASC");
                // en la sentencia se us ? para cada string que se utiliza despues del WHERE nombre = ?
                //ps.setString(1, "string1");
                //ps.setString(2, "string2");
                ps.setDate(1, java.sql.Date.valueOf(fechaInicial));
                ps.setDate(2, java.sql.Date.valueOf(fechaFinal));
                
                ResultSet rs = ps.executeQuery();
            }
            
            ResultSet rs = ps.executeQuery();
                
            // iteración a traves de java resultset
            while (rs.next())
            {   
                mueblesVendidos myMueble = new mueblesVendidos();

                myMueble.setIdentificadorMueble(rs.getString("IdentificadorMueble"));
                myMueble.setNombreMueble(rs.getString("NombreMueble"));
                myMueble.setUsuario(rs.getString("Usuario"));
                myMueble.setCosto(rs.getString("Costo"));
                myMueble.setPrecio(rs.getString("Precio"));
                myMueble.setCantidad(rs.getString("Cantidad"));
                myMueble.setGanancia(rs.getString("Ganancia"));
                myMueble.setNombreCliente(rs.getString("NombreCliente"));
                myMueble.setNITCliente(rs.getString("NITCliente"));
                myMueble.setFechaVenta(rs.getString("FechaVenta"));

                arrayMueblesVendidos.add(myMueble);

                myMueble = null;
            }
            
            ps.close();
            
            ////////////////////////////////////
            // Para obtener los muebles devueltos
            if (fechaInicial.equals("") && fechaFinal.equals("")){
                // Se busca el intervalo requerido
                ps = connection.prepareStatement("SELECT * FROM mimuebleria.mueblesdevueltos ORDER BY mimuebleria.mueblesdevueltos.FechaDevolucion ASC");
                // en la sentencia se us ? para cada string que se utiliza despues del WHERE nombre = ?
                //ps.setString(1, "string1");
                //ps.setString(2, "string2");
                
                rs = ps.executeQuery();
            }
            else if (fechaInicial.equals("") && (!fechaFinal.equals(""))){
                // Se busca el intervalo requerido
                ps = connection.prepareStatement("SELECT * FROM mimuebleria.mueblesdevueltos WHERE FechaDevolucion <= ? ORDER BY mimuebleria.mueblesdevueltos.FechaDevolucion ASC");
                // en la sentencia se us ? para cada string que se utiliza despues del WHERE nombre = ?
                //ps.setString(1, "string1");
                //ps.setString(2, "string2");
                ps.setDate(1, java.sql.Date.valueOf(fechaFinal));
                
                rs = ps.executeQuery();
            }
            else if ((!fechaInicial.equals("")) && fechaFinal.equals("")){
                // Se busca el intervalo requerido
                ps = connection.prepareStatement("SELECT * FROM mimuebleria.mueblesdevueltos WHERE FechaDevolucion >= ? ORDER BY mimuebleria.mueblesdevueltos.FechaDevolucion ASC");
                // en la sentencia se us ? para cada string que se utiliza despues del WHERE nombre = ?
                //ps.setString(1, "string1");
                //ps.setString(2, "string2");
                ps.setDate(1, java.sql.Date.valueOf(fechaInicial));
                
                rs = ps.executeQuery();
            }
            else if ((!fechaInicial.equals("")) && (!fechaFinal.equals(""))){
                // Se busca el intervalo requerido
                ps = connection.prepareStatement("SELECT * FROM mimuebleria.mueblesdevueltos WHERE FechaDevolucion >= ? AND FechaDevolucion <= ? ORDER BY mimuebleria.mueblesdevueltos.FechaDevolucion ASC");
                // en la sentencia se us ? para cada string que se utiliza despues del WHERE nombre = ?
                //ps.setString(1, "string1");
                //ps.setString(2, "string2");
                ps.setDate(1, java.sql.Date.valueOf(fechaInicial));
                ps.setDate(2, java.sql.Date.valueOf(fechaFinal));
                
                rs = ps.executeQuery();
            }
            
            rs = ps.executeQuery();
                
            // iteración a traves de java resultset
            while (rs.next())
            {   
                mueblesDevueltos myMueble = new mueblesDevueltos();

                myMueble.setIdentificadorMueble(rs.getString("IdentificadorMueble"));
                myMueble.setNombreMueble(rs.getString("NombreMueble"));
                myMueble.setCantidad(rs.getString("Cantidad"));
                myMueble.setNombreCliente(rs.getString("NombreCliente"));
                myMueble.setNITCliente(rs.getString("NITCliente"));
                myMueble.setFechaDevolucion(rs.getString("FechaDevolucion"));
                myMueble.setPerdida(rs.getString("Perdida"));

                arrayMueblesDevueltos.add(myMueble);

                myMueble = null;
            }
            
            ps.close();
            
            ///////////////////////////////
            
            if (arrayMueblesVendidos.size() > 0) {
                for (int i = 0; i < arrayMueblesVendidos.size(); i++) {
                    administracionConsultaGanancias += "<tr>\n" +
                        "<td>" + arrayMueblesVendidos.get(i).getIdentificadorMueble() + "</td>\n" +
                        "<td>" + arrayMueblesVendidos.get(i).getNombreMueble() + "</td>\n" +
                        "<td>" + arrayMueblesVendidos.get(i).getUsuario() + "</td>\n" +
                        "<td>" + arrayMueblesVendidos.get(i).getCosto() + "</td>\n" +
                        "<td>" + arrayMueblesVendidos.get(i).getPrecio() + "</td>\n" +
                        "<td>" + arrayMueblesVendidos.get(i).getCantidad() + "</td>\n" +
                        "<td>" + arrayMueblesVendidos.get(i).getGanancia() + "</td>\n" +
                        "<td>" + arrayMueblesVendidos.get(i).getNombreCliente() + "</td>\n" +
                        "<td>" + arrayMueblesVendidos.get(i).getNITCliente() + "</td>\n" +
                        "<td>" + arrayMueblesVendidos.get(i).getFechaVenta() + "</td>\n" +
                        "</tr>";
                }
                
                // Para calcular el total de la perdida
                perdida = 0.0;
                for (int i = 0; i < arrayMueblesDevueltos.size(); i++) {
                    perdida += Double.parseDouble(arrayMueblesDevueltos.get(i).getPerdida());
                }
                
                // Para calcular el total de la ganancia
                ganancia = 0.0;
                for (int i = 0; i < arrayMueblesVendidos.size(); i++) {
                    ganancia += Double.parseDouble(arrayMueblesVendidos.get(i).getGanancia());
                }
                
                administracionConsultaGanancias += "<h2>La ganancia total es de Q. " + String.valueOf(ganancia) + " - Q. " + String.valueOf(perdida) + " = Q. " + String.valueOf(ganancia - perdida) + "</h2>";
            }
            
            // Para exportar el archivo CSV
            if (exportarCSV) {
                String linea = "";
                linea += "ID de Mueble,Nombre de Mueble,Usuario,Costo,Precio,Cantidad,Ganancia,Nombre de Cliente,NIT de Cliente,Fecha de Venta";
                arrayAdministracion.add(linea);
                
                for (int i = 0; i < arrayMueblesVendidos.size(); i++) {
                    linea = "";
                    linea += arrayMueblesVendidos.get(i).getIdentificadorMueble() + ",";
                    linea += arrayMueblesVendidos.get(i).getNombreMueble() + ",";
                    linea += arrayMueblesVendidos.get(i).getUsuario() + ",";
                    linea += arrayMueblesVendidos.get(i).getCosto() + ",";
                    linea += arrayMueblesVendidos.get(i).getPrecio() + ",";
                    linea += arrayMueblesVendidos.get(i).getCantidad() + ",";
                    linea += arrayMueblesVendidos.get(i).getGanancia() + ",";
                    linea += arrayMueblesVendidos.get(i).getNombreCliente() + ",";
                    linea += arrayMueblesVendidos.get(i).getNITCliente() + ",";
                    linea += arrayMueblesVendidos.get(i).getFechaVenta() + ",";
                    
                    arrayAdministracion.add(linea);
                }
                
                linea = "";
                linea += "Ganancia,Perdida,Total";
                arrayAdministracion.add(linea);
                linea = "";
                linea += String.valueOf(ganancia) + "," + String.valueOf(perdida) + "," + String.valueOf(ganancia - perdida);
                arrayAdministracion.add(linea);
                
                guardarArchivoCSV();
            }
            
            arrayMueblesVendidos.clear();
            arrayMueblesDevueltos.clear();
            
            arrayAdministracion.clear();
        }
        catch (Exception e)
        {
            System.err.println("Hay un error! ");
            System.err.println(e.getMessage());
        }
        
        return administracionConsultaGanancias;
    }

    /**
     * Para consultar el usuario con más ventas en un intervalo de tiempo
     * @param fechaInicial
     * @param fechaFinal
     * @param exportarCSV
     * @return String
     */
    
    // Para consultar el usuario con más ventas en un intervalo de tiempo
    public String administracionConsultaUsuarioMasVentas(String fechaInicial, String fechaFinal, boolean exportarCSV) {
        String administracionConsultaUsuarioMasVentas = "";
        
        arrayMueblesVendidos.clear();
        
        arrayAdministracion.clear();

        try {      
            // Nos conectamos a mySQL
            mySQLConnect();
            
            // crea un java statement
            Statement st = connection.createStatement();

            // To sort by UpDown or Category                      
            PreparedStatement ps = connection.prepareStatement("USE mimuebleria;");
            
            if (fechaInicial.equals("") && fechaFinal.equals("")){
                // Se busca el intervalo requerido
                ps = connection.prepareStatement("SELECT * FROM mimuebleria.mueblesvendidos ORDER BY mimuebleria.mueblesvendidos.Usuario ASC");
                // en la sentencia se us ? para cada string que se utiliza despues del WHERE nombre = ?
                //ps.setString(1, "string1");
                //ps.setString(2, "string2");
                
                ResultSet rs = ps.executeQuery();
            }
            else if (fechaInicial.equals("") && !fechaFinal.equals("")){
                // Se busca el intervalo requerido
                ps = connection.prepareStatement("SELECT * FROM mimuebleria.mueblesvendidos WHERE FechaVenta <= ? ORDER BY mimuebleria.mueblesvendidos.Usuario ASC");
                // en la sentencia se us ? para cada string que se utiliza despues del WHERE nombre = ?
                //ps.setString(1, "string1");
                //ps.setString(2, "string2");
                ps.setDate(1, java.sql.Date.valueOf(fechaFinal));
                
                ResultSet rs = ps.executeQuery();
            }
            else if (!fechaInicial.equals("") && fechaFinal.equals("")){
                // Se busca el intervalo requerido
                ps = connection.prepareStatement("SELECT * FROM mimuebleria.mueblesvendidos WHERE FechaVenta >= ? ORDER BY mimuebleria.mueblesvendidos.Usuario ASC");
                // en la sentencia se us ? para cada string que se utiliza despues del WHERE nombre = ?
                //ps.setString(1, "string1");
                //ps.setString(2, "string2");
                ps.setDate(1, java.sql.Date.valueOf(fechaInicial));
                
                ResultSet rs = ps.executeQuery();
            }
            else if (!fechaInicial.equals("") && !fechaFinal.equals("")){
                // Se busca el intervalo requerido
                ps = connection.prepareStatement("SELECT * FROM mimuebleria.mueblesvendidos WHERE FechaVenta >= ? AND FechaVenta <= ? ORDER BY mimuebleria.mueblesvendidos.Usuario ASC");
                // en la sentencia se us ? para cada string que se utiliza despues del WHERE nombre = ?
                //ps.setString(1, "string1");
                //ps.setString(2, "string2");
                ps.setDate(1, java.sql.Date.valueOf(fechaInicial));
                ps.setDate(2, java.sql.Date.valueOf(fechaFinal));
                
                ResultSet rs = ps.executeQuery();
            }
            
            ResultSet rs = ps.executeQuery();
                
            // iteración a traves de java resultset
            while (rs.next())
            {   
                mueblesVendidos myMueble = new mueblesVendidos();

                myMueble.setIdentificadorMueble(rs.getString("IdentificadorMueble"));
                myMueble.setNombreMueble(rs.getString("NombreMueble"));
                myMueble.setUsuario(rs.getString("Usuario"));
                myMueble.setCosto(rs.getString("Costo"));
                myMueble.setPrecio(rs.getString("Precio"));
                myMueble.setCantidad(rs.getString("Cantidad"));
                myMueble.setGanancia(rs.getString("Ganancia"));
                myMueble.setNombreCliente(rs.getString("NombreCliente"));
                myMueble.setNITCliente(rs.getString("NITCliente"));
                myMueble.setFechaVenta(rs.getString("FechaVenta"));

                arrayMueblesVendidos.add(myMueble);

                myMueble = null;
            }
            
            ps.close();
            
            // Definicion de arrays de usuarios
            ArrayList<String> usuarios = new ArrayList<>();
            ArrayList<Integer> usuariosCuenta = new ArrayList<>();
            ArrayList<Integer> usuariosMayores = new ArrayList<>();
            
            if (arrayMueblesVendidos.size() > 0) {

                String usuario = arrayMueblesVendidos.get(0).getUsuario();
                int tempInt = 0;
                
                // Para llenar el array de usuarios y usuariosCuenta
                for (int i = 0; i < arrayMueblesVendidos.size(); i++) {
                    if (i == arrayMueblesVendidos.size() - 1) {
                        if (arrayMueblesVendidos.get(i).getUsuario().equals(usuario)) {
                            tempInt++;
                            usuarios.add(usuario);
                            usuariosCuenta.add(tempInt);
                        }
                        else {
                            usuarios.add(usuario);
                            usuariosCuenta.add(tempInt);
                            usuario = arrayMueblesVendidos.get(i).getUsuario();
                            tempInt = 1;
                            usuarios.add(usuario);
                            usuariosCuenta.add(tempInt);
                        }
                    }
                    else {  
                        if (arrayMueblesVendidos.get(i).getUsuario().equals(usuario)) {
                            tempInt++;
                        }
                        else {
                            usuarios.add(usuario);
                            usuariosCuenta.add(tempInt);
                            usuario = arrayMueblesVendidos.get(i).getUsuario();
                            tempInt = 1;
                        }
                    }
                }
                
                // Para seleccionar los usuarios mayores en un ArrayList usuariosMayores
                int cuentaTemp = 0;
                usuariosMayores.add(0);
                for (int i = 0; i < usuariosCuenta.size(); i++) {
                    if (usuariosCuenta.get(i) == cuentaTemp) {
                        usuariosMayores.add(i);
                    }
                    else if (usuariosCuenta.get(i) > cuentaTemp) {
                        usuariosMayores.clear();
                        usuariosMayores.add(i);
                        cuentaTemp = usuariosCuenta.get(i);
                    }
                }         

                for (int i = 0; i < arrayMueblesVendidos.size(); i++) {
                    for (int j = 0; j < usuariosMayores.size(); j++) {
                        int temp = usuariosMayores.get(j);
                        if  (usuarios.get(temp).equals(arrayMueblesVendidos.get(i).getUsuario())) {
                            administracionConsultaUsuarioMasVentas += "<tr>\n" +
                                "<td>" + arrayMueblesVendidos.get(i).getIdentificadorMueble() + "</td>\n" +
                                "<td>" + arrayMueblesVendidos.get(i).getNombreMueble() + "</td>\n" +
                                "<td>" + arrayMueblesVendidos.get(i).getUsuario() + "</td>\n" +
                                "<td>" + arrayMueblesVendidos.get(i).getCosto() + "</td>\n" +
                                "<td>" + arrayMueblesVendidos.get(i).getPrecio() + "</td>\n" +
                                "<td>" + arrayMueblesVendidos.get(i).getCantidad() + "</td>\n" +
                                "<td>" + arrayMueblesVendidos.get(i).getGanancia() + "</td>\n" +
                                "<td>" + arrayMueblesVendidos.get(i).getNombreCliente() + "</td>\n" +
                                "<td>" + arrayMueblesVendidos.get(i).getNITCliente() + "</td>\n" +
                                "<td>" + arrayMueblesVendidos.get(i).getFechaVenta() + "</td>\n" +
                                "</tr>";
                        }
                    }
                }
            }
            
            // Para exportar el archivo CSV
            if (exportarCSV) {
                String linea = "";
                
                linea += "ID de Mueble,Nombre de Mueble,Usuario,Costo,Precio,Cantidad,Ganancia,Nombre de Cliente,NIT de Cliente,Fecha de Venta";
                arrayAdministracion.add(linea);
                
                for (int i = 0; i < arrayMueblesVendidos.size(); i++) {
                    for (int j = 0; j < usuariosMayores.size(); j++) {
                        int temp = usuariosMayores.get(j);
                        if  (usuarios.get(temp).equals(arrayMueblesVendidos.get(i).getUsuario())) {
                            linea = "";
                            linea += arrayMueblesVendidos.get(i).getIdentificadorMueble() + ",";
                            linea += arrayMueblesVendidos.get(i).getNombreMueble() + ",";
                            linea += arrayMueblesVendidos.get(i).getUsuario() + ",";
                            linea += arrayMueblesVendidos.get(i).getCosto() + ",";
                            linea += arrayMueblesVendidos.get(i).getPrecio() + ",";
                            linea += arrayMueblesVendidos.get(i).getCantidad() + ",";
                            linea += arrayMueblesVendidos.get(i).getGanancia() + ",";
                            linea += arrayMueblesVendidos.get(i).getNombreCliente() + ",";
                            linea += arrayMueblesVendidos.get(i).getNITCliente() + ",";
                            linea += arrayMueblesVendidos.get(i).getFechaVenta() + ",";

                            arrayAdministracion.add(linea);
                        }
                    }
                }
                
                guardarArchivoCSV();
            }
            
            arrayMueblesVendidos.clear();
            
            arrayAdministracion.clear();
        }
        catch (Exception e)
        {
            System.err.println("Hay un error! ");
            System.err.println(e.getMessage());
        }
        
        return administracionConsultaUsuarioMasVentas;
    }
    
    /**
     * Para consultar el usuario con más ganancias en un intervalo de tiempo
     * @param fechaInicial
     * @param fechaFinal
     * @param exportarCSV
     * @return String
     */
    
    // Para consultar el usuario con más ganancias en un intervalo de tiempo
    public String administracionConsultaUsuarioMasGanancias(String fechaInicial, String fechaFinal, boolean exportarCSV) {
        String administracionConsultaUsuarioMasGanancias = "";
        
        arrayMueblesVendidos.clear();
        
        arrayAdministracion.clear();

        try {      
            // Nos conectamos a mySQL
            mySQLConnect();
            
            // crea un java statement
            Statement st = connection.createStatement();

            // To sort by UpDown or Category                      
            PreparedStatement ps = connection.prepareStatement("USE mimuebleria;");
            
            if (fechaInicial.equals("") && fechaFinal.equals("")){
                // Se busca el intervalo requerido
                ps = connection.prepareStatement("SELECT * FROM mimuebleria.mueblesvendidos ORDER BY mimuebleria.mueblesvendidos.Usuario ASC");
                // en la sentencia se us ? para cada string que se utiliza despues del WHERE nombre = ?
                //ps.setString(1, "string1");
                //ps.setString(2, "string2");
                
                ResultSet rs = ps.executeQuery();
            }
            else if (fechaInicial.equals("") && !fechaFinal.equals("")){
                // Se busca el intervalo requerido
                ps = connection.prepareStatement("SELECT * FROM mimuebleria.mueblesvendidos WHERE FechaVenta <= ? ORDER BY mimuebleria.mueblesvendidos.Usuario ASC");
                // en la sentencia se us ? para cada string que se utiliza despues del WHERE nombre = ?
                //ps.setString(1, "string1");
                //ps.setString(2, "string2");
                ps.setDate(1, java.sql.Date.valueOf(fechaFinal));
                
                ResultSet rs = ps.executeQuery();
            }
            else if (!fechaInicial.equals("") && fechaFinal.equals("")){
                // Se busca el intervalo requerido
                ps = connection.prepareStatement("SELECT * FROM mimuebleria.mueblesvendidos WHERE FechaVenta >= ? ORDER BY mimuebleria.mueblesvendidos.Usuario ASC");
                // en la sentencia se us ? para cada string que se utiliza despues del WHERE nombre = ?
                //ps.setString(1, "string1");
                //ps.setString(2, "string2");
                ps.setDate(1, java.sql.Date.valueOf(fechaInicial));
                
                ResultSet rs = ps.executeQuery();
            }
            else if (!fechaInicial.equals("") && !fechaFinal.equals("")){
                // Se busca el intervalo requerido
                ps = connection.prepareStatement("SELECT * FROM mimuebleria.mueblesvendidos WHERE FechaVenta >= ? AND FechaVenta <= ? ORDER BY mimuebleria.mueblesvendidos.Usuario ASC");
                // en la sentencia se us ? para cada string que se utiliza despues del WHERE nombre = ?
                //ps.setString(1, "string1");
                //ps.setString(2, "string2");
                ps.setDate(1, java.sql.Date.valueOf(fechaInicial));
                ps.setDate(2, java.sql.Date.valueOf(fechaFinal));
                
                ResultSet rs = ps.executeQuery();
            }
            
            ResultSet rs = ps.executeQuery();
                
            // iteración a traves de java resultset
            while (rs.next())
            {   
                mueblesVendidos myMueble = new mueblesVendidos();

                myMueble.setIdentificadorMueble(rs.getString("IdentificadorMueble"));
                myMueble.setNombreMueble(rs.getString("NombreMueble"));
                myMueble.setUsuario(rs.getString("Usuario"));
                myMueble.setCosto(rs.getString("Costo"));
                myMueble.setPrecio(rs.getString("Precio"));
                myMueble.setCantidad(rs.getString("Cantidad"));
                myMueble.setGanancia(rs.getString("Ganancia"));
                myMueble.setNombreCliente(rs.getString("NombreCliente"));
                myMueble.setNITCliente(rs.getString("NITCliente"));
                myMueble.setFechaVenta(rs.getString("FechaVenta"));

                arrayMueblesVendidos.add(myMueble);

                myMueble = null;
            }
            
            ps.close();
            
            // Definicion de array de usuarios
            ArrayList<String> usuarios = new ArrayList<>();
            ArrayList<Double> usuariosCuenta = new ArrayList<>();
            ArrayList<Integer> usuariosMayores = new ArrayList<>();
            
            if (arrayMueblesVendidos.size() > 0) {
                
                String usuario = arrayMueblesVendidos.get(0).getUsuario();
                double tempDouble = 0.0;
                
                // Para llenar el array de usuarios y usuariosCuenta
                for (int i = 0; i < arrayMueblesVendidos.size(); i++) {
                    if (i == arrayMueblesVendidos.size() - 1) {
                        if (arrayMueblesVendidos.get(i).getUsuario().equals(usuario)) {
                            tempDouble += Double.parseDouble(arrayMueblesVendidos.get(i).getGanancia());
                            usuarios.add(usuario);
                            usuariosCuenta.add(tempDouble);
                        }
                        else {
                            usuarios.add(usuario);
                            usuariosCuenta.add(tempDouble);
                            usuario = arrayMueblesVendidos.get(i).getUsuario();
                            tempDouble = 0.0;
                            tempDouble += Double.parseDouble(arrayMueblesVendidos.get(i).getGanancia());
                            usuarios.add(usuario);
                            usuariosCuenta.add(tempDouble);
                        }
                    }
                    else {  
                        if (arrayMueblesVendidos.get(i).getUsuario().equals(usuario)) {
                            tempDouble += Double.parseDouble(arrayMueblesVendidos.get(i).getGanancia());
                        }
                        else {
                            usuarios.add(usuario);
                            usuariosCuenta.add(tempDouble);
                            usuario = arrayMueblesVendidos.get(i).getUsuario();
                            tempDouble = 0.0;
                            tempDouble += Double.parseDouble(arrayMueblesVendidos.get(i).getGanancia());
                        }
                    }
                }
                
                // Para seleccionar los usuarios mayores en un ArrayList usuariosMayores
                double cuentaTemp = 0.0;
                usuariosMayores.add(0);
                for (int i = 0; i < usuariosCuenta.size(); i++) {
                    if (usuariosCuenta.get(i) == cuentaTemp) {
                        usuariosMayores.add(i);
                    }
                    else if (usuariosCuenta.get(i) > cuentaTemp) {
                        usuariosMayores.clear();
                        usuariosMayores.add(i);
                        //administracionConsultaUsuarioMasGanancias += "<h2>" + usuarios.get(i) + " " + usuariosCuenta.get(i) + "</h2>";
                        cuentaTemp = usuariosCuenta.get(i);
                    }
                }         

                for (int i = 0; i < arrayMueblesVendidos.size(); i++) {
                    for (int j = 0; j < usuariosMayores.size(); j++) {
                        int temp = usuariosMayores.get(j);
                        if  (usuarios.get(temp).equals(arrayMueblesVendidos.get(i).getUsuario())) {
                            administracionConsultaUsuarioMasGanancias += "<tr>\n" +
                                "<td>" + arrayMueblesVendidos.get(i).getIdentificadorMueble() + "</td>\n" +
                                "<td>" + arrayMueblesVendidos.get(i).getNombreMueble() + "</td>\n" +
                                "<td>" + arrayMueblesVendidos.get(i).getUsuario() + "</td>\n" +
                                "<td>" + arrayMueblesVendidos.get(i).getCosto() + "</td>\n" +
                                "<td>" + arrayMueblesVendidos.get(i).getPrecio() + "</td>\n" +
                                "<td>" + arrayMueblesVendidos.get(i).getCantidad() + "</td>\n" +
                                "<td>" + arrayMueblesVendidos.get(i).getGanancia() + "</td>\n" +
                                "<td>" + arrayMueblesVendidos.get(i).getNombreCliente() + "</td>\n" +
                                "<td>" + arrayMueblesVendidos.get(i).getNITCliente() + "</td>\n" +
                                "<td>" + arrayMueblesVendidos.get(i).getFechaVenta() + "</td>\n" +
                                "</tr>";
                        }
                    }
                }
            }
            
            // Para exportar el archivo CSV
            if (exportarCSV) {
                String linea = "";
                
                linea += "ID de Mueble,Nombre de Mueble,Usuario,Costo,Precio,Cantidad,Ganancia,Nombre de Cliente,NIT de Cliente,Fecha de Venta";
                arrayAdministracion.add(linea);
                
                for (int i = 0; i < arrayMueblesVendidos.size(); i++) {
                    for (int j = 0; j < usuariosMayores.size(); j++) {
                        int temp = usuariosMayores.get(j);
                        if  (usuarios.get(temp).equals(arrayMueblesVendidos.get(i).getUsuario())) {
                            linea = "";
                            linea += arrayMueblesVendidos.get(i).getIdentificadorMueble() + ",";
                            linea += arrayMueblesVendidos.get(i).getNombreMueble() + ",";
                            linea += arrayMueblesVendidos.get(i).getUsuario() + ",";
                            linea += arrayMueblesVendidos.get(i).getCosto() + ",";
                            linea += arrayMueblesVendidos.get(i).getPrecio() + ",";
                            linea += arrayMueblesVendidos.get(i).getCantidad() + ",";
                            linea += arrayMueblesVendidos.get(i).getGanancia() + ",";
                            linea += arrayMueblesVendidos.get(i).getNombreCliente() + ",";
                            linea += arrayMueblesVendidos.get(i).getNITCliente() + ",";
                            linea += arrayMueblesVendidos.get(i).getFechaVenta() + ",";

                            arrayAdministracion.add(linea);
                        }
                    }
                }
                
                guardarArchivoCSV();
            }
            
            arrayMueblesVendidos.clear();
            
            arrayAdministracion.clear();
        }
        catch (Exception e)
        {
            System.err.println("Hay un error! ");
            System.err.println(e.getMessage());
        }
        
        return administracionConsultaUsuarioMasGanancias;
    }
    
    /**
     * Para consultar el mueble con más ventas en un intervalo de tiempo
     * @param fechaInicial
     * @param fechaFinal
     * @param exportarCSV
     * @return String
     */
    
    // Para consultar el mueble con más ventas en un intervalo de tiempo
    public String administracionConsultaMuebleMasVentas(String fechaInicial, String fechaFinal, boolean exportarCSV) {
        String administracionConsultaMuebleMasVentas = "";
        
        arrayMueblesVendidos.clear();
        
        arrayAdministracion.clear();

        try {      
            // Nos conectamos a mySQL
            mySQLConnect();
            
            // crea un java statement
            Statement st = connection.createStatement();

            // To sort by UpDown or Category                      
            PreparedStatement ps = connection.prepareStatement("USE mimuebleria;");
            
            if (fechaInicial.equals("") && fechaFinal.equals("")){
                // Se busca el intervalo requerido
                ps = connection.prepareStatement("SELECT * FROM mimuebleria.mueblesvendidos ORDER BY mimuebleria.mueblesvendidos.NombreMueble ASC");
                // en la sentencia se us ? para cada string que se utiliza despues del WHERE nombre = ?
                //ps.setString(1, "string1");
                //ps.setString(2, "string2");
                
                ResultSet rs = ps.executeQuery();
            }
            else if (fechaInicial.equals("") && !fechaFinal.equals("")){
                // Se busca el intervalo requerido
                ps = connection.prepareStatement("SELECT * FROM mimuebleria.mueblesvendidos WHERE FechaVenta <= ? ORDER BY mimuebleria.mueblesvendidos.NombreMueble ASC");
                // en la sentencia se us ? para cada string que se utiliza despues del WHERE nombre = ?
                //ps.setString(1, "string1");
                //ps.setString(2, "string2");
                ps.setDate(1, java.sql.Date.valueOf(fechaFinal));
                
                ResultSet rs = ps.executeQuery();
            }
            else if (!fechaInicial.equals("") && fechaFinal.equals("")){
                // Se busca el intervalo requerido
                ps = connection.prepareStatement("SELECT * FROM mimuebleria.mueblesvendidos WHERE FechaVenta >= ? ORDER BY mimuebleria.mueblesvendidos.NombreMueble ASC");
                // en la sentencia se us ? para cada string que se utiliza despues del WHERE nombre = ?
                //ps.setString(1, "string1");
                //ps.setString(2, "string2");
                ps.setDate(1, java.sql.Date.valueOf(fechaInicial));
                
                ResultSet rs = ps.executeQuery();
            }
            else if (!fechaInicial.equals("") && !fechaFinal.equals("")){
                // Se busca el intervalo requerido
                ps = connection.prepareStatement("SELECT * FROM mimuebleria.mueblesvendidos WHERE FechaVenta >= ? AND FechaVenta <= ? ORDER BY mimuebleria.mueblesvendidos.NombreMueble ASC");
                // en la sentencia se us ? para cada string que se utiliza despues del WHERE nombre = ?
                //ps.setString(1, "string1");
                //ps.setString(2, "string2");
                ps.setDate(1, java.sql.Date.valueOf(fechaInicial));
                ps.setDate(2, java.sql.Date.valueOf(fechaFinal));
                
                ResultSet rs = ps.executeQuery();
            }
            
            ResultSet rs = ps.executeQuery();
                
            // iteración a traves de java resultset
            while (rs.next())
            {   
                mueblesVendidos myMueble = new mueblesVendidos();

                myMueble.setIdentificadorMueble(rs.getString("IdentificadorMueble"));
                myMueble.setNombreMueble(rs.getString("NombreMueble"));
                myMueble.setUsuario(rs.getString("Usuario"));
                myMueble.setCosto(rs.getString("Costo"));
                myMueble.setPrecio(rs.getString("Precio"));
                myMueble.setCantidad(rs.getString("Cantidad"));
                myMueble.setGanancia(rs.getString("Ganancia"));
                myMueble.setNombreCliente(rs.getString("NombreCliente"));
                myMueble.setNITCliente(rs.getString("NITCliente"));
                myMueble.setFechaVenta(rs.getString("FechaVenta"));

                arrayMueblesVendidos.add(myMueble);

                myMueble = null;
            }
            
            ps.close();
            
            // Definicion de array de muebles
            ArrayList<String> muebles = new ArrayList<>();
            ArrayList<Integer> mueblesCuenta = new ArrayList<>();
            ArrayList<Integer> mueblesMayores = new ArrayList<>();
            
            if (arrayMueblesVendidos.size() > 0) {
                
                String mueble = arrayMueblesVendidos.get(0).getNombreMueble();
                int tempInt = 0;
                
                // Para llenar el array de muebles y mueblesCuenta
                for (int i = 0; i < arrayMueblesVendidos.size(); i++) {
                    if (i == arrayMueblesVendidos.size() - 1) {
                        if (arrayMueblesVendidos.get(i).getNombreMueble().equals(mueble)) {
                            tempInt++;
                            muebles.add(mueble);
                            mueblesCuenta.add(tempInt);
                        }
                        else {
                            muebles.add(mueble);
                            mueblesCuenta.add(tempInt);
                            mueble = arrayMueblesVendidos.get(i).getNombreMueble();
                            tempInt = 1;
                            muebles.add(mueble);
                            mueblesCuenta.add(tempInt);
                        }
                    }
                    else {  
                        if (arrayMueblesVendidos.get(i).getNombreMueble().equals(mueble)) {
                            tempInt++;
                        }
                        else {
                            muebles.add(mueble);
                            mueblesCuenta.add(tempInt);
                            mueble = arrayMueblesVendidos.get(i).getNombreMueble();
                            tempInt = 1;
                        }
                    }
                }
                
                // Para seleccionar los muebles mayores en un ArrayList mueblesMayores
                int cuentaTemp = 0;
                mueblesMayores.add(0);
                for (int i = 0; i < mueblesCuenta.size(); i++) {
                    if (mueblesCuenta.get(i) == cuentaTemp) {
                        mueblesMayores.add(i);
                        //administracionConsultaMuebleMasVentas += "<h2>" + arrayMueblesVendidos.get(mueblesMayores.get(i)).getNombreMueble() + "</h2>";
                    }
                    else if (mueblesCuenta.get(i) > cuentaTemp) {
                        mueblesMayores.clear();
                        mueblesMayores.add(i);
                        //administracionConsultaMuebleMasVentas += "<h2>" + arrayMueblesVendidos.get(mueblesMayores.get(i)).getNombreMueble() + "</h2>";
                        cuentaTemp = mueblesCuenta.get(i);
                    }
                }         

                for (int i = 0; i < arrayMueblesVendidos.size(); i++) {
                    for (int j = 0; j < mueblesMayores.size(); j++) {
                        int temp = mueblesMayores.get(j);
                        if  (muebles.get(temp).equals(arrayMueblesVendidos.get(i).getNombreMueble())) {
                            administracionConsultaMuebleMasVentas += "<tr>\n" +
                                "<td>" + arrayMueblesVendidos.get(i).getIdentificadorMueble() + "</td>\n" +
                                "<td>" + arrayMueblesVendidos.get(i).getNombreMueble() + "</td>\n" +
                                "<td>" + arrayMueblesVendidos.get(i).getUsuario() + "</td>\n" +
                                "<td>" + arrayMueblesVendidos.get(i).getCosto() + "</td>\n" +
                                "<td>" + arrayMueblesVendidos.get(i).getPrecio() + "</td>\n" +
                                "<td>" + arrayMueblesVendidos.get(i).getCantidad() + "</td>\n" +
                                "<td>" + arrayMueblesVendidos.get(i).getGanancia() + "</td>\n" +
                                "<td>" + arrayMueblesVendidos.get(i).getNombreCliente() + "</td>\n" +
                                "<td>" + arrayMueblesVendidos.get(i).getNITCliente() + "</td>\n" +
                                "<td>" + arrayMueblesVendidos.get(i).getFechaVenta() + "</td>\n" +
                                "</tr>";
                        }
                    }
                }
            }
            
            // Para exportar el archivo CSV
            if (exportarCSV) {
                String linea = "";
                
                linea += "ID de Mueble,Nombre de Mueble,Usuario,Costo,Precio,Cantidad,Ganancia,Nombre de Cliente,NIT de Cliente,Fecha de Venta";
                arrayAdministracion.add(linea);
                
                for (int i = 0; i < arrayMueblesVendidos.size(); i++) {
                    for (int j = 0; j < mueblesMayores.size(); j++) {
                        int temp = mueblesMayores.get(j);
                        if  (muebles.get(temp).equals(arrayMueblesVendidos.get(i).getNombreMueble())) {
                            linea = "";
                            linea += arrayMueblesVendidos.get(i).getIdentificadorMueble() + ",";
                            linea += arrayMueblesVendidos.get(i).getNombreMueble() + ",";
                            linea += arrayMueblesVendidos.get(i).getUsuario() + ",";
                            linea += arrayMueblesVendidos.get(i).getCosto() + ",";
                            linea += arrayMueblesVendidos.get(i).getPrecio() + ",";
                            linea += arrayMueblesVendidos.get(i).getCantidad() + ",";
                            linea += arrayMueblesVendidos.get(i).getGanancia() + ",";
                            linea += arrayMueblesVendidos.get(i).getNombreCliente() + ",";
                            linea += arrayMueblesVendidos.get(i).getNITCliente() + ",";
                            linea += arrayMueblesVendidos.get(i).getFechaVenta() + ",";

                            arrayAdministracion.add(linea);
                        }
                    }
                }
                
                guardarArchivoCSV();
            }
            
            arrayMueblesVendidos.clear();
            
            arrayAdministracion.clear();
        }
        catch (Exception e)
        {
            System.err.println("Hay un error! ");
            System.err.println(e.getMessage());
        }
        
        return administracionConsultaMuebleMasVentas;
    }
    
    /**
     * Para consultar el mueble con Menos ventas en un intervalo de tiempo
     * @param fechaInicial
     * @param fechaFinal
     * @param exportarCSV
     * @return String
     */
    
    // Para consultar el mueble con Menos ventas en un intervalo de tiempo
    public String administracionConsultaMuebleMenosVentas(String fechaInicial, String fechaFinal, boolean exportarCSV) {
        String administracionConsultaMuebleMenosVentas = "";
        
        arrayMueblesVendidos.clear();
        
        arrayAdministracion.clear();

        try {      
            // Nos conectamos a mySQL
            mySQLConnect();
            
            // crea un java statement
            Statement st = connection.createStatement();

            // To sort by UpDown or Category                      
            PreparedStatement ps = connection.prepareStatement("USE mimuebleria;");
            
            if (fechaInicial.equals("") && fechaFinal.equals("")){
                // Se busca el intervalo requerido
                ps = connection.prepareStatement("SELECT * FROM mimuebleria.mueblesvendidos ORDER BY mimuebleria.mueblesvendidos.NombreMueble ASC");
                // en la sentencia se us ? para cada string que se utiliza despues del WHERE nombre = ?
                //ps.setString(1, "string1");
                //ps.setString(2, "string2");
                
                ResultSet rs = ps.executeQuery();
            }
            else if (fechaInicial.equals("") && !fechaFinal.equals("")){
                // Se busca el intervalo requerido
                ps = connection.prepareStatement("SELECT * FROM mimuebleria.mueblesvendidos WHERE FechaVenta <= ? ORDER BY mimuebleria.mueblesvendidos.NombreMueble ASC");
                // en la sentencia se us ? para cada string que se utiliza despues del WHERE nombre = ?
                //ps.setString(1, "string1");
                //ps.setString(2, "string2");
                ps.setDate(1, java.sql.Date.valueOf(fechaFinal));
                
                ResultSet rs = ps.executeQuery();
            }
            else if (!fechaInicial.equals("") && fechaFinal.equals("")){
                // Se busca el intervalo requerido
                ps = connection.prepareStatement("SELECT * FROM mimuebleria.mueblesvendidos WHERE FechaVenta >= ? ORDER BY mimuebleria.mueblesvendidos.NombreMueble ASC");
                // en la sentencia se us ? para cada string que se utiliza despues del WHERE nombre = ?
                //ps.setString(1, "string1");
                //ps.setString(2, "string2");
                ps.setDate(1, java.sql.Date.valueOf(fechaInicial));
                
                ResultSet rs = ps.executeQuery();
            }
            else if (!fechaInicial.equals("") && !fechaFinal.equals("")){
                // Se busca el intervalo requerido
                ps = connection.prepareStatement("SELECT * FROM mimuebleria.mueblesvendidos WHERE FechaVenta >= ? AND FechaVenta <= ? ORDER BY mimuebleria.mueblesvendidos.NombreMueble ASC");
                // en la sentencia se us ? para cada string que se utiliza despues del WHERE nombre = ?
                //ps.setString(1, "string1");
                //ps.setString(2, "string2");
                ps.setDate(1, java.sql.Date.valueOf(fechaInicial));
                ps.setDate(2, java.sql.Date.valueOf(fechaFinal));
                
                ResultSet rs = ps.executeQuery();
            }
            
            ResultSet rs = ps.executeQuery();
                
            // iteración a traves de java resultset
            while (rs.next())
            {   
                mueblesVendidos myMueble = new mueblesVendidos();

                myMueble.setIdentificadorMueble(rs.getString("IdentificadorMueble"));
                myMueble.setNombreMueble(rs.getString("NombreMueble"));
                myMueble.setUsuario(rs.getString("Usuario"));
                myMueble.setCosto(rs.getString("Costo"));
                myMueble.setPrecio(rs.getString("Precio"));
                myMueble.setCantidad(rs.getString("Cantidad"));
                myMueble.setGanancia(rs.getString("Ganancia"));
                myMueble.setNombreCliente(rs.getString("NombreCliente"));
                myMueble.setNITCliente(rs.getString("NITCliente"));
                myMueble.setFechaVenta(rs.getString("FechaVenta"));

                arrayMueblesVendidos.add(myMueble);

                myMueble = null;
            }
            
            ps.close();
            
            // Definicion de array de muebles
            ArrayList<String> muebles = new ArrayList<>();
            ArrayList<Integer> mueblesCuenta = new ArrayList<>();
            ArrayList<Integer> mueblesMenores = new ArrayList<>();
            
            if (arrayMueblesVendidos.size() > 0) {
                
                String mueble = arrayMueblesVendidos.get(0).getNombreMueble();
                int tempInt = 0;
                
                // Para llenar el array de muebles y mueblesCuenta
                for (int i = 0; i < arrayMueblesVendidos.size(); i++) {
                    if (i == arrayMueblesVendidos.size() - 1) {
                        if (arrayMueblesVendidos.get(i).getNombreMueble().equals(mueble)) {
                            tempInt++;
                            muebles.add(mueble);
                            mueblesCuenta.add(tempInt);
                        }
                        else {
                            muebles.add(mueble);
                            mueblesCuenta.add(tempInt);
                            mueble = arrayMueblesVendidos.get(i).getNombreMueble();
                            tempInt = 1;
                            muebles.add(mueble);
                            mueblesCuenta.add(tempInt);
                        }
                    }
                    else {  
                        if (arrayMueblesVendidos.get(i).getNombreMueble().equals(mueble)) {
                            tempInt++;
                        }
                        else {
                            muebles.add(mueble);
                            mueblesCuenta.add(tempInt);
                            mueble = arrayMueblesVendidos.get(i).getNombreMueble();
                            tempInt = 1;
                        }
                    }
                }
                
                // Para seleccionar los muebles menores en un ArrayList mueblesMayores
                int cuentaTemp = 1000000;
                mueblesMenores.add(0);
                for (int i = 0; i < mueblesCuenta.size(); i++) {
                    if (mueblesCuenta.get(i) == cuentaTemp) {
                        mueblesMenores.add(i);
                        //administracionConsultaMuebleMenosVentas += "<h2>" + arrayMueblesVendidos.get(mueblesMayores.get(i)).getNombreMueble() + "</h2>";
                    }
                    else if (mueblesCuenta.get(i) < cuentaTemp) {
                        mueblesMenores.clear();
                        mueblesMenores.add(i);
                        //administracionConsultaMuebleMenosVentas += "<h2>" + arrayMueblesVendidos.get(mueblesMayores.get(i)).getNombreMueble() + "</h2>";
                        cuentaTemp = mueblesCuenta.get(i);
                    }
                }  

                for (int i = 0; i < arrayMueblesVendidos.size(); i++) {
                    for (int j = 0; j < mueblesMenores.size(); j++) {
                        int temp = mueblesMenores.get(j);
                        if  (muebles.get(temp).equals(arrayMueblesVendidos.get(i).getNombreMueble())) {
                            administracionConsultaMuebleMenosVentas += "<tr>\n" +
                                "<td>" + arrayMueblesVendidos.get(i).getIdentificadorMueble() + "</td>\n" +
                                "<td>" + arrayMueblesVendidos.get(i).getNombreMueble() + "</td>\n" +
                                "<td>" + arrayMueblesVendidos.get(i).getUsuario() + "</td>\n" +
                                "<td>" + arrayMueblesVendidos.get(i).getCosto() + "</td>\n" +
                                "<td>" + arrayMueblesVendidos.get(i).getPrecio() + "</td>\n" +
                                "<td>" + arrayMueblesVendidos.get(i).getCantidad() + "</td>\n" +
                                "<td>" + arrayMueblesVendidos.get(i).getGanancia() + "</td>\n" +
                                "<td>" + arrayMueblesVendidos.get(i).getNombreCliente() + "</td>\n" +
                                "<td>" + arrayMueblesVendidos.get(i).getNITCliente() + "</td>\n" +
                                "<td>" + arrayMueblesVendidos.get(i).getFechaVenta() + "</td>\n" +
                                "</tr>";
                        }
                    }
                }
            }
            
            // Para exportar el archivo CSV
            if (exportarCSV) {
                String linea = "";
                
                linea += "ID de Mueble,Nombre de Mueble,Usuario,Costo,Precio,Cantidad,Ganancia,Nombre de Cliente,NIT de Cliente,Fecha de Venta";
                arrayAdministracion.add(linea);
                
                for (int i = 0; i < arrayMueblesVendidos.size(); i++) {
                    for (int j = 0; j < mueblesMenores.size(); j++) {
                        int temp = mueblesMenores.get(j);
                        if  (muebles.get(temp).equals(arrayMueblesVendidos.get(i).getNombreMueble())) {
                            linea = "";
                            linea += arrayMueblesVendidos.get(i).getIdentificadorMueble() + ",";
                            linea += arrayMueblesVendidos.get(i).getNombreMueble() + ",";
                            linea += arrayMueblesVendidos.get(i).getUsuario() + ",";
                            linea += arrayMueblesVendidos.get(i).getCosto() + ",";
                            linea += arrayMueblesVendidos.get(i).getPrecio() + ",";
                            linea += arrayMueblesVendidos.get(i).getCantidad() + ",";
                            linea += arrayMueblesVendidos.get(i).getGanancia() + ",";
                            linea += arrayMueblesVendidos.get(i).getNombreCliente() + ",";
                            linea += arrayMueblesVendidos.get(i).getNITCliente() + ",";
                            linea += arrayMueblesVendidos.get(i).getFechaVenta() + ",";

                            arrayAdministracion.add(linea);
                        }
                    }
                }
                
                guardarArchivoCSV();
            }
            
            arrayMueblesVendidos.clear();
            
            arrayAdministracion.clear();
        }
        catch (Exception e)
        {
            System.err.println("Hay un error! ");
            System.err.println(e.getMessage());
        }
        
        return administracionConsultaMuebleMenosVentas;
    }
    
    /**
     * Para crear un usuario nuevo
     * @param usuarioNombre
     * @param usuarioPassword
     * @param usuarioTipo
     * @return String
     */
    
    // Para crear un usuario nuevo
    public String administracionCrearUsuario(String usuarioNombre, String usuarioPassword, String usuarioTipo) {
        String administracionCrearUsuario = "";
        
        arrayUsuario.clear();

        try {      
            // Nos conectamos a mySQL
            mySQLConnect();
            
            // crea un java statement
            Statement st = connection.createStatement();

            // To sort by UpDown or Category                      
            PreparedStatement ps = connection.prepareStatement("USE mimuebleria;");

            ps = connection.prepareStatement("REPLACE INTO mimuebleria.usuario VALUES(?,?,?,?)");
            // en la sentencia se us ? para cada string que se utiliza despues del WHERE nombre = ?
            //ps.setString(1, "string1");
            //ps.setString(2, "string2");
            ps.setString(1, usuarioNombre);
            ps.setString(2, usuarioPassword);
            if (usuarioTipo.equals("Fabrica")) {
                ps.setString(3, "1");
            }
            else if (usuarioTipo.equals("Ventas")) {
                ps.setString(3, "2");
            }
            else if (usuarioTipo.equals("Administracion")) {
                ps.setString(3, "3");
            }
            ps.setString(4, "falso");

            int intUpdate = ps.executeUpdate();
            
            administracionCrearUsuario += "<h2>Usuario creado</h2><h2>Nombre: " + usuarioNombre + "</h2><h2>Tipo: " + usuarioTipo + "</h2";
            
            ps.close();
            
            arrayUsuario.clear();
        }
        catch (Exception e)
        {
            System.err.println("Hay un error! ");
            System.err.println(e.getMessage());
        }
        
        return administracionCrearUsuario;
    }
    
    /**
     * Para bloquear un usuario existente
     * @param usuarioNombre
     * @return String
     */
    
    // Para bloquear un usuario existente
    public String administracionBloquearUsuario(String usuarioNombre) {
        String administracionBloquearUsuario = "";
        
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

                myUsuario.setNombre(rs.getString("Nombre"));
                myUsuario.setPassword(rs.getString("Password"));
                myUsuario.setTipo(rs.getString("Tipo"));
                myUsuario.setBloqueado(rs.getString("Bloqueado"));

                arrayUsuario.add(myUsuario);

                myUsuario = null;
            }

            ps.close();
            
            // Escribe el código html del select de muebles vendidos
            if (arrayUsuario.size() > 0){
                administracionBloquearUsuario = "";
                
                ps = connection.prepareStatement("DELETE FROM mimuebleria.usuario WHERE Nombre = ?");
                ps.setString(1, usuarioNombre);
                
                int intUpdate = ps.executeUpdate();
                
                for (int i = 0; i < arrayUsuario.size(); i++) {
                    if (arrayUsuario.get(i).getNombre().equals(usuarioNombre)) {
                        ps = connection.prepareStatement("REPLACE INTO mimuebleria.usuario VALUES (?,?,?,?)");
                        ps.setString(1, usuarioNombre);
                        ps.setString(2, arrayUsuario.get(i).getPassword());
                        ps.setString(3, arrayUsuario.get(i).getTipo());
                        ps.setString(4, "verdadero");
                        
                        intUpdate = ps.executeUpdate();
                    }
                }
                
                ps.close();
                
                administracionBloquearUsuario += "<h2>Usuario bloqueado </h2><h2>Nombre: " + usuarioNombre + "</h2>";
            }
            
            arrayUsuario.clear();
        }
        catch (Exception e)
        {
            System.err.println("Hay un error! ");
            System.err.println(e.getMessage());
        }
        
        return administracionBloquearUsuario;
    }
    
    /**
     * Para desbloquear un usuario existente
     * @param usuarioNombre
     * @return String
     */
    
    // Para desbloquear un usuario existente
    public String administracionDesbloquearUsuario(String usuarioNombre) {
        String administracionDesbloquearUsuario = "";
        
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

                myUsuario.setNombre(rs.getString("Nombre"));
                myUsuario.setPassword(rs.getString("Password"));
                myUsuario.setTipo(rs.getString("Tipo"));
                myUsuario.setBloqueado(rs.getString("Bloqueado"));

                arrayUsuario.add(myUsuario);

                myUsuario = null;
            }

            ps.close();
            
            // Escribe el código html del select de muebles vendidos
            if (arrayUsuario.size() > 0){
                administracionDesbloquearUsuario = "";
                
                ps = connection.prepareStatement("DELETE FROM mimuebleria.usuario WHERE Nombre = ?");
                ps.setString(1, usuarioNombre);
                
                int intUpdate = ps.executeUpdate();
                
                for (int i = 0; i < arrayUsuario.size(); i++) {
                    if (arrayUsuario.get(i).getNombre().equals(usuarioNombre)) {
                        ps = connection.prepareStatement("REPLACE INTO mimuebleria.usuario VALUES (?,?,?,?)");
                        ps.setString(1, usuarioNombre);
                        ps.setString(2, arrayUsuario.get(i).getPassword());
                        ps.setString(3, arrayUsuario.get(i).getTipo());
                        ps.setString(4, "falso");
                        
                        intUpdate = ps.executeUpdate();
                    }
                }
                
                ps.close();
                
                administracionDesbloquearUsuario += "<h2>Usuario desbloqueado </h2><h2>Nombre: " + usuarioNombre + "</h2>";
            }
            
            arrayUsuario.clear();
        }
        catch (Exception e)
        {
            System.err.println("Hay un error! ");
            System.err.println(e.getMessage());
        }
        
        return administracionDesbloquearUsuario;
    }
    
    /**
     * Para borrar un usuario existente
     * @param usuarioNombre
     * @return String
     */
    
    // Para borrar un usuario existente
    public String administracionBorrarUsuario(String usuarioNombre) {
        String administracionBorrarUsuario = "";
        
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

                myUsuario.setNombre(rs.getString("Nombre"));
                myUsuario.setPassword(rs.getString("Password"));
                myUsuario.setTipo(rs.getString("Tipo"));
                myUsuario.setBloqueado(rs.getString("Bloqueado"));

                arrayUsuario.add(myUsuario);

                myUsuario = null;
            }

            ps.close();
            
            // Escribe el código html del select de muebles vendidos
            if (arrayUsuario.size() > 0){
                administracionBorrarUsuario = "";
                
                ps = connection.prepareStatement("DELETE FROM mimuebleria.usuario WHERE Nombre = ?");
                ps.setString(1, usuarioNombre);
                
                int intUpdate = ps.executeUpdate();
                
                ps.close();
                
                administracionBorrarUsuario += "<h2>Usuario borrado </h2><h2>Nombre: " + usuarioNombre + "</h2>";
            }
            
            arrayUsuario.clear();
        }
        catch (Exception e)
        {
            System.err.println("Hay un error! ");
            System.err.println(e.getMessage());
        }
        
        return administracionBorrarUsuario;
    }
    
    /**
     * Para borrar un usuario existente
     * @return String
     */
    
    // Para borrar un usuario existente
    public String administracionBorrarUsuario() {
        String administracionBorrarUsuario = "";
        
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

                myUsuario.setNombre(rs.getString("Nombre"));
                myUsuario.setPassword(rs.getString("Password"));
                myUsuario.setTipo(rs.getString("Tipo"));
                myUsuario.setBloqueado(rs.getString("Bloqueado"));

                arrayUsuario.add(myUsuario);

                myUsuario = null;
            }

            ps.close();
            
            // Escribe el código html del select de muebles vendidos
            if (arrayUsuario.size() > 0){
                administracionBorrarUsuario = "";
                
                for (int i = 0; i < arrayUsuario.size(); i++) {
                    if (arrayUsuario.get(i).getBloqueado().equals("falso")) {
                        administracionBorrarUsuario += "<option value=\"" + arrayUsuario.get(i).getNombre() + "\">" + arrayUsuario.get(i).getNombre() + "</option>";
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
        
        return administracionBorrarUsuario;
    }
 
    ///////////////////////////////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////
    
    /**
     * To load a file with new information to be inserted in database
     * @param fileName 
     */
    
    // To load a file with new information to be inserted in database
    public void loadFile(String fileName) {
        Scanner stdIn = new Scanner(System.in);
        Scanner fileIn;
        String line;
        try
        {
            //System.out.print("Introduzca el nombre del archivo: ");
            fileIn = new Scanner(new FileReader(fileName));
            while (fileIn.hasNextLine())
            {
                //To process each line of file
                line = fileIn.nextLine();
                //processLine(line);
                System.out.println(line);
            }
            fileIn.close();
        }
        catch (FileNotFoundException e)
        {
            System.out.println("Error: " + e.getMessage());
        }        
    }
    
    /**
     * Para importar un archivo de Base de Datos
     */
    
    // Para importar un archivo de Base de Datos
    private void ImportDatabaseFile() {                                                  

        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Expecifique el archivo por abrir");
        // If the main directory is the user
        //fileChooser.setCurrentDirectory(new File(System.getProperty("user.home")));
        // if the main directory is the file address
        fileChooser.setCurrentDirectory(new File("."));
        // filsters the extension of the file
        FileNameExtensionFilter filter = new FileNameExtensionFilter("Text File","txt");
        fileChooser.setFileFilter(filter);     
        
        int approve = fileChooser.showOpenDialog(null);
        if (approve == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();

            // Loading the file
            loadFile(selectedFile.getAbsolutePath());           
            
        }
        else {
            System.out.println("No hay un archivo que abrir, por favor intente abrir de nuevo un archivo válido");
        }
    }   
    
    /**
     * Para guardar un archivo CSV
     */
    
    // Para guardar un archivo CSV
    public void guardarArchivoCSV() {
        PrintWriter fileOut; // conexión al archivo HTML

        try {
            //***********************
            //This is the FileChooser section
            final JFileChooser SaveAs = new JFileChooser();
            SaveAs.setApproveButtonText("Guardar archivo CSV");
            int actionDialog = SaveAs.showOpenDialog(null);
            if (actionDialog != JFileChooser.APPROVE_OPTION) {
               return;
            }

            File fileName = new File(SaveAs.getSelectedFile() + ".csv"); 
            
            //************************
            
            //fileOut = new PrintWriter("Reporte_MiMueblerias.csv");
            fileOut = new PrintWriter(fileName);
            
            for (int i = 0; i < arrayAdministracion.size(); i++) {
                fileOut.println(arrayAdministracion.get(i));
            }

            fileOut.close();
        }
        catch (FileNotFoundException e)
        {
            System.out.println("Error: " + e.getMessage());
        }  
    }
    
}
