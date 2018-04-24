package com.FM.controller.manage;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;

@Controller
@SessionAttributes("userId")
public class ManageController {
    @RequestMapping("/courseUpload")
    public String uploadCourse(@RequestParam("course") MultipartFile file){

        return null;
    }
    @RequestMapping("/iconUpload")
    public String uploadIcon(@RequestParam("icon") MultipartFile file){
        return null;
    }

    private boolean saveFile(MultipartFile file, String path) {
        // 判断文件是否为空
        Long time = System.currentTimeMillis();
        if (!file.isEmpty()) {
            try {
                File filepath = new File(path);
                if (!filepath.exists())
                    filepath.mkdirs();
                // 文件保存路径
                String savePath = path + time.toString();
                // 转存文件
                file.transferTo(new File(savePath));
                return true;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return false;
    }

}
