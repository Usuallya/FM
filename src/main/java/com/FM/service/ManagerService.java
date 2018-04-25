package com.FM.service;

import com.FM.dao.UserDao;
import com.FM.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ManagerService {

    @Autowired
    UserDao userDao;

    public String ManagertLogin(String userName,String password){
        String userId = userDao.selectByUsername(userName);
        if(userDao.validatePassword(userId,password))
            return userId;
        else
            return null;
    }

    public User getManagerUser(String userId){
        return userDao.getManagerUser(userId);
    }

}
