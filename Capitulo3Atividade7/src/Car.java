public class Car implements Runnable {

	private int id;
	private Monitor carMon;

	public Car(int i, Monitor monitorIn) {
		id = i;
		this.carMon = monitorIn;
	}

	@Override
	public void run() {
		while(true) {
			carMon.passengerGetOn(id);
			try {
				Thread.sleep((int) (Math.random() * 2000));
			} catch (InterruptedException interruptedException){
			}
			carMon.passengerGetOff(id);
		}
	}
}