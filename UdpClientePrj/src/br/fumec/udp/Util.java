package br.fumec.udp;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Random;

public class Util {
	
	private static final int BYTESPORINT = 4;
	private static final int NUMREQS = 5;
	private static final int MAXTAM = 10;
	private static final int MAXELM = 100;

	public static void main(String[] args) {
		
		Random r = new Random();
		
		try {
			for(int i = 0; i < NUMREQS; i++) {
				int tamanho = r.nextInt(MAXTAM) + 1;
				int[] vet = new int[tamanho];
				for(int j = 0; j < vet.length; j++)
					vet[j] = r.nextInt(MAXELM + 1);
				exibe(vet);
				byte[] bvet = converte(vet);
				System.out.println("Tamanho = " + bvet.length);
				int[] vcpy = converte(bvet);
				exibe(vcpy);
			}
		}
		catch (Exception e) {
			e.printStackTrace();
			System.exit(-1);
		}
	}
	
	private static byte[] converte(int[] vet) throws IOException {
		ByteArrayOutputStream out = new ByteArrayOutputStream(BYTESPORINT * vet.length);
		ObjectOutputStream dout = new ObjectOutputStream(out);
		dout.writeObject(vet);
		return out.toByteArray();
	}
	
	private static int[] converte(byte[] vet) throws IOException, ClassNotFoundException {
		ObjectInputStream in = new ObjectInputStream(new ByteArrayInputStream(vet));
		return (int[])in.readObject();
	}
	
	private static void exibe(int[] v) {
		for(int e: v)
			System.out.print(e + " ");
		System.out.println();
	}

}
