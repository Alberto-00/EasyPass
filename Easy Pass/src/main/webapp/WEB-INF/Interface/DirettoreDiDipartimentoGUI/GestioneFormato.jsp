<%--
  Created by IntelliJ IDEA.
  User: albmo
  Date: 23/12/2021
  Time: 10:08
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="it">
<head>
    <jsp:include page="/WEB-INF/Interface/Partials/Head.jsp">
        <jsp:param name="direttoreStyles" value="direttore,gestioneFormato"/>
        <jsp:param name="direttoreScripts" value="direttore"/>
        <jsp:param name="title" value="Easy Pass | Admin"/>
    </jsp:include>
</head>
<body>
    <%@include file="../Partials/Direttore/Navbar.jsp"%>
    <div class="coll-2">
        <jsp:include page="/WEB-INF/Interface/Partials/Direttore/Header.jsp">
            <jsp:param name="title" value="Gestione Formato"/>
        </jsp:include>
        <div class="container-fluid">
            <p>Seleziona i dati del Formato</p>
            <span class="small-text">Attenzione: </span>
            <span class="fst-italic">(se è stato selezionato il campo</span>
            <span class="fst-italic font-monospace">"<u>Data di nascita</u>"</span>
            <span class="fst-italic">è necessario selezionare anche il campo</span>
            <span class="fst-italic font-monospace">"<u>Nome e cognome</u>")</span>

            <form action="${pageContext.request.contextPath}/reportServlet/checkFormato" method="get" name="gestioneFormato">
                <div class="form-check form-switch">
                    <input class="form-check-input" type="checkbox" id="anagrafica" checked>
                    <label class="form-check-label" for="anagrafica">Nome e cognome</label>
                </div>
                <div class="form-check form-switch">
                    <input class="form-check-input" type="checkbox" id="ddn">
                    <label class="form-check-label" for="ddn">Data di nascita</label>
                </div>
                <div class="form-check form-switch">
                    <input class="form-check-input" type="checkbox" id="numValidazioni">
                    <label class="form-check-label" for="numValidazioni">Numero di validazioni effettuate</label>
                </div>
                <div class="form-check form-switch">
                    <input class="form-check-input" type="checkbox" id="gpValido" checked>
                    <label class="form-check-label" for="gpValido">Green Pass
                        <span class="fst-italic font-monospace">"VALIDO"</span>
                    </label>
                </div>
                <div class="form-check form-switch">
                    <input class="form-check-input" type="checkbox" id="gpNonValido" checked>
                    <label class="form-check-label" for="gpNonValido">Green Pass
                        <span class="fst-italic font-monospace">"NON VALIDO"</span>
                    </label>
                </div>
            </form>
            <div class="btn-width">
                <button type="button" class="btn btn-primary">
                    <span>SALVA</span>
                </button>
            </div>
        </div>
        <%@include file="../Partials/Direttore/Footer.jsp"%>
    </div>
    <%@include file="../Partials/Direttore/Logout.jsp"%>
</body>
</html>
