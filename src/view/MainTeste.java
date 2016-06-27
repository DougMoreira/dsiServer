package view;

public class MainTeste {

	/**
	 * @param args
	 * @throws InterruptedException 
	 */
	public static void main(String[] args) throws InterruptedException {
		new ServidorEspera().start();
		
		while(true){
		System.out.println("oi");
		
		Thread.sleep(10000);
		}
	}

}
