<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1, viewport-fit=cover">
<title>${param.title}</title>
<meta name="description" content="Easy Pass">
<link rel="icon" type="image/png" href="${pageContext.request.contextPath}/icons/logo.png">
<meta name="apple-mobile-web-app-capable" content="yes">
<meta name="format-detection" content="telephone-no">
<meta name="apple-mobile-web-app-title" content="Easy Pass">
<meta name="apple-mobile-web-app-status-bar-style" content="default">
<link rel="apple-touch-icon" href="${pageContext.request.contextPath}/icons/logo.png">
<link rel="apple-touch-startup-image" href="${pageContext.request.contextPath}/icons/logo.png">
<meta name="theme-color" content="#93D07C">
<link href="${pageContext.request.contextPath}/css/reset.css" rel="stylesheet">
<link href="${pageContext.request.contextPath}/css/library.css" rel="stylesheet">
<link href="${pageContext.request.contextPath}/css/logout.css" rel="stylesheet">
<link href="${pageContext.request.contextPath}/frontend-lib/bootstrap-5.1.3/css/bootstrap.min.css" rel="stylesheet">

<c:if test="${not empty param.direttoreStyles}">
    <c:forTokens items="${param.direttoreStyles}" delims="," var="direttoreStyle">
        <link rel="stylesheet" href="${pageContext.request.contextPath}/css/Direttore/${direttoreStyle}.css">
    </c:forTokens>
</c:if>

<c:if test="${not empty param.docenteStyles}">
    <c:forTokens items="${param.docenteStyles}" delims="," var="docenteStyle">
        <link rel="stylesheet" href="${pageContext.request.contextPath}/css/Docente/${docenteStyle}.css">
    </c:forTokens>
</c:if>

<c:if test="${not empty param.autenticazioneStyles}">
    <c:forTokens items="${param.autenticazioneStyles}" delims="," var="autenticazioneStyle">
        <link rel="stylesheet" href="${pageContext.request.contextPath}/css/Autenticazione/${autenticazioneStyle}.css">
    </c:forTokens>
</c:if>

<c:if test="${not empty param.errorStyles}">
    <c:forTokens items="${param.errorStyles}" delims="," var="errorStyle">
        <link rel="stylesheet" href="${pageContext.request.contextPath}/css/Errori/${errorStyle}.css">
    </c:forTokens>
</c:if>

<c:if test="${not empty param.studenteStyles}">
    <c:forTokens items="${param.studenteStyles}" delims="," var="studenteStyle">
        <link rel="stylesheet" href="${pageContext.request.contextPath}/css/Studente/${studenteStyle}.css">
    </c:forTokens>
</c:if>

<script src="${pageContext.request.contextPath}/frontend-lib/bootstrap-5.1.3/js/bootstrap.min.js"></script>
<script src="${pageContext.request.contextPath}/js/jquery.js"></script>
<script src="${pageContext.request.contextPath}/js/jquery-validate-plugin.js"></script>

<c:if test="${not empty param.direttoreScripts}">
    <c:forTokens items="${param.direttoreScripts}" delims="," var="direttoreScript">
        <script src="${pageContext.request.contextPath}/js/Direttore/${direttoreScript}.js" defer></script>
    </c:forTokens>
</c:if>

<c:if test="${not empty param.docenteScripts}">
    <c:forTokens items="${param.docenteScripts}" delims="," var="docenteScript">
        <script src="${pageContext.request.contextPath}/js/Docente/${docenteScript}.js" defer></script>
    </c:forTokens>
</c:if>

<c:if test="${not empty param.autenticazioneScripts}">
    <c:forTokens items="${param.autenticazioneScripts}" delims="," var="autenticazioneScript">
        <script src="${pageContext.request.contextPath}/js/Autenticazione/${autenticazioneScript}.js" defer></script>
    </c:forTokens>
</c:if>

<c:if test="${not empty param.studenteScripts}">
    <c:forTokens items="${param.studenteScripts}" delims="," var="studenteScript">
        <script src="${pageContext.request.contextPath}/js/Studente/${studenteScript}.js" defer></script>
    </c:forTokens>
</c:if>