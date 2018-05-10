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
        List<Course> list = tcDao.getCourses(typeId);
        return list;
    }

    public Course getOneCourse(Integer courseId){
        return tcDao.getCourse(courseId);
    }

    public boolean add2Type(List<String> courseId, String typeId){
        Integer cId;
        Integer tId = Integer.parseInt(typeId);
        for(String couId : courseId) {
            cId = Integer.parseInt(couId);
            if(!tcDao.add2Type(cId,tId))
                return false;
        }
        return true;
    }

    public boolean deleteCourse(String courseId){
        Integer cId = Integer.parseInt(courseId);
        return tcDao.deleteCourse(cId);
    }

    public Integer addCourse(String fileName,String location){
        Integer success = tcDao.addCourse(fileName,location);
        return success;
    }

    public Integer orderUp(Integer courseId){

        return tcDao.setCourseOrder(courseId,1);
    }
    public Integer orderDown(Integer courseId){

        return tcDao.setCourseOrder(courseId,0);
    }
}
