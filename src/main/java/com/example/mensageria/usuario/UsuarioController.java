package com.example.mensageria.usuario;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    private final UsuarioService service;

    public UsuarioController(UsuarioService service) { this.service = service; }

    @GetMapping
    public List<Usuario> listar(@RequestHeader(value = "X-Pessoa", defaultValue = "sistema") String pessoa) {
        return service.listar(pessoa);
    }

    @GetMapping("/{id}")
    public Usuario buscar(@PathVariable Long id,
                          @RequestHeader(value = "X-Pessoa", defaultValue = "sistema") String pessoa) {
        return service.buscar(id, pessoa);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Usuario criar(@RequestBody Usuario item,
                         @RequestHeader(value = "X-Pessoa", defaultValue = "sistema") String pessoa) {
        return service.criar(item, pessoa);
    }

    @PutMapping("/{id}")
    public Usuario atualizar(@PathVariable Long id, @RequestBody Usuario item,
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
