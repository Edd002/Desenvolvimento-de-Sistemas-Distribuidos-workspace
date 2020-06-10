public class Main {

	public static void main(String[] args) {
		BufferLetter bufferLetter = new BufferLetter();

		PrintLetter printP = new PrintLetter(bufferLetter, 'P');
		new Thread(printP).start();

		PrintLetter printQ = new PrintLetter(bufferLetter, 'Q');
		new Thread(printQ).start();

		PrintLetter printR = new PrintLetter(bufferLetter, 'R');
		new Thread(printR).start();
	}
}