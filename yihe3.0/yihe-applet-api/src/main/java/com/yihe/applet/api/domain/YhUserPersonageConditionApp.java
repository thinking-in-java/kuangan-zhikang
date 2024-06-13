package com.yihe.applet.api.domain;



import java.io.Serializable;

public class YhUserPersonageConditionApp implements Serializable {
    private Integer id;

    private String userCode;

    /**
     * 腰臀比例
     *
     * @mbg.generated
     */
    private String waist;

    /**
     * 体质指数
     *
     * @mbg.generated
     */
    private String habitas;

    /**
     * 呼吸频率
     *
     * @mbg.generated
     */
    private String breathe;

    /**
     * 身高
     *
     * @mbg.generated
     */
    private String stature;

    /**
     * 体重
     *
     * @mbg.generated
     */
    private String weight;

    /**
     * 腰围
     *
     * @mbg.generated
     */
    private String waistline;

    /**
     * 臀围
     *
     * @mbg.generated
     */
    private String hipline;

    /**
     * 体温
     *
     * @mbg.generated
     */
    private String heat;

    /**
     * 脉搏
     *
     * @mbg.generated
     */
    private String palse;

    /**
     * 舒张压
     *
     * @mbg.generated
     */
    private String dbp;

    /**
     * 收缩压
     *
     * @mbg.generated
     */
    private String sbp;
    
    /**
     * 安静心率
     *
     * @mbg.generated
     */
    private String hrrest;

    private static final long serialVersionUID = 1L;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getUserCode() {
		return userCode;
	}

	public void setUserCode(String userCode) {
		this.userCode = userCode;
	}

	public String getWaist() {
		return waist;
	}

	public void setWaist(String waist) {
		this.waist = waist;
	}

	public String getHabitas() {
		return habitas;
	}

	public void setHabitas(String habitas) {
		this.habitas = habitas;
	}

	public String getBreathe() {
		return breathe;
	}

	public void setBreathe(String breathe) {
		this.breathe = breathe;
	}

	public String getStature() {
		return stature;
	}

	public void setStature(String stature) {
		this.stature = stature;
	}

	public String getWeight() {
		return weight;
	}

	public void setWeight(String weight) {
		this.weight = weight;
	}

	public String getWaistline() {
		return waistline;
	}

	public void setWaistline(String waistline) {
		this.waistline = waistline;
	}

	public String getHipline() {
		return hipline;
	}

	public void setHipline(String hipline) {
		this.hipline = hipline;
	}

	public String getHeat() {
		return heat;
	}

	public void setHeat(String heat) {
		this.heat = heat;
	}

	public String getPalse() {
		return palse;
	}

	public void setPalse(String palse) {
		this.palse = palse;
	}

	public String getDbp() {
		return dbp;
	}

	public void setDbp(String dbp) {
		this.dbp = dbp;
	}

	public String getSbp() {
		return sbp;
	}

	public void setSbp(String sbp) {
		this.sbp = sbp;
	}

	public String getHrrest() {
		return hrrest;
	}

	public void setHrrest(String hrrest) {
		this.hrrest = hrrest;
	}

	@Override
	public String toString() {
		return "YhUserPersonageCondition [id=" + id + ", userCode=" + userCode + ", waist=" + waist + ", habitas="
				+ habitas + ", breathe=" + breathe + ", stature=" + stature + ", weight=" + weight + ", waistline="
				+ waistline + ", hipline=" + hipline + ", heat=" + heat + ", palse=" + palse + ", dbp=" + dbp + ", sbp="
				+ sbp + ", hrrest=" + hrrest + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((breathe == null) ? 0 : breathe.hashCode());
		result = prime * result + ((dbp == null) ? 0 : dbp.hashCode());
		result = prime * result + ((habitas == null) ? 0 : habitas.hashCode());
		result = prime * result + ((heat == null) ? 0 : heat.hashCode());
		result = prime * result + ((hipline == null) ? 0 : hipline.hashCode());
		result = prime * result + ((hrrest == null) ? 0 : hrrest.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((palse == null) ? 0 : palse.hashCode());
		result = prime * result + ((sbp == null) ? 0 : sbp.hashCode());
		result = prime * result + ((stature == null) ? 0 : stature.hashCode());
		result = prime * result + ((userCode == null) ? 0 : userCode.hashCode());
		result = prime * result + ((waist == null) ? 0 : waist.hashCode());
		result = prime * result + ((waistline == null) ? 0 : waistline.hashCode());
		result = prime * result + ((weight == null) ? 0 : weight.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		YhUserPersonageConditionApp other = (YhUserPersonageConditionApp) obj;
		if (breathe == null) {
			if (other.breathe != null)
				return false;
		} else if (!breathe.equals(other.breathe))
			return false;
		if (dbp == null) {
			if (other.dbp != null)
				return false;
		} else if (!dbp.equals(other.dbp))
			return false;
		if (habitas == null) {
			if (other.habitas != null)
				return false;
		} else if (!habitas.equals(other.habitas))
			return false;
		if (heat == null) {
			if (other.heat != null)
				return false;
		} else if (!heat.equals(other.heat))
			return false;
		if (hipline == null) {
			if (other.hipline != null)
				return false;
		} else if (!hipline.equals(other.hipline))
			return false;
		if (hrrest == null) {
			if (other.hrrest != null)
				return false;
		} else if (!hrrest.equals(other.hrrest))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (palse == null) {
			if (other.palse != null)
				return false;
		} else if (!palse.equals(other.palse))
			return false;
		if (sbp == null) {
			if (other.sbp != null)
				return false;
		} else if (!sbp.equals(other.sbp))
			return false;
		if (stature == null) {
			if (other.stature != null)
				return false;
		} else if (!stature.equals(other.stature))
			return false;
		if (userCode == null) {
			if (other.userCode != null)
				return false;
		} else if (!userCode.equals(other.userCode))
			return false;
		if (waist == null) {
			if (other.waist != null)
				return false;
		} else if (!waist.equals(other.waist))
			return false;
		if (waistline == null) {
			if (other.waistline != null)
				return false;
		} else if (!waistline.equals(other.waistline))
			return false;
		if (weight == null) {
			if (other.weight != null)
				return false;
		} else if (!weight.equals(other.weight))
			return false;
		return true;
	}


}