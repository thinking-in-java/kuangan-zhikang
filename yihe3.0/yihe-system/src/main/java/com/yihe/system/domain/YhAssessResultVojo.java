package com.yihe.system.domain;

import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
public class YhAssessResultVojo {
    private String userCode;
    private String name;
    private String mobile;
    private Integer count;
    private String qtime;

    private List<Map<String,String>> integrals;
    private String integral;
    private String treeOrg;
    private String doctor;
    private String timeMin;
    private String timeMax;
    private Integer page;
    private Integer limit;
    private String sort;
}
