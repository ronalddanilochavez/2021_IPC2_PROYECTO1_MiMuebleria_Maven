<%@page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<!DOCTYPE html>

<html>
    <head>
        <title>Administración Formulario</title>
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
        <style>
            table, th, td {
              border: 1px solid black;
              color: black;
            }
        </style>
    </head>
    <body>
        <h1 style="background-color:burlywood;">Área Financiera y Administración</h1>
        
        <!-- /////////////////////////////////////////////////////-->
        <!-- Descripción de Administración-->
        <!-- /////////////////////////////////////////////////////-->
        
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
        
        <!-- Importando clases -->
        <%@ page import="myPackage.MainClass" %>
        <% 
            MainClass mainClass = new MainClass();
            String piezasDisponiblesSelect = mainClass.piezasDisponiblesSelect();
            String usuariosDisponiblesSelect = mainClass.usuariosDisponiblesSelect();
        %>
        
        <!-- /////////////////////////////////////////////////////-->
        <!-- Consulta de ventas en un intervalo de tiempo -->
        <!-- /////////////////////////////////////////////////////-->
        
        <div class="departamento">
            
            <h1>Consulta de ventas en un intervalo de tiempo</h1>
            
            <form action="administracion_formulario_consultVentas_intervaloTiempo.jsp" method="post" autocomplete="on">
                <label for="fname"> Fecha inicial </label>
                <input type="date" id="administracion_consultaventas_fechainicial" name="administracion_consultaventas_fechainicial" value="">
                <label for="fname"> Fecha final </label>
                <input type="date" id="administracion_consultaventas_fechafinal" name="administracion_consultaventas_fechafinal" value="">
                <br><br>
                <input type="submit" value="Consulta de ventas">
                <br><br>
                <!-- Llenar con datos -->
                    <table style="width:100%" id="administracion_tabla_consultaventas">
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
            
            <!--
            <form action="administracion_formulario_consultVentas_intervaloTiempo_CSV.jsp" method="post" autocomplete="on">
                <input type="submit" id="administracion_consultaventas_exportar" name="administracion_consultaventas_exportar" value="Exportar archivo CSV">
                <br><br>
            </form>
            -->
        
        </div>
        
        <!-- /////////////////////////////////////////////////////-->
        <!-- Consulta de devoluciones en un intervalo de tiempo -->
        <!-- /////////////////////////////////////////////////////-->
        
        <div class="departamento">
            
            <h1>Consulta de devoluciones en un intervalo de tiempo</h1>
            
            <form action="administracion_formulario_consultDevoluciones_intervaloTiempo.jsp" method="post" autocomplete="on">
                <label for="fname"> Fecha inicial </label>
                <input type="date" id="administracion_consultadevoluciones_fechainicial" name="administracion_consultadevoluciones_fechainicial" value="">
                <label for="fname"> Fecha final </label>
                <input type="date" id="administracion_consultadevoluciones_fechafinal" name="administracion_consultadevoluciones_fechafinal" value="">
                <br><br>
                <input type="submit" value="Consulta de devoluciones">
                <br><br>
                <!-- Llenar con datos -->
                    <table style="width:100%" id="administracion_tabla_consultadevoluciones">
                        <tr>
                            <th>ID mueble</th>
                            <th>Nombre mueble</th>
                            <th>Cantidad</th>
                            <th>Nombre Cliente</th>
                            <th>NIT Cliente</th>
                            <th>Fecha devolución</th>
                            <th>Pérdida</th>
                        </tr>
                        <!--
                        <tr>
                            <td>ALSDHLAKJHSD</td>
                            <td>Mesa ovalada</td>
                            <td>1</td>
                            <td>Manuel Gómez</td>
                            <td>321654987</td>
                            <td>25/02/2021</td>
                            <td>33.3333</td>
                        </tr>
                        -->
                    </table>
                <!-- Llenar con datos -->
                <br><br>
            </form>
            
            <!--
            <form action="administracion_formulario_consultDevoluciones_intervaloTiempo_CSV.jsp" method="post" autocomplete="on">
                <input type="submit" id="administracion_consultadevoluciones_exportar" name="administracion_consultadevoluciones_exportar" value="Exportar archivo CSV">
                <br><br>
            </form>
            -->
        
        </div>
        
        <!-- /////////////////////////////////////////////////////-->
        <!-- Consulta de ganancias menos pérdidas en un intervalo de tiempo -->
        <!-- /////////////////////////////////////////////////////-->
        
        <div class="departamento">
            
            <h1>Consulta de ganancias menos pérdidas en un intervalo de tiempo</h1>
            
            <form action="administracion_formulario_consultGanancias_intervaloTiempo.jsp" method="post" autocomplete="on">
                <label for="fname"> Fecha inicial </label>
                <input type="date" id="administracion_consultaganancias_fechainicial" name="administracion_consultaganancias_fechainicial" value="">
                <label for="fname"> Fecha final </label>
                <input type="date" id="administracion_consultaganancias_fechafinal" name="administracion_consultaganancias_fechafinal" value="">
                <br><br>
                <input type="submit" value="Consulta de ganancias">
                <br><br>
                <!-- Llenar con datos -->
                    <!--
                        <h2>Las ganancias totales son de Q. 5000.0</h2>
                    -->
                <!-- Llenar con datos -->
                
                <!-- Llenar con datos -->
                    <table style="width:100%" id="administracion_tabla_consultaganacias">
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
            
            <!--
            <form action="administracion_formulario_consultGanancias_intervaloTiempo_CSV.jsp" method="post" autocomplete="on">
                <input type="submit" id="administracion_consultaganancias_exportar" name="administracion_consultaganancias_exportar" value="Exportar archivo CSV">
                <br><br>
            </form>
            -->
        
        </div>
        
        <!-- /////////////////////////////////////////////////////-->
        <!-- Consulta de usuario con más ventas en un intervalo de tiempo -->
        <!-- /////////////////////////////////////////////////////-->
        
        <div class="departamento">
            
            <h1>Consulta de usuario con más ventas en un intervalo de tiempo</h1>
            
            <form action="administracion_formulario_consultaUsuarioMasVentas_intervaloTiempo.jsp" method="post" autocomplete="on">
                <label for="fname"> Fecha inicial </label>
                <input type="date" id="administracion_consultausuarioventas_fechainicial" name="administracion_consultausuarioventas_fechainicial" value="">
                <label for="fname"> Fecha final </label>
                <input type="date" id="administracion_consultausuarioventas_fechafinal" name="administracion_consultausuarioventas_fechafinal" value="">
                <br><br>
                <input type="submit" value="Consulta de usuario con más ventas">
                <br><br>
                <!-- Llenar con datos -->
                    <!--
                        <h2>Las ventas totales son de 21 unidades</h2>
                    -->
                <!-- Llenar con datos -->
                
                <!-- Llenar con datos -->
                    <table style="width:100%" id="administracion_tabla_consultausuarioventas">
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
            
            <!--
            <form action="administracion_formulario_consultaUsuarioMasVentas_intervaloTiempo_CSV.jsp" method="post" autocomplete="on">
                <input type="submit" id="administracion_consultausuarioventas_exportar" name="administracion_consultausuarioventas_exportar" value="Exportar archivo CSV">
                <br><br>
            </form>
            -->
        
        </div>
        
        <!-- /////////////////////////////////////////////////////-->
        <!-- Consulta de usuario con más ganancias en un intervalo de tiempo -->
        <!-- /////////////////////////////////////////////////////-->
        
        <div class="departamento">
            
            <h1>Consulta de usuario con más ganancias en un intervalo de tiempo</h1>
            
            <form action="administracion_formulario_consultaUsuarioMasGanancias_intervaloTiempo.jsp" method="post" autocomplete="on">
                <label for="fname"> Fecha inicial </label>
                <input type="date" id="administracion_consultausuarioganancias_fechainicial" name="administracion_consultausuarioganancias_fechainicial" value="">
                <label for="fname"> Fecha final </label>
                <input type="date" id="administracion_consultausuarioganancias_fechafinal" name="administracion_consultausuarioganancias_fechafinal" value="">
                <br><br>
                <input type="submit" value="Consulta de usuario con más ganancias">
                <br><br>
                <!-- Llenar con datos -->
                    <!--
                        <h2>Las ganancias totales son de Q. 5000.0</h2>
                    -->
                <!-- Llenar con datos -->
                
                <!-- Llenar con datos -->
                    <table style="width:100%" id="administracion_tabla_consultausuarioganacias">
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
            
            <!--
            <form action="administracion_formulario_consultaUsuarioMasGanancias_intervaloTiempo_CSV.jsp" method="post" autocomplete="on">
                <input type="submit" id="administracion_consultausuarioganancias_exportar" name="administracion_consultausuarioganancias_exportar" value="Exportar archivo CSV">
                <br><br>
            </form>
            -->
        
        </div>
        
        <!-- /////////////////////////////////////////////////////-->
        <!-- Consulta de mueble con más ventas en un intervalo de tiempo -->
        <!-- /////////////////////////////////////////////////////-->
        
        <div class="departamento">
            
            <h1>Consulta de mueble con más ventas en un intervalo de tiempo</h1>
            
            <form action="administracion_formulario_consultaMueblesMasVentas_intervaloTiempo.jsp" method="post" autocomplete="on">
                <label for="fname"> Fecha inicial </label>
                <input type="date" id="administracion_consultamuebleventas_fechainicial" name="administracion_consultamuebleventas_fechainicial" value="">
                <label for="fname"> Fecha final </label>
                <input type="date" id="administracion_consultamuebleventas_fechafinal" name="administracion_consultamuebleventas_fechafinal" value="">
                <br><br>
                <input type="submit" value="Consulta de mueble con más ventas">
                <br><br>
                <!-- Llenar con datos -->
                    <!--
                        <h2>Las ventas totales son de 25 unidades</h2>
                    -->
                <!-- Llenar con datos -->
                
                <!-- Llenar con datos -->
                    <table style="width:100%" id="administracion_tabla_consultamuebleventas">
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
            
            <!--
            <form action="administracion_formulario_consultaMueblesMasVentas_intervaloTiempo_CSV.jsp" method="post" autocomplete="on">
                <input type="submit" id="administracion_consultamuebleventas_exportar" name="administracion_consultamuebleventas_exportar" value="Exportar archivo CSV">
                <br><br>
            </form>
            -->
        
        </div>
        
        <!-- /////////////////////////////////////////////////////-->
        <!-- Consulta de mueble con menos ventas en un intervalo de tiempo -->
        <!-- /////////////////////////////////////////////////////-->
        
        <div class="departamento">
            
            <h1>Consulta de mueble con menos ventas en un intervalo de tiempo</h1>
            
            <form action="administracion_formulario_consultaMueblesMenosVentas_intervaloTiempo.jsp" method="post" autocomplete="on">
                <label for="fname"> Fecha inicial </label>
                <input type="date" id="administracion_consultamueblemenosventas_fechainicial" name="administracion_consultamueblemenosventas_fechainicial" value="">
                <label for="fname"> Fecha final </label>
                <input type="date" id="administracion_consultamueblemenosventas_fechafinal" name="administracion_consultamueblemenosventas_fechafinal" value="">
                <br><br>
                <input type="submit" value="Consulta de mueble con menos ventas">
                <br><br>
                <!-- Llenar con datos -->
                    <!--
                        <h2>Las ventas totales son de 5 unidades</h2>
                    -->
                <!-- Llenar con datos -->
                
                <!-- Llenar con datos -->
                    <table style="width:100%" id="administracion_tabla_consultamueblemenosventas">
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
            
            <!--
            <form action="administracion_formulario_consultaMueblesMenosVentas_intervaloTiempo_CSV.jsp" method="post" autocomplete="on">
                <input type="submit" id="administracion_consultamueblemenosventas_exportar" name="administracion_consultamueblemenosventas_exportar" value="Exportar archivo CSV">
                <br><br>
            </form>
            -->
        
        </div>
        
        <!-- /////////////////////////////////////////////////////-->
        <!-- Definir mueble a  partir del ensamble de piezas -->
        <!-- /////////////////////////////////////////////////////-->
        
        <div class="departamento">
        
            <h1>Definir mueble a  partir del ensamble de piezas</h1>

            <form action="administracion_formulario_ensamblarpiezas.jsp" method="post" autocomplete="on">
                <label for="administracion_ensamblarpiezas_nombremueble">Nombre mueble </label>
                <input type="text" id="administracion_ensamblarpiezas_nombremueble" name="administracion_ensamblarpiezas_nombremueble" value="" required>
                <br><br>
                <label for="administracion_piezas_disponibles">Pieza </label> 
                <!-- Llenar los valores con las piezas -->
                    <select id="administracion_ensamblarpiezas_pieza" name="administracion_ensamblarpiezas_pieza" size="1">
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
                <label for="administracion_ensamblarpiezas_cantidad">Cantidad </label>
                <input type="text" id="administracion_ensamblarpiezas_cantidad" name="administracion_ensamblarpiezas_cantidad" value="" required>
                <br><br>
                <input type="submit" id="administracion_ensamblarmueble_agregarpieza" name="administracion_ensamblarmueble_agregarpieza" value="Agregar pieza">
                
                <!-- Ejemplo de datos presentados al usuario -->
                    <!--
                        <p>Mesa redonda con patas cuadradas, Pata cuadrada, 4</p>
                        <p>Mesa redonda con patas cuadradas, Panel redondo, 1</p>
                    -->
                <!-- Ejemplo de datos presentados al usuario -->
                <br><br>
            </form>
            
            <form action="administracion_formulario.jsp" method="post" autocomplete="on">
                <input type="submit" id="administracion_ensamblarmueble_resetear" name="fabrica_ensamblarmueble_resetear" value="Resetear">
                <br><br>
            </form>
        
        </div>
        
        <!-- /////////////////////////////////////////////////////-->
        <!-- Crear, cancelar o borrar usuarios -->
        <!-- /////////////////////////////////////////////////////-->
        
        <div class="departamento">
            
            <!-- Crear usuario -->
        
            <h1>Crear usuario </h1>

            <form action="administracion_formulario_crearUsuario.jsp" method="post" autocomplete="on">
                <label for="fname">Usuario </label>
                <input type="text" id="administracion_usuario_crear" name="administracion_usuario_crear" value="" required>
                <br><br>
                <label for="fname">Password </label>
                <input type="text" id="administracion_password_crear" name="administracion_password_crear" value="" required>
                <br><br>
                <label for="fname">Área </label>
                <select id="administracion_piezas_elegir" name="administracion_tipo_crear" size="1">
                    <option value="Fabrica">Fabrica</option>
                    <option value="Ventas">Ventas</option>
                    <option value="Administracion">Administracion</option>
                </select>  
                <br><br>
                <input type="submit" value="Crear">
                <br><br>
                <% 
                    String usuarioNombre = request.getParameter("administracion_usuario_crear");
                    String usuarioPassword = request.getParameter("administracion_password_crear");
                    String usuarioTipo = request.getParameter("administracion_tipo_crear");
                    String administracionCrearUsuario = mainClass.administracionCrearUsuario(usuarioNombre, usuarioPassword, usuarioTipo);
                    
                    out.print(administracionCrearUsuario);
                %>
                <br><br>
            </form>
            
            <!-- Bloquear usuario -->
            
            <h1>Bloquear o desbloquear usuario </h1>
            
            <form action="administracion_formulario_bloquearUsuario.jsp" method="post" autocomplete="on">
                <label for="administracion_usuarios_disponibles">Usuario </label> 
                <!-- Llenar los valores con las usuarios -->
                    <select id="administracion_piezas_elegir" name="administracion_usuarios_bloquear" size="1">
                        <% 
                            out.print(usuariosDisponiblesSelect);
                        %>
                        <!--
                        <option value="usuario0">------</option>
                        -->
                        <!--
                            <option value="usuario1">usuario1</option>
                            <option value="usuario2">usuario2</option>
                            <option value="usuario3">usuario3</option>
                            <option value="usuario4">usuario4</option>
                        -->
                    </select>               
                <!-- Llenar los valores con las usuarios -->
                <br><br>
                <input type="submit" value="Bloquear">
                <br><br>
            </form>
                        
            <form action="administracion_formulario_desbloquearUsuario.jsp" method="post" autocomplete="on">
                <label for="administracion_usuarios_disponibles">Usuario </label> 
                <!-- Llenar los valores con las usuarios -->
                    <select id="administracion_piezas_elegir" name="administracion_usuarios_desbloquear" size="1">
                        <% 
                            out.print(usuariosDisponiblesSelect);
                        %>
                        <!--
                        <option value="usuario0">------</option>
                        -->
                        <!--
                            <option value="usuario1">usuario1</option>
                            <option value="usuario2">usuario2</option>
                            <option value="usuario3">usuario3</option>
                            <option value="usuario4">usuario4</option>
                        -->
                    </select>               
                <!-- Llenar los valores con las usuarios -->
                <br><br>
                <input type="submit" value="Deesbloquear">
                <br><br>
            </form>
            
            <!-- Borrar usuario -->
            
            <h1>Borrar usuario </h1>
            
            <form action="administracion_formulario_borrarUsuario.jsp" method="post" autocomplete="on">
                <label for="administracion_usuarios_disponibles">Usuario </label> 
                <!-- Llenar los valores con las usuarios -->
                    <select id="administracion_piezas_elegir" name="administracion_usuarios_borrar" size="1">
                        <% 
                            out.print(usuariosDisponiblesSelect);
                        %>
                        <!--
                        <option value="usuario0">--------</option>
                        -->
                        <!--
                            <option value="usuario1">usuario1</option>
                            <option value="usuario2">usuario2</option>
                            <option value="usuario3">usuario3</option>
                            <option value="usuario4">usuario4</option>
                        -->
                    </select>               
                <!-- Llenar los valores con las usuarios -->
                <br><br>
                <input type="submit" value="Borrar">
                <br><br>
            </form>
            
        </div>
        
        <!-- /////////////////////////////////////////////////////-->
        <!-- Importar archivo de base de datos -->
        <!-- /////////////////////////////////////////////////////-->
        
        <div class="departamento">
            
            <h1>Importar archivo de base de datos </h1>
            
            <form action="administracion_formulario_importarArchivo.jsp" method="post" autocomplete="on">
                <input type="submit" id="administracion_datos_importar" name="administracion_datos_importar" value="Importar archivo de base de datos">
                <br><br>
            </form>
            <!-- Ejemplo de posibles errores -->
                <!--
                    <p>Error en línea 25, mal formato</p>
                    <p>Error en línea 31, mala fecha</p>
                -->
            <!-- Ejemplo de posibles errores -->
            
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
        <a href="administracion_formulario.jsp">Administración</a>
        
        </div>
    </body>
</html>
