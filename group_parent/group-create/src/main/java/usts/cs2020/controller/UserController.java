package usts.cs2020.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import usts.cs2020.entity.User;
import usts.cs2020.service.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;


@Controller
@RequestMapping("/group")
public class UserController {
    @Autowired
    private UserService userService;

    @RequestMapping("/tologinpage")
    public ModelAndView tologinpage(){
        ModelAndView mv = new ModelAndView();
        mv.setViewName("/index");
        return mv;
    }

    @RequestMapping("/login")
    public String login(@RequestParam("username") String username, @RequestParam("password") String password, HttpServletRequest req) {
        //md5加密
        byte[] digest = null;
        try {
            MessageDigest md5 = MessageDigest.getInstance("md5");
            digest = md5.digest(password.getBytes("utf-8"));
        } catch (NoSuchAlgorithmException | UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        //16是表示转换为16进制数
        String md5Str = new BigInteger(1, digest).toString(16);
        User user = userService.selectByUsernameAndPassword(username, md5Str);
        System.out.println(user);
        if (user != null) {
            HttpSession session = req.getSession();
            session.setAttribute("username", user.getUsername());
           // model.addAttribute("username",user.getUsername());
            return "redirect:/group/toactivitypage";
        } else {
            return "redirect:/group/tologinpage";
        }
    }
    @RequestMapping("/register")
    public String register(User user){
        String username = user.getUsername();
        String password = user.getPassword();
        String phoneNum = user.getPhoneNum();
        String email = user.getEmail();
        //md5加密
        byte[] digest = null;
        try {
            MessageDigest md5 = MessageDigest.getInstance("md5");
            digest  = md5.digest(password.getBytes("utf-8"));
        } catch (NoSuchAlgorithmException | UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        //16是表示转换为16进制数
        String md5Str = new BigInteger(1, digest).toString(16);
        User user2 = new User(username,md5Str,phoneNum,email);
        System.out.println(user2);
        System.out.println(username);
        //放进数据库

        userService.register(user2);
        return "redirect:/group/tologinpage";
    }

    @RequestMapping("/toregisterpage")
    public ModelAndView toregisterpage(){
        ModelAndView mv = new ModelAndView();
        mv.setViewName("/regist");
        return mv;
    }


    @RequestMapping("/toactivitypage")
    public ModelAndView toactivitypage(){
        ModelAndView mv = new ModelAndView();
        mv.setViewName("/transit");
        return mv;
    }
}
