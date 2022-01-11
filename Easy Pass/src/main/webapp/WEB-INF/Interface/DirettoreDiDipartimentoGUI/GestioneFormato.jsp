<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="it">
<head>
    <jsp:include page="/WEB-INF/Interface/Partials/Head.jsp">
        <jsp:param name="direttoreStyles" value="direttore,gestioneFormato"/>
        <jsp:param name="direttoreScripts" value="direttore,gestioneFormato"/>
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

            <c:if test="${not empty messaggiUtente}">
                <br><span class="warning" id="messaggiUtente">${messaggiUtente}</span>
            </c:if>

            <form action="${pageContext.request.contextPath}/reportServlet/salvaFormato" method="post" name="gestioneFormato" id="gestioneFormato">
                <div class="form-check form-switch">
                    <input class="form-check-input" type="checkbox" id="anagrafica" name="anagrafica" ${actualNomeCognome} >
                    <label class="form-check-label" for="anagrafica">Nome e cognome dello studente</label>
                </div>
                <div class="form-check form-switch">
                    <input class="form-check-input" type="checkbox" id="ddn" name="ddn" ${actualDataDiNascita}>
                    <label class="form-check-label" for="ddn">Data di nascita dello studente</label>
                </div>
                <div class="form-check form-switch">
                    <input class="form-check-input" type="checkbox" id="numValidazioni" name="numValidazioni" ${actualNumStudenti}>
                    <label class="form-check-label" for="numValidazioni">Numero di validazioni effettuate</label>
                </div>
                <div class="form-check form-switch">
                    <input class="form-check-input" type="checkbox" id="gpValidi" name="gpValidi" ${actualGPValidi}>
                    <label class="form-check-label" for="gpValidi">Numero Green Pass
                        <span class="fst-italic font-monospace">"VALIDI"</span>
                    </label>
                </div>
                <div class="form-check form-switch">
                    <input class="form-check-input" type="checkbox" id="gpNonValidi" name="gpNonValidi" ${actualGPNonValidi}>
                    <label class="form-check-label" for="gpNonValidi">Numero Green Pass
                        <span class="fst-italic font-monospace">"NON VALIDI"</span>
                    </label>
                </div>
            </form>
            <div class="btn-width">
               <button type="submit" id="salvaForm" class="btn btn-primary" form="gestioneFormato">
                    <span>SALVA</span>
                </button>
            </div>
        </div>
        <%@include file="../Partials/Direttore/Footer.jsp"%>
    </div>
    <%@include file="../Partials/Logout.jsp"%>
</body>
</html>
