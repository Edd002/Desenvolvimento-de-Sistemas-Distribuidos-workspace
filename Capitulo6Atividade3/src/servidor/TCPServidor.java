package servidor;

// Protocolo:
//
// 1. O cliente deve enviar para o servidor os dados a processar na forma de um vetor de inteiros contendo dois números de 0 a 100.
// 2. O servidor espera receber do cliente dados na forma de um vetor de inteiros.
// 3. Uma mensagem enviada pelo cliente para o servidor conterá um único vetor de inteiros.
// 4. O servidor enviará uma resposta para o cliente na forma de um valor booleano contendo o resultado se os números do vetor são primos entre si.
// 5. O cliente espera receber do servidor dados na forma de um valor boleano.
// 6. Uma mensagem enviada pelo servidor para o cliente conterá um único valor boleano.
// 7. Antes de enviar cada mensagem, o cliente abrirá uma nova conexão com o servidor.
// Ao responder, o servidor fechará a conexão.
// 8. O servidor manterá a conexão com o cliente aberta até que receba uma referência nula,
// 	  quando então fechará a conexão e encerrará a interação com o cliente.

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class TCPServidor {

	private static final int PORT = 4321;

	private static ServerSocket soquete;

	public static void main(String[] args) {
		int porta = PORT;

		try {
			soquete = new ServerSocket(porta);
			Runtime.getRuntime().addShutdownHook(new TCPServidor.Encerramento(soquete));

			while(true) {
				System.out.println("---------> Servidor aguardando conexões");
				Socket s = soquete.accept();
				System.out.println("---------> Cliente conectado: " + s.getInetAddress().getHostName()
						+ ":" + s.getPort());
				new Conexao(s).start();
			}
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(-1);
		}
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

class Conexao extends Thread {
	private Socket s;

	public Conexao(Socket s) {
		this.s = s;
	}

	@Override
	public void run() {
		try {
			ObjectInputStream in = new ObjectInputStream(s.getInputStream());
			DataOutputStream out = new DataOutputStream(s.getOutputStream());
			int vet[] = null;

			do {
				vet = (int[]) in.readObject();
				if (vet != null) {
					boolean resposta = primosEntreSi(vet[0], vet[1]);
					out.writeBoolean(resposta);
				}
			} while(vet != null);

			System.out.println("-----> Cliente encerrando");
			s.close();
		} catch (IOException | ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	// Verificar se dois números são primos entre si
	public static boolean primosEntreSi(int a, int b) {
		return mdc(a, b) == 1 ? true : false;
	}
	public static int mdc(int a, int b) {
		return b == 0 ? a : mdc(b, a % b);
	}
}
