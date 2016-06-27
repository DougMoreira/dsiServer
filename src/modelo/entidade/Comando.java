package modelo.entidade;

import java.io.Serializable;

public class Comando implements Serializable {
	private String parametros;
	
	public Comando(){
	}

	public String getParametros() {
		return parametros;
	}

	public void setParametros(String parametros) {
		this.parametros = parametros;
	}

	
}
