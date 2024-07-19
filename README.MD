# Sistema de Ponto Eletrônico

## Sobre o Projeto

Este sistema de ponto eletrônico foi desenvolvido com Spring Boot e Java 17, destinado a calcular atrasos e horas extras dos funcionários. Utiliza uma arquitetura baseada em Spring Data JPA para interações com o banco de dados, Lombok para redução de código boilerplate, e uma estrutura de API REST para o front-end interagir com o back-end.

## Modelos

- **Horario**: Representa os horários de trabalho, identificando turnos diurnos e noturnos.
- **Marcacao**: Registra as marcações de ponto dos funcionários.
- **Periodo**: Utilizado para calcular atrasos e horas extras.

## Repositórios

- **HorarioRepository**: Operações CRUD para `Horario`.
- **MarcacaoRepository**: Operações CRUD para `Marcacao`.

## Serviços

- **AtrasoService**: Lógica para calcular atrasos.
- **HoraExtraService**: Lógica para calcular horas extras.
- **HorarioService**: Gerencia operações relacionadas a horários.
- **MarcacaoService**: Gerencia operações relacionadas a marcações.

## Controllers

Provêm endpoints para interação com os serviços, incluindo cadastro e listagem de horários, marcações, cálculo de atrasos e horas extras.

## Front-end

- **JavaScript**: Scripts para interagir com a API, realizar operações CRUD e exibir resultados no navegador.
- **index.html**: Página principal para interação do usuário.

## Testes

Exemplos detalhados demonstram a capacidade do sistema de lidar com diversos cenários, incluindo turnos que cruzam a meia-noite, e o cálculo preciso de atrasos e horas extras.

| Horário de Trabalho | Marcações Feitas   | Atraso Esperado   | Hora Extra Esperada          |
|---------------------|--------------------|-------------------|------------------------------|
| 08:00 - 12:00       | 06:00 - 20:00      | -                 | 06:00 - 08:00, 12:00 - 20:00 |
|---------------------| ------------------ | ----------------- | ---------------------        |
| 13:30 - 17:30       | 07:00 - 12:30      | 13:30 - 14:00     | 07:00 - 12:30                |
|                     | 14:00 - 17:00      | 17:00 - 17:30     | -                            |
|---------------------| ------------------ | ----------------- | ---------------------        |
| 22:00 - 05:00       | 21:00 - 03:00      | 03:00 - 04:00     | 21:00 - 22:00                |
|                     | 04:00 - 07:00      | -                 | 05:00 - 07:00                |
|---------------------| ------------------ | ----------------- | ---------------------        |

