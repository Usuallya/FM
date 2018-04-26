package com.FM.dao;

import com.FM.domain.Manager;
import com.FM.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.stereotype.Repository;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import java.sql.ResultSet;
import java.sql.SQLException;


@Repository
public class UserDao {
    @Autowired
    JdbcTemplate jdbcTemplate;
    private static String loginToken="SELECT `id` FROM `user` WHERE `wechattoken`=? ";
    private static String loginUsername="SELECT `id` FROM `manager` WHERE `username`=?";
    private static String validatePassword="SELECT `id` FROM `manager` WHERE `id`=? AND `password`=?";
    private static String selectManager="SELECT * FROM `manager` WHERE `id`=?";
    public String selectByToken(String token){
        String userId = jdbcTemplate.queryForObject(loginToken,new Object[]{token},String.class);
        if(userId!=null && !userId.equals(""))
            return userId;
        else
            return null;
    }

    public Integer selectByUsername(String userName){
        Integer userId;
        try {
            userId = jdbcTemplate.queryForObject(loginUsername, new Object[]{userName}, Integer.class);
        }catch(EmptyResultDataAccessException e){
            userId=null;
        }
            return userId;
    }

    public boolean validatePassword(Integer managerId,String password){
        String user;
        try {
            user = jdbcTemplate.queryForObject(validatePassword, new Object[]{managerId, password}, String.class);
        }catch(EmptyResultDataAccessException e){
            return false;
        }
            if(user!=null && !user.equals(""))
                return true;
            else
                return false;
    }
    public Manager getManagerUser(final Integer managerId){
        final Manager manager = new Manager();
        try {
            jdbcTemplate.query(selectManager, new Object[]{managerId}, new RowCallbackHandler() {
                @Override
                public void processRow(ResultSet resultSet) throws SQLException {
                    manager.setId(managerId);
                    manager.setUserName(resultSet.getString("username"));
                    manager.setPassword("");
                }
            });
        }catch(EmptyResultDataAccessException e){
            return null;
        }
        return manager;
    }
}
