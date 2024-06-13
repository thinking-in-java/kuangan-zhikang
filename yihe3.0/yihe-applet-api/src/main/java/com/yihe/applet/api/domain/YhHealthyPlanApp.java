package com.yihe.applet.api.domain;

import java.io.Serializable;

public class YhHealthyPlanApp implements Serializable {
    private Integer id;

    /**
     * 健康计划名称
     *
     * @mbg.generated
     */
    private String planName;

    /**
     * 健康计划封面
     *
     * @mbg.generated
     */
    private String planImg;

    /**
     * 健康计划共有多少天
     *
     * @mbg.generated
     */
    private Integer planNumber;

    /**
     * 健康计划积分
     *
     * @mbg.generated
     */
    private String planScore;

    /**
     * 健康计划浏览数
     *
     * @mbg.generated
     */
    private String planWatch;

    /**
     * 健康计划简介
     *
     * @mbg.generated
     */
    private String planDescription;
    private Integer total;
    private String plan_people;
    //总任务数
    private Integer plan_allnumber;
    //总积分
    private Integer plan_allintegral;
    private  Integer plan_isapply;

    private Boolean havedeiss;
    private static final long serialVersionUID = 1L;

    public Boolean getHavedeiss() {
        return havedeiss;
    }

    public void setHavedeiss(Boolean havedeiss) {
        this.havedeiss = havedeiss;
    }

    public Integer getPlan_isapply() {
        return plan_isapply;
    }

    public void setPlan_isapply(Integer plan_isapply) {
        this.plan_isapply = plan_isapply;
    }

    public Integer getPlan_allnumber() {
		return plan_allnumber;
	}

	public void setPlan_allnumber(Integer plan_allnumber) {
		this.plan_allnumber = plan_allnumber;
	}

	public Integer getPlan_allintegral() {
		return plan_allintegral;
	}

	public void setPlan_allintegral(Integer plan_allintegral) {
		this.plan_allintegral = plan_allintegral;
	}

	public String getPlan_people() {
		return plan_people;
	}

	public void setPlan_people(String plan_people) {
		this.plan_people = plan_people;
	}

	public Integer getTotal() {
		return total;
	}

	public void setTotal(Integer total) {
		this.total = total;
	}

	public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPlanName() {
        return planName;
    }

    public void setPlanName(String planName) {
        this.planName = planName;
    }

    public String getPlanImg() {
        return planImg;
    }

    public void setPlanImg(String planImg) {
        this.planImg = planImg;
    }

    public Integer getPlanNumber() {
        return planNumber;
    }

    public void setPlanNumber(Integer planNumber) {
        this.planNumber = planNumber;
    }

    public String getPlanScore() {
        return planScore;
    }

    public void setPlanScore(String planScore) {
        this.planScore = planScore;
    }

    public String getPlanWatch() {
        return planWatch;
    }

    public void setPlanWatch(String planWatch) {
        this.planWatch = planWatch;
    }

    public String getPlanDescription() {
        return planDescription;
    }

    public void setPlanDescription(String planDescription) {
        this.planDescription = planDescription;
    }

    @Override
	public String toString() {
		return "YhHealthyPlan [id=" + id + ", planName=" + planName + ", planImg=" + planImg + ", planNumber="
				+ planNumber + ", planScore=" + planScore + ", planWatch=" + planWatch + ", planDescription="
				+ planDescription + ", total=" + total + ", plan_people=" + plan_people + ", plan_allnumber="
				+ plan_allnumber + ", plan_allintegral=" + plan_allintegral + "]";
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
        YhHealthyPlanApp other = (YhHealthyPlanApp) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getPlanName() == null ? other.getPlanName() == null : this.getPlanName().equals(other.getPlanName()))
            && (this.getPlanImg() == null ? other.getPlanImg() == null : this.getPlanImg().equals(other.getPlanImg()))
            && (this.getPlanNumber() == null ? other.getPlanNumber() == null : this.getPlanNumber().equals(other.getPlanNumber()))
            && (this.getPlanScore() == null ? other.getPlanScore() == null : this.getPlanScore().equals(other.getPlanScore()))
            && (this.getPlanWatch() == null ? other.getPlanWatch() == null : this.getPlanWatch().equals(other.getPlanWatch()))
            && (this.getPlanDescription() == null ? other.getPlanDescription() == null : this.getPlanDescription().equals(other.getPlanDescription()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getPlanName() == null) ? 0 : getPlanName().hashCode());
        result = prime * result + ((getPlanImg() == null) ? 0 : getPlanImg().hashCode());
        result = prime * result + ((getPlanNumber() == null) ? 0 : getPlanNumber().hashCode());
        result = prime * result + ((getPlanScore() == null) ? 0 : getPlanScore().hashCode());
        result = prime * result + ((getPlanWatch() == null) ? 0 : getPlanWatch().hashCode());
        result = prime * result + ((getPlanDescription() == null) ? 0 : getPlanDescription().hashCode());
        return result;
    }
}