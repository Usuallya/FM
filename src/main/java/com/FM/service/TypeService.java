package com.FM.service;

import com.FM.dao.TCDao;
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

    public boolean deleteType(String typeName,Integer parentType){
        Type type = new Type();
        type.setTypeName(typeName);
        type.setParentType(parentType);
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



    public Map<String,Type> getNextType(Integer id, Integer signal){
        List<Type> types = tcDao.getAll2Types();
        Integer maxOrder=null;
        Type l2Type=null;
        Map<String,Type> rtTypes = new HashMap<>();
        for(Type type : types){
            if(type.getId()==id){
                l2Type=type;
                maxOrder = tcDao.getMaxTypeOrder(l2Type.getParentType());
                break;
            }
        }
        if(signal == 1){
            if(l2Type.getOrder()+1<maxOrder){
                for(Type type : types){
                    if(type.getParentType()==l2Type.getParentType() && type.getOrder()==l2Type.getOrder()+1){
                        rtTypes.put("l2",type);
                        rtTypes.put("l1",tcDao.getType(type.getParentType()));
                        break;
                    }
                }
            }else{
                //找到下一个一级分类的第一个二级分类
                Type l1Type = getType(l2Type.getParentType());
                Integer l1MaxOrder = tcDao.getMaxTypeOrder(l1Type.getParentType());
                List<Type> l1TypeList = tcDao.getAll1Types();
                if(l1Type.getOrder()<l1MaxOrder) {
                    for (Type ll1Type : l1TypeList) {
                        if (ll1Type.getOrder() == l1Type.getOrder() + 1) {
                            rtTypes.put("l1", ll1Type);
                            rtTypes.put("l2",tcDao.getFirstChildType(ll1Type));
                        }
                    }
                }else {
                    rtTypes.put("l1", l1TypeList.get(0));
                    rtTypes.put("l2",tcDao.getFirstChildType(l1TypeList.get(0)));
                }
            }
        }else if(signal == -1){
            if(l2Type.getOrder()-1>0){
                for(Type type : types){
                    if(type.getParentType()==l2Type.getParentType() && type.getOrder()==l2Type.getOrder()-1){
                        rtTypes.put("l2",type);
                        rtTypes.put("l1",tcDao.getType(type.getParentType()));
                        break;
                    }
                }
            }else{
                //找到上一个一级分类的最后一个二级分类
                Type l1Type = getType(l2Type.getParentType());
                List<Type> l1TypeList = tcDao.getAll1Types();
                if(l1Type.getOrder()>1) {
                    for (Type ll1Type : l1TypeList) {
                        if (ll1Type.getOrder() == l1Type.getOrder() -1) {
                            rtTypes.put("l1", ll1Type);
                            rtTypes.put("l2",tcDao.getLastChildType(ll1Type));
                        }
                    }
                }else {
                    rtTypes.put("l1", l1TypeList.get(l1TypeList.size()-1));
                    rtTypes.put("l2",tcDao.getLastChildType(l1TypeList.get(l1TypeList.size()-1)));
                }
            }
        }
        return rtTypes;
    }

    public Type getType(Integer id){
        return tcDao.getType(id);
    }

}
