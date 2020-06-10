public class Writer implements Runnable {

	private ReaderWriter readerWriter;

	public Writer(ReaderWriter readerWriter) {
		this.readerWriter = readerWriter;
	}

	@Override
	public void run() {
		try {
			readerWriter.startWrite();

			// Critical Session
			System.out.println("Thread " + Thread.currentThread().getName() + " is WRITING");
			Thread.sleep(2500);
			System.out.println("Thread " + Thread.currentThread().getName() + " has finished WRITING");

			readerWriter.endWrite();
		} catch (InterruptedException interruptedException) {
			System.out.println(interruptedException.getMessage());
		}
	}

}