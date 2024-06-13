package com.yihe.applet.api.domain;

import lombok.Data;

import java.util.Date;

/**
 * Description:
 * Created by Administrator on 2020/5/27 14:10
 */

@Data
public class YhDiseaseRiskResultApp{
	
    
	
    // @Fields serialVersionUID : TODO
	private static final long serialVersionUID = 1L;
	
	
	private Long id;
    /** 疾病ID */
    private String diseaseid;

    /** 用户id */
    private String userCode;

    /** 请求序列号 */
    private String requestSerialize;

    /** 年龄对应的风险内容 */
    private String age;

    /** 高密度脂蛋白胆固醇对应的风险内容 */
    private String hdlc;

    /** 低密度脂蛋白胆固醇对应的风险内容 */
    private String ldlc;

    /** 蔬菜水果对应的风险内容 */
    private String food;

    /** 体力活动水平对应的风险内容 */
    private String sport;

    /** 高脂饮食对应的风险内容 */
    private String b4;

    /** 吸烟对应的风险内容 */
    private String smoke;

    /** 饮酒情况对应的风险内容 */
    private String drink;

    /** 红外检测报告对应的风险内容 */
    private String hwCheck;

    /** 体重指数（BMI）对应的风险内容 */
    private String bmi;

    /** 腰围对应的风险内容 */
    private String waist;

    /** 糖尿病史对应的风险内容 */
    private String diabHis;

    /** 高血压病史对应的风险内容 */
    private String hbpHis;

    /** 空腹血糖对应的风险内容 */
    private String fbg;

    /** 收缩压对应的风险内容 */
    private String sbp;

    /** 总胆固醇对应的风险内容 */
    private String cholesterol;

    /** 甘油三酯对应的风险内容 */
    private String tg;

    /** 高血脂的风险内容 */
    private String bf;

    /** 舒张压对应的风险内容 */
    private String dbp;

    /** 高血压家族史对应的风险内容 */
    private String hbps;

    /** 父母髋部骨折史对应的风险内容 */
    private String hfs;

    /** 个人既往骨折史的风险内容 */
    private String hfHis;

    /** 长期服用肾上腺皮质激素的风险内容 */
    private String c1;

    /** 牛奶摄入对应的风险内容 */
    private String milk;

    /** 反式脂肪酸对应的风险内容 */
    private String tfa;

    /** 冠心病家族史对应的风险内容 */
    private String chds;

    /** 糖尿病家族史对应的风险内容 */
    private String dms;

    /** 被动吸烟对应的风险内容 */
    private String smokeSencod;

    /** 肺癌家族史对应的风险内容 */
    private String lungs;

    /** 肺气肿病史对应的风险内容 */
    private String emphyHis;

    /** 粉尘接触史对应的风险内容 */
    private String asbestosContactHis;

    /** 近两年是否新房装修对应的风险内容 */
    private String decorateHouse;

    /** 精神压力对应的风险内容 */
    private String psyNervous;

    /** 慢性支气管炎病史对应的风险内容 */
    private String bronchHis;

    /** 油烟接触史对应的风险内容 */
    private String lampblackContact;

    /** 痛风家族史对应的风险内容 */
    private String gouts;

    /** 饮茶情况对应的风险内容 */
    private String tea;

    /** 高嘌呤饮食对应的风险内容 */
    private String foodPurine;

    /** 冠心病史对应的风险内容 */
    private String chdHis;

    /** 血尿酸对应的风险内容 */
    private String bua;

    /** 避孕药服用对应的风险内容 */
    private String medUsePill;

    /** 使用丰乳产品或做过乳房整形手术对应的风险内容 */
    private String breastPlasticSurgery;

    /** 乳腺癌家族史对应的风险内容 */
    private String bcs;

    /** 良性乳腺疾病史对应的风险内容 */
    private String begindBreastHis;

    /** 初潮年龄对应的风险内容 */
    private String femaleMenstruation;

    /** 初产年龄对应的风险内容 */
    private String femaleTire;

    /** 哺乳喂养时间对应的风险内容 */
    private String femaleBreastTime;

    /** 绝经年龄对应的风险内容 */
    private String femaleMenopauseAge;

    /** 雌激素替代疗法对应的风险内容 */
    private String hasMedErt;

    /** 脑卒中家族史对应的风险内容 */
    private String css;

    /** 心血管病史对应的风险内容 */
    private String cardHis;

    /** 左心室肥大对应的风险内容 */
    private String lvdHis;

    /** 房颤病史对应的风险内容 */
    private String afHis;

    /** 前列腺癌家族史对应的风险内容 */
    private String pcs;

    /** 前列腺炎或肥大病史对应的风险内容 */
    private String prostateDisease;

    /** 尿路感染对应的风险内容 */
    private String urinaryTractInfection;

    /** 尿频、尿痛、尿不尽、排尿困难症状对应的风险内容 */
    private String urineSymptom;

    /** T值对应的风险内容 */
    private String tScore;

    /** 类风湿关节炎对应的风险内容 */
    private String raHis;

    /** 居住环境对应的风险内容 */
    private String livingEnvir;

    /** 啤酒摄入对应的风险内容 */
    private String foodDrinkBeer;
    
    /** 月经规律对应的风险内容*/
    private String femaleMenstruationRegular;
    
    private String chemicalOccupation;
    
    
    private String foodVegetables;
    private String remark;
    /** 时间 */
    private Date qtime;
}
