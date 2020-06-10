import java.util.List;

public class Search extends Thread {

	private int x;
	private int[] ASplitted;
	private List<Search> listSearchThread;
	private int findedIndex = -1;

	public Search(int x) {
		this.x = x;
	}

	public void setASplitted(int[] aSplitted) {
		this.ASplitted = aSplitted;
	}

	public void setListSearchThread(List<Search> listSearchThread) {
		this.listSearchThread = listSearchThread;
	}

	public int getFindedIndex() {
		return findedIndex;
	}

	@Override
	public void run() {
		for (int i = 0; i < ASplitted.length; i++) {
			if (this.isInterrupted())
				break;
			if (ASplitted[i] == x) {
				findedIndex = i;
				for (Search searchThread : listSearchThread)
					searchThread.interrupt();
				break;
			}
		}
	}
}