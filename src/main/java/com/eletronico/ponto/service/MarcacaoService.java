package com.eletronico.ponto.service;

import com.eletronico.ponto.model.Horario;
import com.eletronico.ponto.model.Marcacao;
import com.eletronico.ponto.repository.MarcacaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MarcacaoService {
    @Autowired
    MarcacaoRepository repository;

    public List<Marcacao> listarMarcacao() throws Exception {
        List<Marcacao> todasMarcacoes = findAll();
        if (todasMarcacoes.isEmpty()) {
            throw new Exception("Nenhuma marcação cadastrada para ser listado.");
        }
        return todasMarcacoes;
    }

    public Marcacao addMarcacao(Marcacao marcacao) {
        return repository.save(marcacao);
    }

    public List<Marcacao> findAll() {
        return repository.findAll();
    }
}
