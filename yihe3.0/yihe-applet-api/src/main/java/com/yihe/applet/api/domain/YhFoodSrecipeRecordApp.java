package com.yihe.applet.api.domain;

import lombok.Data;

import java.util.Date;

/**
 * Description:
 * Created by Administrator on 2020/5/28 9:06
 */
@Data
public class YhFoodSrecipeRecordApp {
    /** $column.columnComment */
    private Long recordId;

    /** $column.columnComment */
    private Date recordData;

    /** 套餐名 */
    private String recordName;

    /** 千卡 */
    private Long recordKay;

    /** (1,执行中2，过期 3,挂起) */
    private Long recordType;

    /** 推荐方式 */
    private String recordMode;

    /** 用户id */
    private String userCode;

    /** 套餐id */
    private String mealId;

    /** 疾病id */
    private String illnessId;

    /** 备用 */
    private Date standby;
    
    private Integer age;

    private Float height;

    private Float weight;

    private Integer strength;
    
    private Integer sex;
    
    private String requestSerialNum;
}
