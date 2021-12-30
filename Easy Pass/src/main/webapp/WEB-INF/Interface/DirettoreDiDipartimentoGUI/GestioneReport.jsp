<%@ page import="Storage.Report.Report" %>
<%@ page import="Storage.PersonaleUnisa.Docente.Docente" %>
<%@ page import="java.util.TreeMap" %>
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
                        <a href="#" id="callAjax">
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
                                <th scope="col">Orario</th>
                                <th scope="col">Anteprima</th>
                            </tr>
                            </thead>
                            <tbody>
                            <%TreeMap<Report, Docente> treeMap = (TreeMap<Report, Docente>) request.getAttribute("treeMap");
                            if (!treeMap.isEmpty()) {
                                for (Report report : treeMap.keySet()) {%>
                            <tr id="report<%=report.getId()%>">
                                <td>
                                    <div class="custom-control custom-checkbox">
                                        <input type="checkbox" class="custom-control-input" name="idReport" value="<%=report.getId()%>"
                                               id="<%=report.getId()%>">
                                        <label class="custom-control-label" for="<%=report.getId()%>"><%=report.getId()%></label>
                                    </div>
                                </td>
                                <td><%=treeMap.get(report).getCognome() + " " + treeMap.get(report).getNome()%></td>
                                <td><%=report.getData().toString()%></td>
                                <td><%=report.getOrario().toString()%></td>
                                <td class="td-angle">
                                    <a href="#" class="angle-right">
                                        <img src="${pageContext.request.contextPath}/icons/angle-right.svg" alt="angle-right">
                                    </a>
                                </td>
                            </tr>
                                <%}%>
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
