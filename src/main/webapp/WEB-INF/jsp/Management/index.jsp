<%--
  Created by IntelliJ IDEA.
  User: 王海奇
  Date: 2018/4/21
  Time: 20:52
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
<html>
<head>
    <title>管理系统首页</title>
    <script type="text/javascript" src="<%=request.getContextPath()%>/js/jquery-1.8.3.min.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath()%>/js/Management/index.js"></script>
    <link type="text/css" rel="styleSheet"  href="<%=request.getContextPath()%>/css/managerindex.css" />
    <base href="<%=basePath%>" />
</head>
<body>
<div id="login" >
    欢迎您,${user.userName}
    <a href="/Management/logout">退出登录</a>
</div>

<form action="/Management/courseUpload" method="post"  enctype="multipart/form-data">
    <fieldset>
        <legend>课程音频文件上传</legend>
        <input type="file" name="course" multiple="multiple" /> <br />
        <input type="submit" value="上传" />
        <p>${tips}</p>
    </fieldset>
</form>
<form action="/Management/iconUpload" method="post"  enctype="multipart/form-data">
    <fieldset>
        <legend>二级分类图标上传</legend>
        <input type="file" name="Icon" multiple="multiple" /> <br />
        <input type="submit" value="上传" />
    </fieldset>
</form>

<div id="opration" style="height:200px;">
    <div style="border:5px black;float:left;width:70%;">
        <p>分类操作</p>
        <p>
            <button>添加分类</button>
            <button>删除分类</button>
        </p>
        <p>
            <button>向上调整顺序</button>
            <button>向下调整顺序</button>
        </p>
    </div>
    <div style="border:5px black;float:left;width:30%;">
        <p>文件操作</p>
        <button>向上调整顺序</button>
        <button>向下调整顺序</button>
        <button>添加到选定分类</button>
    </div>
</div>
<div id="select">
    <div id="left">
        <div id="select1" class="select">
            <p>一级分类</p>
            <select multiple="multiple" id="L1List" onchange="getTypes(this)" size="6" name="Level1">
                <c:forEach items="${L1Types}" var="type">
                    <option value="${type.getId()}">${type.getTypeName()}</option>
                </c:forEach>
            </select>
        </div>
        <div id="select2" class="select">
            <p>二级分类</p>
            <select multiple="multiple" id="L2List" onchange="getCourse(this)" size="6" name="Level2">
                <c:forEach items="${L2Types}" var="type">
                    <option value="${type.getId()}">${type.getTypeName()}</option>
                </c:forEach>
            </select>
            <select multiple="multiple" id="L2CourseList" size="6" name="Level2Courses">
                <c:forEach items="${initCourse}" var="course">
                    <option value="${course.getId()}" onchange="flagCourse(this)">${course.getCourseName()}</option>
                </c:forEach>
            </select>
        </div>
    </div>

    <div id="middle">
        <div id="icons">
            <p>图标展示区域</p>
        </div>
    </div>
    <div id="right">
        <div class="select">
            <p>未分类音频文件列表</p>
            <select multiple="multiple" id="NoTypeList" onchange="flagNCourse(this)" size="6" name="coursefile">
                <c:forEach items="${noTypeCourse}" var="course">
                    <option value="${course.getId()}">${course.getCourseName()}</option>
                </c:forEach>
            </select>
        </div>
    </div>
</div>
</body>

</html>