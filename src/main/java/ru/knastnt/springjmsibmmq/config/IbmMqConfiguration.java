package ru.knastnt.springjmsibmmq.config;

import com.ibm.mq.jms.MQConnectionFactory;
import com.ibm.mq.spring.boot.MQConfigurationProperties;
import com.ibm.mq.spring.boot.MQConnectionFactoryFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.jms.config.DefaultJmsListenerContainerFactory;
import org.springframework.jms.connection.JmsTransactionManager;
import org.springframework.jms.support.converter.MessageConverter;
import org.springframework.transaction.PlatformTransactionManager;

import javax.jms.ConnectionFactory;

@Slf4j
@Configuration
public class IbmMqConfiguration {

    /**
     * Бин для чтения конфигурации соединения с брокером очередей
     * @return
     */
    @Bean
    @Profile("!test")
    @ConfigurationProperties(prefix = "client.publ.jms")
    public MQConfigurationProperties configProperties() {
        return new MQConfigurationProperties();
    }

    /**
     * Бин для создания соединения с брокером очередей
     * @param configProperties
     * @return
     */
    @Bean
    @Profile("!test")
    public ConnectionFactory connectionFactory(MQConfigurationProperties configProperties) {
        return new MQConnectionFactoryFactory(configProperties, null)
                .createConnectionFactory(MQConnectionFactory.class);
    }

    /**
     * Бин для настройки контекста обмена сообщениями (тип, конвертация, транзакционность, кэширование, таймауты, обработка ошибок)
     * @param connectionFactory
     * @return
     */
    @Bean
    public DefaultJmsListenerContainerFactory jmsContainer(ConnectionFactory connectionFactory, PlatformTransactionManager transactionManager) {

        DefaultJmsListenerContainerFactory listenerContainer = new DefaultJmsListenerContainerFactory();
        //Включаем использование паттерна publisher/subscriber
        listenerContainer.setPubSubDomain(false);
        //Устанавливаем connectionFactory
        listenerContainer.setConnectionFactory(connectionFactory);
        //Обозначаем логику обработки ошибок
        listenerContainer.setErrorHandler(error -> log.error("Error!!! has occurred in jms listener", error));
        //Можно указать конвертер для автоматического биндинга сообщения в нужный формат, но пока сделаем без него
        //listenerContainer.setMessageConverter(messageConverter);
        listenerContainer.setTransactionManager(transactionManager);

        return listenerContainer;
    }

    @Bean
    public PlatformTransactionManager transactionManager(ConnectionFactory connectionFactory) {
        JmsTransactionManager transactionManager = new JmsTransactionManager();
        transactionManager.setConnectionFactory(connectionFactory);
        return transactionManager;
    }
}
