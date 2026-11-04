package com.example.mensageria.carro;

import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.QueueBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMqConfig {

    @Bean
    public Queue carrosOperacoesQueue(@Value("${mensageria.rabbitmq.queue}") String queue) {
        return QueueBuilder.durable(queue).build();
    }
}
