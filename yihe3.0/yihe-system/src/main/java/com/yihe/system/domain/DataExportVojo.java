package com.yihe.system.domain;

import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
public class DataExportVojo {
    private String[] orgCode;
    private String doctor;
    private String group;
    private List<Map<String, String>> groupList;
    private Integer lockFlag;
    private Integer gender;
    private Integer minage;
    private Integer maxage;
    private String minregTime;
    private String maxregTime;
    private String minExportDate;
    private String maxExportDate;
    private Integer state;
    private Integer assId;
    private String propertyName;
    private String[] exportList;
}
