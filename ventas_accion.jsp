<%@page contentType="text/html" pageEncoding="UTF-8"%>

<!DOCTYPE html>

<html>
    <head>
        <title>Ventas</title>
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
        <h1 style="background-color:burlywood;">Área de Punto de Ventas</h1>
        
        <!-- ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////-->
        <!-- ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////-->
        <!-- ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////-->
        <!-- Descripción de Ventas-->
        <!-- ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////-->
        <!-- ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////-->
        <!-- ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////-->
        
        <div class="departamento">
        
        <p style="background-color:burlywood;">
            Después de que un mueble es ensamblado, se le asigna un identificador único y se pone a la venta con
            un precio que se establece a partir del tipo de mueble ensamblado.
            Los clientes llegan a la sala de ventas para comprar muebles y con los identificadores de los muebles
            que desean comprar pasan a la caja en donde se registra el movimiento y se genera la factura para el
            cliente. Al iniciar el registro de la compra se solicita el NIT, si el NIT ya existe se debe obtener
            automáticamente el nombre y la dirección del cliente, de lo contrario se debe solicitar esos datos al
            cliente y registrarlos en el sistema para su uso en la compra actual y compras futuras.
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
        
            <form action="ventas_accion.jsp" method="post" autocomplete="on">
                <label for="fname">Usuario </label>
                <input type="text" id="ventas_usuario_nombre_submit" name="ventas_usuario_nombre_submit" value=""><br><br>
                <label for="fname">Password </label>
                <input type="password" id="ventas_usuario_password_submit" name="ventas_usuario_password_submit" value=""><br><br>
                <input type="submit" value="Introducir"><br><br>
            </form>
            
            <%@ page import="myPackage.MainClass" %>
            <% 
                usuario_nombre = request.getParameter("ventas_usuario_nombre_submit");
                usuario_password = request.getParameter("ventas_usuario_password_submit");

                if (mainClass.IsUsuario(usuario_nombre, usuario_password, "2")) {
                    // Nos manda a la dirección url
                    response.sendRedirect("ventas_formulario.jsp");
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
