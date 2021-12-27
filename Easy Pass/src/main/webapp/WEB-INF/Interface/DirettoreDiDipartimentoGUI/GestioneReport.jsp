<%--
  Created by IntelliJ IDEA.
  User: albmo
  Date: 24/12/2021
  Time: 17:41
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <jsp:include page="/WEB-INF/Interface/Partials/Head.jsp">
        <jsp:param name="direttoreStyles" value="direttore,gestioneReport"/>
        <jsp:param name="direttoreScripts" value="direttore,gestioneReport"/>
        <jsp:param name="title" value="Easy Pass | Admin"/>
    </jsp:include>
</head>
<body>
<%@include file="../Partials/Direttore/Navbar.jsp"%>
<div class="coll-2">
    <jsp:include page="/WEB-INF/Interface/Partials/Direttore/Header.jsp">
        <jsp:param name="title" value="Gestione Report"/>
    </jsp:include>
    <div class="container-fluid">
        <div class="my-row3">
            <div class="coll-3">
                <a href="#">
                    <img class="trash" src="${pageContext.request.contextPath}/icons/trash.svg" alt="trash">
                </a>
            </div>
            <div class="coll-3">
                <a href="#">
                    <img class="download" src="${pageContext.request.contextPath}/icons/download.svg" alt="download">
                </a>
            </div>
            <div class="coll-5 ">
                <form action="#" method="get">
                    <div class="input-group">
                        <input type="text" class="form-control form-control-lg" id="searchBar" placeholder="Search Here">
                        <label for="searchBar"></label>
                        <button type="submit" class="input-group-text">
                            <img class="search" src="${pageContext.request.contextPath}/icons/search.svg" alt="search">
                        </button>
                    </div>
                    <div class="input-daterange input-group" id="datepicker">
                        <input type="text" class="input-sm form-control" name="start" />
                        <span class="input-group-addon">to</span>
                        <input type="text" class="input-sm form-control" name="end" />
                    </div>
                </form>
            </div>
        </div>
    </div>
    <%@include file="../Partials/Direttore/Footer.jsp"%>
</div>
<%@include file="../Partials/Direttore/Logout.jsp"%>
</body>
</html>
