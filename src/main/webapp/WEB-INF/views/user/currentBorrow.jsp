<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>我的当前借阅</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/layui/css/layui.css" media="all">
    <style type="text/css">

        table {
            font-family: 微软雅黑, "Times New Roman", sans-serif, Verdana;
        }

        .overtime, .canBorrow, .cannotBorrow {
            border: none;
            padding: 5px 10px;
            color: white;
            border-radius: 3px;
        }

        .overtime, .canBorrow:hover, .cannotBorrow:hover {
            cursor: pointer;
        }

    </style>
</head>
<body>
<div style="background: url('${pageContext.request.contextPath}/static/images/library.jpg');height: 40%"></div>
<ul class="layui-nav">
    <li class="layui-nav-item">
        <a href="${pageContext.request.contextPath}/reader/${cred_num}">证件信息</a>
    </li>
    <li class="layui-nav-item">
        <a href="${pageContext.request.contextPath}/reader/history/${cred_num}">借阅历史</a>
    </li>
    <li class="layui-nav-item layui-this">
        <a href="${pageContext.request.contextPath}/reader/currentBorrow/${cred_num}">当前借阅</a>
    </li>
</ul>
<c:if test="${historyList.size() == 0}">
    <div style="width:50%;margin: 0 auto">
        <img src="${pageContext.request.contextPath}/static/images/no-data.png">
    </div>
</c:if>
<c:if test="${historyList.size()!=0}">
    <p style="color:red;margin: 20px auto;width:80%;">当前借阅(${reader.currentBorrowNum})/最大借阅(${reader.maxAvailable})</p>
    <table style="width: 80%;margin: 20px auto;" class="layui-table" lay-skin="line">
        <tr>
            <th>条形码</th>
            <th>中图法编号</th>
            <th>书名</th>
            <th>作者</th>
            <th>借阅日期</th>
            <th>应还日期</th>
            <th>馆藏地</th>
            <th>状态</th>
            <th>续借</th>
        </tr>
        <c:forEach items="${historyList}" var="history">
            <tr>
                <td>${history.barCode}</td>
                <td>${history.cnum}${history.bookNO}</td>
                <td>${history.bookName}</td>
                <td>${history.author}</td>
                <fmt:formatDate value="${history.borrowDate}" var="borrowDate" scope="page" pattern="yyyy-MM-dd"/>
                <td>${borrowDate}</td>
                <fmt:formatDate value="${history.returnDate}" var="returnDate" scope="page" pattern="yyyy-MM-dd"/>
                <td>${returnDate}</td>
                <td>${history.storeAddress}</td>
                <td>${history.status}</td>
                <td>
                    <c:if test="${history.status.equals('续借中')}">
                        <button disabled="disabled" class="layui-bg-green cannotBorrow">不可续借</button>
                    </c:if>
                    <c:if test="${history.status.equals('借阅中')}">
                        <button class="layui-bg-blue canBorrow" onclick="renew('${history.barCode}',this)">续借</button>
                    </c:if>
                    <c:if test="${history.status.equals('超期')}">
                        <button class="layui-btn-danger overtime">超期</button>
                    </c:if>
                </td>
            </tr>
        </c:forEach>
    </table>
</c:if>

<script src="http://apps.bdimg.com/libs/jquery/2.1.4/jquery.min.js"></script>
<script src="${pageContext.request.contextPath}/static/layui/layui.js"></script>
<script src="${pageContext.request.contextPath}/static/reader_common.js"></script>
<script type="text/javascript">
    var layer;
    layui.use('layer', function () { //独立版的layer无需执行这一句
        var $ = layui.jquery;
        layer = layui.layer; //独立版的layer无需执行这一句
    });
    function renew(barCode, btn) {
        $.ajax({
            url: "${pageContext.request.contextPath}/reader/renew/" + barCode,
            method: "post",
            success: function (response) {
                var message = response.message;
                if (message === "ok") {
                    var $canBorrowBtn = $(btn);
                    $canBorrowBtn.text("不可续借");
                    $canBorrowBtn.removeClass("canBorrow");
                    $canBorrowBtn.addClass("cannotBorrow");
                    $canBorrowBtn.addClass("layui-bg-green");
                    $canBorrowBtn.prop("disabled", true);
                    $canBorrowBtn.unbind("click", renew);
                    layer.msg("续借成功！");
                } else if (message === "error") {
                    layer.msg("续借失败！");
                }
            },
            error: function () {
                layer.msg("续借失败！");
            }
        });
    }
</script>
</body>
</html>
