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
public class HoraExtraService {

    @Autowired
    private HorarioRepository horarioRepository;
    @Autowired
    private MarcacaoRepository marcacaoRepository;

    public List<Periodo> calcularHoraExtraParaTodos() {
        List<Horario> todosHorarios = horarioRepository.findAll();
        List<Marcacao> todasMarcacoes = marcacaoRepository.findAll();
        return calcularHorasExtras(todosHorarios, todasMarcacoes);
    }

    private List<Periodo> calcularHorasExtras(List<Horario> horarios, List<Marcacao> marcacoes) {
        List<Periodo> horasExtras = new ArrayList<>();
        marcacoes.forEach(marcacao -> {
            LocalTime fimUltimoHorario = LocalTime.MIN;
            boolean horarioNoturno = false;
            boolean marcacaoProcessada = false;

            for (Horario horario : horarios) {
                if (horario.isTurnoNoturno()) {
                    horarioNoturno = horario.isTurnoNoturno();
                    ajustarHorasExtrasNoturnas(horasExtras, marcacao, horario);
                } else {
                    marcacaoProcessada = processarMarcacaoDiurna(horasExtras, marcacao, horario, fimUltimoHorario, marcacaoProcessada);
                    fimUltimoHorario = horario.getSaida();
                }
            }
            if (marcacao.getSaida().isAfter(fimUltimoHorario) && !horarioNoturno) {
                horasExtras.add(new Periodo(fimUltimoHorario, marcacao.getSaida()));
            }
        });
        return horasExtras;
    }

    private boolean processarMarcacaoDiurna(List<Periodo> horasExtras, Marcacao marcacao, Horario horario, LocalTime fimUltimoHorario, boolean marcacaoProcessada) {
        if (!marcacaoProcessada && marcacao.getEntrada().isBefore(horario.getEntrada())) {
            horasExtras.add(new Periodo(marcacao.getEntrada(), horario.getEntrada()));
            return true;
        }
        if (fimUltimoHorario.isAfter(LocalTime.MIN) && marcacao.getEntrada().isBefore(horario.getEntrada()) && marcacao.getSaida().isAfter(fimUltimoHorario)) {
            horasExtras.add(new Periodo(fimUltimoHorario, horario.getEntrada()));
        }
        return marcacaoProcessada;
    }

    private void ajustarHorasExtrasNoturnas(List<Periodo> horasExtras, Marcacao marcacao, Horario horario) {
        if (marcacao.isTurnoNoturno()) {
            if (marcacao.getEntrada().compareTo(horario.getEntrada()) < 0) {
                horasExtras.add(new Periodo(marcacao.getEntrada(), horario.getEntrada()));
            }
            if (marcacao.getSaida().compareTo(horario.getSaida()) > 0) {
                horasExtras.add(new Periodo(horario.getSaida(), marcacao.getSaida()));
            }
        } else {
            if (marcacao.getEntrada().isAfter(LocalTime.MIDNIGHT) && marcacao.getEntrada().isBefore(horario.getSaida())) {
            } else if (marcacao.getEntrada().isBefore(horario.getEntrada())) {
                horasExtras.add(new Periodo(marcacao.getEntrada(), horario.getEntrada()));
            }
            if (marcacao.getSaida().isAfter(horario.getSaida())) {
                horasExtras.add(new Periodo(horario.getSaida(), marcacao.getSaida()));
            } else if (marcacao.getSaida().isBefore(horario.getEntrada()) && marcacao.getSaida().isAfter(LocalTime.MIDNIGHT) && marcacao.getSaida().isAfter(horario.getSaida())) {
                horasExtras.add(new Periodo(LocalTime.MIDNIGHT, marcacao.getSaida()));
            }
        }
    }
}
