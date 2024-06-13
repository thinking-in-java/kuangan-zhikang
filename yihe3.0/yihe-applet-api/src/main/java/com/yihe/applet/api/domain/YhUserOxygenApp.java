package com.yihe.applet.api.domain;

import java.io.Serializable;
import java.util.Date;

public class YhUserOxygenApp implements Serializable {
    private Integer uOxygenId;

    /**
     * 血氧值
     *
     * @mbg.generated
     */
    private Float oxygen;

    /**
     * 心率
     *
     * @mbg.generated
     */
    private Integer heartRate;

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
     * 所属客户
     *
     * @mbg.generated
     */
    private Integer eUserId;

    /**
     * 测量环境
     *
     * @mbg.generated
     */
    private String testHj;

    /**
     * 数据来源
     *
     * @mbg.generated
     */
    private String type;

    private  double progressvalue;

    private boolean openNo;

    private String jiedu;

    private String tiwen;

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

    public boolean isOpenNo() {
        return openNo;
    }

    public void setOpenNo(boolean openNo) {
        this.openNo = openNo;
    }

    public String getJiedu() {
        return jiedu;
    }

    public void setJiedu(String jiedu) {
        this.jiedu = jiedu;
    }

    private static final long serialVersionUID = 1L;

    public Integer getuOxygenId() {
        return uOxygenId;
    }

    public void setuOxygenId(Integer uOxygenId) {
        this.uOxygenId = uOxygenId;
    }

    public Float getOxygen() {
        return oxygen;
    }

    public void setOxygen(Float oxygen) {
        this.oxygen = oxygen;
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

    public Integer geteUserId() {
        return eUserId;
    }

    public void seteUserId(Integer eUserId) {
        this.eUserId = eUserId;
    }

    public String getTestHj() {
        return testHj;
    }

    public void setTestHj(String testHj) {
        this.testHj = testHj;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", uOxygenId=").append(uOxygenId);
        sb.append(", oxygen=").append(oxygen);
        sb.append(", heartRate=").append(heartRate);
        sb.append(", entryTime=").append(entryTime);
        sb.append(", isDel=").append(isDel);
        sb.append(", eUserId=").append(eUserId);
        sb.append(", testHj=").append(testHj);
        sb.append(", type=").append(type);
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
        YhUserOxygenApp other = (YhUserOxygenApp) that;
        return (this.getuOxygenId() == null ? other.getuOxygenId() == null : this.getuOxygenId().equals(other.getuOxygenId()))
            && (this.getOxygen() == null ? other.getOxygen() == null : this.getOxygen().equals(other.getOxygen()))
            && (this.getHeartRate() == null ? other.getHeartRate() == null : this.getHeartRate().equals(other.getHeartRate()))
            && (this.getEntryTime() == null ? other.getEntryTime() == null : this.getEntryTime().equals(other.getEntryTime()))
            && (this.getIsDel() == null ? other.getIsDel() == null : this.getIsDel().equals(other.getIsDel()))
            && (this.geteUserId() == null ? other.geteUserId() == null : this.geteUserId().equals(other.geteUserId()))
            && (this.getTestHj() == null ? other.getTestHj() == null : this.getTestHj().equals(other.getTestHj()))
            && (this.getType() == null ? other.getType() == null : this.getType().equals(other.getType()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getuOxygenId() == null) ? 0 : getuOxygenId().hashCode());
        result = prime * result + ((getOxygen() == null) ? 0 : getOxygen().hashCode());
        result = prime * result + ((getHeartRate() == null) ? 0 : getHeartRate().hashCode());
        result = prime * result + ((getEntryTime() == null) ? 0 : getEntryTime().hashCode());
        result = prime * result + ((getIsDel() == null) ? 0 : getIsDel().hashCode());
        result = prime * result + ((geteUserId() == null) ? 0 : geteUserId().hashCode());
        result = prime * result + ((getTestHj() == null) ? 0 : getTestHj().hashCode());
        result = prime * result + ((getType() == null) ? 0 : getType().hashCode());
        return result;
    }
}