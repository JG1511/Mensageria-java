package com.example.mensageria.contabilidade;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping({"/contabilidades", "/contabilidade"})
public class ContabilidadeController {

    private final ContabilidadeService service;

    public ContabilidadeController(ContabilidadeService service) { this.service = service; }

    @GetMapping
    public List<Contabilidade> listar(@RequestHeader(value = "X-Pessoa", defaultValue = "sistema") String pessoa) {
        return service.listar(pessoa);
    }

    @GetMapping("/{id}")
    public Contabilidade buscar(@PathVariable Long id,
                                @RequestHeader(value = "X-Pessoa", defaultValue = "sistema") String pessoa) {
        return service.buscar(id, pessoa);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Contabilidade criar(@RequestBody Contabilidade item,
                               @RequestHeader(value = "X-Pessoa", defaultValue = "sistema") String pessoa) {
        return service.criar(item, pessoa);
    }

    @PutMapping("/{id}")
    public Contabilidade atualizar(@PathVariable Long id, @RequestBody Contabilidade item,
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
