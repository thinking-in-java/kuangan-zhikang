package com.yihe.system.domain;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Data
public class YhDiseaseAssessReport implements Serializable {
    private YhDiseaseRiskFactorGroups[] fxlist;
    //统计图
    private YhDiseaseRiskFactorGroups[] flist;

    private YhDiseaseRiskFactorGroupsVOJO[] gvojolist;

    private  List<YHDiseaseRiskOptimal> goodList;

    private  List<YHDiseaseRiskOptimal> nogoodList;
    //当前
    private Integer[] dangqianList;
    //理想
    private Integer[] lixiangList;
    //改善指南
    private List<YHDiseaseAssessResolve> glist;
    //相关因素集合
    private List<RiskFactorsVOJO> rlist;

    private String df_names;

    private String sk_names;

    private String ld_names;

    private int sign;

    private String waistStr;

    private double bmiStr;

    private Integer id;
    // 用户编码
    private String userCode;
    // 返回序列号
    private String resultSerialnum;
    // 评估生成时间
    private Date qtime;
    // 请求序列号
    private String requestSerialnum;
    // 疾病id
    private Integer diseasid;

    private String diseaseName;
    // 风险等级
    private String riskLevel;
    private String riskDesc;
    private String nowRiskDesc;
    // 当前风险值
    private String nowRiskValue;

    private String nowRiskLevel;
    // 上次风险值
    private String nowRiskLastValue;
    // 理想风险
    private String idealRiskLevel;
    private String idealRiskDesc;
    // 上次评估日期
    private String lastTime;
    // 20岁-29的预测值
    private String ageForecastR20;
    // 30岁-39的预测值
    private String ageForecastR30;
    // 40岁-49的预测值
    private String ageForecastR40;
    // 50岁-59的预测值
    private String ageForecastR50;
    // 60岁-69的预测值
    private String ageForecastR60;
    // 70岁-79的预测值
    private String ageForecastR70;
    // 大于80岁的预测值
    private String ageForecastR80;
    // 20岁-29的理想值
    private String ageOpForecastR20Op;
    // 30岁-39的理想值
    private String ageOpForecastR30Op;
    // 40岁-49的理想值
    private String ageOpForecastR40Op;
    // 50岁-59的理想值
    private String ageOpForecastR50Op;
    // 60岁-69的理想值
    private String ageOpForecastR60Op;
    //70岁-79的理想值
    private String ageOpForecastR70Op;
    //大于80岁的理想值
    private String ageOpForecastR80Op;
    //改善id
    private String improves;

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", userCode=").append(userCode);
        sb.append(", resultSerialnum=").append(resultSerialnum);
        sb.append(", qtime=").append(qtime);
        sb.append(", requestSerialnum=").append(requestSerialnum);
        sb.append(", diseasid=").append(diseasid);
        sb.append(", riskLevel=").append(riskLevel);
        sb.append(", riskDesc=").append(riskDesc);
        sb.append(", nowRiskDesc=").append(nowRiskDesc);
        sb.append(", nowRiskValue=").append(nowRiskValue);
        sb.append(", nowRiskLevel=").append(nowRiskLevel);
        sb.append(", nowRiskLastValue=").append(nowRiskLastValue);
        sb.append(", idealRiskLevel=").append(idealRiskLevel);
        sb.append(", idealRiskDesc=").append(idealRiskDesc);
        sb.append(", lastTime=").append(lastTime);
        sb.append(", ageForecastR20=").append(ageForecastR20);
        sb.append(", ageForecastR30=").append(ageForecastR30);
        sb.append(", ageForecastR40=").append(ageForecastR40);
        sb.append(", ageForecastR50=").append(ageForecastR50);
        sb.append(", ageForecastR60=").append(ageForecastR60);
        sb.append(", ageForecastR70=").append(ageForecastR70);
        sb.append(", ageForecastR80=").append(ageForecastR80);
        sb.append(", ageOpForecastR20Op=").append(ageOpForecastR20Op);
        sb.append(", ageOpForecastR30Op=").append(ageOpForecastR30Op);
        sb.append(", ageOpForecastR40Op=").append(ageOpForecastR40Op);
        sb.append(", ageOpForecastR50Op=").append(ageOpForecastR50Op);
        sb.append(", ageOpForecastR60Op=").append(ageOpForecastR60Op);
        sb.append(", ageOpForecastR70Op=").append(ageOpForecastR70Op);
        sb.append(", ageOpForecastR80Op=").append(ageOpForecastR80Op);
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
        YhDiseaseAssessReport other = (YhDiseaseAssessReport) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
                && (this.getUserCode() == null ? other.getUserCode() == null : this.getUserCode().equals(other.getUserCode()))
                && (this.getResultSerialnum() == null ? other.getResultSerialnum() == null : this.getResultSerialnum().equals(other.getResultSerialnum()))
                && (this.getQtime() == null ? other.getQtime() == null : this.getQtime().equals(other.getQtime()))
                && (this.getRequestSerialnum() == null ? other.getRequestSerialnum() == null : this.getRequestSerialnum().equals(other.getRequestSerialnum()))
                && (this.getDiseasid() == null ? other.getDiseasid() == null : this.getDiseasid().equals(other.getDiseasid()))
                && (this.getRiskLevel() == null ? other.getRiskLevel() == null : this.getRiskLevel().equals(other.getRiskLevel()))
                && (this.getRiskDesc() == null ? other.getRiskDesc() == null : this.getRiskDesc().equals(other.getRiskDesc()))
                && (this.getNowRiskDesc() == null ? other.getNowRiskDesc() == null : this.getNowRiskDesc().equals(other.getNowRiskDesc()))
                && (this.getNowRiskValue() == null ? other.getNowRiskValue() == null : this.getNowRiskValue().equals(other.getNowRiskValue()))
                && (this.getNowRiskLevel() == null ? other.getNowRiskLevel() == null : this.getNowRiskLevel().equals(other.getNowRiskLevel()))
                && (this.getNowRiskLastValue() == null ? other.getNowRiskLastValue() == null : this.getNowRiskLastValue().equals(other.getNowRiskLastValue()))
                && (this.getIdealRiskLevel() == null ? other.getIdealRiskLevel() == null : this.getIdealRiskLevel().equals(other.getIdealRiskLevel()))
                && (this.getIdealRiskDesc() == null ? other.getIdealRiskDesc() == null : this.getIdealRiskDesc().equals(other.getIdealRiskDesc()))
                && (this.getLastTime() == null ? other.getLastTime() == null : this.getLastTime().equals(other.getLastTime()))
                && (this.getAgeForecastR20() == null ? other.getAgeForecastR20() == null : this.getAgeForecastR20().equals(other.getAgeForecastR20()))
                && (this.getAgeForecastR30() == null ? other.getAgeForecastR30() == null : this.getAgeForecastR30().equals(other.getAgeForecastR30()))
                && (this.getAgeForecastR40() == null ? other.getAgeForecastR40() == null : this.getAgeForecastR40().equals(other.getAgeForecastR40()))
                && (this.getAgeForecastR50() == null ? other.getAgeForecastR50() == null : this.getAgeForecastR50().equals(other.getAgeForecastR50()))
                && (this.getAgeForecastR60() == null ? other.getAgeForecastR60() == null : this.getAgeForecastR60().equals(other.getAgeForecastR60()))
                && (this.getAgeForecastR70() == null ? other.getAgeForecastR70() == null : this.getAgeForecastR70().equals(other.getAgeForecastR70()))
                && (this.getAgeForecastR80() == null ? other.getAgeForecastR80() == null : this.getAgeForecastR80().equals(other.getAgeForecastR80()))
                && (this.getAgeOpForecastR20Op() == null ? other.getAgeOpForecastR20Op() == null : this.getAgeOpForecastR20Op().equals(other.getAgeOpForecastR20Op()))
                && (this.getAgeOpForecastR30Op() == null ? other.getAgeOpForecastR30Op() == null : this.getAgeOpForecastR30Op().equals(other.getAgeOpForecastR30Op()))
                && (this.getAgeOpForecastR40Op() == null ? other.getAgeOpForecastR40Op() == null : this.getAgeOpForecastR40Op().equals(other.getAgeOpForecastR40Op()))
                && (this.getAgeOpForecastR50Op() == null ? other.getAgeOpForecastR50Op() == null : this.getAgeOpForecastR50Op().equals(other.getAgeOpForecastR50Op()))
                && (this.getAgeOpForecastR60Op() == null ? other.getAgeOpForecastR60Op() == null : this.getAgeOpForecastR60Op().equals(other.getAgeOpForecastR60Op()))
                && (this.getAgeOpForecastR70Op() == null ? other.getAgeOpForecastR70Op() == null : this.getAgeOpForecastR70Op().equals(other.getAgeOpForecastR70Op()))
                && (this.getAgeOpForecastR80Op() == null ? other.getAgeOpForecastR80Op() == null : this.getAgeOpForecastR80Op().equals(other.getAgeOpForecastR80Op()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getUserCode() == null) ? 0 : getUserCode().hashCode());
        result = prime * result + ((getResultSerialnum() == null) ? 0 : getResultSerialnum().hashCode());
        result = prime * result + ((getQtime() == null) ? 0 : getQtime().hashCode());
        result = prime * result + ((getRequestSerialnum() == null) ? 0 : getRequestSerialnum().hashCode());
        result = prime * result + ((getDiseasid() == null) ? 0 : getDiseasid().hashCode());
        result = prime * result + ((getRiskLevel() == null) ? 0 : getRiskLevel().hashCode());
        result = prime * result + ((getRiskDesc() == null) ? 0 : getRiskDesc().hashCode());
        result = prime * result + ((getNowRiskDesc() == null) ? 0 : getNowRiskDesc().hashCode());
        result = prime * result + ((getNowRiskValue() == null) ? 0 : getNowRiskValue().hashCode());
        result = prime * result + ((getNowRiskLevel() == null) ? 0 : getNowRiskLevel().hashCode());
        result = prime * result + ((getNowRiskLastValue() == null) ? 0 : getNowRiskLastValue().hashCode());
        result = prime * result + ((getIdealRiskLevel() == null) ? 0 : getIdealRiskLevel().hashCode());
        result = prime * result + ((getIdealRiskDesc() == null) ? 0 : getIdealRiskDesc().hashCode());
        result = prime * result + ((getLastTime() == null) ? 0 : getLastTime().hashCode());
        result = prime * result + ((getAgeForecastR20() == null) ? 0 : getAgeForecastR20().hashCode());
        result = prime * result + ((getAgeForecastR30() == null) ? 0 : getAgeForecastR30().hashCode());
        result = prime * result + ((getAgeForecastR40() == null) ? 0 : getAgeForecastR40().hashCode());
        result = prime * result + ((getAgeForecastR50() == null) ? 0 : getAgeForecastR50().hashCode());
        result = prime * result + ((getAgeForecastR60() == null) ? 0 : getAgeForecastR60().hashCode());
        result = prime * result + ((getAgeForecastR70() == null) ? 0 : getAgeForecastR70().hashCode());
        result = prime * result + ((getAgeForecastR80() == null) ? 0 : getAgeForecastR80().hashCode());
        result = prime * result + ((getAgeOpForecastR20Op() == null) ? 0 : getAgeOpForecastR20Op().hashCode());
        result = prime * result + ((getAgeOpForecastR30Op() == null) ? 0 : getAgeOpForecastR30Op().hashCode());
        result = prime * result + ((getAgeOpForecastR40Op() == null) ? 0 : getAgeOpForecastR40Op().hashCode());
        result = prime * result + ((getAgeOpForecastR50Op() == null) ? 0 : getAgeOpForecastR50Op().hashCode());
        result = prime * result + ((getAgeOpForecastR60Op() == null) ? 0 : getAgeOpForecastR60Op().hashCode());
        result = prime * result + ((getAgeOpForecastR70Op() == null) ? 0 : getAgeOpForecastR70Op().hashCode());
        result = prime * result + ((getAgeOpForecastR80Op() == null) ? 0 : getAgeOpForecastR80Op().hashCode());
        return result;
    }
}