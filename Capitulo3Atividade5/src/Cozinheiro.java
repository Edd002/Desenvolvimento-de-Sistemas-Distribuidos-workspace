public class Cozinheiro implements Runnable {

	private PanelaMonitor panela;

	public Cozinheiro(PanelaMonitor panela) {
		this.panela = panela;
	}

	@Override
	public void run() {
		try {
			while (true) {
				panela.encherPanela();
				Thread.sleep(1000);
				System.out.println("A panela foi cheia...");
			}
		} catch (InterruptedException interruptedException) {
		}
	}
}