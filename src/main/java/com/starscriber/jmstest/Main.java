
package com.starscriber.jmstest;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicLong;

/**
 * http://localhost:8161/admin/
 * 
 * @author hchen
 */
public class Main 
{
    private static final String url = "tcp://localhost:61616";
    private static final String user = null;
    private static final String password = null;
    private static final String queue = "HanQueue";
    private static final int PRODUCER_NUM_THREADS = 3;
    private static final int CONSUMER_NUM_THREADS = 1;
    
    public static AtomicLong counter = new AtomicLong(0);
    
    public static void main(String[] args) 
    {
        // start XX producers
        ExecutorService producer = Executors.newFixedThreadPool(PRODUCER_NUM_THREADS);
        for (int i = 0; i < PRODUCER_NUM_THREADS; i++) {
            producer.execute(new Sender("Consumer " + i + " -- ", queue, url, user, password));
        }

        // start XX consumers
        ExecutorService consumer = Executors.newFixedThreadPool(CONSUMER_NUM_THREADS);
        for (int i = 0; i < CONSUMER_NUM_THREADS; i++) {
            consumer.execute(new Receiver("Consumer " + i + " -- ", queue, url, user, password));
        }
    }
}
