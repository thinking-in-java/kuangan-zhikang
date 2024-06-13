package com.yihe.applet.api.domain;

import lombok.Data;

import java.util.Date;

@Data
public class YhDiseaseAssessTotalReportNewApp {
    private Integer id;
    /** 请求序列号 */
    private String requestSerialnum;
    /** 用户编码 */
    private String userCode;
    /** 评估时间 */
    private Date qtime;
    /** 性别 */
    private Integer gender;
    /** 年龄 */
    private Integer age;
    /** 评估次数 */
    private Integer times;
    /** 高风险疾病 */
    private String dfNames;
    /** 患或异常疾病 */
    private String skNames;
    /** 中危疾病 */
    private String ldNames;
    /** 低危疾病 */
    private String lrNames;
    /** 1.一般人群 2.慢病患者 3.慢病高危人群 */
    private String classify;
    /** bmi */
    private Double bmi;
}
