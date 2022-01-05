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
<h1>Sei connesso alla sessione: 123456</h1>
<div class="areaInvio">
    <label for="actual-btn">
        <img src="${pageContext.request.contextPath}/icons/file.svg" alt="file" id="preview-img" style="height: 580px; width: 220px; object-fit: contain">
        <img id="img" hidden>
        <canvas id="canvas" hidden></canvas>
    </label>
</div>
<input class="file_added" id="actual-btn" type="file" name="file" accept="image/*" hidden>
<button type="button" id="myButton" onclick="readQR()">Invia Green Pass</button>
<form name="formDgc" id="formDgc" method="post" action="${pageContext.request.contextPath}/sessioneServlet/InvioGP">
    <input type="hidden" name="dgc" id="dgc">
</form>
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

        console.log("sono nell'onload");
        canvasElement.height = img.height;
        canvasElement.width = img.width;
        canvas.drawImage(img, 0, 0, canvasElement.width, canvasElement.height);
        imageData = canvas.getImageData(0, 0, canvasElement.width, canvasElement.height);

        const code = jsQR(imageData.data, imageData.width, imageData.height);
        if (code) {
            console.log("Found QR code", code.data);
            let form = document.getElementById("formDgc");
            let inputDgc = document.getElementById("dgc");
            inputDgc.setAttribute("value", code.data);
            form.submit();
        } else {
            console.log("Code not found");
        }
        console.log(`Started decode for image from ${img.src}`)
        URL.revokeObjectURL(img.src);
    }


</script>
</body>
</html>
