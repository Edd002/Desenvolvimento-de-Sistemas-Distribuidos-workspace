package cliente;

// Protocolo:
//
// 1. O cliente deve enviar para o servidor os dados a processar na forma de um vetor de inteiros contendo dois n�meros de 0 a 100.
// 2. O servidor espera receber do cliente dados na forma de um vetor de inteiros.
// 3. Uma mensagem enviada pelo cliente para o servidor conter� um �nico vetor de inteiros.
// 4. O servidor enviar� uma resposta para o cliente na forma de um valor booleano contendo o resultado se os n�meros do vetor s�o primos entre si.
// 5. O cliente espera receber do servidor dados na forma de um valor boleano.
// 6. Uma mensagem enviada pelo servidor para o cliente conter� um �nico valor boleano.
// 7. Antes de enviar cada mensagem, o cliente abrir� uma nova conex�o com o servidor.
// Ao responder, o servidor fechar� a conex�o.
// 8. O servidor manter� a conex�o com o cliente aberta at� que receba uma refer�ncia nula,
//    quando ent�o fechar� a conex�o e encerrar� a intera��o com o cliente.

import java.io.DataInputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class TCPNovoCliente {

	private static final String HOSTNAME = "localhost";
	private static final int PORT = 4321;
	private static final int NUMERO_REQUISICOES = 5;

	public static void main(String[] args) {
		String hostname = HOSTNAME;
		int porta = PORT;

		try {
			Socket s = new Socket(hostname, porta);
			DataInputStream in = new DataInputStream(s.getInputStream());
			ObjectOutputStream out = new ObjectOutputStream(s.getOutputStream());

			for(int i = 0; i < NUMERO_REQUISICOES; i++) {
				int[] vet = new int[2];
				vet[0] = gerarNumero();
				vet[1] = gerarNumero();

				out.writeObject(vet);

				boolean resultado = in.readBoolean();
				exibe(vet, resultado);
				Thread.sleep(100);
			}

			out.writeObject((int[]) null);
			s.close();
		} catch (IOException | InterruptedException e) {
			e.printStackTrace();
		}
	}

	// Gerar n�mero aleat�rio entre 0 e 100
	private static int gerarNumero() {
		return (int) (Math.random() * 101);
	}

	// Exibe o resultado vindo do servidor
	public static void exibe(int[] vet, boolean primosEntreSi) {
		System.out.print(vet[0] + " e " + vet[1]);
		System.out.println(primosEntreSi ? " s�o primos entre si." : " n�o s�o primos entre si.");
	}
}
