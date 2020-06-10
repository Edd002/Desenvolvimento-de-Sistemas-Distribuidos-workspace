import java.util.Random;

public class MainLock extends Thread {
	private final int TAM = 5;
    private int myId;
    private Lock lock;
    private Random r = new Random();

    public MainLock() {
    }

    public MainLock(int id, Lock lock) {
        myId = id;
        this.lock = lock;
    }

    private void nonCriticalSection() {
        System.out.println(myId + " não está na CS");
        Util.mySleep(r.nextInt(1000));
    }

    private void CriticalSection() {
        System.out.println(myId + " entrou na CS *****");
        // critical section code
        Util.mySleep(r.nextInt(1000));
        System.out.println(myId + " saiu da CS *****");
    }

    @Override
    public void run() {
        for (int i = 0; i < TAM; i++) {
            lock.requestCS(myId);
            CriticalSection();
            lock.releaseCS(myId);
            nonCriticalSection();
        }
    }

    public static void main(String[] args) throws Exception {
        MainLock t[];
        int N = Integer.parseInt(/*args[0]*/"2"); // Número de threads, definir para um número arbitrário
        t = new MainLock[N];
        Lock petersonLock = new PetersonLock(N); //implementação desejada

        for (int i = 0; i < N; i++) {
            t[i] = new MainLock(i, petersonLock);
            t[i].start();
        }
    }
}
