package modelo.entidade;

import java.io.Serializable;

public class Comando implements Serializable {
	private Dispositivo dispositivo;
	private String parametros;
	
	public Comando(Dispositivo dispositivo){
		this.setDispositivo(dispositivo);
	}

	public String getParametros() {
		return parametros;
	}

	public void setParametros(String parametros) {
		this.parametros = parametros;
	}

	public Dispositivo getDispositivo() {
		return dispositivo;
	}

	public void setDispositivo(Dispositivo dispositivo) {
		this.dispositivo = dispositivo;
	}
}
