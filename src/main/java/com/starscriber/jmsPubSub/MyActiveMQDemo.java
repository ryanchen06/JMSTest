package com.starscriber.jmsPubSub;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import javax.jms.JMSException;

/**
 * http://localhost:8161/admin/
 * 
 * @author hchen
 */
public class MyActiveMQDemo 
{
    private static final String url = "tcp://localhost:61616";
    private static final String user = null;
    private static final String password = null;
    private static final String topic = "HanTopic";
    private static final int NUM_THREADS = 3;
    private static final ExecutorService producer = Executors.newFixedThreadPool(NUM_THREADS);
    private static final ExecutorService consumer = Executors.newFixedThreadPool(NUM_THREADS);
    
    public static void main(String[] args) throws InterruptedException, JMSException 
    {
        //start 3 publishers
        for(int i=0; i< NUM_THREADS; i++){
            producer.execute(new MessagePublisher("Publisher " + i + " ", topic, url, user, password));
        }
        
        //start 3 subscribers
        for (int i = 0; i < NUM_THREADS; i++) {
            consumer.execute(new MessageSubscriber("Subscriber " + i + " ", topic, url, user, password));
        }
    }
}