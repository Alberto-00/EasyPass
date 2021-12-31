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
    $("#callAjax").click(function (){
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
                console.log(arr.listReports)
                console.log(arr.listReports.length)
                if (arr.listReports !== "empty"){
                    for (let i = 0; i < arr.listReports.length; i++) {
                        console.log('#report' + arr.listReports[i].report)
                        $('#report' + arr.listReports[i].report).remove();
                    }
                }
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

        var firstDate = $('#primaData').val();
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
                console.log(arr);
                if (arr.empty === "empty"){
                    $('span.warning').text("Non ci sono report generati da questo Docente.").hide().fadeIn(500).delay(3600).fadeOut(500)
                }
            }
        });
    })
});
