package com.eletronico.ponto.controller;

import com.eletronico.ponto.model.Horario;
import com.eletronico.ponto.service.HorarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/horarios")
public class HorarioController {
    @Autowired
    private HorarioService service;

    @PostMapping("/cadastrar")
    public ResponseEntity<String> cadastrarHorario(@RequestBody Horario horario) {
        try {
            service.adicionarHorario(horario);
            return ResponseEntity.ok("Horário cadastrado com sucesso!");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @GetMapping("/listar")
    public ResponseEntity<?> listarHorarios() {
        try {
            List<Horario> horarios = service.listarHorario();
            return ResponseEntity.ok(horarios);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @DeleteMapping("/deletar")
    public ResponseEntity<?> deletarHorarios() {
        try {
            service.deleteAll();
            return ResponseEntity.ok("Horários deletadas");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
}
