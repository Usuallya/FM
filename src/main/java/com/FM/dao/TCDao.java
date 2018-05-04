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
    private final static String courseSQL="SELECT * FROM `course` WHERE `type`=? ORDER BY `order`";
    private final static String typeSQL="SELECT * FROM `types` WHERE `parenttype`=? ORDER BY `order`";
    private final static String addTypeSQL="INSERT INTO `types`(`typename`,`typelevel`,`parenttype`,`iconlocation`,`isdisplay`,`order`)VALUES(?,?,?,?,?,?)";
    private final static String addCourseSQL="INSERT INTO `course`(`coursename`,`type`,`like`,`location`,`order`,`isdisplay`)VALUES(?,0,?,?,?,1)";
    private final static String add2TypeSQL="UPDATE `course` SET `type`=?,`order`=? WHERE `id`=?";
    private final static String existSQL="SELECT count(*) FROM `types` WHERE `typename`=? AND `parenttype`=?";
    private final static String deleteCourseSQL="UPDATE `course` SET `isdisplay`=0 WHERE `id`=?";
    private final static String deleteTypeSQL="UPDATE `types` SET `isdisplay`=0 WHERE `typename` = ? AND `parenttype`=?";
    private final static String uploadIconSQL="UPDATE `types` SET `iconlocation`=? WHERE `id`=?";
    private final static String selectTypeSQL = "SELECT * FROM `types` WHERE `id`=?";
    private final static String selectCourseSQL="SELECT `*` FROM `course` WHERE `id`=?";
    private final static String selectIconSQL="SELECT `iconlocation` FROM `types` WHERE `id`=?";
    private final static String selectTypeByOrderSQL="SELECT `id` FROM `types` WHERE `parenttype`=? AND `order`=?";
    private final static String selectCourseByOrderSQL="SELECT `id` FROM `course` WHERE `type`=? AND `order`=?";
    private final static String updateCourseOrderSQL="UPDATE `course` SET `order`=? WHERE `id`=? ";
    private final static String updateTypeOrderSQL="UPDATE `types` SET `order`=? WHERE `id`=?";
    private final static String selectMaxTypeOrderSQL="SELECT MAX(`order`) FROM `types` WHERE `parenttype`=?";
    private final static String selectMaxCourseOrderSQL="SELECT MAX(`order`) FROM `course` WHERE `type`=?";
    private final static String selectAll2Types="SELECT * FROM `types` WHERE `parenttype`<>0 ORDER BY `order`";
    private final static String selectAll1Types="SELECT * FROM `types` WHERE `parenttype`=0 ORDER BY `order`";
    private final static String selectFirstChildTypeSQL="SELECT * FROM `types` WHERE `parenttype`=? ORDER BY `order`";
    private final static String selectLastChildTypeSQL="SELECT * FROM `types` WHERE `parenttype`=? ORDER BY `order` DESC ";
    public List<Course> getCourses(Integer typeId){
        try{
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
        }catch(Exception e){
            return null;
        }
    }

    public Course getCourse(Integer courseId){
        Course course = jdbcTemplate.queryForObject(selectCourseSQL, new Object[]{courseId}, new RowMapper<Course>() {
            @Override
            public Course mapRow(ResultSet resultSet, int i) throws SQLException {
                Course course = new Course();
                course.setId(resultSet.getInt("id"));
                course.setType(resultSet.getInt("type"));
                course.setOrder(resultSet.getInt("order"));
                course.setCourseName(resultSet.getString("coursename"));
                return course;
            }
        });
        return course;
    }
    public Type getType(Integer typeId){
        Type type = jdbcTemplate.queryForObject(selectTypeSQL, new Object[]{typeId}, new RowMapper<Type>() {
            @Override
            public Type mapRow(ResultSet resultSet, int i) throws SQLException {
                Type tp = new Type();
                tp.setId(resultSet.getInt("id"));
                tp.setParentType(resultSet.getInt("parenttype"));
                tp.setTypeLevel(resultSet.getInt("typelevel"));
                tp.setOrder(resultSet.getInt("order"));
                tp.setTypeName(resultSet.getString("typename"));
                return tp;
            }
        });
        return type;
    }

    public List<Type> getTypes(Integer parentId){
        try {
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
        }catch(Exception e){
            return null;
        }

    }

    public List<Type> getAll2Types(){
        List<Type> list = jdbcTemplate.query(selectAll2Types, new Object[]{}, new RowMapper<Type>() {
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
        return list;
    }

    public List<Type> getAll1Types(){
        List<Type> list = jdbcTemplate.query(selectAll1Types, new Object[]{}, new RowMapper<Type>() {
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
        return list;
    }

    public Integer addType(String type,Integer level,Integer parent){
        //首先查询当前类型名是否有重复
        if(0 != jdbcTemplate.queryForObject(existSQL,new Object[]{type,parent},Integer.class))
            return Constants.TYPE_ALREADY_EXISTS;
        //无重复类型名，添加类型
        Integer order = getMaxTypeOrder(parent)+1;
        Integer rows = jdbcTemplate.update(addTypeSQL,new Object[]{type,level,parent,null, Constants.DISPLAY,order});
        if(rows>0)
            return Constants.ADD_SUCCESS;
        else
            return Constants.ADD_FAIL;
    }

    public Integer addCourse(String courseName,String location)
    {
        try {
            jdbcTemplate.update(addCourseSQL, new Object[]{courseName, null, location, null});
            return Constants.ADD_SUCCESS;
        }catch(Exception e){
            return Constants.ADD_FAIL;
        }
    }

    public boolean add2Type(Integer courseId,Integer typeId){
        Integer order = getMaxCourseOrder(typeId)+1;
        if(jdbcTemplate.update(add2TypeSQL,new Object[]{typeId,order,courseId})>0)
            return true;
        else
            return false;
    }

    public boolean deleteCourse(Integer cId){
        if(jdbcTemplate.update(deleteCourseSQL,new Object[]{cId})>0)
            return true;
        else
            return false;
    }

    public boolean deleteType(Type type){
        Integer rows=jdbcTemplate.update(deleteTypeSQL,new Object[]{type.getTypeName(),type.getParentType()});
        if(rows>0)
            return true;
        else
            return false;
    }

    public String getIconLocation(Integer typeId){
        String location = jdbcTemplate.queryForObject(selectIconSQL,new Object[]{typeId},String.class);
        return location;
    }

    public boolean uploadIcon(String finalPath,Integer typeId){
        if(jdbcTemplate.update(uploadIconSQL,new Object[]{finalPath,typeId})>0)
            return true;
        else
            return false;
    }

    public Integer setCourseOrder(Integer courseId,Integer operation){
        Integer otherId = 0;
        Course course = jdbcTemplate.queryForObject(selectCourseSQL, new Object[]{courseId}, new RowMapper<Course>() {
            @Override
            public Course mapRow(ResultSet resultSet, int i) throws SQLException {
                Course course = new Course();
                course.setId(resultSet.getInt("id"));
                course.setType(resultSet.getInt("type"));
                course.setOrder(resultSet.getInt("order"));
                return course;
            }
        });
        Integer maxOrder = getMaxCourseOrder(course.getType());
        if(operation == 1 && course.getOrder()!=maxOrder){
            course.setOrder(course.getOrder()+1);
            otherId = jdbcTemplate.queryForObject(selectCourseByOrderSQL,new Object[]{course.getType(),course.getOrder()},Integer.class);
            jdbcTemplate.update(updateCourseOrderSQL,new Object[]{course.getOrder(),course.getId()});
            jdbcTemplate.update(updateCourseOrderSQL,new Object[]{course.getOrder()-1,otherId});
        }else if(operation == 0 && course.getOrder()!=1){
            course.setOrder(course.getOrder()-1);
            otherId = jdbcTemplate.queryForObject(selectCourseByOrderSQL,new Object[]{course.getType(),course.getOrder()},Integer.class);
            jdbcTemplate.update(updateCourseOrderSQL,new Object[]{course.getOrder(),course.getId()});
            jdbcTemplate.update(updateCourseOrderSQL,new Object[]{course.getOrder()+1,otherId});
        }
        System.out.println("原ID："+ course.getId()+"新id："+otherId);
        return otherId;
    }

    public Integer setTypeOrder(Integer typeId,Integer operation){
        Integer otherId=0;
        Type type = jdbcTemplate.queryForObject(selectTypeSQL, new Object[]{typeId}, new RowMapper<Type>() {
            @Override
            public Type mapRow(ResultSet resultSet, int i) throws SQLException {
                Type tp = new Type();
                tp.setId(resultSet.getInt("id"));
                tp.setParentType(resultSet.getInt("parenttype"));
                tp.setTypeLevel(resultSet.getInt("typelevel"));
                tp.setOrder(resultSet.getInt("order"));
                return tp;
            }
        });
        Integer maxOrder = getMaxTypeOrder(type.getParentType());
        if(operation == 1 && type.getOrder()!=maxOrder){
            type.setOrder(type.getOrder()+1);
            otherId = jdbcTemplate.queryForObject(selectTypeByOrderSQL,new Object[]{type.getParentType(),type.getOrder()},Integer.class);
            jdbcTemplate.update(updateTypeOrderSQL,new Object[]{type.getOrder(),type.getId()});
            jdbcTemplate.update(updateTypeOrderSQL,new Object[]{type.getOrder()-1,otherId});
        }else if(operation == 0 && type.getOrder()!=1){
            type.setOrder(type.getOrder()-1);
            otherId = jdbcTemplate.queryForObject(updateTypeOrderSQL,new Object[]{type.getParentType(),type.getOrder()},Integer.class);
            jdbcTemplate.update(updateTypeOrderSQL,new Object[]{type.getOrder(),type.getId()});
            jdbcTemplate.update(updateTypeOrderSQL,new Object[]{type.getOrder()+1,otherId});
        }
        System.out.println("原ID："+ type.getId()+"新id："+otherId);
        return otherId;
    }

    public Integer getMaxTypeOrder(Integer parentType){
        Integer order = jdbcTemplate.queryForObject(selectMaxTypeOrderSQL,new Object[]{parentType},Integer.class);
        return order;
    }

    public Integer getMaxCourseOrder(Integer type){
        Integer order = jdbcTemplate.queryForObject(selectMaxCourseOrderSQL,new Object[]{type},Integer.class);
        return order;
    }

    public Type getFirstChildType(Type type){
        List<Type> list = jdbcTemplate.query(selectFirstChildTypeSQL, new Object[]{type.getId()}, new RowMapper<Type>() {
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
        return list.get(0);
    }

    public Type getLastChildType(Type type){
        List<Type> list = jdbcTemplate.query(selectLastChildTypeSQL, new Object[]{type.getId()}, new RowMapper<Type>() {
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
        return list.get(0);
    }

}
