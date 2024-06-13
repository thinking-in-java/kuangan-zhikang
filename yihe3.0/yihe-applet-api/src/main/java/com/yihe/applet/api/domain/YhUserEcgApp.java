package com.yihe.applet.api.domain;

import java.io.Serializable;

public class YhUserEcgApp implements Serializable {
    private Integer uEcgId;

    /**
     * 测量环境
     *
     * @mbg.generated
     */
    private String testHj;

    /**
     * 采样率，每秒钟采集多少次数据
     *
     * @mbg.generated
     */
    private Integer sr;

    /**
     * 采集节点总数
     *
     * @mbg.generated
     */
    private Integer tr;

    /**
     * 心率
     *
     * @mbg.generated
     */
    private Integer pulse;

    /**
     * 测量结果编码
     *
     * @mbg.generated
     */
    private Integer resultcode;

    /**
     * 心电文件位置
     *
     * @mbg.generated
     */
    private String xdValue;

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
     * 图片地址
     *
     * @mbg.generated
     */
    private String pigPath;

    /**
     * 测量结果说明
     *
     * @mbg.generated
     */
    private String resurtText;

    /**
     * 数据来源
     *
     * @mbg.generated
     */
    private String dataSource;

    private static final long serialVersionUID = 1L;

    public Integer getuEcgId() {
        return uEcgId;
    }

    public void setuEcgId(Integer uEcgId) {
        this.uEcgId = uEcgId;
    }

    public String getTestHj() {
        return testHj;
    }

    public void setTestHj(String testHj) {
        this.testHj = testHj;
    }

    public Integer getSr() {
        return sr;
    }

    public void setSr(Integer sr) {
        this.sr = sr;
    }

    public Integer getTr() {
        return tr;
    }

    public void setTr(Integer tr) {
        this.tr = tr;
    }

    public Integer getPulse() {
        return pulse;
    }

    public void setPulse(Integer pulse) {
        this.pulse = pulse;
    }

    public Integer getResultcode() {
        return resultcode;
    }

    public void setResultcode(Integer resultcode) {
        this.resultcode = resultcode;
    }

    public String getXdValue() {
        return xdValue;
    }

    public void setXdValue(String xdValue) {
        this.xdValue = xdValue;
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

    public String getPigPath() {
        return pigPath;
    }

    public void setPigPath(String pigPath) {
        this.pigPath = pigPath;
    }

    public String getResurtText() {
        return resurtText;
    }

    public void setResurtText(String resurtText) {
        this.resurtText = resurtText;
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
        sb.append(", uEcgId=").append(uEcgId);
        sb.append(", testHj=").append(testHj);
        sb.append(", sr=").append(sr);
        sb.append(", tr=").append(tr);
        sb.append(", pulse=").append(pulse);
        sb.append(", resultcode=").append(resultcode);
        sb.append(", xdValue=").append(xdValue);
        sb.append(", entryTime=").append(entryTime);
        sb.append(", isDel=").append(isDel);
        sb.append(", eUserId=").append(eUserId);
        sb.append(", pigPath=").append(pigPath);
        sb.append(", resurtText=").append(resurtText);
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
        YhUserEcgApp other = (YhUserEcgApp) that;
        return (this.getuEcgId() == null ? other.getuEcgId() == null : this.getuEcgId().equals(other.getuEcgId()))
            && (this.getTestHj() == null ? other.getTestHj() == null : this.getTestHj().equals(other.getTestHj()))
            && (this.getSr() == null ? other.getSr() == null : this.getSr().equals(other.getSr()))
            && (this.getTr() == null ? other.getTr() == null : this.getTr().equals(other.getTr()))
            && (this.getPulse() == null ? other.getPulse() == null : this.getPulse().equals(other.getPulse()))
            && (this.getResultcode() == null ? other.getResultcode() == null : this.getResultcode().equals(other.getResultcode()))
            && (this.getXdValue() == null ? other.getXdValue() == null : this.getXdValue().equals(other.getXdValue()))
            && (this.getEntryTime() == null ? other.getEntryTime() == null : this.getEntryTime().equals(other.getEntryTime()))
            && (this.getIsDel() == null ? other.getIsDel() == null : this.getIsDel().equals(other.getIsDel()))
            && (this.geteUserId() == null ? other.geteUserId() == null : this.geteUserId().equals(other.geteUserId()))
            && (this.getPigPath() == null ? other.getPigPath() == null : this.getPigPath().equals(other.getPigPath()))
            && (this.getResurtText() == null ? other.getResurtText() == null : this.getResurtText().equals(other.getResurtText()))
            && (this.getDataSource() == null ? other.getDataSource() == null : this.getDataSource().equals(other.getDataSource()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getuEcgId() == null) ? 0 : getuEcgId().hashCode());
        result = prime * result + ((getTestHj() == null) ? 0 : getTestHj().hashCode());
        result = prime * result + ((getSr() == null) ? 0 : getSr().hashCode());
        result = prime * result + ((getTr() == null) ? 0 : getTr().hashCode());
        result = prime * result + ((getPulse() == null) ? 0 : getPulse().hashCode());
        result = prime * result + ((getResultcode() == null) ? 0 : getResultcode().hashCode());
        result = prime * result + ((getXdValue() == null) ? 0 : getXdValue().hashCode());
        result = prime * result + ((getEntryTime() == null) ? 0 : getEntryTime().hashCode());
        result = prime * result + ((getIsDel() == null) ? 0 : getIsDel().hashCode());
        result = prime * result + ((geteUserId() == null) ? 0 : geteUserId().hashCode());
        result = prime * result + ((getPigPath() == null) ? 0 : getPigPath().hashCode());
        result = prime * result + ((getResurtText() == null) ? 0 : getResurtText().hashCode());
        result = prime * result + ((getDataSource() == null) ? 0 : getDataSource().hashCode());
        return result;
    }
}