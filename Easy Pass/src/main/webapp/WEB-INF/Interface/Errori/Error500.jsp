<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="it">
<head>
    <jsp:include page="/WEB-INF/Interface/Partials/Head.jsp">
        <jsp:param name="errorStyles" value="errors"/>
        <jsp:param name="title" value="Easy Pass | Error 500"/>
    </jsp:include>
</head>
<body>
<div id="clouds">
    <div class="cloud x1"></div>
    <div class="cloud x1_5"></div>
    <div class="cloud x2"></div>
    <div class="cloud x3"></div>
    <div class="cloud x4"></div>
    <div class="cloud x5"></div>
</div>
<div class='c'>
    <div class='_404'>500</div>
    <hr>
    <div class='_1'>ERROR</div>
    <div class='_2'>Internal Server Error.</div>
    <a class='btn' href='${pageContext.request.contextPath}/autenticazione/'>BACK TO HOME-PAGE</a>
</div>
</body>
</html>
