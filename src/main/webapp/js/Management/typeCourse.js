function getTypes(option){
var l1TypeId = option.value;
    $.ajax({
        type : "post",
        url : "/Management/type/getTypes",
        dataType : "json",
        data : {
            parentType:l1TypeId
        },
        success : function(data) {
            $("#L2List").empty();
            data.forEach(function(l2Type,i,array){
                $("#L2List").append("<option value='"+l2Type.id+"'>"+l2Type.typeName+"</option>");
                if(i==0){
                    var e = $("#L2List option[value="+l2Type.id+"]");
                    getDefaultCourse(e);
                }
            });
            $("#L2List option:first").prop("selected", 'selected');
        },
        error : function(XMLHttpRequest, textStatus, errorThrown) {
            alert("发生错误");
            console.log(XMLHttpRequest);
            console.log(textStatus);
        }
    });

}


function cgetTypes(option){
    var l1TypeId = option.value;
    $.ajax({
        type : "post",
        url : "/Management/type/getTypes",
        dataType : "json",
        data : {
            parentType:l1TypeId
        },
        success : function(data) {
            $("#L22List").empty();
            data.forEach(function(l2Type,i,array){
                $("#L22List").append("<option value='"+l2Type.id+"'>"+l2Type.typeName+"</option>");
                if(i==0){
                    var e = $("#L22List option[value="+l2Type.id+"]");
                }
            });

        },
        error : function(XMLHttpRequest, textStatus, errorThrown) {
            alert("发生错误");
            console.log(XMLHttpRequest);
            console.log(textStatus);
        }
    });

}

function getTypesForAdd(option){
    var l1TypeId = $(option).attr("value");
    $.ajax({
        type : "post",
        url : "/Management/type/getTypes",
        dataType : "json",
        data : {
            parentType:l1TypeId
        },
        success : function(data) {
            $("#L2List").empty();
            data.forEach(function(l2Type,i,array){
                $("#L2List").append("<option value='"+l2Type.id+"'>"+l2Type.typeName+"</option>");
            });

        },
        error : function(XMLHttpRequest, textStatus, errorThrown) {
            alert("发生错误");
            console.log(XMLHttpRequest);
            console.log(textStatus);
        }
    });

}


function getDefaultCourse(option){
    var l2TypeId = $(option).attr("value");
    $.ajax({
        type : "post",
        url : "/Management/Course/getCourses",
        dataType : "json",
        data : {
            l2Type:l2TypeId
        },
        success : function(data) {
            $("#L2CourseList").empty();
            data.forEach(function(course,i,array){
                console.log(data[i].id);
                $("#L2CourseList").append("<option value='"+course.id+"'>"+course.courseName+"</option>");
            });
            $("#L2CourseList option:first").prop("selected", 'selected');
        },
        error : function(XMLHttpRequest, textStatus, errorThrown) {
            alert("发生错误");
            console.log(XMLHttpRequest);
            console.log(textStatus);
        }
    });
}


function getCourse(option){
    var l2TypeId = option.value;
    $.ajax({
        type : "post",
        url : "/Management/Course/getCourses",
        dataType : "json",
        data : {
            l2Type:l2TypeId
        },
        success : function(data) {
            $("#L2CourseList").empty();
            data.forEach(function(course,i,array){
                console.log(data[i].id);
                $("#L2CourseList").append("<option value='"+course.id+"'>"+course.courseName+"</option>");
            });
            $("#L2CourseList option:first").prop("selected", 'selected');
        },
        error : function(XMLHttpRequest, textStatus, errorThrown) {
            alert("发生错误");
            console.log(XMLHttpRequest);
            console.log(textStatus);
        }
    });
}

function changeLevel(){
if($("#l1select").val()[0]==2){
    $("#l2select").attr("class","form-control show");
}else
{
    $("#l2select").attr("class","form-control hidden");
}
}

function addType(){
    var typeName = $("#typeName").val();
    var level = $("#l1select").val()[0];
    var parentType=0;
    if(typeName=="")
    {
        alert("请输入类别名称！");
        return;
    }else{
        if(level==2 && $("#l2select option:selected").attr("value")==0)
        {
            alert("请选择对应的一级类别");
            return;
        }else if(level==2 && $("#l2select option:selected").attr("value")!=0) {
            parentType = $("#l2select option:selected").attr("value");
        }
        $.ajax({
            type : "post",
            url : "/Management/type/addType",
            dataType : "json",
            data : {
                type:typeName,
                level:level,
                parent:parentType
            },
            success : function(data) {
                    $("#L1List").empty();
                    $("#L2List").empty();
                    $("#l2select").empty();
                    $.each(data, function (key, values) {
                        if(key!="flag")
                        values.forEach(function (type, i, array) {
                            if(key=="L1Types") {
                                $("#L1List").append("<option value='" + type.id + "'>" + type.typeName + "</option>");
                                if(parentType==0)
                                    $("#l2select").append("<option value='" + type.id + "'>" + type.typeName + "</option>");
                            }
                            if(key=="L2Types") {
                                $("#L2List").append("<option value='" + type.id + "'>" + type.typeName + "</option>");
                            }
                        });
                        if(key=="flag" && values=="0") {
                            alert("添加类别失败");
                            return;
                        }
                        else if(key=="flag" && values=="-1"){
                            alert("已经存在同名类别");
                            return;
                        }else if(key=="flag" && values=="1"){
                            alert("添加类别成功！");
                        }
                    });
                    if(parentType!=0)
                    {
                        $("#L1List").val(parentType);
                        getTypesForAdd($("#L1List option:selected"));
                    }
            },
            error : function(XMLHttpRequest, textStatus, errorThrown) {
                alert("发生错误");
                console.log(XMLHttpRequest);
                console.log(textStatus);
            }
        });
    }
}

function selectL1Level(){
    // var parentType = $("#l2select").val()[0];
    // if(parentType!=0)
    // {
    //     $("#L1List").val(parentType);
    //     getTypesForAdd($("#L1List option:selected"));
    // }
}

function delType(button) {
    var tips = "";
    var Id="";
    if ($(button).attr("id") == "d1"){
        if($("#L1List").val()!=null) {
            tips = "确定删除此一级分类？删除后其下所有二级分类也将同时删除！";
            Id = $("#L1List").val()[0];
        }
        else{
            alert("请选择要删除的分类！");
            return;
        }
    }else if ($(button).attr("id") == "d2") {
        if($("#L2List").val()!=null) {
            tips = "确定删除此分类？";
            Id = $("#L2List").val()[0];
        }
        else{
            alert("请选择要删除的分类！");
            return;
        }
    }
    if (confirm(tips)){
        $.ajax({
            type: "post",
            url: "/Management/type/deleteType",
            dataType: "json",
            data: {
                types:Id
            },
            success: function (data) {
                if(data=="1000"){
                    window.location.reload();
                }else if(data=="1001")
                    alert("删除失败！");
            },
            error: function (XMLHttpRequest, textStatus, errorThrown) {
                alert("发生错误");
                console.log(XMLHttpRequest);
                console.log(textStatus);
            }
        });
}
}


function add2Type(){
    var tId = $("#L22List option:selected").attr("value");
    var noTypeList = $("#NoTypeList").val();
    var cId=JSON.stringify(noTypeList);
    if(tId==null || cId==null) {
        alert("请选择文件和对应分类");
        return;
    }
    else{
        $.ajax({
            type : "post",
            url : "/Management/Course/add2Type",
            dataType : "json",
            data : {
                typeId:tId,
                courseId:cId
            },
            success : function(data) {
                alert("添加成功");
                noTypeList.forEach(function(Id,i,array){
                $("#NoTypeList option[value="+Id+"]").remove();
            });
            },
            error : function(XMLHttpRequest, textStatus, errorThrown) {
                alert("发生错误");
                console.log(XMLHttpRequest);
                console.log(textStatus);
            }
        });
    }
}
function deleteCourse(){
    var cId = $("#NoTypeList").val()[0];
    if(cId==null)
        alert("请选择要删除的文件");
    $.ajax({
        type : "post",
        url : "/Management/Course/deleteCourse",
        dataType : "json",
        data : {
            courseId:cId
        },
        success : function(data) {
            if(data==1000)
            $("#NoTypeList option[value="+cId+"]").remove();
            else
                alert("删除失败");
        },
        error : function(XMLHttpRequest, textStatus, errorThrown) {
            alert("发生错误");
            console.log(XMLHttpRequest);
            console.log(textStatus);
        }
    });
}

function chg2Icon(){

    var typeId = $("#L2List").val();
    $.ajax({
        type : "post",
        url : "/Management/type/getIconLocation",
        dataType : "html",
        data : {
            typeId:typeId
        },
        success : function(data) {
            var newPath = "/icon/"+data;
            $("#iconPreview").attr("src",newPath);
        },
        error : function(XMLHttpRequest, textStatus, errorThrown) {
            alert("发生错误");
            console.log(XMLHttpRequest);
            console.log(textStatus);
        }
    });
}


function order(button){
    var type = $(button).attr("id");
    var optionname;
    var ctType;
    var Id;
    var operation;
    var list;
    switch (type){
        case "l1up":
            Id = $("#L1List").val()[0];
            operation=0;
            ctType="l1";
            list="#L1List";
            optionname=$(list).find("option:selected").text();
            break;
        case "l1down":
            Id =$("#L1List").val()[0];
            operation=1;
            ctType="l1";
            list="#L1List";
            optionname=$(list).find("option:selected").text();
            break;
        case "l2up":
            Id =$("#L2List").val()[0];
            operation=0;
            ctType="l2";
            list="#L2List";
            optionname=$(list).find("option:selected").text();
            break;
        case "l2down":
            Id =$("#L2List").val()[0];
            operation=1;
            ctType="l2";
            list="#L2List";
            optionname=$(list).find("option:selected").text();
            break;
        case "c-up":
            Id =$("#L2CourseList").val()[0];
            operation=0;
            ctType="course";
            list="#L2CourseList";
            optionname=$(list).find("option:selected").text();
            break;
        case "c-down":
            Id =$("#L2CourseList").val()[0];
            operation=1;
            ctType="course";
            list="#L2CourseList";
            optionname=$(list).find("option:selected").text();
            break;
        default:break;
    }

    $.ajax({
        type : "post",
        url : "/Management/changeOrder",
        dataType : "json",
        data : {
            Id:Id,
            ctType:ctType,
            operation:operation
        },
        success : function(data) {
            if(data!=0) {
                if (operation == 0) {
                    $(list + " option[value=" + Id + "]").remove();
                    $("<option value='" + Id + "'>" + optionname + "</option>").insertBefore(list + " option[value=" + data + "]");
                }
                else if (operation == 1) {
                    $(list + " option[value=" + Id + "]").remove();
                    $("<option value='" + Id + "'>" + optionname + "</option>").insertAfter(list + " option[value=" + data + "]");
                }
            }
        },
        error : function(XMLHttpRequest, textStatus, errorThrown) {
            alert("发生错误");
            console.log(XMLHttpRequest);
            console.log(textStatus);
        }
    });
}

function edit(e){
    var edittext;
    var typeId;
    if($(e).attr("id")=="edit1"){
        if($("#editt1text").attr("class")=="form-control hidden") {
            $("#editt1text").attr("class", "form-control show");
            return;
        }
        if($("#L1List").val()==null) {
            alert("请先选择要修改的一级分类");
            return;
        }
        edittext = $("#editt1text").val();
        typeId=$("#L1List").val()[0];

    }else if($(e).attr("id")=="edit2"){
        if($("#editt2text").attr("class")=="form-control hidden") {
            $("#editt2text").attr("class", "form-control show");
            return;
        }
        if($("#L2List").val()==null) {
            alert("请先选择要修改的二级分类");
            return;
        }
        edittext = $("#editt2text").val();
        typeId=$("#L2List").val()[0];
    }
    $.ajax({
        type : "post",
        url : "/Management/type/editType",
        dataType : "json",
        data : {
            newtypeName:edittext,
            typeId:typeId
        },
        success : function(data) {
            if(data==true) {
                alert("修改类型名成功");
                window.location.reload();
            }
        },
        error : function(XMLHttpRequest, textStatus, errorThrown) {
            alert("发生错误");
            console.log(XMLHttpRequest);
            console.log(textStatus);
        }
    });




}


//typeCourse页面
function delCourse(){
    var id = $("#L2CourseList").val()[0];
    if(id=="" || id==null)
    {
        alert("请选择要删除的课程文件！");
        return;
    }
    $.ajax({
        type : "post",
        url : "/Management/Course/deleteCourse",
        dataType : "json",
        data : {
            courseId:id
        },
        success : function(data) {
            if(data=="1000")
                $("#L2CourseList option[value="+id+"]").remove();
                else if(data=="1001")
                    alert("删除失败");
        },
        error : function(XMLHttpRequest, textStatus, errorThrown) {
            alert("发生错误");
            console.log(XMLHttpRequest);
            console.log(textStatus);
        }
    });

}

function validate(){
    var fileName = $("#file").val();
    if(fileName == "" || (!fileName.endsWith(".mp3") && !fileName.endsWith(".wav") &&!fileName.endsWith(".avi")) ) {
        alert("没有上传音频文件或者上传的文件不是所要求的格式！");
        return false;
    }

    return true;
}
function submitURL(){
    var url = $("#urlText").val();
    if(postComment())
    {
        $.ajax({
            type : "post",
            url : "/Management/Course/addURL",
            dataType : "json",
            data : {
                musicURL:url
            },
            success : function(data) {
                alert("添加成功");
                window.location.reload();
            },
            error : function(XMLHttpRequest, textStatus, errorThrown) {
                alert("发生错误");
                console.log(XMLHttpRequest);
                console.log(textStatus);
            }
        });
    }
}
function postComment() {
    //验证url网址
    if($("#urlText").val()) {
        var str=$("#urlText").val();
        var Expression=/http(s)?:\/\/([\w-]+\.)+[\w-]+(\/[\w- .\/?%&=]*)?/;
        var objExp=new RegExp(Expression);
        if(objExp.test(str) != true){
            alert("网址格式不正确！请重新输入");
            return false;
        } else {
            return true;
        }
    }
}

function validateImage(){
    var fileName = $("#file").val();
    if(fileName == "" || (!fileName.endsWith(".png") && !fileName.endsWith(".jpg") &&!fileName.endsWith(".bmp")) ) {
        alert("没有上传图标文件或者上传的文件不是所要求的格式！");
        return false;
    }
    if($("#L1List").val()==0 || $("#L2List").val()==0) {
        alert("请指定分类");
        return false;
    }
    return true;
}


function flagCourse(course){

}