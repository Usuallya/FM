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
    <link href="<%=request.getContextPath()%>/css/course.css" rel="stylesheet" />
    <!-- Google Fonts-->
    <link href='https://fonts.googleapis.com/css?family=Open+Sans' rel='stylesheet' type='text/css' />
    <link href="<%=request.getContextPath()%>/css/searchableSelect.css" rel="stylesheet" type="text/css">
    <script type="text/javascript" src="<%=request.getContextPath()%>/js/searchableSelect.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath()%>/js/jquery-1.11.1.min.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath()%>/js/Management/typeCourse.js"></script>
    <title>学优优FM后台管理系统</title>
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
f
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
                    <a href="javascript:void(0)">课程管理</a>
                </li>
                <li>
                    <a href="#"> 分类管理</a>
                    <ul class="nav nav-second-level">
                        <li>
                            <a href="/Management/type/addTypePage">添加分类</a>
                        </li>
                        <li>
                            <a href="/Management/type/icon">图标指定</a>
                        </li>
                        <li>
                            <a href="/Management/getInitTypesAndCourses">顺序管理</a>
                        </li>
                    </ul>
                </li>
            </ul>

        </div>

    </nav>
    <!-- /. NAV SIDE  -->
    <div id="page-wrapper">
        <div id="page-inner">
            <div><p class="lead">上传文件</p></div>
            <div>
                <form action="/Management/courseUpload" method="post"  enctype="multipart/form-data">
                        <input type="file" id="file" name="course" accept="audio/*" multiple="multiple" /> <br />
                        <input type="submit" class="btn btn-success" onclick="return validate();" value="上传" />
                        <p>${tips}</p>
                </form>
            </div>
            <div><p class="lead">音频文件指定</p></div>
            <label for="L1List">请选择要指定的分类</label>
        <div>
            <div id="select1" style="float:left;">
                <select id="L1List" onchange="getTypes(this)" class="form-control">
                    <c:forEach items="${L1Types}" var="type">
                        <option value="${type.getId()}">${type.getTypeName()}</option>
                    </c:forEach>
                </select>
            </div>
            <div id="select2" style="float:left;">
                <select id="L2List" onchange="getCourse(this)" class="form-control">
                    <c:forEach items="${L2Types}" var="type">
                        <option value="${type.getId()}">${type.getTypeName()}</option>
                    </c:forEach>
                </select>
            </div>
        </div>
            <div>&nbsp;</div>
            <div>&nbsp;</div>
            <label for="NoTypeList">无分类音频文件列表</label>
            <div class="select" style="margin-bottom:20px;">
                <select multiple="multiple" id="NoTypeList" class="form-control" style="width:200px;" size="6">
                    <c:if test="${noTypeCourse.size()==0}">
                        <option value="0">无</option>
                    </c:if>
                    <c:forEach items="${noTypeCourse}" var="course">
                        <option value="${course.getId()}">${course.getCourseName()}</option>
                    </c:forEach>
                </select>
            </div>
            <button onclick="add2Type()" class="btn btn-success">加入到选定分类</button>
            <button onclick="deleteCourse()" class="btn btn-danger">删除课程</button>
        </div>
    </div>
</div>

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