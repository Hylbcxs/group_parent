package usts.cs2020.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import usts.cs2020.entity.Activity;
import usts.cs2020.entity.ActivityUser;
import usts.cs2020.entity.UserPrice;

import java.util.List;

@Mapper
public interface JoinMapper {
    @Select("select * from activity")
    public List<Activity> findAll();

    @Insert("insert into activityuser values(#{oid},#{username},#{id},#{head},#{activityname},#{tprice},#{num})")
    public void add(ActivityUser activityUser);

    @Select("select count(id) from activityuser where activityname=#{activityname}")
    public int count(String activityname);

    @Select("select * from activity where activityname=#{activityname}")
    public Activity findByName(String activityname);

    @Select("select activityname from successcheck where username=#{username}")
    public List<String> alreadyjoinname(String username);

    @Select("select * from cofirmactivity where activityname=#{activityname}")
    public Activity findByConfirmName(String activityname);

    @Update("update successcheck set deprice=#{deprice} where username=#{username} and activityname=#{activityname}")
    public void adddeprice(Double deprice,String username,String activityname);
    //输出账单
    //根据username和activityname查出自主追加的费用
    @Select("select deprice from successcheck where username=#{username} and activityname=#{activityname}")
    public Double finddeprice(String username,String activityname);
    //根据activityname查出团长追加的费用和团费
    @Select("select number from cofirmactivity where activityname=#{activityname}")
    public int findnumber(String activityname);
    @Select("select price/number from cofirmactivity where activityname=#{activityname}")
    public Double findtprice(String activityname);
    @Select("select addprice from cofirmactivity where activityname=#{activityname}")
    public Double findaddprice(String activityname);
}
