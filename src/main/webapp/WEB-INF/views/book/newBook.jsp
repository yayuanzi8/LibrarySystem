<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>添加新书籍</title>
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
<div style="width: 80%;margin: 20px auto">
    <fieldset class="layui-elem-field layui-field-title" style="margin-top: 20px;">
        <legend>添加新书籍</legend>
    </fieldset>
    <form class="layui-form" method="post">
        <div class="layui-form-item">
            <div class="layui-inline">
                <label class="layui-form-label">书名</label>
                <div class="layui-input-inline">
                    <input type="text" name="bookName" lay-verify="required" autocomplete="off" placeholder="请输入书名"
                           class="layui-input">
                </div>
            </div>
            <div class="layui-inline">
                <label class="layui-form-label">副标题</label>
                <div class="layui-input-inline">
                    <input type="text" name="subHead" autocomplete="off" placeholder="请输入副标题(没有可不写)"
                           class="layui-input">
                </div>
            </div>
            <div class="layui-inline">
                <label class="layui-form-label">ISBN</label>
                <div class="layui-input-inline">
                    <input type="text" name="ISBN" lay-verify="required" placeholder="请输入ISBN号"
                           autocomplete="off" class="layui-input">
                </div>
            </div>
        </div>

        <div class="layui-form-item">
            <div class="layui-inline">
                <label class="layui-form-label">作者</label>
                <div class="layui-input-inline">
                    <input type="text" name="author" placeholder="请输入作者" lay-verify="required" autocomplete="off"
                           class="layui-input">
                </div>
            </div>
            <div class="layui-inline">
                <label class="layui-form-label">出版社</label>
                <div class="layui-input-inline">
                    <input type="text" name="press" lay-verify="required" placeholder="请输入出版社"
                           autocomplete="off" class="layui-input">
                </div>
            </div>
            <div class="layui-inline">
                <label class="layui-form-label">出版年份</label>
                <div class="layui-input-inline">
                    <input type="text" name="pressYear" lay-verify="required|number" autocomplete="off" placeholder="请输入出版年份"
                           class="layui-input">
                </div>
            </div>
        </div>
        <div class="layui-form-item layui-form-text">
            <label class="layui-form-label">作者简介</label>
            <div class="layui-input-block">
                <textarea placeholder="请输入内容" lay-verify="required" name="authorDesc" class="layui-textarea"></textarea>
            </div>
        </div>
        <div class="layui-form-item">
            <div class="layui-inline">
                <label class="layui-form-label">翻译</label>
                <div class="layui-input-inline">
                    <input type="text" name="translator" placeholder="请输入译者(没有就不填)"
                           autocomplete="off" class="layui-input">
                </div>
            </div>
            <div class="layui-inline">
                <label class="layui-form-label">页数</label>
                <div class="layui-input-inline">
                    <input type="text" name="pageNum" lay-verify="required|number" placeholder="本书的页数"
                           autocomplete="off" class="layui-input">
                </div>
            </div>
        </div>

        <div class="layui-form-item">
            <div class="layui-inline">
                <label class="layui-form-label">索书号</label>
                <div class="layui-input-inline">
                    <select name="cnum" lay-verify="required">
                        <option value="A" selected="selected">A.马列主义、毛泽东思想、邓小平理论</option>
                        <option value="B">B.哲学、宗教</option>
                        <option value="C">C.社会科学总论</option>
                        <option value="D">D.政治、法律</option>
                        <option value="E">E.军事</option>
                        <option value="F">F.经济</option>
                        <option value="G">G.文化、科学、教育、体育</option>
                        <option value="H">H.语言、文字</option>
                        <option value="I">I.文学</option>
                        <option value="J">J.艺术</option>
                        <option value="K">K.历史、地理</option>
                        <option value="N">N.自然科学总论</option>
                        <option value="O">O.数理科学文化</option>
                        <option value="P">P.天文学、地球科学</option>
                        <option value="Q">Q.生物科学</option>
                        <option value="R">R.医药、卫生</option>
                        <option value="S">S.农业科学</option>
                        <option value="T">T.工业技术</option>
                        <option value="U">U.交通运输</option>
                        <option value="V">V.航空、航天</option>
                        <option value="X">X.环境科学、安全科学</option>
                        <option value="Z">Z.综合性图书</option>
                    </select>
                </div>
                <div class="layui-input-inline">
                    <input type="text" name="bookNO" lay-verify="required" placeholder="输入分类号" autocomplete="off"
                           class="layui-input">
                </div>
            </div>
        </div>

        <div class="layui-form-item">
            <div class="layui-inline">
                <label class="layui-form-label">图书类型</label>
                <div class="layui-input-inline">
                    <select name="bookType" lay-verify="required">
                        <option value="中文图书" selected="selected">中文图书</option>
                        <option value="西方图书">西方图书</option>
                        <option value="中文期刊">中文期刊</option>
                        <option value="西方期刊">西方期刊</option>
                    </select>
                </div>
            </div>
            <div class="layui-inline">
                <label class="layui-form-label">馆藏地</label>
                <div class="layui-input-inline">
                    <select name="storeAddress" lay-verify="required">
                        <option value="白云校区图书馆" selected="selected">白云校区图书馆</option>
                        <option value="海珠校区图书馆">海珠校区图书馆</option>
                    </select>
                </div>
            </div>
        </div>

        <div class="layui-form-item">
            <div class="layui-inline">
                <label class="layui-form-label">单价</label>
                <div class="layui-input-inline" style="width: 100px;">
                    <input type="text" name="price" l placeholder="￥" autocomplete="off" class="layui-input">
                </div>
            </div>
        </div>

        <div class="layui-form-item">
            <div class="layui-inline">
                <label class="layui-form-label">书籍图片</label>
                <div style="display: inline-block" class="site-demo-upload">
                    <img style="width: 250px;height: 200px;" id="LAY_demo_upload"
                         src="${pageContext.request.contextPath}/static/images/library.jpg">
                    <div style="text-align: center" class="site-demo-upbar">
                        <input type="file" name="file" class="layui-upload-file" id="test">
                    </div>
                </div>
            </div>
        </div>

        <input value="${pageContext.request.contextPath}/static/images/library.jpg" type="hidden" name="bookImage"
               id="bookImageHidden"/>

        <div class="layui-form-item layui-form-text">
            <label class="layui-form-label">书籍简介</label>
            <div class="layui-input-block">
                <textarea placeholder="请输入内容" lay-verify="required" name="bookDesc" class="layui-textarea"></textarea>
            </div>
        </div>
        <div class="layui-form-item layui-form-text">
            <label class="layui-form-label">短目录</label>
            <div class="layui-input-block">
                <textarea placeholder="请输入内容" name="shortCatalog" class="layui-textarea"></textarea>
            </div>
        </div>
        <div class="layui-form-item layui-form-text">
            <label class="layui-form-label">长目录</label>
            <div class="layui-input-block">
                <textarea placeholder="请输入内容" name="longCatalog" class="layui-textarea"></textarea>
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
    layui.use(['element', 'form', 'layedit', 'laydate', 'upload'], function () {
        var element = layui.element(); //导航的hover效果、二级菜单等功能，需要依赖element模块
        var form = layui.form()
            , layer = layui.layer
            , layedit = layui.layedit
            , laydate = layui.laydate;

        //创建一个编辑器
        var editIndex = layedit.build('LAY_demo_editor');
        //监听导航点击
        element.on('nav(demo)', function (elem) {
            //console.log(elem)
            layer.msg(elem.text());
        });

        form.on('submit(demo1)', function (data) {
            var field = data.field;
            $.ajax({
                url: "${pageContext.request.contextPath}/book/addNewBook",
                data: "bookName=" + field.bookName +
                "&author=" + field.author + "&press=" +
                field.press + "&translator=" + field.translator +
                "&pageNum=" + field.pageNum + "&cnum=" + field.cnum +
                "&bookNO=" + field.bookNO + "&bookType=" + field.bookType +
                "&storeAddress=" + field.storeAddress + "&price=" +
                field.price + "&bookImage=" + field.bookImage +
                "&bookDesc=" + field.bookDesc + "&ISBN=" + field.ISBN +
                "&subHead=" + field.subHead + "&authorDesc=" + field.authorDesc +
                "&pressYear=" + field.pressYear + "&shortCatalog=" + field.shortCatalog + "&longCatalog=" + field.longCatalog,
                method: "post",
                success: function (response) {
                    var message = response.message;
                    if (message === "error") {
                        layer.open({
                            title: "错误",
                            content: "添加书籍出错！请稍后再试！",
                            type: 0,
                            offset: '100px'
                        });
                    } else if (message === "ok") {
                        layer.open({
                            title: "信息",
                            content: "添加书籍成功！",
                            type: 0,
                            offset: '100px',
                        });
                    } else if (message === "uniqueBookNO") {
                        layer.open({
                            title: "错误",
                            content: "索书号必须唯一！",
                            type: 0,
                            offset: '100px'
                        });
                    }
                }
            });
            return false;
        });

        layui.upload({
            url: '/receiveImage',
            elem: '#test', //指定原始元素，默认直接查找class="layui-upload-file"
            method: 'post', //上传接口的http类型,
            success: function (res) {
                console.log(res);
                LAY_demo_upload.src = res.url;
                $("#bookImageHidden").val(res.url);
            }
        });
    });
</script>
</body>
</html>
