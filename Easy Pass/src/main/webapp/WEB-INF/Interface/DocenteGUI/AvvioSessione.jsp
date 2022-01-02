<%--
  Created by IntelliJ IDEA.
  User: gennarospina
  Date: 24/12/21
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="it">
<head>
    <jsp:include page="/WEB-INF/Interface/Partials/Head.jsp">
        <jsp:param name="docenteStyles" value="docente"/>
        <jsp:param name="docenteScripts" value=""/>
        <jsp:param name="title" value="Easy Pass | Docente"/>
    </jsp:include>
</head>
<body>
<div class="coll-2">
    <div class="login-form-content" hidden>
        <h1>Inserisci il numero di Studenti</h1>
        <form class="login-form" id="NumberOfStudentsForm" name="NumberOfStudentsForm"
              action="${pageContext.request.contextPath}/sessioneServlet/CreaNuovaSessione" method="get">
            <input class="login-form-input" type="text" id="nStudents" name="nStudents"
                   placeholder="Numero di Studenti">
            <input class="login-form-button" type="submit">
        </form>
    </div>
    <div style="width: 150px; margin: auto" id="avviaSessioneBTN">
        <button class="avvia-sessione-button" onclick="showForm()">Avvia Sessione</button>
    </div>
</div>
<script>
    function showForm(){
       document.getElementsByClassName("login-form-content").item(0).toggleAttribute("hidden");
       document.getElementsByClassName("avvia-sessione-button").item(0).toggleAttribute("hidden");
    }
</script>
</body>
</html>
