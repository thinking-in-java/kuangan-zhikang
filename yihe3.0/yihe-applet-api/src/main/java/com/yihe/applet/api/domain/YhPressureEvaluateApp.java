package com.yihe.applet.api.domain;

public class YhPressureEvaluateApp {
    private Integer id;

    private String evaluateData;

    private Integer evaluateType;

    private String evaluateScore;

    private String userCode;

    private String evaluateDate;

    private String doDate;

    private Integer isDel;

    private String evaluateSelect;
    private String xgtype;
	private Integer l_score;
	private Integer s_score;
	
	

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

	public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getEvaluateData() {
        return evaluateData;
    }

    public void setEvaluateData(String evaluateData) {
        this.evaluateData = evaluateData == null ? null : evaluateData.trim();
    }

    public Integer getEvaluateType() {
        return evaluateType;
    }

    public void setEvaluateType(Integer evaluateType) {
        this.evaluateType = evaluateType;
    }

    public String getEvaluateScore() {
        return evaluateScore;
    }

    public void setEvaluateScore(String evaluateScore) {
        this.evaluateScore = evaluateScore == null ? null : evaluateScore.trim();
    }

    public String getUserCode() {
        return userCode;
    }

    public void setUserCode(String userCode) {
        this.userCode = userCode == null ? null : userCode.trim();
    }

    public String getEvaluateDate() {
        return evaluateDate;
    }

    public void setEvaluateDate(String evaluateDate) {
        this.evaluateDate = evaluateDate == null ? null : evaluateDate.trim();
    }

    public String getDoDate() {
        return doDate;
    }

    public void setDoDate(String doDate) {
        this.doDate = doDate == null ? null : doDate.trim();
    }

    public Integer getIsDel() {
        return isDel;
    }

    public void setIsDel(Integer isDel) {
        this.isDel = isDel;
    }

    public String getEvaluateSelect() {
        return evaluateSelect;
    }

    public void setEvaluateSelect(String evaluateSelect) {
        this.evaluateSelect = evaluateSelect == null ? null : evaluateSelect.trim();
    }

	
}