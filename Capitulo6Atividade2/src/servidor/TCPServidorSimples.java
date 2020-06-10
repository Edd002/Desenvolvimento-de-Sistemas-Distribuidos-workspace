package servidor;

// Protocolo:
//
//1. O cliente deve enviar para o servidor os dados a processar na forma de um vetor de inteiros contendo dois números de 0 a 100.
//2. O servidor espera receber do cliente dados na forma de um vetor de inteiros.
//3. Uma mensagem enviada pelo cliente para o servidor conterá um único vetor de inteiros.
//4. O servidor enviará uma resposta para o cliente na forma de um valor booleano contendo o resultado se os números do vetor são primos entre si.
//5. O cliente espera receber do servidor dados na forma de um valor boleano.
//6. Uma mensagem enviada pelo servidor para o cliente conterá um único valor boleano.
//7. Antes de enviar cada mensagem, o cliente abrirá uma nova conexão com o servidor.
// Ao responder, o servidor fechará a conexão.

//Observação: não há regra de tamanho máximo de mensagens (1024 bytes para o equivalente em UDP).
//Em TCP trabalha-se com fluxos de informações no lugar de pacotes de tamanho fixo.

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
				System.out.println("---------> Servidor aguardando conexÃµes");
				Socket s = soquete.accept();
				System.out.println("---------> Cliente conectado: " + s.getInetAddress().getHostName()
						+ ":" + s.getPort());

				ObjectInputStream in = new ObjectInputStream(s.getInputStream());
				DataOutputStream out = new DataOutputStream(s.getOutputStream());

				int[] vet = (int[]) in.readObject();
				boolean resposta = primosEntreSi(vet[0], vet[1]);
				out.writeBoolean(resposta);
				s.close();
			}
		}
		catch (IOException | ClassNotFoundException e) {
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
		public void run() {
			System.out.println("-----> Servidor encerrando");
			try {
				soc.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			System.out.println("-----> Servidor encerrado");
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
