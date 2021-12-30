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
    <div class="grid-container">
        <jsp:include page="../Partials/Docente/grid-card-esito.jsp">
            <jsp:param name="nome" value="Gennaro"/>
            <jsp:param name="cognome" value="Spina"/>
            <jsp:param name="esito" value="Valido"/>
        </jsp:include>

        <jsp:include page="../Partials/Docente/grid-card-esito.jsp">
            <jsp:param name="nome" value="Gennaro"/>
            <jsp:param name="cognome" value="Spina"/>
            <jsp:param name="esito" value="Valido"/>
        </jsp:include>
        <jsp:include page="../Partials/Docente/grid-card-esito.jsp">
            <jsp:param name="nome" value="Gennaro"/>
            <jsp:param name="cognome" value="Spina"/>
            <jsp:param name="esito" value="Valido"/>
        </jsp:include>
        <jsp:include page="../Partials/Docente/grid-card-esito.jsp">
            <jsp:param name="nome" value="Gennaro"/>
            <jsp:param name="cognome" value="Spina"/>
            <jsp:param name="esito" value="Valido"/>
        </jsp:include>
        <jsp:include page="../Partials/Docente/grid-card-esito.jsp">
            <jsp:param name="nome" value="Gennaro"/>
            <jsp:param name="cognome" value="Spina"/>
            <jsp:param name="esito" value="Valido"/>
        </jsp:include>
    </div>

    <button class="termina-sessione-button">Termina</button>
</div>

</body>
</html>