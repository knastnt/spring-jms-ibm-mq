package ru.knastnt.springjmsibmmq.mq;

import lombok.extern.slf4j.Slf4j;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.messaging.Message;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Component
public class Receiver {
    @SendTo("${client.publ.jms.err-queue-name}")
    @JmsListener(destination = "${client.publ.jms.queue-name}", containerFactory = "jmsContainer")
    public Message receiveMessage(final Message message) {
        try {
            log.debug("Message has been received. Id: {}; content: {}", message.getHeaders().getId(), message.getPayload());

        } catch (Exception e) {
            log.error("Error receiving of message. Message: {}. Error:", message, e);
        }
        Object jmsxDeliveryCount = message.getHeaders().get("JMSXDeliveryCount");
        if (jmsxDeliveryCount != null && (jmsxDeliveryCount instanceof Integer) && ((Integer)jmsxDeliveryCount) < 10) throw new RuntimeException("eeeeeeeeeeeeexxxxxxceeeeeeeptioooooon!");
        return message;
    }
}
