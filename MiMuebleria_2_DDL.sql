CREATE SCHEMA MiMuebleria;
USE MiMuebleria;

CREATE TABLE `mimuebleria`.`usuario` (
  `Nombre` VARCHAR(100) NOT NULL DEFAULT '',
  `Password` VARCHAR(45) NULL DEFAULT '',
  `Tipo` VARCHAR(1) NULL DEFAULT '',
  `Bloqueado` VARCHAR(45) NULL DEFAULT 'falso',
  PRIMARY KEY (`Nombre`))
  DEFAULT CHARACTER SET = utf8;

CREATE TABLE `mimuebleria`.`ensamblarmuebles` (
  `IdentificadorMueble` VARCHAR(45) NOT NULL DEFAULT '',
  `NombreMueble` VARCHAR(100) NULL DEFAULT '',
  `Usuario` VARCHAR(45) NULL DEFAULT '',
  `Fecha` VARCHAR(10) NULL DEFAULT '',
  `Costo` VARCHAR(12) NULL DEFAULT '0.0',
  `Precio` VARCHAR(12) NULL DEFAULT '0.0',
  PRIMARY KEY (`IdentificadorMueble`))
  DEFAULT CHARACTER SET = utf8;

CREATE TABLE `mimuebleria`.`ensamblepiezas` (
  `NombreMueble` VARCHAR(100) NULL DEFAULT '',
  `Pieza` VARCHAR(100) NULL DEFAULT '',
  `Cantidad` VARCHAR(9) NULL DEFAULT '0')
  DEFAULT CHARACTER SET = utf8;

CREATE TABLE `mimuebleria`.`pieza` (
  `Tipo` VARCHAR(100) NULL DEFAULT '',
  `Costo` VARCHAR(12) NULL DEFAULT '0.0',
  `Cantidad` VARCHAR(9) NULL DEFAULT '0')
  DEFAULT CHARACTER SET = utf8;

CREATE TABLE `mimuebleria`.`cliente` (
  `NITCliente` VARCHAR(45) NOT NULL DEFAULT '',
  `NombreCliente` VARCHAR(100) NULL DEFAULT '',
  `DireccionCliente` VARCHAR(100) NULL DEFAULT '',
  `Municipio` VARCHAR(100) NULL DEFAULT '',
  `Departamento` VARCHAR(100) NULL DEFAULT '',
  PRIMARY KEY (`NITCliente`))
  DEFAULT CHARACTER SET = utf8;

CREATE TABLE `mimuebleria`.`mueblesvendidos` (
  `IdentificadorMueble` VARCHAR(45) NOT NULL DEFAULT '',
  `NombreMueble` VARCHAR(100) NULL DEFAULT '',
  `Usuario` VARCHAR(45) NULL DEFAULT '',
  `Costo` VARCHAR(12) NULL DEFAULT '0.0',
  `Precio` VARCHAR(12) NULL DEFAULT '0.0',
  `Cantidad` VARCHAR(9) NULL DEFAULT '0',
  `Ganancia` VARCHAR(12) NULL DEFAULT '0.0',
  `NombreCliente` VARCHAR(100) NULL DEFAULT '',
  `ApellidoCliente` VARCHAR(100) NULL DEFAULT '',
  `NITCliente` VARCHAR(45) NULL DEFAULT '',
  `FechaVenta` VARCHAR(10) NULL DEFAULT '',
  PRIMARY KEY (`IdentificadorMueble`))
  DEFAULT CHARACTER SET = utf8;

CREATE TABLE `mimuebleria`.`mueblesdevueltos` (
  `IdentificadorMueble` VARCHAR(45) NOT NULL DEFAULT '',
  `NombreMueble` VARCHAR(100) NULL DEFAULT '',
  `Cantidad` VARCHAR(9) NULL DEFAULT '0',
  `NombreCliente` VARCHAR(100) NULL DEFAULT '',
  `ApellidoCliente` VARCHAR(100) NULL DEFAULT '',
  `NITCliente` VARCHAR(45) NULL DEFAULT '',
  `FechaDevolucion` VARCHAR(10) NULL DEFAULT '',
  `Perdida` VARCHAR(12) NULL DEFAULT '0.0',
  PRIMARY KEY (`IdentificadorMueble`))
  DEFAULT CHARACTER SET = utf8;