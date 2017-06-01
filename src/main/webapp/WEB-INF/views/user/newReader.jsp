<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>录入读者</title>
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

<div style="width: 40%;margin: 20px auto">
    <fieldset class="layui-elem-field layui-field-title" style="margin-top: 20px;">
        <legend>添加读者</legend>
    </fieldset>
    <form class="layui-form" method="post">
        <div class="layui-form-item">
            <label class="layui-form-label">姓名</label>
            <div class="layui-input-inline">
                <input type="text" style="width: 100%" name="stu_name" lay-verify="required" autocomplete="off"
                       placeholder="请输入读者姓名"
                       class="layui-input">
            </div>
        </div>
        <div class="layui-form-item">
            <label class="layui-form-label">邮箱</label>
            <div class="layui-input-inline">
                <input type="text" style="width: 100%" name="email" autocomplete="off" placeholder="输入邮箱(没有可以不输)"
                       class="layui-input">
            </div>
        </div>
        <div class="layui-form-item">
            <div class="layui-inline">
                <label class="layui-form-label">读者类型</label>
                <div class="layui-input-inline">
                    <select name="readerType">
                        <option value="学生" selected="selected">学生</option>
                        <option value="教师">教师</option>
                    </select>
                </div>
            </div>
        </div>
        <div class="layui-form-item">
            <div class="layui-input-block">
                <button class="layui-btn" lay-submit="" lay-filter="demo1">立即提交</button>
                <button type="reset" class="layui-btn layui-btn-primary">重置</button>
            </div>
        </div>
    </form>
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

    layui.use(["form"], function () {
        var form = layui.form()
            , layer = layui.layer;
        form.on('submit(demo1)', function (data) {
            var field = data.field;
            $.ajax({
                url: "${pageContext.request.contextPath}/reader/addNewReader",
                data: "name=" + field.stu_name + "&email=" + field.email + "&readerType=" + field.readerType,
                method: "post",
                success: function (response) {
                    var msg = response.message;
                    if (msg === "ok"){
                        layer.open({
                            title:"添加成功！",
                            content:"添加成功！",
                            offset:"200px",
                            yes:function () {
                                window.location = window.location;
                            },
                            cancel:function () {
                                window.location = window.location;
                            }
                        });
                    }else if(msg === "error"){
                        layer.msg("添加失败！请稍后再试！",{icon:5,offset:"200px"});
                    }
                }
            });
            return false;
        });
    });
</script>
</body>
</html>
