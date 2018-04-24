package com.FM.dao;

import com.FM.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;


@Repository
public class UserDao {
    @Autowired
    JdbcTemplate jdbcTemplate;
    private static String loginToken="SELECT `id` FROM `user` WHERE `wechattoken`=? ";
    private static String loginUsername="SELECT `id` FROM `manager` WHERE `username`=?";
    private static String validatePassword="SELECT `id` FROM `manager` WHERE `id`=? AND `password`=?";
    private static String selectUser="SELECT * FROM `manager` WHERE `id`=?";
    public String selectByToken(String token){
        String userId = jdbcTemplate.queryForObject(loginToken,new Object[]{token},String.class);
        if(userId!=null && !userId.equals(""))
            return userId;
        else
            return null;
    }

    public String selectByUsername(String userName){
        String userId;
        try {
            userId = jdbcTemplate.queryForObject(loginUsername, new Object[]{userName}, String.class);
        }catch(EmptyResultDataAccessException e){
            userId=null;
        }
            return userId;
    }

    public boolean validatePassword(String userId,String password){
        String user;
        try {
            user = jdbcTemplate.queryForObject(validatePassword, new Object[]{userId, password}, String.class);
        }catch(EmptyResultDataAccessException e){
            return false;
        }
            if(user!=null && !user.equals(""))
                return true;
            else
                return false;
    }
    public User getManagerUser(String userId){
        User user = new User();
        try {
            jdbcTemplate.query(selectUser, new Object[]{userId}, new RowCallbackHandler() {
                @Override
                public void processRow(ResultSet resultSet) throws SQLException {
                    user.setUserId(userId);
                    user.setUserName(resultSet.getString("username"));
                    user.setPassword("");
                }
            });
        }catch(EmptyResultDataAccessException e){
            return null;
        }
        return user;
    }
}
