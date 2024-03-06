package com.eletronico.ponto.controller;

import com.eletronico.ponto.model.Periodo;
import com.eletronico.ponto.service.AtrasoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/atrasos")
public class AtrasoController {
    @Autowired
    private AtrasoService service;

    @GetMapping("/listar")
    public ResponseEntity<?> listarAtrasos() {
        try {
            List<Periodo> atraso = service.calcularAtrasoParaTodos();
            return ResponseEntity.ok(atraso);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
}
