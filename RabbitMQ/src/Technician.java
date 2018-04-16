import com.rabbitmq.client.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.TimeoutException;


public class Technician {


    private static TypeOfSurvey specialization1;
    private static TypeOfSurvey specialization2;
    private static String name;


    public static void main(String[] args) throws IOException, TimeoutException {

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("Enter name: ");
        name = br.readLine();
        getSpecialization();

        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();


        String queueName = channel.queueDeclare().getQueue();

        String EXCHANGE_NAME = "exchange2";
        channel.exchangeDeclare(EXCHANGE_NAME, BuiltinExchangeType.TOPIC);


        channel.basicPublish(EXCHANGE_NAME, "", null, new String("register " + name).getBytes("UTF-8"));


        channel.queueBind(queueName, EXCHANGE_NAME, "*." + specialization1.name());
        channel.queueBind(queueName, EXCHANGE_NAME, "*." + specialization2.name());
        channel.queueBind(queueName, EXCHANGE_NAME, name);


        Consumer consumer = new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                String message = new String(body, "UTF-8");
                if (envelope.getRoutingKey().equals(name)) {
                    System.out.println(message);
                } else {
                    System.out.println("Technician " + name + " Received: " + message);
                    Random generator = new Random();

                    try {
                        Thread.sleep(generator.nextInt(5) * 1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    String answer = message + " " + envelope.getRoutingKey().substring(envelope.getRoutingKey().indexOf(".") + 1) + " " + "done";
                    System.out.println("Technician: " + name + " send '" + answer + "' to: " + envelope.getRoutingKey().substring(0, envelope.getRoutingKey().indexOf(".")));
                    channel.basicPublish(EXCHANGE_NAME, envelope.getRoutingKey().substring(0, envelope.getRoutingKey().indexOf(".")), null, answer.getBytes("UTF-8"));

                }

            }
        };

        channel.basicConsume(queueName, true, consumer);
    }


    private static void getSpecialization() {
        ArrayList<TypeOfSurvey> specializations = new ArrayList<>();
        specializations.add(TypeOfSurvey.ELBOW);
        specializations.add(TypeOfSurvey.KNEE);
        specializations.add(TypeOfSurvey.HIP);

        Random generator = new Random();
        TypeOfSurvey specialization = specializations.get(generator.nextInt(3));
        System.out.print("Technician " + name + " " + specialization + " ");
        specializations.remove(specialization);
        specialization1 = specialization;
        specialization = specializations.get(generator.nextInt(2));
        specialization2 = specialization;
        System.out.println(specialization);
    }
}