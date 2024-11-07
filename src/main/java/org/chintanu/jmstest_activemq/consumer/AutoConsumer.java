package org.chintanu.jmstest_activemq.consumer;

import jakarta.jms.Message;
import org.chintanu.jmstest_activemq.message.HelloWorldModel;
import org.chintanu.jmstest_activemq.producer.AutoProducer;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
public class AutoConsumer {

    @JmsListener(destination = AutoProducer.SIMPLE_QUEUE)
    public void pop(@Payload HelloWorldModel model, @Headers MessageHeaders headers, Message message) {

        System.out.println("Popped the queue now");
        System.out.println(message);
        System.out.println(model);
    }
}
