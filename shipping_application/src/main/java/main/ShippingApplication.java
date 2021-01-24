package main;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;

import java.io.IOException;
import java.util.Scanner;
import java.util.concurrent.TimeoutException;

public class ShippingApplication {

    private static Channel channel;

    private final static String QUEUE_NAME_SEND = "OrderQueueSend";
    private final static String QUEUE_NAME_RECEIVE = "OrderQueueReceive";

    public static void main(String[] args) throws IOException, TimeoutException {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        factory.setUsername("guest");
        factory.setPassword("guest");
        factory.setVirtualHost("/");
        factory.setHost("localhost");
        factory.setPort(5672);
        Connection connection = factory.newConnection();
        channel = connection.createChannel();
        channel.queueDeclare(QUEUE_NAME_SEND, false, false, false, null);
        channel.queueDeclare(QUEUE_NAME_RECEIVE, false, false, false, null);
        DeliverCallback deliverCallback = (consumerTag, delivery) -> {
            String message = new String(delivery.getBody(), "UTF-8");
            System.out.println(" [x] Received order '" + message + "' from auctions application.");
        };
        channel.basicConsume(QUEUE_NAME_SEND, true, deliverCallback, consumerTag -> { });
        mainLoop();
    }

    public static void mainLoop() {
        boolean exit = false;
        Scanner scan = new Scanner(System.in);
        System.out.println("Enter the ID of the order you wish confirm shipment for.");
        System.out.println("Enter 'exit' to exit the application.");
        while (true) {
            String userInput = scan.nextLine();
            try {
                long orderId = Long.parseLong(userInput);
                channel.basicPublish("", QUEUE_NAME_RECEIVE, null, userInput.getBytes());
                System.out.println("Input successfully sent.");
            } catch (NumberFormatException nfe) {
                System.out.println("Your input is not a number.");
            } catch (IOException e) {
                System.out.println("AN error occured while sending order id to auctions application.");
            }
        }
    }

}
