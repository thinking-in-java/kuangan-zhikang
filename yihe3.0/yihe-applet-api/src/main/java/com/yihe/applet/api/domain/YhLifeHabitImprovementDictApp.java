package com.yihe.applet.api.domain;

import java.util.Date;

public class YhLifeHabitImprovementDictApp {
    private Integer id;

    private String title;

    private String userCode;

    private String risk;

    private Integer assessScores;

    private Integer questionId;

    private String analyzename;

    private String result;

    private Date dictTime;

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