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
                console.log(arr);
                if (arr.listReports !== "empty"){

                }
            }
        });
    })
});
