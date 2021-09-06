<%@page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<!DOCTYPE html>

<html>
    <head>
        <title>Ventas Formulario</title>
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
        <style>
            table, th, td {
              border: 1px solid black;
              color: black;
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
        <!-- Formulario-->
        <!-- ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////-->
        <!-- ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////-->
        <!-- ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////-->
        
        <!-- Importando clases -->
        <%@ page import="myPackage.MainClass" %>
        <% 
            MainClass mainClass = new MainClass();
            String mueblesDisponiblesSelect = mainClass.mueblesDisponiblesSelect();
            String mueblesVendidosDisponiblesSelect = mainClass.mueblesVendidosDisponiblesSelect();
            String clientesDisponiblesSelect = mainClass.clientesDisponiblesSelect();
        %>
        
        <!-- /////////////////////////////////////////////////////-->
        <!-- Ventas -->
        <!-- /////////////////////////////////////////////////////-->
        
        <div class="departamento">
        
            <h1>Ventas</h1>
            <% 
                String NITCliente = request.getParameter("ventas_NITCliente");
                String ventas_consultarNITCliente = mainClass.ventasConsultarNITClientes(NITCliente);

                out.print(ventas_consultarNITCliente);
            %>
            <!--
            <form action="ventas_formulario_consultarNITCliente.jsp" method="post" autocomplete="on">
                <label for="fname">NIT cliente </label>
                <input type="text" id="ventas_NITCliente" name="ventas_NITCliente" value="" required>
                <br><br>
                <input type="submit" value="Consultar NIT">
                <br><br>
            </form>
            -->
            
            <!-- if NIT exist fill the values -->
            <!--
            <form action="ventas_formulario_crearFactura.jsp" method="post" autocomplete="on">
                <label for="fname">NIT cliente </label>
                <input type="text" id="ventas_NITCliente_factura" name="ventas_NITCliente_factura" value="" required>
                <br><br>
                <label for="fname">Nombre cliente </label>
                <input type="text" id="ventas_nombrecliente" name="ventas_nombrecliente" value="" required>
                <br><br>
                <label for="fname">Dirección cliente </label>
                <input type="text" id="ventas_direccioncliente" name="ventas_direccioncliente" value="" required>
                <br><br>
                <label for="fname">Municipio </label>
                <input type="text" id="ventas_municipio" name="ventas_municipio" value="" required>
                <br><br>
                <label for="fname">Departamento </label>
                <input type="text" id="ventas_departamento" name="ventas_departamento" value="" required>
                <br><br>
                -->
                
                <label for="ventas_muebles_disponibles">Mueble </label>       
                <!-- Llenar los valores con los muebles -->
                    <select id="ventas_mueble_ID" name="ventas_mueble_ID" size="1">
                        <% 
                            out.print(mueblesDisponiblesSelect);
                        %>
                        <!--
                        <option value="mueble0">-------</option>
                        -->
                        <!--
                        <option value="mueble1">mueble1</option>
                        <option value="mueble2">mueble2</option>
                        <option value="mueble3">mueble3</option>
                        <option value="mueble4">mueble4</option>
                        -->
                    </select>               
                <!-- Llenar los valores con los muebles -->
                
                <label for="fname"> Fecha de venta </label>
                <input type="date" id="ventas_fechaventa" name="ventas_fechaventa" value="" required="">
                <br><br>
                <input type="submit" value="Crear factura">
                <br><br>
            </form>
            
            <!-- Datos de ejemplo al crear una factura -->
                <!--
                <p>Identificador mueble = ASDASDASD</p>
                <p>Nombre del mueble = Mesa redonda pequeña</p>
                <p>Usuario = abner</p>
                <p>Costo = 200.0</p>
                <p>Precio = 250.0</p>
                <p>Cantidad = 1</p>
                <p>Ganancia = 50.0</p>
                <p>Nombre cliente = Jeronimo</p>
                <p>Apellido Cliente = Tzul</p>
                <p>NIT cliente = 12345678</p>
                <p>Fecha de venta = 07/08/2020</p>
                -->
            <!-- Datos de ejemplo al crear una factura -->
        
        </div>
        
        <!-- /////////////////////////////////////////////////////-->
        <!-- Devoluciones -->
        <!-- /////////////////////////////////////////////////////-->
        
        <div class="departamento">
        
            <h1>Devoluciones</h1>

            <form action="ventas_formulario_devoluciones.jsp" method="post" autocomplete="on">
                <label for="fname">Muebles vendidos </label>
                <select id="ventas_devolucion_muebleID" name="ventas_devolucion_muebleID" size="1">
                    <% 
                        out.print(mueblesVendidosDisponiblesSelect);
                    %>
                    <!--
                    <option value="mueble0">------</option>
                    -->
                    <!--
                    <option value="mueble1">mueble1</option>
                    <option value="mueble2">mueble2</option>
                    <option value="mueble3">mueble3</option>
                    <option value="mueble4">mueble4</option>
                    -->
                </select>
                <br><br>
                <input type="submit" value="Confirmar devolución">
                <br><br>
                <!-- Datos para elegir -->
                    <!-- Si el mueble NO puede ser devuelto -->
                    <!--
                        <p>El mueble NO puede ser devuelto</p>
                    -->
                    <!-- Si el mueble puede ser devuelto se despliega la información -->
                    <!--
                        <p>Identificador mueble = ASDASDASD</p>
                        <p>Nombre del mueble = Mesa redonda pequeña</p>
                        <p>Cantidad = 1</p>
                        <p>Nombre cliente = Jeronimo</p>
                        <p>Apellido Cliente = Tzul</p>
                        <p>NIT cliente = 12345678</p>
                        <p>Fecha de devolución = 07/08/2020</p>
                        <p>Pérdida = 66.66</p>
                    -->
                <!-- Datos para elegir -->
                <br><br>
            </form>
        
        </div>
        
        <!-- /////////////////////////////////////////////////////-->
        <!-- Consulta de compras de un cliente -->
        <!-- /////////////////////////////////////////////////////-->
        
        <div class="departamento">
            
            <h1>Consultas de compras de un cliente y detalle de facturas</h1>
            
            <form action="ventas_formulario_consultaDeComprasCliente.jsp" method="post" autocomplete="on">
                <label for="fname">NIT de clientes disponibles </label>
                <!-- Llenar con datos -->
                    <select id="ventas_consultaventas_NITCliente" name="ventas_consultaventas_NITCliente" size="1">
                        <% 
                            out.print(clientesDisponiblesSelect);
                        %>
                        <!--
                        <option value="NIT0">----</option>
                        -->
                        <!--
                        <option value="NIT1">NIT1</option>
                        <option value="NIT2">NIT2</option>
                        <option value="NIT3">NIT3</option>
                        <option value="NIT4">NIT4</option>
                        -->
                    </select>
                <!-- Llenar con datos -->
                <label for="fname"> Fecha inicial </label>
                <input type="date" id="ventas_consultaventas_fechainicial" name="ventas_consultaventas_fechainicial" value="">
                <label for="fname"> Fecha final </label>
                <input type="date" id="ventas_consultaventas_fechafinal" name="ventas_consultaventas_fechafinal" value="">
                <br><br>
                <input type="submit" value="Consultar compras">
                <br><br>
                <!-- Llenar con datos -->
                    <table style="width:100%" id="ventas_tabla_consultaventas">
                        <tr>
                            <th>ID mueble</th>
                            <th>Nombre mueble</th>
                            <th>Usuario</th>
                            <th>Costo</th>
                            <th>Precio</th>
                            <th>Cantidad</th>
                            <th>Ganancia</th>
                            <th>Nombre cliente</th>
                            <th>NIT cliente</th>
                            <th>Fecha venta</th>
                        </tr>
                        <!--
                        <tr>
                            <td>ALSDHLAKJHSD</td>
                            <td>Mesa ovalada</td>
                            <td>Casimiro</td>
                            <td>120.0</td>
                            <td>150.0</td>
                            <td>2</td>
                            <td>30.0</td>
                            <td>Manuel</td>
                            <td>321654987</td>
                            <td>25/02/2021</td>
                        </tr>
                        -->
                    </table>
                <!-- Llenar con datos -->
                <br><br>
            </form>
        
        </div>
        
        <!-- /////////////////////////////////////////////////////-->
        <!-- Consulta de ventas del dia -->
        <!-- /////////////////////////////////////////////////////-->
        
        <div class="departamento">
            
            <h1>Consulta de ventas del dia</h1>
            
            <form action="ventas_formulario_consultaVentasDelDia.jsp" method="post" autocomplete="on">
                <label for="fname"> Fecha </label>
                <input type="date" id="ventas_consultaventasdeldia_fechainicial" name="ventas_consultaventasdeldia_fechainicial" value="" required>
                <br><br>
                <input type="submit" value="Consultar ventas del dia">
                <br><br>
                <!-- Llenar con datos -->
                    <table style="width:100%" id="ventas_tabla_consultaventas">
                        <tr>
                            <th>ID mueble</th>
                            <th>Nombre mueble</th>
                            <th>Usuario</th>
                            <th>Costo</th>
                            <th>Precio</th>
                            <th>Cantidad</th>
                            <th>Ganancia</th>
                            <th>Nombre cliente</th>
                            <th>NIT cliente</th>
                            <th>Fecha venta</th>
                        </tr>
                        <!--
                        <tr>
                            <td>ALSDHLAKJHSD</td>
                            <td>Mesa ovalada</td>
                            <td>Casimiro</td>
                            <td>120.0</td>
                            <td>150.0</td>
                            <td>2</td>
                            <td>30.0</td>
                            <td>Manuel</td>
                            <td>321654987</td>
                            <td>25/02/2021</td>
                        </tr>
                        -->
                    </table>
                <!-- Llenar con datos -->
                <br><br>
            </form>
        
        </div>
        
        <!-- /////////////////////////////////////////////////////-->
        <!-- Consulta de devoluciones de un cliente -->
        <!-- /////////////////////////////////////////////////////-->
        
        <div class="departamento">
            
            <h1>Consultas de devoluciones de un cliente</h1>
            
            <form action="ventas_formulario_consultaDevolucionesDeCliente.jsp" method="post" autocomplete="on">
                <label for="fname">NIT de clientes disponibles </label>
                <!-- Llenar con datos -->
                    <select id="ventas_consultadevoluciones_NITCliente" name="ventas_consultadevoluciones_NITCliente" size="1">
                        <% 
                            String clientesDevolucionDisponiblesSelect = mainClass.clientesDevolucionDisponiblesSelect();
                            
                            out.print(clientesDevolucionDisponiblesSelect);
                        %>
                        <!--
                        <option value="NIT0">----</option>
                        -->
                        <!--
                        <option value="NIT1">NIT1</option>
                        <option value="NIT2">NIT2</option>
                        <option value="NIT3">NIT3</option>
                        <option value="NIT4">NIT4</option>
                        -->
                    </select>
                <!-- Llenar con datos -->
                <label for="fname"> Fecha inicial </label>
                <input type="date" id="ventas_consultadevoluciones_fechainicial" name="ventas_consultadevoluciones_fechainicial" value="">
                <label for="fname"> Fecha final </label>
                <input type="date" id="ventas_consultadevoluciones_fechafinal" name="ventas_consultadevoluciones_fechafinal" value="">
                <br><br>
                <input type="submit" value="Consultar devoluciones">
                <br><br>
                <!-- Llenar con datos -->
                    <table style="width:100%" id="ventas_tabla_consultadevoluciones">
                        <tr>
                            <th>ID mueble</th>
                            <th>Nombre mueble</th>
                            <th>Cantidad</th>
                            <th>Nombre cliente</th>
                            <th>NIT cliente</th>
                            <th>Fecha devolución</th>
                            <th>Pérdida</th>
                        </tr>
                        <!--
                        <tr>
                            <td>ALSDHLAKJHSD</td>
                            <td>Mesa ovalada</td>
                            <td>2</td>
                            <td>Manuel</td>
                            <td>321654987</td>
                            <td>25/02/2021</td>
                            <td>50.0</td>
                        </tr>
                        -->
                    </table>
                <!-- Llenar con datos -->
                <br><br>
            </form>
        
        </div>
        
        <!-- /////////////////////////////////////////////////////-->
        <!-- Consulta de muebles en la sala de ventas -->
        <!-- /////////////////////////////////////////////////////-->
        
        <div class="departamento">
            
            <h1>Consulta de muebles disponibles en la sala de ventas</h1>
            
            <form action="ventas_formulario_ConsultaDeMueblesDisponibles.jsp" method="post" autocomplete="on">
                <input type="submit" value="Consultar muebles disponibles">
                <br><br>
                <!-- Llenar con datos -->
                    <table style="width:100%" id="ventas_tabla_consultamuebles">
                        <tr>
                            <th>ID mueble</th>
                            <th>Nombre mueble</th>
                            <th>Usuario</th>
                            <th>Fecha de ensamblaje</th>
                            <th>Costo</th>
                            <th>Precio</th>
                        </tr>
                        <!--
                        <tr>
                            <td>ALSDHLAKJHSD</td>
                            <td>Mesa ovalada</td>
                            <td>fernando</td>
                            <td>25/02/2020</td>
                            <td>200.0</td>
                            <td>250.0</td>
                        </tr>
                        -->
                    </table>
                <!-- Llenar con datos -->
                <br><br>
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
        <br>
        <a href="ventas_formulario.jsp">Ventas</a>
        
        </div>
    </body>
</html>
