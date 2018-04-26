package com.FM.controller.manage;

import com.FM.domain.Manager;
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
import org.springframework.web.bind.support.SessionStatus;
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
            if(userName==null || password==null) {
                modelAndView.setViewName(Constants.MANAGELOGIN);
            }else{
                Integer userId = managerService.ManagerLogin(userName,password);
                if (userId!=null) {
                    session.setAttribute("userId",userId );
                    Manager manager =managerService.getManagerUser(userId);
                    modelAndView.setViewName("forward:"+Constants.MANAGEINDEX);
                    modelAndView.addObject("user",manager);
                } else {
                    modelAndView.setViewName(Constants.MANAGELOGIN);
                    modelAndView.addObject("loginFail", "登录失败，请重试");
                }
            }
        }else{
            Manager manager = managerService.getManagerUser((Integer)session.getAttribute("userId"));
            modelAndView.setViewName("forward:"+Constants.MANAGEINDEX);
            modelAndView.addObject("user",manager);
        }
        return modelAndView;
    }
    @RequestMapping("/logout")
    public String logout(SessionStatus sessionStatus,HttpServletRequest request){
        HttpSession session = request.getSession();
        session.removeAttribute("userId");
        session.invalidate();
        sessionStatus.setComplete();
        return "forward:"+Constants.MANAGELOGIN;
    }

}
