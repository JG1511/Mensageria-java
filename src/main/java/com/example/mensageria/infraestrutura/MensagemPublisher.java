package com.example.mensageria.infraestrutura;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class MensagemPublisher {

    private final RabbitTemplate rabbitTemplate;
    private final ObjectMapper objectMapper;
    private final LogMensagemRepository logRepository;
    private final String fila;

    public MensagemPublisher(RabbitTemplate rabbitTemplate, ObjectMapper objectMapper,
                             LogMensagemRepository logRepository,
                             @Value("${mensageria.rabbitmq.queue}") String fila) {
        this.rabbitTemplate = rabbitTemplate;
        this.objectMapper = objectMapper;
        this.logRepository = logRepository;
        this.fila = fila;
    }

    public void publicar(String entidade, Object dados, String pessoa, String operacao) {
        try {
            String mensagem = objectMapper.writeValueAsString(
                    new MensagemEvento(entidade, dados, pessoa, operacao, LocalDateTime.now().toString()));
            publicarJson(mensagem, entidade, pessoa, operacao);
        } catch (JsonProcessingException e) {
            throw new IllegalStateException("Não foi possível serializar o evento", e);
        }
    }

    public void publicarJson(String mensagem, String entidade, String pessoa, String operacao) {
        rabbitTemplate.convertAndSend(fila, mensagem);
        logRepository.save(new LogMensagem(
                "ENVIADA", fila, entidade, operacao, pessoa, mensagem, LocalDateTime.now()));
    }

    public String getFila() {
        return fila;
    }

    private record MensagemEvento(String entidade, Object dados, String pessoa,
                                  String operacao, String hora) {
    }
}
