/*Create Database miingresohitss */

CREATE DATABASE IF NOT EXISTS miingresohitss;

USE miingresohitss;

/*Table structure for table rol */

DROP TABLE IF EXISTS rol;

CREATE TABLE rol (
  id_rol INT(11) NOT NULL AUTO_INCREMENT,
  rol VARCHAR(15) NOT NULL,
  PRIMARY KEY (id_rol)
) ENGINE=INNODB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4;

/*Data for the table rol */

INSERT INTO rol(id_rol,rol) VALUES
(1,'Administrador'),
(2,'Cliente');

/*Table structure for table persona */

DROP TABLE IF EXISTS persona;

CREATE TABLE persona (
  id_persona INT(11) NOT NULL AUTO_INCREMENT,
  nombres VARCHAR(55) NOT NULL,
  apellidos VARCHAR(55) DEFAULT NULL,
  identificacion VARCHAR(15) NOT NULL,
  sexo VARCHAR(2) NOT NULL,
  estado VARCHAR(2) NOT NULL,
  PRIMARY KEY (id_persona)
) ENGINE=INNODB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4;

/*Data for the table persona */

INSERT INTO persona(id_persona,nombres,apellidos,identificacion,sexo,estado) VALUES
(1,'admin','','1234567890','M','1');

/*Table structure for table usuario */

DROP TABLE IF EXISTS usuario;

CREATE TABLE usuario (
  id_persona INT(11) NOT NULL,
  usuario VARCHAR(55) NOT NULL,
  clave VARCHAR(55) NOT NULL,
  id_rol INT(11) NOT NULL,
  estado VARCHAR(2) NOT NULL,
  KEY id_persona (id_persona),
  KEY id_rol (id_rol),
  CONSTRAINT usuario_ibfk_1 FOREIGN KEY (id_persona) REFERENCES persona (id_persona),
  CONSTRAINT usuario_ibfk_2 FOREIGN KEY (id_rol) REFERENCES rol (id_rol)
) ENGINE=INNODB DEFAULT CHARSET=utf8mb4;

/*Data for the table usuario */

INSERT INTO usuario(id_persona,usuario,clave,id_rol,estado) VALUES
(1,'admin','admin',1,'1');

/*Table structure for table producto */

DROP TABLE IF EXISTS producto;

CREATE TABLE producto (
  id_producto INT(11) NOT NULL AUTO_INCREMENT,
  nombre VARCHAR(150) NOT NULL,
  precio DECIMAL(10,0) NOT NULL,
  PRIMARY KEY (id_producto)
) ENGINE=INNODB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4;

/*Data for the table producto */

/*Table structure for table compra */

DROP TABLE IF EXISTS compra;

CREATE TABLE compra (
  id_compra INT(11) NOT NULL AUTO_INCREMENT,
  id_persona INT(11) NOT NULL,
  fecha TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP() ON UPDATE CURRENT_TIMESTAMP(),
  valor_compra DECIMAL(10,0) NOT NULL,
  PRIMARY KEY (id_compra),
  KEY id_persona (id_persona),
  CONSTRAINT compra_ibfk_1 FOREIGN KEY (id_persona) REFERENCES persona (id_persona)
) ENGINE=INNODB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4;

/*Data for the table compra */

/*Table structure for table detalle_compra */

DROP TABLE IF EXISTS detalle_compra;

CREATE TABLE detalle_compra (
  id_compra INT(11) NOT NULL,
  id_producto INT(11) NOT NULL,
  cantidad INT(11) NOT NULL,
  KEY id_compra (id_compra),
  KEY id_producto (id_producto),
  CONSTRAINT detalle_compra_ibfk_1 FOREIGN KEY (id_compra) REFERENCES compra (id_compra),
  CONSTRAINT detalle_compra_ibfk_2 FOREIGN KEY (id_producto) REFERENCES producto (id_producto)
) ENGINE=INNODB DEFAULT CHARSET=utf8mb4;

/*Data for the table detalle_compra */