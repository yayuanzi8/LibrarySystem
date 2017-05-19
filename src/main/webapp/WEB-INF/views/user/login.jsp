<%--
  Created by IntelliJ IDEA.
  User: yayuanzi8
  Date: 2017/5/18 0018
  Time: 18:15
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>用户登陆</title>
</head>
<body>
<form action="" method="post">
    <input type="number" name="no" placeholder="请输入学号或教职工号" value="201421314405"/><br/>
    <input type="password" name="password" placeholder="请输入密码" value="gaoyisanban67"/><br/>
    <input type="radio" name="role" value="teacher"/>教师
    <input type="radio" name="role" value="student" checked/>学生<br/>
    <input id="rememberMe" name="remember-me" type="checkbox"/>记住我
    <input type="submit" value="提交"/>
</form>
</body>
</html>
