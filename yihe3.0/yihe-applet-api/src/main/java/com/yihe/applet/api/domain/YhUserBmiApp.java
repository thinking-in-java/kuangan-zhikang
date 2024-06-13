package com.yihe.applet.api.domain;

import java.io.Serializable;

public class YhUserBmiApp implements Serializable {
    private Integer uBmiId;

    /**
     * 身高
     *
     * @mbg.generated
     */
    private Float uHeight;

    /**
     * 体重
     *
     * @mbg.generated
     */
    private Float uWeight;

    /**
     * 测量时间
     *
     * @mbg.generated
     */
    private String entryTime;

    /**
     * 是否隐藏
     *
     * @mbg.generated
     */
    private Integer isDel;

    /**
     * 所属用户
     *
     * @mbg.generated
     */
    private Integer eUserId;

    /**
     * 测量环境
     *
     * @mbg.generated
     */
    private String env;

    private String tiwen;

    private double progressvalue;

    private String jiedu;

    private double bmi;

    private boolean openNo;

    public String getTiwen() {
        return tiwen;
    }

    public void setTiwen(String tiwen) {
        this.tiwen = tiwen;
    }

    public double getProgressvalue() {
        return progressvalue;
    }

    public void setProgressvalue(double progressvalue) {
        this.progressvalue = progressvalue;
    }

    public String getJiedu() {
        return jiedu;
    }

    public void setJiedu(String jiedu) {
        this.jiedu = jiedu;
    }

    public double getBmi() {
        return bmi;
    }

    public void setBmi(double bmi) {
        this.bmi = bmi;
    }

    public boolean isOpenNo() {
        return openNo;
    }

    public void setOpenNo(boolean openNo) {
        this.openNo = openNo;
    }

    /**
     * 数据来源
     *
     * @mbg.generated
     */
    private String dataSource;

    private static final long serialVersionUID = 1L;

    public Integer getuBmiId() {
        return uBmiId;
    }

    public void setuBmiId(Integer uBmiId) {
        this.uBmiId = uBmiId;
    }

    public Float getuHeight() {
        return uHeight;
    }

    public void setuHeight(Float uHeight) {
        this.uHeight = uHeight;
    }

    public Float getuWeight() {
        return uWeight;
    }

    public void setuWeight(Float uWeight) {
        this.uWeight = uWeight;
    }

    public String getEntryTime() {
        return entryTime;
    }

    public void setEntryTime(String entryTime) {
        this.entryTime = entryTime;
    }

    public Integer getIsDel() {
        return isDel;
    }

    public void setIsDel(Integer isDel) {
        this.isDel = isDel;
    }

    public Integer geteUserId() {
        return eUserId;
    }

    public void seteUserId(Integer eUserId) {
        this.eUserId = eUserId;
    }

    public String getEnv() {
        return env;
    }

    public void setEnv(String env) {
        this.env = env;
    }

    public String getDataSource() {
        return dataSource;
    }

    public void setDataSource(String dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", uBmiId=").append(uBmiId);
        sb.append(", uHeight=").append(uHeight);
        sb.append(", uWeight=").append(uWeight);
        sb.append(", entryTime=").append(entryTime);
        sb.append(", isDel=").append(isDel);
        sb.append(", eUserId=").append(eUserId);
        sb.append(", env=").append(env);
        sb.append(", dataSource=").append(dataSource);
        sb.append("]");
        return sb.toString();
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
        YhUserBmiApp other = (YhUserBmiApp) that;
        return (this.getuBmiId() == null ? other.getuBmiId() == null : this.getuBmiId().equals(other.getuBmiId()))
            && (this.getuHeight() == null ? other.getuHeight() == null : this.getuHeight().equals(other.getuHeight()))
            && (this.getuWeight() == null ? other.getuWeight() == null : this.getuWeight().equals(other.getuWeight()))
            && (this.getEntryTime() == null ? other.getEntryTime() == null : this.getEntryTime().equals(other.getEntryTime()))
            && (this.getIsDel() == null ? other.getIsDel() == null : this.getIsDel().equals(other.getIsDel()))
            && (this.geteUserId() == null ? other.geteUserId() == null : this.geteUserId().equals(other.geteUserId()))
            && (this.getEnv() == null ? other.getEnv() == null : this.getEnv().equals(other.getEnv()))
            && (this.getDataSource() == null ? other.getDataSource() == null : this.getDataSource().equals(other.getDataSource()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getuBmiId() == null) ? 0 : getuBmiId().hashCode());
        result = prime * result + ((getuHeight() == null) ? 0 : getuHeight().hashCode());
        result = prime * result + ((getuWeight() == null) ? 0 : getuWeight().hashCode());
        result = prime * result + ((getEntryTime() == null) ? 0 : getEntryTime().hashCode());
        result = prime * result + ((getIsDel() == null) ? 0 : getIsDel().hashCode());
        result = prime * result + ((geteUserId() == null) ? 0 : geteUserId().hashCode());
        result = prime * result + ((getEnv() == null) ? 0 : getEnv().hashCode());
        result = prime * result + ((getDataSource() == null) ? 0 : getDataSource().hashCode());
        return result;
    }
}