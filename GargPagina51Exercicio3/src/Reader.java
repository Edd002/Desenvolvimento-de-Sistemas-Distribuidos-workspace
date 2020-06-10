public class Reader implements Runnable {

	private ReaderWriter readerWriter;

	public Reader(ReaderWriter readerWriter) {
		this.readerWriter = readerWriter;
	}

	@Override
	public void run() {
		try {
			readerWriter.startRead();

			// Critical Session
			System.out.println("Thread " + Thread.currentThread().getName() + " is READING");
			Thread.sleep(1500);
			System.out.println("Thread " + Thread.currentThread().getName() + " has FINISHED READING");

			readerWriter.endRead();
		} catch (InterruptedException interruptedException) {
			System.out.println(interruptedException.getMessage());
		}
	}
}