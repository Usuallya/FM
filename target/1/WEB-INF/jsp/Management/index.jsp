<%--
  Created by IntelliJ IDEA.
  User: 王海奇
  Date: 2018/4/21
  Time: 20:52
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page isELIgnored="false" %>
<html>
<head>
    <title>管理系统首页</title>
    <link type="text/css" rel="styleSheet"  href="/css/managerindex.css" />
</head>
<body>
<div id="login" >
    欢迎您,${user.userName}
    <a href="/Management/logout">退出登录</a>
</div>

<form action="/courseUpload" method="post"  enctype="multipart/form-data">
    <fieldset>
        <legend>课程音频文件上传</legend>
<input type="file" name="course" multiple="multiple" /> <br />
        <input type="submit" value="上传" />
    </fieldset>
</form>
<form action="/iconUpload" method="post"  enctype="multipart/form-data">
    <fieldset>
        <legend>二级分类图标上传</legend>
        <input type="file" name="Icon" multiple="multiple" /> <br />
        <input type="submit" value="上传" />
    </fieldset>
</form>

<div id="left">&nbsp;</div>
<div id="middle">
<div class="selectArea">
    <p>音频文件列表</p>
    <select multiple="multiple" size="6" name="coursefile">
    <option value="1">BMW</option>
    <option value="2">Benz</option>
    <option value="3">Ford</option>
</select>
</div>
<div>
    <button>向上调整顺序</button>
    <button>向下调整顺序</button>
</div>


<div class="selectArea">
    <p>一级分类</p>
<select multiple="multiple" size="6" name="Level1">
    <option value="1">一年级</option>
    <option value="2">二年级</option>
    <option value="3">三年级</option>
</select>
</div>
<div>
<button>添加分类</button>
<button>删除分类</button>
</div>
<div>
<button>向上调整顺序</button>
<button>向下调整顺序</button>
</div>

<div class="selectArea">
    <p>二级分类</p>
<select multiple="multiple" size="6" name="Level2">
    <option value="1">睡前故事</option>
    <option value="2">MP3故事</option>
    <option value="3">教学语音</option>
</select>
</div>
    <div id="iconArea">
        图标展示区域
    </div>
</div>
<div id="right">&nbsp;</div>

</body>
</html>
