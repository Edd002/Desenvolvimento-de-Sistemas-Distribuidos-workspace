package br.fumec.rmi;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface SomaService extends Remote {
	
	public int soma(int[] vet) throws RemoteException;

}
