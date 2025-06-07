package com.yihe.system.domain;

import lombok.Data;

@Data
public class YhAssessIntroduce {

    private Integer id;

    private String name;

    private String reference;

    private String introduce;

    private Integer questionCount;

    private Integer requiredTime;
}
