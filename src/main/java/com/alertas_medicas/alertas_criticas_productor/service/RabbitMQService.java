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

    @Value("${rabbitmq.routingkey.alertas}")
    private String alertasRoutingKey;

    public RabbitMQService(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void sendAlertaCritica(SignosVitales senales) {
        rabbitTemplate.convertAndSend(alertasExchange, alertasRoutingKey, senales);
    }
}