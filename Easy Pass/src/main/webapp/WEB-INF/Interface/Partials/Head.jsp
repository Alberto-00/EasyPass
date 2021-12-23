<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: albmo
  Date: 22/12/2021
  Time: 11:15
  To change this template use File | Settings | File Templates.
--%>
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

<c:if test="${not empty param.errorStyles}">
    <c:forTokens items="${param.errorStyles}" delims="," var="errorStyle">
        <link rel="stylesheet" href="${pageContext.request.contextPath}/css/errors/${errorStyle}.css">
    </c:forTokens>
</c:if>

<script src="${pageContext.request.contextPath}/js/jquery.js"></script>
<script src="${pageContext.request.contextPath}/js/jquery-validate-plugin.js"></script>
<!--<script src="https://kit.fontawesome.com/a076d05399.js" crossorigin="anonymous" defer></script>-->

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