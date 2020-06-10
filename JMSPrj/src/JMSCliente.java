import java.util.Random;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MessageConsumer;
import javax.jms.MessageProducer;
import javax.jms.ObjectMessage;
import javax.jms.Session;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

public class JMSCliente {
	
	private static final int NUMREQS = 5;
	private static final int MAXTAM = 10;
	private static final int MAXELM = 100;

	private Connection connection = null;
	private Session session = null;
	private MessageProducer producer = null;
	private MessageConsumer consumer = null;
	
	public JMSCliente() {
		// Faz a inicialização das estruturas de mensagens
		System.out.println("JMSCliente: inicialização");
		
		// Obtenção do contexto JNDI
		Context jndiContext = null;
		try {
			jndiContext = new InitialContext();
		} catch (NamingException e) {
			System.out.println("JMSCliente: obtenção do contexto inicial falhou: " + e);
			System.exit(-1);
		}

		// Obtenção da fábrica de conexões e das filas de mensagens
		ConnectionFactory connectionFactory = null;
		Destination request = null;
		Destination response = null;
        String requestName= "RequestQueue";
        String responseName= "ResponseQueue";
		try {
			connectionFactory = (ConnectionFactory) jndiContext.lookup("queueConnectionFactory");
			request = (Destination) jndiContext.lookup(requestName);
			response = (Destination) jndiContext.lookup(responseName);
		} catch (NamingException e) {
			System.out.println("JMSCliente: falha no lookup JNDI: " + e);
			System.exit(-1);
		}

		try {
			// Criação da conexão e da sessão (falso indica que não utiliza transação)
			connection = connectionFactory.createConnection();
			session = connection.createSession(false,Session.AUTO_ACKNOWLEDGE);
			
			// Criação do produtor de mensagens
			producer = session.createProducer(request);
			
			// Criação do consumidor e início da recepção de mensagens.
			consumer = session.createConsumer(response);
			
			System.out.println("JMSCliente: inicialização concluída");
        } catch (JMSException e) {
            System.out.println("JMSCliente: exceção capturada: " + e.toString());
        }

	}
	
	private void somas() {
		// Implementa a regra de negócio (envia vetores e recebe as somas)
		
		ObjectMessage message;
		try {
			connection.start();

			Random r = new Random();
    		for(int i = 0; i < NUMREQS; i++) {
    			// Geração dos vetores a enviar
				int tamanho = r.nextInt(MAXTAM) + 1;
				int[] vet = new int[tamanho];
				for(int j = 0; j < vet.length; j++)
					vet[j] = r.nextInt(MAXELM + 1);
				// Envio da mensagem
				message = session.createObjectMessage();
				message.setObject(vet);
				producer.send(message);
				// Recepção da resposta e exibição do resultado
				ObjectMessage resultado = (ObjectMessage)consumer.receive();
				int resp = (Integer)resultado.getObject();
				exibe(vet, resp);
			}

    		connection.stop();
		} catch (JMSException e) {
			e.printStackTrace();
            System.out.println("JMSCliente: exceção capturada: " + e.toString());
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (JMSException e) {
                }
            }
        }

	}

	private void exibe(int[] v, int r) {
		System.out.print("JMSCliente: Soma ");
		for(int e: v)
			System.out.print(e + " ");
		System.out.println("= " + r);
	}

	public static void main(String[] args) {
		JMSCliente cliente = new JMSCliente();
		cliente.somas();
	}

}
