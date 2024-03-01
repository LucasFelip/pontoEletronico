package com.eletronico.ponto.service;

import com.eletronico.ponto.model.Horario;
import com.eletronico.ponto.model.Marcacao;
import com.eletronico.ponto.model.Periodo;
import com.eletronico.ponto.repository.HorarioRepository;
import com.eletronico.ponto.repository.MarcacaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Service
public class HoraExtraService {

    @Autowired
    private HorarioRepository horarioRepository;

    @Autowired
    private MarcacaoRepository marcacaoRepository;

    // Método público para ser chamado pelo controller
    public List<Periodo> calcularHorasExtrasParaTodos() {
        List<Horario> todosHorarios = horarioRepository.findAll();
        List<Marcacao> todasMarcacoes = marcacaoRepository.findAll();

        return calcularHorasExtras(todosHorarios, todasMarcacoes);
    }

    // Método privado para a lógica de cálculo das horas extras
    private List<Periodo> calcularHorasExtras(List<Horario> horarios, List<Marcacao> marcacoes) {
        List<Periodo> horasExtras = new ArrayList<>();

        // Ordenar os horários por hora de entrada
        horarios.sort(Comparator.comparing(Horario::getEntrada));

        for (Marcacao marcacao : marcacoes) {
            LocalTime fimUltimoHorario = null;
            for (Horario horario : horarios) {
                // Se a marcação começa antes do horário de entrada
                if (marcacao.getEntrada().isBefore(horario.getEntrada())) {
                    horasExtras.add(new Periodo(marcacao.getEntrada(), horario.getEntrada()));
                }
                // Se a marcação termina depois do horário de saída
                if (marcacao.getSaida().isAfter(horario.getSaida())) {
                    horasExtras.add(new Periodo(horario.getSaida(), marcacao.getSaida()));
                }
                // Para o intervalo entre os horários de trabalho
                if (fimUltimoHorario != null && marcacao.getEntrada().isBefore(horario.getEntrada())) {
                    horasExtras.add(new Periodo(fimUltimoHorario, horario.getEntrada()));
                }
                fimUltimoHorario = horario.getSaida();
            }
        }

        return horasExtras;
    }
}

