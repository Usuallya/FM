package com.FM.service;

import com.FM.dao.UserDao;
import com.FM.domain.User;
import com.FM.utils.ValidateToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    UserDao userDao;
    public boolean login(String token){
        if(!ValidateToken.validateToken(token))
            return false;
        String userId = userDao.selectByToken(token);
        if(userId!=null && (!userId.equals(""))){
            return true;
        }
        return false;
    }

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
