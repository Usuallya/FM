<%--
  Created by IntelliJ IDEA.
  User: 王海奇
  Date: 2018/4/21
  Time: 16:48
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>登陆</title>
</head>
<body>
<form name="login" action="/Management/login" method="post" >
    管理员用户名：<input name="username" type="text" placeholder="username：" />
    管理员密码：<input name="password" type="password" placeholder="password:" />
    <input type="submit" value="登陆" />
</form>
</body>
</html>
