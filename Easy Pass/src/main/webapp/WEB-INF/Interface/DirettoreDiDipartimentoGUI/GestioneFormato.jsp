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
        <div>
            <form>
                <div class="form-check form-switch">
                    <input class="form-check-input" type="checkbox" id="flexSwitchCheckDefault" checked>
                    <label class="form-check-label" for="flexSwitchCheckDefault">Nome e cognome</label>
                </div>
                <div class="form-check form-switch">
                    <input class="form-check-input" type="checkbox" id="flexSwitchCheckChecked">
                    <label class="form-check-label" for="flexSwitchCheckChecked">Data di nascita</label>
                </div>
                <div class="form-check form-switch">
                    <input class="form-check-input" type="checkbox" id="flexSwitchCheckDisabled">
                    <label class="form-check-label" for="flexSwitchCheckDisabled">Numero di validazioni effettuate</label>
                </div>
                <div class="form-check form-switch">
                    <input class="form-check-input" type="checkbox" id="flexSwitchCheckCheckedDisabled">
                    <label class="form-check-label" for="flexSwitchCheckCheckedDisabled">Esito della validazione
                        (Green Pass "VALIDO" o "NON Valido"
                    </label>
                </div>
                <div>
                    <button class="btn btn-primary btn-icon">
                        <svg class="icon icon-white">
                            <use xlink:href="/bootstrap-italia/dist/svg/sprite.svg#it-star-full"></use>
                        </svg>
                        <span>SALVA</span>
                    </button>
                </div>
            </form>
        </div>
        <%@include file="../Partials/Direttore/Footer.jsp"%>
    </div>
    <%@include file="../Partials/Direttore/Logout.jsp"%>
</body>
</html>
