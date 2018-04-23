<%--
  Created by IntelliJ IDEA.
  User: 王海奇
  Date: 2018/4/21
  Time: 20:52
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>管理系统首页</title>
</head>
<body>
上传音频文件：<input type="file" />
实时更新的音频文件datalist
<button>向上调整顺序</button>
<button>向下调整顺序</button>
<input list="courses" />
<datalist id="courses">
    <option value="BMW" />
    <option value="Ford" />
    <option value="Volvo" />
</datalist>
多级联动的类型datalist
<button>添加分类</button>
<button>编辑分类</button>
<button>删除分类</button>
<input list="types" />
<datalist id="types">
    <option value="一年级" />
    <option value="二年级" />
    <option value="三年级" />
</datalist>

<input list="secondTypes" />
<datalist id="secondTypes">
    <option value="睡前故事" />
    <option value="mp3故事" />
</datalist>

</body>
</html>
