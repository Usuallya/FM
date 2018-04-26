

function getTypes(option){
var l1TypeId = option.value;
    $.ajax({
        type : "post",
        url : "/Management/getTypes",
        dataType : "json",
        data : {
            parentType:l1TypeId
        },
        success : function(data) {
            $("#L2List").remove();
           data.forEach(function(i,l2Type){
               $("#L2List").append("<option value='"+l2Type.id+"'>"+l2Type.typeName+"</option>");
           })
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
        url : "/Management/getCourses",
        dataType : "json",
        data : {
            l2Type:l2TypeId
        },
        success : function(data) {
            $("#L2CourseList").remove();
            data.forEach(function(i,course){
                $("#L2CourseList").append("<option value='"+course.id+"'>"+course.courseName+"</option>");
            })
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