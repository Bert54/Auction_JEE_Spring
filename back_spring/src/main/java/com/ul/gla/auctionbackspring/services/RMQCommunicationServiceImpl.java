package com.ul.gla.auctionbackspring.services;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

@Component
public class RMQCommunicationServiceImpl implements RMQCommunicationService{

    private final Channel channel;

    private final static String QUEUE_NAME_SEND = "OrderQueueSend";
    private final static String QUEUE_NAME_SEND_CONFIRMATION = "OrderQueueConfirmation";
    private final static String QUEUE_NAME_RECEIVE = "OrderQueueReceive";

    @Autowired
    private OrderService orderService;

    public RMQCommunicationServiceImpl() throws IOException, TimeoutException {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        factory.setUsername("guest");
        factory.setPassword("guest");
        factory.setVirtualHost("/");
        factory.setHost("localhost");
        factory.setPort(5672);
        Connection connection = factory.newConnection();
        this.channel =  connection.createChannel();
        channel.queueDeclare(QUEUE_NAME_SEND, false, false, false, null);
        channel.queueDeclare(QUEUE_NAME_SEND_CONFIRMATION, false, false, false, null);
        channel.queueDeclare(QUEUE_NAME_RECEIVE, false, false, false, null);
        DeliverCallback deliverCallbackreceive = (consumerTag, delivery) -> {
            String message = new String(delivery.getBody(), "UTF-8");
            System.out.println(" [x] Received order '" + message + "' from shipping application");
            this.receiveOrderShippingConfirmation(message);
        };
        channel.basicConsume(QUEUE_NAME_RECEIVE, true, deliverCallbackreceive, consumerTag -> { });

        DeliverCallback deliverCallbackconfirmation = (consumerTag, delivery) -> {
            String message = new String(delivery.getBody(), "UTF-8");
            System.out.println(" [x] Received order confirmation for order '" + message + "' from shipping application");
            this.confirmOrderReceptionShippingApplication(message);
        };
        channel.basicConsume(QUEUE_NAME_SEND_CONFIRMATION, true, deliverCallbackconfirmation, consumerTag -> { });
    }

    @Override
    public int sendOrder(String id) {
        try {
            channel.basicPublish("", QUEUE_NAME_SEND, null, id.getBytes());
        } catch (Exception e) {
            return -1;
        }
        return 0;
    }

    private void receiveOrderShippingConfirmation(String orderId) {
        if (!isNumeric(orderId)) {
            return;
        }
        this.orderService.updateOrderFromShippingApplication(Long.parseLong(orderId));
    }

    private void confirmOrderReceptionShippingApplication(String orderId) {
        if (!isNumeric(orderId)) {
            return;
        }
        this.orderService.confirmOrderReceptionFromShippingApplication(Long.parseLong(orderId));
    }

    private boolean isNumeric(String numberToCheck) {
        if (numberToCheck == null) {
            return false;
        }
        try {
            long l = Long.parseLong(numberToCheck);
        } catch (NumberFormatException nfe) {
            return false;
        }
        return true;
    }

}
