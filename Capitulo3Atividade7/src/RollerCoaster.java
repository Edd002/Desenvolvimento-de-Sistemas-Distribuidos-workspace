public class RollerCoaster {

	public static int PASSENGER_NUM = 25;
	public static int CAR_NUM = 1;
	public static int SEAT_AVAIL = 4;

	public static void main(String[] args) {
		Monitor rcMon = new Monitor();
		Car theCar;
		Passenger aPassenger;
		Thread t1[] = new Thread[PASSENGER_NUM];
		Thread t2[] = new Thread[CAR_NUM];

		for (int i = 0; i < PASSENGER_NUM; i++) {
			aPassenger = new Passenger(i, rcMon);
			t1[i] = new Thread(aPassenger);
		}

		for (int i = 0; i < CAR_NUM; i++) {
			theCar = new Car(i, rcMon);
			t2[i] = new Thread(theCar);
		}

		for(int i = 0; i < PASSENGER_NUM; i++) {
			t1[i].start();
		}

		for(int i = 0; i < CAR_NUM; i++) {
			t2[i].start();
		}

		try {
			for (int i = 0; i < PASSENGER_NUM; i++) {
				t1[i].join();
			}
		} catch (InterruptedException interruptedException) {
			System.err.println("Thread de passageiro interrompida.");
		}

		try {
			for (int i = 0; i < CAR_NUM; i++) {
				t2[i].join();
			}
		} catch (InterruptedException interruptedException) {
			System.err.println("Thread de carro interrompida.");
		}

		System.out.println("O programa terminou normalmente.");
	}
}