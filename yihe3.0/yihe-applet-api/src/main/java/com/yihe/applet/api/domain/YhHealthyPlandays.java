package com.yihe.applet.api.domain;

import java.io.Serializable;
import java.util.List;

public class YhHealthyPlandays implements Serializable {
    private Integer id;

    /**
     * 计划第几天
     *
     * @mbg.generated
     */
    private Integer planday;

    /**
     * 对应计划详情id
     *
     * @mbg.generated
     */
    private String plandetailId;

    /**
     * 计划id
     *
     * @mbg.generated
     */
    private Integer planId;

    private Integer plan_isskip;
	
	private Integer plan_num;

    public Integer getPlan_num() {
        return plan_num;
    }

    public void setPlan_num(Integer plan_num) {
        this.plan_num = plan_num;
    }


    private List<YhHealthyPlandetail> yhhealthyplandetail;

    private static final long serialVersionUID = 1L;

    public Integer getPlan_isskip() {
        return plan_isskip;
    }

    public void setPlan_isskip(Integer plan_isskip) {
        this.plan_isskip = plan_isskip;
    }

    public List<YhHealthyPlandetail> getYhhealthyplandetail() {
		return yhhealthyplandetail;
	}

	public void setYhhealthyplandetail(List<YhHealthyPlandetail> yhhealthyplandetail) {
		this.yhhealthyplandetail = yhhealthyplandetail;
	}

	public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getPlanday() {
        return planday;
    }

    public void setPlanday(Integer planday) {
        this.planday = planday;
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

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", planday=").append(planday);
        sb.append(", plandetailId=").append(plandetailId);
        sb.append(", planId=").append(planId);
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
        YhHealthyPlandays other = (YhHealthyPlandays) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getPlanday() == null ? other.getPlanday() == null : this.getPlanday().equals(other.getPlanday()))
            && (this.getPlandetailId() == null ? other.getPlandetailId() == null : this.getPlandetailId().equals(other.getPlandetailId()))
            && (this.getPlanId() == null ? other.getPlanId() == null : this.getPlanId().equals(other.getPlanId()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getPlanday() == null) ? 0 : getPlanday().hashCode());
        result = prime * result + ((getPlandetailId() == null) ? 0 : getPlandetailId().hashCode());
        result = prime * result + ((getPlanId() == null) ? 0 : getPlanId().hashCode());
        return result;
    }
}