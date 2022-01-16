$(document).ready(function() {
    $('#example').DataTable({
        "ordering": false,
        "info": false,
        "searching": false,
        "dom": 'rtip',
        "pageLength": 5
    });

    /**
     * Ajax per eliminazione report
     */
    $("#delete").click(function (){
        var report = [];

        // Initializing array with Checkbox checked values
        $("div.custom-checkbox input[type=checkbox]").each(function(){
            if ($(this).is(":checked")) {
                report.push($(this).val());
            }
        });

        var dataString = "" + report;

        $.ajax({
            method: 'GET',
            accepts: {
                json: 'application/json',
            },
            data: {
                report: dataString
            },
            dataType: 'text',
            contentType: "application/json; charset=utf-8",
            url: '../report/delete',
            success: function (response) {
                var arr = JSON.parse(response);

                if (arr.listReports != null){
                    for (let i = 0; i < arr.listReports.length; i++) {
                        $('#report' + arr.listReports[i].report).remove();
                    }
                } else
                    $('#deleteMsg').text("Seleziona almeno un report da eliminare.").hide().fadeIn(500).delay(1600).fadeOut(500)
            }
        });
    })


    /**
     * Ajax per la live searchBar
     */
    $('#searchBar').keyup(function(){
        const searchField = $(this).val();

        if(searchField === '')  {
            $('#filter-records').html('');
            return;
        }

        const regex = new RegExp(searchField, "i"); /*i -> not case sensitive*/
        var output = '<div class="container-search">';
        output += '<div class="rowAjax">';
        var count = 1;
        $.ajax({
            method: 'GET',
            accepts: {
                json: 'application/json',
            },
            dataType: 'text',
            contentType: "application/json; charset=utf-8",
            url: '../report/search',
            success: function (response) {
                var arr = JSON.parse(response);
                $.each(arr.listName, function (key, value){
                    if (value.name.search(regex) != -1 && value.name != null) {
                        output += '<div class="columnAjax">';
                        output += '<p>' + value.name + '</p>'
                        output += '</div>';

                        if(count%1 == 0){
                            output += '</div><div class="rowAjax">'
                        }
                        count++;
                    }
                });
                output += '</div>';
                output += '</div>';
                $('#filter-records').html(output);
            }
        })
    })
    $('#filter-records').on('click', 'p', function() {
        var click_text = $(this).text().split('|');
        $('#searchBar').val($.trim(click_text[0]));
        $("#filter-records").html('');
    });


    /**
     * Ajax per la ricerca per docente e/o data
     */
    $("#btnSearch").click(function (){
        const firstDate = $('#primaData').val();
        const secondDate = $('#secondaData').val();
        const nameDoc = $('#searchBar').val();

        $.ajax({
            method: 'GET',
            accepts: {
                json: 'application/json',
            },
            data: {
                firstDate: firstDate,
                secondDate: secondDate,
                nameDoc: nameDoc,
            },
            dataType: 'text',
            contentType: "application/json; charset=utf-8",
            url: '../report/search_report',
            success: function (response) {
                var arr = JSON.parse(response);

                if (arr.emptyy === "empty"){
                    $('#textMsg').text("Non ci sono report.").hide().fadeIn(500).delay(1600).fadeOut(500)
                } else if (arr.dateError != null){
                    $('#dateMsg').text(arr.dateError).hide().fadeIn(500).delay(1600).fadeOut(500)
                } else {
                    $('tbody#searchRep').empty();
                    for (let i = 0; i < arr.listRep.length; i++){
                        $('#searchRep').append(
                            "<tr id='report"+ arr.listRep[i].report.id +"'> " +
                                "<td>" +
                                    "<div class='custom-control custom-checkbox'>" +
                                        "<input type='checkbox' class='custom-control-input' name='idReport' value='"+ arr.listRep[i].report.id +"' " +
                                            "id='"+ arr.listRep[i].report.id + "'>" +
                                            "<label class='custom-control-label' for='"+ arr.listRep[i].report.id + "'>" + arr.listRep[i].report.id + "</label>" +
                                    "</div>" +
                                "</td>" +
                                "<td>" + arr.listDoc[i].docenti.cognome + " " + arr.listDoc[i].docenti.nome + "</td>" +
                                "<td>" + arr.listRep[i].report.data + "</td>" +
                                "<td>" + arr.listRep[i].report.orario + "</td>" +
                                "<td>" +
                                    "<button value='" + arr.listRep[i].report.path + "' onclick='insertDataPreview(this)' " +
                                        "class='btn angle-right' " +
                                        "data-bs-toggle='modal' data-bs-toggle='modal' data-bs-target='#exampleModal'>" +
                                        "<img src='../icons/angle-right.svg' alt='angle-right'>" +
                                    "</button>" +
                                "</td>" +
                            "</tr>"
                        )
                    }
                }
            }
        });
    })


    /**
     * Ajax per il download del report in .pdf
     */
    $('#download').click(function (){
        const report = [];

        $("div.custom-checkbox input[type=checkbox]").each(function(){
            if ($(this).is(":checked")) {
                report.push($(this).val());
            }
        });

        const dataString = "" + report;

        $.ajax({
            method: 'GET',
            accepts: {
                json: 'application/json',
            },
            data: {
                report: dataString,
            },
            dataType: 'text',
            contentType: "application/json; charset=utf-8",
            url: '../report/download_report',
            success: function (response) {
                var arr = JSON.parse(response);

                if (arr.listDownload != null) {
                    for (let i = 0; i < arr.listDownload.length; i++)
                        download(arr.listDownload[i].report)
                    $("#downloadMsg").text("Download avvenuto con successo!").hide().fadeIn(500).delay(2200).fadeOut(500);
                } else if (arr.noFile != null)
                    $("#downloadMsg").text(arr.noFile).hide().fadeIn(500).delay(1600).fadeOut(500);
            }
        });
    })
    function download(path) {
        var element = document.createElement('a');
        element.setAttribute('href', 'http://localhost:8080/Progetto_EasyPass/Report/' + path );
        element.setAttribute('download', path);

        element.style.display = 'none';
        document.body.appendChild(element);

        element.click();

        document.body.removeChild(element);
    }
});
