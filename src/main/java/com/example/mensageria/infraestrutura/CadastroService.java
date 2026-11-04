package com.example.mensageria.infraestrutura;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

public abstract class CadastroService<T extends Cadastro> {

    private final JpaRepository<T, Long> repository;
    private final MensagemPublisher publisher;
    private final String entidade;

    protected CadastroService(JpaRepository<T, Long> repository, MensagemPublisher publisher,
                              String entidade) {
        this.repository = repository;
        this.publisher = publisher;
        this.entidade = entidade;
    }

    public List<T> listar(String pessoa) {
        List<T> itens = repository.findAll();
        publisher.publicar(entidade, itens, pessoa, "LISTAR");
        return itens;
    }

    public T buscar(Long id, String pessoa) {
        T item = buscarSemPublicar(id);
        publisher.publicar(entidade, item, pessoa, "BUSCAR");
        return item;
    }

    public T criar(T item, String pessoa) {
        T salvo = repository.save(item);
        publisher.publicar(entidade, salvo, pessoa, "CRIAR");
        return salvo;
    }

    public T atualizar(Long id, T dados, String pessoa) {
        T item = buscarSemPublicar(id);
        item.setNome(dados.getNome());
        item.setDescricao(dados.getDescricao());
        item.setAtivo(dados.isAtivo());
        T atualizado = repository.save(item);
        publisher.publicar(entidade, atualizado, pessoa, "ATUALIZAR");
        return atualizado;
    }

    public void excluir(Long id, String pessoa) {
        T item = buscarSemPublicar(id);
        repository.delete(item);
        publisher.publicar(entidade, item, pessoa, "EXCLUIR");
    }

    private T buscarSemPublicar(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, entidade + " não encontrado"));
    }
}
