public class Monitor {

	private int seatsAvailable = 0;
	boolean coasterLoadingPassengers = false;
	boolean passengersRiding = true;
	private Object notifyPassenger = new Object();
	private Object notifyCar = new Object();

	public void tryToGetOnCar(int i) {
		synchronized (notifyPassenger) {
			while (!seatAvailable()) {
				try {
					notifyPassenger.wait();
				} catch (InterruptedException e){}
			}
		}

		System.out.println("O passageiro "+ i + " entrou no carro no tempo: " + System.currentTimeMillis());
		synchronized (notifyCar) {
			notifyCar.notify();
		}
	}

	private synchronized boolean seatAvailable() {
		if ((seatsAvailable > 0)
				&& (seatsAvailable <= RollerCoaster.SEAT_AVAIL)
				&& (!passengersRiding)) {
			seatsAvailable--;
			return true;
		} else {
			return false;
		}
	}

	public void passengerGetOn(int i) {
		synchronized (notifyCar) {
			while (!carIsRunning()) {
				try {
					notifyCar.wait();
				} catch (InterruptedException e){}
			}
		}

		System.out.println("O carro está cheio e começou a andar no tempo: "+ System.currentTimeMillis());
		synchronized (notifyPassenger) {
			notifyPassenger.notifyAll();
		}
	}

	private synchronized boolean carIsRunning() {
		if (seatsAvailable == 0) {
			seatsAvailable = RollerCoaster.SEAT_AVAIL;
			coasterLoadingPassengers = true;
			passengersRiding = true;
			return true;
		} else {
			return false;
		}
	}

	public void passengerGetOff(int i) {
		synchronized (this) {
			passengersRiding = false;
			coasterLoadingPassengers = false;
		}
		synchronized(notifyPassenger) {
			notifyPassenger.notifyAll();
		}
	}
}