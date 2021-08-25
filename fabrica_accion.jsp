<%@page contentType="text/html" pageEncoding="UTF-8"%>

<!DOCTYPE html>

<html>
    <head>
        <title>Fábrica</title>
        <meta charset="UTF-8">
        <!-- JSP meta tag -->
            <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <!-- JSP meta tag -->
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <style>
            body {background-image: url('images/fabrica_gamma.jpg');
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
        
        <h1 style="background-color:burlywood;">Área de Fábrica</h1>
        
        <!-- ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////-->
        <!-- ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////-->
        <!-- ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////-->
        <!-- Descripción de Fábrica-->
        <!-- ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////-->
        <!-- ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////-->
        <!-- ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////-->
        
        <div class="departamento">
        
        <p style="background-color:burlywood;">
            Para el ensamble de muebles, la fábrica compra diferentes piezas de madera por lo que los precios de
            dichas piezas afectan la ganancia final de un mueble ya que las piezas son la materia prima de todos los
            muebles. Las piezas son agrupadas en tipos que son usados en el proceso de fabricación de un mueble
            en particular.
            Actualmente en la fábrica existe un libro de indicaciones que contiene el detalle sobre los tipos de piezas
            y la cantidad de las mismas para el ensamble de algún tipo de mueble, así como también el precio de
            venta final que el mueble tendrá en la sala de ventas. El sistema debe tener funcionalidades que
            cumplan con la responsabilidad de este libro.
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
        <%! 
            String usuario_nombre = ""; 
            String usuario_password = "";
        %>
        <%
            MainClass mainClass = new MainClass();
        %>
        
        <div class="departamento">
        
            <h1>Introdución de información</h1>
            
            <form action="" method="post" autocomplete="on">
                <label for="fname">Usuario </label>
                <input type="text" id="fabrica_usuario_nombre_submit" name="fabrica_usuario_nombre_submit" value=""><br><br>
                <label for="fname">Password </label>
                <input type="password" id="fabrica_usuario_password_submit" name="fabrica_usuario_password_submit" value=""><br><br>
                <input type="submit" value="Introducir"><br><br>
            </form>
            <% 
                usuario_nombre = request.getParameter("fabrica_usuario_nombre_submit");
                usuario_password = request.getParameter("fabrica_usuario_password_submit");

                if (mainClass.IsUsuario(usuario_nombre, usuario_password, "1")) {
                    // Nos manda a la dirección url
                    response.sendRedirect("fabrica_formulario.jsp");
                }
            %>
        
        </div>
        
        <!-- ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////-->
        <!-- ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////-->
        <!-- ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////-->
        <!-- Link de vuelta -->
        <!-- ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////-->
        <!-- ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////-->
        <!-- ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////-->
        
        <div class="departamento">
        
        <a href="index.jsp">Mi Mueblería</a>
        
        </div>
    </body>
</html>
