package br.fumec.rmi;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class SomaServiceImpl extends UnicastRemoteObject implements SomaService {
	
	private static final long serialVersionUID = 8677271687858311172L;

	public SomaServiceImpl() throws RemoteException {
		super();
	}

	@Override
	public int soma(int[] vet) throws RemoteException {
		int resultado = 0;
		for(int e: vet)
			resultado += e;
		return resultado;
	}

}
