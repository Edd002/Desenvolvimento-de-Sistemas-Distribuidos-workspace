public class Main {

	public static void main(String[] args) throws Exception {
		ReaderWriter readerWriter = new ReaderWriter();
		Reader readear = new Reader(readerWriter);
		Writer writer = new Writer(readerWriter);

		Thread thread1 = new Thread(readear);
		thread1.setName("thread1");
		Thread thread2 = new Thread(readear);
		thread2.setName("thread2");
		Thread thread3 = new Thread(writer);
		thread3.setName("thread3");
		Thread thread4 = new Thread(readear);
		thread4.setName("thread4");

		thread1.start();
		thread3.start();
		thread2.start();
		thread4.start();
	}
}
