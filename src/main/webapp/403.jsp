<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>403访问被拒绝</title>
    <style type="text/css">
        body {
            color: #fff;
            font: 100% "Lato", sans-serif;
            font-size: 1.8rem;
            font-weight: 300;
            background: -webkit-linear-gradient(left top, #29b72d, #fa110e); /* Safari 5.1 - 6.0 */
            background: -o-linear-gradient(bottom right, #29b72d, #fa110e); /* Opera 11.1 - 12.0 */
            background: -moz-linear-gradient(bottom right, #29b72d, #fa110e); /* Firefox 3.6 - 15 */
            background: linear-gradient(to bottom right, #29b72d, #fa110e); /* 标准的语法 */
        }

        a {
            color: #75C6D9;
            text-decoration: none;
        }

        h3 {
            margin-bottom: 1%;
        }

        ul {
            list-style: none;
            margin: 0;
            padding: 0;
            line-height: 50px;
        }

        li a:hover {
            color: #fff;
        }

        .center {
            text-align: center;
        }

        /* Search Bar Styling */
        form > * {
            vertical-align: middle;
        }

        /* 404 Styling */
        .header {
            font-size: 13rem;
            font-weight: 700;
            margin: 2% 0 2% 0;
            text-shadow: 0px 3px 0px #7f8c8d;
        }

        /* Error Styling */
        .error {
            margin: -70px 0 2% 0;
            font-size: 7.4rem;
            text-shadow: 0px 3px 0px #7f8c8d;
            font-weight: 100;
        }
    </style>
</head>
<body>
<section class="center">
    <article>
        <h1 class="header">
            403</h1>
        <p class="error" style="font-size: 32px">
            您没有权限访问此页面！</p>
    </article>
    <article>
        <ul>
            <li><a href="${pageContext.request.contextPath}/">回到主页</a></li>
        </ul>
    </article>
</section>
</body>
</html>
