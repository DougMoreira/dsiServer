package view;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;

import controle.BLLDispositivo;

import modelo.entidade.Comando;
import modelo.entidade.Dispositivo;

public class Servidor extends Thread {

	Socket conexao;
	boolean auth = false;

	/**
	 * @param args
	 */
	public Servidor(Socket s) {
		conexao = s;
	}

	public void run(){
		while(true){
			try {
				ObjectInputStream entradaCli = new ObjectInputStream(conexao.getInputStream());
				Object objeto = entradaCli.readObject();

				if(objeto.getClass().getSimpleName().equals("Dispositivo")){
					Dispositivo dispositivo = (Dispositivo) objeto;
					BLLDispositivo bllDispositivo = new BLLDispositivo();

					// Pega a lista de todos os dispositivos cadastrados no banco de dados
					List<Dispositivo> list = bllDispositivo.listar();

					// Faz a comparação de MAC Adress e Pass no banco de dados
					for(int i = 0; list.size() > i; i++){
						if(list.get(i).getPass() == dispositivo.getPass()
								&& list.get(i).getMac().equalsIgnoreCase(dispositivo.getMac()))
							auth = true;
					}

				}
				else if(objeto.getClass().getSimpleName().equals("Comando")){
					Comando comando = (Comando) objeto;

					Process proc = Runtime.getRuntime().exec(comando.getParametros());

					BufferedReader reader =  
							new BufferedReader(new InputStreamReader(proc.getInputStream()));

					String line = "";
					while((line = reader.readLine()) != null) {
						System.out.print(line + "\n");
					}

					proc.waitFor();   


				}
				conexao.close();
			} catch (IOException e) { 

			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

}
