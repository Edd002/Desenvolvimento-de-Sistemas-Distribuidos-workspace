import java.util.concurrent.*;

public class SleepingBarber extends Thread {

	public Semaphore customers = new Semaphore(0);
	public Semaphore barber = new Semaphore(0);
	public Semaphore accessSeats = new Semaphore(1);

	public final int CHAIRS = 5;
	public int numberOfFreeSeats = CHAIRS;

	@Override
	public void run() {
		Barber barber = new Barber(this); 
		barber.start();

		for (int i = 1; i < 16; i++) {
			Customer aCustomer = new Customer(this, i);
			aCustomer.start();
			try {
				sleep(2000);
			} catch (InterruptedException interruptedException) {
			}
		}
	}
}