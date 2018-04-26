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

    public boolean addType(String type,Integer level,Integer parentType){
        return tcDao.addType(type,level,parentType);
    }
    public boolean deleteType(String typeName,Integer parentType){
        Type type = new Type();
        type.setTypeName(typeName);
        type.setParentType(parentType);
        return tcDao.deleteType(type);
    }
    public Integer orderUp(Integer typeId){
        Type type = new Type();
        type.setId(typeId);
        return tcDao.setTypeOrder(type);
    }
    public Integer orderDown(Integer typeId){
        Type type = new Type();
        type.setId(typeId);
        return tcDao.setTypeOrder(type);
    }

}
