package com.yihe.system.domain;

import java.io.Serializable;

public class YhDiseaseCategory implements Serializable {
    private Integer id;

    private Integer diseaseCode;
    private Integer isAssessment;

    private String diseaseName;

    private String riskGroup;
    

    public Integer getIsAssessment() {
		return isAssessment;
	}

	public void setIsAssessment(Integer isAssessment) {
		this.isAssessment = isAssessment;
	}

	private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getDiseaseCode() {
        return diseaseCode;
    }

    public void setDiseaseCode(Integer diseaseCode) {
        this.diseaseCode = diseaseCode;
    }

    public String getDiseaseName() {
        return diseaseName;
    }

    public void setDiseaseName(String diseaseName) {
        this.diseaseName = diseaseName;
    }

    public String getRiskGroup() {
        return riskGroup;
    }

    public void setRiskGroup(String riskGroup) {
        this.riskGroup = riskGroup;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", diseaseCode=").append(diseaseCode);
        sb.append(", diseaseName=").append(diseaseName);
        sb.append(", riskGroup=").append(riskGroup);
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
        YhDiseaseCategory other = (YhDiseaseCategory) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getDiseaseCode() == null ? other.getDiseaseCode() == null : this.getDiseaseCode().equals(other.getDiseaseCode()))
            && (this.getDiseaseName() == null ? other.getDiseaseName() == null : this.getDiseaseName().equals(other.getDiseaseName()))
            && (this.getRiskGroup() == null ? other.getRiskGroup() == null : this.getRiskGroup().equals(other.getRiskGroup()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getDiseaseCode() == null) ? 0 : getDiseaseCode().hashCode());
        result = prime * result + ((getDiseaseName() == null) ? 0 : getDiseaseName().hashCode());
        result = prime * result + ((getRiskGroup() == null) ? 0 : getRiskGroup().hashCode());
        return result;
    }
}