<%@ page import="Storage.Report.Report" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="it">
<head>
    <jsp:include page="/WEB-INF/Interface/Partials/Head.jsp">
        <jsp:param name="docenteStyles" value="anteprimaReport"/>
        <jsp:param name="docenteScripts" value="anteprimaReport"/>
        <jsp:param name="title" value="Easy Pass | Anteprima dei Report"/>
    </jsp:include>
</head>
<body>
<%Report path = (Report) request.getAttribute("report");%>
    <h1>Anteprima di <span class="fst-italic font-monospace responsive">"<%=path.getPathFile()%>"</span></h1>
    <form id="myForm" action="${pageContext.request.contextPath}/sessioneServlet/AvvioSessione" method="get">
        <div class="areaPDF">
            <iframe src="http://localhost:8080/Progetto_EasyPass/Report/<%=path.getPathFile()%>#toolbar=0&navpanes=0&scrollbar=0"></iframe>
            <a id="hrefPDF" href="http://localhost:8080/Progetto_EasyPass/Report/<%=path.getPathFile()%>" download="<%=path.getPathFile()%>"></a>
        </div>
        <div class="areaButton">
            <button style="margin-right: 3em" type="submit">Torna all'HomePage</button>
            <button type="button" data-bs-toggle="modal" data-bs-target="#exampleModal">Scarica Report</button>
        </div>

        <!--Modal Popup-->
        <div class="modal fade" id="exampleModal" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">
            <div class="modal-dialog">
                <div class="modal-content">
                    <div class="modal-header">
                        <h5 class="modal-title" id="exampleModalLabel">Download di
                            <span class="fst-italic font-monospace">"<%=path.getPathFile()%>"</span>
                        </h5>
                    </div>
                    <div class="modal-body">
                        <p>Vuoi effettuare il download del report sul tuo computer?</p>
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">ESCI</button>
                        <button type="submit" id="downloadAnteprima" data-bs-dismiss="modal" class="btn btn-primary">CONFERMA</button>
                    </div>
                </div>
            </div>
        </div>
    </form>
</body>
</html>
