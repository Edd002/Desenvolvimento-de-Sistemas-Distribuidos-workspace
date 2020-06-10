public class Customer extends Thread {

	private SleepingBarber barberShop;
	private int id;
	private boolean notCut = true;

	public Customer(SleepingBarber sleepingBarber, int id) {
		this.barberShop = sleepingBarber;
		this.id = id;
	}

	@Override
	public void run() {   
		while (notCut) { 
			try {
				barberShop.accessSeats.acquire();
				if (barberShop.numberOfFreeSeats > 0) {
					System.out.println("O cliente " + this.id + " acabou de se sentar.");
					barberShop.numberOfFreeSeats--;
					barberShop.customers.release();
					barberShop.accessSeats.release();  
					try {
						barberShop.barber.acquire();
						notCut = false;
						this.getHaircut();
					} catch (InterruptedException ex) {}
				}   
				else  {
					System.out.println("Não a assentos vazios. O cliente " + this.id + " deixou a barbearia.");
					barberShop.accessSeats.release();
					notCut = false;
				}
			}
			catch (InterruptedException ex) {}
		}
	}

	public void getHaircut(){
		System.out.println("O cliente " + this.id + " está cortando cabelo.");
		try {
			sleep(5050);
		} catch (InterruptedException interruptedException) {
		}
	}
}