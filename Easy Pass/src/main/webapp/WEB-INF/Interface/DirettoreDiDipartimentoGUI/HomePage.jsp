<%--
  Created by IntelliJ IDEA.
  User: albmo
  Date: 22/12/2021
  Time: 11:37
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
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
    <div class="col-2">
        <%@include file="../Partials/Direttore/Header.jsp"%>
        <img class="covidImg" src="${pageContext.request.contextPath}/icons/covid.png" alt="covid">
        <%@include file="../Partials/Direttore/Footer.jsp"%>
    </div>
    <%@include file="../Partials/Direttore/Logout.jsp"%>
</body>
</html>
