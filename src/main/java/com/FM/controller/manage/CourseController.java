package com.FM.controller.manage;

import com.FM.domain.Course;
import com.FM.service.CourseService;
import com.FM.service.TypeService;
import com.FM.utils.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@SessionAttributes("userId")
@RequestMapping("/Management/Course")
public class CourseController {
    @Autowired
    private CourseService courseService;
    @Autowired
    private TypeService typeService;

    @RequestMapping("/order/{operation}")
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

    @RequestMapping("/getCourses")
    @ResponseBody
    public List<Course> getCourse(@RequestParam(value = "l2Type") String stypeId){
        Integer typeId= Integer.parseInt(stypeId);
        List<Course> list = courseService.getCourse(typeId);
        return list;
    }

    @RequestMapping("/add2Type")
    @ResponseBody
    public String add2Type(@RequestParam(value="courseId") String courseId,@RequestParam(value="typeId") String typeId){
        System.out.println(courseId);
        if(courseService.add2Type(courseId,typeId))
            return Constants.SUCCESS;
        else
            return Constants.FAIL;
    }


    @RequestMapping("/deleteCourse")
    @ResponseBody
    public String deleteCourse(@RequestParam("courseId") String courseId){
        if(courseService.deleteCourse(courseId))
            return Constants.SUCCESS;
        else
            return Constants.FAIL;
    }
}
