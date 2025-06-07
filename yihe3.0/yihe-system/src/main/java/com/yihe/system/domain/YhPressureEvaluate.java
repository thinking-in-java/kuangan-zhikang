package com.yihe.system.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.yihe.common.annotation.Excel;
import com.yihe.common.core.domain.BaseEntity;

/**
 * 压力管理三大评估问卷数据对象 yh_pressure_evaluate
 * 
 * @author yihe
 * @date 2020-04-07
 */
public class YhPressureEvaluate extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** $column.columnComment */
    private Long id;

    /** $column.columnComment */
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private String evaluateData;

    /** $column.columnComment */
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private Long evaluateType;

    /** $column.columnComment */
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private String evaluateScore;

    /** $column.columnComment */
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private String userCode;

    /** $column.columnComment */
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private String evaluateDate;

    /** $column.columnComment */
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private String doDate;

    /** $column.columnComment */
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private Long isDel;

    /** $column.columnComment */
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private String evaluateSelect;
    
  
    private String xgtype;
	private Integer l_score;
	private Integer s_score;

    private String mobile;

    private String times;

    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTimes() {
        return times;
    }

    public void setTimes(String times) {
        this.times = times;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getXgtype() {
		return xgtype;
	}

	public void setXgtype(String xgtype) {
		this.xgtype = xgtype;
	}

	public Integer getL_score() {
		return l_score;
	}

	public void setL_score(Integer l_score) {
		this.l_score = l_score;
	}

	public Integer getS_score() {
		return s_score;
	}

	public void setS_score(Integer s_score) {
		this.s_score = s_score;
	}

	public void setId(Long id) 
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }
    public void setEvaluateData(String evaluateData) 
    {
        this.evaluateData = evaluateData;
    }

    public String getEvaluateData() 
    {
        return evaluateData;
    }
    public void setEvaluateType(Long evaluateType) 
    {
        this.evaluateType = evaluateType;
    }

    public Long getEvaluateType() 
    {
        return evaluateType;
    }
    public void setEvaluateScore(String evaluateScore) 
    {
        this.evaluateScore = evaluateScore;
    }

    public String getEvaluateScore() 
    {
        return evaluateScore;
    }
    public void setUserCode(String userCode) 
    {
        this.userCode = userCode;
    }

    public String getUserCode() 
    {
        return userCode;
    }
    public void setEvaluateDate(String evaluateDate) 
    {
        this.evaluateDate = evaluateDate;
    }

    public String getEvaluateDate() 
    {
        return evaluateDate;
    }
    public void setDoDate(String doDate) 
    {
        this.doDate = doDate;
    }

    public String getDoDate() 
    {
        return doDate;
    }
    public void setIsDel(Long isDel) 
    {
        this.isDel = isDel;
    }

    public Long getIsDel() 
    {
        return isDel;
    }
    public void setEvaluateSelect(String evaluateSelect) 
    {
        this.evaluateSelect = evaluateSelect;
    }

    public String getEvaluateSelect() 
    {
        return evaluateSelect;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("evaluateData", getEvaluateData())
            .append("evaluateType", getEvaluateType())
            .append("evaluateScore", getEvaluateScore())
            .append("userCode", getUserCode())
            .append("evaluateDate", getEvaluateDate())
            .append("doDate", getDoDate())
            .append("isDel", getIsDel())
            .append("evaluateSelect", getEvaluateSelect())
            .toString();
    }
}
