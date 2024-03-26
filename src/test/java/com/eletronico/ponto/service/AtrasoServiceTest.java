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
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
public class AtrasoServiceTest {

    @Mock
    private HorarioRepository horarioRepository;

    @Mock
    private MarcacaoRepository marcacaoRepository;

    @InjectMocks
    private AtrasoService atrasoService;

    private Horario horarioNormal;
    private Marcacao marcacaoAtrasada;

    @BeforeEach
    void setUp() {
        horarioNormal = new Horario(1L, LocalTime.of(9, 0), LocalTime.of(17, 0));
        marcacaoAtrasada = new Marcacao(1L, LocalTime.of(9, 30), LocalTime.of(17, 30));
    }

    @Test
    void calcularAtrasoParaTodos_DeveRetornarAtrasos() {
        when(horarioRepository.findAll()).thenReturn(Arrays.asList(horarioNormal));
        when(marcacaoRepository.findAll()).thenReturn(Arrays.asList(marcacaoAtrasada));

        List<Periodo> atrasos = atrasoService.calcularAtrasoParaTodos();

        assertEquals(1, atrasos.size());
        assertEquals(LocalTime.of(9, 0), atrasos.get(0).getInicio());
        assertEquals(LocalTime.of(9, 30), atrasos.get(0).getFim());
    }
}
