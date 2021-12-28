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

    <h1>Sei connesso alla sessione: <%session.getId();%></h1>
    <div id="areaInvio">
    Upload file
    </div>
    <button>Invia Green Pass</button>

</body>
</html>
