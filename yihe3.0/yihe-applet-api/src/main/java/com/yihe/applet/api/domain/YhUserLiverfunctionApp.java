package com.yihe.applet.api.domain;

import java.io.Serializable;

public class YhUserLiverfunctionApp implements Serializable {
    /**
     * 肝功能id
     *
     * @mbg.generated
     */
    private Integer liverid;

    /**
     * 谷丙转氨酶
     *
     * @mbg.generated
     */
    private String guBing;

    /**
     * 谷草转氨酶
     *
     * @mbg.generated
     */
    private String milletStraw;

    /**
     * 血清白蛋白
     *
     * @mbg.generated
     */
    private String serumAlbumin;

    /**
     * 血清球蛋白
     *
     * @mbg.generated
     */
    private String serumGlobulin;

    /**
     * 用户id
     *
     * @mbg.generated
     */
    private Integer userId;

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

    private String jiedu;

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

    public Integer getLiverid() {
        return liverid;
    }

    public void setLiverid(Integer liverid) {
        this.liverid = liverid;
    }

    public String getGuBing() {
        return guBing;
    }

    public void setGuBing(String guBing) {
        this.guBing = guBing;
    }

    public String getMilletStraw() {
        return milletStraw;
    }

    public void setMilletStraw(String milletStraw) {
        this.milletStraw = milletStraw;
    }

    public String getSerumAlbumin() {
        return serumAlbumin;
    }

    public void setSerumAlbumin(String serumAlbumin) {
        this.serumAlbumin = serumAlbumin;
    }

    public String getSerumGlobulin() {
        return serumGlobulin;
    }

    public void setSerumGlobulin(String serumGlobulin) {
        this.serumGlobulin = serumGlobulin;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
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
        sb.append(", liverid=").append(liverid);
        sb.append(", guBing=").append(guBing);
        sb.append(", milletStraw=").append(milletStraw);
        sb.append(", serumAlbumin=").append(serumAlbumin);
        sb.append(", serumGlobulin=").append(serumGlobulin);
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
        YhUserLiverfunctionApp other = (YhUserLiverfunctionApp) that;
        return (this.getLiverid() == null ? other.getLiverid() == null : this.getLiverid().equals(other.getLiverid()))
            && (this.getGuBing() == null ? other.getGuBing() == null : this.getGuBing().equals(other.getGuBing()))
            && (this.getMilletStraw() == null ? other.getMilletStraw() == null : this.getMilletStraw().equals(other.getMilletStraw()))
            && (this.getSerumAlbumin() == null ? other.getSerumAlbumin() == null : this.getSerumAlbumin().equals(other.getSerumAlbumin()))
            && (this.getSerumGlobulin() == null ? other.getSerumGlobulin() == null : this.getSerumGlobulin().equals(other.getSerumGlobulin()))
            && (this.getUserId() == null ? other.getUserId() == null : this.getUserId().equals(other.getUserId()))
            && (this.getInserttime() == null ? other.getInserttime() == null : this.getInserttime().equals(other.getInserttime()))
            && (this.getIsDel() == null ? other.getIsDel() == null : this.getIsDel().equals(other.getIsDel()))
            && (this.getDataSource() == null ? other.getDataSource() == null : this.getDataSource().equals(other.getDataSource()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getLiverid() == null) ? 0 : getLiverid().hashCode());
        result = prime * result + ((getGuBing() == null) ? 0 : getGuBing().hashCode());
        result = prime * result + ((getMilletStraw() == null) ? 0 : getMilletStraw().hashCode());
        result = prime * result + ((getSerumAlbumin() == null) ? 0 : getSerumAlbumin().hashCode());
        result = prime * result + ((getSerumGlobulin() == null) ? 0 : getSerumGlobulin().hashCode());
        result = prime * result + ((getUserId() == null) ? 0 : getUserId().hashCode());
        result = prime * result + ((getInserttime() == null) ? 0 : getInserttime().hashCode());
        result = prime * result + ((getIsDel() == null) ? 0 : getIsDel().hashCode());
        result = prime * result + ((getDataSource() == null) ? 0 : getDataSource().hashCode());
        return result;
    }
}