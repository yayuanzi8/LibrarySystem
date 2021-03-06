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
<div style="background: url('${pageContext.request.contextPath}/static/images/library.jpg');height: 40%"></div>
<ul class="layui-nav">
    <li class="layui-nav-item layui-this"><a href="${pageContext.request.contextPath}/reader/${reader.credNum}">证件信息</a>
    </li>
    <li class="layui-nav-item"><a href="${pageContext.request.contextPath}/reader/history/${reader.credNum}">借阅历史</a>
    </li>
    <li class="layui-nav-item"><a
            href="${pageContext.request.contextPath}/reader/currentBorrow/${reader.credNum}">当前借阅</a>
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
            <td class="layui-bg-blue left">失效日期</td>
            <fmt:formatDate value="${reader.endTime}" scope="page" pattern="yyyy-MM-dd" var="endTime"/>
            <td class="right">${endTime}</td>
        </tr>
        <tr>
            <td class="layui-bg-blue left" style="border-radius: 0 0 0 10px;">修改密码</td>
            <td class="right" style="border-radius: 0 0 10px 0;">
                <button class="layui-btn layui-btn-radius" style="background-color: #4CAF50"
                        onclick="openChangePswModal()">修改密码
                </button>
            </td>
        </tr>
    </table>
</div>

<div id="changePasswordArea" style="display: none">
    <form style="margin: 20px" id="changePasswordForm" class="layui-form" action="">
        <div class="layui-form-item">
            <label class="layui-form-label">旧密码</label>
            <div class="layui-input-inline">
                <input type="password" name="oldPassword"
                       lay-verify="required"
                       autocomplete="off" class="layui-input">
            </div>
        </div>
        <div class="layui-form-item">
            <label class="layui-form-label">新密码</label>
            <div class="layui-input-inline">
                <input type="password" name="newPassword"
                       lay-verify="required"
                       autocomplete="off" class="layui-input">
            </div>
        </div>
        <div class="layui-form-item">
            <div style="text-align: left" class="layui-input-block">
                <button class="layui-btn" lay-submit lay-filter="changePassword" style="background-color: #5FB878">修改
                </button>
                <button type="reset" class="layui-btn layui-btn-primary" style="">重置</button>
            </div>
        </div>
    </form>
</div>

<fieldset class="layui-elem-field layui-field-title" style="margin-top: 50px;">
    <legend>修改邮箱</legend>
</fieldset>
<form id="updateEmailForm" class="layui-form" action="">
    <div class="layui-form-item">
        <label class="layui-form-label">旧邮箱</label>
        <div class="layui-input-inline">
            <input type="email" name="oldEmail" value="${reader.email}" disabled required
                   lay-verify="oldEmail"
                   autocomplete="off" class="layui-input">
        </div>
    </div>
    <div class="layui-form-item">
        <label class="layui-form-label">新邮箱</label>
        <div class="layui-input-inline">
            <input type="email" name="newEmail" id="newEmail" lay-verify="newEmail" autocomplete="off"
                   class="layui-input">
        </div>
        <div class="layui-form-mid layui-word-aux">新邮箱必须填写</div>
    </div>
    <div class="layui-form-item">
        <label class="layui-form-label">验证码</label>
        <div class="layui-input-inline">
            <input type="text" name="captcha" lay-verify="captcha" autocomplete="off" class="layui-input">
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

<script src="http://apps.bdimg.com/libs/jquery/2.1.4/jquery.min.js"></script>
<script src="${pageContext.request.contextPath}/static/layui/layui.js"></script>
<script src="${pageContext.request.contextPath}/static/reader_common.js"></script>
<script type="text/javascript">
    //Demo
    var layer;

    function openChangePswModal() {
        layer.open({
            type: 1,
            skin: 'layui-layer-molv',
            area: '50%',
            offset: '200px',
            content: $('#changePasswordArea') //这里content是一个DOM，注意：最好该元素要存放在body最外层，否则可能被其它的相对元素所影响
        });
    }

    layui.use('form', function () {
        var form = layui.form();
        layer = layui.layer;

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
            $.ajax({
                url: "${pageContext.request.contextPath}/reader/updateEmail",
                method: "post",
                processData: false,
                data: "newEmail=" + data.field.newEmail + "&captcha=" + data.field.captcha,
                dataType: "json",
                success: function (response) {
                    var message = response.message;
                    if (message === "ok") {
                        window.location = window.location;
                    } else if (message === "systemError") {
                        layer.msg("系统出错！请稍后再试！", {icon: 5});
                    } else if (message === "captchaError") {
                        layer.msg("验证码错误！", {icon: 5});
                    }
                }
            });
            return false;
        });

        form.on("submit(changePassword)", function (data) {
            layer.msg(JSON.stringify(data.field), {offset: "200px"});
            $.ajax({
                url: "${pageContext.request.contextPath}/reader/changePassword",
                data: "oldPassword=" + data.field.oldPassword + "&newPassword=" + data.field.newPassword,
                method: "post",
                success: function (response) {
                    var msg = response.message;
                    if (msg === "ok") {
                        layer.msg("修改成功！下次登陆生效！", {icon: 1, offset: "200px"});
                    }else if (msg === "error"){
                        layer.msg("修改失败！请稍后再试！", {icon: 5, offset: "200px"});
                    }else if (msg === "errorOldPassword"){
                        layer.msg("旧密码错误！请重新输入！", {icon: 5, offset: "200px"});
                    }
                }
            });
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
            $getCaptchaBtn.prop("disabled", false);
        }, 60000);
        $.ajax({
            url: "${pageContext.request.contextPath}/sendEmail",
            method: "post",
            processData: false,
            dataType: "json",
            data: "email=" + newEmail,
            success: function (response) {
                var message = response.message;
                if (message === "emailPatternError") {
                    layer.msg("邮箱格式必须是X@X.X", {icon: 5});
                    $getCaptchaBtn.text("获取验证码");
                    $getCaptchaBtn.prop("disabled", false);
                } else if (message === "emailExistedError") {
                    layer.msg("邮箱已被使用！", {icon: 5});
                    $getCaptchaBtn.text("获取验证码");
                    $getCaptchaBtn.prop("disabled", false);
                }
            }
        });
    });
</script>
</body>
</html>
