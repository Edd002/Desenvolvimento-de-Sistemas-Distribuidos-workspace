public class Selvagem implements Runnable {

	private Panela panela;

	public Selvagem(Panela panela) {
		this.panela = panela;
	}

	@Override
	public void run() {
		try {
			while (true) {
				Thread.sleep(1000);
				System.out.println("Tentando pegar porção...");
				panela.servirPorcao();
			}
		} catch (InterruptedException interruptedException) {
		}
	}
}