package com.yihe.applet.api.domain;

import java.io.Serializable;
import java.util.Date;

public class YhUserBloodpressureApp implements Serializable {
    private Integer uBloodpressureId;

    /**
     * 收缩压
     *
     * @mbg.generated
     */
    private Integer sbp;

    /**
     * 舒张压
     *
     * @mbg.generated
     */
    private Integer dbp;

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
     * 心率
     *
     * @mbg.generated
     */
    private Integer xinlv;

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

    double progressvalueinfo;

    String jieduInfo;
    String tiwenInfo;

    boolean openNo;

    public boolean isOpenNo() {
        return openNo;
    }

    public void setOpenNo(boolean openNo) {
        this.openNo = openNo;
    }

    public double getProgressvalueinfo() {
        return progressvalueinfo;
    }

    public void setProgressvalueinfo(double progressvalueinfo) {
        this.progressvalueinfo = progressvalueinfo;
    }

    public String getJieduInfo() {
        return jieduInfo;
    }

    public void setJieduInfo(String jieduInfo) {
        this.jieduInfo = jieduInfo;
    }

    public String getTiwenInfo() {
        return tiwenInfo;
    }

    public void setTiwenInfo(String tiwenInfo) {
        this.tiwenInfo = tiwenInfo;
    }

    private static final long serialVersionUID = 1L;

    public Integer getuBloodpressureId() {
        return uBloodpressureId;
    }

    public void setuBloodpressureId(Integer uBloodpressureId) {
        this.uBloodpressureId = uBloodpressureId;
    }

    public Integer getSbp() {
        return sbp;
    }

    public void setSbp(Integer sbp) {
        this.sbp = sbp;
    }

    public Integer getDbp() {
        return dbp;
    }

    public void setDbp(Integer dbp) {
        this.dbp = dbp;
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

    public Integer getXinlv() {
        return xinlv;
    }

    public void setXinlv(Integer xinlv) {
        this.xinlv = xinlv;
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
        sb.append(", uBloodpressureId=").append(uBloodpressureId);
        sb.append(", sbp=").append(sbp);
        sb.append(", dbp=").append(dbp);
        sb.append(", entryTime=").append(entryTime);
        sb.append(", isDel=").append(isDel);
        sb.append(", eUserId=").append(eUserId);
        sb.append(", xinlv=").append(xinlv);
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
        YhUserBloodpressureApp other = (YhUserBloodpressureApp) that;
        return (this.getuBloodpressureId() == null ? other.getuBloodpressureId() == null : this.getuBloodpressureId().equals(other.getuBloodpressureId()))
            && (this.getSbp() == null ? other.getSbp() == null : this.getSbp().equals(other.getSbp()))
            && (this.getDbp() == null ? other.getDbp() == null : this.getDbp().equals(other.getDbp()))
            && (this.getEntryTime() == null ? other.getEntryTime() == null : this.getEntryTime().equals(other.getEntryTime()))
            && (this.getIsDel() == null ? other.getIsDel() == null : this.getIsDel().equals(other.getIsDel()))
            && (this.geteUserId() == null ? other.geteUserId() == null : this.geteUserId().equals(other.geteUserId()))
            && (this.getXinlv() == null ? other.getXinlv() == null : this.getXinlv().equals(other.getXinlv()))
            && (this.getTestHj() == null ? other.getTestHj() == null : this.getTestHj().equals(other.getTestHj()))
            && (this.getType() == null ? other.getType() == null : this.getType().equals(other.getType()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getuBloodpressureId() == null) ? 0 : getuBloodpressureId().hashCode());
        result = prime * result + ((getSbp() == null) ? 0 : getSbp().hashCode());
        result = prime * result + ((getDbp() == null) ? 0 : getDbp().hashCode());
        result = prime * result + ((getEntryTime() == null) ? 0 : getEntryTime().hashCode());
        result = prime * result + ((getIsDel() == null) ? 0 : getIsDel().hashCode());
        result = prime * result + ((geteUserId() == null) ? 0 : geteUserId().hashCode());
        result = prime * result + ((getXinlv() == null) ? 0 : getXinlv().hashCode());
        result = prime * result + ((getTestHj() == null) ? 0 : getTestHj().hashCode());
        result = prime * result + ((getType() == null) ? 0 : getType().hashCode());
        return result;
    }
}