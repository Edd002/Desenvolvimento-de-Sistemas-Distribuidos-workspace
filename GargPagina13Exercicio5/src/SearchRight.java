import java.util.List;

public class SearchRight extends Thread {

	private double elementSearched;
	private List<Double> doubleLinkedList;
	private boolean finded;
	private SearchLeft searchLeft;

	public boolean isFinded() {
		return finded;
	}

	public void setSearchLeft(SearchLeft searchLeft) {
		this.searchLeft = searchLeft;
	}

	public SearchRight(double elementSearched, List<Double> doubleLinkedList) {
		this.elementSearched = elementSearched;
		this.doubleLinkedList = doubleLinkedList;
	}

	@Override
	public void run() {
		for (int i = (doubleLinkedList.size() - 1); i > 0; i--) {
			if (this.isInterrupted())
				break;
			if (doubleLinkedList.get(i) == elementSearched) {
				this.finded = true;
				searchLeft.interrupt();
				break;
			}
		}
	}
}