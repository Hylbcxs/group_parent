package usts.cs2020.entity;

import lombok.Data;

@Data
//自动生成set和get方法
public class User {
    private String username;
    private String password;
    private String phoneNum;
    private String email;

    public User(String username, String password, String phoneNum, String email) {
        this.username = username;
        this.password = password;
        this.phoneNum = phoneNum;
        this.email = email;
    }
}
