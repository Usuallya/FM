package com.FM.service;

import com.FM.dao.UserDao;
import com.FM.domain.Manager;
import com.FM.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ManagerService {

    @Autowired
    UserDao userDao;

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

}
