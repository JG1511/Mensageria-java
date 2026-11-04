package com.example.mensageria.infraestrutura;

/**
 * Campos comuns aos cadastros da aplicação.
 */
public interface Cadastro {

    Long getId();

    String getNome();

    void setNome(String nome);

    String getDescricao();

    void setDescricao(String descricao);

    boolean isAtivo();

    default boolean getAtivo() {
        return isAtivo();
    }

    void setAtivo(boolean ativo);
}
