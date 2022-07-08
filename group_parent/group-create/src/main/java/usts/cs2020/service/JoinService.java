package usts.cs2020.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import usts.cs2020.entity.Activity;
import usts.cs2020.entity.ActivityUser;
import usts.cs2020.entity.UserPrice;
import usts.cs2020.mapper.JoinMapper;

import java.util.List;

@Service
public class JoinService {
    @Autowired
    private JoinMapper joinMapper;
    public List<Activity> findAll(){
        return joinMapper.findAll();
    }

    public void add(ActivityUser activityUser){
        joinMapper.add(activityUser);
    }

    public int count(String activityname){
        return joinMapper.count(activityname);
    }

    public Activity findByName(String activityname){
        return joinMapper.findByName(activityname);
    }
    public List<String> alreadyjoinname(String username){return joinMapper.alreadyjoinname(username);}
    public Activity findByConfirmName(String activityname){
        return joinMapper.findByConfirmName(activityname);
    }
    public void adddeprice(Double deprice,String username,String activityname){
        joinMapper.adddeprice(deprice,username,activityname);
    }
    public Double finddeprice(String username,String activityname){
        return joinMapper.finddeprice(username,activityname);
    }
    public Double findtprice(String activityname){
        return joinMapper.findtprice(activityname);
    }
    public Double findaddprice(String activityname){
        return joinMapper.findaddprice(activityname);
    }
    public int findnumber(String activityname){
        return joinMapper.findnumber(activityname);
    }
}
