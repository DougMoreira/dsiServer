package modelo.entidade;

import java.sql.Date;

public class Historico {
	private int codigo;
	private int codigoDispositivo;
	private String parametro;
	private Date dataComando;
	
	public Historico(){
		
	}

	public int getCodigo() {
		return codigo;
	}

	public void setCodigo(int codigo) {
		this.codigo = codigo;
	}

	public int getCodigoDispositivo() {
		return codigoDispositivo;
	}

	public void setCodigoDispositivo(int codigoDispositivo) {
		this.codigoDispositivo = codigoDispositivo;
	}

	public String getParametro() {
		return parametro;
	}

	public void setParametro(String parametro) {
		this.parametro = parametro;
	}

	public Date getDataComando() {
		return dataComando;
	}

	public void setDataComando(Date dataComando) {
		this.dataComando = dataComando;
	}
}
