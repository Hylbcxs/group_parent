package usts.cs2020.entity;

import lombok.Data;

@Data
public class ActivityUser {
    private int oid;
    private String username;//用户名
    private int id;//活动id
    private String activityname;//活动名称
    private Double tprice;//团费
    private int num;//参加人数
    private String head;//团长
    private String phoneNum;
    private String email;
    private Double deprice;//团员自己追加的费用
}
