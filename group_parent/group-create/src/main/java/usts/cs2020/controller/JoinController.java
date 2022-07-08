package usts.cs2020.controller;

import org.apache.poi.hssf.usermodel.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import usts.cs2020.entity.Activity;
import usts.cs2020.entity.ActivityUser;
import usts.cs2020.entity.UserPrice;
import usts.cs2020.service.JoinService;

import javax.servlet.http.HttpServletResponse;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.List;

@Controller
@RequestMapping("/join")
public class JoinController {
    @Autowired
    private JoinService joinService;
    @RequestMapping("/findAll")
    public ModelAndView findAll(){
        ModelAndView mv = new ModelAndView();
        List<Activity> joinList = joinService.findAll();
        mv.addObject("joinList",joinList);
        mv.setViewName("/join-list");
        return mv;
    }

    @RequestMapping("/tojoinpage")
    public ModelAndView tojoinpage(@RequestParam(value = "activityname",required = false) String activityname){
        ModelAndView mv = new ModelAndView();
        int number = joinService.count(activityname);
        Activity activity = joinService.findByName(activityname);
        mv.addObject("activity",activity);
        mv.addObject("number",number);
        mv.setViewName("/join");
        return mv;
    }

    @RequestMapping("/add")
    public String add(ActivityUser activityUser){
        joinService.add(activityUser);
        return "redirect:/join/findAll";
    }

    @RequestMapping("/alreadyjoin")
    public ModelAndView alreadyjoin(@RequestParam(value = "username",required = false) String username){
        ModelAndView mv = new ModelAndView();
        List<String> activityList = joinService.alreadyjoinname(username);
        for(String activityname:activityList){
            Activity activities = joinService.findByConfirmName(activityname);
            mv.addObject("activities",activities);
            mv.setViewName("/join-alreadyjoin");
            return mv;
        }
        return mv;
    }

    @RequestMapping("/toadddepricepage")
    public ModelAndView toadddepricepage(){
        ModelAndView mv = new ModelAndView();
        mv.setViewName("/join-addeprice");
        return mv;
    }

    @RequestMapping("/adddeprice")
    public String addeprice(@RequestParam("username") String username,@RequestParam("activityname") String activityname,@RequestParam("addprice") Double addprice){
        joinService.adddeprice(addprice,username,activityname);
        return "redirect:/group/toactivitypage";
    }
    @RequestMapping("/export")
    public void exportExcel(@RequestParam("username") String username, @RequestParam("activityname") String activityname, HttpServletResponse response) {
        //第一步，创建一个webbook，对应一个Excel文件
        HSSFWorkbook wb = new HSSFWorkbook();
        //第二步，在webbook中添加一个sheet，对应Excel文件中的sheet
        HSSFSheet sheet = wb.createSheet("分推费用表");
        //第三步，在sheet中添加表头，第0行
        HSSFRow row = sheet.createRow(0);
        //第四步，创建单元格，并设置表头，设置表头居中
        HSSFCellStyle style = wb.createCellStyle();
        style.setAlignment(HSSFCellStyle.ALIGN_CENTER);//创建一个居中格式
        HSSFCell cell = row.createCell(0);
        cell.setCellValue("用户名");
        cell.setCellStyle(style);
        cell = row.createCell((short) 1);
        cell.setCellValue("活动名称");
        cell.setCellStyle(style);
        cell = row.createCell((short) 2);
        cell.setCellValue("团费");
        cell.setCellStyle(style);
        cell = row.createCell((short) 3);
        cell.setCellValue("追加AA费用");
        cell.setCellStyle(style);
        cell = row.createCell((short) 4);
        cell.setCellValue("自行追加的费用");
        cell.setCellStyle(style);
        cell = row.createCell((short) 5);
        cell.setCellValue("总费用");
        cell.setCellStyle(style);
        //第五步， 写入实体数据
        int number=joinService.findnumber(activityname);
        Double deprice = joinService.finddeprice(username, activityname);
        Double tprice=joinService.findtprice(activityname);
        Double addprice_= joinService.findaddprice(activityname);
        Double addprice=addprice_/number;
        Double total=deprice+tprice+addprice;
        //第四步，创建单元格，并设置值
        row = sheet.createRow(1);
        row.createCell((short) 0).setCellValue(username);
        row.createCell((short) 1).setCellValue(activityname);
        row.createCell((short) 2).setCellValue(tprice);
        row.createCell((short) 3).setCellValue(addprice);
        row.createCell((short) 4).setCellValue(deprice);
        row.createCell((short) 5).setCellValue(total);
        //第六步，下载excel
        OutputStream out = null;
        try{
            out = response.getOutputStream();
            String fileName = username + ".xls";//文件名
            response.setContentType("application/x-msdownload");
            response.setHeader("Content-Disposition","attachment;filename=" + URLEncoder.encode(fileName,"UTF-8"));
            wb.write(out);
        }catch (Exception e){
            e.printStackTrace();
        }

    }
}
