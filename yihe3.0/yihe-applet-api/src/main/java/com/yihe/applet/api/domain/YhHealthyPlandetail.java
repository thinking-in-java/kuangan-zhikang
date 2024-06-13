package com.yihe.applet.api.domain;

import java.io.Serializable;

public class YhHealthyPlandetail implements Serializable {
    private Integer id;

    /**
     * 计划内容标题
     *
     * @mbg.generated
     */
    private String plandetailTitle;

    /**
     * 计划内容类型 1:纯文章完成任务;2:文章+记录健康数据; 3:选择题; 4：看视频或者听音频
     *
     * @mbg.generated
     */
    private Integer plandetailType;

    /**
     * 计划内容记录健康数据连接
     *
     * @mbg.generated
     */
    private String plandetailUrl;

    /**
     * 计划内容
     *
     * @mbg.generated
     */
    private String plandetailContent;

    /**
     * 计划完成次数
     *
     * @mbg.generated
     */
    private Integer plandetailTotal;

    private String plandetailTip;
    private String plandetail_jumpuri;
    private String plandetail_finishportrait;
    

    private static final long serialVersionUID = 1L;
    


	public String getPlandetail_jumpuri() {
		return plandetail_jumpuri;
	}

	public void setPlandetail_jumpuri(String plandetail_jumpuri) {
		this.plandetail_jumpuri = plandetail_jumpuri;
	}

	public String getPlandetail_finishportrait() {
		return plandetail_finishportrait;
	}

	public void setPlandetail_finishportrait(String plandetail_finishportrait) {
		this.plandetail_finishportrait = plandetail_finishportrait;
	}

	public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPlandetailTitle() {
        return plandetailTitle;
    }

    public void setPlandetailTitle(String plandetailTitle) {
        this.plandetailTitle = plandetailTitle;
    }

    public Integer getPlandetailType() {
        return plandetailType;
    }

    public void setPlandetailType(Integer plandetailType) {
        this.plandetailType = plandetailType;
    }

    public String getPlandetailUrl() {
        return plandetailUrl;
    }

    public void setPlandetailUrl(String plandetailUrl) {
        this.plandetailUrl = plandetailUrl;
    }

    public String getPlandetailContent() {
        return plandetailContent;
    }

    public void setPlandetailContent(String plandetailContent) {
        this.plandetailContent = plandetailContent;
    }

    public Integer getPlandetailTotal() {
        return plandetailTotal;
    }

    public void setPlandetailTotal(Integer plandetailTotal) {
        this.plandetailTotal = plandetailTotal;
    }

    public String getPlandetailTip() {
        return plandetailTip;
    }

    public void setPlandetailTip(String plandetailTip) {
        this.plandetailTip = plandetailTip;
    }

    @Override
	public String toString() {
		return "YhHealthyPlandetail [id=" + id + ", plandetailTitle=" + plandetailTitle + ", plandetailType="
				+ plandetailType + ", plandetailUrl=" + plandetailUrl + ", plandetailContent=" + plandetailContent
				+ ", plandetailTotal=" + plandetailTotal + ", plandetailTip=" + plandetailTip + ", plandetail_jumpuri="
				+ plandetail_jumpuri + ", plandetail_finishportrait=" + plandetail_finishportrait + "]";
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
        YhHealthyPlandetail other = (YhHealthyPlandetail) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getPlandetailTitle() == null ? other.getPlandetailTitle() == null : this.getPlandetailTitle().equals(other.getPlandetailTitle()))
            && (this.getPlandetailType() == null ? other.getPlandetailType() == null : this.getPlandetailType().equals(other.getPlandetailType()))
            && (this.getPlandetailUrl() == null ? other.getPlandetailUrl() == null : this.getPlandetailUrl().equals(other.getPlandetailUrl()))
            && (this.getPlandetailContent() == null ? other.getPlandetailContent() == null : this.getPlandetailContent().equals(other.getPlandetailContent()))
            && (this.getPlandetailTotal() == null ? other.getPlandetailTotal() == null : this.getPlandetailTotal().equals(other.getPlandetailTotal()))
            && (this.getPlandetailTip() == null ? other.getPlandetailTip() == null : this.getPlandetailTip().equals(other.getPlandetailTip()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getPlandetailTitle() == null) ? 0 : getPlandetailTitle().hashCode());
        result = prime * result + ((getPlandetailType() == null) ? 0 : getPlandetailType().hashCode());
        result = prime * result + ((getPlandetailUrl() == null) ? 0 : getPlandetailUrl().hashCode());
        result = prime * result + ((getPlandetailContent() == null) ? 0 : getPlandetailContent().hashCode());
        result = prime * result + ((getPlandetailTotal() == null) ? 0 : getPlandetailTotal().hashCode());
        result = prime * result + ((getPlandetailTip() == null) ? 0 : getPlandetailTip().hashCode());
        return result;
    }
}