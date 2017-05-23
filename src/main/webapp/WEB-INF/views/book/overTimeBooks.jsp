<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>超期未还书籍</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/layui/css/layui.css" media="all">
</head>
<body>
<div style="background: url('${pageContext.request.contextPath}/static/images/library.jpg');height: 30%"></div>
<ul class="layui-nav">
    <li class="layui-nav-item layui-this">
        <a href="javascript:;">借阅管理</a>
        <dl class="layui-nav-child">
            <dd><a href="${pageContext.request.contextPath}/book/overTimeBooks">超期未还图书</a></dd>
            <dd><a href="">续借中的图书</a></dd>
        </dl>
    </li>
    <li class="layui-nav-item"><a href="">用户管理</a></li>
    <li class="layui-nav-item">
        <a href="javascript:;">图书管理</a>
        <dl class="layui-nav-child">
            <dd><a href="">查看书籍信息</a></dd>
            <dd><a href="">图书馆书籍入库</a></dd>
        </dl>
    </li>
</ul>
<c:if test="${pageNum==0}">
    <div style="width:50%;margin: 0 auto">
        <img src="${pageContext.request.contextPath}/static/images/no-data.png">
    </div>
</c:if>
<c:if test="${pageNum!=0}">
    <form class="layui-form" style="margin: 30px auto;padding-left: 30%" onsubmit="return false;">
        <div class="layui-form-item">
            <div class="layui-input-inline">
                <select name="searchType" lay-verify="required" lay-filter="selectSearchType" lay-search="">
                    <option value="bookName">书名</option>
                    <option value="bookNO">中图法分类号</option>
                    <option value="returnDate">应还日期</option>
                </select>
            </div>
            <div class="layui-input-inline">
                <input type="text" id="searchValue" name="searchValue" lay-verify="required" placeholder="请输入"
                       autocomplete="off"
                       class="layui-input">
            </div>
            <div class="layui-input-inline">
                <button class="layui-btn" lay-submit="" lay-filter="search">搜索</button>
            </div>
        </div>
    </form>


    <table style="width: 90%;margin: 20px auto" class="layui-table" lay-skin="line">
        <colgroup>
            <col width="150">
            <col width="150">
            <col width="200">
            <col>
        </colgroup>
        <thead>
        <tr>
            <th>证件号</th>
            <th>条形码</th>
            <th>书名</th>
            <th>中图法分类号</th>
            <th>借阅日期</th>
            <th>应还日期</th>
            <th>馆藏地</th>
            <th>状态</th>
            <th>催还</th>
        </tr>
        </thead>
        <tbody id="historyItems">

        </tbody>
    </table>
    <div id="demo2"></div>
</c:if>

<script src="http://apps.bdimg.com/libs/jquery/2.1.4/jquery.min.js"></script>
<script src="${pageContext.request.contextPath}/static/layui/layui.js"></script>
<script type="text/javascript">
    layui.use('element', function () {
        var element = layui.element(); //导航的hover效果、二级菜单等功能，需要依赖element模块

        //监听导航点击
        element.on('nav(demo)', function (elem) {
            //console.log(elem)
            layer.msg(elem.text());
        });
    });
    <c:if test="${pageNum!=0}">
    Date.prototype.Format = function (fmt) { //author: meizz
        var o = {
            "M+": this.getMonth() + 1, //月份
            "d+": this.getDate(), //日
            "h+": this.getHours(), //小时
            "m+": this.getMinutes(), //分
            "s+": this.getSeconds(), //秒
            "q+": Math.floor((this.getMonth() + 3) / 3), //季度
            "S": this.getMilliseconds() //毫秒
        };
        if (/(y+)/.test(fmt)) fmt = fmt.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length));
        for (var k in o)
            if (new RegExp("(" + k + ")").test(fmt)) fmt = fmt.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k]) : (("00" + o[k]).substr(("" + o[k]).length)));
        return fmt;
    };
    var layer;
    //分页获取数据
    function loadHistory(pageNum) {
        $.ajax({
            url: "${pageContext.request.contextPath}/book/overTimeBooksInPagination/" + pageNum,
            method: "post",
            success: function (response) {
                $("#historyItems").empty();
                var message = response.message;
                if (message === "ok") {
                    var historyList = response.historyList;
                    for (var i = 0; i < historyList.length; i++) {
                        var history = historyList[i];
                        var tr = $("<tr></tr>");
                        var td = $("<td>" + history.credNum + "</td>")
                        var td0 = $("<td>" + (history.barCode) + "</td>");
                        var td1 = $("<td>" + (history.cnum + history.bookNO) + "</td>");
                        var td2 = $("<td>" + history.bookName + "</td>");
                        var td4 = $("<td>" + new Date(history.borrowDate).Format("yyyy-MM-dd") + "</td>");
                        var td5 = $("<td>" + new Date(history.returnDate).Format("yyyy-MM-dd") + "</td>");
                        var td6 = $("<td>" + history.storeAddress + "</td>");
                        var td7 = $("<td>" + history.status + "</td>");
                        var td8 = $("<td><button onclick='reminder(" + history.credNum + ",\"" + history.bookName + "\")'class='layui-btn layui-btn-danger'>催还</button></td>");
                        td.appendTo(tr);
                        td0.appendTo(tr);
                        td2.appendTo(tr);
                        td1.appendTo(tr);
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

    function loadHistoryBySearch(pageNum, searchType, searchValue) {
        $.ajax({
            url: "${pageContext.request.contextPath}/readerBook/searchOverTimeBooks",
            method: "post",
            data: "pageNum=" + pageNum + "&searchType=" + searchType + "&searchValue=" + searchValue,
            success: function (response) {
                $("#historyItems").empty();
                layer.msg("数据加载完成",{icon:1});
                var message = response.message;
                if (message === "ok") {
                    var historyList = response.historyList;
                    for (var i = 0; i < historyList.length; i++) {
                        var history = historyList[i];
                        var tr = $("<tr></tr>");
                        var td = $("<td>" + history.credNum + "</td>")
                        var td0 = $("<td>" + (history.barCode) + "</td>");
                        var td1 = $("<td>" + (history.cnum + history.bookNO) + "</td>");
                        var td2 = $("<td>" + history.bookName + "</td>");
                        var td4 = $("<td>" + new Date(history.borrowDate).Format("yyyy-MM-dd") + "</td>");
                        var td5 = $("<td>" + new Date(history.returnDate).Format("yyyy-MM-dd") + "</td>");
                        var td6 = $("<td>" + history.storeAddress + "</td>");
                        var td7 = $("<td>" + history.status + "</td>");
                        var td8 = $("<td><button onclick='reminder(" + history.credNum + ",\"" + history.bookName + "\")'class='layui-btn layui-btn-danger'>催还</button></td>");
                        td.appendTo(tr);
                        td0.appendTo(tr);
                        td2.appendTo(tr);
                        td1.appendTo(tr);
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

    layui.use(['laypage', 'layer', 'form', 'laydate'], function () {
        var laypage = layui.laypage;
        var layer = layui.layer,
            form = layui.form(),
            laydate = layui.laydate;
        laypage({
            cont: 'demo2',
            pages: ${pageNum},
            first: 1,
            last: ${pageNum},
            skin: '#1E9FFF',
            jump: function (obj) {
                loadHistory(obj.curr);
            }
        });

        function popupDate() {
            layui.laydate({elem: this})
        };

        form.on("select(selectSearchType)", function (data) {
            if (data.value === "returnDate") {
                $("#searchValue").prop("lay-verify", "date");
                $("#searchValue").bind("click", popupDate);
            } else {
                $("#searchValue").removeProp("lay-verify");
                $("#searchValue").unbind("click", popupDate);
            }
        });

        form.on("submit(search)", function (data) {
            var field = data.field;
            $.ajax({
                url: "${pageContext.request.contextPath}/readerBook/searchOverTimeBooks",
                data: "searchType=" + field.searchType + "&searchValue=" + field.searchValue + "&pageNum=1",
                method: "post",
                processData: false,
                dataType: "json",
                success: function (response) {
                    var message = response.message;
                    if (message === "ok") {
                        if (response.totalPage != 0) {
                            laypage({
                                cont: 'demo2',
                                pages: response.totalPage,
                                first: 1,
                                last: response.totalPage,
                                skin: '#1E9FFF',
                                jump: function (obj) {
                                    loadHistoryBySearch(obj.curr, field.searchType, field.searchValue);
                                }
                            });
                        } else {
                            layer.msg("没有数据！",{icon:5});
                        }
                    } else if (message === "error") {

                    }
                },
                error: function () {

                }
            });
            return false;
        });
    });
    </c:if>
</script>
</body>
</html>
