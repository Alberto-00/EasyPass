<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <jsp:include page="/WEB-INF/Interface/Partials/Head.jsp">
        <jsp:param name="studenteStyles" value="studente"/>
        <jsp:param name="title" value="Easy Pass | Invio Effettuato Green Pass"/>
    </jsp:include>
</head>
<body>
    <h1>Sei connesso alla sessione: 123456</h1>
    <p>Green Pass inviato correttamente</p>
    <div class="image">
        <img src="${pageContext.request.contextPath}/icons/successGP.svg" alt="success">
    </div>
</body>
</html>
