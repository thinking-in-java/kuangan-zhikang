package com.yihe.system.domain;

import lombok.Data;

import java.io.Serializable;

/**
 * 风险因素改善指南
 */
@Data
public class YhReportResolveVOJO implements Serializable {
    /**
     * 主键
     */
    private Integer id;

    /**
     * 评估序列号
     */
    private String resultSerialNum;

    /****
     * 病种id
     */
    private Integer diseaseId;

    /**
     *改善方式
     */
    private String type;

    /**
     * 改善标题名
     */
    private String name;


}