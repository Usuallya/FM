package com.FM.service;

import com.FM.dao.TCDao;
import com.FM.domain.Type;
import com.FM.utils.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TypeService {
    @Autowired
    TCDao tcDao;


    public List<String> getTypes(Integer parentId){
        List<String> list =  tcDao.getTypes(parentId);
        return list;
    }

    public boolean addType(String type,Integer level,String parent){
        Integer parentType = tcDao.getParentTypeId(parent);
        return tcDao.addType(type,level,parentType);
    }
    public boolean deleteType(String typeName,String parent){
        Type type = new Type();
        type.setTypeName(typeName);
        if(parent.equals(""))
            type.setParentType(0);
        else{
            Integer parentType = tcDao.getParentTypeId(parent);
            type.setParentType(parentType);
        }
        if(parent.equals(""))
            return tcDao.deleteType(type);
        else
            return tcDao.deleteType(type);
    }
    public Integer orderUp(Integer typeId,Integer order){
        Type type = new Type();
        type.setId(typeId);
        return tcDao.setTypeOrder(type,order);
    }
    public Integer orderDown(String typeName,String parentName,Integer order){
        Type type = new Type();
        type.setTypeName(typeName);
        if(parentName.equals(""))
            type.setParentType(0);
        else
            type.setParentType(tcDao.getParentTypeId(parentName));
        return tcDao.setTypeOrder(type,order);
    }

}
