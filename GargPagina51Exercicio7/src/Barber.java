public class Barber extends Thread {

	private SleepingBarber barberShop;
	
	public Barber(SleepingBarber sleepingBarber) {
		this.barberShop = sleepingBarber;
	}

	@Override
	public void run() {
		while(true) {
			try {
				barberShop.customers.acquire();
				barberShop.accessSeats.release();
				barberShop.numberOfFreeSeats++;
				barberShop.barber.release();
				barberShop.accessSeats.release();
				this.cutHair();
			} catch (InterruptedException interruptedException) {
			}
		}
	}

	public void cutHair(){
		System.out.println("O barbeiro está cortando cabelo");
		try {
			sleep(5000);
		} catch (InterruptedException interruptedException) {
		}
	}
}       