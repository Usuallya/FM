package com.FM.controller.manage;

import com.FM.domain.Course;
import com.FM.domain.Manager;
import com.FM.domain.Type;
import com.FM.domain.User;
import com.FM.service.CourseService;
import com.FM.service.ManagerService;
import com.FM.service.TypeService;
import com.FM.service.UserService;
import com.FM.utils.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.InputStream;
import java.util.*;

@Controller
@RequestMapping("Management")
public class ManageController {

    @Autowired
    ManagerService managerService;
    @Autowired
    TypeService typeService;
    @Autowired
    CourseService courseService;

    @RequestMapping("/")
    public String ManagerDefault(){
        return "forward:"+"/Management/Courses";
    }

    @RequestMapping("/index")
    public String ManagerIndex(HttpServletRequest request){
        return "forward:"+"/Management/Courses";
    }

    @RequestMapping("/Courses")
    public ModelAndView courses(HttpServletRequest request){
        ModelAndView modelAndView = new ModelAndView(Constants.COURSE_LOAD_PATH);
        HttpSession session = request.getSession();
        Manager manager = managerService.getManagerUser((Integer)session.getAttribute("userId"));
        List<Course> noTypeCourse = courseService.getCourse(0);
        List<Type> initL1Types = typeService.getTypes(0);
        List<Type> initl2Types = null;
        if(initL1Types.size()>0)
            initl2Types = typeService.getTypes(initL1Types.get(0).getId());
        modelAndView.addObject("noTypeCourse",noTypeCourse);
        modelAndView.addObject("L1Types",initL1Types);
        modelAndView.addObject("L2Types",initl2Types);
        modelAndView.addObject("user",manager);
        return modelAndView;
    }

    @RequestMapping(value="/courseUpload",produces = "text/html;charset=utf-8")
    public ModelAndView uploadCourse(@RequestParam(value = "course",required = false) MultipartFile[] files,HttpServletRequest request) throws Exception{
        ModelAndView modelAndView = new ModelAndView(Constants.COURSE_LOAD_PATH);
        Properties properties = new Properties();
        InputStream in = Thread.currentThread().getContextClassLoader().getResourceAsStream("public.properties");
        properties.load(in);
        String courseUploadPath=properties.getProperty("audio_upload_path");
        String coursePlayPath=properties.getProperty("audio_play_path");
        String contextName = request.getContextPath();
        if(files!=null) {
            for(int i =0;i<files.length;i++){
                String path = request.getSession().getServletContext().getRealPath("")
                        + request.getContextPath() + "/" +courseUploadPath + "/";

                String fileName = coursePlayPath+contextName+courseUploadPath+"/"+saveFile(files[i],path);
                courseService.addCourse(files[i].getOriginalFilename(),fileName);
            }
            modelAndView.addObject("tips", "上传成功");
            List<Course> noTypeCourse = courseService.getCourse(0);
            List<Type> l1Types = typeService.getTypes(0);
            List<Type> l2Types = typeService.getTypes(l1Types.get(0).getId());
            modelAndView.addObject("noTypeCourse",noTypeCourse);
            modelAndView.addObject("L2Types",l2Types);
            modelAndView.addObject("L1Types",l1Types);
        }else{
            modelAndView.setViewName("forward:/Management/Courses");
        }
     return modelAndView;
    }
    @RequestMapping("/iconUpload")
    public ModelAndView uploadIcon(@RequestParam(value = "icon",required = false) MultipartFile file,@RequestParam(value = "l2Type",required = false) String l2Type,HttpServletRequest request) throws Exception{
        Properties properties = new Properties();
        InputStream in = Thread.currentThread().getContextClassLoader().getResourceAsStream("public.properties");
        properties.load(in);
        String imageUploadPath=properties.getProperty("image_upload_path");
        String path = request.getSession().getServletContext().getRealPath("")+request.getContextPath()+"/"+imageUploadPath+"/";
        String imageLocation=null;
        String fileName=null;
        ModelAndView modelAndView = new ModelAndView(Constants.ICON_PATH);
        //把信息写入数据库
        if(file!=null && path!=null) {
            fileName = saveFile(file, path);
            if (fileName != null && typeService.uploadIcon(fileName, l2Type))
                modelAndView.addObject("tips", "上传成功");
            else {
                modelAndView.addObject("tips", "上传失败");
            }
        }
        List<Type> initL1Types = typeService.getTypes(0);
        List<Type> initL2Types = null;
        if(initL1Types.size()>0) {
            initL2Types = typeService.getTypes(initL1Types.get(0).getId());
            if (initL2Types.size() > 0)
                imageLocation = typeService.getIconLocation(initL2Types.get(0).getId());
        }
        modelAndView.addObject("L1Types",initL1Types);
        modelAndView.addObject("L2Types",initL2Types);
        modelAndView.addObject("l2fIconLocation",imageLocation);
        return modelAndView;
    }

    @RequestMapping("/changeOrder")
    @ResponseBody
    public String changeOrder(@RequestParam("Id") String Id,@RequestParam("ctType") String ctType,@RequestParam("operation") String operation){
             Integer otherId=0;

             if(ctType.equals("course")){
                if(operation.equals("1")){
                    otherId = courseService.orderUp(Integer.parseInt(Id));
                }else if(operation.equals("0")){
                    otherId = courseService.orderDown(Integer.parseInt(Id));
                }
             }else{
                 if(operation.equals("1")){
                     otherId = typeService.orderUp(Integer.parseInt(Id));
                 }else if(operation.equals("0")){
                     otherId = typeService.orderDown(Integer.parseInt(Id));
                 }
             }
             return otherId.toString();
    }
    private String saveFile(MultipartFile file, String path) {
        // 判断文件是否为空
        if (!file.isEmpty()) {
            try {
                File filepath = new File(path);
                Date now = new Date();
                String fileName=""+now.getTime()+file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));
                if (!filepath.exists())
                    filepath.mkdirs();
                // 转存文件
                file.transferTo(new File(path,fileName));
                return fileName;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }

}
