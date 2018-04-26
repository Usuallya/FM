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
    private final static String courseSQL="SELECT `*` FROM `course` WHERE `type`=? ORDER BY `order`";
    private final static String typeSQL="SELECT `*` FROM `types` WHERE `parenttype`=?";
    private final static String addTypeSQL="INSERT INTO `types`(`typename`,`typelevel`,`parenttype`,`iconlocation`,`isdisplay`)VALUES(?,?,?,?,?)";
    private final static String existSQL="SELECT count(*) FROM `types` WHERE `typename`=? AND `parenttype`=?";
    private final static String selectParentTypeSQL="SELECT `id` FROM `types` WHERE `id`=?";
    private final static String deleteTypeSQL="UPDATE `types` SET `isdisplay`=0 WHERE `typename` = ? AND `parenttype`=?";
    private final static String selectTypeByOrderSQL = "SELECT `id` FROM `types` WHERE `order`=?";
    private final static String selectTypeSQL = "SELECT `id` FROM `types` WHERE `typename`=? AND `parenttype` =?";
    private final static String selectCourseSQL="SELECT `id` FROM `course` WHERE ";
    private final static String setTypeOrderSQL = "UPDATE `types` SET ``";
    private final static String setCourseOrderSQL="UPDATE `` SET ``";

    public List<Course> getCourse(Integer typeId){
    List<Course> list = jdbcTemplate.query(courseSQL, new Object[]{typeId}, new RowMapper<Course>() {
        @Override
        public Course mapRow(ResultSet resultSet, int i) throws SQLException {
            Course course = new Course();
            course.setId(resultSet.getInt("id"));
            course.setCourseName(resultSet.getString("coursename"));
            course.setType(resultSet.getInt("type"));
            return course;
        }
    });
    return list;
    }

    public List<Type> getTypes(Integer parentId){
        List<Type> type = jdbcTemplate.query(typeSQL, new Object[]{parentId}, new RowMapper<Type>() {
            @Override
            public Type mapRow(ResultSet resultSet, int i) throws SQLException {
                Type type = new Type();
                type.setId(resultSet.getInt("id"));
                type.setTypeName(resultSet.getString("typename"));
                type.setTypeLevel(resultSet.getInt("typelevel"));
                type.setParentType(resultSet.getInt("parenttype"));
                return type;
            }
        });
        return type;
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
