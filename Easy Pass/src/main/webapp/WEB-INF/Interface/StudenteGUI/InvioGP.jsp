<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: Vivi
  Date: 28/12/2021
  Time: 20:04
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <jsp:include page="/WEB-INF/Interface/Partials/Head.jsp">
        <jsp:param name="studenteStyles" value="studente"/>
        <jsp:param name="studenteScripts" value="studente"/>
        <jsp:param name="title" value="Easy Pass | Invio Green Pass"/>
    </jsp:include>
</head>
</head>
<body>

    <h1>Sei connesso alla sessione: </h1>

    <form action="${pageContext.request.contextPath}/sessioneServlet/InvioEffettuato" method="post">

    <div id="areaInvio">
        <p>
            <input type="file">
            Carica file <br/>
            <img src="${pageContext.request.contextPath}/icons/file.png">
        </p>
    </div>
        <button>Invia Green Pass</button>

    </form>

</body>
</html>
