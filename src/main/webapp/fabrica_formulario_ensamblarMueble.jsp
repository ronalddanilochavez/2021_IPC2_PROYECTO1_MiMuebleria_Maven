<%@page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<!DOCTYPE html>

<html lang="es">
    <head>
        <title>Fábrica Formulario</title>
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
        <style>
            table, th, td {
            border: 1px solid black;
            color: black;
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
        
        <!-- Importando clases -->
        <%@ page import="myPackage.MainClass" %>
        <% 
            MainClass mainClass = new MainClass();
            String piezasDisponiblesSelect = mainClass.piezasDisponiblesSelect();
            String piezasPorAgotarse = mainClass.piezasPorAgotarse();
        %>
        
        <!-- /////////////////////////////////////////////////////-->
        <!-- Crear, modificar o borrar pieza -->
        <!-- /////////////////////////////////////////////////////-->
        
        <!-- Crear, modificar o borrar pieza -->
        
        <div class="departamento">
            
            <!-- Crear pieza -->
        
            <h1>Crear pieza</h1>

            <form action="fabrica_formulario_CrearPieza.jsp" method="post" autocomplete="on">
                <label for="fname">Tipo de pieza </label>
                <input type="text" id="fabrica_crearpieza_tipo" name="fabrica_crearpieza_tipo" value="" requested>
                <br><br>
                <label for="fname">Cantidad de piezas </label>
                <input type="text" id="fabrica_crearpieza_cantidad" name="fabrica_crearpieza_cantidad" value="" requested>
                <br><br>
                <label for="fname">Costo de pieza </label>
                <input type="text" id="fabrica_crearpieza_costo" name="fabrica_crearpieza_costo" value="" requested>
                <br><br>
                <input type="submit" value="Crear pieza">
                <br><br>
                <!--
                    <h2>Pieza creada</h2>
                    <h2>Tipo: </h2>
                    <h2>Cantidad: </h2>
                    <h2>Costo: </h2>
                    <br><br>
                -->
            </form>
            
            <!-- Modificar pieza -->
            
            <h1>Modificar pieza</h1>

            <form action="fabrica_formulario_ModificarPieza.jsp" method="post" autocomplete="on">
                <label for="fabrica_modificarpieza_piezas_disponibles">Pieza </label> 
                <!-- Llenar los valores con las piezas -->
                    <select id="fabrica_modificarpieza_tipo" name="fabrica_modificarpieza_tipo" size="1">
                        <% 
                            out.print(piezasDisponiblesSelect);
                        %>
                        <!--
                        <option value="pieza0">------</option>
                        -->
                        <!--
                        <option value="pieza1">pieza1</option>
                        <option value="pieza2">pieza2</option>
                        <option value="pieza3">pieza3</option>
                        <option value="pieza4">pieza4</option>
                        -->
                    </select>
                <!-- Llenar los valores con las piezas -->
                <br><br>
                <label for="fname">Cantidad de piezas </label>
                <input type="text" id="fabrica_modificarpieza_cantidad" name="fabrica_modificarpieza_cantidad" value="" requested>
                <br><br>
                <label for="fname">Costo de pieza </label>
                <input type="text" id="fabrica_modificarpieza_costo" name="fabrica_modificarpieza_costo" value="" requested>
                <br><br>
                <input type="submit"  name="fabrica_modificarpieza_modificar" value="Modificar pieza">
                <br><br>
                <!--
                    <h2>Pieza modificada</h2>
                    <h2>Tipo: </h2>
                    <h2>Cantidad: </h2>
                    <h2>Costo: </h2>
                    <br><br>
                -->    
            </form>
            
            <!-- Borrar pieza -->
            
            <h1>Borrar pieza</h1>
            
            <form action="fabrica_formulario_BorrarPieza.jsp" method="post" autocomplete="on">
                <label for="fabrica_modificarpieza_piezas_disponibles">Pieza </label> 
                <!-- Llenar los valores con las piezas -->
                    <select id="fabrica_borrarpieza_tipo" name="fabrica_borrarpieza_tipo" size="1">
                        <% 
                            out.print(piezasDisponiblesSelect);
                        %>
                        <!--
                        <option value="pieza0">------</option>
                        -->
                        <!--
                        <option value="pieza1">pieza1</option>
                        <option value="pieza2">pieza2</option>
                        <option value="pieza3">pieza3</option>
                        <option value="pieza4">pieza4</option>
                        -->
                    </select>
                <!-- Llenar los valores con las piezas -->
                <br><br>
                <input type="submit"  name="fabrica_borrarpieza_borrar" value="Borrar pieza">
                <br><br>
            </form>
            <!--
                <h2>Pieza borrada</h2>
                <h2>Tipo: </h2>
                <br><br>
            -->  
        
        </div>
        
        <!-- /////////////////////////////////////////////////////-->
        <!-- Definir mueble a  partir del ensamble de piezas -->
        <!-- /////////////////////////////////////////////////////-->
        
        <div class="departamento">
        
            <h1>Definir mueble a  partir del ensamble de piezas</h1>

            <form action="fabrica_formulario_ensamblarpiezas.jsp" method="post" autocomplete="on">
                <label for="fabrica_ensamblarpiezas_nombremueble">Nombre mueble </label>
                <input type="text" id="fabrica_ensamblarpiezas_nombremueble" name="fabrica_ensamblarpiezas_nombremueble" value="" requested>
                <br><br>
                <label for="fabrica_ensamblarpiezas_piezas_disponibles">Pieza </label> 
                <!-- Llenar los valores con las piezas -->
                    <select id="fabrica_ensamblarpiezas_pieza" name="fabrica_ensamblarpiezas_pieza" size="1">
                        <% 
                            out.print(piezasDisponiblesSelect);
                        %>
                        <!--
                        <option value="pieza0">-----</option>
                        -->
                        <!--
                            <option value="pieza1">pieza1</option>
                            <option value="pieza2">pieza2</option>
                            <option value="pieza3">pieza3</option>
                            <option value="pieza4">pieza4</option>
                        -->
                    </select>
                <!-- Llenar los valores con las piezas -->
                <br><br>
                <label for="fabrica_ensamblarpiezas_cantidad">Cantidad </label>
                <input type="text" id="fabrica_ensamblarpiezas_cantidad" name="fabrica_ensamblarpiezas_cantidad" value="" requested>
                <br><br>
                <input type="submit" id="fabrica_ensamblarpiezas_agregarpieza" name="fabrica_ensamblarpiezas_agregarpieza" value="Agregar pieza">
                <!-- Ejemplo de datos presentados al usuario -->
                    <!--
                        <h2>Mesa redonda con patas cuadradas, Pata cuadrada, 4</h2>
                        <h2>Mesa redonda con patas cuadradas, Panel redondo, 1</h2>
                    -->
                <!-- Ejemplo de datos presentados al usuario -->
                <br><br>
            </form>
            
            <form action="fabrica_formulario.jsp" method="post" autocomplete="on">
                <input type="submit" id="fabrica_ensamblarpiezas_resetear" name="fabrica_ensamblarpiezas_resetear" value="Resetear">
                <br><br>
            </form>
        
        </div>
                        
        <!-- /////////////////////////////////////////////////////-->
        <!-- Ensamblar mueble -->
        <!-- /////////////////////////////////////////////////////-->
        
        <div class="departamento">
        
            <h1>Ensamblar mueble</h1>

            <form action="fabrica_formulario_ensamblarMueble.jsp" method="post" autocomplete="on">
                <label for="fabrica_ensamblarMueble_muebles_disponibles">Muebles disponibles </label> 
                <!-- Llenar los valores con las piezas -->
                <select id="fabrica_ensamblarMueble_nombremueble" name="fabrica_ensamblarMueble_nombremueble" size="1" lang="es">
                        <% 
                            String mueblesDefinidosSelect = mainClass.mueblesDefinidosSelect();
                                    
                            out.print(mueblesDefinidosSelect);
                        %>
                        <!--
                        <option value="mueble0">-----</option>
                        -->
                        <!--
                            <option value="mueble1">mueble1</option>
                            <option value="mueble2">mueble2</option>
                            <option value="mueble3">mueble3</option>
                            <option value="mueble4">mueble4</option>
                        -->
                    </select>
                <!-- Llenar los valores con las piezas -->
                <br><br>
                <label for="fabrica_ensamblarMueble_usuarios_disponibles">Usuarios disponibles </label> 
                <!-- Llenar los valores con las piezas -->
                    <select id="fabrica_ensamblarMueble_usuario" name="fabrica_ensamblarMueble_usuario" size="1">
                        <% 
                            String usuariosFabricaDisponiblesSelect = mainClass.usuariosFabricaDisponiblesSelect();
                            
                            out.print(usuariosFabricaDisponiblesSelect);
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
                <!-- Llenar los valores con las piezas -->
                <br><br>
                <label for="fabrica_ensamblarMueble_fecha">Fecha </label>
                <input type="date" id="fabrica_ensamblarMueble_fecha" name="fabrica_ensamblarMueble_fecha" value="" requested>
                <br><br>
                <input type="submit" id="fabrica_ensamblarMueble_ensamblarMueble" name="fabrica_ensamblarMueble_ensamblarMueble" value="Ensamblar mueble">
                <br><br>
                <% 
                    String muebleNombre = request.getParameter("fabrica_ensamblarMueble_nombremueble");
                    String muebleUsuario = request.getParameter("fabrica_ensamblarMueble_usuario");
                    String muebleFecha = request.getParameter("fabrica_ensamblarMueble_fecha");
                    String fabricaEnsamblarMueble = mainClass.fabricaEnsamblarMueble(muebleNombre, muebleUsuario, muebleFecha);

                    out.print(fabricaEnsamblarMueble);
                %>
                <!-- Ejemplo de datos presentados al usuario -->
                    <!--
                        <h2>ID = 5</h2>
                        <h2>Nombre mueble = Mesa pequeña con talbero curvo</h2>
                        <h2>Usuario = abner</h2>
                        <h2>Fecha = 2021-08-24</h2>
                        <h2>Costo = 200.0</h2>
                        <h2>Precio = 250.0</h2>
                    -->
                <!-- Ejemplo de datos presentados al usuario -->
                <br><br>
            </form>
        
        </div>
        
        <!-- /////////////////////////////////////////////////////-->
        <!-- Consultar piezas o muebles -->
        <!-- /////////////////////////////////////////////////////-->
        
        <div class="departamento">
        
            <h1>Consultar piezas o muebles</h1>
            
            <!-- ///////////////////////////////// -->
            
            <h2>Piezas agotadas o cerca de agotarse, menores o iguales a 4 unidades</h2>
                
            <!-- Ejemplo de datos presentados al usuario -->
                <table style="width:100%" id="fabrica_tabla_piezas_agotadas">
                    <tr>
                        <th>Tipo</th>
                        <th>Costo</th>
                        <th>Cantidad</th>
                    </tr>
                    <% 
                        out.print(piezasPorAgotarse);
                    %>
                    <!--
                    <tr>
                        <td>Pieza1</td>
                        <td>10.0</td>
                        <td>50</td>
                    </tr>
                    <tr>
                        <td>Pieza2</td>
                        <td>20.0</td>
                        <td>94</td>
                    </tr>
                    -->
                </table>
            <!-- Ejemplo de datos presentados al usuario -->
            <br><br>
            
            <!-- Consultar piezas -->

            <h1>Consultar piezas ordenadas por la cantidad</h1>
            
            <form action="fabrica_formulario_ConsultarPiezas_ascendente.jsp" method="post" autocomplete="on">                
                <input type="submit" id="fabrica_consultarpiezas_consultarascendente" name="fabrica_consultarpiezas_consultarascendente" value="Consultar piezas en forma ascendente">
                <br><br>
            </form>
            
            <form action="fabrica_formulario_ConsultarPiezas_descendente.jsp" method="post" autocomplete="on">                
                <input type="submit" id="fabrica_consultarpiezas_consultardescendente" name="fabrica_consultarpiezas_consultardescendente" value="Consultar piezas en forma descendente">
                <br><br>
            </form>
                
            <!-- Ejemplo de datos presentados al usuario -->
                <table style="width:100%" id="fabrica_tabla_consultarpiezas">
                    <tr>
                        <th>Tipo</th>
                        <th>Costo</th>
                        <th>Cantidad</th>
                    </tr>
                    <!--
                    <tr>
                        <td>Pieza1</td>
                        <td>10.0</td>
                        <td>50</td>
                    </tr>
                    <tr>
                        <td>Pieza2</td>
                        <td>20.0</td>
                        <td>94</td>
                    </tr>
                    -->
                </table>
            <!-- Ejemplo de datos presentados al usuario -->
            <br><br>
            
            <h1>Consultar muebles ordenados por fecha</h1>
            
            <form action="fabrica_formulario_ConsultarMuebles_ascendente.jsp" method="post" autocomplete="on">                
                <input type="submit" id="fabrica_consultarmuebles_consultarascendente" name="fabrica_consultarmuebles_consultarascendente" value="Consultar muebles en forma ascendente">
                <br><br>
            </form>
            
            <form action="fabrica_formulario_ConsultarMuebles_descendente.jsp" method="post" autocomplete="on">                
                <input type="submit" id="fabrica_consultarmuebles_consultardescendente" name="fabrica_consultarmuebles_consultardescendente" value="Consultar muebles en forma descendente">
                <br><br>
            </form>
                
            <!-- Ejemplo de datos presentados al usuario -->
                <table style="width:100%" id="fabrica_tabla_consultarmuebles">
                    <tr>
                        <th>ID mueble</th>
                        <th>Nombre mueble</th>
                        <th>Usuario</th>
                        <th>Fecha</th>
                        <th>Costo</th>
                        <th>Precio</th>
                    </tr>
                    <!--
                    <tr>
                        <td>ID_ASDASDASD</td>
                        <td>Nombre mueble 1</td>
                        <td>Usuario1</td>
                        <td>24/08/2021</td>
                        <td>150.0</td>
                        <td>200.0</td>
                    </tr>
                    <tr>
                        <td>ID_BFBFBFBSD</td>
                        <td>Nombre mueble 2</td>
                        <td>Usuario2</td>
                        <td>23/09/2021</td>
                        <td>130.0</td>
                        <td>160.0</td>
                    </tr>
                    -->
                </table>
            <!-- Ejemplo de datos presentados al usuario -->
            <br><br>
        
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
        <a href="fabrica_formulario.jsp">Fábrica</a>
        
        </div>
    </body>
</html>
