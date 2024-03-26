package com.eletronico.ponto.controller;

import com.eletronico.ponto.model.Marcacao;
import com.eletronico.ponto.service.MarcacaoService;
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
public class MarcacaoControllerTest {

    @Mock
    private MarcacaoService marcacaoService;

    @InjectMocks
    private MarcacaoController marcacaoController;

    private Marcacao marcacao;

    @BeforeEach
    void setUp() {
        marcacao = new Marcacao(1L, LocalTime.of(9, 0), LocalTime.of(17, 0));
    }

    @Test
    void cadastrarMarcacao_ReturnsSuccess() throws Exception {
        when(marcacaoService.addMarcacao(any(Marcacao.class))).thenReturn(marcacao);

        ResponseEntity<String> response = marcacaoController.cadastrarMarcacao(marcacao);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Marcação cadastrado com sucesso!", response.getBody());
        verify(marcacaoService, times(1)).addMarcacao(any(Marcacao.class));
    }

    @Test
    void listarMarcacao_WhenNoMarcacao_ReturnsEmptyList() throws Exception {
        when(marcacaoService.listarMarcacao()).thenReturn(Collections.emptyList());

        ResponseEntity<?> response = marcacaoController.listarMarcacao();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertTrue(((List<?>)response.getBody()).isEmpty());
    }

    @Test
    void listarMarcacao_WhenMarcacoesExist_ReturnsMarcacaoList() throws Exception {
        when(marcacaoService.listarMarcacao()).thenReturn(Arrays.asList(marcacao));

        ResponseEntity<?> response = marcacaoController.listarMarcacao();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(1, ((List<?>)response.getBody()).size());
    }

    @Test
    void deletarMarcacoes_ReturnsSuccess() throws Exception {
        doNothing().when(marcacaoService).deleteAll();

        ResponseEntity<?> response = marcacaoController.deletarMarcacoes();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Marcações deletadas", response.getBody());
        verify(marcacaoService, times(1)).deleteAll();
    }
}
