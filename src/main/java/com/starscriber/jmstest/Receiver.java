
package com.starscriber.jmstest;

import javax.jms.Connection;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;
import javax.jms.Session;
import org.apache.activemq.ActiveMQConnectionFactory;
 
public class Receiver implements Runnable, MessageListener
{
    private final String url;
    private final String user;
    private final String password;
    private final String queue;
    private final String name;        
    private Session session;
    private Destination receiveQueue;
 
    public Receiver(String name, String queue, String url, String user, String password) 
    {
        this.url = url;
        this.user = user;
        this.password = password;
        this.queue = queue;
        this.name = name;
    }
 
    @Override
    public void run() 
    {
        ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory(user, password, url);
        
        try {
            Connection connection = connectionFactory.createConnection();
            session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
            receiveQueue = session.createQueue(queue);
            MessageConsumer consumer = session.createConsumer(receiveQueue);
            consumer.setMessageListener(this);
            connection.start();

        } catch (JMSException e) {
            System.out.println("Receiver: JMSException " + e.getMessage());
        }
    }
    
    @Override
    public void onMessage(Message message)
    {
        try {
            ObjectMessage messageReceiver = (ObjectMessage) message;
            Response resp = (Response) messageReceiver.getObject();
        } catch (JMSException e) {
            System.out.println("Receiver: JMSException " + e.getMessage());
        }
    }
}
