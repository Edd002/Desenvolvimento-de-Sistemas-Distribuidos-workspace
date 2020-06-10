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

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class TCPServidorSimples {

	private static final int PORT = 4321;

	private static ServerSocket soquete;

	public static void main(String[] args) {
		int porta = PORT;

		try {
			soquete = new ServerSocket(porta);
			Runtime.getRuntime().addShutdownHook(new TCPServidorSimples.Encerramento(soquete));
			while(true) {
				System.out.println("---------> Servidor aguardando conexões");
				Socket s = soquete.accept();
				System.out.println("---------> Cliente conectado: " + s.getInetAddress().getHostName()
						+ ":" + s.getPort());

				ObjectInputStream in = new ObjectInputStream(s.getInputStream());
				DataOutputStream out = new DataOutputStream(s.getOutputStream());

				int[] vet = (int[])in.readObject();
				int resposta = soma(vet);
				out.writeInt(resposta);
				s.close();
			}
		}
		catch (IOException | ClassNotFoundException e) {
			e.printStackTrace();
			System.exit(-1);
		}
	}

	private static int soma(int[] v) {
		int resultado = 0;
		for(int e: v)
			resultado += e;
		return resultado;
	}

	public static class Encerramento extends Thread {

		private ServerSocket soc;

		public Encerramento(ServerSocket soc) {
			this.soc = soc;
		}

		@Override
		public void run(){
			System.out.println("-----> Servidor encerrando");
			try {
				soc.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			System.out.println("-----> Servidor encerrado");
		}
	}
}
