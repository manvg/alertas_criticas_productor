package com.alertas_medicas.alertas_criticas_productor.service;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.alertas_medicas.alertas_criticas_productor.model.SignosVitales;

@Service
public class RabbitMQService {

    private final RabbitTemplate rabbitTemplate;

    @Value("${rabbitmq.exchange.alertas}")
    private String alertasExchange;

    @Value("${rabbitmq.routingkey.alertas_criticas}")
    private String alertasCriticasRoutingKey;

    @Value("${rabbitmq.routingkey.alertas_no_criticas}")
    private String alertasNoCriticasRoutingKey;

    public RabbitMQService(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void enviarAlertaCritica(SignosVitales senales) {
        rabbitTemplate.convertAndSend(alertasExchange, alertasCriticasRoutingKey, senales);
        System.out.println("Alerta crítica enviada a RabbitMQ -> Exchange: " + alertasExchange + ", Routing Key: " + alertasCriticasRoutingKey);
    }

    public void enviarAlertaNoCritica(SignosVitales senales) {
        rabbitTemplate.convertAndSend(alertasExchange, alertasNoCriticasRoutingKey, senales);
        System.out.println("Alerta no crítica enviada a RabbitMQ -> Exchange: " + alertasExchange + ", Routing Key: " + alertasNoCriticasRoutingKey);
    }
}