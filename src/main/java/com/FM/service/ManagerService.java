package com.FM.service;

import com.FM.dao.TCDao;
import com.FM.dao.UserDao;
import com.FM.domain.Manager;
import com.FM.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;

@Service
public class ManagerService {

    @Autowired
    UserDao userDao;

    @Autowired
    TCDao tcDao;
    public Integer ManagerLogin(String userName,String password){
        Integer managerId = userDao.selectByUsername(userName);
        if(userDao.validatePassword(managerId,password))
            return managerId;
        else
            return null;
    }

    public Manager getManagerUser(Integer managerId){
        return userDao.getManagerUser(managerId);
    }

    public boolean uploadData(BufferedReader br){
        try{
            if(!tcDao.uploadData(br))
                return false;
        }catch(Exception e){
            return false;
        }
        return true;
    }

}
