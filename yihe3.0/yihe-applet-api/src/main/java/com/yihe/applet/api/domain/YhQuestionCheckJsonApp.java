package com.yihe.applet.api.domain;

import lombok.Data;

import java.util.Date;

/**
 * Description:暂存实体
 * Created by Administrator on 2020/5/28 8:12
 */
@Data
public class YhQuestionCheckJsonApp {
    private Long id;

    /** $column.columnComment */
    private String userCode;

    /** $column.columnComment */
    private String userCheckAnswer;

    /** 暂存下标 */
    private Long userCheckIndex;

    /** 评估 1 ；暂存 2 */
    private Long userCheckTs;

    /** $column.columnComment */
    private Date userCheckTime;
    
    private String requestSerialNum;
}
