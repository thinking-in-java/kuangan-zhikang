package com.yihe.applet.api.domain;

import java.io.Serializable;

public class YhUserOtherfunctionApp implements Serializable {
    /**
     * 其他功能id
     *
     * @mbg.generated
     */
    private Integer otherid;

    /**
     * 尿微量蛋白
     *
     * @mbg.generated
     */
    private String urineMicroprotein;

    /**
     * 糖化血红蛋白
     *
     * @mbg.generated
     */
    private String glycosylatedHemoglobin;

    /**
     * 同型半胱氨酸
     *
     * @mbg.generated
     */
    private String hcy;

    /**
     * 用户编号id
     *
     * @mbg.generated
     */
    private String userId;

    /**
     * 新增时间
     *
     * @mbg.generated
     */
    private String inserttime;

    /**
     * 是否删除
     *
     * @mbg.generated
     */
    private Integer isDel;

    /**
     * 数据来源
     *
     * @mbg.generated
     */
    private String dataSource;

    private boolean openNo;

    private  String jiedu;

    private String tiwen;

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

    public String getTiwen() {
        return tiwen;
    }

    public void setTiwen(String tiwen) {
        this.tiwen = tiwen;
    }

    private static final long serialVersionUID = 1L;

    public Integer getOtherid() {
        return otherid;
    }

    public void setOtherid(Integer otherid) {
        this.otherid = otherid;
    }

    public String getUrineMicroprotein() {
        return urineMicroprotein;
    }

    public void setUrineMicroprotein(String urineMicroprotein) {
        this.urineMicroprotein = urineMicroprotein;
    }

    public String getGlycosylatedHemoglobin() {
        return glycosylatedHemoglobin;
    }

    public void setGlycosylatedHemoglobin(String glycosylatedHemoglobin) {
        this.glycosylatedHemoglobin = glycosylatedHemoglobin;
    }

    public String getHcy() {
        return hcy;
    }

    public void setHcy(String hcy) {
        this.hcy = hcy;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getInserttime() {
        return inserttime;
    }

    public void setInserttime(String inserttime) {
        this.inserttime = inserttime;
    }

    public Integer getIsDel() {
        return isDel;
    }

    public void setIsDel(Integer isDel) {
        this.isDel = isDel;
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
        sb.append(", otherid=").append(otherid);
        sb.append(", urineMicroprotein=").append(urineMicroprotein);
        sb.append(", glycosylatedHemoglobin=").append(glycosylatedHemoglobin);
        sb.append(", hcy=").append(hcy);
        sb.append(", userId=").append(userId);
        sb.append(", inserttime=").append(inserttime);
        sb.append(", isDel=").append(isDel);
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
        YhUserOtherfunctionApp other = (YhUserOtherfunctionApp) that;
        return (this.getOtherid() == null ? other.getOtherid() == null : this.getOtherid().equals(other.getOtherid()))
            && (this.getUrineMicroprotein() == null ? other.getUrineMicroprotein() == null : this.getUrineMicroprotein().equals(other.getUrineMicroprotein()))
            && (this.getGlycosylatedHemoglobin() == null ? other.getGlycosylatedHemoglobin() == null : this.getGlycosylatedHemoglobin().equals(other.getGlycosylatedHemoglobin()))
            && (this.getHcy() == null ? other.getHcy() == null : this.getHcy().equals(other.getHcy()))
            && (this.getUserId() == null ? other.getUserId() == null : this.getUserId().equals(other.getUserId()))
            && (this.getInserttime() == null ? other.getInserttime() == null : this.getInserttime().equals(other.getInserttime()))
            && (this.getIsDel() == null ? other.getIsDel() == null : this.getIsDel().equals(other.getIsDel()))
            && (this.getDataSource() == null ? other.getDataSource() == null : this.getDataSource().equals(other.getDataSource()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getOtherid() == null) ? 0 : getOtherid().hashCode());
        result = prime * result + ((getUrineMicroprotein() == null) ? 0 : getUrineMicroprotein().hashCode());
        result = prime * result + ((getGlycosylatedHemoglobin() == null) ? 0 : getGlycosylatedHemoglobin().hashCode());
        result = prime * result + ((getHcy() == null) ? 0 : getHcy().hashCode());
        result = prime * result + ((getUserId() == null) ? 0 : getUserId().hashCode());
        result = prime * result + ((getInserttime() == null) ? 0 : getInserttime().hashCode());
        result = prime * result + ((getIsDel() == null) ? 0 : getIsDel().hashCode());
        result = prime * result + ((getDataSource() == null) ? 0 : getDataSource().hashCode());
        return result;
    }
}