package cliente;

// Protocolo:
//
// 1. O cliente deve enviar para o servidor os dados a processar na forma de um vetor de inteiros contendo dois números de 0 a 100.
// 2. O servidor espera receber do cliente dados na forma de um vetor de inteiros.
// 3. Uma mensagem enviada pelo cliente para o servidor conterá um único vetor de inteiros.
// 4. O servidor enviará uma resposta para o cliente na forma de um valor booleano contendo o resultado se os números do vetor são primos entre si.
// 5. O cliente espera receber do servidor dados na forma de um valor boleano.
// 6. Uma mensagem enviada pelo servidor para o cliente conterá um único valor boleano.
// 7. Antes de enviar cada mensagem, o cliente abrirá uma nova conexão com o servidor.
//    Ao responder, o servidor fechará a conexão.

// Observação: não há regra de tamanho máximo de mensagens (1024 bytes para o equivalente em UDP).
// Em TCP trabalha-se com fluxos de informações no lugar de pacotes de tamanho fixo.

import java.io.DataInputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class TCPCliente {

	private static final String HOSTNAME = "localhost";
	private static final int PORT = 4321;
	private static final int NUMERO_REQUISICOES = 5;

	public static void main(String[] args) {
		String hostname = HOSTNAME;
		int porta = PORT;

		try {
			for(int i = 0; i < NUMERO_REQUISICOES; i++) {
				int[] vet = new int[2];
				vet[0] = gerarNumero();
				vet[1] = gerarNumero();
				
				Socket s = new Socket(hostname, porta);
				DataInputStream in = new DataInputStream(s.getInputStream());
				ObjectOutputStream out = new ObjectOutputStream(s.getOutputStream());
				out.writeObject(vet);

				boolean resultado = in.readBoolean();
				s.close();
				exibe(vet, resultado);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// Gerar número aleatório entre 0 e 100
	private static int gerarNumero() {
		return (int) (Math.random() * 101);
	}

	// Exibe o resultado vindo do servidor
	public static void exibe(int[] vet, boolean primosEntreSi) {
		System.out.print(vet[0] + " e " + vet[1]);
		System.out.println(primosEntreSi ? " são primos entre si." : " não são primos entre si.");
	}
}
