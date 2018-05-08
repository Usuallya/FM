<%--
  Created by IntelliJ IDEA.
  User: 王海奇
  Date: 2018/4/21
  Time: 16:48
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page isELIgnored="false" %>
<html>
<head>
    <title>学优优FM后台管理系统</title>
    <link rel='stylesheet prefetch' href='http://netdna.bootstrapcdn.com/bootstrap/3.0.2/css/bootstrap.min.css'>
    <link rel="stylesheet" href="<%=request.getContextPath()%>/css/login.css" type="text/css" />
</head>
<body>


<div class="wrapper">
    <form class="form-signin" name="login" action="<%=request.getContextPath()%>/Management/login" method="post">
        <h2 class="form-signin-heading">管理员登录</h2>
        <p>${loginFail}</p>
        <input type="text" class="form-control" name="username" placeholder="用户名：" required="" autofocus="" />
        <input type="password" class="form-control" name="password" placeholder="密码：" required=""/>
        <button class="btn btn-lg btn-primary btn-block" type="submit">登录</button>
    </form>
</div>

</body>
</html>
