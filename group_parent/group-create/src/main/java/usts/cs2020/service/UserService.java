package usts.cs2020.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import usts.cs2020.entity.User;
import usts.cs2020.mapper.UserMapper;
import usts.cs2020.service.UserService;

import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserMapper userMapper;
    public User selectByUsernameAndPassword(String username, String password) {
        return userMapper.selectByUsernameAndPassword(username,password);
    }
    public void register(User user){
        userMapper.register(user);
    };

}
