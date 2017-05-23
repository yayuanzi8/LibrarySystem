<%@ taglib prefix="sst" uri="http://www.springframework.org/security/tags" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>用户登陆</title>
    <style type="text/css">
        #container {
            margin: 100px auto;
            width: 35%;
            text-align: center;
        }
    </style>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/layui/css/layui.css" media="all">
</head>
<body>
<div id="container">
    <fieldset class="layui-elem-field layui-field-title" style="margin-top: 20px;">
        <legend>读者登陆</legend>
    </fieldset>
    <form class="layui-form" action="">
        <div class="layui-form-item">
            <label class="layui-form-label">证件号</label>
            <div class="layui-input-inline">
                <input type="text" name="cred_num" lay-verify="title" autocomplete="off" placeholder="请输入证件号"
                       class="layui-input">
            </div>
            <div class="layui-form-mid layui-word-aux">请务必填写证件号</div>
        </div>
        <div class="layui-form-item">
            <label class="layui-form-label">密码</label>
            <div class="layui-input-inline">
                <input type="password" name="password" placeholder="请输入密码" lay-verify="password" autocomplete="off"
                       class="layui-input">
            </div>
            <div class="layui-form-mid layui-word-aux">请务必填写密码</div>
        </div>
        <div class="layui-form-item">
            <label class="layui-form-label">验证码</label>
            <div class="layui-input-inline">
                <input type="text" name="captcha" lay-verify="captcha" placeholder="请输入验证码" autocomplete="off"
                       class="layui-input">
            </div>
            <div style="text-align: left" class="layui-form-bottom layui-word-aux"><img
                    src="${pageContext.request.contextPath}/user/captcha.jpg"></div>
        </div>
        <div class="layui-form-item">
            <label class="layui-form-label">记住我</label>
            <div class="layui-input-block" style="text-align: left">
                <input type="checkbox" name="remember-me" lay-skin="switch" lay-text="ON|OFF">
            </div>
        </div>
        <div class="layui-form-item" style="text-align: left">
            <div class="layui-input-block">
                <button class="layui-btn" lay-submit="" lay-filter="loginBtn">登录</button>
                <button type="reset" class="layui-btn layui-btn-primary">重置</button>
            </div>
        </div>
    </form>
</div>
<script src="http://apps.bdimg.com/libs/jquery/2.1.4/jquery.min.js"></script>
<script src="${pageContext.request.contextPath}/static/layui/layui.js"></script>
<script type="text/javascript">
    layui.use(['form', 'layedit', 'laydate'], function () {
        var form = layui.form(), layer = layui.layer;

        //自定义验证规则
        form.verify({
            title: function (value) {
                if (value.length < 5) {
                    return '证件号必须为6位';
                }
            },
            password: function (value) {
                if (value.length === 0) {
                    return "密码不能为空";
                }
            },
            captcha: function (value) {
                if (value.length !== 4) {
                    return '验证码必须是4位';
                }
            }
        });
        //监听指定开关
        form.on('switch(remember-me)', function (data) {
            layer.tips('记住我一周', data.othis)
        });

        //监听提交
        form.on('submit(loginBtn)', function (data) {
            var dataJson = data.field;
            var postData = "cred_num=" + dataJson.cred_num + "&password=" + dataJson.password + "&captcha=" + dataJson.captcha;
            var rememberMe = dataJson["remember-me"];
            if (rememberMe) {
                postData += "&remember-me=" + rememberMe;
            }
            $.ajax({
                url: "${pageContext.request.contextPath}/user/logon",
                data: postData,
                method: "post",
                dataType: "json",
                processData: false,
                success: function (response) {
                    var message = response.message;
                    if (message === "ok") {
                        window.location.href = "${pageContext.request.contextPath}/reader/" + dataJson.cred_num;
                    } else if (message === "error") {
                        layer.msg(response.details, {icon: 5});
                    }
                },
                error: function () {
                    layer.open({
                        title: '错误',
                        content: '出现未知错误！'
                    });
                }
            });
            return false;
        });
    });
</script>
</body>
</html>
