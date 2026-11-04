package com.example.mensageria.infraestrutura;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.amqp.rabbit.core.RabbitTemplate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

class MensageriaInfraestruturaTest {

    @Test
    void publicaERegistraEventoEnviado() {
        RabbitTemplate rabbitTemplate = mock(RabbitTemplate.class);
        LogMensagemRepository repository = mock(LogMensagemRepository.class);
        MensagemPublisher publisher = new MensagemPublisher(
                rabbitTemplate, new ObjectMapper(), repository, "fila-teste");

        publisher.publicar("USUARIO", "dados", "maria", "CRIAR");

        verify(rabbitTemplate).convertAndSend(eq("fila-teste"), any(String.class));
        verify(repository).save(argThat(log ->
                "ENVIADA".equals(log.getTipo())
                        && "USUARIO".equals(log.getEntidade())
                        && "maria".equals(log.getPessoa())));
    }

    @Test
    void consumidorRegistraMensagemRecebida() {
        LogMensagemRepository repository = mock(LogMensagemRepository.class);
        MensagemConsumer consumer = new MensagemConsumer(
                new ObjectMapper(), repository, "fila-teste");

        consumer.receber("""
                {"entidade":"VENDA","operacao":"ATUALIZAR","pessoa":"joao","dados":{}}
                """);

        verify(repository).save(argThat(log -> {
            assertEquals("RECEBIDA", log.getTipo());
            return "VENDA".equals(log.getEntidade()) && "joao".equals(log.getPessoa());
        }));
    }
}
