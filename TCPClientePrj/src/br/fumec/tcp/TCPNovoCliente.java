package br.fumec.tcp;

// Protocolo:
//
// 1. O cliente deve enviar para o servidor os dados a processar na forma de um vetor de inteiros.
// 2. O servidor espera receber do cliente dados na forma de um vetor de inteiros.
// 3. Uma mensagem enviada pelo cliente para o servidor conterá um único vetor de inteiros.
// 4. O servidor enviará uma resposta para o cliente na forma de um valor inteiro contendo
//    o resultado da soma dos elementos do vetor recebido.
// 5. O cliente espera receber do servidor dados na forma de um valor inteiro.
// 6. Uma mensagem enviada pelo servidor para o cliente conterá um único valor inteiro.
// 7. O cliente enviará para o servidor uma referência nula para indicar que já terminou suas requisições
//    e que a conexão pode ser fechada.
// 8. O servidor manterá a conexão com o cliente aberta até que receba uma referência nula,
//    quando então fechará a conexão e encerrará a interação com o cliente.

import java.io.DataInputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Random;

public class TCPNovoCliente {

	private static final String HOSTNAME = "localhost";
	private static final int PORT = 4321;
	private static final int NUMREQS = 5;
	private static final int MAXTAM = 10;
	private static final int MAXELM = 100;
	
	public static void main(String[] args) {
		String hostname = HOSTNAME;
		int porta = PORT;
		Random r = new Random();
		
		try {
			Socket s = new Socket(hostname, porta);
			DataInputStream in = new DataInputStream(s.getInputStream());
			ObjectOutputStream out = new ObjectOutputStream(s.getOutputStream());
			for(int i = 0; i < NUMREQS; i++) {
				int tamanho = r.nextInt(MAXTAM) + 1;
				int[] vet = new int[tamanho];
				for(int j = 0; j < vet.length; j++)
					vet[j] = r.nextInt(MAXELM + 1);
				out.writeObject(vet);
				int resultado = in.readInt();
				exibe(vet, resultado);
				Thread.sleep(10000);
			}
			out.writeObject((int[])null);
			s.close();
		} catch (IOException | InterruptedException e) {
			e.printStackTrace();
		}
	}

	private static void exibe(int[] v, int r) {
		System.out.print("Soma ");
		for(int e: v)
			System.out.print(e + " ");
		System.out.println("= " + r);
	}
}
