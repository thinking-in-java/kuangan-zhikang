package com.yihe.applet.api.domain;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;

@Data
public class YhUserReginfoApp implements Serializable{
	  /**
     * 用户code
     *
     * @mbg.generated
     */
    private String userCode;

    /**
     * 用户类型
     *
     * @mbg.generated
     */
    private Integer userType;

    /**
     * 用户姓名
     *
     * @mbg.generated
     */
    private String userName;

    /**
     * 用户密码
     *
     * @mbg.generated
     */
    private String userPwd;

    /**
     * 用户性别（1.男；2.女）
     *
     * @mbg.generated
     */
    private Integer gender;

    /**
     * 出生年月
     *
     * @mbg.generated
     */
   
    private Date birthDate;
    
    
    private String birthtime;
    /**
     * 民族
     *
     * @mbg.generated
     */
    private String nation;

    /**
     * 用户邮箱
     *
     * @mbg.generated
     */
    private String email;

    /**
     * 身份证
     *
     * @mbg.generated
     */
    private String cardidentity;

    /**
     * 省
     *
     * @mbg.generated
     */
    private String provinceId;

    /**
     * 市
     *
     * @mbg.generated
     */
    private String cityId;

    /**
     * 区
     *
     * @mbg.generated
     */
    private String areaId;

    /**
     * 注册时间
     *
     * @mbg.generated
     */
    private Date regTime;

    /**
     * 登录次数
     *
     * @mbg.generated
     */
    private Integer loginTimes;

    /**
     * 真实姓名
     *
     * @mbg.generated
     */
    private String name;

    /**
     * 户籍地址
     *
     * @mbg.generated
     */
    private String address;

    /**
     * 常住地址
     *
     * @mbg.generated
     */
    private String permanentaddress;

    /**
     * 邮编
     *
     * @mbg.generated
     */
    private String postCode;

    /**
     * 紧急联系人手机号
     *
     * @mbg.generated
     */
    private String relation;

    /**
     * 手机号码
     *
     * @mbg.generated
     */
    private String mobile;

    /**
     * 职业
     *
     * @mbg.generated
     */
    private String occupation;

    /**
     * 文化程度
     *
     * @mbg.generated
     */
    private String edBackground;

    /**
     * 婚姻状况
     *
     * @mbg.generated
     */
    private String maritalStatus;

    /**
     * 锁标记
     *
     * @mbg.generated
     */
    private Integer lockFlag;

    /**
     * 注册来源
     *
     * @mbg.generated
     */
    private Integer regSource;

    /**
     * 证书类型
     *
     * @mbg.generated
     */
    private Integer certType;

    /**
     * 证书编号
     *
     * @mbg.generated
     */
    private String certNo;

    /**
     * 组织code
     *
     * @mbg.generated
     */
    private String orgCode;

    /**
     * 血型
     *
     * @mbg.generated
     */
    private String bloodType;

    /**
     * rh阴性
     *
     * @mbg.generated
     */
    private String rhfeminine;

    /**
     * 注册卡Id
     *
     * @mbg.generated
     */
    private String enrolCardId;

    /**
     * 登录次数
     *
     * @mbg.generated
     */
    private Date loginTime;

    /**
     * 工作单位
     *
     * @mbg.generated
     */
    private String unit;

    /**
     * 是否社团（0是1否）
     *
     * @mbg.generated
     */
    private Integer isAssociation;

    /**
     * 社团名称
     *
     * @mbg.generated
     */
    private String associationName;

    /**
     * 是否独居（0是1否）
     *
     * @mbg.generated
     */
    private Integer isAlone;

    /**
     * 是否过敏（0是1否）
     *
     * @mbg.generated
     */
    private Integer isAllergy;

    /**
     * 过敏源
     *
     * @mbg.generated
     */
    private String allergyName;

    /**
     * 是否过期
     */
    private  boolean expire;

    /**
     * openid
     */
    private YhWxUser yhWxUser;


}
