package com.example.mensageria.carro;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class CarroService {

    private final CarroRepository repository;
    private final CarroMensagemPublisher publisher;

    public CarroService(CarroRepository repository, CarroMensagemPublisher publisher) {
        this.repository = repository;
        this.publisher = publisher;
    }

    public List<Carro> listar(String pessoa) {
        List<Carro> carros = repository.findAll();
        carros.forEach(carro -> publisher.publicar(carro, pessoa, "LISTAR"));
        return carros;
    }

    public Carro buscar(Long id, String pessoa) {
        Carro carro = repository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Carro não encontrado"));
        publisher.publicar(carro, pessoa, "BUSCAR");
        return carro;
    }

    public Carro criar(Carro carro, String pessoa) {
        Carro salvo = repository.save(carro);
        publisher.publicar(salvo, pessoa, "CRIAR");
        return salvo;
    }

    public Carro atualizar(Long id, Carro dados, String pessoa) {
        Carro carro = buscarSemPublicar(id);
        carro.setMarca(dados.getMarca());
        carro.setModelo(dados.getModelo());
        carro.setAno(dados.getAno());
        carro.setPlaca(dados.getPlaca());
        Carro atualizado = repository.save(carro);
        publisher.publicar(atualizado, pessoa, "ATUALIZAR");
        return atualizado;
    }

    public void excluir(Long id, String pessoa) {
        Carro carro = buscarSemPublicar(id);
        repository.delete(carro);
        publisher.publicar(carro, pessoa, "EXCLUIR");
    }

    private Carro buscarSemPublicar(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Carro não encontrado"));
    }
}
