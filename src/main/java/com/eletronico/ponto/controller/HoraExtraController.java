package com.eletronico.ponto.controller;

import com.eletronico.ponto.model.Periodo;
import com.eletronico.ponto.service.HoraExtraService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/horas_extras")
public class HoraExtraController {
    @Autowired
    private HoraExtraService service;

    @GetMapping("/listar")
    public ResponseEntity<?> listarHorasExtras() {
        try {
            List<Periodo> horaExtra = service.calcularHoraExtraParaTodos();
            return ResponseEntity.ok(horaExtra);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
}
