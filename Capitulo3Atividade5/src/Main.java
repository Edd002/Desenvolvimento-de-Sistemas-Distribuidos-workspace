public class Main {

	public static void main(String[] args) {
		PanelaMonitor panela = new PanelaMonitor();
		Selvagem selvagem = new Selvagem(panela);
		Cozinheiro cozinheiro = new Cozinheiro(panela);

		Thread threadSelvagem = new Thread(selvagem);
		threadSelvagem.start();

		Thread threadCozinheiro = new Thread(cozinheiro);
		threadCozinheiro.start();
	}
}