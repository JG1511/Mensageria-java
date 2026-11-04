package com.example.mensageria.adm;

import com.example.mensageria.infraestrutura.CadastroService;
import com.example.mensageria.infraestrutura.MensagemPublisher;
import org.springframework.stereotype.Service;

@Service
public class AdmService extends CadastroService<Adm> {

    public AdmService(AdmRepository repository, MensagemPublisher publisher) {
        super(repository, publisher, "ADM");
    }
}
