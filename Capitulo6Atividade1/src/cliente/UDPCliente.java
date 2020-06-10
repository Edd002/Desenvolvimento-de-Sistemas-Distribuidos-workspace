package cliente;

// Protocolo:
//
// 1. O cliente deve enviar para o servidor os dados a processar na forma de um vetor contendo dois números inteiros de 0 a 100 (inclusive).
// 2. O servidor espera receber do cliente dados na forma de um vetor de inteiros.
// 3. Uma mensagem enviada pelo cliente para o servidor conterá um único vetor de inteiros com duas posições.
// 4. O servidor enviará uma resposta para o cliente na forma de um valor boleano, true se forem primos entre si e false se não forem.
// 5. O cliente espera receber do servidor dados de uma variável boleana.
// 6. Uma mensagem enviada pelo servidor para o cliente conterá um único valor boleano.
// 7. O tamanho máximo das mensagens trocadas entre um cliente e o servidor e 1024 bytes.


import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class UDPCliente {
	
	private static final String HOSTNAME = "localhost";
	private static final int PORT = 1234;
	
	private static final int NUMERO_REQUISICOES = 5;
	private static final int MAX_BUFFER_SIZE = 1024;

	public static void main(String[] args) {
		String hostname = HOSTNAME;
		int porta = PORT;
		try {
			InetAddress endereco = InetAddress.getByName(hostname);
			DatagramSocket soquete = new DatagramSocket();

			for(int i = 0; i < NUMERO_REQUISICOES; i++) {
				int[] vet = new int[2];
				vet[0] = gerarNumero();
				vet[1] = gerarNumero();

				byte[] buffer = converte(vet);
				if(buffer.length > MAX_BUFFER_SIZE)
					continue;

				DatagramPacket pacoteEnvio = new DatagramPacket(buffer, buffer.length, endereco, porta);
				soquete.send(pacoteEnvio);

				byte[] resposta = new byte[MAX_BUFFER_SIZE];
				DatagramPacket pacoteRecepcao = new DatagramPacket(resposta, resposta.length);

				soquete.receive(pacoteRecepcao);
				boolean resultado = converte(pacoteRecepcao.getData());

				exibe(vet, resultado);
			}
			soquete.close();
		}
		catch (IOException e) {
			e.printStackTrace();
			System.exit(-1);
		}
	}

	// Vetor de inteiros para vetor de bytes
	private static byte[] converte(int[] vet) throws IOException {
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		ObjectOutputStream dout = new ObjectOutputStream(out);
		dout.writeObject(vet);
		return out.toByteArray();
	}

	// Vetor de bytes para um boolean
	private static boolean converte(byte[] buffer) throws IOException {
		return buffer[0] != 0;
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