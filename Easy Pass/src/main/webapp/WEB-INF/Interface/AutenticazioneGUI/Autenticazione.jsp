<%@ page import="java.util.List" %>
<%@ page import="Storage.Dipartimento.Dipartimento" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="it">
<head>
    <jsp:include page="/WEB-INF/Interface/Partials/Head.jsp">
        <jsp:param name="autenticazioneStyles" value="autenticazione"/>
        <jsp:param name="autenticazioneScripts" value="autenticazione"/>
        <jsp:param name="title" value="Easy Pass | Login"/>
    </jsp:include>
</head>
<body>

<!--LOGIN-->
<c:if test="${not empty errorMsg}">
    <br><span class="warning" id="messaggiUtente">${errorMsg}</span>
</c:if>
<form name="admin-login" id="login-in" class="login-form" action="${pageContext.request.contextPath}/autenticazione/" method="post">
    <div class="login-form-logo-container">
        <img class="login-form-logo" src="${pageContext.request.contextPath}/icons/logo.svg" alt="logo">
    </div>
    <div class="login-form-content">
        <div class="login-form-header">
            <label>Accedi al tuo account</label>
        </div>
        <input class="login-form-input" type='email' name="email" id="email" placeholder="Email Universitaria / Username" required autocomplete="off">
        <input class="login-form-input" type="password" name="password" id="password" placeholder="Password" required autocomplete="off">
        <c:if test="${not empty msg}">
            <label id="errorLog" class="error">${msg}</label>
        </c:if>
        <button class="login-form-button" type="submit">Accedi</button>
        <span class="registrati">Sei un Docente e non hai le credenziali?</span>
        <button class="login-form-button" type="button" id="sign-in">Registrati</button>
    </div>
</form>

<!--REGISTRAZIONE-->
<div class="register">
    <form name="admin-registry" id="login-up" class="login-form none" action="${pageContext.request.contextPath}/autenticazione/registrazione" method="post">
        <div class="login-form-logo-container">
            <img class="login-form-logo" src="${pageContext.request.contextPath}/icons/logo.svg" alt="logo">
        </div>
        <div class="login-form-content">
            <div class="login-form-header">
                <label>Registrati per la prima volta</label>
            </div>
            <input class="login-form-input" type='text' name="nome" id="nome" placeholder="Nome" required autocomplete="off">
            <input class="login-form-input" type='text' name="cognome" id="cognome" placeholder="Cognome" required autocomplete="off">
            <select id="dipartimento" name="dipartimento" class="login-form-input form-select form-select-lg" aria-label=".form-select-lg" required>
                <option disabled selected value="">Dipartimento</option>
                <%List<Dipartimento> dipartimenti = (List<Dipartimento>) request.getServletContext().getAttribute("dipartimenti");
                    for (Dipartimento dipartimento : dipartimenti) {%>
                <option value="<%=dipartimento.getCodice()%>">Dipartimento di <%=dipartimento.getNome()%>
                </option>
                <%}%>
            </select>
            <input class="login-form-input" type='email' name="email2" id="email2" placeholder="Email Universitaria" required autocomplete="off">
            <input class="login-form-input" type="password" name="password2" id="password2" placeholder="Password" required autocomplete="off">
            <button class="login-form-button" type="submit">Registrati</button>
            <span class="formato_passw">Formato della password:</span>
            <ul>
                <li>lunghezza minima di 8 caratteri</li>
                <li>lunghezza massima di 50 caratteri</li>
                <li>deve contenere almeno una lettera minuscola</li>
                <li>deve contenere almeno una lettera Maiuscola</li>
                <li>deve contenere almeno un numero</li>
                <li>deve contenere almeno uno dei seguenti caratteri speciali: ={}+çò°àù§èé#@$!%€*?&:,;'._<>|-</li>
            </ul>
            <span class="registrati">Hai già un account?</span>
            <button class="login-form-button" type="button" id="sign-up">Accedi</button>
        </div>
    </form>
</div>
</body>
</html>
