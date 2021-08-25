<%@page contentType="text/html" pageEncoding="UTF-8"%>

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
        <h1 style="background-color:burlywood;">
            Mi Mueblería
        </h1>
        
        <!-- ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////-->
        <!-- ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////-->
        <!-- ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////-->
        <!-- Descripción de Fábrica-->
        <!-- ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////-->
        <!-- ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////-->
        <!-- ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////-->
        
        <div class="departamento">
        
            <h2>Área de Fábrica</h2>
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
            <a href="fabrica.jsp">Área de Fábrica</a><br><br>
            <a href="fabrica_formulario.jsp">Área de Fábrica Formulario</a>
        
        </div>
        
        <!-- ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////-->
        <!-- ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////-->
        <!-- ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////-->
        <!-- Descripción de Ventas-->
        <!-- ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////-->
        <!-- ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////-->
        <!-- ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////-->
        
        <div class="departamento">
        
            <h2>Área de Punto de Ventas</h2>
            <p style="background-color:burlywood;">
                Después de que un mueble es ensamblado, se le asigna un identificador único y se pone a la venta con
                un precio que se establece a partir del tipo de mueble ensamblado.
                Los clientes llegan a la sala de ventas para comprar muebles y con los identificadores de los muebles
                que desean comprar pasan a la caja en donde se registra el movimiento y se genera la factura para el
                cliente. Al iniciar el registro de la compra se solicita el NIT, si el NIT ya existe se debe obtener
                automáticamente el nombre y la dirección del cliente, de lo contrario se debe solicitar esos datos al
                cliente y registrarlos en el sistema para su uso en la compra actual y compras futuras.
            </p>
            <a href="ventas.jsp">Área de Punto de Ventas</a><br><br>
            <a href="ventas_formulario.jsp">Área de Punto de Ventas Formulario</a>
        
        </div>
        
        <!-- ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////-->
        <!-- ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////-->
        <!-- ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////-->
        <!-- Descripción de Administración-->
        <!-- ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////-->
        <!-- ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////-->
        <!-- ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////-->
        
        <div class="departamento">
        
            <h2>Área Financiera y Administración</h2>
            <p style="background-color:burlywood;">
                Los usuarios de esta área se encargan de recopilar información financiera basada en los costos de
                producción de muebles y las ventas de los mismos para poder identificar las ganancias o pérdidas que la
                fábrica genera en un intervalo de tiempo.
            </p>
            <a href="administracion.jsp">Área Financiera y Administración</a><br><br>
            <a href="administracion_formulario.jsp">Área Financiera y Administración Formulario</a>
        
        </div>
    </body>
</html>
