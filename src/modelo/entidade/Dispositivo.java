package modelo.entidade;

import java.io.Serializable;

public class Dispositivo implements Serializable {
	private int codigo;
	private String mac;
	private int pass;
	
	public Dispositivo(String mac, int pass){
		this.mac = mac;
		this.pass = pass;
	}
	
	public String getMac() {
		return mac;
	}
	public void setMac(String mac) {
		this.mac = mac;
	}
	public int getPass() {
		return pass;
	}
	public void setPass(int pass) {
		this.pass = pass;
	}
	public int getCodigo() {
		return codigo;
	}
	public void setCodigo(int codigo) {
		this.codigo = codigo;
	}
}
