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
        <div class="container">
            <div class="my-row3">
                <div class="coll-3">
                    <button style="display: contents" type="button" data-bs-toggle="modal" data-bs-target="#deleteReport">
                        <img class="trash" src="${pageContext.request.contextPath}/icons/trash.svg" alt="trash">
                    </button>
                </div>
                <div class="coll-3">
                    <img id="download" class="download" src="${pageContext.request.contextPath}/icons/download.svg" alt="download">
                </div>
                <div class="coll-5">
                    <div class="input-group">
                        <label for="searchBar"></label>
                        <input type="text" class="form-control form-control-lg" id="searchBar" placeholder="Cerca qui" autocomplete="off">
                        <button id="btnSearch" type="button" class="input-group-text">
                            <img class="search" src="${pageContext.request.contextPath}/icons/search.svg" alt="search">
                        </button>
                    </div>
                    <div id="filter-records"></div>
                    <span id="textMsg" class="warning"></span>
                    <div class="input-date">
                        <div class="input-group input">
                            <input type="date" id="primaData" class="input-sm form-control" name="primaData" placeholder="gg / mm / yyyy"/>
                            <label for="primaData"></label>
                            <span class="input-group-addon">a</span>
                            <input id="secondaData" type="date" class="input-sm form-control" name="secondaData"/>
                            <label for="secondaData"></label>
                        </div>
                    </div>
                    <span id="dateMsg" class="warning"></span>
                    <span id="downloadMsg" class="warning"></span>
                    <span id="directoryMsg" class="warning"></span>
                    <span id="deleteMsg" class="warning"></span>
                </div>
            </div>
            <div class="row">
                <div class="col-12">
                    <table id="example" class="table table-bordered table-responsive table-striped">
                        <thead>
                        <tr>
                            <th scope="col">ID Report</th>
                            <th scope="col">Docente</th>
                            <th scope="col">Data</th>
                            <th scope="col">Orario</th>
                            <th scope="col">Anteprima</th>
                        </tr>
                        </thead>
                        <tbody id="searchRep">
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
                            <td>
                                <button onclick="insertDataPreview(this)" value="<%=report.getPathFile()%>"
                                        class="btn angle-right" data-bs-toggle="modal" data-bs-target="#exampleModal">
                                    <img src="${pageContext.request.contextPath}/icons/angle-right.svg" alt="angle-right">
                                </button>
                            </td>
                        </tr>
                        <%}%>
                        <%}%>
                        </tbody>
                    </table>
                </div>
            </div>
        </div>
    </div>

    <!--Popup "Delete"-->
    <div class="modal fade" id="deleteReport" tabindex="-1" aria-labelledby="eliminaLabel" aria-hidden="true">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <h5 class="modal-title" id="eliminaLabel">Elimina Report</h5>
                </div>
                <div class="modal-body">
                    <p>Sei sicuro di voler eliminare i Report selezionati?</p>
                </div>
                <div class="modal-footer padding">
                    <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">ESCI</button>
                    <button data-bs-dismiss="modal"
                            type="button" id="delete" class="btn btn-primary">CONFERMA</button>
                </div>
            </div>
        </div>
    </div>
    <script>
        function insertDataPreview(element){
            const $path = $(element).attr("value");
            $('#exampleModalLabel').text($path);
            $('#pdf').attr('src', 'http://localhost:8080/Progetto_EasyPass/Report/' + $path + '#toolbar=0&navpanes=0&scrollbar=0')
        }
    </script>
    <%@include file="../Partials/Direttore/AnteprimaReport.jsp"%>
    <%@include file="../Partials/Direttore/Footer.jsp"%>
</div>
<%@include file="../Partials/Logout.jsp"%>
</body>
</html>
