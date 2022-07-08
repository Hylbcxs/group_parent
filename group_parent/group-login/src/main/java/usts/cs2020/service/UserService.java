package usts.cs2020.service;

import org.springframework.stereotype.Repository;
import usts.cs2020.entity.User;

import java.util.List;

@Repository
public interface UserService {
    List<User> selectByUsernameAndPassword(String username, String password);
}
