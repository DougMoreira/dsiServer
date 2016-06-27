package view;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ServidorEspera extends Thread {
	public void run(){
		try {
			ServerSocket s = new ServerSocket(9998);
			while (true) {
				System.out.print("Esperando alguem se conectar...");
				Socket conexao = s.accept();


				System.out.println("Conectou!");
				Thread t = new Servidor(conexao);
				t.start();
			}
		} catch (IOException e) {
			System.out.println("IOException: " + e);
		}
	}
	
	

}
