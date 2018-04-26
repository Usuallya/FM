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
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;
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
        return "forward:"+Constants.MANAGEINDEX;
    }

    @RequestMapping("/index")
    public ModelAndView ManagerIndex(HttpServletRequest request){
        ModelAndView modelAndView = new ModelAndView(Constants.MANAGEINDEX);
        HttpSession session = request.getSession();
        Manager manager = managerService.getManagerUser((Integer)session.getAttribute("userId"));
        List<Type> initL1Types = typeService.getTypes(0);
        List<Type> initL2Types = typeService.getTypes(initL1Types.get(0).getId());
        List<Course> initCourse = courseService.getCourse(initL2Types.get(0).getId());
        List<Course> noTypeCourse = courseService.getCourse(0);
        modelAndView.addObject("L1Types",initL1Types);
        modelAndView.addObject("L2Types",initL2Types);
        modelAndView.addObject("initCourse",initCourse);
        modelAndView.addObject("noTypeCourse",noTypeCourse);
        modelAndView.addObject("user",manager);
        return modelAndView;
    }

    @RequestMapping(value="/courseUpload",produces = "text/html;charset=utf-8")
    public ModelAndView uploadCourse(@RequestParam(value = "course",required = false) MultipartFile file, HttpServletRequest request){
        ModelAndView modelAndView = new ModelAndView(Constants.MANAGEINDEX);
        if(file!=null) {
            String path = request.getSession().getServletContext().getRealPath("")
                    + request.getContextPath() + "/" + Constants.COURSE_PATH + "/";
            if (saveFile(file, path)) {
                System.out.println("上传成功");
            } else {
                System.out.println("上传失败");
            }
            modelAndView.addObject("tips", "上传成功");
        }
     return modelAndView;
    }
    @RequestMapping("/iconUpload")
    public ModelAndView uploadIcon(@RequestParam("icon") MultipartFile file,HttpServletRequest request){
        String path = request.getSession().getServletContext().getRealPath("")+request.getContextPath()+"/"+Constants.ICON_PATH+"/";
        ModelAndView modelAndView = new ModelAndView();
        if(saveFile(file,path)) {
            modelAndView.addObject("tips","上传成功");
        }
        else {
            modelAndView.addObject("tips","上传失败");
        }
        return modelAndView;
    }

    @RequestMapping("/getCourses")
    @ResponseBody
    public List<Course> getCourse(@RequestParam(value = "l2Type") String stypeId){
        Integer typeId= Integer.parseInt(stypeId);
        return courseService.getCourse(typeId);
    }

    @RequestMapping("/getTypes")
    @ResponseBody
    public List<Type> getTypes(@RequestParam("parentType") String sparentType){
        Integer parentType = Integer.parseInt(sparentType);
        return typeService.getTypes(parentType);
    }

    private boolean saveFile(MultipartFile file, String path) {
        // 判断文件是否为空
        if (!file.isEmpty()) {
            try {
                File filepath = new File(path);
                if (!filepath.exists())
                    filepath.mkdirs();
                // 转存文件
                file.transferTo(new File(path,file.getOriginalFilename()));
                return true;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return false;
    }

}
