package com.eletronico.ponto.controller;

import com.eletronico.ponto.model.Marcacao;
import com.eletronico.ponto.service.MarcacaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/marcacoes")
public class MarcacaoController {
    @Autowired
    private MarcacaoService service;

    @PostMapping("/cadastrar")
    public ResponseEntity<String> cadastrarMarcacao(@RequestBody Marcacao marcacao) {
        try {
            service.addMarcacao(marcacao);
            return ResponseEntity.ok("Marcação cadastrado com sucesso!");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @GetMapping("/listar")
    public ResponseEntity<?> listarMarcacao() {
        try {
            List<Marcacao> marcacao = service.listarMarcacao();
            return ResponseEntity.ok(marcacao);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @DeleteMapping("/deletar")
    public ResponseEntity<?> deletarMarcacoes() {
        try {
            service.deleteAll();
            return ResponseEntity.ok("Marcações deletadas");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
}
