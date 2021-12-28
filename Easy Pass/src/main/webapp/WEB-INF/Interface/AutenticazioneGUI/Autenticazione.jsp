<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: albmo
  Date: 27/12/2021
  Time: 20:40
  To change this template use File | Settings | File Templates.
--%>
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
<form name="admin-login" id="login-in" class="login-form" action="${pageContext.request.contextPath}/direttoreServlet/Autenticazione" method="post">
    <div class="login-form-logo-container">
        <img class="login-form-logo" src="${pageContext.request.contextPath}/icons/logo.svg" alt="logo">
    </div>
    <div class="login-form-content">
        <div class="login-form-header">
            <label>Accedi al tuo account amministratore</label>
        </div>
        <input class="login-form-input" type='email' name="email" id="Email" placeholder="Email / Username" required autocomplete="off">
        <input class="login-form-input" type="password" name="password" id="password" placeholder="Password" required autocomplete="off">
        <c:if test="${not empty msg}">
            <label class="error">${msg}</label>
        </c:if>
        <button class="login-form-button" type="submit">Accedi</button>
        <span class="registrati">Sei un Docente e non hai le credenziali?</span>
        <button class="login-form-button" type="button" id="sign-in">Registrati</button>
    </div>
</form>

<!--REGISTRAZIONE-->
<div class="register">
    <form name="admin-registry" id="login-up" class="login-form none" action="${pageContext.request.contextPath}/direttoreServlet/Autenticazione" method="post">
        <div class="login-form-logo-container">
            <img class="login-form-logo" src="${pageContext.request.contextPath}/icons/logo.svg" alt="logo">
        </div>
        <div class="login-form-content">
            <div class="login-form-header">
                <label>Registrati per la prima volta</label>
            </div>
            <input class="login-form-input" type='text' name="nome" id="nome" placeholder="Nome" required autocomplete="off" >
            <input class="login-form-input" type='text' name="cognome" id="cognome" placeholder="Cognome" required autocomplete="off" >
            <input class="login-form-input" type='email' name="email2" id="email2" placeholder="Email / Username" required autocomplete="off" >
            <input class="login-form-input" type="password" name="password2" id="password2" placeholder="Password" required autocomplete="off">
            <c:if test="${not empty msg}">
                <label class="error">${msg}</label>
            </c:if>
            <button class="login-form-button" type="button" id="sign-up">Registrati</button>
            <span class="registrati">Hai già un account registrato?</span>
            <button class="login-form-button">Accedi</button>
        </div>
    </form>
</div>
</body>
</html>
