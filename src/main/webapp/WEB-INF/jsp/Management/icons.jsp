<%@ page import="com.FM.utils.Constants" %><%--
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
            <li class="dropdown">
                <a href="/Management/logout">退出登录</a>
            </li>
        </ul>
    </nav>
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
                            <a href="javascript:void(0)">图标指定</a>
                        </li>
                    </ul>
                </li>
            </ul>
        </div>
    </nav>
    <!-- /. NAV SIDE  -->
    <div id="page-wrapper">
        <div id="page-inner">
            <div><p class="lead">图标文件上传</p></div>
            <form action="/Management/iconUpload" method="post"  enctype="multipart/form-data">
                    <input type="file" id="file" name="icon" multiple="multiple" accept="image/*" /> <br />
                    <p>${tips}</p>
                <div style="margin-top:20px;"><p class="lead">图标文件指定</p></div>
                <label for="L1List">选择分类</label>
                        <select id="L1List" onchange="getTypes(this)" name="Level1" class="form-control">
                            <c:forEach items="${L1Types}" var="type">
                                <option value="${type.getId()}">${type.getTypeName()}</option>
                            </c:forEach>
                        </select>
                    <select id="L2List" style="margin-top:10px;" name="l2Type" onchange="chg2Icon()" class="form-control">
                        <c:forEach items="${L2Types}" var="type">
                            <option value="${type.getId()}">${type.getTypeName()}</option>
                        </c:forEach>
                    </select>
                    <br />
                    <div style="width:100px; height:120px;">
                        <label for="iconPreview">图标预览</label>
                        <c:if test="${l2fIconLocation!=null}">
                    <img id="iconPreview" class="img-responsive" src="<% String iconPath = request.getContextPath()+"/"+Constants.ICON_UPLOAD_PATH+"/"; out.println(iconPath); %>${l2fIconLocation}"/>
                        </c:if>
                        <c:if test="${l2fIconLocation==null}">
                            <div>暂未指定图标</div>
                        </c:if>
                    </div>
                    <input type="submit" onclick="return validateImage();" class="btn btn-success" value="上传" />
            </form>
        </div>
        <!-- /. PAGE INNER  -->
        <footer><p>Copyright &copy; 2018.xueyouyouFM All rights reserved.</p></footer>
    </div>
    <!-- /. PAGE WRAPPER  -->
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