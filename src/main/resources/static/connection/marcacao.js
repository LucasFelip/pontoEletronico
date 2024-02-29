export const carregarMarcacoes = async () => {
    try {
        const response = await $.ajax({
            type: "GET",
            url: "/marcacoes/listar",
        });
        $('#tabelaMarcacao tbody').empty();
        response.forEach(marcacao => {
            const linha = `<tr>
                                <td>${marcacao.entrada}</td>
                                <td>${marcacao.saida}</td>
                                <td>${marcacao.duracao}</td>
                            </tr>`;
            $('#tabelaMarcacao tbody').append(linha);
        });
    } catch (error) {
        $('#tabelaMarcacao tbody').empty();
        console.error("Erro ao carregar os horários:", error);
    }
}

export const limparCampos = () => {
    $('#entradaMarcacao').val('');
    $('#saidaMarcacao').val('');
};

export const submitMarcacaoForm = async () => {
    const entrada = $('#entradaMarcacao').val();
    const saida = $('#saidaMarcacao').val();
    const data = { entrada, saida };

    try {
        await $.ajax({
            type: "POST",
            url: "/marcacoes/cadastrar",
            contentType: "application/json",
            data: JSON.stringify(data),
        });
        alert("Marcação cadastrado com sucesso!");
        limparCampos();
        location.reload();
    } catch (error) {
        alert(`Erro ao cadastrar marcação: ${error.responseText}`);
        limparCampos();
    }
};
