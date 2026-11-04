package com.example.mensageria.infraestrutura;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.AmqpRejectAndDontRequeueException;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class MensagemConsumer {

    private final ObjectMapper objectMapper;
    private final LogMensagemRepository logRepository;
    private final String fila;

    public MensagemConsumer(ObjectMapper objectMapper, LogMensagemRepository logRepository,
                            @org.springframework.beans.factory.annotation.Value("${mensageria.rabbitmq.queue}")
                            String fila) {
        this.objectMapper = objectMapper;
        this.logRepository = logRepository;
        this.fila = fila;
    }

    @RabbitListener(id = "mensageriaConsumer", queues = "${mensageria.rabbitmq.queue}")
    public void receber(String mensagem) {
        try {
            JsonNode evento = objectMapper.readTree(mensagem);
            String entidade = text(evento, "entidade",
                    evento.has("carro") ? "CARRO" : "DESCONHECIDA");
            String operacao = text(evento, "operacao", "DESCONHECIDA");
            String pessoa = text(evento, "pessoa", "sistema");
            logRepository.save(new LogMensagem(
                    "RECEBIDA", fila, entidade, operacao, pessoa, mensagem, LocalDateTime.now()));
        } catch (Exception e) {
            // Mensagens inválidas não devem voltar indefinidamente para a fila.
            logRepository.save(new LogMensagem(
                    "RECEBIDA_ERRO", fila, "DESCONHECIDA", "ERRO", "sistema",
                    mensagem, LocalDateTime.now()));
            throw new AmqpRejectAndDontRequeueException(
                    "Mensagem inválida recebida na fila " + fila, e);
        }
    }

    private String text(JsonNode node, String field, String fallback) {
        JsonNode value = node.get(field);
        return value == null || value.isNull() ? fallback : value.asText();
    }
}
