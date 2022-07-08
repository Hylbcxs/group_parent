package usts.cs2020.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import usts.cs2020.entity.Activity;
import usts.cs2020.entity.ActivityUser;
import usts.cs2020.entity.User;
import usts.cs2020.service.ActivityService;
import java.util.List;


@Controller
@RequestMapping("/activity")
public class ActivityController {

    @Autowired
    private ActivityService activityService;
    @RequestMapping("/findAll")
    public ModelAndView findAll(@RequestParam String head){
        ModelAndView mv = new ModelAndView();
        List<Activity> activityList = activityService.findAll(head);
        //System.out.println(activityList);
        mv.addObject("activityList",activityList);
        mv.setViewName("/activity-list");
        //mv.setViewName("/tour");
        return mv;
    }
    //来到创建页面
    @RequestMapping("/toaddpage")
    public ModelAndView toAddPage(){
        ModelAndView mv = new ModelAndView();
        mv.setViewName("/activity-add");
        return mv;
    }
    @RequestMapping("/add")
    public String add(Activity activity) throws Exception{
        activityService.add(activity);
        return "redirect:/activity/findAll";
    }
    //查询活动信息
    @RequestMapping("/findById/{id}")
    public Activity findById(@PathVariable("id") int id){
        Activity activity = activityService.findById(id);
        return activity;
    }

    //团员审核信息
    @RequestMapping("/check")
    public ModelAndView check(@RequestParam(value = "head",required = false) String head){
        ModelAndView mv = new ModelAndView();
        List<ActivityUser> activityUserList = activityService.check(head);
        mv.addObject("activityUserList",activityUserList);
        mv.setViewName("/activity-check");
        return mv;
    }

    //审核成功
    @RequestMapping("/checksuccess")
    public String checksuccess(@RequestParam Integer oid){
        ActivityUser activityUser = activityService.findsuccesscheck(oid);
        //System.out.println(activityUser);
        //加入审核成功的名单
        activityService.addsuccesscheck(activityUser);
        //删除待审核的
        activityService.deletetocheck(oid);
        return "redirect:/activity/check";
    }
    //确定成团
    @RequestMapping("/confirm")
    public ModelAndView confirm(@RequestParam(value = "activityname") String activityname,@RequestParam(value = "head") String head){
        ModelAndView mv = new ModelAndView();
        Activity activity = activityService.findByName(activityname);
        //加入已成立的活动
        activityService.addToConfirm(activity);
        //从列表中删除
        activityService.deleteToConfirm(activityname);
        List<Activity> findAllConfirm = activityService.findAllConfirm(head);
        mv.addObject("allconfirmlist",findAllConfirm);
        mv.setViewName("/activity-confirm");
        return mv;
    }
    @RequestMapping("/toconfirmpage")
    public ModelAndView toconfirmpage(@RequestParam String head){
        ModelAndView mv = new ModelAndView();
        List<Activity> findAllConfirm = activityService.findAllConfirm(head);
        mv.addObject("allconfirmlist",findAllConfirm);
        mv.setViewName("/activity-confirm");
        return mv;
    }
    //详情
    @RequestMapping("/details")
    public ModelAndView details(@RequestParam String activityname){
        ModelAndView mv = new ModelAndView();
        List<ActivityUser> list = activityService.details(activityname);
        mv.addObject("details",list);
        mv.setViewName("/activity-users");
        return mv;
    }
    @RequestMapping("/toaddpricepage")
    public ModelAndView toaddpricepage(@RequestParam String activityname){
        ModelAndView mv = new ModelAndView();
        mv.setViewName("/activity-addprice");
        mv.addObject("activityname",activityname);
        return mv;
    }
    //团长追加费用
    @RequestMapping("/addprice")
    public String addprice(@RequestParam("addprice") Double addprice,@RequestParam("activityname") String activityname){
        activityService.addprice(addprice,activityname);
        return "redirect:/group/toactivitypage";
    }

    @RequestMapping("/findinformation")
    public ModelAndView findinformation(@RequestParam("head") String username){
        ModelAndView mv = new ModelAndView();
        List<User> information= activityService.findinformation(username);
        System.out.println(information);
        mv.addObject("information",information);
        mv.setViewName("/user-information");
        return mv;
    }
}
