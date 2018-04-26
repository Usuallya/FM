package com.FM.controller.manage;

import com.FM.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@SessionAttributes("userId")
@RequestMapping("/course")
public class CourseController {
    @Autowired
    private CourseService courseService;

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
}
