<%@ page import="Storage.SessioneDiValidazione.SessioneDiValidazione" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="it">
<head>
    <jsp:include page="/WEB-INF/Interface/Partials/Head.jsp">
        <jsp:param name="docenteStyles" value="docente,grid-layoutCSS"/>
        <jsp:param name="docenteScripts" value="docente,elencoEsiti"/>
        <jsp:param name="title" value="Easy Pass | Docente"/>
    </jsp:include>
    <script src="${pageContext.request.contextPath}/js/jsQR.js"></script>
</head>
<body>
<div class="coll-2">
    <a class="btnn" id="logout" href="javascript:void(0)">Logout</a>
    <!--<h1 class="titleQR titleStudent">QR Code</h1>-->
    <%  SessioneDiValidazione sessioneDiValidazione = (SessioneDiValidazione) session.getAttribute("sessioneDiValidazione");
        String path = sessioneDiValidazione.getqRCode();%>
    <img src='http://localhost:8080/Progetto_EasyPass/QRCodes/<%=path%>' alt="QRcode" id="qrcode" class="qrCode">
    <span id="linkQRcode" style="width: 80%; margin: auto"></span>
    <canvas id="canvas" hidden></canvas>
    <hr class="rounded">
    <div class="grid-container" id="elencoEsitiDiv">
        <c:forEach var="esito" items="${esiti}">
            <div class="grid-cell shadow">
                <span class="grid-data" name="nomeCompleto" id="nomeCompleto">${esito.nomeStudente} ${esito.cognomeStudente}</span>
                <span class="grid-data" name="ddn" id="ddn">${esito.dataDiNascitaStudente}</span>
                <span class="grid-data" name="esito" id="esito">${esito.validita}</span>
            </div>
        </c:forEach>
    </div>
    <div style="display: flex; justify-content: space-between">
        <span class="esitoCounter" id="esitoCounter">
            0/${param.nStudents}
        </span>
        <button onclick="startAnteprimaReport()" class="termina-sessione-button">Termina</button>
    </div>
</div>
<%@include file="/WEB-INF/Interface/Partials/Direttore/Logout.jsp"%>
</body>
<script>
    window.addEventListener('load', function () {
        var canvasElement = document.getElementById("canvas");
        var canvas = canvasElement.getContext("2d");

        var img = document.getElementById('qrcode');
        var imageData;

        if (img.getAttribute('src') == "") {
            alert("Nessun Immagine Trovata");
            return;
        }
        canvasElement.height = img.height;
        canvasElement.width = img.width;
        canvas.drawImage(img, 0, 0, canvasElement.width, canvasElement.height);
        imageData = canvas.getImageData(0, 0, canvasElement.width, canvasElement.height);

        const code = jsQR(imageData.data, imageData.width, imageData.height);
        if (code) {
            let urlQRcode = document.getElementById("linkQRcode");

            while( urlQRcode.firstChild ) {
                urlQRcode.removeChild( urlQRcode.firstChild );
            }
            urlQRcode.appendChild( document.createTextNode(code.data));
            URL.revokeObjectURL(img.src);
        } else {
            URL.revokeObjectURL(img.src);
            alert("Nessun QR trovato")
        }
    })

    const url_string = window.location.href;
    const url = new URL(url_string);
    const paramValue = url.searchParams.get("nStudents");
    var setInterval = setInterval(function (){
        ajaxUpdate(paramValue,setInterval)},
        1000);


</script>
</html>