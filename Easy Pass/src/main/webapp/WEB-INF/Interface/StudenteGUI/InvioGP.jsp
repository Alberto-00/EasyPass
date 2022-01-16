<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="it">
<head>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.6.1/dist/css/bootstrap.min.css"
          integrity="sha384-zCbKRCUGaJDkqS1kPbPd7TveP5iyJE0EjAuZQTgFLD2ylzuqKfdKlfG/eSrtxUkn" crossorigin="anonymous">
    <script src="https://cdn.jsdelivr.net/npm/jquery@3.5.1/dist/jquery.slim.min.js"
            integrity="sha384-DfXdz2htPH0lsSSs5nCTpuj/zy4C+OGpamoFVy38MVBnE+IbbVYUew+OrCXaRkfj"
            crossorigin="anonymous"></script>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@4.6.1/dist/js/bootstrap.bundle.min.js"
            integrity="sha384-fQybjgWLrvvRgtW6bFlB7jaZrFsaBXjsOMm/tB9LTS58ONXgqbR9W8oWht/amnpF"
            crossorigin="anonymous"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/bootbox.js/5.5.2/bootbox.min.js"></script>
    <script src="${pageContext.request.contextPath}/js/jsQR.js"></script>
    <jsp:include page="/WEB-INF/Interface/Partials/Head.jsp">
        <jsp:param name="studenteStyles" value="studente"/>
        <jsp:param name="studenteScripts" value="studente"/>
        <jsp:param name="title" value="Easy Pass | Invio Green Pass"/>
    </jsp:include>

</head>
<body>
<h1>Sei connesso alla sessione: ${param.sessionId}</h1>
<div class="areaInvio">
    <label for="actual-btn">
        <img src="${pageContext.request.contextPath}/icons/file.svg" alt="file" id="preview-img"
             style="width: 50%; object-fit: contain">
        <img src="" id="img" hidden>
        <canvas id="canvas" hidden></canvas>
    </label>
</div>
<form name="formDgc" id="formDgc" method="post" action="${pageContext.request.contextPath}/sessioneServlet/InvioGP">
    <input class="file_added" id="actual-btn" type="file" name="file" accept="image/*" hidden>
    <input type="hidden" name="dgc" id="dgc">
</form>
<button type="button" onclick="readQR()" id="myButton">Invia Green Pass</button>
<script>
    window.addEventListener('load', function () {
        document.querySelector('input[type="file"]').addEventListener('change', function () {
            if (this.files && this.files[0]) {
                var img = document.getElementById('img')
                var previewImg = document.getElementById('preview-img')
                let givenImg = URL.createObjectURL(this.files[0])
                img.src = givenImg; // set src to blob url
                previewImg.src = givenImg;
            }
        });
    })

    function readQR() {
        var canvasElement = document.getElementById("canvas");
        var canvas = canvasElement.getContext("2d");

        var img = document.getElementById('img');
        var imageData;

        if (img.getAttribute('src') == "") {
            alert("Nessun Green Pass inserito.");
            return;
        }
        canvasElement.height = img.height;
        canvasElement.width = img.width;
        canvas.drawImage(img, 0, 0, canvasElement.width, canvasElement.height);
        imageData = canvas.getImageData(0, 0, canvasElement.width, canvasElement.height);

        const code = jsQR(imageData.data, imageData.width, imageData.height);
        if (code && code.data.substring(0, 4).localeCompare("HC1:") == 0) {
            let form = document.getElementById("formDgc");
            let inputDgc = document.getElementById("dgc");
            inputDgc.setAttribute("value", code.data);
            URL.revokeObjectURL(img.src);
            form.submit();
        } else {
            URL.revokeObjectURL(img.src);
            alert("Errore! Formato non valido.")
        }
    }
</script>
</body>
</html>
