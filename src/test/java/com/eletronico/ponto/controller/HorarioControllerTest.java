package com.eletronico.ponto.controller;

import com.eletronico.ponto.model.Horario;
import com.eletronico.ponto.service.HorarioService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class HorarioControllerTest {

    @Mock
    private HorarioService horarioService;

    @InjectMocks
    private HorarioController horarioController;

    private Horario horario;

    @BeforeEach
    void setUp() {
        horario = new Horario(1L, LocalTime.of(9, 0), LocalTime.of(17, 0));
    }

    @Test
    void cadastrarHorario_ReturnsSuccess() throws Exception {
        doNothing().when(horarioService).adicionarHorario(horario);

        Horario horarioTeste = new Horario(1L, LocalTime.of(9, 0), LocalTime.of(17, 0));
        ResponseEntity<String> response = horarioController.cadastrarHorario(horarioTeste);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Horário cadastrado com sucesso!", response.getBody());
        verify(horarioService, times(1)).adicionarHorario(horarioTeste);
    }


    @Test
    void listarHorarios_WhenNoHorarios_ReturnsEmptyList() throws Exception {
        when(horarioService.listarHorario()).thenReturn(Collections.emptyList());

        ResponseEntity<?> response = horarioController.listarHorarios();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertTrue(((List<?>)response.getBody()).isEmpty());
    }

    @Test
    void listarHorarios_WhenHorariosExist_ReturnsHorarioList() throws Exception {
        when(horarioService.listarHorario()).thenReturn(Collections.singletonList(horario));

        ResponseEntity<?> response = horarioController.listarHorarios();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(1, ((List<?>)response.getBody()).size());
    }

    @Test
    void deletarHorarios_ReturnsSuccess() throws Exception {
        doNothing().when(horarioService).deleteAll();

        ResponseEntity<?> response = horarioController.deletarHorarios();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Horários deletadas", response.getBody());
        verify(horarioService, times(1)).deleteAll();
    }
}
