import com.rabbitmq.client.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.concurrent.TimeoutException;

public class Doctor {
    public static void main(String[] argv) throws IOException, TimeoutException {

        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();

        String EXCHANGE_NAME = "exchange2";
        channel.exchangeDeclare(EXCHANGE_NAME, BuiltinExchangeType.TOPIC);

        String queueName = channel.queueDeclare().getQueue();

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("Enter name: ");
        String name = br.readLine();

        channel.queueBind(queueName, EXCHANGE_NAME, name);

        channel.basicPublish(EXCHANGE_NAME, "", null, new String("register " + name).getBytes("UTF-8"));

        Consumer consumer = new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {

                String message = new String(body, "UTF-8");
                System.out.println("Doctor: " + name + " received: " + message);

            }
        };

        channel.basicConsume(queueName, true, consumer);

        while (true) {
            System.out.println("Enter patient name: ");
            String patient = br.readLine();
            System.out.println("Enter type: ");
            String type = br.readLine();

            if ("exit".equals(patient)) break;

            channel.basicPublish(EXCHANGE_NAME, name + "." + type, null, patient.getBytes("UTF-8"));
            System.out.println("Sent: " + patient);
        }
    }
}

