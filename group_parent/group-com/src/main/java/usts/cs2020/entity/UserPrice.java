package usts.cs2020.entity;

import lombok.Data;

@Data
public class UserPrice {
    private String username;
    private String activityname;
    private Double tprice;//团费
    private Double addprice;//团长追加的费用
    private Double ownprice;//团员独立追加的费用
}
