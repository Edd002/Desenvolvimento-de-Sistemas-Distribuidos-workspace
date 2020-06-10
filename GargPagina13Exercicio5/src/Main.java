import java.util.List;
import java.util.LinkedList;

public class Main {

	public static SearchLeft searchLeft;
	public static SearchRight searchRight;

	public static void main(String[] args) {
		List<Double> doubleLinkedList = generateArray();
		double elementSearched = 500.0;

		searchLeft = new SearchLeft(elementSearched, doubleLinkedList);
		searchRight = new SearchRight(elementSearched, doubleLinkedList);
		searchLeft.setSearchRight(searchRight);
		searchRight.setSearchLeft(searchLeft);

		searchLeft.start();
		searchRight.start();

		System.out.println("Linked List: " + doubleLinkedList);
		System.out.println("Element left? " + searchLeft.isFinded());
		System.out.println("Element right? " + searchRight.isFinded());

		if (searchLeft.isFinded() || searchRight.isFinded())
			System.out.println("O elemento " + elementSearched + " foi encontrado na lista.");
		else
			System.out.println("O elemento " + elementSearched + " não foi encontrado na lista.");
	}

	private static List<Double> generateArray() {
		List<Double> doubleLinkedList = new LinkedList<>();
		double element = 0.0;

		for (int i = 0; i <= 1000; i++)
			doubleLinkedList.add(element++);

		return doubleLinkedList;
	}
}