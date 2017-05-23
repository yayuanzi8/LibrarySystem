<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>管理员界面</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/layui/css/layui.css" media="all">
</head>
<body>
<ul class="layui-nav layui-nav-tree" lay-filter="demo">
    <li class="layui-nav-item layui-nav-itemed">
        <a href="javascript:;">借阅管理</a>
        <dl class="layui-nav-child">
            <dd><a href="${pageContext.request.contextPath}/book/overTimeBooks">超期未还图书</a></dd>
            <dd><a href="javascript:;">续借中的图书</a></dd>
        </dl>
    </li>
    <li class="layui-nav-item">
        <a href="javascript:;">用户管理</a>
    </li>
    <li class="layui-nav-item layui-nav-itemed">
        <a href="">图书管理</a>
        <dl class="layui-nav-child">
            <dd><a href="javascript:;">查看书籍信息</a></dd>
            <dd><a href="javascript:;">图书馆书籍入库</a></dd>
        </dl>
    </li>
</ul>
<script src="http://apps.bdimg.com/libs/jquery/2.1.4/jquery.min.js"></script>
<script src="${pageContext.request.contextPath}/static/layui/layui.js"></script>
<script>
    layui.use('element', function(){
        var element = layui.element(); //导航的hover效果、二级菜单等功能，需要依赖element模块

        //监听导航点击
        element.on('nav(demo)', function(elem){
            //console.log(elem)
            layer.msg(elem.text());
        });
    });
</script>
</body>
</html>
