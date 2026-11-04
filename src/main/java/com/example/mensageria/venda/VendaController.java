package com.example.mensageria.venda;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping({"/vendas", "/venda"})
public class VendaController {

    private final VendaService service;

    public VendaController(VendaService service) { this.service = service; }

    @GetMapping
    public List<Venda> listar(@RequestHeader(value = "X-Pessoa", defaultValue = "sistema") String pessoa) {
        return service.listar(pessoa);
    }

    @GetMapping("/{id}")
    public Venda buscar(@PathVariable Long id,
                        @RequestHeader(value = "X-Pessoa", defaultValue = "sistema") String pessoa) {
        return service.buscar(id, pessoa);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Venda criar(@RequestBody Venda item,
                       @RequestHeader(value = "X-Pessoa", defaultValue = "sistema") String pessoa) {
        return service.criar(item, pessoa);
    }

    @PutMapping("/{id}")
    public Venda atualizar(@PathVariable Long id, @RequestBody Venda item,
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
