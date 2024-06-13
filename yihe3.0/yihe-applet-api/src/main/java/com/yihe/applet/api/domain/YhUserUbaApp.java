package com.yihe.applet.api.domain;

import java.io.Serializable;

public class YhUserUbaApp implements Serializable {
    private Integer ubaId;

    /**
     * 测量日期
     *
     * @mbg.generated
     */
    private String adddate;

    /**
     * 测量环境
     *
     * @mbg.generated
     */
    private String env;

    public String getTiwen() {
        return tiwen;
    }

    public void setTiwen(String tiwen) {
        this.tiwen = tiwen;
    }

    public String getJiedu() {
        return jiedu;
    }

    public void setJiedu(String jiedu) {
        this.jiedu = jiedu;
    }

    public boolean isOpenNo() {
        return openNo;
    }

    public void setOpenNo(boolean openNo) {
        this.openNo = openNo;
    }

    /**
     * 葡萄糖
     *
     * @mbg.generated
     */
    private String glucose;

    /**
     * 胆红素
     *
     * @mbg.generated
     */
    private String bilirubin;

    /**
     * 酮体
     *
     * @mbg.generated
     */
    private String ketone;

    /**
     * 比重
     *
     * @mbg.generated
     */
    private String proportion;

    /**
     * 红细胞
     *
     * @mbg.generated
     */
    private String redbloodcell;

    /**
     * ph值
     *
     * @mbg.generated
     */
    private String ph;

    /**
     * 尿蛋白
     *
     * @mbg.generated
     */
    private String urineprotein;

    /**
     * 尿胆原
     *
     * @mbg.generated
     */
    private String urobilinogen;

    /**
     * 亚硝酸盐
     *
     * @mbg.generated
     */
    private String nitrite;

    /**
     * 白细胞
     *
     * @mbg.generated
     */
    private String whitebloodcell;

    /**
     * 关联用户
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

    /**
     * 肌酐
     *
     * @mbg.generated
     */
    private String creatinine;

    /**
     * 微白蛋白
     *
     * @mbg.generated
     */
    private String microalbumin;

    /**
     * 钙离子
     *
     * @mbg.generated
     */
    private String calciumIon;

    /**
     * 维生素
     *
     * @mbg.generated
     */
    private String vitamin;

    /**
     * 结果
     *
     * @mbg.generated
     */
    private String result;
    /**
     * 是否删除
     *
     * @mbg.generated
     */
    private Integer isDel;

    private String tiwen;

    private String jiedu;

    private boolean openNo;


    
    public Integer getIsDel() {
		return isDel;
	}

	public void setIsDel(Integer isDel) {
		this.isDel = isDel;
	}

	private static final long serialVersionUID = 1L;

    public Integer getUbaId() {
        return ubaId;
    }

    public void setUbaId(Integer ubaId) {
        this.ubaId = ubaId;
    }

    public String getAdddate() {
        return adddate;
    }

    public void setAdddate(String adddate) {
        this.adddate = adddate;
    }

    public String getEnv() {
        return env;
    }

    public void setEnv(String env) {
        this.env = env;
    }

    public String getGlucose() {
        return glucose;
    }

    public void setGlucose(String glucose) {
        this.glucose = glucose;
    }

    public String getBilirubin() {
        return bilirubin;
    }

    public void setBilirubin(String bilirubin) {
        this.bilirubin = bilirubin;
    }

    public String getKetone() {
        return ketone;
    }

    public void setKetone(String ketone) {
        this.ketone = ketone;
    }

    public String getProportion() {
        return proportion;
    }

    public void setProportion(String proportion) {
        this.proportion = proportion;
    }

    public String getRedbloodcell() {
        return redbloodcell;
    }

    public void setRedbloodcell(String redbloodcell) {
        this.redbloodcell = redbloodcell;
    }

    public String getPh() {
        return ph;
    }

    public void setPh(String ph) {
        this.ph = ph;
    }

    public String getUrineprotein() {
        return urineprotein;
    }

    public void setUrineprotein(String urineprotein) {
        this.urineprotein = urineprotein;
    }

    public String getUrobilinogen() {
        return urobilinogen;
    }

    public void setUrobilinogen(String urobilinogen) {
        this.urobilinogen = urobilinogen;
    }

    public String getNitrite() {
        return nitrite;
    }

    public void setNitrite(String nitrite) {
        this.nitrite = nitrite;
    }

    public String getWhitebloodcell() {
        return whitebloodcell;
    }

    public void setWhitebloodcell(String whitebloodcell) {
        this.whitebloodcell = whitebloodcell;
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

    public String getCreatinine() {
        return creatinine;
    }

    public void setCreatinine(String creatinine) {
        this.creatinine = creatinine;
    }

    public String getMicroalbumin() {
        return microalbumin;
    }

    public void setMicroalbumin(String microalbumin) {
        this.microalbumin = microalbumin;
    }

    public String getCalciumIon() {
        return calciumIon;
    }

    public void setCalciumIon(String calciumIon) {
        this.calciumIon = calciumIon;
    }

    public String getVitamin() {
        return vitamin;
    }

    public void setVitamin(String vitamin) {
        this.vitamin = vitamin;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", ubaId=").append(ubaId);
        sb.append(", adddate=").append(adddate);
        sb.append(", env=").append(env);
        sb.append(", glucose=").append(glucose);
        sb.append(", bilirubin=").append(bilirubin);
        sb.append(", ketone=").append(ketone);
        sb.append(", proportion=").append(proportion);
        sb.append(", redbloodcell=").append(redbloodcell);
        sb.append(", ph=").append(ph);
        sb.append(", urineprotein=").append(urineprotein);
        sb.append(", urobilinogen=").append(urobilinogen);
        sb.append(", nitrite=").append(nitrite);
        sb.append(", whitebloodcell=").append(whitebloodcell);
        sb.append(", eUserId=").append(eUserId);
        sb.append(", dataSource=").append(dataSource);
        sb.append(", creatinine=").append(creatinine);
        sb.append(", microalbumin=").append(microalbumin);
        sb.append(", calciumIon=").append(calciumIon);
        sb.append(", vitamin=").append(vitamin);
        sb.append(", result=").append(result);
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
        YhUserUbaApp other = (YhUserUbaApp) that;
        return (this.getUbaId() == null ? other.getUbaId() == null : this.getUbaId().equals(other.getUbaId()))
            && (this.getAdddate() == null ? other.getAdddate() == null : this.getAdddate().equals(other.getAdddate()))
            && (this.getEnv() == null ? other.getEnv() == null : this.getEnv().equals(other.getEnv()))
            && (this.getGlucose() == null ? other.getGlucose() == null : this.getGlucose().equals(other.getGlucose()))
            && (this.getBilirubin() == null ? other.getBilirubin() == null : this.getBilirubin().equals(other.getBilirubin()))
            && (this.getKetone() == null ? other.getKetone() == null : this.getKetone().equals(other.getKetone()))
            && (this.getProportion() == null ? other.getProportion() == null : this.getProportion().equals(other.getProportion()))
            && (this.getRedbloodcell() == null ? other.getRedbloodcell() == null : this.getRedbloodcell().equals(other.getRedbloodcell()))
            && (this.getPh() == null ? other.getPh() == null : this.getPh().equals(other.getPh()))
            && (this.getUrineprotein() == null ? other.getUrineprotein() == null : this.getUrineprotein().equals(other.getUrineprotein()))
            && (this.getUrobilinogen() == null ? other.getUrobilinogen() == null : this.getUrobilinogen().equals(other.getUrobilinogen()))
            && (this.getNitrite() == null ? other.getNitrite() == null : this.getNitrite().equals(other.getNitrite()))
            && (this.getWhitebloodcell() == null ? other.getWhitebloodcell() == null : this.getWhitebloodcell().equals(other.getWhitebloodcell()))
            && (this.geteUserId() == null ? other.geteUserId() == null : this.geteUserId().equals(other.geteUserId()))
            && (this.getDataSource() == null ? other.getDataSource() == null : this.getDataSource().equals(other.getDataSource()))
            && (this.getCreatinine() == null ? other.getCreatinine() == null : this.getCreatinine().equals(other.getCreatinine()))
            && (this.getMicroalbumin() == null ? other.getMicroalbumin() == null : this.getMicroalbumin().equals(other.getMicroalbumin()))
            && (this.getCalciumIon() == null ? other.getCalciumIon() == null : this.getCalciumIon().equals(other.getCalciumIon()))
            && (this.getVitamin() == null ? other.getVitamin() == null : this.getVitamin().equals(other.getVitamin()))
            && (this.getResult() == null ? other.getResult() == null : this.getResult().equals(other.getResult()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getUbaId() == null) ? 0 : getUbaId().hashCode());
        result = prime * result + ((getAdddate() == null) ? 0 : getAdddate().hashCode());
        result = prime * result + ((getEnv() == null) ? 0 : getEnv().hashCode());
        result = prime * result + ((getGlucose() == null) ? 0 : getGlucose().hashCode());
        result = prime * result + ((getBilirubin() == null) ? 0 : getBilirubin().hashCode());
        result = prime * result + ((getKetone() == null) ? 0 : getKetone().hashCode());
        result = prime * result + ((getProportion() == null) ? 0 : getProportion().hashCode());
        result = prime * result + ((getRedbloodcell() == null) ? 0 : getRedbloodcell().hashCode());
        result = prime * result + ((getPh() == null) ? 0 : getPh().hashCode());
        result = prime * result + ((getUrineprotein() == null) ? 0 : getUrineprotein().hashCode());
        result = prime * result + ((getUrobilinogen() == null) ? 0 : getUrobilinogen().hashCode());
        result = prime * result + ((getNitrite() == null) ? 0 : getNitrite().hashCode());
        result = prime * result + ((getWhitebloodcell() == null) ? 0 : getWhitebloodcell().hashCode());
        result = prime * result + ((geteUserId() == null) ? 0 : geteUserId().hashCode());
        result = prime * result + ((getDataSource() == null) ? 0 : getDataSource().hashCode());
        result = prime * result + ((getCreatinine() == null) ? 0 : getCreatinine().hashCode());
        result = prime * result + ((getMicroalbumin() == null) ? 0 : getMicroalbumin().hashCode());
        result = prime * result + ((getCalciumIon() == null) ? 0 : getCalciumIon().hashCode());
        result = prime * result + ((getVitamin() == null) ? 0 : getVitamin().hashCode());
        result = prime * result + ((getResult() == null) ? 0 : getResult().hashCode());
        return result;
    }
}