package com.yihe.system.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.yihe.common.annotation.Excel;
import com.yihe.common.core.domain.BaseEntity;
import java.util.Date;

/**
 * 用户健康计划日志对象 yh_user_healthy_plan
 * 
 * @author yihe
 * @date 2020-04-27
 */
public class YhUserHealthyPlan extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** $column.columnComment */
    private Long id;

    /** 用户code */
    @Excel(name = "用户code")
    private String userCode;

    /** 当前计划详情id */
    @Excel(name = "当前计划详情id")
    private Integer courseseq;

    /** 计划执行天数 */
    @Excel(name = "计划执行天数")
    private Integer planDay;

    /** 计划详情id */
    @Excel(name = "计划详情id")
    private String plandetailId;

    /** 计划id */
    @Excel(name = "计划id")
    private Integer planId;

    /** 计划开始时间 */
    @Excel(name = "计划开始时间", width = 30, dateFormat = "yyyy-MM-dd")
    private String planStartdate;

    /** 计划结束时间 */
    @Excel(name = "计划结束时间", width = 30, dateFormat = "yyyy-MM-dd")
    private String planEnddate;

    /** 0未开始；1已开始；2已结束；3已取消 */
    @Excel(name = "0未开始；1已开始；2已结束；3已取消")
    private Integer planState;

    /** 选择题选了哪个 */
    @Excel(name = "选择题选了哪个")
    private String planChoose;

    /** 计划次数唯一标识 */
    @Excel(name = "计划次数唯一标识")
    private Long planUnique;

    public void setId(Long id) 
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }
    public void setUserCode(String userCode) 
    {
        this.userCode = userCode;
    }

    public String getUserCode() 
    {
        return userCode;
    }
    public void setCourseseq(Integer courseseq) 
    {
        this.courseseq = courseseq;
    }

    public Integer getCourseseq() 
    {
        return courseseq;
    }
    public void setPlanDay(Integer planDay) 
    {
        this.planDay = planDay;
    }

    public Integer getPlanDay() 
    {
        return planDay;
    }
    public void setPlandetailId(String plandetailId) 
    {
        this.plandetailId = plandetailId;
    }

    public String getPlandetailId() 
    {
        return plandetailId;
    }
    public void setPlanId(Integer planId) 
    {
        this.planId = planId;
    }

    public Integer getPlanId() 
    {
        return planId;
    }
    public void setPlanStartdate(String planStartdate) 
    {
        this.planStartdate = planStartdate;
    }

    public String getPlanStartdate() 
    {
        return planStartdate;
    }
    public void setPlanEnddate(String planEnddate) 
    {
        this.planEnddate = planEnddate;
    }

    public String getPlanEnddate() 
    {
        return planEnddate;
    }
    public void setPlanState(Integer planState) 
    {
        this.planState = planState;
    }

    public Integer getPlanState() 
    {
        return planState;
    }
    public void setPlanChoose(String planChoose) 
    {
        this.planChoose = planChoose;
    }

    public String getPlanChoose() 
    {
        return planChoose;
    }
    public void setPlanUnique(Long planUnique) 
    {
        this.planUnique = planUnique;
    }

    public Long getPlanUnique() 
    {
        return planUnique;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("userCode", getUserCode())
            .append("courseseq", getCourseseq())
            .append("planDay", getPlanDay())
            .append("plandetailId", getPlandetailId())
            .append("planId", getPlanId())
            .append("planStartdate", getPlanStartdate())
            .append("planEnddate", getPlanEnddate())
            .append("planState", getPlanState())
            .append("planChoose", getPlanChoose())
            .append("planUnique", getPlanUnique())
            .toString();
    }
}
