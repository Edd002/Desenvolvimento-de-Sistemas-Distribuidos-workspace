package service;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface PrimosEntreSiService extends Remote {

	public boolean primosEntreSi(int a, int b) throws RemoteException;
}