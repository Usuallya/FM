package com.FM.controller.manage;

import com.FM.domain.Course;
import com.FM.service.CourseService;
import com.FM.service.TypeService;
import com.FM.utils.Constants;
import com.mysql.cj.xdevapi.JsonArray;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Controller
@RequestMapping("/")
public class CourseController {
    @Autowired
    private CourseService courseService;
    @Autowired
    private TypeService typeService;

    @RequestMapping("Management/Course/order/{operation}")
    @ResponseBody
    public Integer courseOrder(@PathVariable("operation") String operation, @RequestParam("courseId")Integer courseId){
        Integer order = 0;
        if(operation.equals("up")){
            order = courseService.orderUp(courseId);
        }else if(operation.equals("down")){
            order = courseService.orderDown(courseId);
        }
        return order;
    }

    @RequestMapping("Management/Course/addURL")
    @ResponseBody
    public Integer addURL(@RequestParam("musicURL") String musicURL){

        String musicName = musicURL.substring(musicURL.lastIndexOf("/")+1);
        String location = musicURL;
        return courseService.addCourse(musicName,location);
    }

    @RequestMapping("Management/Course/getCourses")
    @ResponseBody
    public List<Course> getCourse(@RequestParam(value = "l2Type") String stypeId){
        Integer typeId= Integer.parseInt(stypeId);
        List<Course> list = courseService.getCourse(typeId);
        return list;
    }

    @RequestMapping("Management/Course/add2Type")
    @ResponseBody
    public String add2Type(@RequestParam(value="courseId") String courseId,@RequestParam(value="typeId") String typeId){
        List<String> list = new ArrayList<String>();
        JSONArray json = JSONArray.fromObject(courseId);
        if(json.size()>0)
        {
            for(int i =0;i<json.size();i++){
                list.add(json.get(i).toString());
            }
        }
        if(courseService.add2Type(list,typeId))
            return Constants.SUCCESS;
        else
            return Constants.FAIL;
    }


    @RequestMapping("Management/Course/deleteCourse")
    @ResponseBody
    public String deleteCourse(@RequestParam("courseId") String courseId){
        if(courseService.deleteCourse(courseId))
            return Constants.SUCCESS;
        else
            return Constants.FAIL;
    }
}
