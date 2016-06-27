CREATE TABLE dispositivo
(
cod_dispositivo int(10) NOT NULL AUTO_INCREMENT,
mac_dispositivo varchar(50) NOT NULL,
pass_dispositivo int(50) NOT NULL,
PRIMARY KEY (cod_dispositivo),
FOREIGN KEY (P_Id) REFERENCES Persons(P_Id)
)

CREATE TABLE historico
(
cod_historio int(10) NOT NULL AUTO_INCREMENT,
cod_dispositivo varchar(50) NOT NULL,
parametro varchar(150) NOT NULL,
data_comando date,
PRIMARY KEY (cod_historio),
FOREIGN KEY (cod_dispositivo) REFERENCES dispositivo(cod_dispositivo)
)