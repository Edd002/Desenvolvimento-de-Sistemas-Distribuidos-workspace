package servidor;

import java.rmi.Naming;

import service.PrimosEntreSiService;
import service.PrimosEntreSiServiceImpl;

public class PrimosEntreSiServidor {

	public static void main(String[] args) {
		System.setProperty("java.security.policy", "server.policy");

		if (System.getSecurityManager() == null)
			System.setSecurityManager(new SecurityManager());

		try {
			PrimosEntreSiService ss = new PrimosEntreSiServiceImpl();
			
			String localServico = "rmi://127.0.0.1:1099/PrimosEntreSiService";
			Naming.rebind(localServico, ss);

			System.out.println("PrimosEntreSiService registrado");
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(-1);
		}
	}

	// Verificar se dois números são primos entre si
	public static boolean primosEntreSi(int a, int b) {
		return mdc(a, b) == 1 ? true : false;
	}
	public static int mdc(int a, int b) {
		return b == 0 ? a : mdc(b, a % b);
	}
}