import java.util.concurrent.Semaphore;

public class BufferLetter {
	private volatile int contPQ = 0;
	private volatile int contR = 0;
	private final Semaphore mutex = new Semaphore(1);
	private final Semaphore letterCondition = new Semaphore(1);
	
	public BufferLetter() {
	}

	public void requestPrintP() throws InterruptedException {
		mutex.acquire();
		contPQ++;
		letterCondition.release();
		mutex.release();
	}

	public void requestPrintQ() throws InterruptedException {
		mutex.acquire();
		contPQ++;
		letterCondition.release();
		mutex.release();
	}

	public void requestPrintR() throws InterruptedException {
		mutex.acquire();
		contR++;
		if (contR >= contPQ) letterCondition.acquire();
		mutex.release();
	}
}