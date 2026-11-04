package com.example.mensageria.testedriver;

import com.example.mensageria.infraestrutura.CadastroService;
import com.example.mensageria.infraestrutura.MensagemPublisher;
import org.springframework.stereotype.Service;

@Service
public class TesteDriverService extends CadastroService<TesteDriver> {

    public TesteDriverService(TesteDriverRepository repository, MensagemPublisher publisher) {
        super(repository, publisher, "TESTE_DRIVER");
    }
}
