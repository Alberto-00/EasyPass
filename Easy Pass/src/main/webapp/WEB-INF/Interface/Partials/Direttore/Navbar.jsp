<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8"%>
<nav class="col-1 grid-y">
    <a href="${pageContext.request.contextPath}/direttoreServlet/HomePage">
        <img class="logoNavbar" src="${pageContext.request.contextPath}/icons/logo.svg" alt="logo">
    </a>
    <a class="btn" href="${pageContext.request.contextPath}/direttoreServlet/gestioneReport">Gestione Report</a>
    <a class="btn" href="${pageContext.request.contextPath}/direttoreServlet/gestioneFormato">Gestione Formato</a>
    <a class="btn" id="logout" href="javascript:void(0)">Logout</a>
    <img class="covidRedImg" src="${pageContext.request.contextPath}/icons/covidRed.png" alt="covid">
</nav>
