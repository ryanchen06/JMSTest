package com.starscriber.jmsPubSub;

import javax.jms.Connection;
import javax.jms.DeliveryMode;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.Topic;
import org.apache.activemq.ActiveMQConnectionFactory;

/**
 * 
 * @author hchen
 */
public class MessagePublisher implements Runnable 
{
    private final String url;
    private final String user;
    private final String password;
    private final String topicName;
    private final String name;
    private Session session;
    private MessageProducer sendPublisher;
    private Topic topic;

    public MessagePublisher(String name, String topicName, String url, String user, String password) 
    {
        this.name = name;
        this.url = url;
        this.user = user;
        this.password = password;
        this.topicName = topicName;
    }

    @Override
    public void run() 
    {
        ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory(user, password, url);
        Connection connection;

        int messageCount = 0;
        try {
            connection = connectionFactory.createConnection();
            connection.start();

            //create Topic
            session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
            topic = session.createTopic(this.topicName);
            sendPublisher = session.createProducer(topic);
            
            while (true) 
            {
                messageCount++;
                String text = " -- [" + name + "]" + "Test Message NO." + messageCount;
                sendPublisher.setDeliveryMode(DeliveryMode.NON_PERSISTENT);
                sendPublisher.send(session.createTextMessage(text));

                if (messageCount == 3) 
                {
                    break;
                }
                Thread.sleep(1000);
            }

            System.out.println(name + "is Done!!");
        } catch (JMSException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}