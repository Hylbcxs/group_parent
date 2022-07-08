package usts.cs2020.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;
import usts.cs2020.entity.User;

import java.util.List;

//controller里的@Autowired不报错
@Repository
public interface UserMapper {
    @Select("select * from users where username=#{username} and password=#{password}")
    User selectByUsernameAndPassword(@Param("username") String username, @Param("password") String password);
    @Insert("insert into users values (#{email},#{username},#{password},#{phoneNum})")
    public void register(User user);
}
