package br.fumec.udp;

// Protocolo:
//
// 1. O cliente deve enviar para o servidor os dados a processar na forma de um vetor de inteiros.
// 2. O servidor espera receber do cliente dados na forma de um vetor de inteiros.
// 3. Uma mensagem enviada pelo cliente para o servidor conterá um único vetor de inteiros.
// 4. O servidor enviará uma resposta para o cliente na forma de um valor inteiro contendo
//    o resultado da soma dos elementos do vetor recebido.
// 5. O cliente espera receber do servidor dados na forma de um valor inteiro.
// 6. Uma mensagem enviada pelo servidor para o cliente conterá um único valor inteiro.
// 7. O tamanho máximo das mensagens trocadas entre um cliente e o servidor e 1024 bytes.


import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Random;

public class UDPCliente {
	
	private static final String HOSTNAME = "localhost";
	private static final int PORT = 1234;
	private static final int NUMREQS = 5;
	private static final int MAXTAM = 10;
	private static final int MAXELM = 100;
	private static final int MAXBUFFERSIZE = 1024;

	public static void main(String[] args) {
		String hostname = HOSTNAME;
		int porta = PORT;
		try {
			Random r = new Random();
			InetAddress endereco = InetAddress.getByName(hostname);
			DatagramSocket soquete = new DatagramSocket();
			for(int i = 0; i < NUMREQS; i++) {
				int tamanho = r.nextInt(MAXTAM) + 1;
				int[] vet = new int[tamanho];
				for(int j = 0; j < vet.length; j++)
					vet[j] = r.nextInt(MAXELM + 1);
				byte[] buffer = converte(vet);
				if(buffer.length > MAXBUFFERSIZE)
					continue;
				DatagramPacket pacoteEnvio = new DatagramPacket(buffer, buffer.length, endereco, porta);
				soquete.send(pacoteEnvio);
				byte[] resposta = new byte[MAXBUFFERSIZE];
				DatagramPacket pacoteRecepcao = new DatagramPacket(resposta, resposta.length);
				soquete.receive(pacoteRecepcao);
				int resultado = converte(pacoteRecepcao.getData());
				exibe(vet, resultado);
			}
			soquete.close();
		}
		catch (IOException e) {
			e.printStackTrace();
			System.exit(-1);
		}
	}

	private static byte[] converte(int[] vet) throws IOException {
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		ObjectOutputStream dout = new ObjectOutputStream(out);
		dout.writeObject(vet);
		return out.toByteArray();
	}
	
	private static int converte(byte[] buffer) throws IOException {
		DataInputStream in = new DataInputStream(new ByteArrayInputStream(buffer));
		return (int)in.readInt();
	}

	private static void exibe(int[] v, int r) {
		System.out.print("Soma ");
		for(int e: v)
			System.out.print(e + " ");
		System.out.println("= " + r);
	}
}
