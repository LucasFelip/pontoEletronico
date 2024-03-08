import { carregarMarcacoes, submitMarcacaoForm } from './connection/marcacao.js';
import { carregarHorarios, submitHorarioForm } from './connection/horario.js';
import { carregarHoraExtras } from "./connection/horaExtra.js";
import { carregarAtraso } from "./connection/atraso.js";
import { deletarHorariosEMarcacoes } from "./connection/limparHorarioMarcacao.js";

$(document).ready(function() {
    $('#marcacaoForm').submit(function(e) {
        e.preventDefault();
        submitMarcacaoForm();
    });

    $('#horarioForm').submit(function(e) {
        e.preventDefault();
        submitHorarioForm();
    });

    $('#limparHorariosMarcacoes').on('click', function(e) {
        e.preventDefault();
        deletarHorariosEMarcacoes();
    });

    carregarMarcacoes();
    carregarHorarios();
    carregarHoraExtras();
    carregarAtraso();
});
