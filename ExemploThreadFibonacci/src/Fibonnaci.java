public class Fibonnaci extends Thread {
	private int n;
	private int result;

	public int getResult() {
		return result;
	}

	public Fibonnaci(int n) {
		this.n = n;
	}

	@Override
	public void run() {
		if (n == 0 || n == 1) {
			result = 1;
		} else {
			Fibonnaci f1 = new Fibonnaci(n - 1);
			Fibonnaci f2 = new Fibonnaci(n - 2);
			f1.start();
			f2.start();
			try {
				f1.join();
				f2.join();
			} catch (InterruptedException interruptedException) {
				result = f1.getResult() + f2.getResult();
			}
		}
	}
}