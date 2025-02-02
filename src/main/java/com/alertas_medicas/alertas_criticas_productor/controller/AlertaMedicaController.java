package com.alertas_medicas.alertas_criticas_productor.controller;

import com.alertas_medicas.alertas_criticas_productor.model.SignosVitales;
import com.alertas_medicas.alertas_criticas_productor.service.RabbitMQService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/alertas")
public class AlertaMedicaController {

    private final RabbitMQService rabbitMQService;

    public AlertaMedicaController(RabbitMQService rabbitMQService) {
        this.rabbitMQService = rabbitMQService;
    }

    @PostMapping("/enviar")
    public ResponseEntity<String> enviarAlerta(@RequestBody @Valid SignosVitales senales) {
        if (esAlertaCritica(senales)) {
            rabbitMQService.sendAlertaCritica(senales);
            return ResponseEntity.ok("Alerta crítica enviada: " + senales);
        }
        return ResponseEntity.ok("Signos vitales dentro de los rangos normales.");
    }

    private boolean esAlertaCritica(SignosVitales senales) {
        return senales.getRitmoCardiaco() < 50 || senales.getRitmoCardiaco() > 120 ||
               senales.getTemperatura() < 35.0 || senales.getTemperatura() > 38.0 ||
               senales.getPresionSistolica() < 90 || senales.getPresionSistolica() > 140 ||
               senales.getPresionDiastolica() < 60 || senales.getPresionDiastolica() > 90;
    }
}