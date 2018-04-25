package com.FM.controller.manage;

import com.FM.domain.User;
import com.FM.service.ManagerService;
import com.FM.service.UserService;
import com.FM.utils.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
@SessionAttributes("userId")
@RequestMapping("/Management")
public class PublicController {

    @Autowired
    ManagerService managerService;

    @RequestMapping("/login")
    public ModelAndView login(@RequestParam(value = "username",required = false) String userName,@RequestParam(value = "password",required = false) String password,HttpServletRequest request){
        ModelAndView modelAndView = new ModelAndView();
        HttpSession session = request.getSession();
        if(session.getAttribute("userId")==null){
            //如果只是到登陆页面
            if(userName==null || password==null) {
                modelAndView.setViewName(Constants.MANAGELOGIN);
            }else{
                //这是真登陆的用户
                String userId = managerService.ManagertLogin(userName,password);
                if (userId!=null) {
                    session.setAttribute("userId",userId );
                    User user =managerService.getManagerUser(userId);
                    modelAndView.setViewName(Constants.MANAGEINDEX);
                    modelAndView.addObject("user",user);
                } else {
                    modelAndView.setViewName(Constants.MANAGELOGIN);
                    modelAndView.addObject("loginFail", "登录失败，请重试");
                }
            }
        }else{
            User user = managerService.getManagerUser((String)session.getAttribute("userId"));
            modelAndView.setViewName(Constants.MANAGEINDEX);
            modelAndView.addObject("user",user);
        }
        return modelAndView;
    }

}
