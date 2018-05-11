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
        List<Type> l1TypeList = tcDao.getAll1Types();
        Type l2Type = tcDao.getType(id);
        List<Type> l2TypeList = tcDao.getTypes(l2Type.getParentType());
        Map<String,Type> rtTypes = new HashMap<>();
        Integer i=0,j=0,lasti=0,lastj=0;
        for(i=0;i<l1TypeList.size();i++){
            if(l1TypeList.get(i).getId().equals(l2Type.getParentType())) {
                lasti = i;
                break;
            }
        }
        for(j=0;j<l2TypeList.size();j++){
            if(l2TypeList.get(j).getId().equals(l2Type.getId())) {
                lastj=j;
                break;
            }
        }
        while(true){
            while(true)
            {
                if(j+signal>=0 && j+signal <=l2TypeList.size()-1) {
                    j += signal;
                    Type ll2Type = l2TypeList.get(j);
                    if(tcDao.getCourses(ll2Type.getId()).size()>0){
                        rtTypes.put("l2",ll2Type);
                        break;
                    }
                }else{
                    break;
                }
            }
            if(rtTypes.size()==0) {
                if (i + signal >= 0 && i + signal <= l1TypeList.size() - 1) {
                    i += signal;
                    l2TypeList = tcDao.getTypes(l1TypeList.get(i).getId());
                    if (signal == 1) {
                        j = -1;
                    } else if (signal == -1) {
                        j = l2TypeList.size();
                    }
                } else {
                    if (i + signal == -1) {
                        rtTypes.put("l1", null);
                        rtTypes.put("l2", null);
                        break;
                    } else if (i + signal == l1TypeList.size()) {
                        i = 0;
                        j = -1;
                        l2TypeList = tcDao.getTypes(l1TypeList.get(i).getId());
                    }
                }
            }else
            {
                rtTypes.put("l1", l1TypeList.get(i));
                break;

            }
            if(i.equals(lasti) && j.equals(lastj))
            {
                rtTypes.put("l1",null);
                rtTypes.put("l2",null);
                break;
            }
        }

        return rtTypes;

    }

    public Type getType(Integer id){

        return tcDao.getType(id);
    }

}
