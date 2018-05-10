<%--
  Created by IntelliJ IDEA.
  User: 王海奇
  Date: 2018/5/2
  Time: 16:13
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://"
            + request.getServerName() + ":" + request.getServerPort()
            + path + "/";
%>

<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">

<head>
    <meta charset="utf-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <!-- Bootstrap Styles-->
    <link href="<%=request.getContextPath()%>/assets/css/bootstrap.css" rel="stylesheet" />
    <!-- FontAwesome Styles-->
    <link href="<%=request.getContextPath()%>/assets/css/font-awesome.css" rel="stylesheet" />
    <!-- Morris Chart Styles-->
    <link href="<%=request.getContextPath()%>/assets/js/morris/morris-0.4.3.min.css" rel="stylesheet" />
    <!-- Custom Styles-->
    <link href="<%=request.getContextPath()%>/assets/css/custom-styles.css" rel="stylesheet" />
    <!-- Google Fonts-->
    <link href="<%=request.getContextPath()%>/css/course.css" rel="stylesheet" />

    <link href='https://fonts.googleapis.com/css?family=Open+Sans' rel='stylesheet' type='text/css' />
    <title>学优优FM后台管理系统</title>
    <script type="text/javascript" src="<%=request.getContextPath()%>/js/jquery-1.11.1.min.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath()%>/js/Management/typeCourse.js"></script>
    <base href="<%=basePath%>" />
</head>

<body>
<div id="wrapper">
    <nav class="navbar navbar-default top-navbar" role="navigation">
        <div class="navbar-header">
            <button type="button" class="navbar-toggle" data-toggle="collapse" data-target=".sidebar-collapse">
                <span class="sr-only">Toggle navigation</span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
            </button>
            <a class="navbar-brand" href="/Management/Courses">学优优FM后台</a>
        </div>

        <ul class="nav navbar-top-links navbar-right">

            <!-- /.dropdown -->
            <li class="dropdown">
                <a href="/Management/logout">退出登录</a>
                <!-- /.dropdown-user -->
            </li>
            <!-- /.dropdown -->
        </ul>
    </nav>
    <!--/. NAV TOP  -->
    <nav class="navbar-default navbar-side" role="navigation">
        <div class="sidebar-collapse">
            <ul class="nav" id="main-menu">

                <li>
                    <a href="/Management/Courses">课程管理</a>
                </li>
                <li>
                    <a href="#">分类管理</a>
                    <ul class="nav nav-second-level">
                        <li>
                            <a href="javascript:void(0)">添加分类</a>
                        </li>
                        <li>
                            <a href="/Management/type/icon">图标指定</a>
                        </li>
                    </ul>
                </li>
            </ul>

        </div>

    </nav>
    <!-- /. NAV SIDE  -->
    <div id="page-wrapper">
        <div id="page-inner">
            <div><p class="lead">添加分类</p></div>
            <div style="margin-bottom:20px;">
            <label for="typeName">请输入分类名称</label>
            <input id="typeName" class="form-control" type="text" placeholder="分类名称" maxlength="40" />
            <label for="l1select">请选择分类级别</label>
            <select id="l1select" class="form-control" onchange="changeLevel()">
                <option value="1">一级</option>
                <option value="2">二级</option>
            </select>
            <select id="l2select" class="form-control hidden" onclick="selectL1Level()">
                <option value="0" >请选择对应的一级分类</option>
                <c:forEach items="${L1Types}" var="type">
                    <option value="${type.getId()}">${type.getTypeName()}</option>
                </c:forEach>
            </select>
                <button onclick="addType()" style="margin-top:20px;float:right;" class="btn btn-success">添加分类</button>
            </div>

            <p class="lead">编辑分类</p>
            <div style="margin-top:40px;">
            <table id="deleteTypes">
                <tr>
                    <td><p class="text-center">一级分类</p></td>
                    <td style="width:100px;">&nbsp;</td>
                    <td><p class="text-center">二级分类</p></td>
                    <td>&nbsp;</td>
                </tr>
                <tbody>
                <tr>
                    <td>
                        <select multiple="multiple" id="L1List" class="form-control" style="width:200px;" onchange="getTypes(this)" size="6" name="Level1">
                            <c:forEach items="${L1Types}" var="type">
                                <option value="${type.getId()}">${type.getTypeName()}</option>
                            </c:forEach>
                        </select>
                    </td>
                    <td style="width:200px;text-align:left;">
                        <button id="l1up" class="btn btn-success" onclick="order(this)">↑</button>
                        <button id="l1down" class="btn btn-warning" onclick="order(this)">↓</button>
                    </td>
                    <td>
                        <select multiple="multiple" id="L2List" style="width:200px;" class="form-control" size="6" name="Level2">
                            <c:forEach items="${L2Types}" var="type">
                                <option value="${type.getId()}">${type.getTypeName()}</option>
                            </c:forEach>
                        </select>
                    </td>
                    <td style="width:200px;text-align:left;" >
                        <button id="l2up" class="btn btn-success" onclick="order(this)">↑</button>
                        <button id="l2down" class="btn btn-warning" onclick="order(this)">↓</button>
                    </td>
                </tr>
                <tr>
                    <td><input type="text" class="form-control hidden" id="editt1text" placeholder="输入新分类名" style="margin-top:10px;width:200px;" /></td>
                    <td>&nbsp;</td>
                    <td><input type="text" class="form-control hidden" id="editt2text" placeholder="输入新分类名" style="margin-top:10px;width:200px;" /></td>
                    <td>&nbsp;</td>
                </tr>
                <tr>
                    <td>
                        <button id="edit1" onclick="edit(this)" style="margin-top:20px;" class="btn btn-warning">修改分类名</button>
                        <button id="d1" onclick="delType(this)" style="margin-top:20px;" class="btn btn-danger">删除一级分类</button>
                    </td>
                    <td style="width:100px;">&nbsp;</td>
                    <td>
                        <button id="edit2" onclick="edit(this)" style="margin-top:20px;" class="btn btn-warning">修改分类名</button>
                        <button id="d2" onclick="delType(this)" style="margin-top:20px;" class="btn btn-danger">删除二级分类</button>
                    </td>
                    <td>&nbsp;</td>
                </tr>
                </tbody>
            </table>
            </div>
        </div>
        <!-- /. PAGE INNER  -->
        <footer><p>Copyright &copy; 2018.xueyouyouFM All rights reserved.</p></footer>
    </div>
    <!-- /. PAGE WRAPPER  -->
</div>
<!-- /. WRAPPER  -->
<!-- JS Scripts-->
<!-- jQuery Js -->
<script src="<%=request.getContextPath()%>/assets/js/jquery-1.10.2.js"></script>
<!-- Bootstrap Js -->
<script src="<%=request.getContextPath()%>/assets/js/bootstrap.min.js"></script>
<!-- Metis Menu Js -->
<script src="<%=request.getContextPath()%>/assets/js/jquery.metisMenu.js"></script>
<!-- Morris Chart Js -->
<script src="<%=request.getContextPath()%>/assets/js/morris/raphael-2.1.0.min.js"></script>
<script src="<%=request.getContextPath()%>/assets/js/morris/morris.js"></script>
<!-- Custom Js -->
<script src="<%=request.getContextPath()%>/assets/js/custom-scripts.js"></script>


</body>

</html>
