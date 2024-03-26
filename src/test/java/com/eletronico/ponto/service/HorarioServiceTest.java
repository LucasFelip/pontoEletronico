package com.eletronico.ponto.service;

import com.eletronico.ponto.model.Horario;
import com.eletronico.ponto.repository.HorarioRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
public class HorarioServiceTest {

    @Mock
    private HorarioRepository horarioRepository;

    @InjectMocks
    private HorarioService horarioService;

    private Horario horario1, horario2, horario3;

    @BeforeEach
    void setUp() {
        horario1 = new Horario(1L, LocalTime.of(8, 0), LocalTime.of(16, 0));
        horario2 = new Horario(2L, LocalTime.of(9, 0), LocalTime.of(17, 0));
        horario3 = new Horario(3L, LocalTime.of(10, 0), LocalTime.of(18, 0));
    }

    @Test
    void adicionarHorario_LimiteAtingido() {
        when(horarioRepository.findAll()).thenReturn(Arrays.asList(horario1, horario2, horario3));

        Exception exception = assertThrows(Exception.class, () -> {
            horarioService.adicionarHorario(horario3);
        });

        assertEquals("Limite de 3 horários já alcançado. Não é possível adicionar mais horários.", exception.getMessage());
    }

    @Test
    void listarHorario_NenhumCadastrado() {
        when(horarioRepository.findAll()).thenReturn(Collections.emptyList());

        Exception exception = assertThrows(Exception.class, () -> {
            horarioService.listarHorario();
        });

        assertEquals("Nenhum horário cadastrado para ser listado.", exception.getMessage());
    }

    @Test
    void adicionarEListarHorario() throws Exception {
        when(horarioRepository.save(any(Horario.class))).thenReturn(horario1);
        when(horarioRepository.findAll()).thenReturn(Collections.singletonList(horario1));

        horarioService.adicionarHorario(horario1);
        List<Horario> result = horarioService.listarHorario();

        assertFalse(result.isEmpty());
        assertEquals(1, result.size());
        assertEquals(horario1, result.get(0));
    }

    @Test
    void deletarTodosHorarios() {
        doNothing().when(horarioRepository).deleteAll();

        horarioService.deleteAll();

        verify(horarioRepository, times(1)).deleteAll();
    }
}
