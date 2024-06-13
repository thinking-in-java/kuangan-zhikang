package com.yihe.applet.api.domain;

import java.io.Serializable;

public class YhUserHealthyPlanApp implements Serializable {
    private Integer id;

    /**
     * 用户code
     *
     * @mbg.generated
     */
    private String userCode;

    /**
     * 健康计划第几节，健康计划未开始时，courseSeq=0
     *
     * @mbg.generated
     */
    private Integer courseseq;

    /**
     * 健康计划第几天
     *
     * @mbg.generated
     */
    private Integer planDay;

    /**
     * 对应多个健康计划详情id 多个以;拼接
     *
     * @mbg.generated
     */
    private String plandetailId;

    /**
     * 健康计划id
     *
     * @mbg.generated
     */
    private Integer planId;

    /**
     * 加入健康计划时间
     *
     * @mbg.generated
     */
    private String planStartdate;

    /**
     * 结束健康计划时间
     *
     * @mbg.generated
     */
    private String planEnddate;
    //总天数
    private Integer plan_allnumber;
    //总积分
    private Integer plan_allintegral;
    private YhHealthyPlandays yhhealthyplandays;
    //完成任务的总个数
    private Integer plan_complate;
    //完成的总积分
    private Integer plan_integral;
    //唯一的标识靠这个获取任务的总个数
    private Integer plan_unique;
    private String plan_choose;
    
    public Integer getPlan_integral() {
		return plan_integral;
	}

	public void setPlan_integral(Integer plan_integral) {
		this.plan_integral = plan_integral;
	}

	public String getPlan_choose() {
		return plan_choose;
	}

	public void setPlan_choose(String plan_choose) {
		this.plan_choose = plan_choose;
	}

	public Integer getPlan_unique() {
		return plan_unique;
	}

	public void setPlan_unique(Integer plan_unique) {
		this.plan_unique = plan_unique;
	}

	public Integer getPlan_complate() {
		return plan_complate;
	}

	public void setPlan_complate(Integer plan_complate) {
		this.plan_complate = plan_complate;
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

	public YhHealthyPlandays getYhhealthyplandays() {
		return yhhealthyplandays;
	}

	public void setYhhealthyplandays(YhHealthyPlandays yhhealthyplandays) {
		this.yhhealthyplandays = yhhealthyplandays;
	}

	/**
     * 0.未开始；1.已开始；2.已结束；3.已取消
     *
     * @mbg.generated
     */
    private Integer planState;
    private Integer plan_number;
    private String plan_name;
    private static final long serialVersionUID = 1L;
    
    public Integer getPlan_number() {
		return plan_number;
	}

	public void setPlan_number(Integer plan_number) {
		this.plan_number = plan_number;
	}

	public String getPlan_name() {
		return plan_name;
	}

	public void setPlan_name(String plan_name) {
		this.plan_name = plan_name;
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

    public Integer getCourseseq() {
        return courseseq;
    }

    public void setCourseseq(Integer courseseq) {
        this.courseseq = courseseq;
    }

    public Integer getPlanDay() {
        return planDay;
    }

    public void setPlanDay(Integer planDay) {
        this.planDay = planDay;
    }

    public String getPlandetailId() {
        return plandetailId;
    }

    public void setPlandetailId(String plandetailId) {
        this.plandetailId = plandetailId;
    }

    public Integer getPlanId() {
        return planId;
    }

    public void setPlanId(Integer planId) {
        this.planId = planId;
    }

  

    public String getPlanStartdate() {
		return planStartdate;
	}

	public void setPlanStartdate(String planStartdate) {
		this.planStartdate = planStartdate;
	}

	public String getPlanEnddate() {
		return planEnddate;
	}

	public void setPlanEnddate(String planEnddate) {
		this.planEnddate = planEnddate;
	}

	public Integer getPlanState() {
        return planState;
    }

    public void setPlanState(Integer planState) {
        this.planState = planState;
    }

    @Override
	public String toString() {
		return "YhUserHealthyPlan [id=" + id + ", userCode=" + userCode + ", courseseq=" + courseseq + ", planDay="
				+ planDay + ", plandetailId=" + plandetailId + ", planId=" + planId + ", planStartdate=" + planStartdate
				+ ", planEnddate=" + planEnddate + ", yhhealthyplandays=" + yhhealthyplandays + ", planState="
				+ planState + ", plan_number=" + plan_number + ", plan_name=" + plan_name + "]";
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
        YhUserHealthyPlanApp other = (YhUserHealthyPlanApp) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getUserCode() == null ? other.getUserCode() == null : this.getUserCode().equals(other.getUserCode()))
            && (this.getCourseseq() == null ? other.getCourseseq() == null : this.getCourseseq().equals(other.getCourseseq()))
            && (this.getPlanDay() == null ? other.getPlanDay() == null : this.getPlanDay().equals(other.getPlanDay()))
            && (this.getPlandetailId() == null ? other.getPlandetailId() == null : this.getPlandetailId().equals(other.getPlandetailId()))
            && (this.getPlanId() == null ? other.getPlanId() == null : this.getPlanId().equals(other.getPlanId()))
            && (this.getPlanStartdate() == null ? other.getPlanStartdate() == null : this.getPlanStartdate().equals(other.getPlanStartdate()))
            && (this.getPlanEnddate() == null ? other.getPlanEnddate() == null : this.getPlanEnddate().equals(other.getPlanEnddate()))
            && (this.getPlanState() == null ? other.getPlanState() == null : this.getPlanState().equals(other.getPlanState()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getUserCode() == null) ? 0 : getUserCode().hashCode());
        result = prime * result + ((getCourseseq() == null) ? 0 : getCourseseq().hashCode());
        result = prime * result + ((getPlanDay() == null) ? 0 : getPlanDay().hashCode());
        result = prime * result + ((getPlandetailId() == null) ? 0 : getPlandetailId().hashCode());
        result = prime * result + ((getPlanId() == null) ? 0 : getPlanId().hashCode());
        result = prime * result + ((getPlanStartdate() == null) ? 0 : getPlanStartdate().hashCode());
        result = prime * result + ((getPlanEnddate() == null) ? 0 : getPlanEnddate().hashCode());
        result = prime * result + ((getPlanState() == null) ? 0 : getPlanState().hashCode());
        return result;
    }
}