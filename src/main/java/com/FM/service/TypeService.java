package com.FM.service;

import com.FM.dao.TCDao;
import com.FM.domain.Course;
import com.FM.domain.Type;
import com.FM.utils.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class TypeService {
    @Autowired
    TCDao tcDao;

    public List<Type> getTypes(Integer parentId){
        List<Type> list =  tcDao.getTypes(parentId);
        return list;
    }

    public Integer addType(String type,Integer level,Integer parentType){
        if(level==1)
            parentType=0;
        return tcDao.addType(type,level,parentType);
    }

    public List<Type> getAll2Types(){
        return tcDao.getAll2Types();
    }

    public boolean deleteType(String typeId){
        Type type = tcDao.getType(Integer.parseInt(typeId));
        return tcDao.deleteType(type);
    }

    public String getIconLocation(Integer typeId){
        return tcDao.getIconLocation(typeId);
    }

    public boolean uploadIcon( String fileName,String typeId){
        Integer Id = Integer.parseInt(typeId);
        return tcDao.uploadIcon(fileName,Id);
    }

    public Integer orderUp(Integer typeId){
        return tcDao.setTypeOrder(typeId,1);
    }

    public Integer orderDown(Integer typeId){
        return tcDao.setTypeOrder(typeId,0);
    }

    public boolean editType(String typeId,String newTypeName){
        Integer tId = Integer.parseInt(typeId);
        return tcDao.editType(tId,newTypeName);
    }

    public Map<String,Type> getNextType(Integer id, Integer signal){
            Map<String,Type> rtTypes = new HashMap<>();
            Type l2Type = getType(id);
            List<Type> l2TypeList = getTypes(l2Type.getParentType());
            Integer i=0;
            for(i=0;i<l2TypeList.size();i++){
                if(l2TypeList.get(i).getId().equals(l2Type.getId()))
                    break;
            }
            while(rtTypes.size()==0) {
                if (i + signal < 0) {
                    rtTypes.put("l1", null);
                    rtTypes.put("l2", null);
                    break;
                } else if (i + signal > (l2TypeList.size() - 1)) {
                    i = -1;
                }
                while (i + signal >= 0 && i + signal <= (l2TypeList.size() - 1)) {
                    i+=signal;
                    Type ll2Type = l2TypeList.get(i);
                    List<Course> courses = tcDao.getCourses(ll2Type.getId());
                    if (courses.size() > 0) {
                        rtTypes.put("l2", ll2Type);
                        rtTypes.put("l1", getType(ll2Type.getParentType()));
                        break;
                    }
                }
            }
            return rtTypes;
    }

    public Type getType(Integer id){

        return tcDao.getType(id);
    }

}
