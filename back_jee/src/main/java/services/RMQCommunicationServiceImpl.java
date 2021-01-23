package services;


import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import javax.ejb.Stateless;
import java.io.IOException;
import java.util.concurrent.TimeoutException;


@Stateless
public class RMQCommunicationServiceImpl implements RMQCommunicationService {

    private final Channel channel;

    private final static String QUEUE_NAME_SEND = "OrderQueueSend";
    private final static String QUEUE_NAME_RECEIVE = "OrderQueueReceive";


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
        channel.queueDeclare(QUEUE_NAME_RECEIVE, false, false, false, null);
    }

    public int sendOrder(String id) {
        try {
            channel.basicPublish("", QUEUE_NAME_SEND, null, id.getBytes());
        } catch (Exception e) {
            return -1;
        }
        return 0;
    }

}
