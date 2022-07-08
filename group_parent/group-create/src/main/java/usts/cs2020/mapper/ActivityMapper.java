package usts.cs2020.mapper;

import org.apache.ibatis.annotations.*;
import org.springframework.web.bind.annotation.PathVariable;
import usts.cs2020.entity.Activity;
import usts.cs2020.entity.ActivityUser;
import usts.cs2020.entity.User;

import java.util.List;

@Mapper
public interface ActivityMapper {

    @Select("select * from activity where head=#{head}")
    @Results({
            @Result(id=true,property = "id",column = "id"),
            @Result(property = "head",column = "head"),
            @Result(property = "activityname",column = "activityname"),
            @Result(property = "time",column = "time"),
            @Result(property = "startplace",column = "startplace"),
            @Result(property = "content",column = "content"),
            @Result(property = "number",column = "number"),
            @Result(property = "price",column = "price"),
            @Result(property = "count",column = "activityname",javaType = Integer.class,one = @One(select = "usts.cs2020.mapper.ActivityMapper.countmember"))
    })
    public List<Activity> findAll(String head);

    @Insert("insert into activity values(#{id},#{head},#{activityname},#{time},#{startplace},#{content},#{number},#{price})")
    public void add(Activity activity);

    @Select("select * from activity where id=#{id}")
    public Activity findById(int id);

    @Select("select * from activityuser where head=#{head}")
    public List<ActivityUser> check(String head);

    @Delete("delete from activityuser where oid=#{oid}")
    public void deletetocheck(int oid);

    @Select("select * from activityuser where oid=#{oid}")
    public ActivityUser findsuccesscheck(int oid);
    @Insert("insert into successcheck values(#{oid},#{username},#{id},#{activityname},#{tprice},#{num},0)")
    public void addsuccesscheck(ActivityUser activityUser);

    @Select("select count(oid) from successcheck where activityname=#{activityname}")
    public int countmember(String activityname);

    @Select("select * from activity where activityname=#{activityname}")
    public Activity findByName(String activityname);

    @Insert("insert into cofirmactivity values(#{id},#{activityname},#{head},#{time},#{startplace},#{content},#{number},#{price},0)")
    public void addToConfirm(Activity activity);

    @Delete("delete from activity where activityname=#{activityname}")
    public void deleteToConfirm(String activityname);

    @Select("select * from cofirmactivity where head=#{head}")
    public List<Activity> findAllConfirm(String head);

    @Select("select * from  successcheck where activityname=#{activityname}")
    @Results({
            @Result(id=true,property = "id",column = "id"),
            @Result(property = "username",column = "username"),
            @Result(property = "phoneNum",column = "username",javaType = String.class,one=@One(select = "usts.cs2020.mapper.ActivityMapper.findPhoneNum")),
            @Result(property = "email",column = "username",javaType = String.class,one=@One(select="usts.cs2020.mapper.ActivityMapper.findEmail"))
    })
    public List<ActivityUser> details(String activityname);

    @Select("select phoneNum from users where username=#{username}")
    public String findPhoneNum(String username);

    @Select("select email from users where username=#{username}")
    public String findEmail(String username);

    @Update("update cofirmactivity set addprice=#{addprice} where activityname=#{activityname}")
    public void addprice(Double addprice,String activityname);

    @Select("select * from users where username=#{username}")
    public List<User> findinformation(String username);
}
