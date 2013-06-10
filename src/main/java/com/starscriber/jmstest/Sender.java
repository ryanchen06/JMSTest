
package com.starscriber.jmstest;

import javax.jms.Connection;
import javax.jms.DeliveryMode;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.ObjectMessage;
import javax.jms.Session;
import org.apache.activemq.ActiveMQConnectionFactory;
 
/**
 * 
 * @author hchen
 */
public class Sender implements Runnable 
{
    private final String url;
    private final String user;
    private final String password;
    private final String queueName;
    private final String name;
    private Session session;
    private Destination sendQueue;
    private final int sendSize = 1000000;
 
    public Sender(String name, String queueName, String url, String user, String password) 
    {
        this.url = url;
        this.user = user;
        this.password = password;
        this.queueName = queueName;
        this.name = name;
    }
 
    @Override
    public void run() 
    {
        ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory(user, password, url);
        
        connectionFactory.setUseAsyncSend(true);
        connectionFactory.setOptimizeAcknowledge(true);
        Connection connection;
 
        Response resp = new Response("1", 100, Response.Type.CALL, "100", "101");

        try {
            connection = connectionFactory.createConnection();
            connection.start();
            session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
            sendQueue = session.createQueue(queueName);
            MessageProducer sender = session.createProducer(sendQueue);
            sender.setDeliveryMode(DeliveryMode.NON_PERSISTENT);
            ObjectMessage objMessage = session.createObjectMessage();
            objMessage.setObject(resp);
            
            long start = System.currentTimeMillis();
            
            while (true) 
            {
                Main.counter.incrementAndGet();
                
                sender.send(objMessage);
 
                if (Main.counter.longValue() == sendSize) {
                    long time = (System.currentTimeMillis() - start)/1000l;
                    System.out.println("Time: " + time + " secs");
                    System.out.println("TPS: " + sendSize/time);
                    break;
                }
            }
            session.close();
            sender.close();
            connection.close();
        } catch (JMSException e) {
            System.out.println("Sender: JMSException " + e.getMessage());
        } 
        
        System.out.println("Sender Done!!!");
    }
}