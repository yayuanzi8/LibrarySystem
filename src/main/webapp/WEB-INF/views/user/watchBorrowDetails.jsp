<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>查看用户借阅情况</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/layui/css/layui.css" media="all">
</head>
<body>
<div style="background: url('${pageContext.request.contextPath}/static/images/library.jpg');height: 30%"></div>
<ul class="layui-nav">
    <li class="layui-nav-item">
        <a href="javascript:;">借阅管理</a>
        <dl class="layui-nav-child">
            <dd><a href="${pageContext.request.contextPath}/book/overTimeBooks">超期未还图书</a></dd>
            <dd><a href="">续借中的图书</a></dd>
        </dl>
    </li>
    <li class="layui-nav-item layui-this"><a href="${pageContext.request.contextPath}/reader/allReader">用户管理</a></li>
    <li class="layui-nav-item">
        <a href="javascript:;">图书管理</a>
        <dl class="layui-nav-child">
            <dd><a href="${pageContext.request.contextPath}/book/allBook">查看书籍信息</a></dd>
            <dd><a href="${pageContext.request.contextPath}/book/newBook">图书馆书籍入库</a></dd>
        </dl>
    </li>
</ul>
<c:if test="${page_num == 0}">
    <div style="width:50%;margin: 0 auto">
        <img src="${pageContext.request.contextPath}/static/images/no-data.png">
    </div>
</c:if>
<c:if test="${page_num != 0}">
    <table style="width: 100%;margin: 20px auto;" class="layui-table" lay-skin="line">
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
            <th>催还</th>
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
        function returnBook(barCode, credNum, btn) {
            if (confirm("确认归还吗？")) {
                $.ajax({
                    url: "${pageContext.request.contextPath}/readerBook/returnBook",
                    data: "barCode=" + barCode + "&credNum=" + credNum,
                    method: "post",
                    success: function (response) {
                        var message = response.message;
                        if (message === "ok") {
                            layer.msg("已归还", {icon: 1});
                            $(btn).text("已归还");
                            $(btn).css("background-color", "#ff4646")
                            $(btn).unbind("click", returnBook);
                        } else if (message === "error") {
                            layer.msg("归还失败", {icon: 5});
                        }
                    },
                    error: function () {
                        layer.msg("归还失败", {icon: 5});
                    }
                });
            }
        }

        function reminder(credNum, bookName) {
            if (confirm("你确定要催还吗？确定之后后台会自动发送邮件给超期未还读者")) {
                $.ajax({
                    url: "${pageContext.request.contextPath}/reader/reminder/" + credNum + "/" + bookName,
                    method: "post",
                    success: function (response) {
                        var message = response.message;
                        if (message === "ok") {
                            layer.msg("催还成功！");
                        } else if (message === "error") {
                            layer.msg("催还失败！邮件发送失败！", {icon: 5});
                        }
                    },
                    error: function () {
                        layer.msg("催还失败！邮件发送失败！", {icon: 5})
                    }
                });
            }
        }
        function loadHistory(pageNum) {
            $.ajax({
                url: "${pageContext.request.contextPath}/reader/adminGetReaderHistory/" + pageNum + "/${credNum}",
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
                            var td7 = $("<td>" + history.status + "</td>");
                            var td8 = $("<td></td>");
                            if (history.status === "超期") {
                                var btn1 = $("<button onclick='reminder(\"" + history.credNum + "\",\"" + history.bookName + "\")' class='layui-btn layui-btn-danger'>催还</button>");
                                var btn2 = $("<button class='layui-btn layui-bg-green' onclick='returnBook(\"" + history.barCode + "\",\"" + history.credNum + "\",this)'>确认归还</button>");
                                btn2.appendTo(td8);
                                btn1.appendTo(td8);
                            } else {
                                var btn3 = $("<button class='layui-btn layui-btn-normal'>正常</button>")
                                btn3.appendTo(td8);
                            }
                            td0.appendTo(tr);
                            td1.appendTo(tr);
                            td2.appendTo(tr);
                            td3.appendTo(tr);
                            td4.appendTo(tr);
                            td5.appendTo(tr);
                            td6.appendTo(tr);
                            td7.appendTo(tr);
                            td8.appendTo(tr);
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
