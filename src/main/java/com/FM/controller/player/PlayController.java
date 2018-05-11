package com.FM.controller.player;

import com.FM.domain.Course;
import com.FM.domain.Type;
import com.FM.service.CourseService;
import com.FM.service.TypeService;
import com.FM.utils.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
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
        Type ll2Type = typeService.getType(id);
        Type ll1Type = typeService.getType(ll2Type.getParentType());

        Boolean result = false;
        String desc="";
        Course rtCourse=null;
        Integer i = 0;
        Map<String,Type> types= new HashMap<>();
        types.put("l1",ll1Type);
        types.put("l2",ll2Type);

        Map<String,String> rtArr=new HashMap<>();
        for(int inum=0;inum<list.size();inum++) {
            Course lcourse = list.get(inum);
            if(lcourse.getId()==music_id) {
                i=inum;
                break;
            }
        }
                if(i + signal < list.size() && i + signal > -1) {
                    rtCourse = list.get(i + signal);
                    Type l2Type = typeService.getType(rtCourse.getType());
                    Type l1Type = typeService.getType(l2Type.getParentType());
                    rtArr.put("sorting_id", l1Type.getId().toString());
                    rtArr.put("sorting_name", l1Type.getTypeName());
                    rtArr.put("subtype_id", l2Type.getId().toString());
                    rtArr.put("subtype_name", l2Type.getTypeName());
                }
                if (i + signal <= -1) {
                    List<Course> llist=null;
                    while(llist==null || llist.size()==0) {
                        types = typeService.getNextType(types.get("l2").getId(), signal);
                        llist = courseService.getCourse(types.get("l2").getId());
                    }
                    if(llist!=null)
                    rtCourse = llist.get(llist.size() - 1);

                    rtArr.put("sorting_id", types.get("l1").getId().toString());
                    rtArr.put("sorting_name", types.get("l1").getTypeName());
                    rtArr.put("subtype_id", types.get("l2").getId().toString());
                    rtArr.put("subtype_name", types.get("l2").getTypeName());
                } else if (i + signal >= list.size()) {
                    List<Course> llist=null;
                    while(llist==null || llist.size()==0) {
                        types = typeService.getNextType(types.get("l2").getId(), signal);
                        //System.out.println(types.get("l2").getTypeName());
                        llist = courseService.getCourse(types.get("l2").getId());
                        //System.out.println(llist.size());
                    }
                    if(llist!=null)
                    rtCourse = llist.get(0);
                    rtArr.put("sorting_id", types.get("l1").getId().toString());
                    rtArr.put("sorting_name", types.get("l1").getTypeName());
                    rtArr.put("subtype_id", types.get("l2").getId().toString());
                    rtArr.put("subtype_name", types.get("l2").getTypeName());
                }

        if(rtCourse!=null)
        {
            result=true;
        }else{
            desc= Constants.FAIL;
        }
        rtArr.put("music_id",rtCourse.getId().toString());
        Map<String,Object> map = new HashMap<>();
        map.put("result",result);
        map.put("music_message",rtArr);
        map.put("desc",desc);
        return map;
    }

}
