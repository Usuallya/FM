package com.FM.service;

import com.FM.dao.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    UserDao userDao;
    public boolean login(String token){
        String userId = userDao.selectByToken(token);
        if(userId!=null && (!userId.equals("")))
            return true;
        else
            return false;
    }

    public String ManagementLogin(String userName,String password){
        String userId = userDao.selectByUsername(userName);
        if(userDao.validatePassword(userId,password))
            return userId;
        else
            return null;
    }


}
