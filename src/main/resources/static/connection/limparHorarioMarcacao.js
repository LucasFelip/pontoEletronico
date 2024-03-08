export const deletarHorariosEMarcacoes = async () => {
    try {
        await $.ajax({
            type: "DELETE",
            url: "/horarios/deletar",
        });
        await $.ajax({
            type: "DELETE",
            url: "/marcacoes/deletar",
        });
        location.reload();
    } catch (error) {
        alert("Erro ao limpar horários e marcações: " + error.responseText);
    }
}
