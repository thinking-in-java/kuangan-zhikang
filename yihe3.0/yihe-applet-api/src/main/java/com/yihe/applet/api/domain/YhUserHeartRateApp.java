package com.yihe.applet.api.domain;

import java.io.Serializable;

public class YhUserHeartRateApp implements Serializable {
    /**
     * 心率自增id
     *
     * @mbg.generated
     */
    private Integer rid;

    /**
     * 心率
     *
     * @mbg.generated
     */
    private Integer heartRate;

    /**
     * 录入时间
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
     * 测量环境
     *
     * @mbg.generated
     */
    private String env;

    /**
     * 数据来源
     *
     * @mbg.generated
     */
    private String dataSource;

    /**
     * 用户id
     *
     * @mbg.generated
     */
    private Integer eUserId;

    private String jiedu;

    private boolean openNo;

    private String tiwen;

    private double progressvalue;

    public String getJiedu() {
        return jiedu;
    }

    public void setJiedu(String jiedu) {
        this.jiedu = jiedu;
    }

    public boolean isOpenNo() {
        return openNo;
    }

    public void setOpenNo(boolean openNo) {
        this.openNo = openNo;
    }

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

    private static final long serialVersionUID = 1L;

    public Integer getRid() {
        return rid;
    }

    public void setRid(Integer rid) {
        this.rid = rid;
    }

    public Integer getHeartRate() {
        return heartRate;
    }

    public void setHeartRate(Integer heartRate) {
        this.heartRate = heartRate;
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

    public Integer geteUserId() {
        return eUserId;
    }

    public void seteUserId(Integer eUserId) {
        this.eUserId = eUserId;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", rid=").append(rid);
        sb.append(", heartRate=").append(heartRate);
        sb.append(", entryTime=").append(entryTime);
        sb.append(", isDel=").append(isDel);
        sb.append(", env=").append(env);
        sb.append(", dataSource=").append(dataSource);
        sb.append(", eUserId=").append(eUserId);
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
        YhUserHeartRateApp other = (YhUserHeartRateApp) that;
        return (this.getRid() == null ? other.getRid() == null : this.getRid().equals(other.getRid()))
            && (this.getHeartRate() == null ? other.getHeartRate() == null : this.getHeartRate().equals(other.getHeartRate()))
            && (this.getEntryTime() == null ? other.getEntryTime() == null : this.getEntryTime().equals(other.getEntryTime()))
            && (this.getIsDel() == null ? other.getIsDel() == null : this.getIsDel().equals(other.getIsDel()))
            && (this.getEnv() == null ? other.getEnv() == null : this.getEnv().equals(other.getEnv()))
            && (this.getDataSource() == null ? other.getDataSource() == null : this.getDataSource().equals(other.getDataSource()))
            && (this.geteUserId() == null ? other.geteUserId() == null : this.geteUserId().equals(other.geteUserId()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getRid() == null) ? 0 : getRid().hashCode());
        result = prime * result + ((getHeartRate() == null) ? 0 : getHeartRate().hashCode());
        result = prime * result + ((getEntryTime() == null) ? 0 : getEntryTime().hashCode());
        result = prime * result + ((getIsDel() == null) ? 0 : getIsDel().hashCode());
        result = prime * result + ((getEnv() == null) ? 0 : getEnv().hashCode());
        result = prime * result + ((getDataSource() == null) ? 0 : getDataSource().hashCode());
        result = prime * result + ((geteUserId() == null) ? 0 : geteUserId().hashCode());
        return result;
    }
}