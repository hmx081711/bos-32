package com.hmx.messagelistener;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

public class MessageLinster implements MessageListener {

    @Override
    public void onMessage(Message message) {
        TextMessage message1 = (TextMessage)message;
        try {
            String text = message1.getText();
            System.out.println(text);
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }
}
