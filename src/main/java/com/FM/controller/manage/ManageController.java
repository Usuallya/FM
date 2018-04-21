package com.FM.controller.manage;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Controller
public class ManageController {
    @RequestMapping("/fileupload")
    public String uploadCourse(@RequestParam("file") MultipartFile file){
        return null;
    }
}
