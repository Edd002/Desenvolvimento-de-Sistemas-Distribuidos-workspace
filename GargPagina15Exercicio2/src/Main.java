import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Main {

	public static void main(String[] args) {
		int[] integerArray = generateArray(1000);
		int elementPos = parallelSearch(750, integerArray, 10);

		System.out.println("Array: " + Arrays.toString(integerArray));
		System.out.println("Posição do elemento (-1 se não encontrado): " + elementPos);
	}

	private static int[] generateArray(int tam) {
		int[] integerArray = new int[tam];

		for (int i = 0; i < tam; i++)
			integerArray[i] = i;

		return integerArray;
	}

	public static int parallelSearch(int x, int[] A, int numThreads) {
		List<Search> listSearchThread = new ArrayList<Search>();
		int tamInterval =  A.length / numThreads;
		int leftLim = 0;
		int rightLim = tamInterval;

		// Criação das threads (quantidade definida pelo usuário) para pesquisar o elemento colocando todas em um array para uma controlar a outra (mandar parar caso ache)
		for (int i = 0; i < numThreads; i++)
			listSearchThread.add(new Search(x));

		// Iniciar todas as threads com uma parte do array para cada
		for (Search searchThread : listSearchThread) {
			//System.out.println("L: " + leftLim);
			//System.out.println("R: " + rightLim);

			int[] ASplitted = Arrays.copyOfRange(A, leftLim, rightLim); // Dividir o array
			searchThread.setASplitted(ASplitted); // Parte do array para procurar o elemento
			searchThread.setListSearchThread(listSearchThread); // Array contendo todas as threads para uma controlar a outra

			searchThread.start();

			leftLim = rightLim;
			rightLim += tamInterval;
		}

		// Aguardar o resultado ser retornado por uma das threads
		for (Search searchThread : listSearchThread) {
			try {
				searchThread.join();
			} catch (InterruptedException interruptedException) { }
		}

		// Verificar se alguma das threads encontrou o elemento
		int contThread = 0;
		for (Search searchThread : listSearchThread) {
			if (searchThread.getFindedIndex() >= 0)
				return searchThread.getFindedIndex() + (tamInterval * contThread);
			contThread++;
		}

		return -1;
	}
}