public class Main {

	public static void main(String[] args) {
		System.out.println("Args: " + args[0]);

		Fibonnaci fibonnaci = new Fibonnaci(Integer.parseInt(args[0]));
		fibonnaci.start();
		try {
			fibonnaci.join();
		} catch (InterruptedException interruptedException) {
			System.out.println("Answer is " + fibonnaci.getResult());
		}
	}
}