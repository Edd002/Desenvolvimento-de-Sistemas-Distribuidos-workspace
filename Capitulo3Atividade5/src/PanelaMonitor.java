public class PanelaMonitor {

	private final int NUM_PORCOES = 5;
	private volatile int M = NUM_PORCOES;

	public PanelaMonitor() {
	}

	public synchronized void servirPorcao() throws InterruptedException {
		while (M < 0) {
			notifyAll(); // Notificar cozinheiro para encher a panela
			this.wait(); // Aguarda a panela encher
		}
		M--; // Retirar uma porção
	}

	public synchronized void encherPanela() throws InterruptedException {
		this.wait(); // Cozinheiro dormindo
		M = NUM_PORCOES; // Encher panela quando notificado (acordado)
		notifyAll(); // Avisar selvagens que a panela está cheia
	}
}