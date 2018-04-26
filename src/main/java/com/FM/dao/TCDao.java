package com.FM.dao;

import com.FM.domain.Course;
import com.FM.domain.Type;
import com.FM.utils.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class TCDao {
    @Autowired
    private JdbcTemplate jdbcTemplate;
    private final static String courseSQL="SELECT `coursename` FROM `course` WHERE `type`=? ORDER BY `order`";
    private final static String typeSQL="SELECT `typename` FROM `types` WHERE `parentId`=?";
    private final static String addTypeSQL="INSERT INTO `types`(`typename`,`typelevel`,`parenttype`,`iconlocation`,`isdisplay`)VALUES(?,?,?,?,?)";
    private final static String existSQL="SELECT count(*) FROM `types` WHERE `typename`=? AND `parenttype`=?";
    private final static String selectParentTypeSQL="SELECT `id` FROM `types` WHERE `id`=?";
    private final static String deleteTypeSQL="UPDATE `types` SET `isdisplay`=0 WHERE `typename` = ? AND `parenttype`=?";
    private final static String selectTypeByOrderSQL = "SELECT `id` FROM `types` WHERE `order`=?";
    private final static String selectTypeSQL = "SELECT `id` FROM `types` WHERE `typename`=? AND `parenttype` =?";
    private final static String selectCourseSQL="SELECT `id` FROM `course` WHERE ";
    private final static String setTypeOrderSQL = "UPDATE `types` SET ``";
    private final static String setCourseOrderSQL="UPDATE `` SET ``";

    public List<String> getCourse(Integer typeId){
    List<String> list = jdbcTemplate.query(courseSQL, new Object[]{typeId}, new RowMapper<String>() {
        @Override
        public String mapRow(ResultSet resultSet, int i) throws SQLException {
            String course = resultSet.getString("course");
            return course;
        }
    });
    return list;
    }

    public List<String> getTypes(Integer parentId){
        List<String> list = jdbcTemplate.query(typeSQL, new Object[]{parentId}, new RowMapper<String>() {
            @Override
            public String mapRow(ResultSet resultSet, int i) throws SQLException {
                String type = resultSet.getString("type");
                return type;
            }
        });
        return list;
    }

    public Integer addType(String type,Integer level,Integer parent){
        //首先查询当前类型名是否有重复
        if(0 == jdbcTemplate.queryForObject(existSQL,new Object[]{type,parent},Integer.class))
            return Constants.TYPE_ALREADY_EXISTS;
        //无重复类型名，添加类型
        Integer rows = jdbcTemplate.update(addTypeSQL,new Object[]{type,level,parent,"", Constants.DISPLAY});
        if(rows>0)
            return Constants.ADD_TYPE_SUCCESS;
        else
            return Constants.ADD_TYPE_FAIL;
    }

    public boolean deleteType(Type type){
        Integer rows=jdbcTemplate.update(deleteTypeSQL,new Object[]{type.getTypeName(),type.getParentType()});
        if(rows>0)
            return true;
        else
            return false;
    }

    public Integer setCourseOrder(Course course){

        return null;
    }
    public Integer setTypeOrder(Type type){
        return null;
    }
}
