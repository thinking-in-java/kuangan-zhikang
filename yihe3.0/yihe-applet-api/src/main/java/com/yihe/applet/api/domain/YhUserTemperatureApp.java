package com.yihe.applet.api.domain;

import java.io.Serializable;

public class YhUserTemperatureApp implements Serializable {
    /**
     * 自增主键
     *
     * @mbg.generated
     */
    private Integer tid;

    /**
     * 体温
     *
     * @mbg.generated
     */
    private Float temperature;

    /**
     * 新增时间
     *
     * @mbg.generated
     */
    private String entryTime;

    /**
     * 是否删除
     *
     * @mbg.generated
     */
    private Integer isDel;

    /**
     * 用户
     *
     * @mbg.generated
     */
    private String eUserId;

    /**
     * 数据来源
     *
     * @mbg.generated
     */
    private String dataSource;

    String jieduInfo;
    String tiwenInfo;
    double progressvalueinfo;
    boolean openNo;

    public boolean isOpenNo() {
        return openNo;
    }

    public void setOpenNo(boolean openNo) {
        this.openNo = openNo;
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

    public double getProgressvalueinfo() {
        return progressvalueinfo;
    }

    public void setProgressvalueinfo(double progressvalueinfo) {
        this.progressvalueinfo = progressvalueinfo;
    }

    private static final long serialVersionUID = 1L;

    public Integer getTid() {
        return tid;
    }

    public void setTid(Integer tid) {
        this.tid = tid;
    }

    public Float getTemperature() {
        return temperature;
    }

    public void setTemperature(Float temperature) {
        this.temperature = temperature;
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

    public String geteUserId() {
        return eUserId;
    }

    public void seteUserId(String eUserId) {
        this.eUserId = eUserId;
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
        sb.append(", tid=").append(tid);
        sb.append(", temperature=").append(temperature);
        sb.append(", entryTime=").append(entryTime);
        sb.append(", isDel=").append(isDel);
        sb.append(", eUserId=").append(eUserId);
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
        YhUserTemperatureApp other = (YhUserTemperatureApp) that;
        return (this.getTid() == null ? other.getTid() == null : this.getTid().equals(other.getTid()))
            && (this.getTemperature() == null ? other.getTemperature() == null : this.getTemperature().equals(other.getTemperature()))
            && (this.getEntryTime() == null ? other.getEntryTime() == null : this.getEntryTime().equals(other.getEntryTime()))
            && (this.getIsDel() == null ? other.getIsDel() == null : this.getIsDel().equals(other.getIsDel()))
            && (this.geteUserId() == null ? other.geteUserId() == null : this.geteUserId().equals(other.geteUserId()))
            && (this.getDataSource() == null ? other.getDataSource() == null : this.getDataSource().equals(other.getDataSource()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getTid() == null) ? 0 : getTid().hashCode());
        result = prime * result + ((getTemperature() == null) ? 0 : getTemperature().hashCode());
        result = prime * result + ((getEntryTime() == null) ? 0 : getEntryTime().hashCode());
        result = prime * result + ((getIsDel() == null) ? 0 : getIsDel().hashCode());
        result = prime * result + ((geteUserId() == null) ? 0 : geteUserId().hashCode());
        result = prime * result + ((getDataSource() == null) ? 0 : getDataSource().hashCode());
        return result;
    }
}