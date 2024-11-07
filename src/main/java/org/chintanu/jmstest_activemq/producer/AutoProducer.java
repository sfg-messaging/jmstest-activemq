package org.chintanu.jmstest_activemq.producer;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.chintanu.jmstest_activemq.message.HelloWorldModel;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@RequiredArgsConstructor
@Data
public class AutoProducer {

    public static final String SIMPLE_QUEUE = "convert.and.send";
    private final JmsTemplate jmsTemplate;

    @Scheduled(fixedDelay = 3000)
    public void push() {

        System.out.println("In push");

        HelloWorldModel msg = HelloWorldModel.builder().msg("First msg Yay").uuid(UUID.randomUUID()).build();
        jmsTemplate.convertAndSend(SIMPLE_QUEUE, msg);

        System.out.println("Pushed msg to topic : " + SIMPLE_QUEUE);
    }
}
