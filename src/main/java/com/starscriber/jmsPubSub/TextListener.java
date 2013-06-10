package com.starscriber.jmsPubSub;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

/**
 * 
 * @author hchen
 */
public class TextListener implements MessageListener 
{
    private final String name;
    
    public TextListener(String name){
        this.name = name;
    }
    
    @Override
    /**
     * Casts the message to a TextMessage and displays its text.
     *
     * @param message the incoming message
     */
    public void onMessage(Message message) 
    {
        TextMessage msg;

        try {
            if (message instanceof TextMessage) {
                msg = (TextMessage) message;
                System.out.println(name + msg.getText());
            } else {
                System.out.println(name + "Message of wrong type: " + message.getClass().getName());
            }
        } catch (JMSException e) {
            System.out.println("JMSException in onMessage(): " + e.toString());
        } catch (Throwable t) {
            System.out.println("Exception in onMessage():" + t.getMessage());
        }
    }
}