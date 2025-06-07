package com.yihe.applet.api.domain;

import lombok.Data;

import java.util.Date;

@Data
public class DoctorXcxHomePageChat {
    private String senderId;
    private String name;
    private String gender;
    private Date birthDate;
    private String mobile;
    private String avatar;
    private Integer messageType;
    private String message;
    private String addTimeInfo;
    private String unread;
    private String age;
    private String realname;


}
