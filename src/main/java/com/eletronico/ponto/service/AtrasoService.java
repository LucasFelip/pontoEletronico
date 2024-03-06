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
import java.util.List;

@Service
public class AtrasoService {
    @Autowired
    private HorarioRepository horarioRepository;
    @Autowired
    private MarcacaoRepository marcacaoRepository;

    public List<Periodo> calcularAtrasoParaTodos() {
        List<Horario> todosHorarios = horarioRepository.findAll();
        List<Marcacao> todasMarcacoes = marcacaoRepository.findAll();
        return calcularAtraso(todosHorarios, todasMarcacoes);
    }

    private List<Periodo> calcularAtraso(List<Horario> horarios, List<Marcacao> marcacoes) {
        List<Periodo> atrasos = new ArrayList<>();
        marcacoes.forEach(marcacao -> {
            LocalTime fimUltimoHorario = LocalTime.MIN;
            boolean horarioNoturno = false;
            boolean marcacaoProcessada = false;

            for (Horario horario : horarios) {
                if (horario.isTurnoNoturno()) {
                    horarioNoturno = horario.isTurnoNoturno();
                    ajustarAtrasosNoturnos(atrasos, marcacao, horario);
                } else {
                    marcacaoProcessada = processarAtrasoDiurno(atrasos, marcacao, horario, fimUltimoHorario, marcacaoProcessada);
                    fimUltimoHorario = horario.getSaida();
                }
            }
            if (marcacao.getSaida().isBefore(fimUltimoHorario) && !horarioNoturno) {
                atrasos.add(new Periodo(marcacao.getSaida(), fimUltimoHorario));
            }
        });
        return atrasos;
    }

    private boolean processarAtrasoDiurno(List<Periodo> atrasos, Marcacao marcacao, Horario horario, LocalTime inicioUltimoHorario, boolean marcacaoProcessada) {
        if (!marcacaoProcessada && marcacao.getEntrada().isAfter(horario.getEntrada())) {
            atrasos.add(new Periodo(horario.getEntrada(), marcacao.getEntrada()));
            marcacaoProcessada = true;
        }
        if (inicioUltimoHorario.isAfter(LocalTime.MIN) && marcacao.getSaida().isBefore(horario.getSaida()) && marcacao.getEntrada().isBefore(inicioUltimoHorario)) {
            atrasos.add(new Periodo(marcacao.getSaida(), horario.getSaida()));
        }
        return marcacaoProcessada;
    }

    private void ajustarAtrasosNoturnos(List<Periodo> atrasos, Marcacao marcacao, Horario horario) {
        if (marcacao.isTurnoNoturno()) {
            if (marcacao.getEntrada().compareTo(horario.getEntrada()) > 0) {
                atrasos.add(new Periodo(horario.getEntrada(), marcacao.getEntrada()));
            }
            if (marcacao.getSaida().compareTo(horario.getSaida()) < 0) {
                atrasos.add(new Periodo(marcacao.getSaida(), horario.getSaida()));
            }
        } else {
            if (marcacao.getEntrada().isAfter(LocalTime.MIDNIGHT) && marcacao.getEntrada().isBefore(horario.getSaida())) {
                atrasos.add(new Periodo(horario.getEntrada(), marcacao.getEntrada()));
            }
            if (marcacao.getSaida().isBefore(horario.getSaida()) && marcacao.getSaida().isAfter(LocalTime.MIDNIGHT)) {
                atrasos.add(new Periodo(marcacao.getSaida(), horario.getSaida()));
            }
        }
    }

}
