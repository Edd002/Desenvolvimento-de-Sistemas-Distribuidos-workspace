public class PrintLetter implements Runnable {

	private final int NUM_PRINTS = 10;
	private static final char LETTER_P = 'P';
	private static final char LETTER_Q = 'Q';
	private static final char LETTER_R = 'R';
	private char letter;
	private BufferLetter bufferLetter;

	public PrintLetter(BufferLetter bufferLetter, char letter) {
		this.bufferLetter = bufferLetter;
		this.letter = Character.toUpperCase(letter);
	}

	@Override
	public void run() {
		int i = 0;
		while (i < NUM_PRINTS) {
			if (letter == LETTER_P) {
				try {
					bufferLetter.requestPrintP();
					System.out.println(i + " - " + LETTER_P);
				} catch (InterruptedException interruptedException) { }
			} else if (letter == LETTER_Q) {
				try {
					bufferLetter.requestPrintQ();
					System.out.println(i + " - " + LETTER_Q);
				} catch (InterruptedException interruptedException) { }
			} else if (letter == LETTER_R) {
				try {
					bufferLetter.requestPrintR();
					System.out.println(i + " - " + LETTER_R);
				} catch (InterruptedException interruptedException) { }
			}

			i++;
		}
	}
}