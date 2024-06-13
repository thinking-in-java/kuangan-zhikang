package com.yihe.system.domain;

import java.io.Serializable;

public class YhSportRecommendRecordVojo implements Serializable {
    private Integer id;

    private String userCode;

    /**
     * 疾病id
     *
     * @mbg.generated
     */
    private String diseaseid;

    /**
     * 套餐id
     *
     * @mbg.generated
     */
    private String setmealid;

    /**
     * 天数（第几天）
     *
     * @mbg.generated
     */
    private String number;

    /**
     * 动作完成id
     *
     * @mbg.generated
     */
    private String finishid;

    /**
     * 备用
     *
     * @mbg.generated
     */
    private String standby;
    
    
    private String diseaseName;

    private String riskGroup;
    
    private String subjectId;
    
    private Integer recid;
    
    private String sportName;

    private static final long serialVersionUID = 1L;
    
    
    

    public String getSportName() {
		return sportName;
	}

	public void setSportName(String sportName) {
		this.sportName = sportName;
	}

	public Integer getRecid() {
		return recid;
	}

	public void setRecid(Integer recid) {
		this.recid = recid;
	}

	public String getSubjectId() {
		return subjectId;
	}

	public void setSubjectId(String subjectId) {
		this.subjectId = subjectId;
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

    public String getDiseaseid() {
        return diseaseid;
    }

    public void setDiseaseid(String diseaseid) {
        this.diseaseid = diseaseid;
    }

    public String getSetmealid() {
        return setmealid;
    }

    public void setSetmealid(String setmealid) {
        this.setmealid = setmealid;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getFinishid() {
        return finishid;
    }

    public void setFinishid(String finishid) {
        this.finishid = finishid;
    }

    public String getStandby() {
        return standby;
    }

    public void setStandby(String standby) {
        this.standby = standby;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", userCode=").append(userCode);
        sb.append(", diseaseid=").append(diseaseid);
        sb.append(", setmealid=").append(setmealid);
        sb.append(", number=").append(number);
        sb.append(", finishid=").append(finishid);
        sb.append(", standby=").append(standby);
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
        YhSportRecommendRecordVojo other = (YhSportRecommendRecordVojo) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getUserCode() == null ? other.getUserCode() == null : this.getUserCode().equals(other.getUserCode()))
            && (this.getDiseaseid() == null ? other.getDiseaseid() == null : this.getDiseaseid().equals(other.getDiseaseid()))
            && (this.getSetmealid() == null ? other.getSetmealid() == null : this.getSetmealid().equals(other.getSetmealid()))
            && (this.getNumber() == null ? other.getNumber() == null : this.getNumber().equals(other.getNumber()))
            && (this.getFinishid() == null ? other.getFinishid() == null : this.getFinishid().equals(other.getFinishid()))
            && (this.getStandby() == null ? other.getStandby() == null : this.getStandby().equals(other.getStandby()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getUserCode() == null) ? 0 : getUserCode().hashCode());
        result = prime * result + ((getDiseaseid() == null) ? 0 : getDiseaseid().hashCode());
        result = prime * result + ((getSetmealid() == null) ? 0 : getSetmealid().hashCode());
        result = prime * result + ((getNumber() == null) ? 0 : getNumber().hashCode());
        result = prime * result + ((getFinishid() == null) ? 0 : getFinishid().hashCode());
        result = prime * result + ((getStandby() == null) ? 0 : getStandby().hashCode());
        return result;
    }
}