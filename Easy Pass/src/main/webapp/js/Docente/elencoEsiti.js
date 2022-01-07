/**
 * Aggiornamento live degli esiti
 */
function ajaxUpdate(paramValue,setInterval) {
    $.ajax({
        method: 'GET',
        accepts: {
            json: 'application/json',
        },
        dataType: 'text',
        contentType: "application/json; charset=utf-8",
        url: '../report/aggiornaElencoEsiti',
        success: function (response) {
            const argument = JSON.parse(response);
            const $elencoEsiti = $("#elencoEsitiDiv");
            const $esitoCounter = $("#esitoCounter");

            $elencoEsiti.empty();
            for (let i = 0; i < argument.listaEsiti.length; i++) {
                $elencoEsiti.append("" +
                    "<div class='grid-cell shadow'>" +
                        "<span class='grid-data' name='nomeCompleto' id='nomeCompleto'>" + argument.listaEsiti[i].esitoJson.nomeStudente + " " + argument.listaEsiti[i].esitoJson.cognomeStudente + "</span>" +
                        "<span class='grid-data' name='ddn' id='ddn'>" + argument.listaEsiti[i].esitoJson.dataDiNascitaStudente + "</span>" +
                        "<span class='grid-data' name='esito' id='esito'>" + argument.listaEsiti[i].esitoJson.validita + "</span>" +
                    "</div>")
            }
            $esitoCounter.empty();
            $esitoCounter.append('' + argument.listaEsiti.length + '/' + paramValue);

            if (argument.listaEsiti.length >= paramValue) {
                clearInterval(setInterval);
                window.location.href = 'AnteprimaReport'
            }
        }
    });
}


/**
 * viene chiamata la jsp AnteprimaReport
 * passando prima per la rispettiva servlet in "SessionController"
 */
function startAnteprimaReport(){
    document.location.href = '../sessioneServlet/AnteprimaReport';
}