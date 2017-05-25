<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>全部书籍</title>
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
    <li class="layui-nav-item"><a href="${pageContext.request.contextPath}/reader/allReader">用户管理</a></li>
    <li class="layui-nav-item layui-this">
        <a href="javascript:;">图书管理</a>
        <dl class="layui-nav-child">
            <dd><a href="${pageContext.request.contextPath}/book/allBook">查看书籍信息</a></dd>
            <dd><a href="${pageContext.request.contextPath}/book/newBook">图书馆书籍入库</a></dd>
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
                <select class="browser-default" name="searchType" lay-verify="required" lay-filter="selectSearchType"
                        lay-search="">
                    <option value="bookName">书名</option>
                    <option value="bookNO">中图法分类号</option>
                    <option value="author">作者</option>
                    <option value="press">出版社</option>
                </select>
            </div>
            <div class="layui-input-inline">
                <input type="text" id="searchValue" name="searchValue" lay-verify="required" placeholder="请输入"
                       autocomplete="off"
                       class="browser-default layui-input">
            </div>
            <div class="layui-input-inline">
                <button class="layui-btn" lay-submit="" lay-filter="search">搜索</button>
            </div>
        </div>
    </form>

    <table style="width:100%;margin: 20px auto" class="layui-table" lay-skin="line">
        <thead>
        <tr>
            <th>中图法编号</th>
            <th>书名</th>
            <th>作者</th>
            <th>出版社</th>
            <th>翻译</th>
            <th>页数</th>
            <th>类型</th>
            <th>馆藏地</th>
            <th>单价</th>
            <th>查看详情</th>
        </tr>
        </thead>
        <tbody id="bookItems">

        </tbody>
    </table>
    <div id="demo2"></div>
</c:if>

<div id="bookBorrowDetailsArea" style="display: none">
    <table class="layui-table">
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
        <tbody id="bookBorrowDetails">

        </tbody>
    </table>
    <div style="text-align: center" id="demo3"></div>
</div>

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
    <c:if test="${pageNum!=0}">
    var layer;

    function watchBookDetails(bookNO) {
        alert(bookNO);
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

    function loadBookBorrowDetails(bookNO, pageNum) {
        $.ajax({
            url: "${pageContext.request.contextPath}/readerBook/loadBookBorrowHistory",
            data: "bookNO=" + bookNO + "&pageNum=" + pageNum,
            method: "post",
            success: function (response) {
                var message = response.message;
                if (message === "ok") {
                    var $bookBorrowDetails = $('#bookBorrowDetails');
                    $bookBorrowDetails.empty();
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
                        var td7 = $("<td></td>");
                        if (history.status === "超期"){
                            var reminderBtn = $("<button onclick='reminder(\""+history.credNum+"\",\""+history.bookName+"\")' class='layui-btn layui-btn-danger'>催还</button>");
                            reminderBtn.appendTo(td7);
                        }else {
                            td7.text(history.status);
                        }
                        td0.appendTo(tr);
                        td1.appendTo(tr);
                        td2.appendTo(tr);
                        td3.appendTo(tr);
                        td4.appendTo(tr);
                        td5.appendTo(tr);
                        td6.appendTo(tr);
                        td7.appendTo(tr);
                        tr.appendTo($bookBorrowDetails);
                    }
                    layer.open({
                        type: 1,
                        skin: 'layui-layer-molv',
                        area: '85%',
                        offset: 't',
                        content: $('#bookBorrowDetailsArea') //这里content是一个DOM，注意：最好该元素要存放在body最外层，否则可能被其它的相对元素所影响
                    });
                }
            }
        });
    }

    function watchBookBorrowDetails(bookNO) {
        var $bookBorrowDetails = $('#bookBorrowDetails');
        var noData = false;
        $.ajax({
            url: "${pageContext.request.contextPath}/readerBook/loadBookBorrowHistoryPageNum",
            method: "post",
            data: "bookNO=" + bookNO,
            success: function (response) {
                $bookBorrowDetails.empty();
                var message = response.message;
                if (message === "ok") {
                    if (response.totalPage != 0) {
                        layui.use(['laypage', 'layer'], function () {
                            var laypage = layui.laypage
                                , layer = layui.layer;
                            laypage({
                                cont: 'demo3',
                                pages: response.totalPage,
                                first: 1,
                                last: response.totalPage,
                                prev: '<em><</em>',
                                next: '<em>></em>',
                                jump: function (obj) {
                                    loadBookBorrowDetails(bookNO, obj.curr);
                                }
                            });
                        });
                    } else {
                        layer.msg("没有数据！", {icon: 5});
                    }
                }
            }
        });

    }

    //分页获取数据
    function loadBookItems(pageNum) {
        $.ajax({
            url: "${pageContext.request.contextPath}/book/allBookInPagination/" + pageNum,
            method: "post",
            success: function (response) {
                $("#bookItems").empty();
                var message = response.message;
                if (message === "ok") {
                    var bookList = response.bookList;
                    for (var i = 0; i < bookList.length; i++) {
                        var book = bookList[i];
                        var tr = $("<tr></tr>");
                        var td = $("<td>" + (book.cnum + book.bookNO) + "</td>")
                        var td0 = $("<td>" + book.bookName + "</td>");
                        var td1 = $("<td>" + book.author + "</td>");
                        var td2 = $("<td>" + book.press + "</td>");
                        var td4 = $("<td>" + book.translator + "</td>");
                        var td5 = $("<td>" + book.pageNum + "</td>");
                        var td6 = $("<td>" + book.bookType + "</td>");
                        var td7 = $("<td>" + book.storeAddress + "</td>");
                        var td8 = $("<td>￥" + book.price + "</td>");
                        var td9 = $("<td><button style='width: 120px' onclick='watchBookDetails(\"" + book.bookNO + "\")' class='layui-btn layui-btn-danger'>查看详情</button></td>");
                        td9.append($("<br/>"));
                        var borrowDetails = $("<button style='margin-top: 5px;width: 120px' class='layui-btn layui-btn-normal' onclick='watchBookBorrowDetails(\"" + book.bookNO + "\")'>查看借阅记录</button>");
                        td9.append(borrowDetails);
                        td.appendTo(tr);
                        td0.appendTo(tr);
                        td1.appendTo(tr);
                        td2.appendTo(tr);
                        td4.appendTo(tr);
                        td5.appendTo(tr);
                        td6.appendTo(tr);
                        td7.appendTo(tr);
                        td8.appendTo(tr);
                        td9.appendTo(tr);
                        tr.appendTo($("#bookItems"));
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
    layui.use(['laypage', 'layer', 'form', 'laydate'], function () {
        var laypage = layui.laypage;
        layer = layui.layer;
        var form = layui.form(),
            laydate = layui.laydate;
        laypage({
            cont: 'demo2',
            pages: ${pageNum},
            first: 1,
            last: ${pageNum},
            skin: '#1E9FFF',
            jump: function (obj) {
                loadBookItems(obj.curr);
            }
        });
    });
    </c:if>
</script>
</body>
</html>
