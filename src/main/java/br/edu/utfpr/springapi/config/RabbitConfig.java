package br.edu.utfpr.springapi.config;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitConfig {

    public static final String EXCHANGE_NAME = "spray.exchange";
    public static final String APLICACAO_QUEUE = "aplicacao.queue";
    public static final String APLICACAO_ROUTING_KEY = "aplicacao.event";

    @Bean
    public DirectExchange exchange() {
        return new DirectExchange(EXCHANGE_NAME);
    }

    @Bean
    public Queue aplicacaoQueue() {
        return QueueBuilder.durable(APLICACAO_QUEUE).build();
    }

    @Bean
    public Binding binding(Queue aplicacaoQueue, DirectExchange exchange) {
        return BindingBuilder.bind(aplicacaoQueue).to(exchange).with(APLICACAO_ROUTING_KEY);
    }

    @Bean
    public MessageConverter jsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
        RabbitTemplate template = new RabbitTemplate(connectionFactory);
        template.setMessageConverter(jsonMessageConverter());
        return template;
    }
}