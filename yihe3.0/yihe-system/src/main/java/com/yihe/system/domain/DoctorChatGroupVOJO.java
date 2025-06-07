package com.yihe.system.domain;

import lombok.Data;

import java.util.List;

@Data
public class DoctorChatGroupVOJO {
    //分组id
    private String uuid;
    //分组名
    private String name;
    //分组头像备用
    private String avata = "https://xcxqny.yhy.ren/%E5%9B%BE%E7%89%87%2076684.png";
    //用户uuid集合
    private List<String> userList;
    //分组人数
    private String groupMembers;
    //最新时间
    private String newest;

}
