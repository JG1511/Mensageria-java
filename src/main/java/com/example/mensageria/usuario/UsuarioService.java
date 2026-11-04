package com.example.mensageria.usuario;

import com.example.mensageria.infraestrutura.CadastroService;
import com.example.mensageria.infraestrutura.MensagemPublisher;
import org.springframework.stereotype.Service;

@Service
public class UsuarioService extends CadastroService<Usuario> {

    public UsuarioService(UsuarioRepository repository, MensagemPublisher publisher) {
        super(repository, publisher, "USUARIO");
    }
}
