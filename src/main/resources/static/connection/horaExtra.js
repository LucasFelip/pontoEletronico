export const carregarHoraExtras = async () => {
    try {
        const response = await $.ajax({
            type: "GET",
            url: "/horas_extras/listar",
        });
        $('#tabelaHoraExtra tbody').empty();
        response.forEach(periodo => {
            const linha = `<tr>
                                <td>${periodo.inicio}</td>
                                <td>${periodo.fim}</td>
                            </tr>`;
            $('#tabelaHoraExtra tbody').append(linha);
        });
    } catch (error) {
        $('#tabelaHoraExtra tbody').empty();
        // console.error("Erro ao carregar as Horas Extras:", error);
    }
}