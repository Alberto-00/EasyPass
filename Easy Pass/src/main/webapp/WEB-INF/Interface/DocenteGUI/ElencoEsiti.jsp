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
</head>
<body>
<div class="coll-2">
    <h1 style="text-align: center; color: white; padding-top: 15px">QR Code</h1>
    <img src="${pageContext.request.contextPath}/icons/covid.png" id="qrcode" class="qrCode">
    <hr class="rounded">
    <div class="grid-container">
        <jsp:include page="../Partials/Docente/grid-card-esito.jsp">
            <jsp:param name="nomeCompleto" value="Gennaro Spina"/>
            <jsp:param name="ddn" value="06/09/1998"/>
            <jsp:param name="esito" value="Valido"/>
        </jsp:include>

        <jsp:include page="../Partials/Docente/grid-card-esito.jsp">
            <jsp:param name="nomeCompleto" value="Gennaro Spina"/>
            <jsp:param name="ddn" value="06/09/1998"/>
            <jsp:param name="esito" value="Valido"/>
        </jsp:include>
        <jsp:include page="../Partials/Docente/grid-card-esito.jsp">
            <jsp:param name="nomeCompleto" value="Gennaro Spina"/>
            <jsp:param name="ddn" value="06/09/1998"/>
            <jsp:param name="esito" value="Valido"/>
        </jsp:include>
        <jsp:include page="../Partials/Docente/grid-card-esito.jsp">
            <jsp:param name="nomeCompleto" value="Gennaro Spina"/>
            <jsp:param name="ddn" value="06/09/1998"/>
            <jsp:param name="esito" value="Valido"/>
        </jsp:include>
        <jsp:include page="../Partials/Docente/grid-card-esito.jsp">
            <jsp:param name="nomeCompleto" value="Gennaro Spina"/>
            <jsp:param name="ddn" value="06/09/1998"/>
            <jsp:param name="esito" value="Valido"/>
        </jsp:include>
        <jsp:include page="../Partials/Docente/grid-card-esito.jsp">
            <jsp:param name="nomeCompleto" value="Gennaro Spina"/>
            <jsp:param name="ddn" value="06/09/1998"/>
            <jsp:param name="esito" value="Valido"/>
        </jsp:include>

    </div>
    <div style="display: flex; justify-content: space-between">
        <jsp:include page="../Partials/Docente/esitoCounter.jsp">
            <jsp:param name="esitiArrived" value="6"/>
            <jsp:param name="esitiRequired" value="${param.nStudents}"/>
        </jsp:include>
        <button class="termina-sessione-button">Termina</button>
    </div>

</div>

</body>
</html>