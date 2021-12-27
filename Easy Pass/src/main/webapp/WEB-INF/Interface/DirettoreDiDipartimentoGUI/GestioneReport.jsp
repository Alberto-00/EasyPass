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
    <script type="text/javascript" src="${pageContext.request.contextPath}/frontend-lib/DataTables/datatables.min.js"></script>
</head>
<body>
<%@include file="../Partials/Direttore/Navbar.jsp"%>
<div class="coll-2">
    <jsp:include page="/WEB-INF/Interface/Partials/Direttore/Header.jsp">
        <jsp:param name="title" value="Gestione Report"/>
    </jsp:include>
    <div class="container-fluid">
        <form action="#" method="get">
            <div class="container">
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
                    <div class="coll-5">
                        <div class="input-group">
                            <input type="text" class="form-control form-control-lg" id="searchBar" placeholder="Cerca qui">
                            <label for="searchBar"></label>
                            <button type="submit" class="input-group-text">
                                <img class="search" src="${pageContext.request.contextPath}/icons/search.svg" alt="search">
                            </button>
                        </div>
                        <div class="input-date">
                            <div class="input-group input">
                                <input type="date" id="primaData" class="input-sm form-control" name="primaData" placeholder="gg / mm / yyyy"/>
                                <label for="primaData"></label>
                                <span class="input-group-addon">a</span>
                                <input id="secondaData" type="date" class="input-sm form-control" name="secondaData"/>
                                <label for="secondaData"></label>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="row">
                    <div class="col-12">
                        <table id="example" class="table table-bordered">
                            <thead>
                            <tr>
                                <th scope="col">ID Report</th>
                                <th scope="col">Docente</th>
                                <th scope="col">Data</th>
                                <th scope="col">Anteprima</th>
                            </tr>
                            </thead>
                            <tbody>
                            <%for(int i = 0; i<17; i++){%>
                            <tr>
                                <td>
                                    <div class="custom-control custom-checkbox">
                                        <input type="checkbox" class="custom-control-input" checked>
                                        <label class="custom-control-label" >1</label>
                                    </div>
                                </td>
                                <td>Bootstrap 4 CDN and Starter Template</td>
                                <td>Cristina</td>
                                <td class="td-angle">
                                    <a href="#" class="angle-right">
                                        <img src="${pageContext.request.contextPath}/icons/angle-right.svg" alt="angle-right">
                                    </a>
                                </td>
                            </tr>
                            <%}%>
                            </tbody>
                        </table>
                    </div>
                </div>
            </div>
        </form>
    </div>
    <%@include file="../Partials/Direttore/Footer.jsp"%>
</div>
<%@include file="../Partials/Direttore/Logout.jsp"%>
</body>
</html>
