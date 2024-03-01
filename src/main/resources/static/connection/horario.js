export const carregarHorarios = async () => {
    try {
        const response = await $.ajax({
            type: "GET",
            url: "/horarios/listar",
        });
        $('#tabelaHorario tbody').empty();
        response.forEach(horario => {
            const linha = `<tr>
                                <td>${horario.entrada}</td>
                                <td>${horario.saida}</td>
                                <td>${horario.duracao}</td>
                            </tr>`;
            $('#tabelaHorario tbody').append(linha);
        });
    } catch (error) {
        $('#tabelaHorario tbody').empty();
        // console.error("Erro ao carregar os horários:", error);
    }
};

export const limparCamposHorario = () => {
    $('#entradaHorario').val('');
    $('#saidaHorario').val('');
};

export const submitHorarioForm = async () => {
    const entrada = $('#entradaHorario').val();
    const saida = $('#saidaHorario').val();
    const data = { entrada, saida };

    try {
        await $.ajax({
            type: "POST",
            url: "/horarios/cadastrar",
            contentType: "application/json",
            data: JSON.stringify(data),
        });
        alert("Horário cadastrado com sucesso!");
        limparCamposHorario();
        location.reload();
    } catch (error) {
        alert(`Erro ao cadastrar horário: ${error.responseText}`);
        limparCamposHorario();
    }
};
