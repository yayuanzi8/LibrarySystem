<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>条形码查书</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/layui/css/layui.css" media="all">
</head>
<body>
<div style="background: url('${pageContext.request.contextPath}/static/images/library.jpg');height: 30%"></div>
<ul class="layui-nav">
    <li class="layui-nav-item">
        <a href="javascript:;">借阅管理</a>
        <dl class="layui-nav-child">
            <dd><a href="${pageContext.request.contextPath}/book/overTimeBooks">超期未还图书</a></dd>
        </dl>
    </li>
    <li class="layui-nav-item">
        <a href="javascript:;">用户管理</a>
        <dl class="layui-nav-child">
            <dd><a href="${pageContext.request.contextPath}/reader/allReader">全部读者</a></dd>
            <dd><a href="${pageContext.request.contextPath}/reader/newReader">读者录入</a></dd>
        </dl>
    </li>
    <li class="layui-nav-item layui-this">
        <a href="javascript:;">图书管理</a>
        <dl class="layui-nav-child">
            <dd><a href="${pageContext.request.contextPath}/book/allBook">查看书籍信息</a></dd>
            <dd><a href="${pageContext.request.contextPath}/book/searchByBarCode">条形码查书</a></dd>
            <dd><a href="${pageContext.request.contextPath}/book/newBook">图书馆书籍入库</a></dd>
        </dl>
    </li>
</ul>
<form class="layui-form" style="margin: 30px auto;padding-left: 40%" onsubmit="return false;">
    <div class="layui-form-item">
        <div class="layui-input-inline">
            <input type="text" id="searchValue" name="barCode" lay-verify="required" placeholder="请输入"
                   autocomplete="off"
                   class="browser-default layui-input">
        </div>
        <div class="layui-input-inline">
            <button class="layui-btn" lay-submit="" lay-filter="search">搜索</button>
        </div>
    </div>
</form>

<table id="bookTable" style="width:100%;margin: 20px auto;" class="layui-table" lay-skin="line">
    <thead>
    <tr>
        <th>条形码</th>
        <th>索书号</th>
        <th>书名</th>
        <th>作者</th>
        <th>出版社</th>
        <th>翻译</th>
        <th>页数</th>
        <th>类型</th>
        <th>馆藏地</th>
        <th>单价</th>
        <th>操作</th>
    </tr>
    </thead>
    <tbody id="bookItems">

    </tbody>
</table>

<%--借阅历史--%>
<div id="bookBorrowDetailsArea" style="display: none">
    <table class="layui-table">
        <thead>
        <tr>
            <th>条形码</th>
            <th>索书号</th>
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

    function watchBookDetails(bookNO) {
        alert(bookNO);
    }

    function editBookInfo(bookNO) {
        window.open("${pageContext.request.contextPath}/book/editBookInfo?bookNO=" + bookNO);
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

    function loadBookBorrowDetails(barCode, pageNum) {
        $.ajax({
            url: "${pageContext.request.contextPath}/readerBook/loadBookBorrowHistoryByBarCode",
            data: "barCode=" + barCode + "&pageNum=" + pageNum,
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
                        if (history.status === "超期") {
                            var reminderBtn = $("<button onclick='reminder(\"" + history.credNum + "\",\"" + history.bookName + "\")' class='layui-btn layui-btn-danger'>催还</button>");
                            reminderBtn.appendTo(td7);
                        } else {
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

    function watchBookBorrowDetails(barCode) {
        var $bookBorrowDetails = $('#bookBorrowDetails');
        $.ajax({
            url: "${pageContext.request.contextPath}/readerBook/loadBookBorrowHistoryPageNumByBarCode",
            method: "post",
            data: "barCode=" + barCode,
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
                                    loadBookBorrowDetails(barCode, obj.curr);
                                }
                            });
                        });
                    } else {
                        layer.msg("没有数据！", {icon: 5,offset:"200px"});
                    }
                }
            }
        });
    }

    function deleteBook(barCode) {
        layer.confirm("你确定要删除吗？", {
            offset: "200px"
        }, function (index) {
            $.ajax({
                url: "${pageContext.request.contextPath}/book/deleteBookByBarCode",
                method: "post",
                data: "barCode=" + barCode,
                success: function (response) {
                    var msg = response.message;
                    if (msg === "ok") {
                        layer.msg("删除成功！", {icon: 1, offset: "200px"});
                    } else if (msg === "error") {
                        layer.msg("删除失败！请稍后再试！", {icon: 5, offset: "200px"});
                    } else if (msg === "hasBorrow") {
                        layer.msg("删除失败！当前还有人借阅！请查看借阅历史进行催还！", {icon: 5, offset: "200px"});
                    }
                }
            });
            layer.close(index);
        }, function (index) {
            layer.close(index);
        });
    }

    layui.use(['layer', 'form'], function () {
        var layer = layui.layer;
        var form = layui.form();
        form.on("submit(search)", function (data) {
            var barCode = data.field.barCode;
            $.ajax({
                url: "${pageContext.request.contextPath}/book/searchBookByBarCode",
                method: "post",
                data: "barCode=" + barCode,
                success: function (response) {
                    var msg = response.message;
                    if (msg === "ok") {
                        var book = response.bookDetails.book;
                        var $bookItems = $("#bookItems");
                        $bookItems.empty();
                        var $tr = $("<tr></tr>");
                        var $td0 = $("<td>" + response.bookDetails.barCode + "</td>");
                        var $td1 = $("<td>" + (book.cnum + book.bookNO) + "</td>");
                        var $td2 = $("<td>" + book.bookName + "</td>");
                        var $td3 = $("<td>" + book.author + "</td>");
                        var $td4 = $("<td>" + book.press + "</td>");
                        var $td5 = $("<td>" + book.translator + "</td>");
                        var $td6 = $("<td>" + book.pageNum + "</td>");
                        var $td7 = $("<td>" + book.bookType + "</td>");
                        var $td8 = $("<td>" + book.storeAddress + "</td>");
                        var $td9 = $("<td>" + book.price + "</td>");
                        var $td10 = $("<td></td>");
                        var $btn1 = $("<button style='width: 120px' onclick='watchBookDetails(\"" + book.bookNO + "\")' class='layui-btn layui-btn-danger'>查看详情</button>");
                        var $btn2 = $("<button style='margin-top: 5px;width: 120px' class='layui-btn layui-btn-normal' onclick='watchBookBorrowDetails(\"" + response.bookDetails.barCode + "\")'>查看借阅记录</button>");
                        var $div1 = $("<div style='margin-top: 3px;display: inline-block' class='layui-btn-group'></div>");
                        var $btn3 = $("<button style='width: 60px' onclick='editBookInfo(\"" + book.bookNO + "\")' class='layui-btn layui-bg-blue'>编辑</button>");
                        var $btn4 = $("<button style='width: 60px' onclick='deleteBook(\"" + response.bookDetails.barCode + "\")' class='layui-btn layui-btn-warm'>删除</button>");
                        $div1.append($btn3);
                        $div1.append($btn4);
                        $td0.appendTo($tr);
                        $td1.appendTo($tr);
                        $td2.appendTo($tr);
                        $td3.appendTo($tr);
                        $td4.appendTo($tr);
                        $td5.appendTo($tr);
                        $td6.appendTo($tr);
                        $td7.appendTo($tr);
                        $td8.appendTo($tr);
                        $td9.appendTo($tr);
                        $td10.append($btn1);
                        $td10.append("<br/>");
                        $td10.append($btn2);
                        $td10.append("<br/>");
                        $td10.append($div1);
                        $td10.append("<br/>");
                        $td10.appendTo($tr);
                        $tr.appendTo($bookItems);
                        layer.msg("搜索完毕！", {icon: 1, offset: "200px"});
                    } else if (msg === "error") {
                        layer.msg("搜索失败！请稍后再试！", {icon: 5, offset: "200px"});
                    } else if (msg === "noResult") {
                        layer.msg("没有结果", {icon: 3, offset: "200px"});
                    }
                }
            });
        });
    });
</script>
</body>
</html>
