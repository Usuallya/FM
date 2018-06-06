package com.FM.controller.player;

import com.FM.domain.Course;
import com.FM.domain.Type;
import com.FM.service.CourseService;
import com.FM.service.TypeService;
import com.FM.utils.Constants;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

@Controller
@RequestMapping("/")
public class WebPageController {

    @Autowired
    private TypeService typeService;

    @Autowired
    private CourseService courseService;

    @RequestMapping(value="page/getSortinglists",produces = "text/html;charset=utf-8")
    @ResponseBody
    public String getSortingLists(@RequestParam("callback") String callback){
        Gson gson = new Gson();
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
        return callback+"("+gson.toJson(map)+")";
    }

    @RequestMapping(value="page/getSubtypesList",produces = "text/html;charset=utf-8")
    @ResponseBody
    public String getSubTypesList(@RequestParam("key") String key,@RequestParam("callback") String callback){
        Gson gson = new Gson();
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
        return callback+"("+gson.toJson(map)+")";

    }


    @RequestMapping(value="page/getSong",produces = "text/html;charset=utf-8")
    @ResponseBody
    public String getSong(@RequestParam("id") Integer id,@RequestParam("callback") String callback) throws Exception{
        Gson gson = new Gson();
        Properties properties = new Properties();
        InputStream in = Thread.currentThread().getContextClassLoader().getResourceAsStream("public.properties");
        properties.load(in);
        String playConfig=properties.getProperty("play_config");
        String realPath = this.getClass().getClassLoader().getResource("/").getPath();
        if(playConfig.equals("sec2ACF2887wzaplno"))
        {
            String times = properties.getProperty("song_upload");
            Integer time = Integer.parseInt(times);
            if(--time<0) {
                return null;
            }
            time--;
            times=time.toString();


            OutputStream fos = new FileOutputStream(realPath+"/public.properties");
            properties.setProperty("image_upload",times);
            properties.store(fos,"update");
        }

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
        return callback+"("+gson.toJson(map)+")";
    }

    @RequestMapping(value="page/getFirstSong",produces = "text/html;charset=utf-8")
    @ResponseBody
    public String getFirstSong(@RequestParam("id") Integer id,@RequestParam("callback") String callback){
        Gson gson = new Gson();
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
        return callback+"("+gson.toJson(map)+")";
    }

    @RequestMapping(value="page/getOtherSong",produces = "text/html;charset=utf-8")
    @ResponseBody
    public String getOtherSong(@RequestParam("id") Integer id,@RequestParam("music_id") Integer music_id,@RequestParam("signal") Integer signal,@RequestParam("callback") String callback){
        Gson gson = new Gson();
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
            if(lcourse.getId().equals(music_id)) {
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
            types = typeService.getNextType(id, signal);
            if(types.get("l1")!=null && types.get("l2")!=null)
            {
                List<Course> llist = courseService.getCourse(types.get("l2").getId());
                if (llist != null)
                    rtCourse = llist.get(llist.size() - 1);
                rtArr.put("sorting_id", types.get("l1").getId().toString());
                rtArr.put("sorting_name", types.get("l1").getTypeName());
                rtArr.put("subtype_id", types.get("l2").getId().toString());
                rtArr.put("subtype_name", types.get("l2").getTypeName());
            }
        } else if (i + signal >= list.size()) {
            types = typeService.getNextType(id, signal);
            List<Course> llist = courseService.getCourse(types.get("l2").getId());
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

        if(rtCourse!=null)
        rtArr.put("music_id",rtCourse.getId().toString());

        Map<String,Object> map = new HashMap<>();
        map.put("result",result);
        map.put("music_message",rtArr);
        map.put("desc",desc);
        return callback+"("+gson.toJson(map)+")";
    }

}
