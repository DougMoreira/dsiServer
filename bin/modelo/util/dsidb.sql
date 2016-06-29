CREATE TABLE dispositivo
(
cod_dispositivo int(10) NOT NULL AUTO_INCREMENT,
mac_dispositivo varchar(50) NOT NULL,
pass_dispositivo int(50) NOT NULL,
status varchar(50) NOT NULL,
PRIMARY KEY (cod_dispositivo)
);

CREATE TABLE historico
(
cod_historico int(10) NOT NULL AUTO_INCREMENT,
cod_dispositivo int(10) NOT NULL,
parametro varchar(150) NOT NULL,
data_comando datetime,
PRIMARY KEY (cod_historico),
FOREIGN KEY (cod_dispositivo) REFERENCES dispositivo(cod_dispositivo)
);