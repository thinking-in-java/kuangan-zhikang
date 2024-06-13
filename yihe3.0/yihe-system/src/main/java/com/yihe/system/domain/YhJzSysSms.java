package com.yihe.system.domain;

import java.io.Serializable;
import java.util.Date;

public class YhJzSysSms implements Serializable {
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
    /**
     * 服务平台1.阿里，2.腾讯，3.云片4.网易云信5.七牛
     */
    private Integer type;
    private static final long serialVersionUID = 1L;
    
    public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public Long getSmsId() {
        return smsId;
    }

    public void setSmsId(Long smsId) {
        this.smsId = smsId;
    }

    public String getTemplateId() {
        return templateId;
    }

    public void setTemplateId(String templateId) {
        this.templateId = templateId;
    }

    public String getSmsname() {
        return smsname;
    }

    public void setSmsname(String smsname) {
        this.smsname = smsname;
    }

    public String getSignname() {
        return signname;
    }

    public void setSignname(String signname) {
        this.signname = signname;
    }

    public String getAccesskey() {
        return accesskey;
    }

    public void setAccesskey(String accesskey) {
        this.accesskey = accesskey;
    }

    public String getAccesskeysecret() {
        return accesskeysecret;
    }

    public void setAccesskeysecret(String accesskeysecret) {
        this.accesskeysecret = accesskeysecret;
    }

    public Integer getSmscodeNum() {
        return smscodeNum;
    }

    public void setSmscodeNum(Integer smscodeNum) {
        this.smscodeNum = smscodeNum;
    }

    public Date getSmsCreate() {
        return smsCreate;
    }

    public void setSmsCreate(Date smsCreate) {
        this.smsCreate = smsCreate;
    }

    public Date getSmsModified() {
        return smsModified;
    }

    public void setSmsModified(Date smsModified) {
        this.smsModified = smsModified;
    }

    @Override
	public String toString() {
		return "YhJzSysSms [smsId=" + smsId + ", templateId=" + templateId + ", smsname=" + smsname + ", signname="
				+ signname + ", accesskey=" + accesskey + ", accesskeysecret=" + accesskeysecret + ", smscodeNum="
				+ smscodeNum + ", smsCreate=" + smsCreate + ", smsModified=" + smsModified + ", type=" + type + "]";
	}

    @Override
    public boolean equals(Object that) {
        if (this == that) {
            return true;
        }
        if (that == null) {
            return false;
        }
        if (getClass() != that.getClass()) {
            return false;
        }
        YhJzSysSms other = (YhJzSysSms) that;
        return (this.getSmsId() == null ? other.getSmsId() == null : this.getSmsId().equals(other.getSmsId()))
            && (this.getTemplateId() == null ? other.getTemplateId() == null : this.getTemplateId().equals(other.getTemplateId()))
            && (this.getSmsname() == null ? other.getSmsname() == null : this.getSmsname().equals(other.getSmsname()))
            && (this.getSignname() == null ? other.getSignname() == null : this.getSignname().equals(other.getSignname()))
            && (this.getAccesskey() == null ? other.getAccesskey() == null : this.getAccesskey().equals(other.getAccesskey()))
            && (this.getAccesskeysecret() == null ? other.getAccesskeysecret() == null : this.getAccesskeysecret().equals(other.getAccesskeysecret()))
            && (this.getSmscodeNum() == null ? other.getSmscodeNum() == null : this.getSmscodeNum().equals(other.getSmscodeNum()))
            && (this.getSmsCreate() == null ? other.getSmsCreate() == null : this.getSmsCreate().equals(other.getSmsCreate()))
            && (this.getSmsModified() == null ? other.getSmsModified() == null : this.getSmsModified().equals(other.getSmsModified()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getSmsId() == null) ? 0 : getSmsId().hashCode());
        result = prime * result + ((getTemplateId() == null) ? 0 : getTemplateId().hashCode());
        result = prime * result + ((getSmsname() == null) ? 0 : getSmsname().hashCode());
        result = prime * result + ((getSignname() == null) ? 0 : getSignname().hashCode());
        result = prime * result + ((getAccesskey() == null) ? 0 : getAccesskey().hashCode());
        result = prime * result + ((getAccesskeysecret() == null) ? 0 : getAccesskeysecret().hashCode());
        result = prime * result + ((getSmscodeNum() == null) ? 0 : getSmscodeNum().hashCode());
        result = prime * result + ((getSmsCreate() == null) ? 0 : getSmsCreate().hashCode());
        result = prime * result + ((getSmsModified() == null) ? 0 : getSmsModified().hashCode());
        return result;
    }
}