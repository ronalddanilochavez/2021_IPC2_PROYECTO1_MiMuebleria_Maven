<%@page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<!DOCTYPE html>

<html>
    <head>
        <title>Administración</title>
        <meta charset="UTF-8">
        <!-- JSP meta tag -->
            <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <!-- JSP meta tag -->
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <style>
            body {background-image: url('images/administracion_gamma.jpg');
            background-repeat: no-repeat;
            background-attachment: fixed;
            background-size: cover;
        }
        </style> 
        <style>
            .departamento {
            opacity: 0.9;
            background-color: burlywood;
            color: black;
            border: 2px solid black;
            margin: 20px;
            padding: 20px;
            }
        </style>
    </head>
    <body>
        <h1 style="background-color:burlywood;">Área Financiera y Administración</h1>
        
        <!-- ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////-->
        <!-- ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////-->
        <!-- ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////-->
        <!-- Descripción de Administración-->
        <!-- ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////-->
        <!-- ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////-->
        <!-- ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////-->
        
        <div class="departamento">
        
        <p style="background-color:burlywood;">
            Los usuarios de esta área se encargan de recopilar información financiera basada en los costos de
            producción de muebles y las ventas de los mismos para poder identificar las ganancias o pérdidas que la
            fábrica genera en un intervalo de tiempo.
        </p>
        
        </div>
        
        <!-- ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////-->
        <!-- ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////-->
        <!-- ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////-->
        <!-- Formulario -->
        <!-- ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////-->
        <!-- ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////-->
        <!-- ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////-->
        
        <%@ page import="myPackage.MainClass" %>
        <%
            MainClass mainClass = new MainClass();
        %>
        
        <div class="departamento">
            
            <h1>Introdución de información</h1>
        
            <form action="administracion_accion.jsp" method="post" autocomplete="on">
                <label for="fname">Usuario </label> 
                <!-- Llenar los valores con los usuarios -->
                    <select id="administracion_usuario_nombre_submit" name="administracion_usuario_nombre_submit" size="1">
                        <% 
                            String usuariosAdministracionDisponiblesSelect = mainClass.usuariosAdministracionDisponiblesSelect();
                            
                            out.print(usuariosAdministracionDisponiblesSelect);
                        %>
                        <!--
                        <option value="pieza0">-----</option>
                        -->
                        <!--
                            <option value="usuario1">usuario1</option>
                            <option value="usuario2">usuario2</option>
                            <option value="usuario3">usuario3</option>
                            <option value="usuario4">usuario4</option>
                        -->
                    </select>
                <!-- Llenar los valores con los usuarios -->
                <br><br>
                <label for="fname">Password </label>
                <input type="password" id="administracion_usuario_password_submit" name="administracion_usuario_password_submit" value="" required>
                <br><br>
                <input type="submit" value="Introducir"><br><br>
            </form>
        
        </div>
        
        <!-- ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////-->
        <!-- ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////-->
        <!-- ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////-->
        <!-- Link de vuelta -->
        <!-- ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////-->
        <!-- ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////-->
        <!-- ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////-->
        
        <div class="departamento">
        
        <a href="mi_muebleria_intro.jsp">Mi Mueblería</a>
        
        </div>
    </body>
</html>
