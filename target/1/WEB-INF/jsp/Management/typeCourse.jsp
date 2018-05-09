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
                            <a href="/Management/type/addTypePage">添加分类</a>
                        </li>
                        <li>
                            <a href="/Management/type/icon">图标指定</a>
                        </li>
                        <li>
                            <a href="javascript:void(0)">顺序管理</a>
                        </li>
                    </ul>
                </li>
            </ul>
        </div>
    </nav>
    <!-- /. NAV SIDE  -->
    <div id="page-wrapper">
        <div id="page-inner">

            <table style="text-align:center">
                <tr>
                    <td><p class="lead">分类顺序管理</p></td>
                    <td>&nbsp;</td>
                    <td>&nbsp;</td>
                </tr>
                <tr>
                    <td>
                        <label for="L1List">一级分类顺序管理</label>
                        <select multiple="multiple" id="L1List" size="6" onchange="getTypes(this)" style="width:200px;" class="form-control" name="Level1">
                            <c:forEach items="${L1Types}" var="type">
                                <option value="${type.getId()}">${type.getTypeName()}</option>
                            </c:forEach>
                        </select>
                    </td>
                    <td>
                        <button id="l1up" class="btn btn-success" style="margin-left:20px;" onclick="order(this)">↑</button>
                        <button id="l1down" class="btn btn-warning" onclick="order(this)">↓</button>
                    </td>
                    <td style="padding-left:20px;">
                        <label for="L2List">二级分类顺序管理</label>
                        <select multiple="multiple" id="L2List" size="6" onchange="getCourse(this)" style="width:200px;" class="form-control" name="Level2">
                            <c:forEach items="${L2Types}" var="type">
                                <option value="${type.getId()}">${type.getTypeName()}</option>
                            </c:forEach>
                        </select>
                    </td>
                    <td>
                        <button id="l2up" class="btn btn-success" style="margin-left:20px;" onclick="order(this)">↑</button>
                        <button id="l2down" class="btn btn-warning" onclick="order(this)">↓</button>
                    </td>
                </tr>
                <tr>
                    <td>&nbsp;</td>
                    <td>&nbsp;</td>
                    <td>&nbsp;</td>
                    <td>&nbsp;</td>
                </tr>
                <tr>
                    <td>
                <p class="lead">课程顺序管理</p>
                    </td>
                    <td>
                        &nbsp;
                    </td>
                    <td>
                        &nbsp;
                    </td>
                    <td>
                        &nbsp;
                    </td>
                </tr>
                <tr>
                    <td>
                        <select multiple="multiple" id="L2CourseList" class="form-control" style="width:200px;" size="6" name="Level2Courses">
                    <c:forEach items="${initCourse}" var="course">
                        <option value="${course.getId()}" onchange="flagCourse(this)">${course.getCourseName()}</option>
                    </c:forEach>
                         </select>
                    </td>
                    <td>
                        <button id="c-up" class="btn btn-success" style="margin-left:20px;" onclick="order(this)">↑</button>
                        <button id="c-down" class="btn btn-warning" onclick="order(this)">↓</button>
                    </td>
                    <td>&nbsp;</td>
                    <td>&nbsp;</td>
                </tr>
                <tr>
                    <td><button id="del" class="btn btn-danger" style="margin-top:20px;" onclick="delCourse()">删除课程</button></td>
                </tr>
            </table>
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
