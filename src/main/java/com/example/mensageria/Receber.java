package com.example.mensageria;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;

import java.io.IOException;
import java.nio.charset.StandardCharsets;


public class Receber {
    static Enviar l = new Enviar();

    public static void main(String[] args) {
        try {
            lerMensagem();
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    public static void lerMensagem() throws IOException {
        ConnectionFactory factory;
        Connection connection;
        Channel channel;

        String queue = System.getenv().getOrDefault("MENSAGERIA_RABBITMQ_QUEUE", "guilherme_brunno");
        try {
            factory = new ConnectionFactory();
            factory.setHost(System.getenv().getOrDefault("MENSAGERIA_RABBITMQ_HOST", "10.209.1.81"));
            factory.setPort(Integer.parseInt(System.getenv().getOrDefault("MENSAGERIA_RABBITMQ_PORT", "5672")));
            factory.setUsername(System.getenv().getOrDefault("MENSAGERIA_RABBITMQ_USERNAME", "admin"));
            factory.setPassword(System.getenv().getOrDefault("MENSAGERIA_RABBITMQ_PASSWORD", "admin"));

            connection = factory.newConnection();
            channel = connection.createChannel();

            channel.queueDeclare(queue, true, false, false, null);

            DeliverCallback deliverCallbackQuestion = (consumerTag, delivery) -> {
                String message = new String(delivery.getBody(), StandardCharsets.UTF_8);
                System.out.println("MENSAGEM RECEBIDA = '" + message + "'");

                String[] partes = message.split(",");
                if (partes.length > 1 && "12345".equals(partes[1])) {
                    Enviar.enviarMensagem("true", partes[0]);
                }
            };

            channel.basicConsume(queue, true, deliverCallbackQuestion, consumerTag -> {
            });


        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
