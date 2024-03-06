import { carregarMarcacoes, submitMarcacaoForm } from './connection/marcacao.js';
import { carregarHorarios, submitHorarioForm } from './connection/horario.js';
import { carregarHoraExtras } from "./connection/horaExtra.js";
import { carregarAtraso } from "./connection/atraso.js";

$(document).ready(function() {
    $('#marcacaoForm').submit(function(e) {
        e.preventDefault();
        submitMarcacaoForm();
    });

    $('#horarioForm').submit(function(e) {
        e.preventDefault();
        submitHorarioForm();
    });

    carregarMarcacoes();
    carregarHorarios();
    carregarHoraExtras();
    carregarAtraso();
});
