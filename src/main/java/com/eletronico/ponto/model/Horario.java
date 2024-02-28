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
public class Horario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalTime entrada;
    private LocalTime saida;

    public int getDuracao() {
        return (int) ChronoUnit.HOURS.between(entrada, saida);
    }
}
