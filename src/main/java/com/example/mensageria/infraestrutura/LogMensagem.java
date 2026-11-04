package com.example.mensageria.infraestrutura;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.Table;

import java.time.LocalDateTime;

@Entity
@Table(name = "mensageria_logs")
public class LogMensagem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 20)
    private String tipo;

    @Column(nullable = false, length = 200)
    private String fila;

    @Column(length = 100)
    private String entidade;

    @Column(length = 50)
    private String operacao;

    @Column(length = 200)
    private String pessoa;

    @Lob
    @Column(nullable = false)
    private String mensagem;

    @Column(nullable = false)
    private LocalDateTime dataHora;

    protected LogMensagem() {
    }

    public LogMensagem(String tipo, String fila, String entidade, String operacao,
                       String pessoa, String mensagem, LocalDateTime dataHora) {
        this.tipo = tipo;
        this.fila = fila;
        this.entidade = entidade;
        this.operacao = operacao;
        this.pessoa = pessoa;
        this.mensagem = mensagem;
        this.dataHora = dataHora;
    }

    public Long getId() {
        return id;
    }

    public String getTipo() {
        return tipo;
    }

    public String getFila() {
        return fila;
    }

    public String getEntidade() {
        return entidade;
    }

    public String getOperacao() {
        return operacao;
    }

    public String getPessoa() {
        return pessoa;
    }

    public String getMensagem() {
        return mensagem;
    }

    public LocalDateTime getDataHora() {
        return dataHora;
    }
}
