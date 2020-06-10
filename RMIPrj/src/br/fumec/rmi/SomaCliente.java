package br.fumec.rmi;

import java.rmi.Naming;
import java.util.Random;

public class SomaCliente {
	
	private static final int NUMREQS = 5;
	private static final int MAXTAM = 10;
	private static final int MAXELM = 100;

	public static void main(String[] args) {
		System.setProperty("java.security.policy", "client.policy");
		if (System.getSecurityManager() == null)
			System.setSecurityManager(new SecurityManager());
		
        try {
            SomaService s = (SomaService)Naming.lookup("rmi://localhost:1099/SomaService");
            
    		Random r = new Random();

    		for(int i = 0; i < NUMREQS; i++) {
				int tamanho = r.nextInt(MAXTAM) + 1;
				int[] vet = new int[tamanho];
				for(int j = 0; j < vet.length; j++)
					vet[j] = r.nextInt(MAXELM + 1);
				int resultado = s.soma(vet);
				exibe(vet, resultado);
			}
            
        } catch (Exception e) {
			e.printStackTrace();
			System.exit(-1);
        }
	}

	private static void exibe(int[] v, int r) {
		System.out.print("Soma ");
		for(int e: v)
			System.out.print(e + " ");
		System.out.println("= " + r);
	}
}
