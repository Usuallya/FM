package com.FM.controller.manage;

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
    public ModelAndView ManagerIndex(HttpServletRequest request){
        ModelAndView modelAndView = new ModelAndView(Constants.MANAGEINDEX);
        HttpSession session = request.getSession();
        User user = managerService.getManagerUser((String)session.getAttribute("userId"));
        modelAndView.addObject("user",user);
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
    public List<String> Course(@RequestParam(value = "type") Integer typeId){
        return courseService.getCourse(typeId);
    }

    @RequestMapping("/getTypes")
    @ResponseBody
    public List<String> getTypes(@RequestParam("parentType") Integer parentType){
        return typeService.getTypes(parentType);
    }

    @RequestMapping("/addType")
    @ResponseBody
    public String addType(@RequestParam("type") String type,@RequestParam("level") Integer level,@RequestParam("parent") String parent){
        if(typeService.addType(type,level,parent))
            return Constants.SUCCESS;
        else
            return Constants.FAIL;
    }
    @RequestMapping("/deleteType")
    @ResponseBody
    public String deleteType(@RequestParam("type") String typeName,@RequestParam("parent") String parent){
            if(typeService.deleteType(typeName,parent))
                return Constants.SUCCESS;
            else
                return Constants.FAIL;
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
