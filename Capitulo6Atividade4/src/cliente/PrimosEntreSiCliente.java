package cliente;

import java.rmi.Naming;
import service.PrimosEntreSiService;

public class PrimosEntreSiCliente {

	private static final int NUMERO_REQUISICOES = 5;

	public static void main(String[] args) {
		System.setProperty("java.security.policy", "client.policy");

		if (System.getSecurityManager() == null)
			System.setSecurityManager(new SecurityManager());

		try {
			PrimosEntreSiService s = (PrimosEntreSiService) Naming.lookup("rmi://127.0.0.1:1099/PrimosEntreSiService");

			for(int i = 0; i < NUMERO_REQUISICOES; i++) {
				int[] vet = new int[2];
				vet[0] = gerarNumero();
				vet[1] = gerarNumero();

				boolean resultado = s.primosEntreSi(vet[0], vet[1]);
				exibe(vet, resultado);
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(-1);
		}
	}

	// Gerar número aleatório entre 0 e 100
	private static int gerarNumero() {
		return (int) (Math.random() * 101);
	}

	// Exibe o resultado vindo do servidor
	public static void exibe(int[] vet, boolean primosEntreSi) {
		System.out.print(vet[0] + " e " + vet[1]);
		System.out.println(primosEntreSi ? " são primos entre si." : " não são primos entre si.");
	}
}