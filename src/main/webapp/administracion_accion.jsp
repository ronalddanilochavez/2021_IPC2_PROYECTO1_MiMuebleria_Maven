<%@page contentType="text/html" pageEncoding="UTF-8"%>

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
        <%! 
            String usuario_nombre = ""; 
            String usuario_password = "";
        %>
        <%
            MainClass mainClass = new MainClass();
        %>
        
        <div class="departamento">
            
            <h1>Introdución de información</h1>
        
            <form action="administracion_accion.jsp" method="post" autocomplete="on">
                <label for="fname">Usuario </label>
                <input type="text" id="administracion_usuario_nombre_submit" name="administracion_usuario_nombre_submit" value=""><br><br>
                <label for="fname">Password </label>
                <input type="password" id="administracion_usuario_password_submit" name="administracion_usuario_password_submit" value=""><br><br>
                <input type="submit" value="Introducir"><br><br>
            </form>
            
            <% 
                usuario_nombre = request.getParameter("administracion_usuario_nombre_submit");
                usuario_password = request.getParameter("administracion_usuario_password_submit");

                if (mainClass.IsUsuario(usuario_nombre, usuario_password, "3")) {
                    // Nos manda a la dirección url
                    response.sendRedirect("administracion_formulario.jsp");
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