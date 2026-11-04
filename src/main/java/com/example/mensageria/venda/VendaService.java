package com.example.mensageria.venda;

import com.example.mensageria.infraestrutura.CadastroService;
import com.example.mensageria.infraestrutura.MensagemPublisher;
import org.springframework.stereotype.Service;

@Service
public class VendaService extends CadastroService<Venda> {

    public VendaService(VendaRepository repository, MensagemPublisher publisher) {
        super(repository, publisher, "VENDA");
    }
}
