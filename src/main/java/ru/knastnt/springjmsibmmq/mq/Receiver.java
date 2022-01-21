package ru.knastnt.springjmsibmmq.mq;

import lombok.extern.slf4j.Slf4j;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class Receiver {
    @JmsListener(destination = "${client.publ.jms.topic-name}", containerFactory = "jmsContainer")
    public void receiveMessage(final Message message) {
        try {
            log.debug("Message has been received. Id: {}; content: {}", message.getHeaders().getId(), message.getPayload());

        } catch (Exception e) {
            log.error("Error receiving of message. Message: {}. Error:", message, e);
        }
    }
}
