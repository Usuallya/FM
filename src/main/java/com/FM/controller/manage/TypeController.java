package com.FM.controller.manage;

import com.FM.domain.Type;
import com.FM.service.TypeService;
import com.FM.utils.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@SessionAttributes("userId")
@RequestMapping("/Management/type")
public class TypeController {
    @Autowired
    private TypeService typeService;


    @RequestMapping("/addTypePage")
    public ModelAndView addTypePage(){
        ModelAndView modelAndView = new ModelAndView(Constants.ADD_TYPE_PATH);
        List<Type> initL1Types = typeService.getTypes(0);
        List<Type> initL2Types = typeService.getTypes(initL1Types.get(0).getId());
        modelAndView.addObject("L1Types",initL1Types);
        modelAndView.addObject("L2Types",initL2Types);
        return modelAndView;
    }

    @RequestMapping("/addType")
    @ResponseBody
    public Map<String,Object> addType(@RequestParam(value = "type",required = false) String type, @RequestParam(value = "level",required = false) Integer level, @RequestParam(value = "parent",required = false) Integer parent){
        Integer flag = 0;
        Map<String,Object> map = new HashMap<>();
        if(type!=null && level!=null && parent!=null){
            flag = typeService.addType(type,level,parent);
        }

        List<Type> initL1Types = typeService.getTypes(0);
        List<Type> initL2Types = typeService.getTypes(initL1Types.get(0).getId());

        map.put("L1Types",initL1Types);
        map.put("L2Types",initL2Types);
        map.put("flag",flag);

        return map;
    }


    @RequestMapping("/icon")
    @ResponseBody
    public ModelAndView getIconPage(){
        String imageLocation=null;
        ModelAndView modelAndView = new ModelAndView(Constants.ICON_PATH);
        List<Type> initL2Types = typeService.getAll2Types();
        if(initL2Types!=null)
         imageLocation= typeService.getIconLocation(initL2Types.get(0).getId());
        modelAndView.addObject("L2Types",initL2Types);
        modelAndView.addObject("l2fIconLocation",imageLocation);
        return modelAndView;
    }

    @RequestMapping("/getTypes")
    @ResponseBody
    public List<Type> getTypes(@RequestParam("parentType") String sparentType){
        Integer parentType = Integer.parseInt(sparentType);
        return typeService.getTypes(parentType);
    }

    @RequestMapping("/deleteType")
    @ResponseBody
    public String deleteType(@RequestParam("type") String typeName,@RequestParam("parent") Integer parent){
        if(typeService.deleteType(typeName,parent))
            return Constants.SUCCESS;
        else
            return Constants.FAIL;
    }

    @RequestMapping("/getIconLocation")
    @ResponseBody
    public String getIconLocation(@RequestParam("typeId") String stypeId){
        Integer typeId = Integer.parseInt(stypeId);
        String imageLocation= typeService.getIconLocation(typeId);
        return imageLocation;
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
