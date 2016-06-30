package view;

import java.awt.AWTException;
import java.awt.Image;
import java.awt.SystemTray;
import java.awt.Toolkit;
import java.awt.TrayIcon;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.net.Socket;
import java.sql.Date;
import java.util.List;

import modelo.entidade.Comando;
import modelo.entidade.Dispositivo;
import modelo.entidade.Historico;
import controle.BLLDispositivo;
import controle.BLLHistorico;

public class Servidor extends Thread {

	Socket conexao;
	boolean auth = false;
	Dispositivo dispositivo;

	static Image image = Toolkit.getDefaultToolkit().getImage("img/java.gif");

	static TrayIcon trayIcon = new TrayIcon(image, "dsiServer");

	/**
	 * @autor Douglas Moreira Barcellos
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

					// Faz a comparação de MAC Address, Pass e Status no banco de dados
					for(int i = 0; list.size() > i; i++){
						if(list.get(i).getPass() == dispositivoIni.getPass()
								&& list.get(i).getMac().equalsIgnoreCase(dispositivoIni.getMac()) 
								&& list.get(i).getStatus().equals("ATIVO")){
							this.dispositivo = list.get(i);
							this.auth = true;
						}

						// Ativa o SystemTray e exibe balão informando conexão
						if (SystemTray.isSupported() && this.auth) {
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
					if(comando.getParametros().equals("sair")) {
						conexao.close();
					}
					else {

						// Os dados do dispositivo e do comando são armazenados no histórico
						Historico historico = new Historico();
						historico.setDataComando(new Date(System.currentTimeMillis()));
						historico.setParametro(comando.getParametros());
						historico.setCodigoDispositivo(dispositivo.getCodigo());

						BLLHistorico bllHistorico = new BLLHistorico();
						bllHistorico.salvar(historico);

						// Esta linha se comunica com o terminal e envia os comandos
						Process proc = Runtime.getRuntime().exec(comando.getParametros());
						proc.waitFor();

						// Aqui a saída do terminal é lida
						BufferedReader reader =  
								new BufferedReader(new InputStreamReader(proc.getInputStream()));

						// Caminho do arquivo de logs, que recebe como nome o MAC Adress do dispositivo
						File file = new File("/home/douglas/logs/" + dispositivo.getMac() + ".log");

						// Se o arquivo ainda não existir, ele é criado nesse bloco
						if(!file.exists()) {
							new File("/home/douglas/logs/" + dispositivo.getMac() + ".log").createNewFile();
						}

						// As próximas linha preenchem o arquivo com os comandos e repostas do terminal
						FileWriter fw = new FileWriter(file, true);
						BufferedWriter writer = new BufferedWriter(fw);

						writer.write("Data: [" + historico.getDataComando() + "]\nComando: [" + comando.getParametros() + "]\n\n[INICIO da Leitura do Terminal]\n\n");

						String line = "";
						while((line = reader.readLine()) != null) {
							writer.write(line);
							writer.newLine();
						}

						writer.write("\n[FIM da Leitura do Terminal]\n =======================\\ DELIMITER \\======================= \n\n");

						writer.close();

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
