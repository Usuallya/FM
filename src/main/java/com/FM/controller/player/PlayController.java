package com.FM.controller.player;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping("/")
public class PlayController {

    @RequestMapping("/playCourse/{courseName}")
    public MultipartFile playCourse(@PathVariable(value="courseName") String courseName){
        return null;
    }
}
