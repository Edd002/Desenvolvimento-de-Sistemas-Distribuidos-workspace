package br.fumec.rmi;

import java.rmi.Naming;

public class SomaServidor {

	public static void main(String[] args) {
		System.setProperty("java.security.policy", "server.policy");
		if (System.getSecurityManager() == null)
			System.setSecurityManager(new SecurityManager());
		
		try {
			SomaService ss = new SomaServiceImpl();
			Naming.rebind("SomaService", ss);
			System.out.println("SomaService registrado");
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(-1);
		}
	}
}
