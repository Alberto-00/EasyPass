<%@ page import="Storage.SessioneDiValidazione.SessioneDiValidazione" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="it">
<head>
    <jsp:include page="/WEB-INF/Interface/Partials/Head.jsp">
        <jsp:param name="docenteStyles" value="docente,grid-layoutCSS"/>
        <jsp:param name="docenteScripts" value="elencoEsiti"/>
        <jsp:param name="title" value="Easy Pass | Docente"/>
    </jsp:include>
</head>
<body>
<div class="coll-2">
    <h1 class="titleQR titleStudent">QR Code</h1>
    <%  SessioneDiValidazione s= (SessioneDiValidazione) session.getAttribute("sessioneDiValidazione");%>
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
</body>
<script>
    const url_string = window.location.href;
    const url = new URL(url_string);
    const paramValue = url.searchParams.get("nStudents");
    const setInterval = setInterval(function (){
        ajaxUpdate(paramValue)
    },2000);
</script>
</html>