import com.rabbitmq.client.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.TimeoutException;
import java.util.logging.Logger;

public class Admin {
    private static final Logger LOGGER = Logger.getLogger(Admin.class.getName());

    public static void main(String[] args) throws IOException, TimeoutException {

        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();
        Set<String> senders = new HashSet<>();

        String EXCHANGE_NAME = "exchange2";
        channel.exchangeDeclare(EXCHANGE_NAME, BuiltinExchangeType.TOPIC);

        String queueName = channel.queueDeclare().getQueue();
        channel.queueBind(queueName, EXCHANGE_NAME, "#");


        Consumer consumer = new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {

                String message = new String(body, "UTF-8");
                if (message.split(" ")[0].equals("register")) {
                    senders.add(message.split(" ")[1]);
                } else if (!message.split(" ")[0].equals("Admin_message:")) {
                    LOGGER.info(message);
                }
            }
        };

        channel.basicConsume(queueName, true, consumer);

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String message;
        while (true) {
            System.out.println("Enter message: ");
            message = br.readLine();
            String answear = "Admin_message: " + message;
            for (String s : senders) {
                channel.basicPublish(EXCHANGE_NAME, s, null, answear.getBytes("UTF-8"));
                System.out.println("Sent message to " + s);
            }
        }
    }
}
