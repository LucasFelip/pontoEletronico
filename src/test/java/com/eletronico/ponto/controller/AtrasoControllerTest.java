package com.eletronico.ponto.controller;

import com.eletronico.ponto.model.Periodo;
import com.eletronico.ponto.service.AtrasoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalTime;
import java.util.Collections;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class AtrasoControllerTest {

    @Mock
    private AtrasoService atrasoService;

    @InjectMocks
    private AtrasoController atrasoController;

    private Periodo periodoAtraso;

    @BeforeEach
    void setUp() {
        periodoAtraso = new Periodo(LocalTime.of(9, 0), LocalTime.of(9, 30));
    }

    @Test
    void listarAtrasos_ReturnsSuccess() {
        when(atrasoService.calcularAtrasoParaTodos()).thenReturn(Collections.singletonList(periodoAtraso));

        ResponseEntity<?> response = atrasoController.listarAtrasos();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(1, ((List<?>) response.getBody()).size());
        verify(atrasoService, times(1)).calcularAtrasoParaTodos();
    }

    @Test
    void listarAtrasos_ReturnsBadRequestOnException() {
        when(atrasoService.calcularAtrasoParaTodos()).thenThrow(new RuntimeException("Erro de serviço"));

        ResponseEntity<?> response = atrasoController.listarAtrasos();

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Erro de serviço", response.getBody());
        verify(atrasoService, times(1)).calcularAtrasoParaTodos();
    }
}
