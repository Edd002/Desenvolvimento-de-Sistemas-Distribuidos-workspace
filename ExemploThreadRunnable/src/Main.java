public class Main {

	public static void main(String[] args) {
		Iterate iterate = new Iterate();
		Thread thread = new Thread(iterate);

		thread.start();
		for (int i = 0; i < 10; i++)
			System.out.println("Thread principal");
	}
}