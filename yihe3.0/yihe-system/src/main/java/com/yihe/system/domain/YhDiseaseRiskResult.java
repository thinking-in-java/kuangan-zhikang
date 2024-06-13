package com.yihe.system.domain;

import java.io.Serializable;
import java.util.Date;

public class YhDiseaseRiskResult implements Serializable {
    private Integer id;

    /**
     * 疾病ID
     *
     * @mbg.generated
     */
    private String diseaseid;

    /**
     * 用户id
     *
     * @mbg.generated
     */
    private String userCode;

    /**
     * 请求序列号
     *
     * @mbg.generated
     */
    private String requestSerialize;

    /**
     * 风险因素名
     *
     * @mbg.generated
     */
    private String qname;

    /**
     * 风险因素对应的字段名
     *
     * @mbg.generated
     */
    private String qcode;

    /**
     * 当前值
     *
     * @mbg.generated
     */
    private String qvalue;

    /**
     * 上次的值
     *
     * @mbg.generated
     */
    private String lastValue;

    /**
     * 变化的值
     *
     * @mbg.generated
     */
    private String changes;

    /**
     * 理想值
     *
     * @mbg.generated
     */
    private String optimal;

    private String remark;

    /**
     * 时间
     *
     * @mbg.generated
     */
    private Date qtime;

    private Integer isradar;

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDiseaseid() {
        return diseaseid;
    }

    public void setDiseaseid(String diseaseid) {
        this.diseaseid = diseaseid;
    }

    public String getUserCode() {
        return userCode;
    }

    public void setUserCode(String userCode) {
        this.userCode = userCode;
    }

    public String getRequestSerialize() {
        return requestSerialize;
    }

    public void setRequestSerialize(String requestSerialize) {
        this.requestSerialize = requestSerialize;
    }

    public String getQname() {
        return qname;
    }

    public void setQname(String qname) {
        this.qname = qname;
    }

    public String getQcode() {
        return qcode;
    }

    public void setQcode(String qcode) {
        this.qcode = qcode;
    }

    public String getQvalue() {
        return qvalue;
    }

    public void setQvalue(String qvalue) {
        this.qvalue = qvalue;
    }

    public String getLastValue() {
        return lastValue;
    }

    public void setLastValue(String lastValue) {
        this.lastValue = lastValue;
    }

    public String getChanges() {
        return changes;
    }

    public void setChanges(String changes) {
        this.changes = changes;
    }

    public String getOptimal() {
        return optimal;
    }

    public void setOptimal(String optimal) {
        this.optimal = optimal;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Date getQtime() {
        return qtime;
    }

    public void setQtime(Date qtime) {
        this.qtime = qtime;
    }

    public Integer getIsradar() {
        return isradar;
    }

    public void setIsradar(Integer isradar) {
        this.isradar = isradar;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", diseaseid=").append(diseaseid);
        sb.append(", userCode=").append(userCode);
        sb.append(", requestSerialize=").append(requestSerialize);
        sb.append(", qname=").append(qname);
        sb.append(", qcode=").append(qcode);
        sb.append(", qvalue=").append(qvalue);
        sb.append(", lastValue=").append(lastValue);
        sb.append(", changes=").append(changes);
        sb.append(", optimal=").append(optimal);
        sb.append(", remark=").append(remark);
        sb.append(", qtime=").append(qtime);
        sb.append(", isradar=").append(isradar);
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
        YhDiseaseRiskResult other = (YhDiseaseRiskResult) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getDiseaseid() == null ? other.getDiseaseid() == null : this.getDiseaseid().equals(other.getDiseaseid()))
            && (this.getUserCode() == null ? other.getUserCode() == null : this.getUserCode().equals(other.getUserCode()))
            && (this.getRequestSerialize() == null ? other.getRequestSerialize() == null : this.getRequestSerialize().equals(other.getRequestSerialize()))
            && (this.getQname() == null ? other.getQname() == null : this.getQname().equals(other.getQname()))
            && (this.getQcode() == null ? other.getQcode() == null : this.getQcode().equals(other.getQcode()))
            && (this.getQvalue() == null ? other.getQvalue() == null : this.getQvalue().equals(other.getQvalue()))
            && (this.getLastValue() == null ? other.getLastValue() == null : this.getLastValue().equals(other.getLastValue()))
            && (this.getChanges() == null ? other.getChanges() == null : this.getChanges().equals(other.getChanges()))
            && (this.getOptimal() == null ? other.getOptimal() == null : this.getOptimal().equals(other.getOptimal()))
            && (this.getRemark() == null ? other.getRemark() == null : this.getRemark().equals(other.getRemark()))
            && (this.getQtime() == null ? other.getQtime() == null : this.getQtime().equals(other.getQtime()))
            && (this.getIsradar() == null ? other.getIsradar() == null : this.getIsradar().equals(other.getIsradar()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getDiseaseid() == null) ? 0 : getDiseaseid().hashCode());
        result = prime * result + ((getUserCode() == null) ? 0 : getUserCode().hashCode());
        result = prime * result + ((getRequestSerialize() == null) ? 0 : getRequestSerialize().hashCode());
        result = prime * result + ((getQname() == null) ? 0 : getQname().hashCode());
        result = prime * result + ((getQcode() == null) ? 0 : getQcode().hashCode());
        result = prime * result + ((getQvalue() == null) ? 0 : getQvalue().hashCode());
        result = prime * result + ((getLastValue() == null) ? 0 : getLastValue().hashCode());
        result = prime * result + ((getChanges() == null) ? 0 : getChanges().hashCode());
        result = prime * result + ((getOptimal() == null) ? 0 : getOptimal().hashCode());
        result = prime * result + ((getRemark() == null) ? 0 : getRemark().hashCode());
        result = prime * result + ((getQtime() == null) ? 0 : getQtime().hashCode());
        result = prime * result + ((getIsradar() == null) ? 0 : getIsradar().hashCode());
        return result;
    }
}