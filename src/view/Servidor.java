package view;

import java.awt.AWTException;
import java.awt.Image;
import java.awt.SystemTray;
import java.awt.Toolkit;
import java.awt.TrayIcon;
import java.awt.event.ActionEvent;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.Date;
import java.util.List;

import controle.BLLDispositivo;
import controle.BLLHistorico;

import modelo.entidade.Comando;
import modelo.entidade.Dispositivo;
import modelo.entidade.Historico;

public class Servidor extends Thread {

	Socket conexao;
	boolean auth = false;
	Dispositivo dispositivo;

	static Image image = Toolkit.getDefaultToolkit().getImage("src/java.gif");

	static TrayIcon trayIcon = new TrayIcon(image, "dsiServer");

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

				// Se o objeto serializado for do tipo Dispositivo, esse bloco executará
				if(objeto.getClass().getSimpleName().equals("Dispositivo")){
					Dispositivo dispositivoIni = (Dispositivo) objeto;
					BLLDispositivo bllDispositivo = new BLLDispositivo();

					// Pega a lista de todos os dispositivos cadastrados no banco de dados
					List<Dispositivo> list = bllDispositivo.listar();

					// Faz a comparação de MAC Adress e Pass no banco de dados
					for(int i = 0; list.size() > i; i++){
						if(list.get(i).getPass() == dispositivoIni.getPass()
								&& list.get(i).getMac().equalsIgnoreCase(dispositivoIni.getMac()))
							this.dispositivo = list.get(i);
						this.auth = true;
						
						// Ativa o SystemTray e exibe balão informando conexão
						if (SystemTray.isSupported()) {
							SystemTray tray = SystemTray.getSystemTray();
							trayIcon.setImageAutoSize(true);
							try {
								tray.remove(trayIcon);
								tray.add(trayIcon);
								trayIcon.displayMessage("Conexão!", "Dispositivo " + list.get(i).getMac() + " conectado.", TrayIcon.MessageType.INFO);
								
							} catch (AWTException e) {
								System.err.println("TrayIcon could not be added.");
							}
						}
					}
				}
				
				// Verificação do objeto e de autenticação
				else if(objeto.getClass().getSimpleName().equals("Comando") && this.auth){
					Comando comando = (Comando) objeto;

					// Caso o parâmetro do comando seja "sair", a conexão é encerrada
					if(comando.getParametros().equals("sair")){
						conexao.close();
					}

					else{

						// Os dados do dispositivo e do comando são armazenados no histórico
						Historico historico = new Historico();
						historico.setDataComando(new Date(System.currentTimeMillis()));
						historico.setParametro(comando.getParametros());
						historico.setCodigoDispositivo(dispositivo.getCodigo());

						BLLHistorico bllHistorico = new BLLHistorico();
						bllHistorico.salvar(historico);

						// Esta linha se comunica com o terminal e envia os comandos
						Process proc = Runtime.getRuntime().exec(comando.getParametros());

						// Aqui a saída do terminal é lida
						BufferedReader reader =  
								new BufferedReader(new InputStreamReader(proc.getInputStream()));

						String line = "";
						while((line = reader.readLine()) != null) {
							System.out.print(line + "\n");
						}

						proc.waitFor(); 

					}
				}

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
