package com.example.mensageria.carro;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.example.mensageria.infraestrutura.MensagemPublisher;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class CarroMensagemPublisher {

    private final MensagemPublisher mensagemPublisher;
    private final ObjectMapper objectMapper;

    public CarroMensagemPublisher(
            MensagemPublisher mensagemPublisher,
            ObjectMapper objectMapper) {
        this.mensagemPublisher = mensagemPublisher;
        this.objectMapper = objectMapper;
    }

    public void publicar(Carro carro, String pessoa, String operacao) {
        try {
            String mensagem = objectMapper.writeValueAsString(
                    new CarroEvento(carro, LocalDateTime.now().toString(), pessoa, operacao));
            mensagemPublisher.publicarJson(mensagem, "CARRO", pessoa, operacao);
        } catch (JsonProcessingException e) {
            throw new IllegalStateException("Não foi possível serializar o evento do carro", e);
        }
    }
}
