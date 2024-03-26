package com.eletronico.ponto.service;

import com.eletronico.ponto.model.Marcacao;
import com.eletronico.ponto.repository.MarcacaoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalTime;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
public class MarcacaoServiceTest {

    @Mock
    private MarcacaoRepository marcacaoRepository;

    @InjectMocks
    private MarcacaoService marcacaoService;

    private Marcacao marcacao1, marcacao2;

    @BeforeEach
    void setUp() {
        marcacao1 = new Marcacao(1L, LocalTime.of(8, 0), LocalTime.of(16, 0));
        marcacao2 = new Marcacao(2L, LocalTime.of(9, 0), LocalTime.of(17, 0));
    }

    @Test
    void listarMarcacao_NenhumaCadastrada() throws Exception {
        when(marcacaoRepository.findAll()).thenReturn(Collections.emptyList());

        Exception exception = assertThrows(Exception.class, () -> {
            marcacaoService.listarMarcacao();
        });

        assertEquals("Nenhuma marcação cadastrada para ser listado.", exception.getMessage());
    }

    @Test
    void adicionarEListarMarcacao() throws Exception {
        when(marcacaoRepository.save(any(Marcacao.class))).thenReturn(marcacao1);
        when(marcacaoRepository.findAll()).thenReturn(Collections.singletonList(marcacao1));

        Marcacao savedMarcacao = marcacaoService.addMarcacao(marcacao1);
        List<Marcacao> result = marcacaoService.listarMarcacao();

        assertSame(marcacao1, savedMarcacao);
        assertFalse(result.isEmpty());
        assertEquals(1, result.size());
        assertEquals(marcacao1, result.get(0));
    }

    @Test
    void deletarTodasMarcacoes() {
        doNothing().when(marcacaoRepository).deleteAll();

        marcacaoService.deleteAll();

        verify(marcacaoRepository, times(1)).deleteAll();
    }
}
