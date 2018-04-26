package com.FM.service;

import com.FM.dao.TCDao;
import com.FM.domain.Course;
import com.FM.domain.Type;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CourseService {
    @Autowired
    private TCDao tcDao;

    public List<Course> getCourse(Integer typeId){
        List<Course> list = tcDao.getCourse(typeId);
        return list;
    }

    public boolean add2Type(Course course, Type type){

        return false;
    }

    public Integer orderUp(Integer courseId){
        Course course = new Course();
        course.setId(courseId);
        return tcDao.setCourseOrder(course);
    }
    public Integer orderDown(Integer courseId){
        Course course = new Course();
        course.setId(courseId);
        return tcDao.setCourseOrder(course);
    }
}
