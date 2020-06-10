package br.fumec.udp;

//Protocolo:
//
//1. O cliente deve enviar para o servidor os dados a processar na forma de um vetor de inteiros.
//2. O servidor espera receber do cliente dados na forma de um vetor de inteiros.
//3. Uma mensagem enviada pelo cliente para o servidor conterá um único vetor de inteiros.
//4. O servidor enviará uma resposta para o cliente na forma de um valor inteiro contendo
// o resultado da soma dos elementos do vetor recebido.
//5. O cliente espera receber do servidor dados na forma de um valor inteiro.
//6. Uma mensagem enviada pelo servidor para o cliente conterá um único valor inteiro.
//7. O tamanho máximo das mensagens trocadas entre um cliente e o servidor e 1024 bytes.

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;

public class UDPServidor {

	private static final int PORT = 1234;
	private static final int MAXBUFFERSIZE = 1024;

	private static DatagramSocket soquete;

	public static void main(String[] args) {
		int porta = PORT;
		DatagramPacket pacoteRecepcao, pacoteEnvio;
		try {
			System.out.println("-----> Servidor iniciando");
			soquete = new DatagramSocket(porta);
			Runtime.getRuntime().addShutdownHook(new UDPServidor.Encerramento(soquete));

			while (true) {
				byte[] buffer = new byte[MAXBUFFERSIZE];
				pacoteRecepcao = new DatagramPacket(buffer, buffer.length);
				soquete.receive(pacoteRecepcao);
				System.out.println("-----> RequisiÃ§Ã£o recebida");
				int[] vet = converte(pacoteRecepcao.getData());
				byte[] resposta = converte(soma(vet));

				pacoteEnvio = new DatagramPacket(resposta, resposta.length, pacoteRecepcao.getAddress(), pacoteRecepcao.getPort());
				soquete.send(pacoteEnvio);
			}

		}
		catch (SocketException e) {
			System.out.println("-----> " + e.getMessage());
		}
		catch (IOException | ClassNotFoundException e) {
			e.printStackTrace();
			System.exit(-1);
		}
	}


	private static byte[] converte(int r) throws IOException {
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		DataOutputStream dout = new DataOutputStream(out);
		dout.writeInt(r);
		return out.toByteArray();
	}

	private static int[] converte(byte[] vet) throws IOException, ClassNotFoundException {
		ObjectInputStream in = new ObjectInputStream(new ByteArrayInputStream(vet));
		return (int[])in.readObject();
	}

	private static int soma(int[] v) {
		int resultado = 0;
		for(int e: v)
			resultado += e;
		return resultado;
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
