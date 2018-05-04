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
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Controller
@SessionAttributes("userId")
@RequestMapping("/Management")
public class ManageController {

    @Autowired
    ManagerService managerService;
    @Autowired
    TypeService typeService;
    @Autowired
    CourseService courseService;

    @RequestMapping("/")
    public String ManagerDefault(){
        return "forward:"+"/Courses";
    }

    @RequestMapping("/index")
    public String ManagerIndex(HttpServletRequest request){
        return "forward:"+"Courses";
    }

    @RequestMapping("/Courses")
    public ModelAndView courses(HttpServletRequest request){
        ModelAndView modelAndView = new ModelAndView(Constants.COURSE_LOAD_PATH);
        HttpSession session = request.getSession();
        Manager manager = managerService.getManagerUser((Integer)session.getAttribute("userId"));
        List<Course> noTypeCourse = courseService.getCourse(0);
        List<Type> l2Types = typeService.getAll2Types();
        modelAndView.addObject("noTypeCourse",noTypeCourse);
        modelAndView.addObject("L2Types",l2Types);
        modelAndView.addObject("user",manager);
        return modelAndView;
    }

    @RequestMapping(value="/courseUpload",produces = "text/html;charset=utf-8")
    public ModelAndView uploadCourse(MultipartHttpServletRequest request){
        List<MultipartFile> files = request.getFiles("course");
        ModelAndView modelAndView = new ModelAndView(Constants.COURSE_LOAD_PATH);
        if(files!=null) {
            for(int i =0;i<files.size();i++){
                //上传路径保存在数据库中？
                String path = request.getSession().getServletContext().getRealPath("")
                        + request.getContextPath() + "/" + Constants.COURSE_UPOLOAD_PATH + "/";

                String fileName = saveFile(files.get(i),path);
                courseService.addCourse(files.get(i).getOriginalFilename(),fileName);
            }
            modelAndView.addObject("tips", "上传成功");
        }
     return modelAndView;
    }
    @RequestMapping("/iconUpload")
    public ModelAndView uploadIcon(@RequestParam(value = "icon",required = false) MultipartFile file,@RequestParam(value = "l2Type",required = false) String l2Type,HttpServletRequest request){
        String path = request.getSession().getServletContext().getRealPath("")+request.getContextPath()+"/"+Constants.ICON_UPLOAD_PATH+"/";
        String imageLocation=null;
        String fileName=null;
        ModelAndView modelAndView = new ModelAndView(Constants.ICON_PATH);
        //把信息写入数据库
        if(file!=null && path!=null)
            fileName =saveFile(file,path);
            if(fileName !=null && typeService.uploadIcon(fileName,l2Type))
                modelAndView.addObject("tips","上传成功");
            else{
                modelAndView.addObject("tips","上传失败");
            }
        List<Type> initL2Types = typeService.getAll2Types();
            //这里应该改为默认选中刚上传的那个
        if(initL2Types!=null)
            imageLocation= typeService.getIconLocation(initL2Types.get(0).getId());
        modelAndView.addObject("L2Types",initL2Types);
        modelAndView.addObject("l2fIconLocation",imageLocation);
        return modelAndView;
    }

    @RequestMapping("/getInitTypesAndCourses")
    @ResponseBody
    public ModelAndView getTypesAndCourses(){
        ModelAndView modelAndView = new ModelAndView();
        List<Type> initL1Types = typeService.getTypes(0);
        List<Type> initL2Types = typeService.getTypes(initL1Types.get(0).getId());
        List<Course> initCourse = new ArrayList<>();
        if(initL2Types.size()>0)
         initCourse = courseService.getCourse(initL2Types.get(0).getId());
        modelAndView.addObject("L1Types",initL1Types);
        modelAndView.addObject("L2Types",initL2Types);
        modelAndView.addObject("initCourse",initCourse);
        modelAndView.setViewName(Constants.TYPE_COURSES);
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
                String fileName=""+now.getTime();
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
