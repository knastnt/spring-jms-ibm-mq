package ru.knastnt.springjmsibmmq;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.test.context.ActiveProfiles;

import javax.jms.ConnectionFactory;

@ActiveProfiles("test") //Говорим что в интеграционных тестах профиль будет test
@SpringBootTest
public class BaseIT {
    @Configuration
    public static class TestConfig {
        @Bean //Подключаемся к уже встроенному локальному JMS-серверу, и отключаем сохраняемость сообщений
        public ConnectionFactory connectionFactory() {
            return new ActiveMQConnectionFactory("vm://localhost?broker.persistent=false");
        }

        @Bean //Создаём темплейт, чтобы слать сообщения в топик
        public JmsTemplate topicJmsTemplate(ConnectionFactory connectionFactory){
            JmsTemplate jmsTemplate = new JmsTemplate(connectionFactory);
            jmsTemplate.setPubSubDomain(true);//говорим что будем работать в режиме Publisher/Subscriber
            return jmsTemplate;
        }

    }

    @Autowired
    private JmsTemplate topicJmsTemplate;

    @Value("${client.publ.jms.topic-name}")
    private String topicName;

    //Метод для простой отправки сообщения в топик
    public void writeInTopic(String message) {
        topicJmsTemplate.convertAndSend(topicName, message);
    }
}
