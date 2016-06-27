package view;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import modelo.entidade.Dispositivo;

import controle.BLLDispositivo;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;


public class ViewPrincipal extends JFrame {

	private JPanel contentPane;
	private JTextField tfPesquisar;
	private int cont = 0;


	public static void main(String[] args) {
		ViewPrincipal frame = new ViewPrincipal();
		frame.setVisible(true);
	}


	public ViewPrincipal() {
		super("dsiServer");


		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 493, 157);
		contentPane = new JPanel();

		JPanel panelDispositivo = new JPanel();
		panelDispositivo.setBorder(BorderFactory.createTitledBorder("Dispositivo")); 

		JPanel panelComandos = new JPanel();
		panelComandos.setBorder(BorderFactory.createTitledBorder("Comandos"));

		contentPane.setLayout(new BorderLayout(0, 0));
		contentPane.add(panelDispositivo, BorderLayout.NORTH);
		contentPane.add(panelComandos, BorderLayout.SOUTH);
		//contentPane.add(new JLabel("foo"));

		//contentPane.setBorder(BorderFactory.createTitledBorder("Opa")); 
		setContentPane(contentPane);

		JButton btnAdicionarDispositivo = new JButton("Adicionar");
		btnAdicionarDispositivo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				boolean volta = true;
				JTextField tf = new JTextField();
				JPasswordField pf = new JPasswordField();
				int pass = 0;

				while(volta){

					int okCxxl = JOptionPane.showConfirmDialog(null, tf, "Insira o MAC Adress", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
					if(tf.getText().length() != 17)
						JOptionPane.showMessageDialog(null, "Insira o MAC corretamente. São 17 caracteres contando com dois pontos(:)");
					else
						volta = false;
				}
				volta = true;

				while(volta){
					try{
						int okCxl = JOptionPane.showConfirmDialog(null, pf, "Senha (apenas números)", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
						System.out.println(String.valueOf(pf.getPassword()));
						if(pf.getPassword().toString().length() == 0);
						else{
							volta = false;
							pass = Integer.parseInt(String.valueOf(pf.getPassword()));
						}
					}
					catch(Exception e){
						volta = true;
						JOptionPane.showMessageDialog(null, "Insira apenas números!");
					}
				}

				Dispositivo dispositivo = new Dispositivo(tf.getText(), pass);
				BLLDispositivo bllDispositivo = new BLLDispositivo();
				bllDispositivo.salvar(dispositivo);
				JOptionPane.showMessageDialog(null, "Dispositivo Cadastrado");

			}
		});
		panelDispositivo.add(btnAdicionarDispositivo);

		JButton btnRemoverDispositivo = new JButton("Remover");
		btnRemoverDispositivo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				JTextField tf = new JTextField();
				boolean volta = true;
				int id = 0;

				while(volta){
					try{
						int okCxxl = JOptionPane.showConfirmDialog(null, tf, "Insira a ID ", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
						id = Integer.parseInt(String.valueOf(tf.getText()));
						volta = false;
					}
					catch(Exception e){
						volta = true;
						JOptionPane.showMessageDialog(null, "Insira apenas números!");
					}
				}
				
				
				BLLDispositivo bllDispositivo = new BLLDispositivo();
				Dispositivo dispositivo = bllDispositivo.buscarPorCodigo(id);
				bllDispositivo.excluir(dispositivo);
				JOptionPane.showMessageDialog(null, "Dispositivo Removido");

			}
		});
		panelDispositivo.add(btnRemoverDispositivo);

		JButton btnListarDispositivos = new JButton("Listar Dispositivos");
		btnListarDispositivos.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				
				BLLDispositivo bllDispositivo = new BLLDispositivo();
				List<Dispositivo> list = bllDispositivo.listar();
				String listagem = "Lista de Dispositivos:\n\n";
				for(int i = 0; i < list.size(); i++){
					listagem += list.get(i).getCodigo() + " --- " + list.get(i).getMac() + "\n";
				}
				
				JOptionPane.showMessageDialog(null, listagem);

			}
		});
		panelDispositivo.add(btnListarDispositivos);

		JButton btnListarHistorico = new JButton("Listar Histórico");
		btnListarHistorico.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {


			}
		});
		panelComandos.add(btnListarHistorico);

	}




}
