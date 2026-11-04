package com.example.mensageria.adm;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping({"/adms", "/adm"})
public class AdmController {

    private final AdmService service;

    public AdmController(AdmService service) { this.service = service; }

    @GetMapping
    public List<Adm> listar(@RequestHeader(value = "X-Pessoa", defaultValue = "sistema") String pessoa) {
        return service.listar(pessoa);
    }

    @GetMapping("/{id}")
    public Adm buscar(@PathVariable Long id,
                      @RequestHeader(value = "X-Pessoa", defaultValue = "sistema") String pessoa) {
        return service.buscar(id, pessoa);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Adm criar(@RequestBody Adm item,
                     @RequestHeader(value = "X-Pessoa", defaultValue = "sistema") String pessoa) {
        return service.criar(item, pessoa);
    }

    @PutMapping("/{id}")
    public Adm atualizar(@PathVariable Long id, @RequestBody Adm item,
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
