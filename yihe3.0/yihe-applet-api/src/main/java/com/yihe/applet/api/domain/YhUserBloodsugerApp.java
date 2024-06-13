package com.yihe.applet.api.domain;

import java.io.Serializable;

public class YhUserBloodsugerApp implements Serializable {
    private Integer uBloodsugerId;

    /**
     * 血糖值
     *
     * @mbg.generated
     */
    private Float bs;

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

    /**
     * 数据来源
     *
     * @mbg.generated
     */
    private String dataSource;

    private  String jieduInfo;
    private  String tiwenInfo;
    private  double progressvalueinfo;

    private boolean openNo;

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

    public Integer getuBloodsugerId() {
        return uBloodsugerId;
    }

    public void setuBloodsugerId(Integer uBloodsugerId) {
        this.uBloodsugerId = uBloodsugerId;
    }

    public Float getBs() {
        return bs;
    }

    public void setBs(Float bs) {
        this.bs = bs;
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
        sb.append(", uBloodsugerId=").append(uBloodsugerId);
        sb.append(", bs=").append(bs);
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
        YhUserBloodsugerApp other = (YhUserBloodsugerApp) that;
        return (this.getuBloodsugerId() == null ? other.getuBloodsugerId() == null : this.getuBloodsugerId().equals(other.getuBloodsugerId()))
            && (this.getBs() == null ? other.getBs() == null : this.getBs().equals(other.getBs()))
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
        result = prime * result + ((getuBloodsugerId() == null) ? 0 : getuBloodsugerId().hashCode());
        result = prime * result + ((getBs() == null) ? 0 : getBs().hashCode());
        result = prime * result + ((getEntryTime() == null) ? 0 : getEntryTime().hashCode());
        result = prime * result + ((getIsDel() == null) ? 0 : getIsDel().hashCode());
        result = prime * result + ((geteUserId() == null) ? 0 : geteUserId().hashCode());
        result = prime * result + ((getEnv() == null) ? 0 : getEnv().hashCode());
        result = prime * result + ((getDataSource() == null) ? 0 : getDataSource().hashCode());
        return result;
    }
}