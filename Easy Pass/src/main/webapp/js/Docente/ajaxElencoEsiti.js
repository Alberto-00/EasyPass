function ajaxUpdate() {
    $.ajax({
        method: 'GET',
        accepts: {
            json: 'application/json',
        },
        dataType: 'text',
        contentType: "application/json; charset=utf-8",
        url: '../report/aggiornaElencoEsiti',
        success: function (response) {
            var argument = JSON.parse(response);
            $("#elencoEsitiDiv").empty();
            for(let i=0;i < argument.listaEsiti.length;i++){
                $("#elencoEsitiDiv").append("" +
                    "<div class='grid-cell shadow'>\n" +
                    "     <span class='grid-data' name='nomeCompleto' id='nomeCompleto'>"+ argument.listaEsiti[i].esitoJson.nomeStudente +" "+argument.listaEsiti[i].esitoJson.cognomeStudente +"</span>\n" +
                    "     <span class='grid-data' name='ddn' id='ddn'>"+ argument.listaEsiti[i].esitoJson.dataDiNascitaStudente +"</span>\n" +
                    "     <span class='grid-data' name='esito' id='esito'>"+ argument.listaEsiti[i].esitoJson.validita+"</span>\n" +
                    "</div>")
            }
        }
    });
}