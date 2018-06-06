package com.FM.controller.player;


import com.FM.service.UserService;
import com.FM.utils.Constants;
import com.FM.utils.ValidateToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/")
public class PlayPublicController {

    @Autowired
    UserService userService;

    @RequestMapping("/login")
    public ModelAndView login(@RequestParam(value = "token",required = false) String token, HttpServletRequest request){
        ModelAndView modelAndView = new ModelAndView();
        HttpSession session = request.getSession();
        if(session.getAttribute("token")==null){
            if(token==null || token.equals("")) {
                modelAndView.setViewName(Constants.LOGIN);
            }else{
                boolean is_login = userService.login(token);
                if (is_login) {
                    session.setAttribute("token", token);
                    modelAndView.setViewName(Constants.INDEX);
                } else {
                    modelAndView.setViewName(Constants.LOGIN);
                    modelAndView.addObject("loginFail", "登录失败，请重试");
                }
            }
        }else{
            modelAndView.setViewName(Constants.INDEX);
        }
        return modelAndView;
    }
}
