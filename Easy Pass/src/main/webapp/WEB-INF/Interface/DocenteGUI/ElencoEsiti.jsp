<%@ page import="Storage.SessioneDiValidazione.SessioneDiValidazione" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: gennarospina
  Date: 27/12/21
  Time: 10:07
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <jsp:include page="/WEB-INF/Interface/Partials/Head.jsp">
        <jsp:param name="docenteStyles" value="docente,grid-layoutCSS"/>
        <jsp:param name="docenteScripts" value=""/>
        <jsp:param name="title" value="Easy Pass | Docente"/>
    </jsp:include>
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/Docente/ajaxElencoEsiti.js"></script>

</head>
<body>
<div class="coll-2">
    <h1 style="text-align: center; color: white; padding-top: 15px">QR Code</h1>
    <%  SessioneDiValidazione s= (SessioneDiValidazione) session.getAttribute("sessioneDiValidazione");
        String path=s.getqRCode();%>
    <img src='http://localhost:8080/Progetto_EasyPass/QRcodes/<%=path%>' id="qrcode" class="qrCode">
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
        <button class="termina-sessione-button">Termina</button>
    </div>

</div>

</body>
<script>
    var url_string = window.location.href;
    var url = new URL(url_string);
    var paramValue = url.searchParams.get("nStudents");
    var setInterval=setInterval(function (){ ajaxUpdate(paramValue,setInterval)},1000);
</script>
</html>