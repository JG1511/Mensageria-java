package com.example.mensageria.contabilidade;

import com.example.mensageria.infraestrutura.CadastroService;
import com.example.mensageria.infraestrutura.MensagemPublisher;
import org.springframework.stereotype.Service;

@Service
public class ContabilidadeService extends CadastroService<Contabilidade> {

    public ContabilidadeService(ContabilidadeRepository repository, MensagemPublisher publisher) {
        super(repository, publisher, "CONTABILIDADE");
    }
}
