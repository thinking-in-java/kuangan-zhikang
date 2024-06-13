package com.yihe.applet.api.domain;

import lombok.Data;

import java.util.Date;

/**
 * Description:评估报告_基本信息
 * Created by Administrator on 2020/5/27 9:42
 */
@Data
public class YhDiseaseAssessTotalReportApp {
    private Long id;

    /** 请求序列号 */
    private String requestSerialnum;

    /** 用户编码 */
    private String userCode;
    //距离上次测评时间
    private String rusult;

    /** 评估时间 */
    private Date qtime;

    /** 性别 */
    private Integer gender;

    /** 年龄 */
    private Integer age;

    /** 用户名 */
    private String userName;

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

    /** 冠心病  --1,是2，否 */
    private Integer chdHis;

    /** 脑卒中  --1,是2，否 */
    private Integer strokeHis;

    /** 高血压  --1,是2，否 */
    private Integer hbpHis;

    /** 2型糖尿病  --1,是2，否 */
    private Integer diab2His;

    /** 血脂异常  --1,是2，否 */
    private Integer bfHis;

    /** 高尿酸血症  --1,是2，否 */
    private Integer huaHis;

    /** 痛风急性期  --1,是2，否 */
    private Integer gaeHis;

    /** 痛风非急性期  --1,是2，否 */
    private Integer gnaHis;

    /** 骨质疏松症  --1,是2，否 */
    private Integer opHis;

    /** 脂肪肝  --1,是2，否 */
    private Integer flHis;

    /** 慢性支气管炎  --1,是2，否 */
    private Integer bronchHis;

    /** 肺气肿  --1,是2，否 */
    private Integer emphyHis;

    /** 肺癌  --1,是2，否 */
    private Integer lcHis;

    /** 肺结核史  --1,是2，否 */
    private Integer declineHis;

    /** 既往骨折  --1,是2，否 */
    private Integer fractureHis;

    /** 前列腺炎或肥大  --1,是2，否 */
    private Integer prostateDisease;

    /** 前列腺癌  --1,是2，否 */
    private Integer pcHis;

    /** 乳腺癌  --1,是2，否 */
    private Integer bcHis;

    /** 家族患糖尿病   --1,是2，否 */
    private Integer familyDiab;

    /** 家族患高血压   --1,是2，否 */
    private Integer familyHbp;

    /** 家族患冠心病   --1,是2，否 */
    private Integer familyChd;

    /** 家族患脑卒中   --1,是2，否 */
    private Integer familyStroke;

    /** 家族髋部骨折   --1,是2，否 */
    private Integer familyHf;

    /** 家族肺癌   --1,是2，否 */
    private Integer familyLung;

    /** 家族前列腺癌    --1,是2，否 */
    private Integer familyPro;

    /** 家族乳腺癌   --1,是2，否 */
    private Integer familyBreast;

    /** 家族痛风   --1,是2，否 */
    private Integer familyGout;

    /** 吸烟  --1,吸烟 ;2，不吸烟 ;3，已戒烟 */
    private Integer smokeStatus;

    /** 喝酒  --1喝酒2不喝 */
    private Integer foodDrink;

    /** $column.columnComment */
    private float bmi;

    /** 蔬菜水果摄入 1.不足;2.充足 */
    private Integer foodVegetables;
    
    /** 高嘌呤饮食  --1,是2，否 */
    private Integer foodPurine;
    
    /** 牛奶摄入  1.不足;2.充足 */
    private Integer foodDairy;
    /** 水果摄入 1.不足;2.充足 */
    private Integer foodFruits;
    /** 高脂饮食 --1,是2，否 */
    private Integer B4;
    /** 运动  --1不足；2中等；3.充分 */
    private Integer sportIn;
}
