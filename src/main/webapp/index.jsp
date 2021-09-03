<%@page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<!DOCTYPE html>

<html>
    <head>
        <title>Mi Mueblería</title>
        <meta charset="UTF-8">
        <!-- JSP meta tag -->
            <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <!-- JSP meta tag -->
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <style>
            body {background-image: url('images/mimuebleria_gamma.jpg');
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
        <%@ page import="myPackage.MainClass" %>
        <%! 
            String usuario_nombre = ""; 
            String usuario_password = "";
        %>
        <%
            MainClass mainClass = new MainClass();
        %>
        
        <h1 style="background-color:burlywood;">
            Mi Mueblería
        </h1>
        
        <!-- ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////-->
        <!-- ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////-->
        <!-- ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////-->
        <!-- INGRESO DE BASE DE DATOS-->
        <!-- ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////-->
        <!-- ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////-->
        <!-- ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////-->
        
        <div class="departamento">
        
            <center><img src="images/mimuebleria.jpg" alt="Mi Mueblería" align="middle"></center>
        
        </div>
        
        <!-- ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////-->
        <!-- ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////-->
        <!-- ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////-->
        <!-- INGRESO DE BASE DE DATOS-->
        <!-- ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////-->
        <!-- ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////-->
        <!-- ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////-->
        
        <div class="departamento">
        
            <h1>INGRESO DE BASE DE DATOS</h1>

            <form action="index_basededatos_login.jsp" method="post" autocomplete="on">
                <label for="index_basededatos_url">URL de Base de Datos </label>
                <input type="text" id="index_basededatos_url" name="index_basededatos_url" value="jdbc:mysql://localhost:3306" required="">
                <br><br>
                <label for="index_basededatos_usuario">Usuario de Base de Datos </label>
                <input type="text" id="index_basededatos_usuario" name="index_basededatos_usuario" value="root" required="">
                <br><br>
                <label for="index_basededatos_password">Constraseña de Base de Datos </label>
                <input type="text" id="index_basededatos_password" name="index_basededatos_password" value="123456" required="">
                <br><br>
                <input type="submit" id="index_basededatos_ingresar" name="index_basededatos_ingresar" value="Ingresar">
                <br><br>
            </form>
        
        </div>

    </body>
</html>
