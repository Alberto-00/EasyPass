<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<jsp:useBean id="direttoreSession" scope="session" type="Storage.PersonaleUnisa.Direttore.DirettoreDiDipartimento"/>
<!DOCTYPE html>
<html lang="it">
<head>
    <jsp:include page="/WEB-INF/Interface/Partials/Head.jsp">
        <jsp:param name="direttoreStyles" value="direttore,homepage"/>
        <jsp:param name="direttoreScripts" value="direttore"/>
        <jsp:param name="title" value="Easy Pass | Admin"/>
    </jsp:include>
</head>
<body>
    <%@include file="../Partials/Direttore/Navbar.jsp"%>
    <div class="coll-2">
        <jsp:include page="/WEB-INF/Interface/Partials/Direttore/Header.jsp">
            <jsp:param name="title" value="Benvenuto\a ${direttoreSession.cognome} ${direttoreSession.nome}"/>
        </jsp:include>
        <img class="covidImg" src="${pageContext.request.contextPath}/icons/covid.png" alt="covid">
        <%@include file="../Partials/Direttore/Footer.jsp"%>
    </div>
    <%@include file="../Partials/Logout.jsp"%>
</body>
</html>
