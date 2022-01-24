package ru.knastnt.springjmsibmmq.mq;

import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.messaging.Message;
import ru.knastnt.springjmsibmmq.BaseIT;

import static org.junit.jupiter.api.Assertions.*;

class ReceiverIT extends BaseIT {
    @SpyBean(Receiver.class)
    private Receiver receiver;

    @Test
    void test() {
        for (int i = 0; i < 1000; i++) {
            writeInTopic("hi!"+i);

            ArgumentCaptor<Message> ac = ArgumentCaptor.forClass(Message.class);
            Mockito.verify(receiver, Mockito.timeout(10000)).receiveMessage(ac.capture());

            assertEquals("hi!"+i, ac.getValue().getPayload());

            Mockito.clearInvocations(receiver);
        }
    }
}
