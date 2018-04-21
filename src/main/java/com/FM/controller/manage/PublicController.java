package com.FM.controller.manage;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;
import javax.servlet.http.HttpServletRequest;

@Controller
@SessionAttributes("userId")
public class PublicController {
    @RequestMapping("/login")
    public ModelAndView login(@RequestParam("userName") String userName, @RequestParam("password") String password){
        return null;
    }
    @RequestMapping("/loginByToken")
    public ModelAndView login(@RequestParam("token") String token){
        return null;
    }
    @ExceptionHandler(RuntimeException.class)
    public String handleException(RuntimeException e, HttpServletRequest request){
        return "forward:/";
    }
}
