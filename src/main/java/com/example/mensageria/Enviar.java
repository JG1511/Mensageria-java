package com.example.mensageria;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import java.nio.charset.StandardCharsets;

public class Enviar {

    public static void main(String[] args) {
        enviarMensagem(
                "Trabalho realizado por: SEU NOME e NOME DA SUA DUPLA",
                filaPadrao()
        );
    }

    public static void enviarMensagem(String message, String queue) {
        ConnectionFactory factory = new ConnectionFactory();

        try {
            factory.setHost(System.getenv().getOrDefault("MENSAGERIA_RABBITMQ_HOST", "10.209.1.81"));
            factory.setPort(Integer.parseInt(System.getenv().getOrDefault("MENSAGERIA_RABBITMQ_PORT", "5672")));
            factory.setUsername(System.getenv().getOrDefault("MENSAGERIA_RABBITMQ_USERNAME", "admin"));
            factory.setPassword(System.getenv().getOrDefault("MENSAGERIA_RABBITMQ_PASSWORD", "admin"));
        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }

        try (Connection connection = factory.newConnection(); Channel channel = connection.createChannel()) {
            channel.queueDeclare(queue, true, false, false, null);
            channel.basicPublish("", queue, null, message.getBytes(StandardCharsets.UTF_8));
            System.out.println("MENSAGEM ENVIADA = '" + message + "'");
        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }

    private static String filaPadrao() {
        return System.getenv().getOrDefault("MENSAGERIA_RABBITMQ_QUEUE", "guilherme_brunno");
    }
}
