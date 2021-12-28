<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8"%>
<nav class="coll-1 gridd-y">
    <a href="${pageContext.request.contextPath}/reportServlet/HomePage">
        <img class="logoNavbar" src="${pageContext.request.contextPath}/icons/logo.svg" alt="logo">
    </a>
    <a class="btnn" href="${pageContext.request.contextPath}/reportServlet/GestioneReport">Gestione Report</a>
    <a class="btnn" href="${pageContext.request.contextPath}/reportServlet/GestioneFormato">Gestione Formato</a>
    <a class="btnn" id="logout" href="javascript:void(0)">Logout</a>
    <img class="covidRedImg" src="${pageContext.request.contextPath}/icons/covidRed.png" alt="covid">
</nav>
