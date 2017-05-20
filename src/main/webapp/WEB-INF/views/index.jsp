<%@ taglib prefix="sst" uri="http://www.springframework.org/security/tags" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<sst:authorize access="!isAuthenticated()">
    你还没登陆！<a href="${pageContext.request.contextPath}/user/login">登陆</a>
</sst:authorize>
<sst:authorize access="isAuthenticated()">
    <sst:authentication property="principal.username"/><br/>
    <sst:authentication property="principal.email"/><br/>
    <sst:authentication property="principal.readerType"/><br/>
    <a href="${pageContext.request.contextPath}/user/logout">注销</a>
</sst:authorize>
</body>
</html>
