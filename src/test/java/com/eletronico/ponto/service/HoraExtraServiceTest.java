package com.eletronico.ponto.service;

import com.eletronico.ponto.model.Horario;
import com.eletronico.ponto.model.Marcacao;
import com.eletronico.ponto.model.Periodo;
import com.eletronico.ponto.repository.HorarioRepository;
import com.eletronico.ponto.repository.MarcacaoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
public class HoraExtraServiceTest {

    @Mock
    private HorarioRepository horarioRepository;

    @Mock
    private MarcacaoRepository marcacaoRepository;

    @InjectMocks
    private HoraExtraService horaExtraService;

    private Horario horarioDiurno;
    private Marcacao marcacaoComHoraExtra;

    @BeforeEach
    void setUp() {
        horarioDiurno = new Horario(1L, LocalTime.of(9, 0), LocalTime.of(17, 0));
        marcacaoComHoraExtra = new Marcacao(1L, LocalTime.of(8, 0), LocalTime.of(18, 0));
    }

    @Test
    void calcularHorasExtrasParaTodos_DeveRetornarHorasExtras() {
        List<Horario> todosHorarios = new ArrayList<>();
        todosHorarios.add(horarioDiurno);
        List<Marcacao> todasMarcacoes = new ArrayList<>();
        todasMarcacoes.add(marcacaoComHoraExtra);

        when(horarioRepository.findAll()).thenReturn(todosHorarios);
        when(marcacaoRepository.findAll()).thenReturn(todasMarcacoes);

        List<Periodo> resultado = horaExtraService.calcularHoraExtraParaTodos();

        assertEquals(2, resultado.size());
        assertEquals(LocalTime.of(8, 0), resultado.get(0).getInicio());
        assertEquals(LocalTime.of(9, 0), resultado.get(0).getFim());
        assertEquals(LocalTime.of(17, 0), resultado.get(1).getInicio());
        assertEquals(LocalTime.of(18, 0), resultado.get(1).getFim());
    }
}
