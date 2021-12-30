$(document).ready(function() {
    $('#example').DataTable({
        "ordering": false,
        "info": false,
        "searching": false,
        "dom": 'rtip',
        "pageLength": 5
    });

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
                        $('#report' + arr.listReports[i].report).remove().fadeOut(400);
                    }
                }
            }
        });
    })
});
