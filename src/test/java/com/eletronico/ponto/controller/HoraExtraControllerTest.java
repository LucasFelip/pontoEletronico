package com.eletronico.ponto.controller;

import com.eletronico.ponto.model.Periodo;
import com.eletronico.ponto.service.HoraExtraService;
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
public class HoraExtraControllerTest {

    @Mock
    private HoraExtraService horaExtraService;

    @InjectMocks
    private HoraExtraController horaExtraController;

    private Periodo periodoHoraExtra;

    @BeforeEach
    void setUp() {
        periodoHoraExtra = new Periodo(LocalTime.of(17, 0), LocalTime.of(18, 0));
    }

    @Test
    void listarHorasExtras_ReturnsSuccess() {
        when(horaExtraService.calcularHorasExtrasParaTodos()).thenReturn(Collections.singletonList(periodoHoraExtra));

        ResponseEntity<?> response = horaExtraController.listarHorasExtras();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(1, ((List<?>) response.getBody()).size());
        verify(horaExtraService, times(1)).calcularHorasExtrasParaTodos();
    }

    @Test
    void listarHorasExtras_ReturnsBadRequestOnException() {
        when(horaExtraService.calcularHorasExtrasParaTodos()).thenThrow(new RuntimeException("Erro de serviço"));

        ResponseEntity<?> response = horaExtraController.listarHorasExtras();

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Erro de serviço", response.getBody());
        verify(horaExtraService, times(1)).calcularHorasExtrasParaTodos();
    }
}
