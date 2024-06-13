package com.yihe.system.domain;

import java.io.Serializable;
import java.util.Date;

public class YhUserEnrolinfo implements Serializable {
    private Integer eUserId;

    /**
     * 仪器序列号
     *
     * @mbg.generated
     */
    private String seqInstrument;

    private String docRuserid;

    /**
     * 干预分组
     *
     * @mbg.generated
     */
    private String flagDgroup;

    private Integer sexCode;

    private Float height;

    private Float weight;

    /**
     * 正常运行时间
     *
     * @mbg.generated
     */
    private Date hwUptime;

    /**
     * 腰围
     *
     * @mbg.generated
     */
    private Float waistc;

    /**
     * W正常运行时间
     *
     * @mbg.generated
     */
    private Date wUptime;

    private Float hipc;

    private Date hUptime;
    
    private Date evaluateFlagUptime;

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

    private Date sdUptime;

    private Float bs;

    private Date bsUptime;

    private Float cholesterin;

    private Float tring;

    private Float hdl;

    private Float ldl;

    private Date bfUptime;

    private Date fooddiaryUptime;

    private Date sportdiaryUptime;

    private Date foodmonUptime;

    private Date energyUptime;

    private Date lifestyleUptime;

    private Date healthMonitoring;

    private Integer isDel;

    private String userCode;

    
    
    /**
     * 是否干预
     *
     * @mbg.generated
     */
    private Integer evaluateflag;

    private Integer lVentricularHype;

    /**
     * 健康管理包ID
     *
     * @mbg.generated
     */
    private String measureCard;

    private static final long serialVersionUID = 1L;
    
    

    public Date getEvaluateFlagUptime() {
		return evaluateFlagUptime;
	}

	public void setEvaluateFlagUptime(Date evaluateFlagUptime) {
		this.evaluateFlagUptime = evaluateFlagUptime;
	}

	public Integer geteUserId() {
        return eUserId;
    }

    public void seteUserId(Integer eUserId) {
        this.eUserId = eUserId;
    }

    public String getSeqInstrument() {
        return seqInstrument;
    }

    public void setSeqInstrument(String seqInstrument) {
        this.seqInstrument = seqInstrument;
    }

    public String getDocRuserid() {
        return docRuserid;
    }

    public void setDocRuserid(String docRuserid) {
        this.docRuserid = docRuserid;
    }

    public String getFlagDgroup() {
        return flagDgroup;
    }

    public void setFlagDgroup(String flagDgroup) {
        this.flagDgroup = flagDgroup;
    }

    public Integer getSexCode() {
        return sexCode;
    }

    public void setSexCode(Integer sexCode) {
        this.sexCode = sexCode;
    }

    public Float getHeight() {
        return height;
    }

    public void setHeight(Float height) {
        this.height = height;
    }

    public Float getWeight() {
        return weight;
    }

    public void setWeight(Float weight) {
        this.weight = weight;
    }

    public Date getHwUptime() {
        return hwUptime;
    }

    public void setHwUptime(Date hwUptime) {
        this.hwUptime = hwUptime;
    }

    public Float getWaistc() {
        return waistc;
    }

    public void setWaistc(Float waistc) {
        this.waistc = waistc;
    }

    public Date getwUptime() {
        return wUptime;
    }

    public void setwUptime(Date wUptime) {
        this.wUptime = wUptime;
    }

    public Float getHipc() {
        return hipc;
    }

    public void setHipc(Float hipc) {
        this.hipc = hipc;
    }

    public Date gethUptime() {
        return hUptime;
    }

    public void sethUptime(Date hUptime) {
        this.hUptime = hUptime;
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

    public Date getSdUptime() {
        return sdUptime;
    }

    public void setSdUptime(Date sdUptime) {
        this.sdUptime = sdUptime;
    }

    public Float getBs() {
        return bs;
    }

    public void setBs(Float bs) {
        this.bs = bs;
    }

    public Date getBsUptime() {
        return bsUptime;
    }

    public void setBsUptime(Date bsUptime) {
        this.bsUptime = bsUptime;
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

    public Date getBfUptime() {
        return bfUptime;
    }

    public void setBfUptime(Date bfUptime) {
        this.bfUptime = bfUptime;
    }

    public Date getFooddiaryUptime() {
        return fooddiaryUptime;
    }

    public void setFooddiaryUptime(Date fooddiaryUptime) {
        this.fooddiaryUptime = fooddiaryUptime;
    }

    public Date getSportdiaryUptime() {
        return sportdiaryUptime;
    }

    public void setSportdiaryUptime(Date sportdiaryUptime) {
        this.sportdiaryUptime = sportdiaryUptime;
    }

    public Date getFoodmonUptime() {
        return foodmonUptime;
    }

    public void setFoodmonUptime(Date foodmonUptime) {
        this.foodmonUptime = foodmonUptime;
    }

    public Date getEnergyUptime() {
        return energyUptime;
    }

    public void setEnergyUptime(Date energyUptime) {
        this.energyUptime = energyUptime;
    }

    public Date getLifestyleUptime() {
        return lifestyleUptime;
    }

    public void setLifestyleUptime(Date lifestyleUptime) {
        this.lifestyleUptime = lifestyleUptime;
    }

    public Date getHealthMonitoring() {
        return healthMonitoring;
    }

    public void setHealthMonitoring(Date healthMonitoring) {
        this.healthMonitoring = healthMonitoring;
    }

    public Integer getIsDel() {
        return isDel;
    }

    public void setIsDel(Integer isDel) {
        this.isDel = isDel;
    }

    public String getUserCode() {
        return userCode;
    }

    public void setUserCode(String userCode) {
        this.userCode = userCode;
    }

    public Integer getEvaluateflag() {
        return evaluateflag;
    }

    public void setEvaluateflag(Integer evaluateflag) {
        this.evaluateflag = evaluateflag;
    }

    public Integer getlVentricularHype() {
        return lVentricularHype;
    }

    public void setlVentricularHype(Integer lVentricularHype) {
        this.lVentricularHype = lVentricularHype;
    }

    public String getMeasureCard() {
        return measureCard;
    }

    public void setMeasureCard(String measureCard) {
        this.measureCard = measureCard;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", eUserId=").append(eUserId);
        sb.append(", seqInstrument=").append(seqInstrument);
        sb.append(", docRuserid=").append(docRuserid);
        sb.append(", flagDgroup=").append(flagDgroup);
        sb.append(", sexCode=").append(sexCode);
        sb.append(", height=").append(height);
        sb.append(", weight=").append(weight);
        sb.append(", hwUptime=").append(hwUptime);
        sb.append(", waistc=").append(waistc);
        sb.append(", wUptime=").append(wUptime);
        sb.append(", hipc=").append(hipc);
        sb.append(", hUptime=").append(hUptime);
        sb.append(", sbp=").append(sbp);
        sb.append(", dbp=").append(dbp);
        sb.append(", sdUptime=").append(sdUptime);
        sb.append(", bs=").append(bs);
        sb.append(", bsUptime=").append(bsUptime);
        sb.append(", cholesterin=").append(cholesterin);
        sb.append(", tring=").append(tring);
        sb.append(", hdl=").append(hdl);
        sb.append(", ldl=").append(ldl);
        sb.append(", bfUptime=").append(bfUptime);
        sb.append(", fooddiaryUptime=").append(fooddiaryUptime);
        sb.append(", sportdiaryUptime=").append(sportdiaryUptime);
        sb.append(", foodmonUptime=").append(foodmonUptime);
        sb.append(", energyUptime=").append(energyUptime);
        sb.append(", lifestyleUptime=").append(lifestyleUptime);
        sb.append(", healthMonitoring=").append(healthMonitoring);
        sb.append(", isDel=").append(isDel);
        sb.append(", userCode=").append(userCode);
        sb.append(", evaluateflag=").append(evaluateflag);
        sb.append(", lVentricularHype=").append(lVentricularHype);
        sb.append(", measureCard=").append(measureCard);
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
        YhUserEnrolinfo other = (YhUserEnrolinfo) that;
        return (this.geteUserId() == null ? other.geteUserId() == null : this.geteUserId().equals(other.geteUserId()))
            && (this.getSeqInstrument() == null ? other.getSeqInstrument() == null : this.getSeqInstrument().equals(other.getSeqInstrument()))
            && (this.getDocRuserid() == null ? other.getDocRuserid() == null : this.getDocRuserid().equals(other.getDocRuserid()))
            && (this.getFlagDgroup() == null ? other.getFlagDgroup() == null : this.getFlagDgroup().equals(other.getFlagDgroup()))
            && (this.getSexCode() == null ? other.getSexCode() == null : this.getSexCode().equals(other.getSexCode()))
            && (this.getHeight() == null ? other.getHeight() == null : this.getHeight().equals(other.getHeight()))
            && (this.getWeight() == null ? other.getWeight() == null : this.getWeight().equals(other.getWeight()))
            && (this.getHwUptime() == null ? other.getHwUptime() == null : this.getHwUptime().equals(other.getHwUptime()))
            && (this.getWaistc() == null ? other.getWaistc() == null : this.getWaistc().equals(other.getWaistc()))
            && (this.getwUptime() == null ? other.getwUptime() == null : this.getwUptime().equals(other.getwUptime()))
            && (this.getHipc() == null ? other.getHipc() == null : this.getHipc().equals(other.getHipc()))
            && (this.gethUptime() == null ? other.gethUptime() == null : this.gethUptime().equals(other.gethUptime()))
            && (this.getSbp() == null ? other.getSbp() == null : this.getSbp().equals(other.getSbp()))
            && (this.getDbp() == null ? other.getDbp() == null : this.getDbp().equals(other.getDbp()))
            && (this.getSdUptime() == null ? other.getSdUptime() == null : this.getSdUptime().equals(other.getSdUptime()))
            && (this.getBs() == null ? other.getBs() == null : this.getBs().equals(other.getBs()))
            && (this.getBsUptime() == null ? other.getBsUptime() == null : this.getBsUptime().equals(other.getBsUptime()))
            && (this.getCholesterin() == null ? other.getCholesterin() == null : this.getCholesterin().equals(other.getCholesterin()))
            && (this.getTring() == null ? other.getTring() == null : this.getTring().equals(other.getTring()))
            && (this.getHdl() == null ? other.getHdl() == null : this.getHdl().equals(other.getHdl()))
            && (this.getLdl() == null ? other.getLdl() == null : this.getLdl().equals(other.getLdl()))
            && (this.getBfUptime() == null ? other.getBfUptime() == null : this.getBfUptime().equals(other.getBfUptime()))
            && (this.getFooddiaryUptime() == null ? other.getFooddiaryUptime() == null : this.getFooddiaryUptime().equals(other.getFooddiaryUptime()))
            && (this.getSportdiaryUptime() == null ? other.getSportdiaryUptime() == null : this.getSportdiaryUptime().equals(other.getSportdiaryUptime()))
            && (this.getFoodmonUptime() == null ? other.getFoodmonUptime() == null : this.getFoodmonUptime().equals(other.getFoodmonUptime()))
            && (this.getEnergyUptime() == null ? other.getEnergyUptime() == null : this.getEnergyUptime().equals(other.getEnergyUptime()))
            && (this.getLifestyleUptime() == null ? other.getLifestyleUptime() == null : this.getLifestyleUptime().equals(other.getLifestyleUptime()))
            && (this.getHealthMonitoring() == null ? other.getHealthMonitoring() == null : this.getHealthMonitoring().equals(other.getHealthMonitoring()))
            && (this.getIsDel() == null ? other.getIsDel() == null : this.getIsDel().equals(other.getIsDel()))
            && (this.getUserCode() == null ? other.getUserCode() == null : this.getUserCode().equals(other.getUserCode()))
            && (this.getEvaluateflag() == null ? other.getEvaluateflag() == null : this.getEvaluateflag().equals(other.getEvaluateflag()))
            && (this.getlVentricularHype() == null ? other.getlVentricularHype() == null : this.getlVentricularHype().equals(other.getlVentricularHype()))
            && (this.getMeasureCard() == null ? other.getMeasureCard() == null : this.getMeasureCard().equals(other.getMeasureCard()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((geteUserId() == null) ? 0 : geteUserId().hashCode());
        result = prime * result + ((getSeqInstrument() == null) ? 0 : getSeqInstrument().hashCode());
        result = prime * result + ((getDocRuserid() == null) ? 0 : getDocRuserid().hashCode());
        result = prime * result + ((getFlagDgroup() == null) ? 0 : getFlagDgroup().hashCode());
        result = prime * result + ((getSexCode() == null) ? 0 : getSexCode().hashCode());
        result = prime * result + ((getHeight() == null) ? 0 : getHeight().hashCode());
        result = prime * result + ((getWeight() == null) ? 0 : getWeight().hashCode());
        result = prime * result + ((getHwUptime() == null) ? 0 : getHwUptime().hashCode());
        result = prime * result + ((getWaistc() == null) ? 0 : getWaistc().hashCode());
        result = prime * result + ((getwUptime() == null) ? 0 : getwUptime().hashCode());
        result = prime * result + ((getHipc() == null) ? 0 : getHipc().hashCode());
        result = prime * result + ((gethUptime() == null) ? 0 : gethUptime().hashCode());
        result = prime * result + ((getSbp() == null) ? 0 : getSbp().hashCode());
        result = prime * result + ((getDbp() == null) ? 0 : getDbp().hashCode());
        result = prime * result + ((getSdUptime() == null) ? 0 : getSdUptime().hashCode());
        result = prime * result + ((getBs() == null) ? 0 : getBs().hashCode());
        result = prime * result + ((getBsUptime() == null) ? 0 : getBsUptime().hashCode());
        result = prime * result + ((getCholesterin() == null) ? 0 : getCholesterin().hashCode());
        result = prime * result + ((getTring() == null) ? 0 : getTring().hashCode());
        result = prime * result + ((getHdl() == null) ? 0 : getHdl().hashCode());
        result = prime * result + ((getLdl() == null) ? 0 : getLdl().hashCode());
        result = prime * result + ((getBfUptime() == null) ? 0 : getBfUptime().hashCode());
        result = prime * result + ((getFooddiaryUptime() == null) ? 0 : getFooddiaryUptime().hashCode());
        result = prime * result + ((getSportdiaryUptime() == null) ? 0 : getSportdiaryUptime().hashCode());
        result = prime * result + ((getFoodmonUptime() == null) ? 0 : getFoodmonUptime().hashCode());
        result = prime * result + ((getEnergyUptime() == null) ? 0 : getEnergyUptime().hashCode());
        result = prime * result + ((getLifestyleUptime() == null) ? 0 : getLifestyleUptime().hashCode());
        result = prime * result + ((getHealthMonitoring() == null) ? 0 : getHealthMonitoring().hashCode());
        result = prime * result + ((getIsDel() == null) ? 0 : getIsDel().hashCode());
        result = prime * result + ((getUserCode() == null) ? 0 : getUserCode().hashCode());
        result = prime * result + ((getEvaluateflag() == null) ? 0 : getEvaluateflag().hashCode());
        result = prime * result + ((getlVentricularHype() == null) ? 0 : getlVentricularHype().hashCode());
        result = prime * result + ((getMeasureCard() == null) ? 0 : getMeasureCard().hashCode());
        return result;
    }
}