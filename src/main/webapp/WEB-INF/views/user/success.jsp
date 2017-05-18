<%@ taglib prefix="sst" uri="http://www.springframework.org/security/tags" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>登陆成功！</title>
</head>
<body>
<sst:authorize access="isAuthenticated()">
    欢迎你！<sst:authentication property="principal.username"/>
    你的密码是：<sst:authentication property="principal.password"/>
    入学年份：<sst:authentication property="principal.entryYear"/>
</sst:authorize>
</body>
</html>
