<%--
  Created by IntelliJ IDEA.
  User: Vivi
  Date: 29/12/2021
  Time: 00:47
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <jsp:include page="/WEB-INF/Interface/Partials/Head.jsp">
        <jsp:param name="studenteStyles" value="studente"/>
        <jsp:param name="studenteScripts" value="studente"/>
        <jsp:param name="title" value="Easy Pass | Invio Effettuato Green Pass"/>
    </jsp:include>
</head>
<body>
    <h1>Sei connesso alla sessione: <%session.getId();%></h1>

    <p id="invio2">
        Green Pass inviato correttamente <br>
        <img src="${pageContext.request.contextPath}/icons/ok.png">
    </p>
</body>
</html>
