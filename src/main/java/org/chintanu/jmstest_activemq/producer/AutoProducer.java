package org.chintanu.jmstest_activemq.producer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.jms.JMSException;
import jakarta.jms.Message;
import jakarta.jms.Session;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.chintanu.jmstest_activemq.message.HelloWorldModel;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@RequiredArgsConstructor
@Data
public class AutoProducer {

    public static final String SIMPLE_QUEUE = "convert.and.send";
    public static final String NOT_SO_SIMPLE_QUEUE = "send.and.receive";

    private final JmsTemplate jmsTemplate;
    private final ObjectMapper objectMapper;

    //@Scheduled(fixedDelay = 3000)
    public void push() {

        System.out.println("In push");

        HelloWorldModel msg = HelloWorldModel.builder().msg("First msg Yay").uuid(UUID.randomUUID()).build();
        jmsTemplate.convertAndSend(SIMPLE_QUEUE, msg);

        System.out.println("Pushed msg to topic : " + SIMPLE_QUEUE);
    }

    @Scheduled(fixedDelay = 3000)
    public void pushWithReceiver() throws JMSException {

        System.out.println("In pushWithReceiver");

        HelloWorldModel msg = HelloWorldModel.builder().msg("Second msg Yay").uuid(UUID.randomUUID()).build();
        Message receivedMSg = jmsTemplate.sendAndReceive(NOT_SO_SIMPLE_QUEUE, new MessageCreator() {
            @Override
            public Message createMessage(Session session) throws JMSException {

                Message message = null;
                try {
                    message = session.createTextMessage(objectMapper.writeValueAsString(msg));
                } catch (JsonProcessingException e) {
                    e.printStackTrace();
                    throw new RuntimeException(e);
                }
                message.setStringProperty("_type", "org.chintanu.jmstest_activemq.message.HelloWorldModel");

                System.out.println("Message constructed");

                return message;
            }
        });

        System.out.println("Got response back : " + receivedMSg.getBody(String.class));
    }
}
