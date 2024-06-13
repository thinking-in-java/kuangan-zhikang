package com.yihe.applet.api.domain;

import lombok.Data;

import java.util.Date;

/**
 * Description:
 * Created by Administrator on 2020/5/28 14:08
 */
@Data
public class YhSportRecommendRecordApp {
    /** $column.columnComment */
    private Long id;

    /** $column.columnComment */
    private Date recordData;

    /** $column.columnComment */
    private String userCode;

    /** 疾病id */
    private String diseaseid;

    /** 套餐id */
    private String setmealid;

    /** 天数（第几天） */
    private String number;

    /** 动作完成id */
    private String finishid;

    /** （1,执行中 2，已完成 ） */
    private Integer recordType;

    /** 千卡 */
    private Long recordKay;

    /** 推荐方式 */
    private String recordMode;

    /** 总共多少天 */
    private String standby;
    
    /** 套餐名称 */
    private String sportName;
}
