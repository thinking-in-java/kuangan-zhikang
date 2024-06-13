package com.yihe.system.domain;

import com.jayway.jsonpath.JsonPath;

public class YHDiseaseRiskOptimal {
    private Integer id;

    /**
     * 请求序列号
     *
     * @mbg.generated
     */
    private String requestSerial;

    private String depart;

    private Integer sno;

    private String qname;

    private String qcode;

    /**
     * 理想值
     *
     * @mbg.generated
     */
    private String optimal;

    private Float weight;

    /**
     * 范围说明
     *
     * @mbg.generated
     */
    private String remark;

    private String value;

    /**
     * 最小值范围
     *
     * @mbg.generated
     */
    private Double minv;

    /**
     * 最大值范围
     *
     * @mbg.generated
     */
    private Double maxv;

    private String qtype;

    private String sex;

    /**
     * 疾病ID
     *
     * @mbg.generated
     */
    private String diseaseid;

    /**
     * 字段名
     *
     * @mbg.generated
     */
    private String xpath;

    /**
     * 是否雷达图因素
     *
     * @mbg.generated
     */
    private Integer isradar;

    /**
     * 雷达图范围段
     *
     * @mbg.generated
     */
    private String radarRangePoint;

    /**
     * 雷达图范围值
     *
     * @mbg.generated
     */
    private String radarRangeName;
    
   
	private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getRequestSerial() {
        return requestSerial;
    }

    public void setRequestSerial(String requestSerial) {
        this.requestSerial = requestSerial;
    }

    public String getDepart() {
        return depart;
    }

    public void setDepart(String depart) {
        this.depart = depart;
    }

    public Integer getSno() {
        return sno;
    }

    public void setSno(Integer sno) {
        this.sno = sno;
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

    public String getOptimal() {
        return optimal;
    }

    public void setOptimal(String optimal) {
        this.optimal = optimal;
    }

    public Float getWeight() {
        return weight;
    }

    public void setWeight(Float weight) {
        this.weight = weight;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public Double getMinv() {
        return minv;
    }

    public void setMinv(Double minv) {
        this.minv = minv;
    }

    public Double getMaxv() {
        return maxv;
    }

    public void setMaxv(Double maxv) {
        this.maxv = maxv;
    }

    public String getQtype() {
        return qtype;
    }

    public void setQtype(String qtype) {
        this.qtype = qtype;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getDiseaseid() {
        return diseaseid;
    }

    public void setDiseaseid(String diseaseid) {
        this.diseaseid = diseaseid;
    }

    public String getXpath() {
        return xpath;
    }

    public void setXpath(String xpath) {
        this.xpath = xpath;
    }

    public Integer getIsradar() {
        return isradar;
    }

    public void setIsradar(Integer isradar) {
        this.isradar = isradar;
    }

    public String getRadarRangePoint() {
        return radarRangePoint;
    }

    public void setRadarRangePoint(String radarRangePoint) {
        this.radarRangePoint = radarRangePoint;
    }

    public String getRadarRangeName() {
        return radarRangeName;
    }

    public void setRadarRangeName(String radarRangeName) {
        this.radarRangeName = radarRangeName;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", requestSerial=").append(requestSerial);
        sb.append(", depart=").append(depart);
        sb.append(", sno=").append(sno);
        sb.append(", qname=").append(qname);
        sb.append(", qcode=").append(qcode);
        sb.append(", optimal=").append(optimal);
        sb.append(", weight=").append(weight);
        sb.append(", remark=").append(remark);
        sb.append(", value=").append(value);
        sb.append(", minv=").append(minv);
        sb.append(", maxv=").append(maxv);
        sb.append(", qtype=").append(qtype);
        sb.append(", sex=").append(sex);
        sb.append(", diseaseid=").append(diseaseid);
        sb.append(", xpath=").append(xpath);
        sb.append(", isradar=").append(isradar);
        sb.append(", radarRangePoint=").append(radarRangePoint);
        sb.append(", radarRangeName=").append(radarRangeName);
        sb.append("]");
        return sb.toString();
    }

   
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getRequestSerial() == null) ? 0 : getRequestSerial().hashCode());
        result = prime * result + ((getDepart() == null) ? 0 : getDepart().hashCode());
        result = prime * result + ((getSno() == null) ? 0 : getSno().hashCode());
        result = prime * result + ((getQname() == null) ? 0 : getQname().hashCode());
        result = prime * result + ((getQcode() == null) ? 0 : getQcode().hashCode());
        result = prime * result + ((getOptimal() == null) ? 0 : getOptimal().hashCode());
        result = prime * result + ((getWeight() == null) ? 0 : getWeight().hashCode());
        result = prime * result + ((getRemark() == null) ? 0 : getRemark().hashCode());
        result = prime * result + ((getValue() == null) ? 0 : getValue().hashCode());
        result = prime * result + ((getMinv() == null) ? 0 : getMinv().hashCode());
        result = prime * result + ((getMaxv() == null) ? 0 : getMaxv().hashCode());
        result = prime * result + ((getQtype() == null) ? 0 : getQtype().hashCode());
        result = prime * result + ((getSex() == null) ? 0 : getSex().hashCode());
        result = prime * result + ((getDiseaseid() == null) ? 0 : getDiseaseid().hashCode());
        result = prime * result + ((getXpath() == null) ? 0 : getXpath().hashCode());
        result = prime * result + ((getIsradar() == null) ? 0 : getIsradar().hashCode());
        result = prime * result + ((getRadarRangePoint() == null) ? 0 : getRadarRangePoint().hashCode());
        result = prime * result + ((getRadarRangeName() == null) ? 0 : getRadarRangeName().hashCode());
        return result;
    }
}
