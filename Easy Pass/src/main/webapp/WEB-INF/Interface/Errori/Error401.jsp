<%--
  Created by IntelliJ IDEA.
  User: albmo
  Date: 22/12/2021
  Time: 12:58
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <jsp:include page="/WEB-INF/Interface/Partials/Head.jsp">
        <jsp:param name="errorStyles" value="errors"/>
        <jsp:param name="title" value="Easy Pass | Error 401"/>
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
    <div class='_404'>401</div>
    <hr>
    <div class='_1'>No authorization found.</div>
    <div class='_2'>This page is not publically avaible.</div>
    <div class='_2'>To access it please login first.</div>
    <a class='btn' href='${pageContext.request.contextPath}/autenticazione/'>BACK TO HOME-PAGE</a>
</div>
</body>
</html>
