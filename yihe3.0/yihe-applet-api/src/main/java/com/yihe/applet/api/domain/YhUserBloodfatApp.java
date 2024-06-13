package com.yihe.applet.api.domain;

import java.io.Serializable;
import java.util.Date;

public class YhUserBloodfatApp implements Serializable {
    private Integer uBloodfatId;

    /**
     * 总胆固醇
     *
     * @mbg.generated
     */
    private Float cholesterin;

    /**
     * 甘油三酯
     *
     * @mbg.generated
     */
    private Float tring;

    /**
     * 高密度脂蛋白胆固醇
     *
     * @mbg.generated
     */
    private Float hdl;

    /**
     * 低密度脂蛋白胆固醇
     *
     * @mbg.generated
     */
    private Float ldl;

    /**
     * 录入时间
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
     * 用户id
     *
     * @mbg.generated
     */
    private Integer eUserId;

    /**
     * 数据来源
     *
     * @mbg.generated
     */
    private String dataSource;

    private static final long serialVersionUID = 1L;

    public Integer getuBloodfatId() {
        return uBloodfatId;
    }

    public void setuBloodfatId(Integer uBloodfatId) {
        this.uBloodfatId = uBloodfatId;
    }

    public Float getCholesterin() {
        return cholesterin;
    }

    public void setCholesterin(Float cholesterin) {
        this.cholesterin = cholesterin;
    }

    public Float getTring() {
        return tring;
    }

    public void setTring(Float tring) {
        this.tring = tring;
    }

    public Float getHdl() {
        return hdl;
    }

    public void setHdl(Float hdl) {
        this.hdl = hdl;
    }

    public Float getLdl() {
        return ldl;
    }

    public void setLdl(Float ldl) {
        this.ldl = ldl;
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
        sb.append(", uBloodfatId=").append(uBloodfatId);
        sb.append(", cholesterin=").append(cholesterin);
        sb.append(", tring=").append(tring);
        sb.append(", hdl=").append(hdl);
        sb.append(", ldl=").append(ldl);
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
        YhUserBloodfatApp other = (YhUserBloodfatApp) that;
        return (this.getuBloodfatId() == null ? other.getuBloodfatId() == null : this.getuBloodfatId().equals(other.getuBloodfatId()))
            && (this.getCholesterin() == null ? other.getCholesterin() == null : this.getCholesterin().equals(other.getCholesterin()))
            && (this.getTring() == null ? other.getTring() == null : this.getTring().equals(other.getTring()))
            && (this.getHdl() == null ? other.getHdl() == null : this.getHdl().equals(other.getHdl()))
            && (this.getLdl() == null ? other.getLdl() == null : this.getLdl().equals(other.getLdl()))
            && (this.getEntryTime() == null ? other.getEntryTime() == null : this.getEntryTime().equals(other.getEntryTime()))
            && (this.getIsDel() == null ? other.getIsDel() == null : this.getIsDel().equals(other.getIsDel()))
            && (this.geteUserId() == null ? other.geteUserId() == null : this.geteUserId().equals(other.geteUserId()))
            && (this.getDataSource() == null ? other.getDataSource() == null : this.getDataSource().equals(other.getDataSource()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getuBloodfatId() == null) ? 0 : getuBloodfatId().hashCode());
        result = prime * result + ((getCholesterin() == null) ? 0 : getCholesterin().hashCode());
        result = prime * result + ((getTring() == null) ? 0 : getTring().hashCode());
        result = prime * result + ((getHdl() == null) ? 0 : getHdl().hashCode());
        result = prime * result + ((getLdl() == null) ? 0 : getLdl().hashCode());
        result = prime * result + ((getEntryTime() == null) ? 0 : getEntryTime().hashCode());
        result = prime * result + ((getIsDel() == null) ? 0 : getIsDel().hashCode());
        result = prime * result + ((geteUserId() == null) ? 0 : geteUserId().hashCode());
        result = prime * result + ((getDataSource() == null) ? 0 : getDataSource().hashCode());
        return result;
    }
}