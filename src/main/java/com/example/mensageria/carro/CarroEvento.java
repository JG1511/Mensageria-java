package com.example.mensageria.carro;

public class CarroEvento {

    private final Carro carro;
    private final String hora;
    private final String pessoa;
    private final String operacao;

    public CarroEvento(Carro carro, String hora, String pessoa, String operacao) {
        this.carro = carro;
        this.hora = hora;
        this.pessoa = pessoa;
        this.operacao = operacao;
    }

    public Carro getCarro() {
        return carro;
    }

    public String getHora() {
        return hora;
    }

    public String getPessoa() {
        return pessoa;
    }

    public String getOperacao() {
        return operacao;
    }
}
