package org.chintanu.jmstest_activemq.consumer;

import jakarta.jms.JMSException;
import jakarta.jms.Message;
import lombok.RequiredArgsConstructor;
import org.chintanu.jmstest_activemq.message.HelloWorldModel;
import org.chintanu.jmstest_activemq.producer.AutoProducer;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class AutoConsumer {

    private final JmsTemplate jmsTemplate;

    @JmsListener(destination = AutoProducer.SIMPLE_QUEUE)
    public void pop(@Payload HelloWorldModel model, @Headers MessageHeaders headers, Message message) {

        System.out.println("Popped the SIMPLE_QUEUE queue now");
        System.out.println(message);
        System.out.println(model);
    }

    @JmsListener(destination = AutoProducer.NOT_SO_SIMPLE_QUEUE)
    public void popAndRespond(@Payload HelloWorldModel model, @Headers MessageHeaders headers, Message message) throws JMSException {

        System.out.println("Popped the NOT_SO_SIMPLE_QUEUE queue now");
        System.out.println(message);
        System.out.println(model);

        HelloWorldModel msg = HelloWorldModel.builder().msg("You sure you're Second?").uuid(UUID.randomUUID()).build();

        jmsTemplate.convertAndSend(message.getJMSReplyTo(), msg);
    }
}
