package com.eletronico.ponto.model;

import lombok.*;

import java.time.LocalTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Periodo {
    private LocalTime inicio;
    private LocalTime fim;
}
