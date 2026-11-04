package com.example.mensageria.carro;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/carros")
public class CarroController {

    private final CarroService service;

    public CarroController(CarroService service) {
        this.service = service;
    }

    @GetMapping
    public List<Carro> listar(
            @RequestHeader(value = "X-Pessoa", defaultValue = "sistema") String pessoa) {
        return service.listar(pessoa);
    }

    @GetMapping("/{id}")
    public Carro buscar(
            @PathVariable Long id,
            @RequestHeader(value = "X-Pessoa", defaultValue = "sistema") String pessoa) {
        return service.buscar(id, pessoa);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Carro criar(
            @RequestBody Carro carro,
            @RequestHeader(value = "X-Pessoa", defaultValue = "sistema") String pessoa) {
        return service.criar(carro, pessoa);
    }

    @PutMapping("/{id}")
    public Carro atualizar(
            @PathVariable Long id,
            @RequestBody Carro carro,
            @RequestHeader(value = "X-Pessoa", defaultValue = "sistema") String pessoa) {
        return service.atualizar(id, carro, pessoa);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void excluir(
            @PathVariable Long id,
            @RequestHeader(value = "X-Pessoa", defaultValue = "sistema") String pessoa) {
        service.excluir(id, pessoa);
    }
}
