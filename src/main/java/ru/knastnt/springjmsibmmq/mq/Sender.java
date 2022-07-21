package ru.knastnt.springjmsibmmq.mq;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.jms.ConnectionFactory;

@Component
public class Sender {
    @Autowired
    private ConnectionFactory connectionFactory;
    @Value("${client.publ.jms.topic-name}")
    private String dest;

    private JmsTemplate jmsTemplate;

    @PostConstruct
    public void init() {
        jmsTemplate = new JmsTemplate(connectionFactory);
    }

    public void send(String msg){
        jmsTemplate.convertAndSend(dest, msg);
    }
}
