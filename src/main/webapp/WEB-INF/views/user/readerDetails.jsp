<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>我的主页</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/layui/css/layui.css" media="all">
    <style type="text/css">
        #userDetailsTable {
            width: 60%;
            text-align: center;
            margin: 0 auto;
        }

        #userDetailsTable, #userDetailsTable td {
            border: none;
            padding: 20px 5px;
        }

        #userDetailsTable tr {
            border-bottom: 1px solid #eeeeee;
        }

        .right {
            color: white;
            background-color: #ff4646
        }

        .left {
            color: white;
        }

        legend {
            color: crimson;
            font-weight: bold;
        }

        #updateEmailForm {
            width: 50%;
            margin: 0 auto;
        }

    </style>
</head>
<body>
<div style="background: url('/static/images/library.jpg');height: 30%"></div>
<ul class="layui-nav">
    <li class="layui-nav-item"><a href="">证件信息</a></li>
    <li class="layui-nav-item"><a href="">借阅历史</a></li>
    <li class="layui-nav-item"><a href="">当前借阅</a>
    </li>
</ul>
<fieldset class="layui-elem-field layui-field-title" style="margin-top: 50px;">
    <legend>我的资料</legend>
</fieldset>
<div class="layui-field-box">
    <table id="userDetailsTable">
        <tr>
            <td style="border-radius: 10px 0 0 0" class="layui-bg-blue left">证件号</td>
            <td style="border-radius: 0 10px 0 0;" class="right">${reader.credNum}</td>
        </tr>
        <tr>
            <td class="layui-bg-blue left">姓名</td>
            <td class="right">${reader.name}</td>
        </tr>
        <tr>
            <td class="layui-bg-blue left">读者类型</td>
            <td class="right">${reader.readerType}</td>
        </tr>
        <tr>
            <td class="layui-bg-blue left">邮箱</td>
            <td class="right">${reader.email}</td>
        </tr>
        <tr>
            <td class="layui-bg-blue left">最大可借数目</td>
            <td class="right">${reader.maxAvailable}本</td>
        </tr>
        <tr>
            <td class="layui-bg-blue left">累计已借数目</td>
            <td class="right">${reader.cumAvailNum}本</td>
        </tr>
        <tr>
            <td class="layui-bg-blue left">办证日期</td>
            <fmt:formatDate value="${reader.startTime}" scope="page" pattern="yyyy-MM-dd" var="startTime"/>
            <td class="right">${startTime}</td>
        </tr>
        <tr>
            <td style="border-radius: 0 0 0 10px;" class="layui-bg-blue left">失效日期</td>
            <fmt:formatDate value="${reader.endTime}" scope="page" pattern="yyyy-MM-dd" var="endTime"/>
            <td style="border-radius: 0 0 10px 0" class="right">${endTime}</td>
        </tr>
    </table>
</div>

<fieldset class="layui-elem-field layui-field-title" style="margin-top: 50px;">
    <legend>修改邮箱</legend>
</fieldset>
<div class="layui-field-box">
    <form id="updateEmailForm" class="layui-form" action="">
        <div class="layui-form-item">
            <label class="layui-form-label">旧邮箱</label>
            <div class="layui-input-inline">
                <input type="email" name="oldEmail" value="${reader.email}" disabled required
                       lay-verify="required oldEmail"
                       autocomplete="off" class="layui-input">
            </div>
        </div>
        <div class="layui-form-item">
            <label class="layui-form-label">新邮箱</label>
            <div class="layui-input-inline">
                <input type="email" name="newEmail" id="newEmail" lay-verify="required|newEmail" autocomplete="off"
                       class="layui-input">
            </div>
            <div class="layui-form-mid layui-word-aux">新邮箱必须填写</div>
        </div>
        <div class="layui-form-item">
            <label class="layui-form-label">验证码</label>
            <div class="layui-input-inline">
                <input type="text" name="captcha" lay-verify="required|captcha" autocomplete="off" class="layui-input">
            </div>
            <div class="layui-word-aux">
                <button type="button" id="getCaptcha" class="layui-btn layui-btn-danger">获取验证码</button>
            </div>
        </div>
        <div class="layui-form-item">
            <div style="text-align: left" class="layui-input-block">
                <button class="layui-btn" lay-submit lay-filter="update" style="background-color: #5FB878">修改</button>
                <button type="reset" class="layui-btn layui-btn-primary" style="">重置</button>
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
</script>
<script>
    //Demo
    layui.use('form', function () {
        var form = layui.form();

        form.verify({
            oldEmail: [/\w+@\w+\.\w+/, "邮箱格式必须是X@X.X"],
            newEmail: [/\w+@\w+\.\w+/, "邮箱格式必须是X@X.X"],
            captcha: function (value) {
                if (value.trim().length !== 4) {
                    return "验证码必须为4位";
                }
            }
        });

        //监听提交
        form.on('submit(update)', function (data) {
            layer.msg(JSON.stringify(data.field));
            return false;
        });
    });
    $("#getCaptcha").click(function () {
        var newEmail = $("#newEmail").val();
        if (newEmail.trim().length === 0) {
            layer.msg("邮箱不能为空", {icon: 5});
            return false;
        }
        if (!newEmail.match(/\w+@\w+\.\w+/)) {
            layer.msg("邮箱格式必须是X@X.X", {icon: 5});
            return false;
        }
        var $getCaptchaBtn = $("#getCaptcha");
        $getCaptchaBtn.text("已发送验证码！60秒后可重试");
        $getCaptchaBtn.prop("disabled", "disabled");
        setTimeout(function () {
            $getCaptchaBtn.text("获取验证码");
            $getCaptchaBtn.removeProp("disabled", "disabled");
        }, 60000);
        $.ajax({
            url: "${pageContext.request.contextPath}/sendEmail",
            method: "post",
            processData: false,
            dataType: "json",
            data: "email=" + newEmail,
            success: function (response) {

            }
            ,
            error: function () {

            }
        });
    });
</script>
</body>
</html>
