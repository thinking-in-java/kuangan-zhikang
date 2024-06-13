package com.yihe.system.domain;

import lombok.Data;

import java.util.Date;

@Data
public class YhAssessResult {
    private Integer id;
    private String userCode;
    private Integer score;
    private Integer scoreL;
    private String state;
    private Integer age;
    private String sex;
    private Date qtime;
    private String answer;
    private Integer isfalg;
    private Integer assess;

    private String userName;
    private String mobile;
    private String name;
    private String orgName;
}
