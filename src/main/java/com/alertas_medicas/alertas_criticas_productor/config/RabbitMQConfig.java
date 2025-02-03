package com.alertas_medicas.alertas_criticas_productor.config;

import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.amqp.core.*;

@Configuration
public class RabbitMQConfig {

    @Value("${rabbitmq.exchange.alertas}")
    private String alertasExchange;

    @Value("${rabbitmq.queue.alertas_criticas}")
    private String alertasCriticasQueue;

    @Value("${rabbitmq.queue.alertas_no_criticas}")
    private String alertasNoCriticasQueue;

    @Value("${rabbitmq.routingkey.alertas_criticas}")
    private String alertasCriticasRoutingKey;

    @Value("${rabbitmq.routingkey.alertas_no_criticas}")
    private String alertasNoCriticasRoutingKey;

    @Bean
    public DirectExchange exchange() {
        return new DirectExchange(alertasExchange);
    }

    @Bean
    public Queue alertasCriticasQueue() {
        return new Queue(alertasCriticasQueue, true);
    }

    @Bean
    public Queue alertasNoCriticasQueue() {
        return new Queue(alertasNoCriticasQueue, true);
    }

    @Bean
    public Binding bindingAlertasCriticas(Queue alertasCriticasQueue, DirectExchange exchange) {
        return BindingBuilder.bind(alertasCriticasQueue).to(exchange).with(alertasCriticasRoutingKey);
    }

    @Bean
    public Binding bindingAlertasNoCriticas(Queue alertasNoCriticasQueue, DirectExchange exchange) {
        return BindingBuilder.bind(alertasNoCriticasQueue).to(exchange).with(alertasNoCriticasRoutingKey);
    }

    @Bean
    public Jackson2JsonMessageConverter jsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }
}