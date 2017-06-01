<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>全部读者</title>
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
    <li class="layui-nav-item layui-this">
        <a href="javascript:;">用户管理</a>
        <dl class="layui-nav-child">
            <dd><a href="${pageContext.request.contextPath}/reader/allReader">全部读者</a></dd>
            <dd><a href="${pageContext.request.contextPath}/reader/newReader">读者录入</a></dd>
        </dl>
    </li>
    <li class="layui-nav-item">
        <a href="javascript:;">图书管理</a>
        <dl class="layui-nav-child">
            <dd><a href="${pageContext.request.contextPath}/book/allBook">查看书籍信息</a></dd>
            <dd><a href="${pageContext.request.contextPath}/book/searchByBarCode">条形码查书</a></dd>
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
                <select name="searchType" lay-verify="required" lay-filter="selectSearchType" lay-search="">
                    <option value="credNum">证件号</option>
                    <option value="name">姓名</option>
                    <option value="entryDate">入学年份</option>
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
        <thead>
        <tr>
            <th>证件号</th>
            <th>姓名</th>
            <th>入学时间</th>
            <th>毕业时间</th>
            <th>最大借阅数</th>
            <th>读者类型</th>
            <th>邮箱</th>
            <th>当前借阅数</th>
            <th>查看借阅详情</th>
        </tr>
        </thead>
        <tbody id="readerItems">

        </tbody>
    </table>
    <div style="text-align: center" id="demo2"></div>
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

    function watchBorrowDetails(credNum) {
        window.open("${pageContext.request.contextPath}/reader/watchBorrowDetails/" + credNum);
    }

    //分页获取数据
    function loadReader(pageNum) {
        $.ajax({
            url: "${pageContext.request.contextPath}/reader/getReaderInPagination/" + pageNum,
            method: "post",
            success: function (response) {
                $("#readerItems").empty();
                var message = response.message;
                if (message === "ok") {
                    var readerList = response.readerList;
                    for (var i = 0; i < readerList.length; i++) {
                        var reader = readerList[i];
                        var tr = $("<tr></tr>");
                        var td = $("<td>" + reader.credNum + "</td>");
                        var td0 = $("<td>" + reader.name + "</td>");
                        var td1 = $("<td>" + new Date(reader.startTime).Format("yyyy-MM-dd") + "</td>");
                        var td2 = $("<td>" + new Date(reader.endTime).Format("yyyy-MM-dd") + "</td>");
                        var td4 = $("<td>" + reader.maxAvailable + "</td>");
                        var td5 = $("<td>" + reader.readerType + "</td>");
                        var td6 = $("<td><a href='mailto:" + reader.email + "'>" + reader.email + "</a></td>");
                        var td7 = $("<td>" + reader.currentBorrowNum + "</td>");
                        var td8 = $("<td><button data-method='notice' data-type='auto' onclick='watchBorrowDetails(" + reader.credNum + ")' class='layui-btn layui-btn-warm'>查看借阅情况</button></td>");
                        td.appendTo(tr);
                        td0.appendTo(tr);
                        td1.appendTo(tr);
                        td2.appendTo(tr);
                        td4.appendTo(tr);
                        td5.appendTo(tr);
                        td6.appendTo(tr);
                        td7.appendTo(tr);
                        td8.appendTo(tr);
                        tr.appendTo($("#readerItems"));
                    }
                    $2('#readerItems').find('.layui-btn').on('click', function () {
                        var othis = $2(this), method = othis.data('method');
                        active[method] ? active[method].call(this, othis, othis.attr('crednum')) : '';
                    });
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
        var form = layui.form(),
            laydate = layui.laydate;
        $2 = layui.jquery;
        layer = layui.layer;
        laypage({
            cont: 'demo2',
            pages: ${pageNum},
            first: 1,
            last: ${pageNum},
            skin: '#1E9FFF',
            jump: function (obj) {
                loadReader(obj.curr);
            }
        });

        //弹出日期框
        function popupDate() {
            layui.laydate({elem: this})
        }

        //改变搜索类型的时候调用
        form.on("select(selectSearchType)", function (data) {
            if (data.value === "entryDate") {
                $("#searchValue").prop("lay-verify", "date");
                $("#searchValue").bind("click", popupDate);
            } else {
                $("#searchValue").removeProp("lay-verify");
                $("#searchValue").unbind("click", popupDate);
            }
        });

        function loadReaderBySearch(pageNum, searchType, searchValue) {
            $.ajax({
                url: "${pageContext.request.contextPath}/reader/searchReader",
                method: "post",
                data: "pageNum=" + pageNum + "&searchType=" + searchType + "&searchValue=" + searchValue,
                success: function (response) {
                    $("#readerItems").empty();
                    layer.msg("数据加载完成", {icon: 1});
                    var message = response.message;
                    if (message === "ok") {
                        var readerList = response.readerList;
                        for (var i = 0; i < readerList.length; i++) {
                            var reader = readerList[i];
                            var tr = $("<tr></tr>");
                            var td = $("<td>" + reader.credNum + "</td>");
                            var td0 = $("<td>" + reader.name + "</td>");
                            var td1 = $("<td>" + new Date(reader.startTime).Format("yyyy-MM-dd") + "</td>");
                            var td2 = $("<td>" + new Date(reader.endTime).Format("yyyy-MM-dd") + "</td>");
                            var td4 = $("<td>" + reader.maxAvailable + "</td>");
                            var td5 = $("<td>" + reader.readerType + "</td>");
                            var td6 = $("<td><a href='mailto:" + reader.email + "'>" + reader.email + "</a></td>");
                            var td7 = $("<td>" + reader.currentBorrowNum + "</td>");
                            var td8 = $("<td><button data-method='notice' data-type='auto' onclick='watchBorrowDetails(" + reader.credNum + ")' class='layui-btn layui-btn-warm'>查看借阅情况</button></td>");
                            td.appendTo(tr);
                            td0.appendTo(tr);
                            td1.appendTo(tr);
                            td2.appendTo(tr);
                            td4.appendTo(tr);
                            td5.appendTo(tr);
                            td6.appendTo(tr);
                            td7.appendTo(tr);
                            td8.appendTo(tr);
                            tr.appendTo($("#readerItems"));
                        }
                        $2('#readerItems').find('.layui-btn').on('click', function () {
                            var othis = $2(this), method = othis.data('method');
                            active[method] ? active[method].call(this, othis) : '';
                        });
                    } else if (message === "error") {
                        alert("错误！");
                    }
                },
                error: function () {
                    alert("服务器出错！");
                }
            });
        }

        form.on("submit(search)", function (data) {
            var field = data.field;
            $.ajax({
                url: "${pageContext.request.contextPath}/reader/searchReader",
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
                                    loadReaderBySearch(obj.curr, field.searchType, field.searchValue);
                                }
                            });
                        } else {
                            layer.msg("没有数据！", {icon: 5});
                        }
                    } else if (message === "error") {

                    }
                }
            });
            return false;
        });
    });
    </c:if>
</script>
</body>
</html>
