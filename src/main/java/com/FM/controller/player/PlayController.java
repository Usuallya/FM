package com.FM.controller.player;

import com.FM.domain.Course;
import com.FM.domain.Type;
import com.FM.service.CourseService;
import com.FM.service.TypeService;
import com.FM.utils.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/")
public class PlayController {

    @Autowired
    private TypeService typeService;

    @Autowired
    private CourseService courseService;

    @RequestMapping("/getSortinglists")
    @ResponseBody
    public Map<String,Object> getSortingLists(){
        List<Type> list = typeService.getTypes(0);;
        Boolean result = false;
        String desc="";
        if(list!=null)
        {
            result=true;
        }else{
            desc= Constants.FAIL;
        }
        Map<String,Object> map = new HashMap<>();
        map.put("result",result);
        map.put("sortingList",list);
        map.put("desc",desc);
        return map;
    }

    @RequestMapping("/getSubtypesList")
    @ResponseBody
    public Map<String,Object> getSubTypesList(@RequestParam("key") String key){
        Integer id = Integer.parseInt(key);
        List<Type> list = typeService.getTypes(id);
        Boolean result = false;
        String desc="";
        if(list!=null)
        {
            result=true;
        }else{
            desc= Constants.FAIL;
        }
        Map<String,Object> map = new HashMap<>();
        map.put("result",result);
        map.put("sortingList",list);
        map.put("desc",desc);
        return map;

    }


    @RequestMapping("/getSong")
    @ResponseBody
    public Map<String,Object> getSong(@RequestParam("id") Integer id){
        Boolean result = false;
        String desc="";
        Course course  = courseService.getOneCourse(id);
        if(course!=null)
        {
            result=true;
        }else{
            desc= Constants.FAIL;
        }
        Map<String,Object> map = new HashMap<>();
        map.put("result",result);
        map.put("songs",course);
        map.put("desc",desc);
        return map;
    }

    @RequestMapping("/getFirstSong")
    @ResponseBody
    public Map<String,Object> getFirstSong(@RequestParam("id") Integer id){
        Boolean result = false;
        String desc="";
        Course course = courseService.getCourse(id).get(0);
        if(course!=null)
        {
            result=true;
        }else{
            desc= Constants.FAIL;
        }
        Map<String,Object> map = new HashMap<>();
        map.put("result",result);
        map.put("songs",course);
        map.put("desc",desc);
        return map;
    }

    @RequestMapping("/getOtherSong")
    @ResponseBody
    public Map<String,Object> getOtherSong(@RequestParam("id") Integer id,@RequestParam("music_id") Integer music_id,@RequestParam("signal") Integer signal){
        List<Course> list = courseService.getCourse(id);
        Boolean result = false;
        String desc="";
        Course rtCourse=null;
        Map<String,Type> types=null;
        for(int i=0;i<list.size();i++){
            Course course = list.get(i);
            if(course.getId()==music_id)
                if(i+signal<list.size() && i+signal>-1) {
                    rtCourse = list.get(i+signal);
                    break;
                }else if(i+signal<-1){
                     types =typeService.getNextType(id,signal);
                    List<Course> llist = courseService.getCourse(types.get("l2").getId());
                    rtCourse = llist.get(llist.size()-1);
                    break;
                }else if(i+signal>=list.size()){
                     types =typeService.getNextType(id,signal);
                    List<Course> llist = courseService.getCourse(types.get("l2").getId());
                    rtCourse = llist.get(0);
                break;
                }
        }
        if(rtCourse!=null)
        {
            result=true;
        }else{
            desc= Constants.FAIL;
        }
        Map<String,String> rtArr=new HashMap<>();
        rtArr.put("music_id",rtCourse.getId().toString());
        rtArr.put("sorting_id",types.get("l2").getId().toString());
        rtArr.put("sorting_name",types.get("l2").getTypeName());
        rtArr.put("subtype_id",types.get("l1").getId().toString());
        rtArr.put("subtype_name",types.get("l1").getTypeName());

        Map<String,Object> map = new HashMap<>();
        map.put("result",result);
        map.put("music_message",rtArr);
        map.put("desc",desc);
        return map;
    }

}
