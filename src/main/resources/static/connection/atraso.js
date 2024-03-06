export const carregarAtraso = async () => {
    try {
        const response = await $.ajax({
            type: "GET",
            url: "/atrasos/listar",
        });
        $('#tabelaAtraso tbody').empty();
        response.forEach(periodo => {
            const linha = `<tr>
                                <td>${periodo.inicio}</td>
                                <td>${periodo.fim}</td>
                            </tr>`;
            $('#tabelaAtraso tbody').append(linha);
        });
    } catch (error) {
        $('#tabelaAtraso tbody').empty();
        // console.error("Erro ao carregar os Atrasos:", error);
    }
}