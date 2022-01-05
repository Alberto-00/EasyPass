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
<%String path = (String) request.getAttribute("path");%>
    <h1>Anteprima di <span class="fst-italic font-monospace">"<%=path%>.pdf"</span></h1>
    <form id="myForm" action="${pageContext.request.contextPath}/sessioneServlet/AvvioSessione" method="get">
        <div class="areaPDF">
            <iframe src="http://localhost:8080/Progetto_EasyPass/Report/<%=path%>.pdf#toolbar=0&navpanes=0&scrollbar=0"></iframe>
            <a id="hrefPDF" href="../Progetto_EasyPass/Report/<%=path%>.pdf" download="<%=path%>.pdf"></a>
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
                            <span class="fst-italic font-monospace">"<%=path%>.pdf"</span>
                        </h5>
                        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                    </div>
                    <div class="modal-body">
                        <p>Vuoi effettuare il download del report sul tuo computer?</p>
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">ESCI</button>
                        <button type="submit" id="downloadAnteprima" class="btn btn-primary">CONFERMA</button>
                    </div>
                </div>
            </div>
        </div>
    </form>
</body>
</html>
