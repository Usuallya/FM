package com.FM.controller.manage;

import com.FM.service.TypeService;
import com.FM.utils.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@SessionAttributes("userId")
@RequestMapping("/type")
public class TypeController {
    @Autowired
    private TypeService typeService;
    @RequestMapping("/addType")
    @ResponseBody
    public String addType(@RequestParam("type") String type, @RequestParam("level") Integer level, @RequestParam("parent") Integer parent){
        Integer flag = typeService.addType(type,level,parent);
        if(flag==Constants.ADD_TYPE_SUCCESS)
            return Constants.SUCCESS;
        else if(flag == Constants.TYPE_ALREADY_EXISTS)
            return Constants.EXISTS;
        else
            return Constants.FAIL;
    }
    @RequestMapping("/deleteType")
    @ResponseBody
    public String deleteType(@RequestParam("type") String typeName,@RequestParam("parent") Integer parent){
        if(typeService.deleteType(typeName,parent))
            return Constants.SUCCESS;
        else
            return Constants.FAIL;
    }
    @RequestMapping("/order/{operation}")
    @ResponseBody
    public Integer typeOrder(@PathVariable("operation") String operation, @RequestParam("typeId")Integer typeId){
        Integer order =0;
        if(operation.equals("up")){
            order = typeService.orderUp(typeId);
        }else if(operation.equals("down")){
            order = typeService.orderDown(typeId);
        }
        return order;
    }
}
