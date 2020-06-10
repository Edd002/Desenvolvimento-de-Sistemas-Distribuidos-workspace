public class Passenger implements Runnable {

	private int id;
	private Monitor passengerMon;

	public Passenger(int i, Monitor monitorIn) {
		id = i;
		this.passengerMon = monitorIn;
	}

	@Override
	public void run() {
		for (int i = 0; i < 1; i++) {
			try {
				Thread.sleep((int) (Math.random() * 2000));
			} catch(InterruptedException interruptedException){
			}
			passengerMon.tryToGetOnCar(id);
		}
	}
}