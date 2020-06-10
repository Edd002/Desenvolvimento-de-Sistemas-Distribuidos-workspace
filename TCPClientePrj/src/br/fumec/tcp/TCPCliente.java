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
// 7. Antes de enviar cada mensagem, o cliente irá abrir uma nova conexão com o servidor.
//    Ao responder, o servidor fechará a conexão.

// Observação: não há regra de tamanho máximo de mensagens (1024 bytes para o equivalente em UDP).
// Em TCP trabalha-se com fluxos de informação no lugar de pacotes de tamanho fixo.

import java.io.DataInputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Random;

public class TCPCliente {

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
			for(int i = 0; i < NUMREQS; i++) {
				int tamanho = r.nextInt(MAXTAM) + 1;
				int[] vet = new int[tamanho];
				for(int j = 0; j < vet.length; j++)
					vet[j] = r.nextInt(MAXELM + 1);
				Socket s = new Socket(hostname, porta);
				DataInputStream in = new DataInputStream(s.getInputStream());
				ObjectOutputStream out = new ObjectOutputStream(s.getOutputStream());
				out.writeObject(vet);
				int resultado = in.readInt();
				s.close();
				exibe(vet, resultado);
			}
		} catch (IOException e) {
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
