import java.util.concurrent.atomic.AtomicInteger;

public class PetersonLock implements Lock {

	// Apenas para duas threads
	/*
    private volatile boolean wantCS[] = {false, false};
    private volatile int turn = 1;

    public void requestCS(int i) {
        int j = 1 - i; 
        wantCS[i] = true; 
        turn = j;
        while (wantCS[j] && (turn == j));
    }

    public void releaseCS(int i) {
        wantCS[i] = false;
    }
	*/


	// Para várias threads
	private AtomicInteger[] FLAG;
	private AtomicInteger[] LAST;
	private int N;

	public PetersonLock(int N) {
		this.N = N;
		this.FLAG = new AtomicInteger[N];
		this.LAST = new AtomicInteger[N];

		for (int i = 0; i < N; i++) {
			this.FLAG[i] = new AtomicInteger(-1);
			this.LAST[i] = new AtomicInteger(-1);
		}
	}

	@Override
	public void requestCS(int pid) {
		for (int last = 0; last < this.N - 1; last++) {
			this.FLAG[pid].set(last);
			this.LAST[last].set(pid);
			boolean other_flags = true;
			while (other_flags && this.LAST[last].get() == pid) {
				other_flags = false;
				for (int k = 0; k < this.N; k++) {
					if (k == pid) continue;
					if (this.FLAG[k].get() >= last) {
						other_flags = true;
						break;
					}
				}
			}
		}
	}

	@Override
	public void releaseCS(int pid) {
		this.FLAG[pid].set(-1);
	}
}