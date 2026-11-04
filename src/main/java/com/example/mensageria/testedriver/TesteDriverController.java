package com.example.mensageria.testedriver;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping({"/teste-drivers", "/testes-driver", "/testes-drivers", "/teste-driver"})
public class TesteDriverController {

    private final TesteDriverService service;

    public TesteDriverController(TesteDriverService service) { this.service = service; }

    @GetMapping
    public List<TesteDriver> listar(@RequestHeader(value = "X-Pessoa", defaultValue = "sistema") String pessoa) {
        return service.listar(pessoa);
    }

    @GetMapping("/{id}")
    public TesteDriver buscar(@PathVariable Long id,
                              @RequestHeader(value = "X-Pessoa", defaultValue = "sistema") String pessoa) {
        return service.buscar(id, pessoa);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public TesteDriver criar(@RequestBody TesteDriver item,
                             @RequestHeader(value = "X-Pessoa", defaultValue = "sistema") String pessoa) {
        return service.criar(item, pessoa);
    }

    @PutMapping("/{id}")
    public TesteDriver atualizar(@PathVariable Long id, @RequestBody TesteDriver item,
                                 @RequestHeader(value = "X-Pessoa", defaultValue = "sistema") String pessoa) {
        return service.atualizar(id, item, pessoa);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void excluir(@PathVariable Long id,
                        @RequestHeader(value = "X-Pessoa", defaultValue = "sistema") String pessoa) {
        service.excluir(id, pessoa);
    }
}
