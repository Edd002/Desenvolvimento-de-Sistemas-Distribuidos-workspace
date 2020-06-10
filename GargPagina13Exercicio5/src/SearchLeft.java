import java.util.List;

public class SearchLeft extends Thread {

	private double elementSearched;
	private List<Double> doubleLinkedList;
	private boolean finded;
	private SearchRight searchRight;

	public boolean isFinded() {
		return finded;
	}

	public void setSearchRight(SearchRight searchRight) {
		this.searchRight = searchRight;
	}

	public SearchLeft(double elementSearched, List<Double> doubleLinkedList) {
		this.elementSearched = elementSearched;
		this.doubleLinkedList = doubleLinkedList;
	}

	@Override
	public void run() {
		for (int i = 0; i < doubleLinkedList.size(); i++) {
			if (this.isInterrupted())
				break;
			if (doubleLinkedList.get(i) == elementSearched) {
				this.finded = true;
				searchRight.interrupt();
				break;
			}
		}
	}
}