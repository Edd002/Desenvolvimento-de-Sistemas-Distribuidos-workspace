package service;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class PrimosEntreSiServiceImpl extends UnicastRemoteObject implements PrimosEntreSiService {

	private static final long serialVersionUID = 8677271687858311172L;

	public PrimosEntreSiServiceImpl() throws RemoteException {
		super();
	}

	// Verificar se dois números são primos entre si
	@Override
	public boolean primosEntreSi(int a, int b) throws RemoteException {
		return mdc(a, b) == 1 ? true : false;
	}
	public static int mdc(int a, int b) {
		return b == 0 ? a : mdc(b, a % b);
	}
}