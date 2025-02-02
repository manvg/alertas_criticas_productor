package com.alertas_medicas.alertas_criticas_productor.service;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.alertas_medicas.alertas_criticas_productor.model.SenalesVitales;

@Service
public class RabbitMQService {

    private final RabbitTemplate rabbitTemplate;

    @Value("${rabbitmq.queue.alertas}")
    private String alertasQueue;

    public RabbitMQService(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void sendAlertaCritica(SenalesVitales senales) {
        rabbitTemplate.convertAndSend(alertasQueue, senales);
    }
}