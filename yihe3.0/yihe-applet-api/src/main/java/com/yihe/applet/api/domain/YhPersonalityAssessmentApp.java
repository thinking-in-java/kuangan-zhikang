package com.yihe.applet.api.domain;

public class YhPersonalityAssessmentApp {
    private Integer paId;

    private String paQue;

    private Integer paDel;

    private Integer paYes;

    private Integer paNo;

    public Integer getPaId() {
        return paId;
    }

    public void setPaId(Integer paId) {
        this.paId = paId;
    }

    public String getPaQue() {
        return paQue;
    }

    public void setPaQue(String paQue) {
        this.paQue = paQue == null ? null : paQue.trim();
    }

    public Integer getPaDel() {
        return paDel;
    }

    public void setPaDel(Integer paDel) {
        this.paDel = paDel;
    }

    public Integer getPaYes() {
        return paYes;
    }

    public void setPaYes(Integer paYes) {
        this.paYes = paYes;
    }

    public Integer getPaNo() {
        return paNo;
    }

    public void setPaNo(Integer paNo) {
        this.paNo = paNo;
    }
}