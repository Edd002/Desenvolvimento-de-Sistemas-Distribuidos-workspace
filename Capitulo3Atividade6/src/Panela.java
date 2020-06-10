import java.util.concurrent.Semaphore;

public class Panela {

	private final int NUM_PORCOES = 5;
	private volatile int M = NUM_PORCOES;
	private Semaphore mutex = new Semaphore(1);
	private Semaphore isEmpty = new Semaphore(0);
	private Semaphore notEmpty = new Semaphore(0);

	public Panela() {
	}

	public void servirPorcao() throws InterruptedException {
		while (M < 0) {
			notEmpty.release(); // Notificar cozinheiro para encher a panela
			isEmpty.acquire(); // Aguarda a panela encher
		}
		
		mutex.acquire();
		M--; // Retirar uma porção
		mutex.release();
	}

	public void encherPanela() throws InterruptedException {
		notEmpty.acquire(); // Cozinheiro dormindo

		mutex.acquire();
		M = NUM_PORCOES; // Encher panela quando notificado (acordado)
		mutex.release();

		isEmpty.release(); // Avisar selvagens que a panela está cheia
	}
}