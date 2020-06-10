package servidor;

//Protocolo:
//
//1. O cliente deve enviar para o servidor os dados a processar na forma de um vetor contendo dois números inteiros de 0 a 100 (inclusive).
//2. O servidor espera receber do cliente dados na forma de um vetor de inteiros.
//3. Uma mensagem enviada pelo cliente para o servidor conterá um único vetor de inteiros com duas posições.
//4. O servidor enviará uma resposta para o cliente na forma de um valor boleano, true se forem primos entre si e false se não forem.
//5. O cliente espera receber do servidor dados de uma variável boleana.
//6. Uma mensagem enviada pelo servidor para o cliente conterá um único valor boleano.
//7. O tamanho máximo das mensagens trocadas entre um cliente e o servidor e 1024 bytes.

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;

public class UDPServidor {

	private static final int PORT = 1234;
	private static final int MAX_BUFFER_SIZE = 1024;

	private static DatagramSocket soquete;

	public static void main(String[] args) {
		int porta = PORT;
		DatagramPacket pacoteRecepcao, pacoteEnvio;

		try {
			System.out.println("-----> Servidor iniciando");
			soquete = new DatagramSocket(porta);
			Runtime.getRuntime().addShutdownHook(new UDPServidor.Encerramento(soquete));

			while (true) {
				byte[] buffer = new byte[MAX_BUFFER_SIZE];
				pacoteRecepcao = new DatagramPacket(buffer, buffer.length);

				soquete.receive(pacoteRecepcao);
				System.out.println("-----> Requisição recebida");

				int[] vet = converte(pacoteRecepcao.getData());
				byte[] resposta = converte(primosEntreSi(vet[0], vet[1]));

				pacoteEnvio = new DatagramPacket(resposta, resposta.length, pacoteRecepcao.getAddress(), pacoteRecepcao.getPort());
				soquete.send(pacoteEnvio);
			}

		} catch (SocketException e) {
			System.out.println("-----> " + e.getMessage());
		} catch (IOException | ClassNotFoundException e) {
			e.printStackTrace();
			System.exit(-1);
		}
	}

	// Boolean para vetor de bytes
	private static byte[] converte(boolean primosEntreSi) throws IOException {
		return new byte[] { (byte) (primosEntreSi ? 1 : 0) };
	}

	// Vetor de bytes para vetor de inteiros
	private static int[] converte(byte[] vet) throws IOException, ClassNotFoundException {
		ObjectInputStream in = new ObjectInputStream(new ByteArrayInputStream(vet));
		return (int[]) in.readObject();
	}

	// Verificar se dois números são primos entre si
	public static boolean primosEntreSi(int a, int b) {
		return mdc(a, b) == 1 ? true : false;
	}
	public static int mdc(int a, int b) {
		return b == 0 ? a : mdc(b, a % b);
	}

	public static class Encerramento extends Thread {
		private DatagramSocket soc;

		public Encerramento(DatagramSocket soc) {
			this.soc = soc;
		}

		@Override
		public void run(){
			System.out.println("-----> Servidor encerrando");
			soc.close();
			System.out.println("-----> Servidor encerrado");
		}
	}
}