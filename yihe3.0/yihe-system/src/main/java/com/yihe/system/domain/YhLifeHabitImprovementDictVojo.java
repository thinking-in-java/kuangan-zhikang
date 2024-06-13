package com.yihe.system.domain;

import java.util.Date;

public class YhLifeHabitImprovementDictVojo {
    private Integer id;

    private String title;

    private String userCode;

    private String risk;

    private Integer assessScores;

    private Integer questionId;

    private String analyzename;

    private String result;

    private Date dictTime;
    
    private String userName;
    
    private String sex;
    
    private String name;
    
    private int frequency;
    
    
	public int getFrequency() {
		return frequency;
	}

	public void setFrequency(int frequency) {
		this.frequency = frequency;
	}
    public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title == null ? null : title.trim();
    }

    public String getUserCode() {
        return userCode;
    }

    public void setUserCode(String userCode) {
        this.userCode = userCode == null ? null : userCode.trim();
    }

    public String getRisk() {
        return risk;
    }

    public void setRisk(String risk) {
        this.risk = risk == null ? null : risk.trim();
    }

    public Integer getAssessScores() {
        return assessScores;
    }

    public void setAssessScores(Integer assessScores) {
        this.assessScores = assessScores;
    }

    public Integer getQuestionId() {
        return questionId;
    }

    public void setQuestionId(Integer questionId) {
        this.questionId = questionId;
    }

    public String getAnalyzename() {
        return analyzename;
    }

    public void setAnalyzename(String analyzename) {
        this.analyzename = analyzename == null ? null : analyzename.trim();
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result == null ? null : result.trim();
    }

    public Date getDictTime() {
        return dictTime;
    }

    public void setDictTime(Date dictTime) {
        this.dictTime = dictTime;
    }
}