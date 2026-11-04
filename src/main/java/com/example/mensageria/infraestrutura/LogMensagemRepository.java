package com.example.mensageria.infraestrutura;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LogMensagemRepository extends JpaRepository<LogMensagem, Long> {

    List<LogMensagem> findAllByOrderByDataHoraDesc();
}
