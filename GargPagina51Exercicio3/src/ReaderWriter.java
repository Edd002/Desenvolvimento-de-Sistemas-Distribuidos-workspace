import java.util.concurrent.Semaphore;

public class ReaderWriter {

	private volatile int readCount = 0;
	private volatile int writeCount = 0;
	private Semaphore readLock = new Semaphore(1);
	private Semaphore writeLock = new Semaphore(1);
	private Semaphore readMutex = new Semaphore(1);
	private Semaphore writeMutex = new Semaphore(1);

	public void startRead() throws InterruptedException {
		readLock.acquire();

		readMutex.acquire();
		readCount++;
		if (readCount == 1)
			writeLock.acquire();
		readMutex.release();

		readLock.release();
	}

	public void endRead() throws InterruptedException {
		readMutex.acquire();
		readCount--;
		if(readCount == 0)
			writeLock.release();
		readMutex.release();
	}

	public void startWrite() throws InterruptedException {
		writeMutex.acquire();
		writeCount++;
		if (writeCount == 1)
			readLock.acquire();
		writeMutex.release();

		writeLock.acquire();
	}

	public void endWrite() throws InterruptedException {
		writeLock.release();

		writeMutex.acquire();
		writeCount--;
		if (writeCount == 0)
			readLock.release();
		writeMutex.release();
	}
}