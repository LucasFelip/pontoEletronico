package com.eletronico.ponto.model;

import lombok.*;
import jakarta.persistence.*;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Marcacao {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalTime entrada;
    private LocalTime saida;

    public boolean isTurnoNoturno() {
        return saida.isBefore(entrada);
    }

    public long getDuracao() {
        if (isTurnoNoturno()) {
            long duracao = ChronoUnit.HOURS.between(entrada, LocalTime.MAX) + 1;
            duracao += ChronoUnit.HOURS.between(LocalTime.MIN, saida);
            return duracao;
        } else {
            return ChronoUnit.HOURS.between(entrada, saida);
        }
    }

    public boolean sobreposicaoComHorarioDeTrabalho(Horario horario) {
        return !saida.isBefore(horario.getEntrada()) && !entrada.isAfter(horario.getSaida());
    }
}
