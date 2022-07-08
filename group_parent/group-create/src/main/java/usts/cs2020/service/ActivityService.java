package usts.cs2020.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import usts.cs2020.entity.Activity;
import usts.cs2020.entity.ActivityUser;
import usts.cs2020.entity.User;
import usts.cs2020.mapper.ActivityMapper;

import java.util.List;
@Service
public class ActivityService {
    @Autowired
    private ActivityMapper activityMapper;
    public List<Activity> findAll(String head){
        return activityMapper.findAll(head);
    }
    public void add(Activity activity){
        activityMapper.add(activity);
    }
    public Activity findById(int id){
        return activityMapper.findById(id);
    }
    public List<ActivityUser> check(String head){
        return activityMapper.check(head);
    }
    public void deletetocheck(int oid){
        activityMapper.deletetocheck(oid);
    }
    public ActivityUser findsuccesscheck(int oid){
        return activityMapper.findsuccesscheck(oid);
    }
    public void addsuccesscheck(ActivityUser activityUser){
        activityMapper.addsuccesscheck(activityUser);
    }
    public Activity findByName(String activityname){return activityMapper.findByName(activityname);}
    public void addToConfirm(Activity activity){activityMapper.addToConfirm(activity);}
    public void deleteToConfirm(String activityname){activityMapper.deleteToConfirm(activityname);}
    public List<Activity> findAllConfirm(String head){return activityMapper.findAllConfirm(head);}
    public List<ActivityUser> details(String activityname){return activityMapper.details(activityname);}
    public void addprice(Double addprice,String activityname){
        activityMapper.addprice(addprice,activityname);
    }
    public List<User> findinformation(String username){
        return activityMapper.findinformation(username);
    }
}
