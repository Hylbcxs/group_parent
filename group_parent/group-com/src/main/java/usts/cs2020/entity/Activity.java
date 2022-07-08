package usts.cs2020.entity;

import lombok.Data;

@Data
public class Activity {
    private int id;//活动ID
    private String head;//团长
    private String activityname;//活动名称
    private String time;//活动时间
    private String startplace;//活动起点
    private String content;//活动内容
    private int number;//活动人数
    private Double price;//活动团费
    private int count;//现有人数
    private Double addprice;//团长追加费用
}
