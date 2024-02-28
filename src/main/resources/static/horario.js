$(document).ready(function() {
    const carregarHorarios = async () => {
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
            console.error("Erro ao carregar os horários:", error);
        }
    };

    const limparCampos = () => {
        $('#entrada').val('');
        $('#saida').val('');
    };

    $('#horarioForm').submit(async (e) => {
        e.preventDefault();

        const entrada = $('#entrada').val();
        const saida = $('#saida').val();
        const data = { entrada, saida };

        try {
            await $.ajax({
                type: "POST",
                url: "/horarios/cadastrar",
                contentType: "application/json",
                data: JSON.stringify(data),
            });
            alert("Horário cadastrado com sucesso!");
            limparCampos();
            location.reload();
        } catch (error) {
            alert(`Erro ao cadastrar horário: ${error.responseText}`);
            limparCampos();
        }
    });

    carregarHorarios();
});
