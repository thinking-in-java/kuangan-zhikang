package com.yihe.applet.api.domain;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;
@Data
public class YhJzSysSmsApp implements Serializable {
    private Long smsId;

    /**
     * 消息模本ID
     *
     * @mbg.generated
     */
    private String templateId;

    /**
     * 短信名称
     *
     * @mbg.generated
     */
    private String smsname;

    /**
     * 短信签名
     *
     * @mbg.generated
     */
    private String signname;

    /**
     * ak
     *
     * @mbg.generated
     */
    private String accesskey;

    /**
     * aks
     *
     * @mbg.generated
     */
    private String accesskeysecret;

    /**
     * 验证码位数
     *
     * @mbg.generated
     */
    private Integer smscodeNum;

    /**
     * 创建时间
     *
     * @mbg.generated
     */
    private Date smsCreate;

    /**
     * 修改时间
     *
     * @mbg.generated
     */
    private Date smsModified;
    
    
    private Integer type;

    private static final long serialVersionUID = 1L;

   
}