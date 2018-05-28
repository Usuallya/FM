package com.FM.dao;

import com.FM.domain.Course;
import com.FM.domain.Type;
import com.FM.utils.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.io.BufferedReader;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class TCDao {
    @Autowired
    private JdbcTemplate jdbcTemplate;
    private final static String courseSQL="SELECT * FROM `course` WHERE `type`=? AND `isdisplay`=1 ORDER BY `order`";
    private final static String typeSQL="SELECT * FROM `types` WHERE `parenttype`=? AND `isdisplay`=1 ORDER BY `order`";
    private final static String addTypeSQL="INSERT INTO `types`(`typename`,`typelevel`,`parenttype`,`iconlocation`,`isdisplay`,`order`)VALUES(?,?,?,?,?,?)";
    private final static String addCourseSQL="INSERT INTO `course`(`coursename`,`type`,`like`,`location`,`order`,`isdisplay`)VALUES(?,0,0,?,0,1)";
    private final static String add2TypeSQL="UPDATE `course` SET `type`=?,`order`=? WHERE `id`=?";
    private final static String existSQL="SELECT count(*) FROM `types` WHERE `typename`=? AND `parenttype`=? AND `isdisplay`=1";
    private final static String deleteCourseSQL="UPDATE `course` SET `isdisplay`=0 WHERE `id`=?";
    private final static String deleteTypeSQL="UPDATE `types` SET `isdisplay`=0 WHERE `id` = ?";
    private final static String deletel2TypeSQL="UPDATE `types` SET `isdisplay`=0 WHERE `parenttype`=?";
    private final static String deletel2CourseSQL="UPDATE `course` SET `type`=0 WHERE `type`=?";
    private final static String uploadIconSQL="UPDATE `types` SET `iconlocation`=? WHERE `id`=?";
    private final static String selectTypeSQL = "SELECT * FROM `types` WHERE `id`=? AND `isdisplay`=1";
    private final static String selectCourseSQL="SELECT * FROM `course` WHERE `id`=? AND `isdisplay`=1";
    private final static String selectIconSQL="SELECT `iconlocation` FROM `types` WHERE `id`=? AND `isdisplay`=1";
    private final static String selectTypeByOrderSQL="SELECT `id` FROM `types` WHERE `parenttype`=? AND `order`=? AND `isdisplay`=1";
    private final static String selectCourseByOrderSQL="SELECT `id` FROM `course` WHERE `type`=? AND `order`=? AND `isdisplay`=1";
    private final static String updateCourseOrderSQL="UPDATE `course` SET `order`=? WHERE `id`=? ";
    private final static String updateTypeOrderSQL="UPDATE `types` SET `order`=? WHERE `id`=?";
    private final static String selectMaxTypeOrderSQL="SELECT MAX(`order`) FROM `types` WHERE `parenttype`=? AND `isdisplay`=1";
    private final static String selectMaxCourseOrderSQL="SELECT MAX(`order`) FROM `course` WHERE `type`=? AND `isdisplay`=1";
    private final static String selectAll2Types="SELECT * FROM `types` WHERE `parenttype`<>0 AND `isdisplay`=1";
    private final static String selectAll1Types="SELECT * FROM `types` WHERE `parenttype`=0 AND `isdisplay`=1 ORDER BY `order` ";
    private final static String selectFirstChildTypeSQL="SELECT * FROM `types` WHERE `parenttype`=? AND `isdisplay`=1 ORDER BY `order`";
    private final static String selectLastChildTypeSQL="SELECT * FROM `types` WHERE `parenttype`=? AND `isdisplay`=1 ORDER BY `order` DESC ";
    private final static String editTypeSQL = "UPDATE `types` SET `typename`=? WHERE `id`=?";
    private final static String uploadData = "INSERT INTO `course`(`type`,`coursename`,`like`,`location`,`order`,`isdisplay`)VALUES(?,?,0,?,?,1)";
    private final static String clearData="UPDATE `course` SET `isdisplay`=0 WHERE `type`=?";
    private final static String selectTypeByName ="SELECT `id` FROM `types` WHERE `typename`=? AND `isdisplay`=1";
    private final static String selectTypeByNameAndParent = "SELECT `id` FROM `types` WHERE `parenttype`=? AND `typename`=? AND `isdisplay`=1";
    public List<Course> getCourses(Integer typeId){
        try{
    List<Course> list = jdbcTemplate.query(courseSQL, new Object[]{typeId}, new RowMapper<Course>() {
        @Override
        public Course mapRow(ResultSet resultSet, int i) throws SQLException {
            Course course = new Course();
            course.setId(resultSet.getInt("id"));
            course.setCourseName(resultSet.getString("coursename"));
            course.setType(resultSet.getInt("type"));
            course.setLocation(resultSet.getString("location"));
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
                course.setLocation(resultSet.getString("location"));
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
                tp.setIconLocation(resultSet.getString("iconlocation"));
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
                    Type tp = new Type();
                    tp.setId(resultSet.getInt("id"));
                    tp.setParentType(resultSet.getInt("parenttype"));
                    tp.setTypeLevel(resultSet.getInt("typelevel"));
                    tp.setOrder(resultSet.getInt("order"));
                    tp.setTypeName(resultSet.getString("typename"));
                    tp.setIconLocation(resultSet.getString("iconlocation"));
                    return tp;
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
                Type tp = new Type();
                tp.setId(resultSet.getInt("id"));
                tp.setParentType(resultSet.getInt("parenttype"));
                tp.setTypeLevel(resultSet.getInt("typelevel"));
                tp.setOrder(resultSet.getInt("order"));
                tp.setTypeName(resultSet.getString("typename"));
                tp.setIconLocation(resultSet.getString("iconlocation"));
                return tp;
            }
        });
        return list;
    }

    public List<Type> getAll1Types(){
        List<Type> list = jdbcTemplate.query(selectAll1Types, new Object[]{}, new RowMapper<Type>() {
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
        return list;
    }

    public Integer addType(String type,Integer level,Integer parent){
        if(0 != jdbcTemplate.queryForObject(existSQL,new Object[]{type,parent},Integer.class))
            return Constants.TYPE_ALREADY_EXISTS;
        Integer order = getMaxTypeOrder(parent)+1;
        Integer rows = jdbcTemplate.update(addTypeSQL,new Object[]{type,level,parent,null, Constants.DISPLAY,order});
        if(rows>0)
            return Constants.ADD_SUCCESS;
        else
            return Constants.ADD_FAIL;
    }

    public Integer addCourse(String courseName,String location)
    {
            if(jdbcTemplate.update(addCourseSQL, new Object[]{courseName, location})>0)
                return Constants.ADD_SUCCESS;
            else
                return Constants.ADD_FAIL;

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
        if(type.getParentType()==0) {
            List<Type> types = getAll2Types();
            for(Type ltype : types){
                if(ltype.getParentType()==type.getId()){
                    jdbcTemplate.update(deletel2CourseSQL,new Object[]{ltype.getId()});
                }
            }
            jdbcTemplate.update(deletel2TypeSQL,new Object[]{type.getId()});
        }else{
            jdbcTemplate.update(deletel2CourseSQL,new Object[]{type.getId()});
        }
        Integer rows=jdbcTemplate.update(deleteTypeSQL,new Object[]{type.getId()});

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

    public boolean editType(Integer typeId, String newTypeName){
        if(jdbcTemplate.update(editTypeSQL,new Object[]{newTypeName,typeId})>0)
            return true;
        else
            return false;
    }

    public Integer setCourseOrder(Integer courseId,Integer operation){
        Integer otherId = 0;
        Course course = getCourse(courseId);
        Integer maxOrder = getMaxCourseOrder(course.getType());
        if(operation == 1 && course.getOrder()!=maxOrder){
            while(otherId==0 && course.getOrder()<maxOrder+1) {
                course.setOrder(course.getOrder() + 1);
                otherId = jdbcTemplate.queryForObject(selectCourseByOrderSQL, new Object[]{course.getType(), course.getOrder()}, Integer.class);
                jdbcTemplate.update(updateCourseOrderSQL, new Object[]{course.getOrder(), course.getId()});
                jdbcTemplate.update(updateCourseOrderSQL, new Object[]{course.getOrder() - 1, otherId});
            }
        }else if(operation==1 && course.getOrder()==maxOrder)
        {
            otherId = 0;
        }
        if(operation == 0 && course.getOrder()!=1){
            while(otherId==0 && course.getOrder()>0) {
                course.setOrder(course.getOrder() - 1);
                otherId = jdbcTemplate.queryForObject(selectCourseByOrderSQL, new Object[]{course.getType(), course.getOrder()}, Integer.class);
                jdbcTemplate.update(updateCourseOrderSQL, new Object[]{course.getOrder(), course.getId()});
                jdbcTemplate.update(updateCourseOrderSQL, new Object[]{course.getOrder() + 1, otherId});
            }
        }else if(operation == 0 && course.getOrder()==1){
            otherId = 0;
        }
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
            while(otherId==0 && type.getOrder()<maxOrder+1) {
                type.setOrder(type.getOrder() + 1);
                otherId = jdbcTemplate.queryForObject(selectTypeByOrderSQL, new Object[]{type.getParentType(), type.getOrder()}, Integer.class);
                jdbcTemplate.update(updateTypeOrderSQL, new Object[]{type.getOrder(), type.getId()});
                jdbcTemplate.update(updateTypeOrderSQL, new Object[]{type.getOrder() - 1, otherId});
            }
        }else if(operation == 1 && type.getOrder()==maxOrder){
            otherId = 0;
        }
        if(operation == 0 && type.getOrder()!=1){
            while(otherId==0 && type.getOrder()>0) {
                type.setOrder(type.getOrder() - 1);
                otherId = jdbcTemplate.queryForObject(selectTypeByOrderSQL, new Object[]{type.getParentType(), type.getOrder()}, Integer.class);
                jdbcTemplate.update(updateTypeOrderSQL, new Object[]{type.getOrder(), type.getId()});
                jdbcTemplate.update(updateTypeOrderSQL, new Object[]{type.getOrder() + 1, otherId});
            }
        }else if(operation == 1 && type.getOrder()==1){
            otherId = 0;
        }
        System.out.println("原ID："+ type.getId()+"新id："+otherId);
        return otherId;
    }

    public Integer getMaxTypeOrder(Integer parentType){
        Integer order = jdbcTemplate.queryForObject(selectMaxTypeOrderSQL,new Object[]{parentType},Integer.class);
        order = order==null ? 0 : order;
        return order;
    }

    public Integer getMaxCourseOrder(Integer type){
        Integer order = jdbcTemplate.queryForObject(selectMaxCourseOrderSQL,new Object[]{type},Integer.class);
        order = order==null ? 0 : order;
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

    public boolean uploadData(BufferedReader br) throws Exception{
        int count = 0;
        String line = null;
        String l1Name="";
        String l2Name="";
        Integer l1Id=null;
        Integer l2Id=null;
        int order=1;
        line = br.readLine();
        if(line==null)
            return false;
        while ((line = br.readLine()) != null) {
            count += 1;
            String[] message = line.split(",");
            if (message.length != 3) {
                continue;
            }
            if(!l1Name.equals(message[0]) || !l2Name.equals(message[1]))
            {
                l1Name=message[0];
                l2Name=message[1];
                l1Id = jdbcTemplate.queryForObject(selectTypeByName,new Object[]{l1Name},Integer.class);
                if(l1Id==null || l1Id.equals(0))
                    return false;
                l2Id = jdbcTemplate.queryForObject(selectTypeByNameAndParent,new Object[]{l1Id,l2Name},Integer.class);
                jdbcTemplate.update(clearData,new Object[]{l2Id});
                order=1;
            }
            if(l2Id==null || l2Id.equals(0))
                return false;
            String musicName = message[2].substring(message[2].lastIndexOf("/")+1);
            int flag = jdbcTemplate.update(uploadData,new Object[]{l2Id,musicName,message[2],order});
            order++;
            System.out.println(flag+"  "+l2Id+"  "+musicName+"  "+message[2]);
            if(flag==0)
                return false;
        }
        return true;
    }
}
