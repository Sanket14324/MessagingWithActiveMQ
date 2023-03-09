package com.activemq.messaging;

import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.jms.*;
import java.util.Scanner;

@SpringBootApplication
public class MessagingApplication {

    //default broker url is : tcp://localhost:61616

    private static String url = ActiveMQConnection.DEFAULT_BROKER_URL;


    // can make multiple queues
    private static String queueName = "MESSAGE_QUEUE";

    public static void main(String[] args) throws JMSException {
        SpringApplication.run(MessagingApplication.class, args);

        System.out.println( "URL -> "+ url );

        ConnectionFactory connectionFactory = new ActiveMQConnectionFactory(url);
        Connection connection = connectionFactory.createConnection();
        connection.start();


        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

        Destination destination = session.createQueue(queueName);

        MessageProducer producer = session.createProducer(destination);

        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter your message->\n");
        String text = scanner.nextLine();

        scanner.close();
        TextMessage message = session.createTextMessage(text);
                //session.createTextMessage("Hi Sanket, Welcome!!!");

        producer.send(message);

        System.out.println("Message '"+message.getText()+"', Sent successfully to the Queue");

        connection.close();;
    }

}
