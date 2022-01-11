<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="it">
<head>
    <jsp:include page="/WEB-INF/Interface/Partials/Head.jsp">
        <jsp:param name="docenteStyles" value="docente"/>
        <jsp:param name="docenteScripts" value="docente,avvioSessione"/>
        <jsp:param name="title" value="Easy Pass | Docente"/>
    </jsp:include>
</head>
<body>
<div class="coll-2">
    <div class="center">
        <a class="btnn" id="logout" href="javascript:void(0)">LOGOUT</a>
    </div>
    <div class="student-form-content" hidden>
        <h1 class="titleStudent">Inserisci il numero di Studenti</h1>
        <form class="student-form" id="NumberOfStudentsForm" name="NumberOfStudentsForm"
              action="${pageContext.request.contextPath}/sessioneServlet/CreaNuovaSessione" method="get">
            <input class="student-form-input" type="text" id="nStudents" name="nStudents"
                   placeholder="Numero di Studenti" required autocomplete="off">
            <input class="student-form-button" type="submit">
        </form>
    </div>
    <div style="width: 150px; margin: auto" id="avviaSessioneBTN">
        <button class="avvia-sessione-button" onclick="showForm()">Avvia Sessione</button>
    </div>
</div>
<script>
    function showForm(){
       document.getElementsByClassName("student-form-content").item(0).toggleAttribute("hidden");
       document.getElementsByClassName("avvia-sessione-button").item(0).toggleAttribute("hidden");
    }
</script>
<%@include file="/WEB-INF/Interface/Partials/Logout.jsp"%>
</body>
</html>
