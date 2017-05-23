<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>我的借阅历史</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/layui/css/layui.css" media="all">
</head>
<body>
<div style="background: url('${pageContext.request.contextPath}/static/images/library.jpg');height: 40%"></div>
<ul class="layui-nav">
    <li class="layui-nav-item"><a href="${pageContext.request.contextPath}/reader/${cred_num}">证件信息</a></li>
    <li class="layui-nav-item layui-this"><a
            href="${pageContext.request.contextPath}/reader/history/${cred_num}">借阅历史</a></li>
    <li class="layui-nav-item"><a href="${pageContext.request.contextPath}/reader/currentBorrow/${cred_num}">当前借阅</a>
    </li>
</ul>
<c:if test="${page_num == 0}">
    <div style="width:50%;margin: 0 auto">
        <img src="${pageContext.request.contextPath}/static/images/no-data.png">
    </div>
</c:if>
<c:if test="${page_num != 0}">
    <table style="width: 80%;margin: 20px auto;" class="layui-table" lay-skin="line">
        <thead>
        <tr>
            <th>条形码</th>
            <th>中图法编号</th>
            <th>书名</th>
            <th>作者</th>
            <th>借阅时间</th>
            <th>归还时间</th>
            <th>馆藏地点</th>
            <th>状态</th>
        </tr>
        </thead>
        <tbody id="historyItems"></tbody>
    </table>
    <div style="text-align: center" id="demo3"></div>
</c:if>
<script src="http://apps.bdimg.com/libs/jquery/2.1.4/jquery.min.js"></script>
<script src="${pageContext.request.contextPath}/static/layui/layui.js"></script>
<script src="${pageContext.request.contextPath}/static/reader_common.js"></script>
<c:if test="${page_num!=0}">
    <script type="text/javascript">
        function loadHistory(pageNum) {
            $.ajax({
                url: "${pageContext.request.contextPath}/reader/historyPagination/" + pageNum,
                method: "post",
                success: function (response) {
                    $("#historyItems").empty();
                    var message = response.message;
                    if (message === "ok") {
                        var historyList = response.historyList;
                        for (var i = 0; i < historyList.length; i++) {
                            var history = historyList[i];
                            var tr = $("<tr></tr>");
                            var td0 = $("<td>" + (history.barCode) + "</td>");
                            var td1 = $("<td>" + (history.cnum + history.bookNO) + "</td>");
                            var td2 = $("<td>" + history.bookName + "</td>");
                            var td3 = $("<td>" + history.author + "</td>");
                            var td4 = $("<td>" + new Date(history.borrowDate).Format("yyyy-MM-dd") + "</td>");
                            var td5 = $("<td>" + new Date(history.returnDate).Format("yyyy-MM-dd") + "</td>");
                            var td6 = $("<td>" + history.storeAddress + "</td>");
                            var td7 = $("<td>"+history.status+"</td>");
                            td0.appendTo(tr);
                            td1.appendTo(tr);
                            td2.appendTo(tr);
                            td3.appendTo(tr);
                            td4.appendTo(tr);
                            td5.appendTo(tr);
                            td6.appendTo(tr);
                            td7.appendTo(tr);
                            tr.appendTo($("#historyItems"));
                        }
                    } else if (message === "error") {
                        alert("错误！");
                    }
                },
                error: function () {
                    alert("服务器出错！");
                }
            });
        }
        layui.use(['laypage', 'layer'], function () {
            var laypage = layui.laypage
                , layer = layui.layer;
            laypage({
                cont: 'demo3',
                pages: "${page_num}",
                first: 1,
                last: "${page_num}",
                prev: '<em><</em>',
                next: '<em>></em>',
                jump: function (obj) {
                    loadHistory(obj.curr);
                }
            });
        });
    </script>
</c:if>
</body>
</html>
