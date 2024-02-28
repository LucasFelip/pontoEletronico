package com.eletronico.ponto.repository;

import com.eletronico.ponto.model.Marcacao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MarcacaoRepository extends JpaRepository<Marcacao, Long> {
}