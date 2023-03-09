package com.activemq.messaging;


import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;

import org.springframework.boot.SpringApplication;

import javax.jms.*;

public class MessageReceiver {
    //default broker url is : tcp://localhost:61616

    private static String url = ActiveMQConnection.DEFAULT_BROKER_URL;


    // can make multiple queues
    private static String queueName = "MESSAGE_QUEUE";

    public static void main(String[] args)  throws  JMSException{


        System.out.println( "URL -> "+ url );

        ConnectionFactory connectionFactory = new ActiveMQConnectionFactory(url);
        Connection connection = connectionFactory.createConnection();
        connection.start();


        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

        Destination destination = session.createQueue(queueName);

        MessageConsumer consumer = session.createConsumer(destination);

        Message message = consumer.receive();

        if(message instanceof TextMessage){
            TextMessage textMessage = (TextMessage) message;
            System.out.println("Message received -> '"+ textMessage.getText()+"'" );
        }

        connection.close();;

    }

}
