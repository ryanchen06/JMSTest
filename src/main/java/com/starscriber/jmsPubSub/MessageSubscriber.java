package com.starscriber.jmsPubSub;

import javax.jms.Connection;
import javax.jms.JMSException;
import javax.jms.MessageConsumer;
import javax.jms.Session;
import javax.jms.Topic;
import org.apache.activemq.ActiveMQConnectionFactory;

/**
 * 
 * @author hchen
 */
public class MessageSubscriber implements Runnable 
{
    private final String name;
    private final String url;
    private final String user;
    private final String password;
    private final String topicName;
    private Session session;
    private MessageConsumer subscriber;
    private Topic topic;

    public MessageSubscriber(String name, String topicName, String url, String user, String password) 
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
        Connection connection = null;
        
        try {
            connection = connectionFactory.createConnection();
            session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
            
            // create Topic
            topic = session.createTopic(this.topicName);
            session = connection.createSession(true, Session.AUTO_ACKNOWLEDGE);
            subscriber = session.createConsumer(topic);
            subscriber.setMessageListener(new TextListener(name));
            connection.start();

            System.out.println(name + "start!");

        } catch (JMSException e) {
            if (connection != null) 
            {
                try {
                    connection.close();
                } catch (JMSException e1) {
                    e1.printStackTrace();
                }
            }
        }
    }
}