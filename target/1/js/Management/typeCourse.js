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
            });
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
    $("#l2select").show();
}else
{
    $("#l2select").hide();
}
}

function addType(){
    var typeName = $("#typeName").val();
    var level = $("#l1select").val()[0];
    var parent = $("#l2select").val()[0];
    if(typeName==null)
    {
        alert("请输入类别名称！");
    }else{
        $.ajax({
            type : "post",
            url : "/Management/type/addType",
            dataType : "json",
            data : {
                type:typeName,
                level:level,
                parent:parent
            },
            success : function(data) {
                    $("#L1List").empty();
                    $("#L2List").empty();
                    $.each(data, function (key, values) {
                        if(key!="flag")
                        values.forEach(function (type, i, array) {
                            if(key=="L1Types")
                                $("#L1List").append("<option value='" + type.id + "'>" + type.typeName + "</option>");
                            if(key=="L2Types")
                                $("#L2List").append("<option value='" + type.id + "'>" + type.typeName + "</option>");
                        });
                        if(key=="flag" && values=="0")
                            alert("添加类别失败");
                        else if(key=="flag" && values=="-1")
                            alert("已经存在同名类别");
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

function delType(button){
    var tips="";
    if($(button).attr("id")=="d1"){
        tips="确定删除此一级分类？删除后其下所有二级分类也将同时删除！";
    }else if($(button).attr("id")=="d2"){
        tips="确定删除此分类？";
    }

    //删除选定的option：$("#L2CourseList option:selected").remove();
}


function add2Type(){
    var tId = $("#L2List").val()[0];
    var cId = $("#NoTypeList").val()[0];
    if(tId==null || cId==null)
        alert("请选择文件和对应分类");
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
                $("#NoTypeList option[value="+cId+"]").remove();
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

    var typeId = $("#l2Type").val();
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
            alert(data);
            if(operation==0) {
                $(list + " option[value=" + Id + "]").remove();
                $("<option id='"+Id+"'>"+optionname+"</option>").insertBefore(list + " option[value=" + data + "]");
            }
            else if(operation==1){
                $(list + " option[value=" + Id + "]").remove();
                $("<option id='"+Id+"'>"+optionname+"</option>").insertAfter(list + " option[value=" + data + "]");
            }
        },
        error : function(XMLHttpRequest, textStatus, errorThrown) {
            alert("发生错误");
            console.log(XMLHttpRequest);
            console.log(textStatus);
        }
    });
}

function uploadImage(){
//判断文件类型，限制为图片。 参照农场
}

//typeCourse页面
function delCourse(){
    var id = $("#L2CourseList").val();
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

function flagCourse(course){

}