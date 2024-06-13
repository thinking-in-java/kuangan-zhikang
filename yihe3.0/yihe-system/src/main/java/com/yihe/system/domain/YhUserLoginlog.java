package com.yihe.system.domain;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;


public class YhUserLoginlog implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 登录日志id
     *
     * @mbg.generated
     */
    private Integer infoId;

    /**
     * 登录帐号
     *
     * @mbg.generated
     */
    private String loginName;

    /**
     * 登录IP地址
     *
     * @mbg.generated
     */
    private String ipaddr;

    /**
     * 登录地点
     *
     * @mbg.generated
     */
    private String loginLocation;

    /**
     * 浏览器类型
     *
     * @mbg.generated
     */
    private String browser;

    /**
     * 消耗时间
     *
     * @mbg.generated
     */
    private String os;

    /**
     * 登录状态（0成功 1失败）
     *
     * @mbg.generated
     */
    private String status;

    /**
     * 提示消息
     *
     * @mbg.generated
     */
    private String msg;

    /**
     * 请求路径
     *
     * @mbg.generated
     */
    private Date loginTime;

    public Integer getInfoId() {
        return infoId;
    }

    public void setInfoId(Integer infoId) {
        this.infoId = infoId;
    }

    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    public String getIpaddr() {
        return ipaddr;
    }

    public void setIpaddr(String ipaddr) {
        this.ipaddr = ipaddr;
    }

    public String getLoginLocation() {
        return loginLocation;
    }

    public void setLoginLocation(String loginLocation) {
        this.loginLocation = loginLocation;
    }



    public String getBrowser() {
        return browser;
    }

    public void setBrowser(String browser) {
        this.browser = browser;
    }

    public String getOs() {
        return os;
    }

    public void setOs(String os) {
        this.os = os;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Date getLoginTime() {
        return loginTime;
    }

    public void setLoginTime(Date loginTime) {
        this.loginTime = loginTime;
    }
}